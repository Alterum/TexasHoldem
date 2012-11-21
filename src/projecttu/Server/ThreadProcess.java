package projecttu.Server;

import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadProcess {
	
	private DataParserOnServerSide parser;
	private DBDriver db;
//	private ThreadGate gate;
	private BusinessProcess process;
	
	private PokerTable table;
	
	ThreadProcess(PokerTable table) {
		this.table = table;
	}

	public OutputObject getResponse(OutputObject info) {
		parser.setData(info); // wait
		
		semaphoreOn(); // ????
		
		process = new BusinessProcess(table, db, parser);
		
		process.design(); // v design ras4ety i info iz DB
		OutputObject data = process.getOutput();
		
		semaphoreOff(); // ???
		
		
		return data;
	}


	private void semaphoreOff() {
		// TODO Auto-generated method stub
		
	}

	private void semaphoreOn() {
		// TODO Auto-generated method stub
		
	}

}
