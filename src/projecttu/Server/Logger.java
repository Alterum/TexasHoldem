package projecttu.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	private PrintWriter out;
	public Logger(String fileName) {
		File path = new File(fileName); 
		try {
			path.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out = new PrintWriter(new FileWriter(fileName), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void log(String str) {
		System.out.println(str);
		out.println(str);
	}
}
