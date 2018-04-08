package swe.draw.layout.example;

import java.util.Collection;
import swe.draw.graph.Nodes;
import swe.draw.graph.Show;
import swe.draw.layout.parent.Layout;


// this class layout is to show example for adjusting node location 



public class ExampleLayout extends Layout {

    Show show;


    public ExampleLayout(Show show) {

        this.show = show;

    }

    public void execute() {

        Collection<Nodes> nodes = show.getMap().getNodeTableId();
        
        
        
        
        for (Nodes node : nodes) {
        	//String temp = node.getNodeId();
        	
        	//adjust location of node using x and y
        	double x = 0.0;
            double y = 0.0;
            
            //testing temp varible
            //System.out.println(temp);
        	switch (node.getNodeId()) {
        	
        	case "Image1":
        		x = 100.0;
        		break;
        		
        	case "Button1":
        		x = 100.0;
        		y = 110.0;
        		break;
        		
        	case "PImage1":
        		x = 160.0;
        		y = 65.0;
        		break;
        		
        	case "Image2":
        		x = 500.0;
        		break;
        		
        	case "Button2":
        		x = 500.0;
        		y = 110.0;
        		break;
        		
        	case "PImage2":
        		x = 460.0;
        		y = 65.0;
        		break;
        		
        	default:
        		throw new UnsupportedOperationException("Unsupported type: " + node.getNodeId());
        	
        	
        	}
            

        	x += 200;
        	y += 200;
            node.relocate(x, y);

        }

    }

}
