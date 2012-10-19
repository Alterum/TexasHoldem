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
	private PlayHoldem play;
	private Player player;
	
	public Connection(Socket socket, Logger log,
			PlayHoldem play) throws IOException {
		incoming = socket;
		this.log = log;
		this.play = play;
		
		log.log("Connection open. "+incoming+" "+this);
		
		in = new BufferedReader(
				new InputStreamReader(
						incoming.getInputStream()));
		out =  new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								incoming.getOutputStream())), true);
		
		ois = new ObjectInputStream(incoming.getInputStream());
	    oos = new ObjectOutputStream(incoming.getOutputStream());
		
		thread = new Thread(this);
		thread.start();
	}
		
	public void run() {
		try {
//			out.println("Welcome to PokerGame");
			getInputName();
			
			log.log("Connection name: "+name);
			thread.setName(name);
			player = new Player(name);
			play.addPlayerToTheTable(player);
			
			while(true) {
				String s = in.readLine(); // Blocked, waiting request on start Game 
	
				if(s.equals("start"))
					player.setStatus(1);
				else if(s.equals("observer"))
					player.setStatus(0);
				else if(s.equals("exit")) {
					play.removePlayerFromTheTable(player);
					return;
				}
				
				while (!play.isStartGame()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				out.println("startPlay");
				
				while(true) {
					OutputObject outObj = play.getOutputData(name);
					oos.writeObject(outObj);
					
					try {
						OutputObject inObj = (OutputObject) ois.readObject();
						play.setInputData(inObj);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
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
	
	private void getInputName() {
		while(true) {
			String nm = null;
			try {
				nm = in.readLine();  // Blocked
			} catch (IOException e) {
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
		play.removePlayerFromTheTable(
				play.getPlayer(name));
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public String getName() {
		return name;
	}
	
	private synchronized String setName(String str) throws IOException {
		if(play.getPlayer(str) != null && !str.equals(""))
			return null;
		else
			return str;
	}
}
