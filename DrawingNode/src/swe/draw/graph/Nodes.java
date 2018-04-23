package src.swe.draw.graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;  // this is for display component
import javafx.scene.layout.Pane;


public class Nodes extends Pane {

    String nodeId;
    
    Node view; 
    // two list for tree view, parent point to children
    
    
    List<Nodes> NodeChild = new ArrayList<>();
    
    List<Nodes> NodeParent = new ArrayList<>();
    

    public Nodes(String nodeId) {
        this.nodeId = nodeId;
    }

    public void addNodeChild(Nodes node) {
        NodeChild.add(node);
    }

    public List<Nodes> getNodeChild() {
        return NodeChild;
    }

    public void addNodeParent(Nodes node) {
        NodeParent.add(node);
    }

    public List<Nodes> getNodeParent() {
        return NodeParent;
    }

    public void removeNodeChild(Node node) {
        NodeChild.remove(node);
    }

    // add new node to the pane(scroll pane)
    public void drawView(Node view) {

        this.view = view;
        getChildren().add(view);
    }

    public Node getView() {
        return this.view;
    }

    public String getNodeId() {
        return this.nodeId;
    }
    
    
    
}
