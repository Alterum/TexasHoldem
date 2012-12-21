package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCPostgresqlExample {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
 
		try {
 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 
		Connection connection = null;
		Statement state = null;
        ResultSet set = null;
 
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/holdem", "joker",
					"joker#");
 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			state = connection.createStatement();
			set = state.executeQuery("SELECT * FROM people");
			while (set.next()) {
                System.out.println(set.getString(3));
            }
			System.out.println(connection.getCatalog());
		} else {
			System.out.println("Failed to make connection!");
		}
		
		
	}

}
