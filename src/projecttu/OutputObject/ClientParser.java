package projecttu.OutputObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.Server.Logger;

public class ClientParser implements Parser {	

	private int status;
	private int bet;
	private String name;
	private boolean on = false;
	private OutputObject output = null;
	private OutputToClient input = null;
	private HashMap<String, JButton> buttons = null;
	private HashMap<String, JLabel> labels = null;
	private Logger log;
	private final int OBSERVER = 0;
	
	public ClientParser() {
		log = new Logger("client_parser.log");
	}
	
	public void setChangingComponents(HashMap<String, JLabel> labels,
			HashMap<String, JButton> buttons) {
		this.labels = labels;
		this.buttons = buttons;
	}
	
	public void setName(String playerName) {
		name = playerName;
	}
	
	public OutputObject getOutputObject() {
		on = false;
		return output;
	}
	
	public void requestNewGame() {
		disabledButtons();
		output = new OutputToServer(0, -5, name);
		on = true;
	}
	
	public void requestRaise(int raiseBet) {
		disabledButtons();
		output = new OutputToServer(raiseBet, status, name);
		on = true;
	}

	public boolean isRequest() {
		return on;
	}
	
	public synchronized void parserInputObject(OutputObject obj) {
		input = (OutputToClient) obj;
		parserInput();
	}
	
	private synchronized void parserInput() {
		enabledButtons();
		setInfoPanel();	
		
		if(status == OBSERVER)
			on = true;
		
	}

	public void enabledButtons() {
		HashMap<String, Boolean> access = input.getAccess();
		for(String key : access.keySet())
			buttons.get(key).setEnabled(access.get(key));
	}
	
	private void disabledButtons() {
		for(String key : buttons.keySet())
			buttons.get(key).setEnabled(false);
	}
	
	public void setInfoPanel() {
		HashMap<String, String> info = input.getInfo();
		for(String key : info.keySet()) {
			log.log("Clietn "+name+" Input: "+key+" = "+info.get(key));
			labels.get(key).setText(info.get(key));
		}

		getCardsImage(info, "cards");
		getCardsImage(info, "playerCards");
		
		status = Integer.parseInt(info.get("playerStatus"));
		
		if(Integer.parseInt(info.get("currentRound")) >= 4) {
			disabledButtons();
			status = -5;
			buttons.get("newGame").setEnabled(true);
		}

	}
	
	public void getCardsImage(HashMap<String, String> info, String str) {
		String cards = info.get(str);
		String[] splitCards = cards.split(" ");
		int i = 1;
		for(String card : splitCards) {
			if(card == "")
				continue;
			File f = new File("cards/"+card+".gif");
			try {
				BufferedImage image = ImageIO.read(f);
				labels.get(str+i++).setIcon(new ImageIcon(image));
			} catch (IOException e) {
				System.out.println(card);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void parserOutput(String query) {
		// TODO Auto-generated method stub
		
	}

	public void requestFold() {
		disabledButtons();
		status = OBSERVER;
		output = new OutputToServer(0, status, name);
		on = true;
		
	}
}
