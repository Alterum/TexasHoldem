package projecttu.Gamelogic;

public class Player {
	
	public Player(String name) {
		if(name.equals(""))
			name = null;
		this.name = name;
		hand = null;
		score = 1000;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void increaseScore(int x) {
		score += x;
	}
	
	public void setScore(int y) {
		score = y;
	}
	
	public void setHand(String[] hand) {
		this.hand = hand;
	}
	
	public String[] getHand() {
		return hand;
	}
	
	public int getBet() {
		return bet;
	}
	
	public void setBet(int x) {
		bet = x;
	}
	
	public void setStatus(int x) {
		if(x < 0)
			status = -1;
		else if(x > 0)
			status = 1;
		else
			status = 0;
	}
	
	public int getStatus() {
		return status;
	}
	/**
	 * @param status : -1 not ready, 0 observer, 1 ready to play
	 */
	private int status = -1;
	private int score;
	private int bet = 0;
	private String[] hand;
	private String name;
}
