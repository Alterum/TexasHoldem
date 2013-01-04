package projecttu.OutputObject;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import javax.swing.JLabel;

import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.Server.BusinessProcess;
import projecttu.Server.DBDriver;

public class testParser {

	public void prepareTableForGame(PokerTable table) {
		putPlayerToTheTable(3, table);
		table.dealCardsToPlayers();
		table.dealCardsToTheTable(5);
		
	}

	
	public void putPlayerToTheTable(int j, PokerTable table) {
		for(int i=0;i<j;i++) {
			table.addPlayer(new Player("name"+i));
			table.setActivePlayer(table.getPlayer("name"+i));
		}	
	}
	
	private void createLabels(
			HashMap<String, JLabel> labels) {
		
		String[] nameLabels = {
				"message", "playerName", "playerBank", "playerStatus",
				"playerCards", "playerCards1", "playerCards2", "playerBet",
				"oppStatus1", "oppName1", "bankInRound","currentRound",
				"oppBank1", "oppCards1", "oppBet1", "oppStatus2",
				"oppName2", "oppBank2", "oppCards2", "oppBet2", "cards",
				"cards1","cards2","cards3","cards4","cards5", "bank",
				"currentBet", "round", "ring"};
		
		for(String key : nameLabels) {
			labels.put(key, new JLabel());
		}
	}
	
	@Test
	public void prepareOutputObject() {
		PokerTable table = new PokerTable();
		ClientParser parser = new ClientParser();
		
		HashMap<String, JLabel> labels =
				new HashMap<String, JLabel>();
		
		createLabels(labels);
		
		
		prepareTableForGame(table);
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
//		PlayerPanel pp = new PlayerPanel(new ServerListener("localhost", 10101));
		
//		parser.requestNewGame();
//		bp.setInputData((OutputToServer) parser.getOutputObject());
//		parser.parserInputObject(bp.getOutputData("name0"));
		
		assertNotNull(bp);
		
		
		
	}
	
}
