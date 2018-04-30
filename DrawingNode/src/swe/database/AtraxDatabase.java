package src.swe.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import src.application.MetaData;

public class AtraxDatabase {
	
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String URL = "jdbc:derby:atraxdb";
	public Connection connection =  null;
	
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
	
	// This method populate data to Atrax database
	// todo: insert into tables, keywords, library, etc..
	public boolean insertToDatabase(MetaData metaData)  
	{
		String query = "INSERT INTO DOCUMENTS(filename, title, subject, Created_at, Absolute_path, Library_id) "+ "VALUES (?,?,?,?,?,?)";
		
//		// This is for testing purpose
//	    Calendar calendar = Calendar.getInstance();
//	    java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		// create the mysql insert preparedstatement
		try {
		    PreparedStatement preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setString (1, metaData.getFileName());
		    preparedStmt.setString (2, metaData.getTitle());
		    preparedStmt.setString (3, metaData.getSubject());
		    preparedStmt.setDate(4, (Date) metaData.getPublicationDate()); 
		    preparedStmt.setString(5, metaData.getFilePath());
		    // TODO: insert into library, then query a library id
		    preparedStmt.setInt(6, 1); 
		    
		    // execute the preparedstatement successfully, return 0
		    return !(preparedStmt.execute());
		} catch (SQLException ex) {
			System.out.println("\nDatabase connection Error code: " + ex.getErrorCode());
			return false;
		}		
	}
	
}
