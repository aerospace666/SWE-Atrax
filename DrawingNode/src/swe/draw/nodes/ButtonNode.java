package swe.draw.nodes;

import javafx.scene.control.Button;
import swe.draw.graph.Nodes;

public class ButtonNode extends Nodes{

	public ButtonNode(String nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
		
		Button button = new Button(nodeId);
		
		drawView(button);
	}

}
