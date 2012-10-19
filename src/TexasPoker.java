

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import projecttu.Additional.Combinations;
import projecttu.Gamelogic.Deck;
import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerHands;
import projecttu.Gamelogic.PokerTable;
import projecttu.GraphicInterface.PlayerPanel;
import projecttu.GraphicInterface.ServerListener;

public class TexasPoker {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable()
		{
			public void run() {
				JFrame frame = new PFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		}
				);
	}
}

class PFrame extends JFrame {
	private static final long serialVersionUID = -4869073630645960579L;

	public PFrame() {
		setTitle("Texas Holdem");
		setSize(800, 600);
		
//		ServerListener listener = new ServerListener("localhost", 8880);
		
		JPanel panel = new JPanel();
		
		String filenam = "cards/";
		String[] filenames = {"3c", "3d", "4s", "3h"};
		ArrayList<File> images = new ArrayList<File>();
		for(String s : filenames)
			images.add(new File("cards/"+s+".gif"));
		Box box = Box.createHorizontalBox();
		BufferedImage image;
		try {
			for(File f : images) {
				image = ImageIO.read(f);
				box.add(new JLabel(new ImageIcon(image)));
			}
			add(box);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
