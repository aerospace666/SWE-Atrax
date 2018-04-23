package swe.main.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainControl implements Initializable {

	@FXML
    private javafx.scene.control.TabPane TabPane;

    @FXML
    private Tab Library;

    @FXML
    private AnchorPane LibraryPane;

    @FXML
    private TreeTableColumn<?, ?> explorer;

    @FXML
    private TableView<Book> librarytable;

    @FXML
    private TableColumn<Book, String> tableid;

    @FXML
    private TableColumn<Book, String> tabletitle;

    @FXML
    private TableColumn<Book, String> tableauthor;

    @FXML
    private TableColumn<Book, String> tabledate;

    @FXML
    private TableColumn<Book, String> tabletype;
    
    @FXML
    private Tab Infotab;

    @FXML
    void FileOpen(ActionEvent event) {
    	
    }
    
    
    //TODO declare book object
    public static class Book {
  
    	private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty date;
 
        
        public Book(String title, String id, String author, String date) {
        	this.title = new SimpleStringProperty(title);
        	this.id = new SimpleStringProperty(id);
        	this.author = new SimpleStringProperty(author);
        	this.date = new SimpleStringProperty(date);
        }
        public String getTitle() {
        	
        	return title.get();
        }
        public String getId() {
        	
        	return id.get();
        }
        public String getAuthor() {
        	
        	return author.get();
        }
        public String getDate() {
	
        	return date.get();
        }
    }
    
    //TODO import bookdata and display
    
    ObservableList<Book> BookList = FXCollections.observableArrayList();
    
    private Stage getStage() {
    	return (Stage) librarytable.getScene().getWindow();
    }
    
    
    public void init() {
    	tableid.setCellValueFactory(new PropertyValueFactory<>("id"));
    	tabletitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	tableauthor.setCellValueFactory(new PropertyValueFactory<>("author"));
    	tabledate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    public void load() {
    	
    	BookList.add(new Book("Title A", "ID 1", "Author Adam", "Date 2000"));
    	
    	
    	librarytable.setItems(BookList);
    	
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		init();
		load();
		
		
	}
    
    
    

}
