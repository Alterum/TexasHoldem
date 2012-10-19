package projecttu.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class PokerServer {
	private static final int port = 8880;
	
	public static void main(String[] args) {
		try
		{
			ServerSocket s = new ServerSocket(port);
			try
			{
				new PokerGame(s);
			}
			finally
			{
				System.out.println( "Server exit!" );
				s.close();
			}															
		}
		catch (IOException e)
		{
			System.out.println( "Server closed!" );
			e.printStackTrace();
		}
}
}
