package ui;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import logic.Course;

import java.util.Map;

public class MainPane extends StackPane {

    public Course course;
    public Map<String, String> majorMap;
    public double preGradeFactor;
    private TextArea studentList;
    private Label gradeFactorValue;

    public MainPane(Map<String, String> majorMap, Course course) {
        this.majorMap = majorMap;
        this.course = course;
        this.preGradeFactor = 1;

        Button refreshTextBtn = new Button("Update");
        refreshTextBtn.setOnAction(event -> {
            this.studentList.setText(course.toString(majorMap, this.preGradeFactor));
        });

        this.studentList = new TextArea(course.toString(majorMap, preGradeFactor));
        VBox.setVgrow(studentList, Priority.ALWAYS);
        VBox.setMargin(studentList, new Insets(0, 0, 15, 0));
        studentList.setEditable(false);

        Label gradeFactorText = new Label("Set the weight for the pregrades:");
        this.gradeFactorValue = new Label(preGradeFactorInPercent());
        Slider gradeFactorSlider = new Slider(0,1,1);
        gradeFactorSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            this.preGradeFactor =  newValue.doubleValue();
            this.gradeFactorValue.setText(preGradeFactorInPercent());
        });

        HBox hb = new HBox();
        VBox.setMargin(hb, new Insets(0, 0, 10, 0));
        VBox vb = new VBox();

        hb.getChildren().addAll(gradeFactorText, gradeFactorSlider, gradeFactorValue);
        vb.getChildren().addAll(studentList, hb, refreshTextBtn);
        vb.setMaxHeight(200);
        vb.setMaxWidth(400);

        getChildren().addAll(vb);
    }

    private String preGradeFactorInPercent() {
        return String.format("%.1f %%", this.preGradeFactor*100.0);
    }
}
