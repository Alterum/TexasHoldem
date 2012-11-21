package projecttu.Server;

public class Observer implements Runnable {
	int values;
	int count;
	
	
	Observer() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		waitAllPlayers();
		waitHarvestAllInfo();
	}
	
	private void waitAllPlayers() {
		try {
			Thread.sleep(100);
			if(values >= 2) {
				values = 0;
				Thread.sleep(60000);
				notifyAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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
