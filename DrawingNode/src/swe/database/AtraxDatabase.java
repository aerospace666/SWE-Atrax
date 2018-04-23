package src.swe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AtraxDatabase {
	
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String URL = "jdbc:derby:atraxdb;create=true";
	private Connection connection =  null;
	
	public AtraxDatabase(){}
	
	public void getDatabaseConnection()
	{
		try 
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(URL);
			System.out.println("Start database connection....");
		}
		catch (SQLException e)
		{
			System.out.println("Database connection Error code: " + e.getErrorCode());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Database connection Error: " + e.toString());
		}
	}
	
	public void closeDatabaseConnection() 
	{
		try {
			connection.close();
			System.out.println("Close database connection.");
		} catch (SQLException e) {
			System.out.println("Close Database connection Error code: " + e.getErrorCode());
		}
	}
}
