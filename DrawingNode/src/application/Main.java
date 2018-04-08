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

        

        mapNode.checkNode("Image1", NodeType.IMAGE);
        mapNode.checkNode("PImage1", NodeType.RECTANGLE);
        mapNode.checkNode("Button1", NodeType.BUTTON);
        
        mapNode.checkNode("Image2", NodeType.IMAGE);
        mapNode.checkNode("PImage2", NodeType.RECTANGLE);
        mapNode.checkNode("Button2", NodeType.BUTTON);

        
        // bind line (add connection)
        mapNode.addBindLine("PImage1", "PImage2");
        
        
       
       
        show.update();

    }

    public static void main(String[] args) {
        launch(args);
    }
}