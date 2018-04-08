package swe.draw.nodes;


import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import swe.draw.graph.Nodes;

public class RectangleNode extends Nodes {

    public RectangleNode( String nodeId) {
    	super(nodeId);

        Rectangle rectangle = new Rectangle(8,8);

        
        rectangle.setStroke(Color.RED);
        
        rectangle.setFill(Color.RED);

        drawView(rectangle);

    }

}
