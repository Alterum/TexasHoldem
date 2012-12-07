package projecttu.Server;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadProcess {
	
	private DBDriver db;

	private Status status;
	private ServerProcess process;
	
	private Player player;
	
	ThreadProcess(Status observer) {
		status = observer;
	}
	
	public String isPlayerNameReserved(String name) {
		Player p;
		synchronized(status) {
			p = status.getPlayer(name);
		}
		if(p != null)
			return null;
		else
			return name;
	}

	public void prepareGameProcess(String name) {
		player = new Player(name);
		status.putPlayerAtTheTable(player);
		
		// Create BusinessProcess Object
		process = status.getProcess();
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
			status.readyToPlay();
			
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
		
		return process.get(player.getName());
	}
	
	public boolean input(OutputObject info) {
		
		process.set(info);
		return true;
	}

	public synchronized boolean removePlayerFromTheTable() {
		return status.getUpFromTheTable(player);
	}		

}
