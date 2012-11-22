package projecttu.Server;

import java.util.concurrent.Semaphore;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadProcess {
	
	private DataParserOnServerSide parser;
	private DBDriver db;
//	private ThreadGate gate;
	private Status viewer;
	private BusinessProcess process;
	
	private Semaphore smphr;
	
	private Player player;
	private PokerTable table;
	
	ThreadProcess(PokerTable table, Status observer) {
		this.table = table;
		viewer = observer;
		process = new BusinessProcess(table, new DBDriver());
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
		synchronized(viewer) {
			viewer.done();
			try {
				viewer.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("exit from WAIT");
	}
	
	public void getResponse(OutputObject info) {
		parser.setData(info); // wait
		
		//process.design(); // v design ras4ety i info iz DB
		//data = process.getOutputData(player.getName());
			process.setInputData(info);
		
		System.out.println("Data getResponse() after process "+Thread.currentThread());
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
	public synchronized boolean addPlayerToTheTable() {
		return table.addPlayer(player);
	}
//	public synchronized Player getPlayer(String name) {
//		return table.getPlayer(name);
//	}

	public synchronized String getPlayerName(String name) {
		Player p = table.getPlayer(name);
		if(p != null)
			return null;
		else
			return name;
	}
	
	public OutputObject getOutputData() {
		int READY_TO_START = -5;
		if(player.getStatus() == READY_TO_START)
			waitNewGame();
		//waitInQueue();
		
		System.out.println("Player name: "+player.getName());
		
		OutputObject obj = null;
		
		try{
			obj = process.getOutputData(player.getName());
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	private void waitInQueue() {
		try {
			smphr.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	private void waitNewGame() {
		while(viewer.isNewGame()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
