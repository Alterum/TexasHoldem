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
	private BusinessProcess game;
	private Player player;
	
	public Connection(Socket socket, Logger log,
			BusinessProcess play) throws IOException {
		incoming = socket;
		this.log = log;
		this.game = play;
		
		log.log("Connection open. "+incoming+" "+this);
		
		setIO();
		start();
	}

	public void run() {
			getInputName();
			
			log.log("Connection name: "+name);
			thread.setName(name);
			player = new Player(name);
			game.addPlayerToTheTable(player);
			
			gameProcess();
	}
	
	private void start() {
		thread = new Thread(this);
		thread.start();	
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
	
	private void gameProcess() {
		try {
			while(true) {
				if(!isPlayerReadyToPlay(in.readLine()))
					return;			
				waitingForOtherPlayers();
				processDataExchange();
			}
		} 
		catch (IOException e) {
			try {
				incoming.close();
				log.log( "Socket emergency closed! Connection name: "+name+"\r\n"+e );
			} catch (IOException e1) {
				log.log( "Socket not closed!\r\n"+e1);
			}
		}
		finally {
			try {
				log.log( "Socket closed! Connection name: "+name);
				incoming.close();
			} catch(IOException e) {}
		}
		
	}

	private boolean isPlayerReadyToPlay(String input) {
		if(input.equals("start")) {
			player.setStatus(1);
			return true;
		}
		else if(input.equals("observer")) {
			player.setStatus(0);
			return true;
		}
		return false;
	}
	
	private void processDataExchange() throws IOException {
		while(true) {
			sendDataToClient();
			prepareOutputData(getDataFromClient());
		}
		
	}

	private void prepareOutputData(OutputObject data) {
		game.setInputData(data);
		
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
			try {
				incoming.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return data;
		
	}

	private void sendDataToClient() {
		OutputObject data = game.getOutputData(name);
		try { oos.writeObject(data); }
		catch (IOException e1) {
			log.log("Server: IOExceptio for Write Output Object: "+e1);
			e1.printStackTrace();
		}
		
	}

	private void waitingForOtherPlayers() {
		while (!game.isStartGame()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		out.println("startPlay");
	}
	
	private void getInputName() {
		while(true) {
			String nm = null;
			try {
				nm = in.readLine();  // Blocked
			} catch (IOException e) {
				log.log("Server: not get name "+nm);
				e.printStackTrace();
			}
			System.out.println(nm);
			try {
				if(setName(nm) == null) {
					System.out.println("incorect");
					out.println("false");
					continue;
				}
				else {
					System.out.println("correct");
					out.println("true");
					name = nm;
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void removePlayerFromTable() {
		game.removePlayerFromTheTable(
				game.getPlayer(name));
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public String getName() {
		return name;
	}
	
	private synchronized String setName(String str) throws IOException {
		if(game.getPlayer(str) != null && !str.equals(""))
			return null;
		else
			return str;
	}
}
