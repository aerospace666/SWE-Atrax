package swe.draw.graph;

/* Show class is to add all modeled nodes into a viewable scrollpane
 * 
 * 
 * */




import javafx.scene.Group; 
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Show {
	
	//update node(with new position) to MapNode
    private MapNode mapNode;

    //This is to group all the Node into same group
    private Group draw;

   	private ScrollPane sp = new ScrollPane();

 

    NodeLayout nodeLayout;

    public Show() {

        this.mapNode = new MapNode();

        draw = new Group();
        nodeLayout = new NodeLayout();

        draw.getChildren().add(nodeLayout);

    
        // set all node into scrollpane to be displayed
        sp.setContent(draw);
        
    }
    
    
    public ScrollPane getScrollPane() {
        return this.sp;
    }

    public Pane getNodeLayout() {
        return this.nodeLayout;
    }

    public MapNode getMap() {
        return mapNode;
    }

   

    public void update() {

     
        
        getNodeLayout().getChildren().addAll(mapNode.getAddedNode());
        getNodeLayout().getChildren().addAll(mapNode.getAddedBindLine());

        getNodeLayout().getChildren().removeAll(mapNode.getRemovedNode());
        getNodeLayout().getChildren().removeAll(mapNode.getRemovedBindLine());
    
        
        
        
    
        getMap().refresh();
    }

}
