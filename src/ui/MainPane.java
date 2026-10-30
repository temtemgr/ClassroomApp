package ui;

import javafx.scene.layout.Pane;
import logic.Course;

import java.util.Map;

public class MainPane extends Pane {

    // Data to display
    public Course course;
    public Map<String, String> majorMap;

    public MainPane(Map<String, String> majorMap, Course course) {
        this.majorMap = majorMap;
        this.course = course;
    }
}
