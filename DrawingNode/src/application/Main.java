package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import swe.draw.layout.parent.Layout;
import swe.draw.graph.MapNode;
import swe.draw.graph.NodeType;
import swe.draw.graph.Show;
import swe.draw.layout.example.ExampleLayout;

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

    public static void main(String[] args) {
    	System.out.println("Start main application........");
        launch(args);
    }
}