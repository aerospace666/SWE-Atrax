package swe.main.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class mainUi extends Application {
	@Override
    public void start(Stage stage) throws Exception {
        TabPane root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
