package src.swe.main.ui.library;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;


public class LibraryMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("/src/swe/main/ui/library/Library.fxml"));
			Scene scene = new Scene(root,750,500);
			scene.getStylesheets().add(getClass().getResource("/src/swe/main/ui/library/library.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
