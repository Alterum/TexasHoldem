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
import java.net.SocketException;


import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;
import projecttu.OutputObject.OutputToServer;


public class Connection implements Runnable {
	private String name;
	private Socket incoming;
	private BufferedReader in;
	private PrintWriter out;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Thread thread;
	private Logger log;
	private ThreadProcess server;
	
	public Connection(Socket socket, PokerTable table,
			Status status) throws IOException {
		incoming = socket;
		this.log = new Logger("connection.log");
		
		server = new ThreadProcess(table, status);
		
		log.log("Connection open. "+incoming+" "+this);
		
		setIO();
		start();
	}
	
	private void setIO() throws IOException {
		in = new BufferedReader(
				new InputStreamReader(
						incoming.getInputStream()));
		out =  new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								incoming.getOutputStream())), true);
		
		ois = new ObjectInputStream(incoming.getInputStream());
	    oos = new ObjectOutputStream(incoming.getOutputStream());
	}
	
	private void start() {
		thread = new Thread(this);
		thread.start();	
	}
	
	public void run() {
		
		getPlayerName();
		
		log.log("Connection name: "+name);
		
		thread.setName(name);
		server.prepareGameProcess(name);
		
		try {
			if(server.startGameProcess(
					in.readLine()))
				out.println("startPlay");
			else
				return;
			
			processDataExchange();
			
		} catch (IOException e) {
			try {
				incoming.close();
				log.log( "Socket emergency closed! Connection name: "+name+"\r\n"+e );
			} catch (IOException e1) {
				log.log( "Socket not closed!\r\n"+e1);
			}
		} finally {
			try {
				log.log( "Socket closed! Connection name: "+name);
				incoming.close();
			} catch(IOException e) {}
		}
	}
	
	private void getPlayerName() {
		while(true) {
			try {
				if(getInputName(server.getPlayerName(in.readLine()))){
					out.println("true");
					break;
				} else
					out.println("false");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		
	}

	private boolean getInputName(String in) {
		if(in == null) {
			log.log("InputName for "+Thread.currentThread()+" incorect");
			return false;
		}
		else {
			log.log("InputName for "+Thread.currentThread()+" corect");
			name = in;
			return true;
		}
	}
	
	private void processDataExchange() throws IOException {
		while(true) {
			sendDataToClient(
					server.output());
			
			if(!server.input(
					getDataFromClient()))
				return;
		}	
	}

	private OutputObject getDataFromClient() {
		OutputObject data = null;
		try {
			data = (OutputObject) ois.readObject();
		} catch (ClassNotFoundException e) {
			log.log("Server: ClassNotFoundException: "+e);
			e.printStackTrace();
		} catch (IOException e) {
			log.log("Server: IOException for Read Output Object: "+e);
			e.printStackTrace();
		}
		return data;
		
	}

	private void sendDataToClient(OutputObject data) {
		try { oos.writeObject(data); }
		catch (IOException e1) {
			log.log("Server: IOExceptio for Write Output Object: "+e1);
			e1.printStackTrace();
		}	
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public String getName() {
		return name;
	}

	public void close() {
		try {
			incoming.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
