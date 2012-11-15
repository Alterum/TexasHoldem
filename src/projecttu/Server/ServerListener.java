package projecttu.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import projecttu.OutputObject.ClientParser;
import projecttu.OutputObject.OutputObject;
import projecttu.OutputObject.OutputToClient;
import projecttu.OutputObject.OutputToServer;
import projecttu.OutputObject.ServerParser;



public class ServerListener implements Runnable{
	public ServerListener(String addr, int port) {
		
		connectToServer(addr, port);
		getIO();
		parser = new ClientParser();
	}
	
	public void startListen() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		waitServerResponseForStartGame();
		dataExchangeWithServer();
	}
	
	private void dataExchangeWithServer() {
		while(true) {
			waitDataFromServer();
			
			prepareOutput();
			
			sendDataToServer();
		}
		
	}

	private void prepareOutput() {
		while(true) {
			if(parser.isRequest())
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		System.out.println("after: "+parser.isRequest());	
	}

	private void sendDataToServer() {
		try {
			oos.writeObject(parser.getOutputObject());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void waitDataFromServer() {
		try {
			OutputObject obj = (OutputObject) ois.readObject(); // blocked
			System.out.println(obj);
			parser.parserInputObject(obj);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void waitServerResponseForStartGame() {
		try {
			while(true) {
				String str = in.readLine();
				if(str.equals("startPlay"))
					break;
			}
			
			System.out.println("Server access on");
			
			try { Thread.sleep(200); }
			catch (InterruptedException e) { e.printStackTrace(); }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void connectToServer(String addr, int port) {
		try {
			socket = new Socket(addr, port);
		} catch( IOException e ) {
			System.out.println("Sry - Server not response: "+e);
			return;
		}	
	}
	
	public void getIO() {
		try {
			System.out.println("socket = " + socket);
			
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			out =  new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream())), true);
			
			oos = new ObjectOutputStream(socket.getOutputStream());
	        ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void send(OutputToServer out) {
//		try {
//			for(String key : out.getActions().keySet())
//				System.out.println(out+" "+key+" "+out.getActions(key));
//			System.out.println(out+" "+out.hashCode());
//			oos.writeObject(out);
//			oos.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//	}
	public void send(String str) {
		System.out.println(str);
//			oos.writeChars(out);
//			oos.flush();
		out.println(str);	
	}
	
	public void send(int flag) {
		System.out.println(flag);
		out.print(flag);
	}
	
	
	public OutputToClient getObject() {
		try {
			return (OutputToClient) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getString() {
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getBoolean() {
		try {
			return ois.read();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getInt() {
		try {
//			return ois.readBoolean();
			return in.read();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientParser getParser() {
		return parser;
	}
	
//	public OutputToClient get() {
//		return input;
//	}
	
//	public void set(OutputToClient out) {
//		input = out;
//	}
	
//	private OutputToClient input = null;
//	private Thread thread;
	private BufferedReader in;
	private PrintWriter out;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
//	private OutputToServer output = null;
	private ClientParser parser;
}
