package projecttu.Server;

import java.util.concurrent.Semaphore;

import projecttu.OutputObject.OutputObject;

public class DataAccessChanel {
	
	private BusinessProcess business;
	private final Semaphore available=
			new Semaphore(1, true);
	
	DataAccessChanel(BusinessProcess business) {
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

	public void put(OutputObject info) {
		business.setInputData(info);
		available.release();
	}
	
	
	
	
}
