package projecttu.Server;

public class Status {
	private int count = 0;
	
	public synchronized boolean ready() {
		if(count == 2) {
			count = 0;
			return true;
		}
		return false;
	}
	
	public synchronized void done() {
		count++;
	}

	public boolean isNewGame() {
		// TODO Auto-generated method stub
		return false;
	}

}
