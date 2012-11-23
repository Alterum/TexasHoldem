package projecttu.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
			out = new PrintWriter(new FileWriter(fileName, true), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void log(String str) {
	
		String date = getCurrentDateAndTime();
		
		System.out.println(date+str);
		out.println(date+str);
	}
	
	public String getCurrentDateAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return "[ "+dateFormat.format(cal.getTime())+" ] ";
	}
}
