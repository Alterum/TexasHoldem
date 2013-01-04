package projecttu.OutputObject;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.Server.BusinessProcess;
import projecttu.Server.DBDriver;
import projecttu.Server.OutputDataHarvester;

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
	
	public HashMap<String, JButton> creatButtons() {
		HashMap<String, JButton> menuButtons = 
				new HashMap<String, JButton>();
		String[] menuBttnsNames =
			{"play", "call", "raise", "fold", "check", "newGame"};
		for(String key : menuBttnsNames) {
			menuButtons.put(key, new JButton(key));
		}
		return menuButtons;
	}
	
	public HashMap<String, JLabel> createLabels() {
		HashMap<String, JLabel> labels =
				new HashMap<String, JLabel>();
		
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
		return labels;
	}
	
	@Test
	public void prepareOutputObject() {
		PokerTable table = new PokerTable();
		ClientParser parser = new ClientParser();
		
		HashMap<String, JButton> menuButtons =
				creatButtons();
		
		HashMap<String, JLabel> labels = 
				createLabels();
		
		parser.setChangingComponents(labels, menuButtons);
		
		parser.requestNewGame();
		parser.requestRaise(100);
		parser.requestFold();
		
		parser.isRequest();
		parser.isNewGame();
		
		prepareTableForGame(table);
		BusinessProcess bp = new BusinessProcess(table, null);
		assertNotNull(bp.getOutputData("name0"));

		
		assertNotNull(bp);
		
		
		
	}
	
}
