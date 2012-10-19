package projecttu.OutputObject;

import java.io.Serializable;

public class OutputObject implements Serializable {
	private String message = "Default";
	
	public OutputObject() {}

	public OutputObject(String str) {
		message = str;
	}
	
	public String toString() {
		return message;
	}
}
