package projecttu.Server;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadProcess {
	
	private DBDriver db;

	private Status status;
	private BusinessProcess process;
	
	private Player player;
	private PokerTable table;
	
	ThreadProcess(PokerTable table, Status observer) {
		this.table = table;
		status = observer;
	}
	
	public synchronized String getPlayerName(String name) {
		Player p = table.getPlayer(name);
		if(p != null)
			return null;
		else
			return name;
	}

	public void prepareGameProcess(String name) {
		player = new Player(name);
		table = status.getNewTable();
		process = new BusinessProcess(table, db);
		
		System.out.println(table);
		
		synchronized(table) {
			table.addPlayer(player);
		}	
	}
	
	public boolean startGameProcess(String readLine) {
		if(!isPlayerReadyToPlay(readLine))
			return false;
		waitingForOtherPlayers();
		// table.selectSmallAndBigBlinds(); nado vynesti v status
		return true;
	}
	
	private boolean isPlayerReadyToPlay(String input) {
		if(input.equals("start")) {
			player.setStatus(1);
			return true;
		}
		else if(input.equals("observer")) {
			player.setStatus(0);
			return true;
		}
		return false;
	}
	
	private void waitingForOtherPlayers() {
		synchronized(status) {
			status.ready();
			
			System.out.println(Thread.currentThread()+": Before Wait waitingForOtherPlayers");
			
			try {
				status.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread()+": After Wait waitingForOtherPlayers");
		}
	}
	
	public OutputObject output() {
		return process.getOutputData(player.getName());
	}
	
	public boolean input(OutputObject info) {

		process.setInputData(info);
		return true;
	}

	public synchronized boolean removePlayerFromTheTable() {
		return table.removePlayer(player);
	}		

}
