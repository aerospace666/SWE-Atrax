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
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.swe.database.AtraxDatabase;

public class Main extends Application {
    @Override
<<<<<<< HEAD
    public void start(Stage primaryStage) throws IOException {
=======
    public void start(Stage primaryStage) throws IOException, SQLException {
    	
    	boolean uploadButtonClick = true;
>>>>>>> 6139e348defc4860f4ce0eb6b27b24b54e1ae5c0
    	
    	MetaData Data = new MetaData();
    	

		String Path = "./TestPdf"; //include path here
		
<<<<<<< HEAD
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

=======
		if(uploadButtonClick == true)
		{
			Data.getFilePath(Path,"library"); // get metadata then insert into database
			
		}
		
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
    	
        launch(args);
>>>>>>> 6139e348defc4860f4ce0eb6b27b24b54e1ae5c0
    }
}