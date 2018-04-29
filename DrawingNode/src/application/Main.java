package src.application;

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

    	
//    	MetaData Data = new MetaData();
//		
//		String Path = ""; //include path here
//		
//		Data.getFilePath(Path,"library"); //testing purpose
		

    
    	try {
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("/src/swe/main/ui/library/LibraryUI.fxml"));
			Scene scene = new Scene(root,750,500);
			scene.getStylesheets().add(getClass().getResource("/src/swe/main/ui/library/library.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
    }
    

    public static void main(String[] args) throws IOException, SQLException {
    	System.out.println("Start main application........");
    	
    	AtraxDatabase atraxdb = new AtraxDatabase();
    	
    	atraxdb.getDatabaseConnection();
    	
    	Statement statement = atraxdb.connection.createStatement();
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
    	
    	
<<<<<<< HEAD
=======
    	System.out.println("- Keywords: " + Data.getKeywords());
    	System.out.println("- Author: " + Data.getAuthor());
    	System.out.println("- Name: " + Data.getFileNamet());
    	System.out.println("- Subject:  " + Data.getSubject());
    	System.out.println("- Title: " + Data.getTitle());
>>>>>>> d7cbe982157fc9c73290768d677087c5952112b8
    	
        launch(args);
        

        if(atraxdb.connection != null)
        {
        	atraxdb.closeDatabaseConnection();
        }

    }
    
}