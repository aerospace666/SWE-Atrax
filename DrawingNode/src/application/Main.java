package src.application;

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
    public void start(Stage primaryStage) throws IOException, SQLException {
    	
    	boolean uploadButtonClick = true;
    	
    	MetaData Data = new MetaData();
    	

		String Path = "./TestPdf"; //include path here
		
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
    }
}