package projecttu.Server;

import projecttu.OutputObject.OutputObject;
import projecttu.OutputObject.OutputToServer;

public class DataParserOnServerSide {
	OutputObject data;
//	ThreadGate gate = new ThreadGate();
	Observer controller;
	DBDriver db;
	
	public DataParserOnServerSide (Observer observer, DBDriver driver) {
		controller = observer;
		db = driver;
	}
	
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
		
	}
	
	public OutputObject getData() {
		return data;
	}
	
	private void notifyObserver() {
		// TODO Auto-generated method stub
		
	}

	private void writeToDB() {
		// TODO Auto-generated method stub
	}

	private void parserData() {
		// TODO Auto-generated method stub
		OutputToServer input = (OutputToServer) data;
		
		
		int bet = input.getBet();
		int status = input.getPlayerStatus();
		
		String name = input.toString();
		db.putInfo(bet, status, name);
	}
	

}
