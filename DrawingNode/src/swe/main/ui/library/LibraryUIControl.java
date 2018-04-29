package src.swe.main.ui.library;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class LibraryUIControl implements Initializable{

    @FXML
    private TableView<Book> LibraryTable;

    @FXML
    private TableColumn<Book, String> ID;

    @FXML
    private TableColumn<Book, String> Title;

    @FXML
    private TableColumn<Book, String> Author;

    @FXML
    private TableColumn<Book, String> Date;

    @FXML
    private TreeView<Path> FileExplorer;

    @FXML
    private MenuItem OpenFolderId;
    
    
    //TODO passing folder path & library name 
    @FXML
    void OpenFolder(ActionEvent event) {
    	
    	//Open Folder dialog
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	File selectedDirectory = directoryChooser.showDialog(LibraryTable.getScene().getWindow());
         
        if(selectedDirectory == null){
            System.out.println("No Directory selected");
        }else{
        	
            System.out.println(selectedDirectory.getAbsolutePath());   //this return folder path
            
            
            //Creating a pop up to get Library name
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
			popup.setTitle("Enter Libary Name");
			popup.show();
			
			
			create.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				    public void handle(ActionEvent e) {
				       
					
						System.out.println(name.getText());   //this return libraryname
						popup.close();
				       
						
						//TODO call function to reading folder and insert to database
						
						
						//example:
				        //ReadFolder data = new ReadFolder;
				        // data.getFilePath(selectedDirectory.getAbsolutePath(),name.getText());
				       
						
						//TODO then call load funtion to load data to table view
				        // load();
				        				        
				     }
				 });
			
			
			
			
        }
    }
    
    
    
    
    
    //TODO import bookdata and display
    
    ObservableList<Book> BookList = FXCollections.observableArrayList();
    
 
    
    
    public void init() {
    	ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	Title.setCellValueFactory(new PropertyValueFactory<>("title"));
    	Author.setCellValueFactory(new PropertyValueFactory<>("author"));
    	Date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    
    //TODO retrieve data from database, asign to book object then display to table view
    public void load() {
    	//this is only example
    	
    	//need to use pdfbox code to load all book
    	
    	BookList.add(new Book("A", "1", "Adam", "2000", "/TestPdf/PROJECT PLAN copy.pdf"));
    	BookList.add(new Book("B", "2", "David", "2000", "/TestPdf/PROJECT PLAN copy.pdf"));
    	
    	
    	LibraryTable.setItems(BookList);
    	
    }
    
    
    


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		init();
		load();
		
		//add open file on double click, required: file resources path
		LibraryTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                System.out.println(LibraryTable.getSelectionModel().getSelectedItem().getFilepath());
                try {
                    Desktop desktop = null;
                    if (Desktop.isDesktopSupported()) {
                      desktop = Desktop.getDesktop();
                    }
                    
                    //fixed unrecognized resources path
                    String temp = "." + LibraryTable.getSelectionModel().getSelectedItem().getFilepath();
                     desktop.open(new File(temp));
                  } catch (IOException ioe) {
                    ioe.printStackTrace();
                  }
            }
        });
		
		
		
		
	}
    
}
