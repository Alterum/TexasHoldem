package projecttu.Server;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;

public class Statistics {
	private DBDriver db;
	private PokerTable table;
	private int currentRound;
	
	public Statistics(DBDriver db, PokerTable table) {
		this.db = db;
		this.table = table;
	}
	
	public void writeData() {
		if(currentRound==1) {
			for(Player player : table.getPlayers()) {
				String sql1 = "select id from player where name='"+player.getName()+"'";
				String sql2 = "select id from cash_table where name='"+table.toString()+"'";
			}
		}
	}

}
