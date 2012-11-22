package projecttu.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import projecttu.Gamelogic.PokerTable;

public class PokerGame {
	public PokerGame(ServerSocket s) throws IOException {
		new Revision(online, log);
		log.log("GameServer start: "+s);
		
		activeGameTables.put(new PokerTable(), new Observer());
		
		while(true) {
			Socket incoming = s.accept(); // blocked
			
			PokerTable table = null;
			Observer viewer = null;
			
			for(PokerTable tble : activeGameTables.keySet())
				if(!tble.isMaxPlayersForTheTable()) {
					table = tble;
					viewer = activeGameTables.get(tble);
				}
			
			
			if(table == null) {
				table = new PokerTable();
				viewer = new Observer();
				activeGameTables.put(table, viewer);	
			}
			
			viewer.start();
			
			try {
				online.addConnection(new Connection(
						incoming, log, table, viewer.getStatus()));
			} catch(IOException e) {
				log.log("Socket emergency has benn closed! "+incoming+"\r\n"+e);
				incoming.close();
			}
		}
	}
	
	private ActiveConnections online = 
			new ActiveConnections();
	private Logger log = new Logger("data.log");
	private Map<PokerTable, Observer> activeGameTables =
			new HashMap<PokerTable, Observer>();
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
						c.close();
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