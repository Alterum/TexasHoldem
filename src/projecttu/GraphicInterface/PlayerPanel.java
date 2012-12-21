package projecttu.GraphicInterface;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import projecttu.OutputObject.ClientParser;
import projecttu.Server.ServerListener;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 5944846484617779988L;
	
	public PlayerPanel(ServerListener listener) {
		
		this.listener = listener;
		parser = listener.getParser();
//		this.parser = parser;
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		createLabels();
		creatButtons();
		startButton.addActionListener(new StartGameAction());
		for(String key : menuButtons.keySet()) {
			menuButtons.get(key).setEnabled(false);
		}
		
		addComponents();

		
		parser.setChangingComponents(labels, menuButtons);
		
		//Player Menu
		for(String key : menuButtons.keySet()) {
			menuButtons.get(key).setEnabled(false);
		}
		
	}

	private class StartGameAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			startGame();
		}
		
	}
	
	private class PlayAction implements ActionListener {
		private String function;
		public PlayAction(String key) {
			function = key;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(function.equals("play"))
				requestOnPlay();
			else if(function.equals("newGame"))
				newGame();
			else if(function.equals("raise"))
				raise();
			else if(function.equals("fold"))
				fold();
			else if(function.equals("check"))
				check();
			else if(function.equals("call"))
				call();
		}
		
	}
	
	private void creatButtons() {
		String[] menuBttnsNames =
			{"play", "call", "raise", "fold", "check", "newGame"};
		for(String key : menuBttnsNames) {
			menuButtons.put(key, new JButton(key));
			menuButtons.get(key).addActionListener(new PlayAction(key));
		}
	}
	
	private void createLabels() {	
		String[] nameLabels = {
				"message", "playerName", "playerBank", "playerStatus",
				"playerCards", "playerCards1", "playerCards2", "playerBet",
				"oppStatus1", "oppName1", "bankInRound","currentRound",
				"oppBank1", "oppCards1", "oppBet1", "oppStatus2",
				"oppName2", "oppBank2", "oppCards2", "oppBet2", "cards",
				"cards1","cards2","cards3","cards4","cards5", "bank",
				"currentBet", "round", "ring"};
		
		for(String key : nameLabels) {
			labels.put(key, new JLabel());
		}
	}
	
	private void addComponents() {
		// 0 column
		add(constant, new GBC(0,0).setAnchor(GBC.EAST));
		add(new JLabel("oppName1"), new GBC(0,1).setAnchor(GBC.EAST));
		add(new JLabel("oppBank1"), new GBC(0,2).setAnchor(GBC.EAST));
		add(new JLabel("oppBet1"), new GBC(0,3).setAnchor(GBC.EAST));
		add(new JLabel("oppCards1"), new GBC(0,4).setAnchor(GBC.EAST));
		add(new JLabel("playerName"), new GBC(0,5).setAnchor(GBC.EAST));
		add(new JLabel("playerBank"), new GBC(0,6).setAnchor(GBC.EAST));
		add(new JLabel("playerBet"), new GBC(0,7).setAnchor(GBC.EAST));
		add(new JLabel("currentBet"), new GBC(0,8).setAnchor(GBC.EAST));
		
		// 1 column
		add(namePlayerField, new GBC(1,0).setWeight(100, 0));
		add(labels.get("oppName1"), new GBC(1,1).setWeight(100, 0));
		add(labels.get("oppBank1"), new GBC(1,2).setWeight(100, 0));
		add(labels.get("oppBet1"), new GBC(1,3).setWeight(100, 0));
		add(labels.get("oppCards1"), new GBC(1,4).setWeight(100, 0));
		add(labels.get("playerName"), new GBC(1,5).setWeight(100, 0));
		add(labels.get("playerBank"), new GBC(1,6).setWeight(100, 0));
		add(labels.get("playerBet"), new GBC(1,7).setWeight(100, 0));
		add(labels.get("currentBet"), new GBC(1,8).setWeight(100, 0));
		
		// 2 column
		add(startButton, new GBC(2,0).setWeight(100, 100));
		add(labels.get("cards1"), new GBC(2, 1, 1, 4).setWeight(100, 100));
		add(labels.get("playerCards1"), new GBC(2,5, 1, 4).setWeight(100, 100));
		
		// 3 column
		add(menuButtons.get("play"), new GBC(3,0).setWeight(100, 100));
		add(labels.get("cards2"), new GBC(3, 1, 1, 4).setWeight(100, 100));
		add(labels.get("playerCards2"), new GBC(3,5, 1, 4).setWeight(100, 100));
		
		// 4 column
		add(new JLabel("message"), new GBC(4, 0).setWeight(100, 0));
		add(labels.get("cards3"), new GBC(4, 1, 1, 4).setWeight(100, 100));
		add(menuButtons.get("call"), new GBC(4, 7).setWeight(100, 100));
		add(menuButtons.get("newGame"), new GBC(4, 8).setWeight(100, 100));
		
		// 5 column
		add(labels.get("message"), new GBC(5, 0).setWeight(100, 100));
		add(labels.get("cards4"), new GBC(5, 1, 1, 4).setWeight(100, 100));
		add(menuButtons.get("check"), new GBC(5, 7).setWeight(100, 100));
		add(menuButtons.get("raise"), new GBC(5, 8).setWeight(100, 100));
		
		// 6 column
		add(labels.get("cards5"), new GBC(6, 1, 1, 4).setWeight(100, 100));
		add(menuButtons.get("fold"), new GBC(6, 7).setWeight(100, 100));
		add(raiseField, new GBC(6, 8).setWeight(100, 100) );
		
		// 7 column
		add(new JLabel("bank: "), new GBC(7,0));
		add(new JLabel("oppName2: "), new GBC(7,1));
		add(new JLabel("oppBank2: "), new GBC(7,2));
		add(new JLabel("oppBet2: "), new GBC(7,3));
		add(new JLabel("oppCards2: "), new GBC(7,4));
		add(new JLabel("Bank in round: "), new GBC(7,6));
		add(new JLabel("current round: "), new GBC(7,7));
		
		// 8 column
		add(labels.get("bank"), new GBC(8,0).setWeight(100, 0).setAnchor(GBC.WEST));
		add(labels.get("oppName2"), new GBC(8,1).setWeight(100, 0).setAnchor(GBC.WEST));
		add(labels.get("oppBank2"), new GBC(8,2).setWeight(100, 0).setAnchor(GBC.WEST));
		add(labels.get("oppBet2"), new GBC(8,3).setWeight(100, 0).setAnchor(GBC.WEST));
		add(labels.get("oppCards2"), new GBC(8,4).setWeight(100, 0).setAnchor(GBC.WEST));
		add(labels.get("bankInRound"), new GBC(8,6).setWeight(100, 0).setAnchor(GBC.WEST));
		add(labels.get("currentRound"), new GBC(8,7).setWeight(100, 0).setAnchor(GBC.WEST));
	}
	
	private void startGame() {
		String playerName = namePlayerField.getText();
		parser.setName(playerName);
		try {
			listener.send(playerName);
			String flag = listener.getString();
			System.out.println(flag);
			
			if(flag.equals("true")) {
				labels.get("playerName").setText(playerName);
				startButton.setVisible(false);
				namePlayerField.setVisible(false);
				menuButtons.get("play").setEnabled(true);
				
			} else
				constant.setText("incorrect");
		} catch ( java.lang.NullPointerException e) {
			System.out.println("Not connected to server");
		}
	}
	
	private void requestOnPlay() {
//		listener.send("start");
		for(String key : menuButtons.keySet())
			menuButtons.get(key).setEnabled(false);
		listener.startListen();
	}
	
	private void call() {
		try {
			Integer currentBet = Integer.parseInt(labels.get("currentBet").getText());
			parser.requestRaise(currentBet);
		} catch(NumberFormatException e) {
			constant.setText("incorect");
		}
	}
	
	private void fold() {
		/**
		 *  set status 0 - observer
		 *  get data and return without deelay
		 */
		
		parser.requestFold();
		
//		System.exit(0);
	}
	
	private void check() {
		parser.requestRaise(0);
	}
	
	private void raise() {
		try {
			Integer raiseBet = Integer.parseInt(raiseField.getText());
			Integer playerScore = Integer.parseInt(labels.get("playerBank").getText());
			labels.get("playerBank").setText(""+(playerScore-raiseBet));
			Integer currentBet = Integer.parseInt(labels.get("currentBet").getText());
			parser.requestRaise(raiseBet);

		} catch(NumberFormatException e) {
			constant.setText("incorect");
		}
		
	}
	
	private void newGame() {
		parser.requestNewGame();
	}
	
	private ServerListener listener;
	private ClientParser parser;
	private JLabel constant = new JLabel(
			"Player Name: ", SwingConstants.RIGHT);
	private JTextField namePlayerField = new JTextField("", 7);
	private JTextField raiseField = new JTextField("", 4);
	private JButton startButton = new JButton("start");
	private HashMap<String, JButton> menuButtons =
			new HashMap<String, JButton>();
	private HashMap<String, JLabel> labels =
			new HashMap<String, JLabel>();
}
