package projecttu.Server;

public class Observer implements Runnable {
	int values;
	int count;
	
	
	Observer() {
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
				Thread.sleep(100);
				if(values >= 2) {
					Thread.sleep(60000);
					count = values;
					values = 0;
					notifyAll();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void waitHarvestAllInfo() {
		while(true) {
			if(values >= count) {
				values = 0;
				notifyAll();
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

}
