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
    	
<<<<<<< HEAD
    	MetaData Data = new MetaData();
		
		String Path = "C:/Users/dolly/Downloads/Atraxtestdata/demo";
		
		Data.getFilePath(Path,"library"); //testing purpose
		
    
    	/**
        BorderPane root = new BorderPane();

        show = new Show();

        root.setCenter(show.getScrollPane());

        Scene scene = new Scene(root, 1024, 720);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        addDrawNodes();

        //check example layout for arranging the nodes
        Layout layout = new ExampleLayout(show);
        layout.execute();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addDrawNodes() {

        MapNode mapNode = show.getMap();

        for (int i = 0; i != 9; i++) {
        	mapNode.checkNode("Imagehaha" + i, NodeType.IMAGE);
        }

        for(int j = 65; j != 68; j++) {
        	mapNode.checkNode( "Label " + (char)j , NodeType.LABEL);
        }
        
       for(int f = 0; f != 3; f++) {
    	   mapNode.checkNode("Rectangle" + f, NodeType.RECTANGLE);
       }
        show.update();
=======
    	try {
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("/src/swe/main/ui/library/LibraryUI.fxml"));
			Scene scene = new Scene(root,750,500);
			scene.getStylesheets().add(getClass().getResource("/src/swe/main/ui/library/library.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
>>>>>>> 5b2f6e02ab5fcec147d130e2c53205b4bae5481f
    }
    

    public static void main(String[] args) throws SQLException {
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
    	
        
        launch(args);
        
        if(atraxdb.connection != null)
        {
        	atraxdb.closeDatabaseConnection();
        }
<<<<<<< HEAD
        **/
=======
        
        
>>>>>>> 5b2f6e02ab5fcec147d130e2c53205b4bae5481f
    }
    
}