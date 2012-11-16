package projecttu.Server;

import java.util.ArrayList;

import projecttu.Gamelogic.Player;

public class BusinessKeeper implements Runnable {
	public BusinessKeeper(BusinessProcess play) {
		game = play;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(!game.isStartGame())
				checkStartGame();
			checkNewGame();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			checkNewGame();
		}
	}
	
	public void checkStartGame() {
		ArrayList<Player> players = game.getPlayers();
		boolean flag = true;
		for(Player player : players)
			if(player.getStatus() < 0)
				flag = false;
		if(flag && players.size() > 1) {
			game.setStart(true);
			game.startGame();
//			notifyAll();
		}
//		table.setReady(false);	
	}
	
	// ?????????????
	public void checkNewGame() {
		ArrayList<Player> players = game.getPlayers();
		boolean flag = true;
		for(Player player : players)
			if(player.getStatus() != -5)
				flag = false;
		if(flag && players.size() > 1) {
			game.startGame();
//			notifyAll();
		}
		
//		table.setReady(false);	
	}

	private BusinessProcess game;
}
