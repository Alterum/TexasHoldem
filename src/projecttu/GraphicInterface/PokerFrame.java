package projecttu.GraphicInterface;

import javax.swing.JFrame;
import javax.swing.JPanel;

import projecttu.Server.ServerListener;

public class PokerFrame extends JFrame {
	private static final long serialVersionUID = -4869073630645960579L;
	private final String HOST = "192.168.2.65";
	private final int PORT = 8907;
	
	public PokerFrame() {
		setTitle("Texas Holdem");
		setSize(800, 600);
		
		ServerListener listener = new ServerListener(HOST, PORT);
		
		JPanel playerPanel = new PlayerPanel(listener);
		
		add(playerPanel);
	}
}

