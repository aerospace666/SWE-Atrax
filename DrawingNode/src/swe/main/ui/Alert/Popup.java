package src.swe.main.ui.Alert;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Popup {
	
	private String CollectionName;

	public String start() {
    	

    	
    	Stage popup = new Stage();

        GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		//Defining the Name text field
		final TextField name = new TextField();
		name.setPromptText("Enter your libary name.");
		name.setPrefColumnCount(10);
		name.getText();
		GridPane.setConstraints(name, 0, 0);
		grid.getChildren().add(name);
		
		//Defining the Submit button
		Button create = new Button("Create");
		GridPane.setConstraints(create, 1, 0);
		grid.getChildren().add(create);
		
		Scene pop = new Scene(grid, 250, 50);
		popup.setScene(pop);
		popup.setTitle("Enter Collection Name");
		
		popup.show();
		
		
		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				System.out.println("----Collection name is: " + name.getText());
				
				if (name.getText().isEmpty()) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("No name");
					alert.setContentText("Please enter Libray name");
					alert.showAndWait();
					
					popup.show(); //recursive to ask library name again
					
					//return;
				
				}
			}
			
			
		});
		//return name.getText();
		return CollectionName;
    }
}
