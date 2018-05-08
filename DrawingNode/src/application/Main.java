package src.application;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.swe.database.AtraxDatabase;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
    	
    	MetaData Data = new MetaData();
		
		String Path = "C:/Users/dolly/Downloads/Atraxtestdata/demo";
		
		Data.getFilePath(Path,"library"); //testing purpose

    	
//    	AtraxDatabase atraxdb = new AtraxDatabase();
//    	
//    	atraxdb.getDatabaseConnection();
//    	
//    	Statement statement = atraxdb.connection.createStatement();
//    	ResultSet resultSet = statement.executeQuery("SELECT * FROM documents");
//        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//        System.out.println("Retreive data from table----");
//        
//        int colCount = resultSetMetaData.getColumnCount();
//        for(int x=1; x <= colCount; x++)
//        {
//        	System.out.format("%20s", resultSetMetaData.getColumnName(x) + " | ");
//        }
//        System.out.println("\n");
//        while(resultSet.next())
//        {
//        	for(int x=1; x <= colCount; x++)
//        	{
//        		System.out.format("%20s", resultSet.getString(x) + " | ");
//        	}
//        	System.out.println("\n");
//        }
   
        
//        if(atraxdb.connection != null)
//        {
//        	atraxdb.closeDatabaseConnection();
//        }

    }
    
}