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
	private DataAccessChanel chanel;
	private PokerTable table;
	private DBDriver db;
	
	ServerProcess(DBDriver db) {
		this.db = db;
		setNewTable();
	}
	
	void setAccessChanel() {
		db.setValue("cash_table", "table_name", table.toString());
		
		chanel = new DataAccessChanel(
				 new BusinessProcess(
						 table, db));
	}
	
	void setNewTable() {
		table = new PokerTable();
	}
	
	void setAmountPlayers(int x) {
		allPlayers = x;
	}
	
	synchronized void readyToPlay() {
		readyPlayers++;
		System.out.println(Thread.currentThread()+
				": in Status ready players: "+readyPlayers);
	}
	
	synchronized void readyToNewGame() {
		readyNewGame++;
	}
	
	synchronized void readyToNextRound() {
		readyToNextRound++;
		System.out.println(Thread.currentThread()+
				": in Status ready to next round: "+readyPlayers);
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
		if(readyPlayers < allPlayers)
			return false;
		
		readyPlayers = 0;
		prepareGameTable();
		setAccessChanel();
		return true;
	}
	
	private void prepareGameTable() {
		for(Player player : table.getPlayers())
			table.setActivePlayer(player);
		table.dealCardsToPlayers();
		
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

	public DataAccessChanel getAccessChanel() {
		return chanel;
	}
	
	public boolean isNewGame() {
		return false;
	}

	synchronized public Player getPlayer(String name) {
		return table.getPlayer(name);
	}

	synchronized public void putPlayerAtTheTable(Player player) {
		db.setValue("player", "name", player.getName());
		table.addPlayer(player);
	}

	synchronized public boolean getUpFromTheTable(Player player) {
		return table.removePlayer(player);
	}
}
