package src.swe.draw.nodes;

import src.swe.draw.graph.Nodes;
import javafx.scene.control.Label;
public class LabelNode extends Nodes {

	public LabelNode(String nodeId) {
		super(nodeId);
		// TODO Auto-generated constructor stub
			Label label = new Label(nodeId);
			drawView(label);
	}

}
