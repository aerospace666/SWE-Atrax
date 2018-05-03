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
	private String[] results;

	public AtraxDatabase(){}

	//Create connection to DB
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
			System.out.println("\n Database connection error!! The error code is: " + e.getErrorCode() + "\n The error message is: " +  e.getMessage() + "\n The SQL state is: " + e.getSQLState());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Database connection Error: " + e.toString());
		}
	}

	//Close connection to DB
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
		//System.out.println("\n Entered into insertintoDocLibrary function");
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
			preparedStmt.setInt (1, library_id);
			preparedStmt.setString (2, filePath);
			preparedStmt.setString (3, FileName);
			// execute the preparedstatement
			ResultSet rs = preparedStmt.executeQuery();
			int result = 0;
			//get the result into an int. There will only ever be a single result in the query
			while (rs.next()) {
				result = rs.getInt("ID");

			}
			// ResultSet resultset = preparedStmt.getResultSet();
			if (result == 0) {
				//means the file doesn't exist/nothing was returned above, so process it
				try {
					//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/
					//System.out.println("\n means the file doesn't exist, so process it");
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt1 = connection.prepareStatement(updateQuery);
					//convert java.util date to sql.date 
					java.sql.Date sqlDate = new java.sql.Date(CreationDate.getTime());
					//prepare the statement
					preparedStmt1.setString (1, FileName);
					preparedStmt1.setString (2, Title);
					preparedStmt1.setString (3, Subject);
					preparedStmt1.setDate(4, sqlDate); // nullable
					preparedStmt1.setString(5, filePath);
					preparedStmt1.setInt(6, library_id); 
					preparedStmt1.setString(7, author);

					// execute the preparedstatement
					preparedStmt1.executeUpdate();
					//return 0 meaning success, catch block will catch failure
					return "0";
				} catch (SQLException ex) {
					System.out.println("\n Failed to insert into documents table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
					//return 1 meaning error
					return "1";
				}
			}else {
				//means document DOES exist, so return the docID instead of adding duplicate
				//System.out.println("\n means the file exists, so do not process it!!");
				return Integer.toString(result);
			}

		}catch (SQLException ex) {
			System.out.println("\n Failed to select from documents table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
			//return 1 meaning error
			return "1";
		}		 

	}

	// Add a new library to DB and get the ID back
	public String createNewLibrary(String LibraryName)  

	
	{
		String updateQuery = "INSERT INTO LIBRARIES(LibraryName) VALUES (?)";
		String checkExistance = "SELECT ID FROM LIBRARIES WHERE NAME=?";

		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}

		//Check if the LIBRARY already exists, if so, don't process it and return the LIBRARY ID instead
		try {
			//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
			//create the statement
			PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
			preparedStmt.setString (1, LibraryName);
			// execute the preparedstatement
			ResultSet rs = preparedStmt.executeQuery();
			int result = 0;
			//get the result into an int. There will only ever be a single result in the query
			while (rs.next()) {
				result = rs.getInt("ID");

			}
			if (result == 0) {
				//means the libraray doesn't exist/nothing was returned above, so process it
				try {
					//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/
					//System.out.println("\n means the file doesn't exist, so process it");
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt1 = connection.prepareStatement(updateQuery);
					//prepare the statement
					preparedStmt1.setString (1, LibraryName);

					// execute the preparedstatement
					preparedStmt1.executeUpdate();
					//return 0 meaning success, catch block will catch failure
					return "SUCCESS";
				} catch (SQLException ex) {
					System.out.println("\n Failed to insert into LIBRARY table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
					//return 1 meaning error
					return "ERROR";
				}
			}else {
				//means document DOES exist, so return the docID instead of adding duplicate
				//System.out.println("\n means the file exists, so do not process it!!");
				return Integer.toString(result);
			}

		}catch (SQLException ex) {
			System.out.println("\n Failed to select from LIBRARY table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
			//return 1 meaning error
			return "ERROR";
		}		 

	}

	// get libraryID
	public String getLibraryID(String LibraryName)  

	
	{
		String checkExistance = "SELECT ID FROM LIBRARIES WHERE NAME=?";

		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}

		//Check if the LIBRARY already exists, if so, don't process it and return the LIBRARY ID instead
		try {
			//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
			//create the statement
			PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
			preparedStmt.setString (1, LibraryName);
			// execute the preparedstatement
			ResultSet rs = preparedStmt.executeQuery();
			int result = 0;
			//get the result into an int. There will only ever be a single result in the query
			while (rs.next()) {
				result = rs.getInt("ID");

			}
			if (result == 0) {
				//means the libraray doesn't exist/nothing was returned above, so error it
					return "ERROR";
			}else {
				//means document DOES exist, so return the docID instead of adding duplicate
				//System.out.println("\n means the file exists, so do not process it!!");
				return Integer.toString(result);
			}

		}catch (SQLException ex) {
			System.out.println("\n Failed to select from LIBRARY table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
			//return 1 meaning error
			return "ERROR";
		}		 

	}
	
	// Get array of all libraries in DB
	
	public String[] getAllLibraryNames(){
		
		{
			String checkExistance = "SELECT NAME FROM LIBRARIES";

			// check for database connection
			if(connection == null)
			{
				getDatabaseConnection();
			}

			//get all the library names
			try {
				//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
				//create the statement
				PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
				// execute the preparedstatement
				ResultSet rs = preparedStmt.executeQuery();
				String[] result = new String[100];
				int n = 0;
				//get the result into a string
				while (rs.next()) {
					result[n] = rs.getString("NAME");
					n++;

				}
				System.out.println("the result arrary is: " + result[1]);
				return result;
				
			}catch (SQLException ex) {
				System.out.println("\n Failed to select from LIBRARY table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				//return 1 meaning error
				return new String[] { "ERROR" };
			

		}
		
	}
	}
	
	// Get array of all file data for a single library
	
	public ResultSet getAllLibraryDoc(int libraryID){
		
		{
			String checkExistance = "SELECT ID, FILENAME, TITLE, SUBJECT, CREATEION_DATE, FILE_PATH, AUTHOR FROM DOCUMENTS WHERE LIBRARY_ID=?";

			// check for database connection
			if(connection == null)
			{
				getDatabaseConnection();
			}

			//get all the library names
			try {
				//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
				//create the statement
				PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
				preparedStmt.setInt (1, libraryID);
				// execute the preparedstatement
				ResultSet rs = preparedStmt.executeQuery();
				
				return rs;
				
			}catch (SQLException ex) {
				System.out.println("\n Failed to select from DOC table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				return null;
			

		}
		
	}
	}

	// This method populate data to keywords table

	// This method populate data to document_keyword table


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
