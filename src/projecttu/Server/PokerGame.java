package projecttu.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import projecttu.Gamelogic.PokerTable;

public class PokerGame {
	public PokerGame(ServerSocket s) throws IOException {
		new Revision(online, log);
		log.log("GameServer start: "+s);
		
		PokerTable t = new PokerTable();
		PlayHoldem plh = new PlayHoldem(t);
		new GameViewer(plh);
		activeGameTables.put(t, plh);
		
		while(true) {
			Socket incoming = s.accept(); // blocked
			
			PokerTable table = null;
			PlayHoldem play = null;
			
			for(PokerTable tble : activeGameTables.keySet())
				if(!tble.isMaxPlayersForTheTable()) {
					table = tble;
					play = activeGameTables.get(tble);
				}
					
			if(table == null) {
				table = new PokerTable();
				play = new PlayHoldem(table);
				new GameViewer(play);
				activeGameTables.put(table, play);	
			}
			
			try {
				online.addConnection(new Connection(
						incoming, log, play));
			} catch(IOException e) {
				log.log("Socket emergency has benn closed! "+incoming+"\r\n"+e);
				incoming.close();
			}
		}
	}
	
	private ActiveConnections online = 
			new ActiveConnections();
	private Logger log = new Logger("data.log");
	private Map<PokerTable, PlayHoldem> activeGameTables =
			new HashMap<PokerTable, PlayHoldem>();
}

class Revision extends Thread {
	public Revision(ActiveConnections online, Logger log) {
		this.online = online;
		this.log = log;
		start();
	}
	
	public void run() {
		while(true) {
			synchronized(online) {
				Iterator<Connection> itr = online.iterator();
				while( itr.hasNext() ) {
					Connection c = itr.next();
					if( !c.getThread().isAlive() ) {
						log.log(c.getThread()+" is dead and was removed from Active connections");
						c.removePlayerFromTable();
						itr.remove();
					}
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					log.log("Revision: " + e.toString());
					e.printStackTrace();
				}
			}
		}
	}
	
	private Logger log;
	private ActiveConnections online;
}