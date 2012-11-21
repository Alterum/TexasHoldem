package projecttu.Server;

public class Observer implements Runnable {
	private int values;
	private int count;
	private Status status;
	
	Observer() {
		status = new Status();
		values = 0;
	}
	
	void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		waitAllPlayers();
		waitHarvestAllInfo();
	}
	
	private void waitAllPlayers() {
		while(true) {
			try {
				Thread.sleep(300);
				synchronized(status) {
					if(status.ready()) {
						status.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void waitHarvestAllInfo() {
		while(true) {
			synchronized(status) {
				if(status.ready()) {
					status.notifyAll();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	synchronized void done() {
		values++;
	}
	
	synchronized void setCountConnections(int x) {
		count = x;
	}
	
	Status getStatus() {
		return status;
	}

}
