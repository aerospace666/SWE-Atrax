package src.swe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AtraxDatabase {

	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String URL = "jdbc:derby:atraxdb";
	public Connection connection =  null;

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
	public String insertDocToLibrary(String FileName, String Title, String Subject, java.util.Date CreationDate, String filePath, int library_id, String author, String keywords)  // add keywords
	{
		//System.out.println("\n Entered into insertintoDocLibrary function");
		
		/**
		String updateQuery = "INSERT INTO DOCUMENTS(filename, title, subject, CREATION_DATE, file_path, library_id, author) VALUES (?,?,?,?,?,?,?)";
		String checkExistance = "SELECT ID FROM DOCUMENTS WHERE LIBRARY_ID=? AND FILE_PATH=? AND FILENAME=?";
		*/
		
		//Quick fix for keywords-> change to table document_temp
		String updateQuery = "INSERT INTO DOCUMENT_TEMP(filename, title, subject, CREATION_DATE, file_path, library_id, author, keywords) VALUES (?,?,?,?,?,?,?,?)";
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
					
					//add keywords for document_temp
					preparedStmt1.setString(8, keywords);

					// execute the preparedstatement
					preparedStmt1.executeUpdate();
					
					//if successful, fetch the docID
					ResultSet rs1 = preparedStmt.executeQuery();
					int result1 = 0;
					//get the result into an int. There will only ever be a single result in the query
					while (rs1.next()) {
						result1 = rs1.getInt("ID");

					}	
					return Integer.toString(result1);
					
					} catch (SQLException ex) {
					System.out.println("\n Failed to insert into documents table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
					//return ERROR meaning error
					return "ERROR";
				}
			}else {
				//means document DOES exist, so return the docID instead of adding duplicate
				//System.out.println("\n means the file exists, so do not process it!!");
				return Integer.toString(result);
			}

		}catch (SQLException ex) {
			System.out.println("\n Failed to select from documents table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
			//return ERROR meaning error
			return "ERROR";
		}		 

	}

	// Add a new library to DB and get the ID back
	public String createNewLibrary(String LibraryName)  

	
	{
		String updateQuery = "INSERT INTO LIBRARIES(Name) VALUES (?)";
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
	
	public List<String> getAllLibraryNames(){
		
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
				List<String> result = new ArrayList<String>();
				//int n = 0;
				//get the result into a string
				while (rs.next()) {
					result.add(rs.getString("NAME"));
					//System.out.println("the result is: " + result.get(n));
					//n++;

				}
				//System.out.println("the result arrary is: " + result[0]);
				return result;
				
			}catch (SQLException ex) {
				System.out.println("\n Failed to select from LIBRARY table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				//return 1 meaning error
				return new ArrayList<String>();
			

		}
		
	}
	}
	
	// Get array of all file data for a single library
	
	public ResultSet getAllLibraryDoc(int libraryID){
		
		{
			//String checkExistance = "SELECT ID, FILENAME, TITLE, SUBJECT, CREATION_DATE, FILE_PATH, AUTHOR FROM DOCUMENTS WHERE LIBRARY_ID=?";
			
			//table Document_temp
			String checkExistance = "SELECT ID, FILENAME, TITLE, SUBJECT, CREATION_DATE, FILE_PATH, AUTHOR, KEYWORDS FROM DOCUMENT_TEMP WHERE LIBRARY_ID=?";
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
	
	public String insertKeywordtoKeywordsTable(List<String> keywords)  
	{
		String updateQuery = "INSERT INTO KEYWORDS(KEYWORD) VALUES (?)";
		String checkExistance = "SELECT ID FROM KEYWORDS WHERE KEYWORD=?";

		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
		//for each KW in the list, let's process it
		for (String tempKW : keywords) {
			//Check if the keyword already exists in the DB, if so, don't process it and return the keyword ID instead
			try {
				//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
				//create the statement
				PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
				preparedStmt.setString (1, tempKW);
				// execute the preparedstatement
				ResultSet rs = preparedStmt.executeQuery();
				int result = 0;
				//get the result into an int. There will only ever be a single result in the query
				while (rs.next()) {
					result = rs.getInt("ID");
	
				}
				// ResultSet resultset = preparedStmt.getResultSet();
				if (result == 0) {
					//means the KW doesn't exist/nothing was returned above, so process it
					try {
						//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/
						//System.out.println("\n means the file doesn't exist, so process it");
						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt1 = connection.prepareStatement(updateQuery);
						//prepare the statement
						preparedStmt1.setString (1, tempKW);	
						// execute the preparedstatement
						preparedStmt1.executeUpdate();
						//return 0 meaning success, catch block will catch failure
						return "0";
					} catch (SQLException ex) {
						System.out.println("\n Failed to insert into KEYWORDS table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
						//return 1 meaning error
						return "1";
					}
				}else {
					//means KW DOES exist, so return the docID instead of adding duplicate
					//System.out.println("\n means the file exists, so do not process it!!");
					return Integer.toString(result);
				}
	
			}catch (SQLException ex) {
				System.out.println("\n Failed to select from KEYWORDS table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				//return 1 meaning error
				return "1";
			}		
		}
		//if it ever gets to here, means the list was empty
		System.out.println("\n Failed to run function insertKeyword...most likely the list was empty");
		return "1";

	}

	//Get the keyword ID from Keywords table

	public String getKeywordID(String keyword)  
	{
		String checkExistance = "SELECT ID FROM KEYWORDS WHERE KEYWORD=?";

		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
			//Check if the keyword already exists in the DB, if so, don't process it and return the keyword ID instead
			try {
				//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
				//create the statement
				PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
				preparedStmt.setString (1, keyword);
				// execute the preparedstatement
				ResultSet rs = preparedStmt.executeQuery();
				int result = 0;
				//get the result into an int. There will only ever be a single result in the query
				while (rs.next()) {
					result = rs.getInt("ID");
	
				}
				// ResultSet resultset = preparedStmt.getResultSet();
				if (result == 0) {
					//means the KW doesn't exist/nothing was returned above
						return "1";
					}
				else {
					//means KW DOES exist, so return the KWID
					return Integer.toString(result);
				}
	
			}catch (SQLException ex) {
				System.out.println("\n Failed to select from KEYWORDS table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				//return 1 meaning error
				return "1";
			}		


	}
	
	// This method populate data to document_keyword table

	public String insertIntoDocKeywordTable(int docID, int keywordID, int keywordOccurence)  
	{
		String updateQuery = "INSERT INTO DOCUMENT_KEYWORD(DOC_ID,KEYWORD_ID,OCCURRENCE) VALUES (?,?,?)";

		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}

			try {
				//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/
				//System.out.println("\n means the file doesn't exist, so process it");
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt1 = connection.prepareStatement(updateQuery);
				//prepare the statement
				preparedStmt1.setInt (1, docID);	
				preparedStmt1.setInt (2, keywordID);	
				preparedStmt1.setInt (3, keywordOccurence);	

				// execute the preparedstatement
				preparedStmt1.executeUpdate();
				//return 0 meaning success, catch block will catch failure
				return "0";
			} catch (SQLException ex) {
				System.out.println("\n Failed to insert into DOCUMENT_KEYWORD table!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				//return 1 meaning error
				return "1";
			}

		}

	//get keywords for a docID
	
	public ResultSet getKeywordsforDoc(int docID)  
	{
		String checkExistance = "SELECT KEYWORD FROM KEYWORDS KW LEFT JOIN DOCUMENT_KEYWORD DKW on DKW.KEYWORD_ID=KW.ID WHERE DKW.DOC_ID=?";

		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
			try {
				//https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
				//create the statement
				PreparedStatement preparedStmt = connection.prepareStatement(checkExistance);
				preparedStmt.setInt (1, docID);
				// execute the preparedstatement
				ResultSet rs = preparedStmt.executeQuery();
				//return the result
				return rs;

	
			}catch (SQLException ex) {
				System.out.println("\n Failed to select from KEYWORDS/DOC_KEYWORDS table(s)!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
				//return 1 meaning error
				return null;
			}		


	}
	
	/*
	 * this method retrieve relevant documents based on user input keyword
	 * @param user input word
	 * return information of documents
	 */
	public ResultSet getRelavantDocument(String inputWord)
	{
		// check for database connection
		if(connection == null)
		{
			getDatabaseConnection();
		}
		// implementation of regular expression 
		String relevantDocQuery = "SELECT DOCS.ID, FILENAME, TITLE, SUBJECT, CREATION_DATE, FILE_PATH, AUTHOR FROM DOCUMENTS DOCS " +
								  "LEFT JOIN DOCUMENT_KEYWORD DKW ON DOCS.ID = DKW.DOC_ID " +
								  "LEFT JOIN KEYWORDS KW ON DKW.KEYWORD_ID = KW.ID " + 
								  "WHERE KW.KEYWORD LIKE " + "'" + inputWord + "%" + "'";
		try {
			//create the statement
			PreparedStatement preparedStmt = connection.prepareStatement(relevantDocQuery);
			// execute the statement
			ResultSet rs = preparedStmt.executeQuery();
			
			return rs;
			
		}catch (SQLException ex) {
			System.out.println("\n Failed to select relevant DOC from user search!! The error code is: " + ex.getErrorCode() + "\n The error message is: " +  ex.getMessage() + "\n The SQL state is: " + ex.getSQLState());
			return null;
		}
	}
	
	/*
	 * this method delete single documents from the library
	 * @param document id
	 * return 0 for success, -1 for any error
	 */
	public int deleteDocument(int doc_id)
	{
		if(connection == null)
		{
			getDatabaseConnection();
		}
		// delete from documents and document_keyword table
		String deleteDocument = "DELETE DOCUMENTS, DOCUMENT_KEYWORD " +
								"FROM DOCUMENTS d " +
								"INNER JOIN DOCUMENT_KEYWORD dkw ON d.id = dkw.doc_id " +
								"WHERE d.id = " + doc_id;
		try {
			PreparedStatement preparedStmt = connection.prepareStatement(deleteDocument);
			if(preparedStmt.execute() == true) 
			{
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return -1;
	}
	
	/*
	 * this method delete documents from the library
	 * @param library id
	 * return 0 for success, -1 for any error
	 */
	public int deleteLibrary(int library_id)
	{
		if(connection == null)
		{
			getDatabaseConnection();
		}
		// delete from documents and library table
		String deleteLibrary =  "DELETE DOCUMENTS, LIBRARIES " +
								"FROM DOCUMENTS d " +
								"INNER JOIN LIBRARIES l ON d.library_id = l.id " +
								"WHERE l.id = " + library_id;
		try {
			PreparedStatement preparedStmt = connection.prepareStatement(deleteLibrary);
			if(preparedStmt.execute() == true) 
			{
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return -1;
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
