package src.swe.main.ui.Alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertFormat {
	
	public void errorAlert(String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
		return;
	}
	
	public void infoAlert(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
		return;
	}
}
