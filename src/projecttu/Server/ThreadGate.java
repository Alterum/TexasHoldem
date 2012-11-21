package projecttu.Server;

import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;

public class ThreadGate {
	BusinessProcess process;
	OutputObject data;
	ThreadGate(BusinessProcess bp) {
		process = bp;
	}
	
	public void start() {
		
		semaphoreOn(); // ????
		
		process.design(); // v design ras4ety i info iz DB
		data = process.getOutput();
		
		semaphoreOff(); // ???
		
	}
	
	private void semaphoreOff() {
		// TODO Auto-generated method stub
		
	}

	private void semaphoreOn() {
		// TODO Auto-generated method stub
		
	}

	public OutputObject getOutputData() {
		return data;
	}

}
