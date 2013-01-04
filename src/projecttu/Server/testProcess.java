package projecttu.Server;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import javax.swing.JLabel;

import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.OutputObject.ClientParser;
import projecttu.OutputObject.OutputObject;
import projecttu.OutputObject.OutputToServer;

public class testProcess {
	PokerTable table = new PokerTable();
	
	public void prepareTableForGame(PokerTable table, int x) {
		putPlayerToTheTable(x, table);
		dealCards(table);
		
	}
	
	public void setPlayerStatus(PokerTable table, int x, String name) {
		table.getActivePlayer(name).setStatus(x);
	}
	
	public void dealCards(PokerTable table) {
		table.dealCardsToPlayers();
		table.dealCardsToTheTable(5);
	}

	
	public void putPlayerToTheTable(int j, PokerTable table) {
		for(int i=0;i<j;i++) {
			table.addPlayer(new Player("name"+i));
			table.setActivePlayer(table.getPlayer("name"+i));
		}	
	}
	
//	private void createLabels(
//			HashMap<String, JLabel> labels) {
//		
//		String[] nameLabels = {
//				"message", "playerName", "playerBank", "playerStatus",
//				"playerCards", "playerCards1", "playerCards2", "playerBet",
//				"oppStatus1", "oppName1", "bankInRound","currentRound",
//				"oppBank1", "oppCards1", "oppBet1", "oppStatus2",
//				"oppName2", "oppBank2", "oppCards2", "oppBet2", "cards",
//				"cards1","cards2","cards3","cards4","cards5", "bank",
//				"currentBet", "round", "ring"};
//		
//		for(String key : nameLabels) {
//			labels.put(key, new JLabel());
//		}
//	}
	
//	@Test
//	public void prepareOutputObject() {
//		PokerTable table = new PokerTable();
//		ClientParser parser = new ClientParser();
//		
//		HashMap<String, JLabel> labels =
//				new HashMap<String, JLabel>();
//		
//		createLabels(labels);
//		
//		
//		prepareTableForGame(table);
//		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
//		
////		PlayerPanel pp = new PlayerPanel(new ServerListener("localhost", 10101));
//		
////		parser.requestNewGame();
////		bp.setInputData((OutputToServer) parser.getOutputObject());
////		parser.parserInputObject(bp.getOutputData("name0"));
//		
//		assertNotNull(bp);
//		
//		
//		
//	}
	
	@Test
	public void OtpuToClientIfPlayerStatusIsNewGame() {
		OutputDataHarvester dh = new OutputDataHarvester(table, new Logger("testlog"));		
		
		prepareTableForGame(table, 2);
		
		assertNotNull(dh.getData("name0", 1));	
		
	}
	
	@Test
	public void OtpuToClientIfPlayerStatusIsObserver() {
		
		OutputDataHarvester dh = new OutputDataHarvester(table, new Logger("testlog"));		
		
		prepareTableForGame(table, 2);
		setPlayerStatus(table, 0, "name0");
		
		assertNotNull(dh.getData("name0", 2));	
		
	}
	
	@Test
	public void OtpuToClientIfPlayerStatusIsToPlay() {
		OutputDataHarvester dh = new OutputDataHarvester(table, new Logger("testlog"));		
		
		prepareTableForGame(table, 3);
		setPlayerStatus(table, 1, "name0");
		
		assertNotNull(dh.getData("name0", 3));	
		
	}
	
	@Test
	public void OtpuToClientIfRound0() {
		OutputDataHarvester dh = new OutputDataHarvester(table, new Logger("testlog"));		
		
		prepareTableForGame(table, 3);
		
		assertNotNull(dh.getData("name0", 0));	
		
	}
	
	@Test
	public void OtpuToClientIfRound4() {
		OutputDataHarvester dh = new OutputDataHarvester(table, new Logger("testlog"));		
		
		prepareTableForGame(table, 3);
		
		assertNotNull(dh.getData("name0", 4));	
		
	}
	
	@Test
	public void OtpuToServerBProcessIfPlayerStatusToPlay() {
		
		prepareTableForGame(table, 3);
		OutputObject os = new OutputToServer(100, 1, "name0");
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		assertTrue(bp.setInputData(os));	
		
	}
	
	@Test
	public void OtpuToServerBProcessIfPlayerStatusNewGame() {
		
		prepareTableForGame(table, 3);
		OutputObject os = new OutputToServer(100, -5, "name0");
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		assertFalse(bp.setInputData(os));	
		
	}
	
	@Test
	public void OtpuToServerBProcessIfPlayerStatusObserver() {
		
		prepareTableForGame(table, 3);
		OutputObject os = new OutputToServer(100, 0, "name0");
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		assertTrue(bp.setInputData(os));	
		
	}
	
	@Test
	public void BProcessIfPlayerStatusObserverAndCountPlayersOne() {
		
		prepareTableForGame(table, 1);
		OutputObject os = new OutputToServer(100, 0, "name0");
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		assertTrue(bp.setInputData(os));	
		
	}
	
	@Test
	public void BProcessIfPlayersCompareBets() {
		
		prepareTableForGame(table, 3);
		OutputObject os1 = new OutputToServer(100, 1, "name0");
		OutputObject os2 = new OutputToServer(100, 1, "name1");
		OutputObject os3 = new OutputToServer(100, 1, "name2");
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		assertTrue(bp.setInputData(os1));
		assertTrue(bp.setInputData(os2));
		assertTrue(bp.setInputData(os3));
		
	}
	
	@Test
	public void BProcessIfCurrentRoundIsLastRound() {
		
		prepareTableForGame(table, 3);
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		for(int i=0;i<4;i++)
			for(int j=0;j<3;j++) {
				assertTrue(bp.setInputData(new OutputToServer(100, 1, "name"+j)));
			}
		
	}
	
	@Test
	public void inBusinessProcessBankInRoundIsNotNull() {
		
		prepareTableForGame(table, 3);
		OutputObject os = new OutputToServer(100, -1, "name0");
		BusinessProcess bp = new BusinessProcess(table, new DBDriver());
		
		assertNotNull(bp.getMap().get("name0"));
	}
}
