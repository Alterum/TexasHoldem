package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerHands;
import projecttu.Gamelogic.PokerTable;


public class TestPokerTable {
	
	@Test
	public void testPokerTableIsNotNull() {
		PokerTable table = new PokerTable();
		
		assertNotNull(table);
	}
	
	@Test
	public void testPokerTableToString() {
		PokerTable table = new PokerTable();
		
		assertNotNull(table.toString());
	}
	
	@Test
	public void testPokerTableCardsOnTableIsNotNull() {
		PokerTable table = new PokerTable();
		
		table.dealCardsToTheTable(5);
		
		assertNotNull(table.getCardsOnTable());
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
	public void testTablePokerSelectSmallAndBigBlinds() {
		PokerTable table = new PokerTable();
		
		for(int i=0;i<3;i++)
			table.setActivePlayer(new Player("name"+i));
		table.selectSmallAndBigBlinds();
			
		assertNotNull(table.getSmallBlind());
		assertNotNull(table.getBigBlind());
	}
	
	public void setSmallAndBigBlinds(PokerTable table) {
		for(int i=0;i<3;i++)
			table.setActivePlayer(new Player("name"+i));
		table.selectSmallAndBigBlinds();
		
	}
	
	@Test
	public void testTablePokerIfPlayerIsSmallBlindWillReset() {
		PokerTable table = new PokerTable();
		
		setSmallAndBigBlinds(table);
		table.ifPlayerIsBlindWillReset(table.getActivePlayer("name0"));
		
		assertNull(table.getSmallBlind());
	}
	
	@Test
	public void testTablePokerIfPlayerIsBigBlindWillReset() {
		PokerTable table = new PokerTable();
		
		setSmallAndBigBlinds(table);
		table.ifPlayerIsBlindWillReset(table.getActivePlayer("name1"));
		
		assertNull(table.getBigBlind());
	}
	
	@Test
	public void testPokerTableGetWinCombination() {
		PokerTable table = new PokerTable();
		
		prepareTableForGame(table);
		
		assertNotNull(table.getWinCombination());
	}
	
	@Test
	public void testPokerTableGetPlayerWithBestHandCombination() {
		PokerTable table = new PokerTable();
		
		prepareTableForGame(table);
		
		assertNotNull(table.getPlayerWithBestHand());
	}
	
	
	private void prepareTableForGame(PokerTable table) {
		putPlayerToTheTable(3, table);
		table.dealCardsToPlayers();
		table.dealCardsToTheTable(5);
		
	}

	
	private void putPlayerToTheTable(int j, PokerTable table) {
		for(int i=0;i<j;i++) {
			table.addPlayer(new Player("name"+i));
			table.setActivePlayer(table.getPlayer("name"+i));
		}
		
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
		
		assertEquals(table.getBigBlind().getName(), "name");
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
		
		assertEquals(table.getSmallBlind().getName(), "name");
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
	public void testPokerTableCurrentBet() {
		PokerTable table = new PokerTable();
		
		table.setCurrentBet(100);
		
		assertEquals(table.getCurrentBet(), 100);
	}
	
	@Test
	public void testPokerTableAddToTheBank() {
		PokerTable table = new PokerTable();
		
		table.setBank(100);
		table.addToTheBank(100);
		
		assertEquals(table.getBank(), 200);
	}
	
	@Test
	public void testPokerTableBankInRound() {
		PokerTable table = new PokerTable();
		
		table.setBankInRound(100);
		
		assertEquals(table.getBankInRound(), 100);
	}
	
	@Test
	public void testPokerHandRankIsStraightFlush() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "3s", "4s", "5s", "6s"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIsKind4() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "2h", "2d", "2c", "6s"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIsFullhouse() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "2h", "2d", "3c", "3h"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIsStraight() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "3h", "4d", "5c", "6h"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIsFlush() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "3s", "8s", "5s", "9s"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIsPair() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "2h", "8s", "5s", "9s"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIs2Pair() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "2h", "8s", "8h", "9s"};
		
		assertNotNull(hand.handRank(h));
	}
	
	@Test
	public void testPokerHandRankIsKind3() {
		PokerHands hand = new PokerHands();
		
		String[] h = {"2s", "2h", "2c", "8h", "9s"};
		
		assertNotNull(hand.handRank(h));
	}

	
}
