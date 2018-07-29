package src.swe.main.ui.library;

import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SelectableLabel extends TextArea {

    public SelectableLabel() {
        this("", true);
    }

    public SelectableLabel(String text) {
        this(text, true);
    }

    public SelectableLabel(String text, boolean selectable) {
        super(text);

        this.setMouseTransparent(!selectable);
        this.setEditable(false);
        this.setFocusTraversable(false);
        this.setId("selectable-label");
        this.setWrapText(true);

        sizeToText();

        textProperty().addListener(e -> sizeToText());
    }

    public void setSelectable(boolean selectable) {
        setMouseTransparent(!selectable);
    }

    private void sizeToText() {
        Text t = new Text(getText());
        t.setFont(this.getFont());
        StackPane pane = new StackPane(t);
        pane.layout();
        double width = t.getLayoutBounds().getWidth();
        double height = t.getLayoutBounds().getHeight();
        this.setMaxSize(width, height);
    }

}
