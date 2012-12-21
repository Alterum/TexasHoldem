package tests;

import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;
import projecttu.Additional.Combinations;
import projecttu.Gamelogic.Deck;
import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;

public class TestTexasPoker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		//new TestCombinations();
//		new TestPlayer();
//		new TestPokerTable();
		PokerTable pt = new PokerTable();
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		pt.addPlayer(p1);
		pt.addPlayer(p2);
		pt.setActivePlayer(p1);
		pt.setActivePlayer(p2);
//		pt.resetTable();
		pt.dealCardsToPlayers();
//		System.out.println(pt.dealCardsToTheTable(5));
		for(String[] cards : pt.getWinCombination()) {
			System.out.println("  !!!!!!!!!!!!!!!!!!!!!!!!!1  ");
			for(String card : cards)
				System.out.println(card);
		}
		
//		Combinations n1 = new Combinations(2, 2);
//		System.out.println(n1.getCompliteArr());
//		for(Integer[] i : n1.getCompliteArr())
//			for(Integer j : i)
//				System.out.println(j);
	}

}
