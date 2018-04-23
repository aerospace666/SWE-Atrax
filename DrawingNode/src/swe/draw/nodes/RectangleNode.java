package src.swe.draw.nodes;


import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import src.swe.draw.graph.Nodes;

public class RectangleNode extends Nodes {

    public RectangleNode( String nodeId) {
    	super(nodeId);

        Rectangle rectangle = new Rectangle(1,800);

       
        rectangle.setStroke(Color.LIGHTGRAY);
        
        //rectangle.setFill(Color.RED);

        drawView(rectangle);

    }

}
