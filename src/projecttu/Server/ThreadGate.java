package projecttu.Server;

import projecttu.Gamelogic.PokerTable;

public class ThreadGate {
	BusinessProcess process;
	ConstructOutData constructor =
			new ConstructOutData();
	Connection connection;
	
	ThreadGate() {
		process = new BusinessProcess(
				new PokerTable());
	}
	
	public void start() {
		
		semaphoreOn(); // ????
		
		process.design(); // v design ras4ety i info iz DB
		
		semaphoreOff(); // ???
		
		connection.setOutput(
				constructor.newOutData());
		
		
		
	}
	
	void setConnection(Connection con) {
		connection = con;
	}

}
