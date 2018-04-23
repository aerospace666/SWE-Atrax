package src.swe.draw.nodes;

import javafx.scene.control.Button;
import src.swe.draw.graph.Nodes;

public class ButtonNode extends Nodes{

	public ButtonNode(String nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
		
		Button button = new Button(nodeId);
		
		drawView(button);
	}

}
