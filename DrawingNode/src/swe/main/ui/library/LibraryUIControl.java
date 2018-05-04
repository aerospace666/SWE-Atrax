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
import javafx.scene.control.Label;
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

import src.swe.database.AtraxDatabase;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private TableColumn<Book, Date> Date;

    @FXML
    private TableColumn<Book, String> Subject;
    
    @FXML
    private TreeView<Path> FileExplorer;

    @FXML
    private MenuItem AddFolder;
    
    @FXML
    private MenuItem AddFile;
    
    @FXML
    private Label showKeywords;		//label for keywords syntax: showKeywords.setContentDisplay(value)
 
    
    private String Libraryname;  //library name, declare at line 121
    AtraxDatabase dbConn = new AtraxDatabase();
    ObservableList<Book> BookList = FXCollections.observableArrayList();
    
    //TODO passing folder path & library name to metadata function
    @FXML
    void AddFolder(ActionEvent event) {
    	
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
				       
					
						System.out.println(name.getText()); 
						Libraryname = name.getText();//this return libraryname
						popup.close();
				       
						
						
						//TODO call function to reading folder and insert to database
						
						try {
							
							getFilePath(selectedDirectory.getAbsolutePath(),Libraryname);
						
							
							for (Book book: BookList) {
								
								dbConn.insertDocToLibrary(book.getTitle(), book.getTitle(), book.getSubject(), book.getDate(), book.getFilepath(), Integer.parseInt(book.getLibid()), book.getAuthor());
							}
							
							
							
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
						//example:
				        //ReadFolder data = new ReadFolder;
				        // data.getFilePath(selectedDirectory.getAbsolutePath(),name.getText());
				       
						
						//TODO then call load function to load data to table view
				        load();
				        				        
				     }
				 });
			
			
			
			
        }
    }
    
    public void getFilePath(String FilePath,String Library) throws IOException
	{
    	dbConn.createNewLibrary(Library);
    	
    	String libid = dbConn.getLibraryID(Library);
    	//System.out.println(libid);
    	ExtractMetadata extractBook = new ExtractMetadata();
		File path = new File(FilePath);
		int id = 0;
		
		if(path.isDirectory())
		{
			
			File[] ListOfFiles = path.listFiles();
			for(File file : ListOfFiles)
			{	
				if (file.getName().contains(".pdf")) {
					id++;
					BookList.add(extractBook.Extractdata(file, id, libid));
				}
				
			}
		}
		else
		{
			id++;
			BookList.add(new ExtractMetadata().Extractdata(path, id, libid));
		}
	}
    
    
    
    
    
    //TODO initialize table column & Load() import bookdata and display
     
    public void init() {
    	
    	ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	Title.setCellValueFactory(new PropertyValueFactory<>("title"));
    	Author.setCellValueFactory(new PropertyValueFactory<>("author"));
    	Subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
    	Date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	
    }
    
    
    //TODO retrieve data from database, assign to book object then display to table view
    public void load() {
    	
    	//ObservableList<Book> rBook = FXCollections.observableArrayList();
    	BookList.clear();
    	if (dbConn.getLibraryID(Libraryname) != null) {
    		
    		int libid = Integer.parseInt(dbConn.getLibraryID(Libraryname));
    		System.out.println(libid);
    	
    		ResultSet rs = dbConn.getAllLibraryDoc(libid);
    	
    		try {
				while(rs.next()) {
					BookList.add(new Book(rs.getInt("ID"), rs.getString("SUBJECT"), rs.getString("TITLE"), rs.getString("AUTHOR"), rs.getDate("CREATEION_DATE"), rs.getString("FILE_PATH"), libid + ""));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    		}
    	
    	LibraryTable.setItems(BookList);
    }
    
    
    


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		init();
		
		
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
                    String temp = LibraryTable.getSelectionModel().getSelectedItem().getFilepath();
                     desktop.open(new File(temp));
                  } catch (IOException ioe) {
                    ioe.printStackTrace();
                  }
            }
        });
		
		
		
		
	}
    
}
