package projecttu.OutputObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;

public class OutputToClient extends OutputObject implements Serializable {
	
	private String message = "Default";
	private HashMap<String, String> info;
	private HashMap<String, Boolean> access;
	
	public OutputToClient() {}
	
	public OutputToClient(HashMap<String, String> map) {
		info = map;
	}

	public OutputToClient(String str) {
		message = str;
	}
	
	public OutputToClient(HashMap<String, String> map1,
			HashMap<String, Boolean> map2) {
		info = map1;
		access = map2;
	}

	public HashMap<String, String> getInfo() {
		return info;
	}
	
	public HashMap<String, Boolean> getAccess() {
		return access;
	}
	
	public String toString() {
		return message;
	}
}
