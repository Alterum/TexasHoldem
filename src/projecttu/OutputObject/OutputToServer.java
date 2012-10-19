package projecttu.OutputObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;


public class OutputToServer extends OutputObject implements Serializable {
	public OutputToServer() {}
	public OutputToServer(int bet, int status, String name) {
		this.bet = bet;
		this.status = status;
		this.name = name;
	}
	
	public int getBet() {
		return bet;
	}
	
	public int getPlayerStatus() {
		return status;
	}
	
	public String toString() {
		return name;
	}
	
	private String name;
	private int bet;
	private int status;
}
