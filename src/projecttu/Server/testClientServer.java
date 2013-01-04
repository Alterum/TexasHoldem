package projecttu.Server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.GraphicInterface.GameInterface;
import projecttu.GraphicInterface.PlayerPanel;
import projecttu.GraphicInterface.PokerFrame;
import projecttu.OutputObject.OutputToServer;


public class testClientServer {
	
	@Test
	public void testSPAccessChanelNotNull() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		sv.setNewTable();
		sv.setAccessChanel();

		assertNotNull(sv.getCurrentTable());
		
	}
	
	@Test
	public void testSPReady() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		sv.resetReadyPlayersToNewGame();
		sv.resetReadyPlayers();
		sv.ressetAllReadyToNextRound();
		
		assertFalse(sv.isAllReadyToNextRound());
		assertFalse(sv.isAllReadyToNewGame());
		assertFalse(sv.ready());
		
		allPlayersReady(sv);
		
		assertTrue(sv.isAllReadyToNextRound());
		assertTrue(sv.isAllReadyToNewGame());
		assertTrue(sv.ready());
		
		assertFalse(sv.isNewGame());
		
		Player player = new Player("name0");
		sv.putPlayerAtTheTable(player);
	
		assertNotNull(sv.getPlayer("name0"));

		assertFalse(sv.getUpFromTheTable(player));
	}
	
	private void allPlayersReady(ServerProcess sv) {
		sv.setAmountPlayers(3);
		for(int i=0;i<3;i++) {
			sv.readyToPlay();
			sv.readyToNewGame();
			sv.readyToNextRound();
		}
		
	}

	@Test
	public void testObserver() {
		Observer ob = new Observer(new DBDriver());
		
		ob.newGame();
		ServerProcess sv = ob.getStatus();
		assertFalse(ob.isMaxConnInCurTable());
		
		
//		ob.start();
//		ob.run();
		
		
	}
	
	@Test
	public void testThreadProcess() {
		Observer ob = new Observer(new DBDriver());
		ob.newGame();
		ServerProcess sv = ob.getStatus();
		sv.putPlayerAtTheTable(new Player("p0"));
		ThreadProcess tp = new ThreadProcess(sv);
		
		tp.isPlayerNameReserved("p0");
		tp.isPlayerNameReserved("p1");
		
		tp.prepareGameProcess("p1");
		tp.startGameProcess("Start");
		tp.startGameProcess("start");
		tp.startGameProcess("observer");

		
	}
	
	@Test
	public void testConnection() throws IOException {
//		PokerFrame frame = new PokerFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		new PlayerPanel(new ServerListener("localhost", 8907));
	}

}
