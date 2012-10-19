package projecttu.GraphicInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PokerClient {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public PokerClient(String name) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		InetAddress addr;
		addr = InetAddress.getByName("localhost" );
		try {
			socket = new Socket(addr, 8880);
		} catch( IOException e ) {
			System.out.println("Sry - server pole nähtav :(((");
			return;
		}
		try {
			System.out.println("socket = " + socket);
			in = new BufferedReader( 
					new InputStreamReader( socket.getInputStream() ));
			PrintWriter out =
					new PrintWriter( new BufferedWriter( 
							new OutputStreamWriter(socket.getOutputStream() )),true);
			BufferedReader stdin = 
					new BufferedReader(
							new InputStreamReader( System.in ));
			out.println("NAME "+name);
			String s = null;
			do {
				s = stdin.readLine();
				String str = in.readLine();
				System.out.println(str+"\n"+s);
			} while(!s.equalsIgnoreCase("QUIT"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("closing...");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	   }
	}
	
	private static BufferedReader in;
	private static Socket socket;
}
