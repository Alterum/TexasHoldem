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

public class BusinessProcess {
	
	private HashMap<String, Integer> bankInRound =
			new HashMap<String, Integer>();
	private int currentRound;
	private OutputObject output;
	private PokerTable table;
	private DBDriver db;
	private Logger log;
	
	public BusinessProcess(PokerTable table, DBDriver driver) {
		db = driver;
		this.table = table;
		log = new Logger("bussines_process.log");
	}
	
	public OutputObject getOutputData(String name) {
		
		HashMap<String, Boolean> map2 =
				convertAccessButtons(name);
		HashMap<String, String> map1 =
				convertInfo(name);
		
		output = new OutputToClient(map1, map2);

		return output;
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
		
		log.log("score: "+playerScore+" bet:"+currentBet);
		
		if(playerScore > currentBet)
			map.put("raise", true);
		
		if(currentBet == 0)
			map.put("check", true);
		else if(playerScore >= currentBet)
			map.put("call", true);
		
		return map; 
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

	public void setInputData(OutputObject obj) {
		
		OutputToServer input = (OutputToServer) obj;
		
		
		int bet = input.getBet();
		int status = input.getPlayerStatus();
		String name = input.toString();
		
		log.log("setInputData- bet: "+bet+" status: "+status+" name: "+name);
		
		Player player = table.getPlayer(name);
		
		player.setScore(player.getScore()-bet);
		player.setBet(player.getBet()+bet);
		player.setStatus(status);
		
		log.log("setInputData- bankInRound: "+(table.getBankInRound()+bet));
		
		table.setBankInRound(table.getBankInRound()+bet);
		
		
		if (bankInRound.get(name) != null)
			bankInRound.put(name, bankInRound.get(name)+bet);
		else
			bankInRound.put(name, bet);
		
		compareBets(name);

		// Start log
//		for(Player s : table.getActivePlayers())
//			log.log("ActivePlayer "+s.getName());
//		
//		for(Player s : table.getPlayers())
//			log.log("TablePlayer "+s.getName());
		for(String s : bankInRound.keySet())
			log.log("setInputData: before setCurentBet: "+s + " sum bet in round: "+bankInRound.get(s));
		// End log
		
		table.setCurrentBet(bankInRound.get(name));
		
		// Start log
		for(String s : bankInRound.keySet())
			log.log("setInputData: after setCurentBet: "+s + " sum bet in round: "+bankInRound.get(s));
		log.log("SetInputData: "+name+" bet: "+bet);
		log.log("setInputData: Table currentBet: "+table.getCurrentBet());
		// End log
		
	}
	
	public void compareBets(String name) {

		int playerBet = bankInRound.get(name);
		boolean isNextRound = true;
		int bank = 0;
		for(String key : bankInRound.keySet()) {
			if(playerBet != bankInRound.get(key)) {
				isNextRound = false;
				break;
			}
			playerBet = bankInRound.get(key);
			bank += playerBet;
		}
		
		if(isNextRound) { // NEXT ROUND
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
}
	