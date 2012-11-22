package projecttu.Server;

import projecttu.Gamelogic.PokerTable;

public class Status {
	private int readyPlayers = 0;
	private int readyNewGame = 0;
	private int allPlayers;
	private PokerTable table;
	
	synchronized boolean ready() {
		if(readyPlayers == 2) {
			readyPlayers = 0;
			return true;
		}
		return false;
	}
	
	synchronized void done() {
		readyPlayers++;
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
	
	void setAmountPlayers(int x) {
		allPlayers = x;
	}

	void resetReadyPlayers() {
		readyPlayers = 0;
		
	}

	synchronized boolean isAllReadyToNewGame() {
		if(readyNewGame == allPlayers)
			return true;
		return false;
	}

	void resetReadyPlayersToNewGame() {
		readyNewGame = 0;
		
	}
	
	void readyToNewGame() {
		readyNewGame++;
	}
}
