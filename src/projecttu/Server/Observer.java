package projecttu.Server;

import java.sql.SQLException;

public class Observer implements Runnable {
	private ServerProcess status;
	
	Observer(DBDriver db) {
		try {
			db.connectToDB();
			status = new ServerProcess(db);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		waitAllPlayers();
	}
	
	private void waitAllPlayers() {
		while(true) {
			try {
				Thread.sleep(300);
				synchronized(status) {
					if(status.ready()) {
						status.notifyAll();
						status.resetReadyPlayers();
					}
					if(status.isAllReadyToNewGame()){
						status.notifyAll();
						status.resetReadyPlayersToNewGame();
					}
					if(status.isAllReadyToNextRound()){
						status.notifyAll();
						status.ressetAllReadyToNextRound();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	ServerProcess getStatus() {
		return status;
	}

	public void newGame() {
		status.newTable();	
	}
	
	public boolean isMaxConnInCurTable() {
		return status.getCurrentTable().isMaxPlayersForTheTable();
	}

}
