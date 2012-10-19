package projecttu.Gamelogic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


public class Deck {
	
	public Deck() {
		String rank = "23456789TJQKA";
		String suit = "shdc";
		fillDeck(suit, rank);
	}
	
	public void print() {
		for(String s : deck)
			System.out.println(s);
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
//	public HashMap<String, String[]> deal(
//			ArrayList<String> players, int numCards) {
//		HashMap<String, String[]> deal =
//				new HashMap<String, String[]>();
//		for(int i = 0; i<players.size(); i++) {
//			String[] str = new String[numCards];
//			int index = 0;
//			for(int j = i*numCards; j < (i+1)*numCards; j++)
//				str[index++] = deck.get(j);
//			deal.put(players.get(i), str);
//		}
//		return deal;
//	}
	
	public ArrayList<String[]> deal(int numHands, int numCards) {
		ArrayList<String[]> deal =
				new ArrayList<String[]>();
		for(int i = 0; i<numHands; i++) {
			String[] str = new String[numCards];
			int index = 0;
			for(int j=0; j<numCards; j++) {
				if(itr.hasNext())
					str[index++] = itr.next();
				else
					return null;
			}
			deal.add(str);
		}
		return deal;
	}
	
	private void fillDeck(String suit, String rank) {
		for(int i = 0; i<suit.length(); i++)
			for(int j = 0; j<rank.length(); j++)
				deck.add(Character.toString(rank.charAt(j))+
						Character.toString(suit.charAt(i)));
		itr = deck.iterator();
	}
	
//	public void testPrintDeal() {
//		ArrayList<String> pl = new ArrayList<String>();
//		pl.add("Miwa");
//		pl.add("Allam");
//		pl.add("Kisel");
//		HashMap<String, String[]> deal =
//				deal(pl, 5);
//		for(String  key : deal.keySet()) {
//			System.out.println(key);
//			for(int i=0; i<deal.get(key).length; i++)
//				System.out.println(deal.get(key)[i]);
//		}
//	}
	
	private ArrayList<String> deck = 
			new ArrayList<String>();
	private Iterator<String> itr;
}
