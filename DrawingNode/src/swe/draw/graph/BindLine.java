package swe.draw.graph;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class BindLine extends Group {

    protected Nodes source;
    protected Nodes destin;

    Line line;

    public BindLine(Nodes source, Nodes destin) {

        this.source = source;
        this.destin = destin;

        source.addNodeChild(destin);
        destin.addNodeParent(source);

        line = new Line();

        line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 4.0));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 4.0));

        line.endXProperty().bind( destin.layoutXProperty().add(destin.getBoundsInParent().getWidth() / 4.0));
        line.endYProperty().bind( destin.layoutYProperty().add(destin.getBoundsInParent().getHeight() / 4.0));

        getChildren().add(line);

    }

    public Nodes getSource() {
        return source;
    }

    public Nodes getDestin() {
        return destin;
    }

}