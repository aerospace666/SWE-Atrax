package src.swe.main.ui.library;

<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> a587306da099b25bddc7de1fffc9a22f69d09246
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.grobid.core.data.BiblioItem;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
=======
>>>>>>> a587306da099b25bddc7de1fffc9a22f69d09246
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.swe.database.AtraxDatabase;
import src.swe.main.ui.Alert.AlertFormat;
import src.swe.main.ui.Alert.Popup;
import src.swe.main.ui.Alert.WorkIndicatorDialog;

public class TestNewInterfaceControl implements Initializable{

    @FXML
    private StackPane MainID;

    @FXML
    private Menu FileId;

    @FXML
    private MenuItem AddFolder;

    @FXML
    private TabPane tabpane;

    @FXML
    private Tab LibraryTab;

    @FXML
    private TreeView<String> CollectionTable;

    @FXML
    private ContextMenu CollectionContextMenu;

    @FXML
    private TableView<BiblioItem> LibraryTable;

    @FXML
    private TableColumn<BiblioItem, String> TitleCo;

    @FXML
    private TableColumn<BiblioItem, String> AuthorCo;

    @FXML
    private Label showItemType;

    @FXML
    private Label showTitle;

    @FXML
    private Label showAuthors;

    @FXML
    private Label showAbstract;

    @FXML
    private Label showKeywords;

    @FXML
    private Label showDate;

    @FXML
    private Label showAffiliation;

    @FXML
    private Label showPublication;

    @FXML
    private Label showLanguage;

    @FXML
    private Label showDOI;

    @FXML
    private Label showISSN;

    @FXML
    private Label showURL;

    @FXML
    private Label showNote;

    @FXML
    private Tab MindmapTab;

    
    
    /*
     * Variable declaration
     * 
     * 
     * */
    
    ObservableList<BiblioItem> BookList = FXCollections.observableArrayList(); //Book List for LibaryTable
    ObservableList<TreeItem<String>> LibraryList = FXCollections.observableArrayList(); //LibrayList for Collection table 
    Image LibraryIcon = new Image(getClass().getClassLoader().getResourceAsStream("Icon/196124.png")); //icon for treeview library elements
    TreeItem<String> CollectionItem = new TreeItem<String> ("Library", new ImageView(LibraryIcon));	//library icon for collection table
    
    /*
     * End Variable declaration
     * */
    
    
    
    /*
     * Handling addFolder and addFile functionalities
     * 
     * Folderselect -> TaskInidicator(getFilePath->studyMetada) -> insertintodatabse-> load()
     * 
     * */
    
    @FXML
    void AddFile(ActionEvent event) {

    	String CollectionName; 
    	//if select from empty collection then return
    	if (CollectionTable.getSelectionModel().getSelectedItem() == null) {
    		AlertFormat alert = new AlertFormat();
			alert .errorAlert("No Collection selecetd", "Please choose a collection first");
    		return;
    	}
    	
    	CollectionName = CollectionTable.getSelectionModel().getSelectedItem().getValue();
    	
    	FileChooser fileChooser = new FileChooser();
    	
    	//enable to select multiples file
    	List<File> selectedfile = fileChooser.showOpenMultipleDialog(MainID.getScene().getWindow());
    	
    	//if there is no files selected then return
    	if(selectedfile == null || selectedfile.isEmpty()) {
    		return;
    	}
    	
    	for (File file : selectedfile)
    	{
    		ProcessData(file.getAbsolutePath(), CollectionName);
    	}
    	
    }

    
   
    @FXML
    void AddFolder(ActionEvent event) {
    	
    	String CollectionName;
    	
    	//open folder select dialog
    	DirectoryChooser directory = new DirectoryChooser();
    	File selectedFolder =  directory.showDialog(MainID.getScene().getWindow());
    	
    	
    	
    	//if user dosent select any directory
    	if (selectedFolder == null) {
    		return;
    	}
    	
    	System.out.println(selectedFolder.getAbsolutePath());
    	
    	//Check if add to existed collection
    	if (CollectionTable.getSelectionModel().getSelectedItem() == null) {
    		
    		Popup popup = new Popup();
    		CollectionName = popup.start();
    		
    		//TODO insert new collection name into database
    				
    	} 
    	else
    	{
    		CollectionName = CollectionTable.getSelectionModel().getSelectedItem().getValue();
    	}
    	
    	
    	
    	ProcessData(selectedFolder.getAbsolutePath(), CollectionName);
    	
    	
    }
    
    
   
    

    @FXML
    void ClearCollectionSelection(ActionEvent event) {
    	CollectionTable.getSelectionModel().clearSelection();
    }

    
  //TODO initialize table column & Load() import BiblioItem and display
    
    public void init() {
    	
    	//Library table

    	TitleCo.setCellValueFactory(new PropertyValueFactory<>("title"));
    	AuthorCo.setCellValueFactory(new PropertyValueFactory<>("authors"));
    	
    	
    	//collection table
    	
    	CollectionItem.setExpanded(true);
    	LibraryList.add(new TreeItem<String> ("Test1"));
    	
    	
    
    	
    }
    
    //TODO retrieve data from database, assign to book object then display to table view
    public void load(String libname) {
    	
    	
    	
		//BookList.add(resHeader);
		LibraryTable.setItems(BookList);
		
    	CollectionItem.getChildren().setAll(LibraryList);		//update new elements
    	
    	CollectionTable.setRoot(CollectionItem);		//set collection to tree view
    	
    	
    	//showAbstract.setText(resHeader.getAbstract());
    	//System.out.println(getClass().getClassLoader().getResource("196124.jpg"));
    }
    
    
    /*
     * Task processing
     * 
     * */
    
   
	public void ProcessData(String PdfPath, String CollectionName) {
    	
    	AlertFormat alert = new AlertFormat();
    	
    	WorkIndicatorDialog<String> wd = new WorkIndicatorDialog<>(LibraryTable.getScene().getWindow(), "Generating Files's metadata...");
    	
    	wd.addTaskEndNotification(result -> {
	    	
	    	//debug log
	    	System.out.println("-Line " + Thread.currentThread().getStackTrace()[1].getLineNumber() + ": " + result);
	    		    	
	    	//if return 0, means no pdf file in the folder
	    	if (result == 0) {
	    		
	   			alert.errorAlert("Unsupported file type", "Please choose only pdf file");
				return;
	    	}
	    });
    	
    	 wd.exec("123", inputParam -> {
		    	
		    	
		    	//Processing code
		    	
    		 //return 0 if there is no Pdf file in the folfer
    		 int check = getPdfData(PdfPath, CollectionName);
		    	
    		 
    		 
    		 
    		 return check;
    	
    	 });
				
    	wd = null;
    }
    
	/*
	 * File processing
	 * 
	 * */
	
	public int getPdfData(String PdfPath, String CollectionName) {
		int check = 0;
		BookList.clear();
		
		//Processing metadata extraction
		 String pGrodHome = "grobid-home";
			
		 
		 GrobidHomeFinder Grofind = new GrobidHomeFinder(Arrays.asList(pGrodHome)) ;
			
		 GrobidProperties.getInstance(Grofind);
			
		 System.out.println("-------- GROBID_HOME="+GrobidProperties.get_GROBID_HOME_PATH());
			
		 Engine engine = GrobidFactory.getInstance().createEngine();
		 
		 BiblioItem resHeader = new BiblioItem();
		 
		
		 File WorkingDirectory = new File(PdfPath);
		
		 if (WorkingDirectory.isDirectory())
		 {
			 File[] ListOfFiles = WorkingDirectory.listFiles();
			 for(File file : ListOfFiles) 
			 {
				 if (file.getName().toLowerCase().contains(".pdf")) {
					 check++;
					 engine.processHeader(file.getAbsolutePath(), true, resHeader);
					 BookList.add(resHeader);
					 insertIntoDatabase(resHeader, CollectionName);
				 }
			 }
		 } 
		 else	//if a file
		 {
			if (WorkingDirectory.getName().toLowerCase().contains(".pdf"))
			{
				check++;
				engine.processHeader(WorkingDirectory.getAbsolutePath(), true, resHeader);
				BookList.add(resHeader);
				//insertIntoDatabase(resHeader, CollectionName);
			}
		 }
	
		load(CollectionName);
		return check;
	}
	
	/*
     * Database Management section
     * 
     * */
	
	public void insertIntoDatabase(BiblioItem resHeader, String CollectionName) {
		
		//check database connection
		AtraxDatabase dbConn = new AtraxDatabase(); 
		dbConn.createNewLibrary(CollectionName);
		String CollectionId = dbConn.getLibraryID(CollectionName);
		
		
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		init();
		load("hi");
	}
	
	
    
    
    
}
