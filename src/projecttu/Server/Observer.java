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
		while(true) {
			if(values >= count) {
				values = 0;
				notifyAll();
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	synchronized void setValue() {
		values++;
	}
	
	void setCount(int x) {
		count = x;
	}

}
