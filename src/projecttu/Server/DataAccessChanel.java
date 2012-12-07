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
		
		
		System.out.println("In semaphore queue");
		try {
			available.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("After semaphore queue");
		return business.getOutputData(name);
	}

	public void put(OutputObject info) {
		business.setInputData(info);
		System.out.println("Before exit from semaphore queue");
		available.release();
		System.out.println("exit from semaphore queue");
	}
	
	
	
	
}
