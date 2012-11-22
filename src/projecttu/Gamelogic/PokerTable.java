package projecttu.Gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class PokerTable {
	public PokerTable() {
		count++;
		name = "Table #"+count;
		deck = new Deck();
		players = new ArrayList<Player>();
		cardsOnTable = new ArrayList<String[]>();
		currentBet = 0;
		bank = 0;
		small = 0;
		bankInRound = 0;
		dealCardsToTheTable(5);
	}
	
	public String toString() {
		return name;
	}

	public void selectSmallAndBigBlinds() {
		// TODO Auto-generated method stub
		if(players.size() < 2)
			return;
		if(small == players.size())
			small = 0;
		int big = small+1;
		if(small == players.size()-1)
			big = 0;
		
		smallBlind = players.get(small++);
		bigBlind = players.get(big);
	}
	
	public String[] getCardsOnTable() {
		return cardsOnTable.get(0);
	}
	
//	public void newGame() {
//		deck = new Deck();
//		deck.shuffle();
//		cardsOnTable = new ArrayList<String[]>();
//		activePlayers = new ArrayList<Player>();
////		activePlayers.addAll(players);
//		currentBet = 0;
//		bank = 0;
//		small = 0;
//		bankInRound = 0;
//	}
	
	public void setToSmallBlind(Player small) {
		smallBlind = small;
	}
	
	public void setToBigBlind(Player big) {
		bigBlind = big;
	}
	
	public Player getSmallBlind() {
		return smallBlind;
	}
	
	public Player getBigBlind() {
		return bigBlind;
	}
	
	public boolean addPlayer(Player pl) {
		if(players.size() < MAX)
			players.add(pl);
		else
			return false;
		return true;
	}
	
	public boolean removePlayer(Player pl) {
		Iterator<Player> itr = players.iterator();
		while(itr.hasNext()) {
			Player player = itr.next();
			if(player.equals(pl)) {
				
				if(smallBlind == player)
					smallBlind = null;
				else if(bigBlind == player)
					bigBlind = null;
				
				itr.remove();
				selectSmallAndBigBlinds();
				return true;
			}
		}
		return false;
	}
	
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(String name) {
		for(Player player : players)
			if(player.getName().equals(name))
				return player;
		return null;
	}
	
	public void setActivePlayer(int i, Player player) {
		activePlayers.add(i, player);
	}
	
	public ArrayList<Player> getActivePlayers() {
		return activePlayers;
	}
	
	public boolean isMaxPlayersForTheTable() {
		if(players.size() < MAX)
			return false;
		else
			return true;
	}
	
	public ArrayList<String[]> getWinCombination() {
		// TODO Auto-generated method stub
		ArrayList<String[]> allHands = 
				new ArrayList<String[]>();
		for(Player player : players) {
			String[] allHand = new String[7];
			int index = 0;
			for(String s : player.getHand())
				allHand[index++] = s;
			for(String s : cardsOnTable.get(0))
				allHand[index++] = s;
			allHands.add(
					PokerHands.bestHand(allHand).get(0));
		}
		
		// TEST
		for(String[] str : PokerHands.max(allHands)) {
			System.out.println("\n");
			for(String s : str)
				System.out.print(s+" ");
		}
		
		return PokerHands.max(allHands);
	}
	
	public Player getPlayerWithBestHand() {
		ArrayList<String[]> allHands = 
				new ArrayList<String[]>();
		HashMap<String, String[]> bestPlayersHand = 
				new HashMap<String, String[]>();
		for(Player player : activePlayers) {
			int index = 0;
			String[] playerhand = new String[7];
			for(String s : player.getHand())
				playerhand[index++] = s;
			for(String s : cardsOnTable.get(0))
				playerhand[index++] = s;
			bestPlayersHand.put(player.getName(),
					PokerHands.bestHand(playerhand).get(0));
			allHands.add(PokerHands.bestHand(playerhand).get(0));
		}
		String name = "";
		for(String key : bestPlayersHand.keySet())
			for(String[] s : PokerHands.max(allHands))
				if(bestPlayersHand.get(key) == s) {
					name = key;
					break;
				}
			
		return getPlayer(name);
	}
	
	
	public void dealCardsToPlayers() {
		ArrayList<String[]> hands = 
				deck.deal(players.size(), 2);
		int index = 0;
		for(Player pl : players)
			pl.setHand(hands.get(index++));
		
	}
	
	public boolean dealCardsToTheTable(int numCards) {
		if(cardsOnTable.size() + numCards <= 5)
			cardsOnTable.addAll(deck.deal(1, numCards));
		else
			return false;
		return true;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(int x) {
		currentBet = x;
	}
	public int getBank() {
		return bank;
	}
	public void setBank(int b) {
		bank = b;
	}
	public int getBankInRound() {
		return bankInRound;
	}
	public void setBankInRound(int b) {
		bankInRound = b;
	}
	
	
	private final int MAX = 3;
	private ArrayList<Player> players;
	private ArrayList<Player> activePlayers;
	private Player smallBlind = null;
	private Player bigBlind = null;
	private ArrayList<String[]> cardsOnTable;
	private Deck deck;
	private String name;
	private int small = 0;
	private int currentBet = 0;
	private int bankInRound = 0;
	private int bank = 0;
	private static int count = 0;
}
