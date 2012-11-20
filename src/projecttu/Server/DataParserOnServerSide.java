package projecttu.Server;

import projecttu.OutputObject.OutputObject;

public class DataParserOnServerSide {
	OutputObject data;
	ThreadGate gate = new ThreadGate();
	
	public void setData(OutputObject d) {
		data = d;
		
		parserData();
		writeToDB();
		notifyObserver();
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		gate.start();
	}

	private void notifyObserver() {
		// TODO Auto-generated method stub
		
	}

	private void writeToDB() {
		// TODO Auto-generated method stub
		
	}

	private void parserData() {
		// TODO Auto-generated method stub
		
	}
}
