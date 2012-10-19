package projecttu.GraphicInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartGameInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable()
		{
			public void run() {
				JFrame frame = new StartFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		}
				);
	}
}

class StartFrame extends JFrame {
	public StartFrame() {
		setTitle("Texas Holdem");
		setSize(300, 100);
		
		JPanel startPanel = new JPanel();
				
		startPanel.setLayout(new GridLayout(1, 3));
		
		// StartMenu
		startPanel.add(constantName);
		startPanel.add(namePlayerField);
		startButton.addActionListener(new StartGameAction());
		sendButton.addActionListener(new SendButtonAction());
		sendButton.setEnabled(false);
		startPanel.add(startButton);
		startPanel.add(sendButton);
		
		add(startPanel, BorderLayout.CENTER);
		
	}
	
	private class StartGameAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String playerName = namePlayerField.getText();
			
			if(!playerName.equals("")) {
				Client c = new Client(playerName);
				c.start();
				constantName.setText(playerName);
				startButton.setEnabled(false);
				sendButton.setEnabled(true);
			}
		}
		
	}
	
	private class SendButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = namePlayerField.getText();
		}
		
	}
	
	
	private JLabel constantName = new JLabel(
			"Player Name: ", SwingConstants.RIGHT);
	private JTextField namePlayerField = new JTextField();
	private JButton startButton = new JButton("start");
	private JButton sendButton = new JButton("send");
}

class Client extends Thread  {
	private String name;
	
	public Client(String name) {
		this.name = name;
	}
	public void run() {
		try {
			new PokerClient(name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
