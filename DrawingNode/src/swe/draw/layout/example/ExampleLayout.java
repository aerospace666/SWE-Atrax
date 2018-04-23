package src.swe.draw.layout.example;

import java.util.Collection;
import src.swe.draw.graph.Nodes;
import src.swe.draw.graph.Show;
import src.swe.draw.layout.parent.Layout;


// this class layout is to show example for adjusting node location 



public class ExampleLayout extends Layout {

    Show show;


    public ExampleLayout(Show show) {

        this.show = show;

    }

    public void execute() {

        Collection<Nodes> nodes = show.getMap().getNodeTableId();
        double x = 0.0, xLab = 0.0, xRec = 0.0;
        double y1 = 0.0, y2 = 0.0, y3 = 0.0, yLab = 0.0, yRec = 0.0;
        int counter = 0; 
        
        
        for (Nodes node : nodes) {
        	
        	
        	//adjust location of node using x and y
        	
            
            
        	switch (node.getNodeId().substring(0, 3)) {
        	
        	case "Ima":
        		counter++;
        		
        		if (counter < 5) {
        			x = 200.0;
        			y1 += 150.0;
        			node.relocate(x, y1);
        		} 
        		
        		if (counter >= 5 && counter < 8){
        			x = 400.0;
        			y2 += 150.0;
        			node.relocate(x, y2);
        		}
        		
        		if (counter >= 8) {
        			x = 600.0;
        			y3 += 150.0;
        			node.relocate(x, y3);
        		}
     
        		
        		
        		break;
        		
        	case "Lab":
        		
        		xLab += 200.0;
        		yLab = 100.0;
        		node.relocate(xLab, yLab);
        		break;
        		
        	case "Rec":
        		xRec += 180.0;
        		yRec = 50.0;
        		node.relocate(xRec, yRec);
        		break;
        		
        	default:
        		throw new UnsupportedOperationException("Unsupported type: " + node.getNodeId());
        	
        	
        	}
            

        	
            

        }

    }

}
