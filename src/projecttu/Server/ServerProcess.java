package projecttu.Server;

import java.util.ArrayList;
import java.util.List;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;

public class ServerProcess {
	private int readyPlayers = 0;
	private int readyNewGame = 0;
	private int readyToNextRound = 0;
	private int allPlayers = 2; // need to change
	private DataAccessChanel process;
	private PokerTable table;
	
	ServerProcess() {
		setNewTable();
	}
	
	void setNewTable() {
		table = new PokerTable();
	}
	
	void setAmountPlayers(int x) {
		allPlayers = x;
	}
	
	synchronized void readyToPlay() {
		readyPlayers++;
		System.out.println(Thread.currentThread()+": in Status ready players: "+readyPlayers);
	}
	
	synchronized void readyToNewGame() {
		readyNewGame++;
	}
	
	synchronized void readyToNextRound() {
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
	
	PokerTable getCurrentTable() {
		return table;
	}
	
	void newTable() {
		if(readyPlayers == allPlayers) {
			setNewTable();
			readyPlayers = 0;
		}
	}

	public DataAccessChanel getProcess() {

		 process = new DataAccessChanel(
				 new BusinessProcess(
						 table, new DBDriver()));
		
		return process;
	}
	
	public boolean isNewGame() {
		return false;
	}

	synchronized public Player getPlayer(String name) {
		return table.getPlayer(name);
	}

	synchronized public void putPlayerAtTheTable(Player player) {
		table.addPlayer(player);
	}

	synchronized public boolean getUpFromTheTable(Player player) {
		return table.removePlayer(player);
	}
}
