package src.swe.main.ui.library;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.swe.database.AtraxDatabase;
import src.swe.main.ui.Alert.AlertFormat;
import src.swe.main.ui.Alert.WorkIndicatorDialog;;
public class LibraryUIControl implements Initializable{

	@FXML
    private StackPane MainID;
		
	@FXML
    private BorderPane LibraryUI;
	
    @FXML
    private TableView<Book> LibraryTable;

    @FXML
    private Tab LibraryTab;
    
    @FXML
    private Tab MindmapTab;

    @FXML
    private TabPane tabpane;
    
    @FXML
    private TableColumn<Book, String> ID;

    @FXML
    private TableColumn<Book, String> Title;

    @FXML
    private TableColumn<Book, String> Author;

    @FXML
    private TableColumn<Book, Date> Date;

    @FXML
    private TableColumn<Book, String> Name;
    
    
    @FXML
    private TableView<String> CollectionTable;

    @FXML
    private TableColumn<String, String> CollectionColumn;

    @FXML
    private Menu FileMenu;
    
    @FXML
    private MenuItem AddFolderMenu;
    
    @FXML
    private MenuItem AddFileMenu;
    
    @FXML
    private ContextMenu CollectionContextMenu;
    
    @FXML
    private Label infoLabels; 		//labels for info tab -> use to set css themes
    
    @FXML
    private Label showKeywords;		//label for keywords, syntax: showKeywords.setText(book.getKeywords())
 
    @FXML
    private Label showSubject;		//label for subject, syntax: showKeywords.setText(book.getSubject())


    @FXML
    private Label showTitle;		//label for title, syntax: showTitle.setText(book.getTitle())
    
    
    
    private String Libraryname;  //library name
    AtraxDatabase dbConn = new AtraxDatabase();
    
    ObservableList<Book> BookList = FXCollections.observableArrayList(); //Book List for LibaryTable
    ObservableList<String> LibraryList = FXCollections.observableArrayList(); //LibrayList for Collection table
    
    AlertFormat alert = new AlertFormat();
    
    private WorkIndicatorDialog<String> wd=null;	// progress indicator object. import from main.ui.Alert.WorkIndicatorDialog
    
    
    // Implement in menu item: file, Clear collection select when add new collection
    @FXML
    void ClearCollectionSelection(ActionEvent event) {
    	CollectionTable.getSelectionModel().clearSelection();
    }
    
    
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
			popup.setTitle("Enter Collection Name");
			
			//check if it from collection context menu
			if (CollectionTable.getSelectionModel().getSelectedItem() == null)
			{
				popup.show();
				
			} else {
				
				
				// progress indicator wrap the code
				wd = new WorkIndicatorDialog<String>(LibraryTable.getScene().getWindow(), "Generating Files's metadata...");
				 
			    wd.addTaskEndNotification(result -> {
			    	
			    	//debug log
			    	System.out.println("-Line " + Thread.currentThread().getStackTrace()[1].getLineNumber() + ": " + result);
			    	
			    	wd=null; // don't keep the object, cleanup
			    	
			    	//if return 0, means no pdf file in the folder
			    	if (result == 0) {
			   			alert.errorAlert("Unsupported file type", "Please choose only pdf file");
						return;
			    	}
			    });
			 
			    wd.exec("123", inputParam -> {
			    	
			    	
			    	//actual code
			    	//start process with libraryname equal the collection selection 
			    	
			    	int check = 0;
					try {
						Libraryname = CollectionTable.getSelectionModel().getSelectedItem();
						
						//extract data
						//if no pdf file in the folder then getfilepath return 0 for error alert
						 check = getFilePath(selectedDirectory.getAbsolutePath(),Libraryname);
						
					
						//insert generated book to database
						for (Book book: BookList) {
							
							int docId = Integer.parseInt(dbConn.insertDocToLibrary(book.getName(), book.getTitle(), book.getSubject(), book.getDate(), book.getFilepath(), Integer.parseInt(book.getLibid()), book.getAuthor(), book.getKeywords()));
			    			
							//0 mean existed in database
							if(docId != 0)
			    			{	
							dbConn.insertKeywordtoKeywordsTable(book.getKeywordlist());
							for (String tempString : book.getKeywordlist()) {
								for (int TempOccur : book.getKeyOcurrences().keySet()) {
									if(book.getKeyOcurrences().get(TempOccur).equals(tempString)) {
										int keyId = Integer.parseInt(dbConn.getKeywordID(tempString));
										dbConn.insertIntoDocKeywordTable(docId, keyId, TempOccur);
									}
								}
							}
			    			}
						}
						
						
						
					} catch (IOException e1) {
						
						e1.printStackTrace();
					} 
					
					//example:
			        //ReadFolder data = new ReadFolder;
			        // data.getFilePath(selectedDirectory.getAbsolutePath(),name.getText());
			       
					
					//TODO then call load function to load data to table view
			        load(Libraryname);
			       
			       return check;
			    });	//end wrap
				
		        				        
			}
			
			
			//If not select from a collection then pop up create library name
			create.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				    public void handle(ActionEvent e) {
				       
						
						System.out.println(name.getText()); 
						Libraryname = name.getText();//this return libraryname
						popup.close();
						
						if (name.getText().isEmpty()) {
							
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("No name");
							alert.setContentText("Please enter Libray name");
							alert.showAndWait();
							
							popup.show();			//recursive to ask library name again
							return;
							
						}
				       
						
						// progress indicator wrap the code
						wd = new WorkIndicatorDialog<String>(LibraryTable.getScene().getWindow(), "Generating Files's metadata...");
						 
					    wd.addTaskEndNotification(result -> {
					    	//debug log
					    	System.out.println("-Line " + Thread.currentThread().getStackTrace()[1].getLineNumber() + ": " + result);
					    	wd=null; // don't keep the object, cleanup
					    	
					    	//if return 0, means no pdf file in the folder
					    	if (result == 0) {
					   			alert.errorAlert("Unsupported file type", "Please choose only pdf file");
								return;
					    	}
					    });
					 
					    wd.exec("123", inputParam -> {
					    	
					    	
					    	//actual code
					    	//TODO call function to reading folder and insert to database
					    	
					    	int check = 0;
					    	try {
					    		//if no pdf file in the folder then getfilepath return 0 for error alert
					    		
					    		check = getFilePath(selectedDirectory.getAbsolutePath(),Libraryname);
						
					    		
								
								
					    		for (Book book: BookList) {
								
					    			int docId = Integer.parseInt(dbConn.insertDocToLibrary(book.getName(), book.getTitle(), book.getSubject(), book.getDate(), book.getFilepath(), Integer.parseInt(book.getLibid()), book.getAuthor(), book.getKeywords()));
					    			
					    			//0 mean existed in database
					    			if(docId != 0)
					    			{
									dbConn.insertKeywordtoKeywordsTable(book.getKeywordlist());
									for (String tempString : book.getKeywordlist()) {
										for (int TempOccur : book.getKeyOcurrences().keySet()) {
											if(book.getKeyOcurrences().get(TempOccur).equals(tempString)) {
												int keyId = Integer.parseInt(dbConn.getKeywordID(tempString));
												dbConn.insertIntoDocKeywordTable(docId, keyId, TempOccur);
											}
										}
									}
					    			}
					    		}
							
							
							
					    	} catch (IOException e1) {
							
					    		e1.printStackTrace();
					    	}
			
						
					    	//example:
					    	//ReadFolder data = new ReadFolder;
					    	// data.getFilePath(selectedDirectory.getAbsolutePath(),name.getText());
				       
						
					    	//TODO load new collection name & update library table
				       
					    	
					    	
					    	
					    	
					    	return check;
					    }); // end wrap
					    
					    LibraryList.add(Libraryname);
				    	CollectionTable.setItems(LibraryList);
				    	CollectionTable.getSelectionModel().selectLast();
					    load(Libraryname);
				     }
				 });
			
        	}
    }
    
    
    //addfile option in collection context menu
    @FXML
    void AddFile(ActionEvent event) {
    	
    	//if select from empty collection then return
    	if (CollectionTable.getSelectionModel().getSelectedItem() == null) {
    		alert.errorAlert("No Collection selecetd", "Please choose a collection first");
    		return;
    	}
    	
    	//empty previous bookdata
    	BookList.clear();
    	
    	//assign libraryname to selected collection then open multiple file chooser
    	Libraryname = CollectionTable.getSelectionModel().getSelectedItem();
    	FileChooser fileChooser = new FileChooser();
    	//enable to select multiples file
    	List<File> selectedfile = fileChooser.showOpenMultipleDialog(LibraryTable.getScene().getWindow());
    	Libraryname = CollectionTable.getSelectionModel().getSelectedItem();
    	
    	//if there is no files selected then return
    	if(selectedfile == null || selectedfile.isEmpty()) {
    		return;
    	}
    	
    	
    	//progress indicator wrap the code
    	wd = new WorkIndicatorDialog<String>(LibraryTable.getScene().getWindow(), "Generating Files's metadata...");
		 
	    wd.addTaskEndNotification(result -> {
	    	wd=null; // don't keep the object, cleanup
	    	//debug log
	    	System.out.println("-Line " + Thread.currentThread().getStackTrace()[1].getLineNumber() + ": " + result);
	    	//result return = counter ->check 353 for counter declaration
	    	//if no pdf file, alert error and return
	    	if (result == 0) {
	    			
	   			alert.errorAlert("Unsupported file type", "Please choose only pdf file");
				return;
	    	}
	    	
	    });
	 
	    wd.exec("123", inputParam -> {
	    	
	    //actual code	
	    	
    	int counter = 0; //counter check for non pdf
    	
    	//extract each file data and insert into database
    	for (File tempFile : selectedfile) {
    		
    		if (tempFile.getName().contains(".pdf")) {
    			counter++;
    			
    			try {
    				getFilePath(tempFile.getAbsolutePath(), Libraryname);
			
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			//insert book data into database
    	    	for (Book book: BookList) {
    				
    	    		int docId = Integer.parseInt(dbConn.insertDocToLibrary(book.getName(), book.getTitle(), book.getSubject(), book.getDate(), book.getFilepath(), Integer.parseInt(book.getLibid()), book.getAuthor(), book.getKeywords()));
	    			//0 mean existed in database
    	    		if(docId != 0)
	    			{
					dbConn.insertKeywordtoKeywordsTable(book.getKeywordlist());
					for (String tempString : book.getKeywordlist()) {
						for (int TempOccur : book.getKeyOcurrences().keySet()) {
							if(book.getKeyOcurrences().get(TempOccur).equals(tempString)) {
								int keyId = Integer.parseInt(dbConn.getKeywordID(tempString));
								dbConn.insertIntoDocKeywordTable(docId, keyId, TempOccur);
							}
						}
					}
	    			}
    			}
    			
    		}
    	}
    		
    	//if no pdf file, alert error and return
    	if (counter == 0) {
    			
   			//alert.errorAlert("Unsupported file type", "Please choose only pdf file");
			return counter;
    	}
    		
    	
    	
    	//refresh library table 
    	load(Libraryname);
    	return 1;
    	}); //end warp
	    
	    
    }
    
    
    //import file path and library name to extract file data
    public int getFilePath(String FilePath,String Library) throws IOException
	{
    	//empty previous book list
    	BookList.clear();
    	
    	//create new collection _ if already exist then return libraryid
    	dbConn.createNewLibrary(Library);
    	
    	String libid = dbConn.getLibraryID(Library);
		//System.out.println(libid);
    	
    	//extract data from ExtractMetadata.java
    	ExtractMetadata extractBook = new ExtractMetadata();
		File path = new File(FilePath);
		int id = 0; 		//counter for how many book
		
		if(path.isDirectory())		//if a folder then put in loop
		{
			
			File[] ListOfFiles = path.listFiles();
			for(File file : ListOfFiles)
			{	
				if (file.getName().contains(".pdf")) {
					id++;
					BookList.add(extractBook.Extractdata(file, id, libid));	//extractdata return a book identified with id and libid then add to bookList
				}
					
			}
			// if no pdf file in folder show error
			if (id == 0) {
				//alert.errorAlert("Unsupported file type(s)", "Please choose only pdf file");
				return 0;
			}
		}
		else	//if a file
		{
			if (path.getName().contains(".pdf")) {
				id++;
				BookList.add(new ExtractMetadata().Extractdata(path, id, libid));
			} else 		// show error 
			{
				//alert.errorAlert("Unsupported file type", "Please choose only pdf file");
				return 0;
			}
		}
		return 1;
		
	}
    
    


    
    //TODO initialize table column & Load() import bookdata and display
     
    public void init() {
    	
    	//book table
    	ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	Title.setCellValueFactory(new PropertyValueFactory<>("title"));
    	Author.setCellValueFactory(new PropertyValueFactory<>("author"));
    	Name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	Date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	//collection table
    	CollectionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    	
    	
    }
    
    
    //TODO retrieve data from database, assign to book object then display to table view
    public void load(String libname) {
    	int counter = 0;
    	//ObservableList<Book> rBook = FXCollections.observableArrayList();
    	BookList.clear();						//Erase bookList then assign database to book
    	
    	//System.out.println(dbConn.getLibraryID(libname));
    	if (dbConn.getLibraryID(libname) != "ERROR") {
    		
    		int libid = Integer.parseInt(dbConn.getLibraryID(libname));
    		//System.out.println(libid);
    	
    		ResultSet rs = dbConn.getAllLibraryDoc(libid);
    	
    		try {
				while(rs.next()) {
					counter++;
					BookList.add(new Book(counter, rs.getString("SUBJECT"), rs.getString("FILENAME"), rs.getString("TITLE"), rs.getString("AUTHOR"), rs.getDate("CREATION_DATE"), rs.getString("FILE_PATH"),  rs.getString("KEYWORDS"), libid + ""));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    		}
    	
    	LibraryTable.setItems(BookList);	//set book to table view

 
    }
    
    //TODO check if database is existed, if yes load Libraries name to collection and document(book) to library 
    public void CheckExistDatabase () {
    	LibraryList.clear();	//clear previous collection list
    	
    	if (!dbConn.getAllLibraryNames().isEmpty()) {
    		for (String lib : dbConn.getAllLibraryNames()) {
    			LibraryList.add(lib);		//get all collection list
    		}
    		
    		//set to collection table & select the first collection & show all book of the selected collection 
    		CollectionTable.setItems(LibraryList);
    		CollectionTable.getSelectionModel().selectFirst();
    		load(CollectionTable.getSelectionModel().getSelectedItem());
    		
    	} else {
    		return;		//if non in database then return
    	}
    	
    } 
    
    
    
    //TODO collection event, if mouse click one once then load book from that collection to library table 
    @FXML
    void CheckCollectionEvent(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
    		
    		//if select from empty collection then return
        	if (CollectionTable.getSelectionModel().getSelectedItem() == null) {
        		return;
        	}
    		
    		
    		// System.out.println(CollectionTable.getSelectionModel().getSelectedItem());
            load(CollectionTable.getSelectionModel().getSelectedItem());
        }
    }
    
    //TODO library event, if mouse click ONE then load book subject&keyword, if TWO then open the file 
    @FXML
    void CheckLibraryEvent(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            //System.out.println(LibraryTable.getSelectionModel().getSelectedItem().getFilepath());
    		 
    		//if null selection then skips
            if (LibraryTable.getSelectionModel().getSelectedItem() == null) {
            	return;
            }
            
            try {
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                  desktop = Desktop.getDesktop();
                }
                
                //fixed unrecognized resources path
                String temp = LibraryTable.getSelectionModel().getSelectedItem().getFilepath();
                File openFile = new File(temp);
                
                if(openFile.exists()) {
                 desktop.open(openFile);
                } else
                {
                	alert.errorAlert("", "File not found");
                }
                
              } catch (IOException ioe) {
                ioe.printStackTrace();
              }
        }
        
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
            //System.out.println(LibraryTable.getSelectionModel().getSelectedItem().getTitle());
           
            //if null selection then skips
            if (LibraryTable.getSelectionModel().getSelectedItem() == null) {
            	return;
            }
            
            //Keywords section for database will be fixed after the keyoccurrence is fixed
            /**
            String Keywords = "";
            
            ResultSet rs = dbConn.getKeywordsforDoc(LibraryTable.getSelectionModel().getSelectedItem().getId());
            
           
            try {
				while (rs.next()) {
					Keywords += " "  + rs.getString("KEYWORD");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            //System.out.println(Keywords);
             * 
             */
            
            
            showKeywords.setText(LibraryTable.getSelectionModel().getSelectedItem().getKeywords());
            
            showSubject.setText(LibraryTable.getSelectionModel().getSelectedItem().getSubject());
            
            showTitle.setText(LibraryTable.getSelectionModel().getSelectedItem().getTitle());
        }
    }
    
    

   
    @Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		init();
		
		CheckExistDatabase();
	
    }
   
}
