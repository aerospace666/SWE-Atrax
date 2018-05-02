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
	
	// Add a document into the DOCUMENT table
	public String insertDocToLibrary(String FileName, String Title, String Subject, java.util.Date CreationDate, String filePath, int library_id, String author)  
	{
		System.out.println("\n Entered into insertintoDocLibrary function");
		String updateQuery = "INSERT INTO DOCUMENTS(filename, title, subject, CREATEION_DATE, file_path, library_id, author) VALUES (?,?,?,?,?,?,?)";
		String checkExistance = "SELECT ID FROM DOCUMENTS WHERE LIBRARY_ID=? AND FILE_PATH=? AND FILENAME=?";
	
		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
		
		//Check if the file already exists in the library, if so, don't process it and return the document ID instead
		try {
			//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
			//create the statement
			PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
			preparedStmt.setInt (1, 1);
			preparedStmt.setString (2, filePath);
			preparedStmt.setString (3, FileName);
			// execute the preparedstatement successfully, return 0
			ResultSet rs = preparedStmt.executeQuery();
			int result = 0;
			while (rs.next()) {
				result = rs.getInt("ID");

			}
		   // ResultSet resultset = preparedStmt.getResultSet();
		    if (result == 0) {
		    	//means the file doesn't exist, so process it
				try {
					//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/
					System.out.println("\n means the file doesn't exist, so process it");
					// create the mysql insert preparedstatement
				    PreparedStatement preparedStmt1 = connection.prepareStatement(updateQuery);
				    
				    java.sql.Date sqlDate = new java.sql.Date(CreationDate.getTime());
				    
				    preparedStmt1.setString (1, FileName);
				    preparedStmt1.setString (2, Title);
				    preparedStmt1.setString (3, Subject);
				    preparedStmt1.setDate(4, sqlDate); // nullable
				    preparedStmt1.setString(5, filePath);
				    preparedStmt1.setInt(6, library_id); 
				    preparedStmt1.setString(7, author);
				    
				    // execute the preparedstatement successfully, return 0
				    //TO DO: why is this "not'ed" using ! ? 
				    // convert to string
				    preparedStmt1.executeUpdate();
				    //rs1.insertRow();
				    return "0";
				} catch (SQLException ex) {
					System.out.println("\n Failed to insert into documents table : " + ex.getErrorCode() + ex.getMessage() + ex.getSQLState());
					return "1";
				}
		    }else {
		    	//means it DOES exist, so return the docID
		    	//resultset.getObject(0);
				System.out.println("\n means the file exists, so do not process it!!");
		    	return Integer.toString(result);
		    }
			
		}catch (SQLException ex) {
			System.out.println("\n Failed to select from documents table : " + ex.getErrorCode() + ex.getMessage() + ex.getSQLState());
			return "1";
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
