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
	
	public Connection(Socket socket, Logger log,
			PokerTable play, Status observer) throws IOException {
		incoming = socket;
		this.log = log;
//		this.game = play;
		
		server = new ThreadProcess(play, observer);
		
		log.log("Connection open. "+incoming+" "+this);
		
		setIO();
		start();
	}
	
	private void start() {
		thread = new Thread(this);
		thread.start();	
	}
	
	public void run() {
		while(true)
			try {
				if(getInputName(server.getPlayerName(in.readLine())))
					break;
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		
		log.log("Connection name: "+name);
		thread.setName(name);
//			player = new Player(name);
//			game.addPlayerToTheTable(player);
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
	
	private boolean getInputName(String in) {
		if(in == null) {
			System.out.println("incorect");
			out.println("false");
			return false;
		}
		else {
			System.out.println("correct");
			out.println("true");
			name = in;
			return true;
		}
	}
	
//	private void gameProcess() {
//		try {
//			while(true) {
//				if(!isPlayerReadyToPlay(in.readLine()))
//					return;			
//				waitingForOtherPlayers();
//				processDataExchange();
//			}
//		} 
//		catch (IOException e) {
//			try {
//				incoming.close();
//				log.log( "Socket emergency closed! Connection name: "+name+"\r\n"+e );
//			} catch (IOException e1) {
//				log.log( "Socket not closed!\r\n"+e1);
//			}
//		}
//		finally {
//			try {
//				log.log( "Socket closed! Connection name: "+name);
//				incoming.close();
//			} catch(IOException e) {}
//		}
//		
//	}

//	private boolean isPlayerReadyToPlay(String input) {
//		if(input.equals("start")) {
//			player.setStatus(1);
//			return true;
//		}
//		else if(input.equals("observer")) {
//			player.setStatus(0);
//			return true;
//		}
//		return false;
//	}
	
	private void processDataExchange() throws IOException {
		while(true) {
			sendDataToClient(
					server.getOuptutData());
			
			if(!server.getResponse(
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
//		OutputObject data = game.getOutputData(name);
		try { oos.writeObject(data); }
		catch (IOException e1) {
			log.log("Server: IOExceptio for Write Output Object: "+e1);
			e1.printStackTrace();
		}
		
	}

//	private void waitingForOtherPlayers() {
//		while (!game.isStartGame()) {
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		out.println("startPlay");
//	}
	
	

//	public void removePlayerFromTable() {
//		game.removePlayerFromTheTable(
//				game.getPlayer(name));
//	}
	
	public Thread getThread() {
		return thread;
	}
	
	public String getName() {
		return name;
	}

	public void close() {
		// TODO delete Player from the table
		
		try {
			incoming.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
