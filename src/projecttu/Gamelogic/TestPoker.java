package projecttu.Gamelogic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestPoker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PokerTable t = new PokerTable();
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		t.addPlayer(p1);
		t.addPlayer(p2);
				
		t.dealCardsToTheTable(5);
		
		t.setToSmallBlind(p1);
		t.setToBigBlind(p2);
		
		// perflop
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		t.dealCardsToPlayers();
		// ostalnye igroki delajut stavki, poka ne urovnajjutsja
		
		// flop
		System.out.println(t.getCardsOnTable()[0]+" "+t.getCardsOnTable()[1]+" "+t.getCardsOnTable()[2]);
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		//uravnivanija
		
		// river 
		System.out.println(t.getCardsOnTable()[3]);
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		//uravnivanija
		
		// turn 
		System.out.println(t.getCardsOnTable()[4]);
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		//uravnivanija
		
		
		t = new PokerTable();
		t.addPlayer(p1);
		t.addPlayer(p2);
			
		t.dealCardsToTheTable(5);
		
		t.setToSmallBlind(p1);
		t.setToBigBlind(p2);
		
		// perflop
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		t.dealCardsToPlayers();
		// ostalnye igroki delajut stavki, poka ne urovnajjutsja
		
		// flop
		System.out.println(t.getCardsOnTable()[0]+" "+t.getCardsOnTable()[1]+" "+t.getCardsOnTable()[2]);
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		//uravnivanija
		
		// river 
		System.out.println(t.getCardsOnTable()[3]);
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		//uravnivanija
		
		// turn 
		System.out.println(t.getCardsOnTable()[4]);
		p1.setBet(10); // small blind
		p2.setBet(20); // big blind
		//uravnivanija
		
		
		

	}

}
