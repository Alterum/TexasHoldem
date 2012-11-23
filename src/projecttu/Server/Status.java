package projecttu.Server;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;

public class Status {
	private int readyPlayers = 0;
	private int readyNewGame = 0;
	private int readyToNextRound = 0;
	private int allPlayers = 2; // need to change
	private ServerProcess process;
	private PokerTable table;
	
	Status() {
		table = new PokerTable();
		process = new ServerProcess(
				new BusinessProcess(table, new DBDriver()));
	}
	
	void setAmountPlayers(int x) {
		allPlayers = x;
	}
	
	void readyToPlay() {
		readyPlayers++;
		System.out.println(Thread.currentThread()+": in Status ready players: "+readyPlayers);
	}
	
	void readyToNewGame() {
		readyNewGame++;
	}
	
	void readyToNextRound() {
		readyToNextRound++;
		System.out.println(Thread.currentThread()+": in Status ready to next round: "+readyPlayers);
	}
	
	void ressetAllReadyToNextRound() {
		readyToNextRound = 0;	
	}
	
	void resetReadyPlayersToNewGame() {
		readyNewGame = 0;	
	}
	
	void resetReadyPlayers() {
		readyPlayers = 0;
	}
	
	synchronized boolean isAllReadyToNextRound() {
		if(readyToNextRound >= allPlayers)
			return true;
		return false;
	}
	
	synchronized boolean isAllReadyToNewGame() {
		if(readyNewGame >= allPlayers)
			return true;
		return false;
	}
	
	synchronized boolean ready() {
		if(readyPlayers >= allPlayers) {
			readyPlayers = 0;
			
			for(Player player : table.getPlayers())
				table.setActivePlayer(player);
			table.dealCardsToPlayers();
			
			return true;
		}
		return false;
	}
	
	PokerTable getNewTable() {
		return table;
	}
	
	void newTable() {
		if(readyPlayers == allPlayers) {
			table = new PokerTable();
			readyPlayers = 0;
		}
	}

	public ServerProcess getProcess() {
		return process;
	}
	
	public boolean isNewGame() {
		// TODO Auto-generated method stub
		return false;
	}
}
