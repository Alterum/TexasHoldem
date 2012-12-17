package projecttu.Server;

import java.util.HashMap;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.OutputObject;
import projecttu.OutputObject.OutputToClient;

public class OutputDataHarvester {
	private PokerTable table;
	private Logger log;
	
	public OutputDataHarvester(PokerTable table, Logger log) {
		this.table = table;
		this.log = log;
	}

	public OutputObject getData(String name, int round) {
		HashMap<String, Boolean> buttons =
				convertAccessButtons(name);
		HashMap<String, String> info =
				convertInfo(name, round);
		
		return (new OutputToClient(info, buttons));
		
	}
	
	public HashMap<String, Boolean> convertAccessButtons(String name) {
		String[] bttnsNames =
			{"play", "call", "raise", "fold", "check", "newGame"};
		
		HashMap<String, Boolean> map = 
				new HashMap<String, Boolean>();
		
		for(String key : bttnsNames)
			map.put(key, false);
		
		int OBSERVER = 0;
		int NEWGAME = -1;
		
		int playerStatus = table.getPlayer(name).getStatus();
		
		if(playerStatus == OBSERVER) // If push button fold
			return map;
		else if (playerStatus == NEWGAME) {
			map.put("newGame", true);
			return map;
		}
		map.put("fold", true);
		
		int playerScore = table.getPlayer(name).getScore();
		int currentBet = table.getCurrentBet();
		
		log.log("score: "+playerScore+" bet:"+currentBet);
		
		// 
		if(playerScore > currentBet)
			map.put("raise", true);
		
		if(currentBet == 0)
			map.put("check", true);
		else if(playerScore >= currentBet)
			map.put("call", true);
		
		return map; 
	}
	
	public HashMap<String, String> convertInfo(
			String name, int round) {
		
		HashMap<String, String> map =
				new HashMap<String, String>();
		setPlayerInfo(name, map);
		setOpponentsInfo(name, map);
		setTableInfo(map, round);
		
		return map;
	}
	
	private void setTableInfo(HashMap<String, String> map, int currentRound) {
		String cards="";
		if(currentRound == 1){ // 3 cards visible
			cards += "BB BB "+table.getCardsOnTable()[2] + " "+
					table.getCardsOnTable()[1] + " " +
					table.getCardsOnTable()[0];
		} else if(currentRound == 2){ // 4 cards visible
			cards += "BB "+table.getCardsOnTable()[3] + " "+
					table.getCardsOnTable()[2] + " " +
					table.getCardsOnTable()[1] + " " +
					table.getCardsOnTable()[0];
		} else if(currentRound == 3) { // 5 cards visible	
			for(String card : table.getCardsOnTable())
				cards+=card+" ";
		} else // All cards invisible
			cards += "BB BB BB BB BB";
		map.put("cards", cards);
		map.put("currentBet", Integer.toString(table.getCurrentBet()));
		map.put("bank", Integer.toString(table.getBank()));
		map.put("bankInRound", Integer.toString(table.getBankInRound()));
		map.put("currentRound", Integer.toString(currentRound));
		
	}

	private void setPlayerInfo(String name, HashMap<String, String> map) {
		Player player = table.getPlayer(name);
		map.put("playerName", name);
		map.put("playerCards", getPlayerCards(name));
		map.put("playerStatus", 
				Integer.toString(
						player.getStatus()));
		map.put("playerBank", 
				Integer.toString(
						player.getScore()));
		map.put("playerBet", 
				Integer.toString(
						player.getBet()));
	}
	
	private String getPlayerCards(String name) {
		String playerCards = "";
		for(String card : table.getPlayer(name).getHand())
			playerCards += card+" ";
		return playerCards;
	}

	private void setOpponentsInfo(String name, HashMap<String, String> map) {
		int index = 1;
		for(Player player : table.getPlayers()) {
			if(player.getName() == name)
				continue;
			map.put("oppName"+index, player.getName());
			map.put("oppCards"+index, player.getHand()[0]+" "+player.getHand()[1]);
			map.put("oppBank"+index, Integer.toString(player.getScore()));
			map.put("oppBet"+index, Integer.toString(player.getBet()));
			index++;
		}
		
	}
	
}
