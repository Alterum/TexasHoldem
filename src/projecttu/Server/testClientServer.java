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
	public void testSPisNewGame() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		assertFalse(sv.isNewGame());
	}
	
	@Test
	public void SPGetPlayerFromTheTable() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		Player player = new Player("name0");
		sv.putPlayerAtTheTable(player);
		
		assertNotNull(sv.getPlayer("name0"));
	}
	
	@Test
	public void SPRemoveFromTheTable() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		Player player = new Player("name0");
		sv.putPlayerAtTheTable(player);
		
		assertFalse(sv.getUpFromTheTable(sv.getPlayer("name0")));
	}
	
	@Test
	public void resetReadyPlayersToNewGame() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		sv.resetReadyPlayersToNewGame();
		
		assertFalse(sv.isAllReadyToNextRound());
	}
	
	@Test
	public void resetReadyPlayers() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		sv.resetReadyPlayers();
		
		assertFalse(sv.isAllReadyToNextRound());
	}
	
	@Test
	public void ressetAllReadyToNextRound() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		sv.ressetAllReadyToNextRound();
		
		assertFalse(sv.ready());
	}
	
	@Test
	public void isAllReadyToNextRound() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		allPlayersReady(sv);
		
		assertTrue(sv.isAllReadyToNextRound());
	}
	
	@Test
	public void isAllReadyToNewGame() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		allPlayersReady(sv);
		
		assertTrue(sv.isAllReadyToNewGame());
	}
	
	@Test
	public void ready() {
		ServerProcess sv = new ServerProcess(new DBDriver());
		
		allPlayersReady(sv);

		assertTrue(sv.ready());
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
		
//		ob.newGame();
		ServerProcess sv = ob.getStatus();
		
		assertFalse(ob.isMaxConnInCurTable());
	}
	
	public ThreadProcess getThreadProcess() {
		Observer ob = new Observer(new DBDriver());
		ServerProcess sv = ob.getStatus();
		sv.putPlayerAtTheTable(new Player("p0"));
		ThreadProcess tp = new ThreadProcess(sv);
		return tp;
	}
	
	@Test
	public void TPStartGameProcessWithResponseStart() {
		ThreadProcess tp = getThreadProcess();
		
		tp.prepareGameProcess("p1");
		
		assertTrue(tp.startGameProcess("start"));	
	}
	
	@Test
	public void TPStartGameProcessWithNotValidResponse() {
		ThreadProcess tp = getThreadProcess();
		
		tp.prepareGameProcess("p1");
		
		assertFalse(tp.startGameProcess("Start"));	
	}
	
	@Test
	public void TPStartGameProcessWithResponseObserver() {
		ThreadProcess tp = getThreadProcess();
		
		tp.prepareGameProcess("p1");
		
		assertTrue(tp.startGameProcess("observer"));	
	}
	
	@Test
	public void TPPlayerNameReserved() {
		ThreadProcess tp = getThreadProcess();
		
		assertNull(tp.isPlayerNameReserved("p0"));
	}
	
	@Test
	public void TPPlayerNameNotReserved() {
		ThreadProcess tp = getThreadProcess();
		
		assertNotNull(tp.isPlayerNameReserved("p1"));
	}
	
	@Test
	public void startNewGameIfAllNotReady() {
		Observer ob = new Observer(new DBDriver());
		
		assertFalse(ob.newGame());
	}
	
	@Test
	public void startNewGameIfAllReady() {
		Observer ob = new Observer(new DBDriver());
		
		allPlayersReady(ob.getStatus());
		
		assertTrue(ob.newGame());
	}
	
	@Test
	public void testConnection() throws IOException {
//		PokerFrame frame = new PokerFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		new PlayerPanel(new ServerListener("localhost", 8907));
	}

}
