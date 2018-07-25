package src.swe.main.ui.library;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.grobid.core.data.BiblioItem;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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
     * Variable sections
     * 
     * 
     * */
    
    ObservableList<BiblioItem> BookList = FXCollections.observableArrayList(); //Book List for LibaryTable
    ObservableList<TreeItem<String>> LibraryList = FXCollections.observableArrayList(); //LibrayList for Collection table
    
    
    //icon for treeview library elements
    //final Node LibraryIcon = new ImageView(new Image(getClass().getResourceAsStream("/Users/conghienhoang/Documents/GitHub/SWE-Atrax/DrawingNode/books-certificate-collection-of-study-learning-school-37a090ff7826e98f-512x512.png")));
    
    TreeItem<String> CollectionItem = new TreeItem<String> ("Library"); //, LibraryIcon );
    
    @FXML
    void AddFile(ActionEvent event) {

    	LibraryList.add(new TreeItem<String>("Test 2"));
    	load("Hi");
    }

    @FXML
    void AddFolder(ActionEvent event) {

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
    	
    	//BiblioItem e = new BiblioItem();
    	//e.setTitle("Hi this is Test1");
    	//BookList.add(e);
    	
    	//LibraryTable.setItems(BookList);		//set book to table view
    	String pdfPath = "TestPdf/Koelsch SFS Collagen ja508190d.pdf";
		
		String pGrodHome = "grobid-0.5.1/grobid-home";
		
		//File input = new File("Koelsch SFS Collagen ja508190d.pdf");
		
		GrobidHomeFinder Grofind = new GrobidHomeFinder(Arrays.asList(pGrodHome)) ;
		
		GrobidProperties.getInstance(Grofind);
		
		System.out.println(">>>>>>>> GROBID_HOME="+GrobidProperties.get_GROBID_HOME_PATH());
		
		Engine engine = GrobidFactory.getInstance().createEngine();
		
		BiblioItem resHeader = new BiblioItem();
		engine.processHeader(pdfPath, true, resHeader);
    	
		BookList.add(resHeader);
		LibraryTable.setItems(BookList);
		
    	CollectionItem.getChildren().setAll(LibraryList);		//update new elements
    	
    	CollectionTable.setRoot(CollectionItem);		//set collection to tree view
    	
    	
    	showAbstract.setText(resHeader.getAbstract());
 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		init();
		load("hi");
	}
	
	
    
    
    
}
