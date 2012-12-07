package projecttu.Server;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadProcess {
	
	private ServerProcess process;
	private DataAccessChanel data;
	private Player player;
	
	ThreadProcess(ServerProcess process) {
		this.process = process;
	}
	
	public String isPlayerNameReserved(String name) {
		Player p;
		synchronized(process) {
			p = process.getPlayer(name);
		}
		if(p != null)
			return null;
		else
			return name;
	}

	public void prepareGameProcess(String name) {
		player = new Player(name);
		process.putPlayerAtTheTable(player);
	}
	
	public boolean startGameProcess(String readLine) {
		if(!isPlayerReadyToPlay(readLine))
			return false;
		waitingForOtherPlayers();
		data = process.getAccessChanel();
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
		synchronized(process) {
			process.readyToPlay();
			
			System.out.println(Thread.currentThread()+": Before Wait waitingForOtherPlayers");
			
			try {
				process.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread()+": After Wait waitingForOtherPlayers");
		}
	}
	
	public OutputObject output() {
		
		return data.get(player.getName());
	}
	
	public boolean input(OutputObject info) {
		
		
		data.put(info);
		return true;
	}

	public boolean removePlayerFromTheTable() {
		return process.getUpFromTheTable(player);
	}		

}
