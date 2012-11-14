package projecttu.GraphicInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import projecttu.Server.ServerListener;


public class GameInterface {
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable()
			{
				public void run() {
					PokerFrame frame = new PokerFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			}
					);
	}
}

class PokerFrame extends JFrame {
	private static final long serialVersionUID = -4869073630645960579L;

	public PokerFrame() {
		setTitle("Texas Holdem");
		setSize(800, 600);
		
		ServerListener listener = new ServerListener("localhost", 8880);
		
		JPanel playerPanel = new PlayerPanel(listener);
		
		add(playerPanel);
	}
}

