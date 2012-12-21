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
	private PokerTable table;
	private OutputDataHarvester harvester;
	private DBDriver db;
	private Logger log;
	private final int NEWGAME = -1;
	private final int OBSERVER = 0;
	private final int LASTROUND = 4;
	
	public BusinessProcess(PokerTable table, DBDriver driver) {
		db = driver;
		this.table = table;
		
		for(Player player : table.getPlayers())
			bankInRound.put(player.getName(), 0);
		
		log = new Logger("bussines_process.log");
		
		harvester = new OutputDataHarvester(table, log);
	}
	
	public OutputObject getOutputData(String name) {
		return harvester.getData(name, currentRound);
	}

	public boolean setInputData(OutputObject obj) {
		
		OutputToServer input = (OutputToServer) obj;
		
		log.log("SetInputData - name: "+input.toString());
		
		if(!roundGameProcess(
				input.toString(), 
				input.getBet(), 
				input.getPlayerStatus()))
			return false;
		
		return true;
	}
	
	private boolean roundGameProcess(String name, int bet, int status) {
		
		if(status == NEWGAME) {
//			table.getPlayer(name).setStatus(1);
			return false;
		}
		
		/**
		 * OBSERVER
		 * check Active player: esli ostalsja tolk oodin togda on stanovitsja pobeditelem
		 * 
		 */
		if(status == OBSERVER) {
			log.log("OBSERVER");
			if(table.getActivePlayer(name) != null) {
				table.getActivePlayer(name).setStatus(OBSERVER);
				table.removeActivePlayer(table.getActivePlayer(name));
				table.setBankInRound(table.getBankInRound()+bankInRound.get(name));
				bankInRound.remove(name);
			}
			if(table.getActivePlayers().size() == 1) {
				table.getActivePlayers().get(0).setScore(table.getBank());
				currentRound = LASTROUND;
				table.getPlayer(name).setStatus(NEWGAME);
			}
			
//			return true;
		}
		
		setPlayerData(name, bet, status);
		setBankInRound(name, bet);
		comparePlayersBets(name);
		setTableCurrentBet(name, bet);
		
		return true;
		
	}

	private void setTableCurrentBet(String name, int bet) {
		// Start log
		for(String playerName : bankInRound.keySet())
			log.log("setInputData: before setCurentBet: "+playerName+
					" sum bet in round: "+bankInRound.get(playerName));
		// End log
		
		// Write to DB
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
		
		// Write to DB
		table.setBankInRound(roundBank);
//		table.addToTheBank(roundBank);
		
		bankInRound.put(name, playerRoundBank);
	}

	private void setPlayerData(String name, int bet, int status) {
		log.log("setInputData- bet: "+bet+" status: "+status+" name: "+name);
		Player player = table.getPlayer(name);
		int playerScore = player.getScore()-bet;
		int playerBet = player.getBet()+bet;
		
		// write to DB
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
		setTableBank();
		isGameOver(name);
		resetRoundData(name);
	}

	private void setTableBank() {
		int bank = 0;
		for(String player : bankInRound.keySet())
			bank+=bankInRound.get(player);
		table.setBank(table.getBank()+bank);
	}

	private void resetRoundData(String name) {
		
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
//			log.log("HAPPY END winer is: "+
//					winer.getName()+", many: "+winer.getScore());
		}
	}
}
	