package src.swe.draw.nodes;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import src.swe.draw.graph.Nodes;

public class BookNode extends Nodes {
	private
		double x = 0.0;
		double y = 0.0;
		ImageView i = new ImageView("https://s-media-cache-ak0.pinimg.com/originals/f5/c9/bd/f5c9bdb64a87dd158cc0dd42fe3d6028.jpg"); 
		Label l = new Label();
		
	public BookNode(String nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
		
		
		i.setLayoutX(x);
		i.setLayoutY(y);
		l.setLayoutX(x);
		i.setLayoutY(y + 10);
	}
	
	public void SetLayoutX (double x) {
		this.x = x;	
	}
	
	public void SetLayoutY (double y) {
		this.y = y;
	}
	
	public void relocate (double x, double y) {
		SetLayoutX(x);
		SetLayoutY(y);
	}
}
