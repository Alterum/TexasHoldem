package projecttu.Gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import projecttu.Server.Logger;


public class PokerTable {
	public PokerTable() {
		loadTable();
	}
	
	private void loadTable() {
		name = "Table #"+count++;
		players = new ArrayList<Player>();
		activePlayers = new ArrayList<Player>();
		resetTable();
	}
	
	public void resetTable() {
		deck = new Deck();
		cardsOnTable = new ArrayList<String[]>();
		currentBet = 0;
		bank = 0;
		small = 0;
		bankInRound = 0;
		deck.shuffle();
		dealCardsToTheTable(5);
	}
	
	public String toString() {
		return name;
	}

	public void selectSmallAndBigBlinds() {
		// TODO Auto-generated method stub
		if(activePlayers.size() < 2)
			return;
		setSmall();
		int big = setBig();
		smallBlind = activePlayers.get(small++);
		bigBlind = activePlayers.get(big);
	}
	
	private int setBig() {
		int big = small+1;
		if(small == activePlayers.size()-1)
			big = 0;
		return big;
		
	}

	private void setSmall() {
		if(small == activePlayers.size())
			small = 0;	
	}

	public String[] getCardsOnTable() {
		return cardsOnTable.get(0);
	}
	
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
		return removePlayerFromTable(pl, 1);
	}
	
	public boolean removeActivePlayer(Player pl) {
		return removePlayerFromTable(pl, 0);
	}
	
	private boolean removePlayerFromTable(Player player, int x) {
		if(x==1) {
			removeFromArray(players.iterator(), player);
			selectSmallAndBigBlinds();
		}
		return removeFromArray(activePlayers.iterator(), player);
	}
	
	private boolean removeFromArray(Iterator<Player> itr, Player pl) {
		while(itr.hasNext()) {
			Player player = itr.next();
			if(player.equals(pl)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}
	
	public void ifPlayerIsBlindWillReset(Player player) {
		if(smallBlind == player)
			smallBlind = null;
		else if(bigBlind == player)
			bigBlind = null;
		
	}
	
	public Player getActivePlayer(String name) {
		return getPlayerFromArray(name, activePlayers);
	}
	
	public Player getPlayer(String name) {
		return getPlayerFromArray(name, players);
	}
	
	private Player getPlayerFromArray(String name,
			ArrayList<Player> array) {
		for(Player player : array)
			if(player.getName().equals(name))
				return player;
		return null;
	}
	
	public void setActivePlayer(Player player) {
		activePlayers.add(player);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Player> getActivePlayers() {
		return activePlayers;
	}
	
	public boolean isMaxPlayersForTheTable() {
		if(players.size() < MAX && activePlayers.size() == 0)
			return false;
		else
			return true;
	}
	
	public ArrayList<String[]> getWinCombination() {
		ArrayList<String[]> allHands = 
				new ArrayList<String[]>();
		for(Player player : players)
			preparePlayerHand(player, allHands);
		
		return PokerHands.max(allHands);
	}
	
	public Player getPlayerWithBestHand() {
		ArrayList<String[]> allHands = 
				new ArrayList<String[]>();
		HashMap<String, String[]> bestPlayersHand = 
				new HashMap<String, String[]>();
				
		preparePlayerHands(bestPlayersHand, allHands);
		String name = getPlayerNameWithBestHand(
				bestPlayersHand, allHands);
		
		return getPlayer(name);
	}
	
	private String[] preparePlayerHand(Player player, ArrayList<String[]> allHands) {
		String[] allHand = new String[7];
		int index = 0;
		putCardsOfPlayerAndTableToArray(player, index, allHand);
		allHands.add(
				PokerHands.bestHand(allHand).get(0));
		return allHand;
		
	}

	private void putCardsOfPlayerAndTableToArray(
			Player player, int index, String[] allHand) {
		for(String s : player.getHand())
			allHand[index++] = s;
		for(String s : cardsOnTable.get(0))
			allHand[index++] = s;
		
	}
	
	private void preparePlayerHands(
			HashMap<String, String[]> bestPlayersHand, 
			ArrayList<String[]> allHands) {
		for(Player player : activePlayers) {
			String[] playerhand = 
					preparePlayerHand(player, allHands);
			bestPlayersHand.put(player.getName(),
					PokerHands.bestHand(playerhand).get(0));
		}
		
	}

	private String getPlayerNameWithBestHand(
			HashMap<String, String[]> bestPlayersHand, 
			ArrayList<String[]> allHands) {
		String name = "";
		for(String key : bestPlayersHand.keySet())
			for(String[] s : PokerHands.max(allHands)) {
				boolean flag = true;
				for(int o=0; o<bestPlayersHand.get(key).length; o++)
					if(!bestPlayersHand.get(key)[o].equals(s[o]))
						flag = false;
						
				if(flag) {
					name = key;
					break;
				}
			}
				
		return name;
	}

	public void dealCardsToPlayers() {
		ArrayList<String[]> hands = 
				deck.deal(activePlayers.size(), 2);
		int index = 0;
		for(Player pl : activePlayers) {
			pl.setHand(hands.get(index++));
		}
		
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
	public void addToTheBank(int b) {
		bank += b;
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
	private Logger log = new Logger("table.txt");
}
