package src.application;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.swe.draw.layout.parent.Layout;
import src.swe.database.AtraxDatabase;
import src.swe.draw.graph.MapNode;
import src.swe.draw.graph.NodeType;
import src.swe.draw.graph.Show;
import src.swe.draw.layout.example.ExampleLayout;

public class Main extends Application {

    Show show = new Show();

    @Override
    public void start(Stage primaryStage) {
    	
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
        	mapNode.checkNode("Image-----" + i, NodeType.IMAGE);
        }

        for(int j = 65; j != 68; j++) {
        	mapNode.checkNode( "Label " + (char)j , NodeType.LABEL);
        }
        
       for(int f = 0; f != 3; f++) {
    	   mapNode.checkNode("Rectangle" + f, NodeType.RECTANGLE);
       }
        show.update();
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
    }
}