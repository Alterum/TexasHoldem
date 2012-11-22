package projecttu.Server;

import projecttu.Gamelogic.PokerTable;

public class Status {
	private int readyPlayers = 0;
	private int readyNewGame = 0;
	private int readyToDeal = 0;
	private int allPlayers = 2; // need to change
	private PokerTable table;
	
	Status() {
		table = new PokerTable();
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
	
	void readyToDeal() {
		readyToDeal++;
	}
	
	void resetReadyPlayersToDeal() {
		readyToDeal = 0;	
	}
	
	void resetReadyPlayersToNewGame() {
		readyNewGame = 0;	
	}
	
	void resetReadyPlayers() {
		readyPlayers = 0;
	}
	
	synchronized boolean isAllReadyToDeal() {
		if(readyToDeal >= allPlayers)
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
}
