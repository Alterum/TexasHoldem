package tests;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;


public class TestPokerTable {
	
	@Test
	public void testPokerTableIsNotNull() {
		PokerTable table = new PokerTable();
		
		assertNotNull(table);
	}
	
	@Test
	public void testTablePokerStartBigBlindIsNull() {
		PokerTable table = new PokerTable();
		
		assertNull(table.getBigBlind());
	}
	
	@Test
	public void testTablePokerStartSmallBlindIsNull() {
		PokerTable table = new PokerTable();
		
		assertNull(table.getSmallBlind());
	}
	
	@Test
	public void testAmountPlayersFromTableIsMax() {
		PokerTable table = new PokerTable();

		table.addPlayer(new Player("name1"));
		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		
		assertTrue(table.isMaxPlayersForTheTable());
	}
	
	@Test
	public void testAddPlayerToTableWhereAmountPlayersIsMax() {
		PokerTable table = new PokerTable();
		// MAX = 3
		table.addPlayer(new Player("name1"));
		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		
		assertFalse(table.addPlayer(new Player("name4")));
	}
	
	@Test
	public void testCountPlayerOnTheTableAfterAddPlayer() {
		PokerTable table = new PokerTable();
		
		table.addPlayer(new Player("name1"));
		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		
		assertEquals(table.getPlayers().size(), 3);
	}
	
	@Test
	public void testCountPlayerOnTheTableAfterRemovePlyer() {
		PokerTable table = new PokerTable();
		
		Player p1 = new Player("name1");
		table.addPlayer(p1);
		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		table.removePlayer(p1);
		
		assertEquals(table.getPlayers().size(), 2);
	}
	
	@Test
	public void testRemovePlyerFromEmptyTable() {
		PokerTable table = new PokerTable();
		Player p1 = new Player("name1");
		
		assertFalse(table.removePlayer(p1));
	}
	
	@Test
	public void testRemovePlyerFromAnotherTable() {
		PokerTable table = new PokerTable();
		Player p1 = new Player("name1");

		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		
		assertFalse(table.removePlayer(p1));
	}
	
	@Test
	public void testRemovePlyerFromTableWithSameName() {
		PokerTable table = new PokerTable();
		Player p1 = new Player("name");

		table.addPlayer(new Player("name"));
		table.addPlayer(new Player("name2"));
		
		assertFalse(table.removePlayer(p1));
	}
	
	@Test
	public void testRemoveBigBlindFromTable() {
		PokerTable table = new PokerTable();
		Player p1 = new Player("name");

		table.addPlayer(p1);
		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		table.setToBigBlind(p1);
		table.removePlayer(p1);
		
		assertEquals(table.getBigBlind().getName(), "name3");
	}
	
	@Test
	public void testRemoveSmallBlindFromTable() {
		PokerTable table = new PokerTable();
		Player p1 = new Player("name");
		
		table.addPlayer(p1);
		table.addPlayer(new Player("name2"));
		table.addPlayer(new Player("name3"));
		table.setToSmallBlind(p1);
		table.removePlayer(p1);
		
		assertEquals(table.getSmallBlind().getName(), "name2");
	}
	
	@Test
	public void testGetPlayerFromTable() {
		PokerTable table = new PokerTable();
		Player p1 = new Player("name1");
		
		table.addPlayer(p1);
		table.addPlayer(new Player("name2"));
		
		assertEquals(table.getPlayer("name1"), p1);
	}
	
//	@Test (expected= PlayerWithSameNameException.class)
//	public void testGetPlayerFromTableWithSameName() {
//		PokerTable table = new PokerTable();
//
//		table.addPlayer(new Player("name"));
//		table.addPlayer(new Player("name"));
//
//	}
	
	@Test
	public void testGetWinCombination() {
		
	}
	
	@Test
	public void testMaxDealCardsToTheTable() {
		PokerTable table = new PokerTable();
		
		assertTrue(table.dealCardsToTheTable(5));
	}
	
	@Ignore
	public void testIterationDealCardsToTheTable() {
		PokerTable table = new PokerTable();
		
		table.dealCardsToTheTable(3);
		table.dealCardsToTheTable(1);
		
		assertFalse(table.dealCardsToTheTable(2));
	}

	
}
