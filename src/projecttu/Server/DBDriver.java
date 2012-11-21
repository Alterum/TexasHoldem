package projecttu.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDriver {
	
	private Connection connection;
	private Statement state;
    private ResultSet set;
	
	DBDriver() {
		try {
			 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");

	}
	
	void connectToDB() throws SQLException {

		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/testh", "joker",
					"holdem76");
 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			state = connection.createStatement();
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public void putInfo(int bet, int status, String name) {
//		try {
//			set = state.executeQuery("SELECT * FROM people");
//			while (set.next()) {
//	            System.out.println(set.getString(3));
//	        }
//			System.out.println(connection.getCatalog());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
	}
	
}
