package projecttu.Server;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadProcess {
	
	private DataParserOnServerSide parser;
	private DBDriver db;
//	private ThreadGate gate;
	private BusinessProcess process;
	
	private Player player;
	private PokerTable table;
	
	ThreadProcess(PokerTable table) {
		this.table = table;
	}

	public void prepareGameProcess(String name) {
		player = new Player(name);
		addPlayerToTheTable();
		
	}
	
	public boolean startGameProcess(String readLine) {
		if(!isPlayerReadyToPlay(readLine))
			return false;
		waitingForOtherPlayers();
		return true;
	}
	
	public OutputObject getResponse(OutputObject info) {
		parser.setData(info); // wait
		
		semaphoreOn(); // ????
		
		process = new BusinessProcess(table, db);
		
		process.design(); // v design ras4ety i info iz DB
		OutputObject data = process.getOutputData(player.getName());
		
		semaphoreOff(); // ???
		
		
		return data;
	}


	private void semaphoreOff() {
		// TODO Auto-generated method stub
		
	}

	private void semaphoreOn() {
		// TODO Auto-generated method stub
		
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
		while (!isStartGame()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Observer thread sobirajuwii infu
	public boolean isStartGame() {
		return startToGame;
	}
	

	public synchronized boolean removePlayerFromTheTable() {
		return table.removePlayer(player);
	}	
	public synchronized boolean addPlayerToTheTable() {
		return table.addPlayer(player);
	}
//	public synchronized Player getPlayer(String name) {
//		return table.getPlayer(name);
//	}

	public synchronized String getPlayerName(String name) {
		return table.getPlayer(name).toString();
	}
	

}
