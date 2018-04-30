package src.swe.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	// This method populate data to documents table
	public boolean insertToDocument(String FileName, String Title, String Subject, Date CreationDate, 
									String filePath, int library_id, String author)  
	{
		String documet_query = "INSERT INTO DOCUMENTS(filename, title, subject, creation_date, file_path, library_id, author) "
								+ "VALUES (?,?,?,?,?,?,?)";
	
		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}

		try {
			// create the mysql insert preparedstatement
		    PreparedStatement preparedStmt = connection.prepareStatement(documet_query);
		    
		    preparedStmt.setString (1, FileName);
		    preparedStmt.setString (2, Title);
		    preparedStmt.setString (3, Subject);
		    preparedStmt.setDate(4, (Date)CreationDate); // nullable
		    preparedStmt.setString(5, filePath);
		    preparedStmt.setInt(6, library_id); 
		    preparedStmt.setString(7, author);
		    
		    // execute the preparedstatement successfully, return 0
		    return !(preparedStmt.execute());
		} catch (SQLException ex) {
			System.out.println("\n Failed to insert into documents table : " + ex.getErrorCode());
			return false;
		}		
	}
	
	// This method populate data to keywords table
	public boolean insertToKeyword(String keyword)  
	{
//		String documet_query = "INSERT INTO DOCUMENTS(filename, title, subject, Created_at, Absolute_path, Library_id) "
//								+ "VALUES (?,?,?,?,?,?)";
		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
		return false;
	}
	
	// This method populate data to document_keyword table
	public boolean insertToDoc_Keyword(int document_id, int keyword_id, int occurrence)  
	{
//			String documet_query = "INSERT INTO DOCUMENTS(filename, title, subject, Created_at, Absolute_path, Library_id) "
//									+ "VALUES (?,?,?,?,?,?)";
		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
		return false;
	}
	
	// This method retrieve metadata for display
	public boolean showDocMetadata(int document_id)  
	{
//		String documet_query = "INSERT INTO DOCUMENTS(filename, title, subject, Created_at, Absolute_path, Library_id) "
//											+ "VALUES (?,?,?,?,?,?)";
		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
		
		return false;
	}
	
	// this method is for testing the query
	public void testQuery() throws SQLException
	{
		if(connection == null)
		{
			getDatabaseConnection();
		}
		Statement statement = connection.createStatement();
    	ResultSet resultSet = statement.executeQuery("SELECT * FROM documents");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        System.out.println("Retreive data from table----");
        
        int colCount = resultSetMetaData.getColumnCount();
        for(int x=1; x <= colCount; x++)
        {
        	System.out.format("%20s", resultSetMetaData.getColumnName(x) + " | ");
        }
        System.out.println("\n");
        while(resultSet.next())
        {
        	for(int x=1; x <= colCount; x++)
        	{
        		System.out.format("%20s", resultSet.getString(x) + " | ");
        	}
        	System.out.println("\n");
        }
	}
}
