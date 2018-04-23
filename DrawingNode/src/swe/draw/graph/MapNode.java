package src.swe.draw.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import src.swe.draw.nodes.*;


public class MapNode {

    Nodes temp;

    List<Nodes> allNodes;
    List<Nodes> addedNode;
    List<Nodes> removedNode;

    List<BindLine> allBindLine;
    List<BindLine> addedBindline;
    List<BindLine> removedBindLine;
    
    HashMap<String,Nodes> nodeTable;

    public MapNode() {

         temp = new Nodes("temp");
         clear();
    }

    public void clear() {

        allNodes = new ArrayList<>();
        addedNode = new ArrayList<>();
        removedNode = new ArrayList<>();

        allBindLine = new ArrayList<>();
        addedBindline = new ArrayList<>();
        removedBindLine = new ArrayList<>();

        nodeTable = new HashMap<>(); 

    }

    public void clearAddedLists() {
        addedNode.clear();
        addedBindline.clear();
    }

    public List<Nodes> getAddedNode() {
        return addedNode;
    }


    public List<Nodes> getAllNodes() {
        return allNodes;
    }
    
    public List<Nodes> getRemovedNode() {
        return removedNode;
    }

    public List<BindLine> getAddedBindLine() {
        return addedBindline;
    }


    public List<BindLine> getAllBindLine() {
        return allBindLine;
    }
    
    public List<BindLine> getRemovedBindLine() {
    	return removedBindLine;
    }

    //check node type the update correct type to node table(hashMap)
    public void checkNode(String id, NodeType type) {

        switch (type) {

        case RECTANGLE:
            RectangleNode rectangleNode = new RectangleNode(id);
            addNode(rectangleNode);
            //allNodes.add(rectangleNode);
            break;

        case IMAGE:
        	ImageNode imageNode = new ImageNode(id);
        	addNode(imageNode);
        	//allNodes.add(imageNode);
        	break;
        
        case BUTTON:
        	ButtonNode buttonNode = new ButtonNode(id);
        	addNode(buttonNode);
        	//allNodes.add(buttonNode);
        	break;
        	
        case LABEL:
        	LabelNode labelNode = new LabelNode(id);
        	addNode(labelNode);
        	break;
        	
        case BOOK:
        	BookNode bookNode = new BookNode(id);
        	addNode(bookNode);
        	break;
        	
        default:
            throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    private void addNode( Nodes node) {

        addedNode.add(node);
        //System.out.println(node.getNodeId());
        nodeTable.put( node.getNodeId(), node);

    }

    public void addBindLine( String sourceId, String destinId) {

        Nodes sourceNode = nodeTable.get( sourceId);
        Nodes destinNode = nodeTable.get( destinId);

        BindLine bindLine = new BindLine( sourceNode, destinNode);

        addedBindline.add( bindLine);

    }
    
    public Collection<Nodes> getNodeTableId(){
    	return nodeTable.values();	
    }
 
    public void refresh() {

    
        allNodes.addAll( addedNode);
        allNodes.removeAll(removedNode);

        addedNode.clear();
        removedNode.clear();

       
        allBindLine.addAll( addedBindline);
        allBindLine.removeAll( removedBindLine);

        addedBindline.clear();
        removedBindLine.clear();

    }

 
}
