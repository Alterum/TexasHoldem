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

