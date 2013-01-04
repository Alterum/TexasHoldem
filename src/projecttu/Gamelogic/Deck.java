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
	
//	public void print() {
//		for(String s : deck)
//			System.out.println(s);
//	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public ArrayList<String[]> deal(int numHands, int numCards) {
		ArrayList<String[]> deal =
				new ArrayList<String[]>();
		for(int i = 0; i<numHands; i++) {
			String str[] = prepareCards(numCards);
			deal.add(str);
		}
		return deal;
	}
	
	private String[] prepareCards(int numCards) {
		String[] str = new String[numCards];
		int index = 0;
		for(int j=0; j<numCards; j++) {
			if(itr.hasNext())
				str[index++] = itr.next();
			else
				break;
		}
		return str;
	}

	private void fillDeck(String suit, String rank) {
		for(int i = 0; i<suit.length(); i++)
			for(int j = 0; j<rank.length(); j++)
				deck.add(Character.toString(rank.charAt(j))+
						Character.toString(suit.charAt(i)));
		itr = deck.iterator();
	}
	
	private ArrayList<String> deck = 
			new ArrayList<String>();
	private Iterator<String> itr;
}
