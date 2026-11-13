package ui;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import logic.Course;

import java.util.Map;

public class MainPane extends StackPane {

    public MainPane(Map<String, String> majorMap, Course initialCourse) {
        StateModel stateModel = new StateModel(initialCourse);

        ControlPane controlPane = new ControlPane(stateModel);
        GraphicsPane graphicsPane = new GraphicsPane(stateModel);

        final SplitPane verticalSplitPane = new SplitPane();
        verticalSplitPane.setOrientation(Orientation.HORIZONTAL);
        verticalSplitPane.setDividerPosition(0, 0.5);
        verticalSplitPane.getItems().addAll(controlPane, graphicsPane);
        getChildren().add(verticalSplitPane);
    }
}