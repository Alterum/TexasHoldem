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
	private ConvertData convert;
	private int currentRound;
	private OutputObject output;
	private PokerTable table;
	private DBDriver db;
	private Logger log;
	
	public BusinessProcess(PokerTable table, DBDriver driver) {
		db = driver;
		this.table = table;
		
		for(Player player : table.getPlayers())
			bankInRound.put(player.getName(), 0);
		
		log = new Logger("bussines_process.log");
	}
	
	public OutputObject getOutputData(String name) {
		
		HashMap<String, Boolean> buttons =
				convertAccessButtons(name);
		HashMap<String, String> info =
				convertInfo(name);
		
		output = new OutputToClient(info, buttons);
		
		log.log("OutputData, current round "+currentRound);

		return output;
	}
	
	public HashMap<String, Boolean> convertAccessButtons(String name) {
		String[] bttnsNames =
			{"play", "call", "raise", "fold", "check", "newGame"};
		
		HashMap<String, Boolean> map = 
				new HashMap<String, Boolean>();
		
		for(String key : bttnsNames)
			map.put(key, false);
		
		int OBSERVER = 0;
		int NEWGAME = -1;
		
		if(table.getPlayer(name).getStatus() == OBSERVER) // If push button fold
			return map;
		else if (table.getPlayer(name).getStatus() == NEWGAME) {
			map.put("newGame", true);
			return map;
		}
		map.put("fold", true);
		
		int playerScore = table.getPlayer(name).getScore();
		int currentBet = table.getCurrentBet();
		
		log.log("score: "+playerScore+" bet:"+currentBet);
		
		// 
		if(playerScore > currentBet)
			map.put("raise", true);
		
		if(currentBet == 0)
			map.put("check", true);
		else if(playerScore >= currentBet)
			map.put("call", true);
		
		return map; 
	}
	
	public synchronized HashMap<String, String> convertInfo(String name) {
		
		HashMap<String, String> map =
				new HashMap<String, String>();
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
		map.put("playerCards", getPlayerCards(name));
		map.put("playerStatus", 
				Integer.toString(
						table.getPlayer(name).getStatus()));
		map.put("playerBank", 
				Integer.toString(
						table.getPlayer(name).getScore()));
		map.put("playerBet", 
				Integer.toString(
						table.getPlayer(name).getBet()));
	}
	
	private String getPlayerCards(String name) {
		String playerCards = "";
		for(String card : table.getPlayer(name).getHand())
			playerCards += card+" ";
		return playerCards;
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
		
		log.log("SetInputData - name: "+input.toString());
		
		roundGameProcess(
				input.toString(), 
				input.getBet(), 
				input.getPlayerStatus());
	}
	
	private void roundGameProcess(String name, int bet, int status) {
		setPlayerData(name, bet, status);
		setBankInRound(name, bet);
		comparePlayersBets(name);
		setTableCurrentBet(name, bet);
		
	}

	private void setTableCurrentBet(String name, int bet) {
		// Start log
		for(String playerName : bankInRound.keySet())
			log.log("setInputData: before setCurentBet: "+playerName+
					" sum bet in round: "+bankInRound.get(playerName));
		// End log
		
		table.setCurrentBet(bankInRound.get(name));
		
		// Start log
		for(String playerName : bankInRound.keySet())
			log.log("setInputData: after setCurentBet: "+playerName+
					" sum bet in round: "+bankInRound.get(playerName));
		log.log("SetInputData: "+name+" bet: "+bet);
		log.log("setInputData: Table currentBet: "+table.getCurrentBet());
		log.log("SetInputData, current riund: "+currentRound);
		// End log
		
	}

	private void setBankInRound(String name, int bet) {
		int roundBank =
				table.getBankInRound()+bet;
		int playerRoundBank = 
				bankInRound.get(name)+bet;
		
		log.log("setInputData, bankInRound: "+roundBank);
		
		table.setBankInRound(roundBank);
		if (bankInRound.get(name) != null)
			bankInRound.put(name, playerRoundBank);
		else
			bankInRound.put(name, bet);
		
	}

	private void setPlayerData(String name, int bet, int status) {
		log.log("setInputData- bet: "+bet+" status: "+status+" name: "+name);
		Player player = table.getPlayer(name);
		int playerScore = player.getScore()-bet;
		int playerBet = player.getBet()+bet;
		
		player.setScore(playerScore);
		player.setBet(playerBet);
		player.setStatus(status);
	}

	public void comparePlayersBets(String name) {
		boolean isNextRound = 
				readyToNextRound(name);
		log.log("CompareBets, next round = "+isNextRound);
		if(isNextRound) {
			newRound(name);
		}
	}
	
	private void newRound(String name) {
		currentRound++;
		isGameOver(name);
		resetRoundData(name);
		
	}

	private void resetRoundData(String name) {
		table.setBank(table.getBankInRound());
		table.setBankInRound(0);
		
		for(String playerName : bankInRound.keySet()) {
			bankInRound.put(playerName, 0);
			table.getPlayer(name).setBet(0);
			table.setCurrentBet(0);
		}
		
	}

	private boolean readyToNextRound(String name) {
		int playerBetInRound = bankInRound.get(name);
		if(table.getPlayers().size() != bankInRound.size())
			return false;
		
		for(String playerName : bankInRound.keySet()) {
			log.log("compareBets, player bet: "+playerBetInRound+
					"; name: "+playerName+" = "+bankInRound.get(playerName));
			if(playerBetInRound != bankInRound.get(playerName))
				return false;
			playerBetInRound = bankInRound.get(playerName);
		}
		
		return true;
		
	}

	public void isGameOver(String name) {
		Player player = table.getPlayer(name);
		int LASTROUND = 4;
		if(currentRound == LASTROUND) { // GAME OVER
			Player winer =	table.getPlayerWithBestHand();
			if(table.getPlayer(name).equals(winer)) {
				player.setScore(table.getBank());
			}
		}
	}
}
	