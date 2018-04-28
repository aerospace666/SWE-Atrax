package src.swe.main.ui.library;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.fxmisc.livedirs.LiveDirs;


import java.nio.file.Path;
import java.nio.file.Paths;


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
    
   
    //TODO import bookdata and display
    
    ObservableList<Book> BookList = FXCollections.observableArrayList();
    
 
    
    
    public void init() {
    	ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	Title.setCellValueFactory(new PropertyValueFactory<>("title"));
    	Author.setCellValueFactory(new PropertyValueFactory<>("author"));
    	Date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
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
		
		try {
			LiveDirs<ChangeSource> Dirs = new LiveDirs<>(ChangeSource.EXTERNAL);
			
			Path dir = Paths.get("/Users/conghienhoang/Documents/GitHub/SWE-Atrax/DrawingNode");
			Dirs.addTopLevelDirectory(dir);

			// use LiveDirs as a TreeView model
			
			FileExplorer.setShowRoot(false);
			FileExplorer.setRoot(Dirs.model().getRoot());

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
		
		
	}
    
}
