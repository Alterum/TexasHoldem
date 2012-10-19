package projecttu.Server;
import java.util.ArrayList;
import java.util.Iterator;


public class ActiveConnections {
	ArrayList<Connection> arr = new ArrayList<Connection>();

	public void addConnection(Connection member) {
		arr.add(member);
	}
	
	public synchronized Iterator<Connection> iterator() {
		return arr.iterator();
	}
	public synchronized boolean isConnection(String name) {
		for(Connection con : arr)
			if(con.getName().equals(name))
				return true;
		return false;
	}
	
//	public synchronized void removeConnection(String name) {
//		Iterator<Connection> itr = arr.iterator();
//		while(itr.hasNext()) {
//			if((itr.next()).getName().equals(name)) {
//				itr.remove();
//				break;
//			}
//		}
//	}
}
