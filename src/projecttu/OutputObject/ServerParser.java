package projecttu.OutputObject;

import java.util.HashMap;

import projecttu.Gamelogic.Player;
import projecttu.Gamelogic.PokerTable;


public class ServerParser implements Parser {
	
	public ServerParser(PokerTable table) {
		this.table = table;
	}
	
	private PokerTable table;

	@Override
	public void parserOutput(String query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parserInputObject(OutputObject obj) {
		// TODO Auto-generated method stub
		
	}
}
