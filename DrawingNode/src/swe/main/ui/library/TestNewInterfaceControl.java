package src.swe.main.ui.library;


import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.Person;
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
    	
    	
    	
    	//if user doesnt select any directory
    	if (selectedFolder == null) {
    		return;
    	}
    	
    	System.out.println(selectedFolder.getAbsolutePath());
    	
    	
    	
    	/*
    	 * Popup ask for Collection name
    	 *
    	 * */
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
		
		
		//Create event handler
		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				System.out.println("----Collection name is: " + name.getText());
				
				if (name.getText() == null || name.getText().isEmpty()) {
				
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("No name");
					alert.setContentText("Please enter Collection name");
					alert.showAndWait();
					
					popup.show();
					return;
				}
				
				
				popup.close();
				
				LibraryList.add(new TreeItem<String>(name.getText()));
				
				ProcessData(selectedFolder.getAbsolutePath(), name.getText());	
				
			}
			
		});		
		
		
		
		
		
    	//Check if add to existed collection
    	if (CollectionTable.getSelectionModel().getSelectedItem() == null) {
    		//popup class ask for collection name then process the data
    		popup.show();
  
    		//TODO insert new collection name into database	
    		
    	} 
    	else
    	{
    		CollectionName = CollectionTable.getSelectionModel().getSelectedItem().getValue();
    		System.out.println(CollectionName);
    		ProcessData(selectedFolder.getAbsolutePath(), CollectionName);
    	}
    	
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
    	
    	
    	
    
    	
    }
    
    //TODO retrieve data from database, assign to book object then display to table view
    public void load(String CollectionName){
  
    	try {
			RetrieveDataDatabase(CollectionName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    /*
     * Task processing
     * 
     * */
    
   
	public void ProcessData(String PdfPath, String CollectionName) {
    	
    	AlertFormat alert = new AlertFormat();
    	
    	WorkIndicatorDialog<String> wd = new WorkIndicatorDialog<>(MainID.getScene().getWindow(), "Generating Files's metadata...");
    	
    	wd.addTaskEndNotification(result -> {
	    	
	    	//debug log
	    	System.out.println("-Line " + Thread.currentThread().getStackTrace()[1].getLineNumber() + ": " + result);
	    		    	
	    	//if return 0, means no pdf file in the folder
	    	if (result == 0) {
	    		
	   			alert.errorAlert("Unsupported file type", "Please choose only pdf file");
				return;
	    	} else if (result == -1)
	    	{
	    		
	    		alert.infoAlert("Notice", "Folder is empty");
				return;
	    	}
	    });
    	
    	 wd.exec("123", inputParam -> {
		    	
		    	
		    	//Processing code
		    	
    		 //return 0 if there is no Pdf file in the folfer, -1 if the folder is empty
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
			 if (ListOfFiles.length == 0)
			 {
				 return -1;
			 }
			 
			 for(File file : ListOfFiles) 
			 {
				 if (file.getName().toLowerCase().contains(".pdf")) {
					 check++;
					 
					 resHeader = new BiblioItem();
					 engine.processHeader(file.getAbsolutePath(), true, resHeader);
					 BookList.add(resHeader);
					 insertIntoDatabase(resHeader, CollectionName, file.getAbsolutePath());
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
	
	public void insertIntoDatabase(BiblioItem resHeader, String CollectionName, String PdfPath) {
		
		//check database connection
		AtraxDatabase dbConn = new AtraxDatabase(); 
		
		//Create new Collection name - Do nothing if already exist
		dbConn.createNewLibrary(CollectionName);
		
		int CollectionId = dbConn.getLibraryID(CollectionName);
		
		/*
		 * Validate input
		 * */
		String Title = resHeader.getTitle();
		String Journal = resHeader.getJournal();
		String Abstract =  resHeader.getAbstract();
		String Publisher =  resHeader.getPublisher();
		String Lang = resHeader.getLanguage();
		String Doi = resHeader.getDOI();
		String ISSN = resHeader.getISSNe();
		String URL = "";
		if (Doi != null)
			URL = "WWW.dx.doi.org/" + Doi;
		String Institution =  resHeader.getInstitution();
		String Extra = resHeader.getNote();
		String PublicDate = resHeader.getPublicationDate();
		List<Person> authors = resHeader.getFullAuthors(); 
		
		if (Title == null)
		{
			Title = "Can not retrieve title.";
			showTitle.setStyle("-fx-underline : true");
		}
		
		if (authors.size() > 1) 
			resHeader.setAuthors(authors.get(0).getFirstName() + " " 
									+ authors.get(0).getLastName() + " et al");		
		
		//insert into Document command
		dbConn.populateDocumentsTable(Journal, Title,
				Abstract, Publisher, Lang,
				Doi, ISSN, URL,
				Institution, Extra, PdfPath, 
				CollectionId, PublicDate);
		
		//insert into Author command
		if (authors.size() > 0)
		{
			for (int i = 0; i <= authors.size(); i++)
			{
				String authorName = authors.get(i).getFirstName() + " " + authors.get(i).getLastName();
				dbConn.populateAuthorsTable(authorName);
				
				//insert into Author-Doc command
				dbConn.populateDocument_Author(dbConn.getDocumentID(Title), dbConn.getAuthorID(authorName));
			}
			
		}
		
	}
	
	public void RetrieveDataDatabase (String CollectionName) throws SQLException {
		AtraxDatabase dbConn = new AtraxDatabase();
		int CollectionID = dbConn.getLibraryID(CollectionName);
		ResultSet rs = dbConn.getAllLibraryDoc(CollectionID);
		
		while(rs.next())
		{
			BiblioItem resHeader = new BiblioItem();
			resHeader.setTitle(rs.getString("TITLE"));
			
			//TODO retrieve author from author table 
			resHeader.setAuthors("");
				    
			showTitle.setText(rs.getString("TITLE"));
			showItemType.setText(rs.getString("ITEM_TYPE"));
			showAbstract.setText(rs.getString("ABSTRACT"));
			showDate.setText(rs.getString("Date"));
			showPublication.setText(rs.getString("PUBLICATION"));
			showLanguage.setText(rs.getString("LANGUAGE"));
			showDOI.setText(rs.getString("DOI"));
			showISSN.setText(rs.getString("ISSN"));
			showURL.setText(rs.getString("URL"));
			showNote.setText(rs.getString("EXTRA"));
		}
	}
	
	public void CheckExistDatabase () {
		
		AtraxDatabase dbConn = new AtraxDatabase();
		LibraryList.clear();
		
		//retreive all Collection name command
		List<String> CollectionNames = dbConn.getAllLibraryNames();
		
		if (CollectionNames.isEmpty())
			return;
		
		for (String CollectionName : CollectionNames)
		{
			LibraryList.add(new TreeItem<String>(CollectionName));
		}
		CollectionItem.getChildren().setAll(LibraryList);
		CollectionTable.setRoot(CollectionItem);
		CollectionTable.getSelectionModel().selectFirst();
		load(CollectionTable.getSelectionModel().getSelectedItem().getValue());
		
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		init();
		CheckExistDatabase();
	}
	
	
    
    
    
}
