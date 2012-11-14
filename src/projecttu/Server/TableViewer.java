package projecttu.Server;

import java.util.ArrayList;

import projecttu.Gamelogic.Player;

public class TableViewer implements Runnable {
	public TableViewer(PlayHoldem play) {
		table = play;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(!table.isStartGame())
				checkStartGame();
			checkNewGame();
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			checkNewGame();
		}
	}
	
	public void checkStartGame() {
		ArrayList<Player> players = table.getPlayers();
		boolean flag = true;
		for(Player player : players)
			if(player.getStatus() < 0)
				flag = false;
		if(flag && players.size() > 1) {
			table.setStart(true);
			table.startGame();
//			notifyAll();
		}
//		table.setReady(false);	
	}
	
	// ?????????????
	public void checkNewGame() {
		ArrayList<Player> players = table.getPlayers();
		boolean flag = true;
		for(Player player : players)
			if(player.getStatus() != -5)
				flag = false;
		if(flag && players.size() > 1) {
			table.startGame();
//			notifyAll();
		}
		
//		table.setReady(false);	
	}

	private PlayHoldem table;
}
