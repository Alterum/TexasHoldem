package projecttu.Server;

import java.util.concurrent.Semaphore;

import projecttu.OutputObject.OutputObject;

public class ServerProcess {
	
	private BusinessProcess business;
	private final Semaphore available=
			new Semaphore(1, true);
	
	ServerProcess(BusinessProcess business) {
		this.business = business;
	}
	
	public OutputObject get(String name) {
		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return business.getOutputData(name);
	}

	public void set(OutputObject info) {
		business.setInputData(info);
		available.release();
	}
	
	
	
	
}
