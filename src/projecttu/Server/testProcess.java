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
