package projecttu.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;


import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;
import projecttu.OutputObject.OutputToClient;
import projecttu.OutputObject.OutputToServer;
import projecttu.OutputObject.ServerParser;

public class BussinesProcess {
	public BussinesProcess(PokerTable table) {
		this.table = table;
		parser = new ServerParser(table);
	}
	
	public OutputObject getOutputData(String name) {
		
		System.out.println("getOutputData before "+Thread.currentThread().getName());
		
		// BRED
		int READY_TO_START = -5;
		if(table.getPlayer(name).getStatus() == READY_TO_START)
			waitNewGame();
		
		waitInQueue();
		
		HashMap<String, Boolean> map2 = convertAccessButtons(name);
		HashMap<String, String> map1 = convertInfo(name);
		
		output = new OutputToClient(map1, map2);

		System.out.println("getoutputData after "+Thread.currentThread().getName());
		return output;
	}
	
	private void waitInQueue() {
		try {
			smphr.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	private void waitNewGame() {
		while(!newGame) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setInputData(OutputObject obj) {
		
		OutputToServer input = (OutputToServer) obj;
		
		
		int bet = input.getBet();
		int status = input.getPlayerStatus();
		
		String name = input.toString();
		
		Player player = table.getPlayer(name);
		
		player.setScore(player.getScore()-bet);
		player.setBet(player.getBet()+bet);
		player.setStatus(status);
		
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		table.setBankInRound(table.getBankInRound()+bet);
		
		compareBets(name, bet);

		for(String s : bankInRound.keySet())
			System.out.println("before: "+s + " sum bet in round: "+bankInRound.get(s));
//		System.out.println(Thread.currentThread().getName()+" currentBet: "+bet);
		for(Player s : table.getActivePlayers())
				System.out.println("ActivePlayer "+s.getName());
//		compareBets(name, bet);
//		
		for(Player s : table.getPlayers())
			System.out.println("TablePlayer "+s.getName());
				
		table.setCurrentBet(bankInRound.get(name));
//		
		System.out.println(name+" bet "+bet);
		System.out.println("currentBet: "+table.getCurrentBet());
		for(String s : bankInRound.keySet())
			System.out.println("after: "+s + " sum bet in round: "+bankInRound.get(s));
//		
		smphr.release();
		
	}
	
	public void compareBets(String name, int bet) {
		bankInRound.put(name, bankInRound.get(name)+bet);
		int playerBet = bankInRound.get(name);
		boolean flag = true;
		int bank = 0;
		for(String key : bankInRound.keySet()) {
			if(playerBet != bankInRound.get(key)) {
				flag = false;
				break;
			}
			playerBet = bankInRound.get(key);
			bank += playerBet;
		}
		
		if(flag) { // urovnjalis NEXT ROUND
			currentRound++;
			
			isGameOver(name);
			
			table.setBank(table.getBankInRound());
			table.setBankInRound(0);
			
			for(String key : bankInRound.keySet()) {
				bankInRound.put(key, 0);
				table.getPlayer(name).setBet(0);
				table.setCurrentBet(0);
			}
		}
	}
	
	public void isGameOver(String name) {
		Player player = table.getPlayer(name);
		if(currentRound == 4) { // GAME OVER
			Player winer =	table.getPlayerWithBestHand();
			if(table.getPlayer(name) == winer) {
				player.setScore(table.getBank());
			}
		}
	}
	
	public synchronized void startGame() {
			table.newGame();
			
			System.out.println("NEW GANE NEW GAME NEW GANE NEW GAME NEW GANE NEW GAME NEW GANE NEW GAME NEW GANE NEW GAME ");
			// function set Players to SmallBlind and BigBlind
			table.selectSmallAndBigBlinds();
			
			// Create Semaphore
			smphr = new Semaphore(1, true);
			
			int index = 0;
			for(Player player : table.getPlayers()) {
				player.setStatus(1);
				table.setActivePlayer(index++, player);
				player.setStatus(1);
				player.setBet(0);
				bankInRound.put(player.getName(), player.getBet());
				System.out.println("TablePlayerSTRAT "+player.getName());
			}
			
			table.dealCardsToPlayers();
			table.dealCardsToTheTable(5);
			table.getWinCombination();

			currentRound = 0;
	}
	
	public synchronized HashMap<String, String> convertInfo(String name) {
		HashMap<String, String> map = new HashMap<String, String>();

		setPlayerInfo(name, map);

		setOpponentsInfo(name, map);

		setTableInfo(map);
		
		return map;
	}
	
	private void setTableInfo(HashMap<String, String> map) {
		String cards="";
		if(currentRound == 1){ // 3 cards visible
			cards += "BB BB "+table.getCardsOnTable()[2] + " "+
					table.getCardsOnTable()[1] + " " +
					table.getCardsOnTable()[0];
		} else if(currentRound == 2){ // 4 cards visible
			cards += "BB "+table.getCardsOnTable()[3] + " "+
					table.getCardsOnTable()[2] + " " +
					table.getCardsOnTable()[1] + " " +
					table.getCardsOnTable()[0];
		} else if(currentRound == 3) { // 5 cards visible	
			for(String card : table.getCardsOnTable())
				cards+=card+" ";
		} else // All cards invisible
			cards += "BB BB BB BB BB";
		map.put("cards", cards);
		map.put("currentBet", Integer.toString(table.getCurrentBet()));
		map.put("bank", Integer.toString(table.getBank()));
		map.put("bankInRound", Integer.toString(table.getBankInRound()));
		map.put("currentRound", Integer.toString(currentRound));
		
	}

	private void setPlayerInfo(String name, HashMap<String, String> map) {
		map.put("playerName", name);
		map.put("playerStatus", Integer.toString(table.getPlayer(name).getStatus()));
		map.put("playerBank", Integer.toString(table.getPlayer(name).getScore()));
		String playerCards = "";
		for(String card : table.getPlayer(name).getHand())
			playerCards += card+" "; 
		map.put("playerCards", playerCards);
		map.put("playerBet", Integer.toString(table.getPlayer(name).getBet()));
	}
	
	private void setOpponentsInfo(String name, HashMap<String, String> map) {
		String oppName="oppName";
		String oppCards="oppCards";
		String oppBank ="oppBank";
		String oppBet ="oppBet";
		int index = 1;
		for(Player player : table.getPlayers()) {
			if(player.getName() == name)
				continue;
			map.put(oppName+index, player.getName());
			map.put(oppCards+index, player.getHand()[0]+" "+player.getHand()[1]);
			map.put(oppBank+index, Integer.toString(player.getScore()));
			map.put(oppBet+index, Integer.toString(player.getBet()));
			index++;
		}
		
	}

	public HashMap<String, Boolean> convertAccessButtons(String name) {
		String[] bttnsNames =
			{"play", "call", "raise", "fold", "check", "newGame"};
		
		HashMap<String, Boolean> map = 
				new HashMap<String, Boolean>();
		
		for(String key : bttnsNames)
			map.put(key, false);
				
		if(table.getPlayer(name).getStatus() == 0) // If push button fold
			return map;
		else if (table.getPlayer(name).getStatus() == -1) {
			map.put("newGame", true);
			return map;
		}
		map.put("fold", true);
		
		int playerScore = table.getPlayer(name).getScore();
		int currentBet = table.getCurrentBet();
		
		if(playerScore > currentBet)
			map.put("raise", true);
		
		if(currentBet == 0)
			map.put("check", true);
		else if(playerScore >= currentBet)
			map.put("call", true);
		
		return map; 
	}
	
	public boolean isStartGame() {
		return startToGame;
	}
	
	public void setStart(boolean flag) {
		startToGame = flag;
	}
	
	public synchronized boolean addPlayerToTheTable(Player player) {
		return table.addPlayer(player);
	}
	public synchronized Player getPlayer(String name) {
		return table.getPlayer(name);
	}
	public synchronized boolean removePlayerFromTheTable(Player player) {
		return table.removePlayer(player);
	}
	public synchronized ArrayList<Player> getPlayers() {
		return table.getPlayers();
	}
	
	public boolean isNewGame() {
		return newGame;
	}
	
	public void setNewGame(boolean t) {
		newGame = t;
	}
	
	private HashMap<String, Integer> bankInRound =
			new HashMap<String, Integer>();
	private ServerParser parser;
	private int currentRound;
	private OutputObject output;
	private boolean startToGame = false;
	private boolean newGame = false;
	private PokerTable table;
	private Semaphore smphr;

}
