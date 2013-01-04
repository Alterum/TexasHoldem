package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import projecttu.Gamelogic.Player;

public class TestPlayer {
	
	@Test
	public void testPlayerNameNotNull() {
		Player p = new Player("");
		
		assertNull(p.getName());
	}
	
	@Test
	public void testPlayerToString() {
		Player p = new Player("name");
		
		assertEquals(p.toString(), "name");
	}
	
	@Test
	public void testPlayerIncreaseScore() {
		Player p = new Player("name");
		
		p.increaseScore(100);
		
		assertEquals(p.getScore(), 1100);
	}
	
	@Test
	public void testPlayerSetScore() {
		Player p = new Player("name");
		
		p.setScore(100);
		
		assertEquals(p.getScore(), 100);
	}
	
	@Test
	public void testPlayerSetBet() {
		Player p = new Player("name");
		
		p.setBet(100);
		
		assertEquals(p.getBet(), 100);
	}
	
	@Test
	public void testPlayerSetHand() {
		Player p = new Player("name");
		
		String hand[] = {"s2", "J2"};
		p.setHand(hand);
		
		assertEquals(p.getHand()[0], "s2");
		assertEquals(p.getHand()[1], "J2");
	}
	
	@Test
	public void testPlayerStartScore() {
		Player p = new Player("name");
		
		assertEquals(p.getScore(), 1000);
	}
	
	@Test
	public void testPlayerStartBet() {
		Player p = new Player("name");
		
		assertEquals(p.getBet(), 0);
	}
	
	@Test
	public void testPlayerStartStatus() {
		Player p = new Player("name");
		
		assertEquals(p.getStatus(), -1);
	}
	
	@Test
	public void testPlayerStartHand() {
		Player p = new Player("name");
		
		assertNull(p.getHand());
	}
	
	@Test
	public void testPlayersWithSameName() {
		Player p1 = new Player("name");
		Player p2 = new Player("name");
		
		assertFalse(p1.equals(p2));
	}
	
	@Test
	public void testPlayerChangeStatus() {
		Player p1 = new Player("name");
		Player p2 = new Player("name");
		Player p3 = new Player("name");
		
		
		p1.setStatus(-12);
		p2.setStatus(1);
		p3.setStatus(0);
		
		assertEquals(p1.getStatus(), -1);
		assertEquals(p2.getStatus(), 1);
		assertEquals(p3.getStatus(), 0);
	}
	
}
