package projecttu.OutputObject;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;
import projecttu.GraphicInterface.PlayerPanel;
import projecttu.Server.BusinessProcess;
import projecttu.Server.DBDriver;
import projecttu.Server.ServerListener;

public class testOutputServer {

	@Test
	public void ParserInterfaceIsNotNull() {
		Parser parser = new ClientParser();
		
		assertNotNull(parser);
	}
	
	@Test
	public void OutputToServerIsNotNull() {
		OutputObject os = new OutputToServer();
	
		assertNotNull(os);
	}
	
	@Test
	public void OutputToServerGetBet() {
		OutputToServer os = new OutputToServer(1, 1, "Name");
	
		assertEquals(os.getBet(), 1);
	}
	
	@Test
	public void OutputToServerGetPlayerStatus() {
		OutputToServer os = new OutputToServer(1, 1, "Name");
	
		assertEquals(os.getPlayerStatus(), 1);
	}
	
	@Test
	public void OutputToServerToString() {
		OutputToServer os = new OutputToServer(1, 1, "Name");
	
		assertEquals(os.toString(), "Name");
	}
	
	@Test
	public void OutputToClientIsNotNull() {
		OutputObject os = new OutputToClient();
		
		assertNotNull(os);
	}
	
	@Test
	public void OutputToClientInfoIsNotNull() {
		HashMap<String, String> map = new HashMap<String, String>();
		OutputToClient os = new OutputToClient(map);
		
		assertNotNull(os.getInfo());
	}
	
	@Test
	public void OutputToClientAccessIsNotNull() {
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();
		OutputToClient os = new OutputToClient(map, map2);
		
		assertNotNull(os.getAccess());
	}
	
	@Test
	public void OutputToClientGetMessage() {
		OutputToClient os = new OutputToClient("message");
		
		assertEquals(os.toString(), "message");
	}
	
	@Test
	public void OutputObjectToString() {
		OutputObject os = new OutputObject("message");
		
		assertEquals(os.toString(), "message");
	}
	
	@Test
	public void parserInputObject() {
		Parser parser = new ClientParser();
		
		assertNotNull(parser);
	}
	
}
