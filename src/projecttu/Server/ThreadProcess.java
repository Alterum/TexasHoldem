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
		
		synchronized(table) {
			table.addPlayer(player);
		}
		
	}
	
	public boolean startGameProcess(String readLine) {
		if(!isPlayerReadyToPlay(readLine))
			return false;
		waitingForOtherPlayers();
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
			try {
				status.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean getResponse(OutputObject info) {
		
		semaphoreOn(); // ????
		
		process = new BusinessProcess(table, db);
		
		process.design(); // v design ras4ety i info iz DB
		OutputObject data = process.getOutputData(player.getName());
		
		semaphoreOff(); // ???
		
		
		return true;
	}


	private void semaphoreOff() {
		// TODO Auto-generated method stub
		
	}

	private void semaphoreOn() {
		// TODO Auto-generated method stub
		
	}

	public synchronized boolean removePlayerFromTheTable() {
		return table.removePlayer(player);
	}	
//	public synchronized Player getPlayer(String name) {
//		return table.getPlayer(name);
//	}

	public OutputObject getOuptutData() {
		return process.getOutputData(player.getName());
	}
	

}
