package swe.draw.nodes;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import swe.draw.graph.Nodes;

public class ImageNode extends Nodes {

	public ImageNode(String nodeId) {
		super(nodeId);
		//System.out.println(this.getClass().getResource(".\\DrawingNode\\f5c9bdb64a87dd158cc0dd42fe3d6028.jpg"));
		ImageView image = new ImageView("https://s-media-cache-ak0.pinimg.com/originals/f5/c9/bd/f5c9bdb64a87dd158cc0dd42fe3d6028.jpg");
		image.setFitHeight(100.0);
		image.setFitWidth(50.0);
		Button btn = new Button(nodeId, image);
		drawView(btn);
		
	}

}
