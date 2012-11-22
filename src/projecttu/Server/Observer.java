package projecttu.Server;

public class Observer implements Runnable {
	private Status status;
	
	Observer() {
		status = new Status();
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
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	Status getStatus() {
		return status;
	}

}
