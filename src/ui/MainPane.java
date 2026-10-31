package ui;

import io.CourseDataReaderFactory;
import io.DataReader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.Course;

import java.nio.file.Path;
import java.util.Map;

public class MainPane extends StackPane {
    private double preGradeFactor = 1;
    private Course course;

    public MainPane(Map<String, String> majorMap, Course course) {
        this.course = course;

        // Textarea
        TextArea studentList = new TextArea(course.toString(majorMap, preGradeFactor));
        VBox.setVgrow(studentList, Priority.ALWAYS);
        VBox.setMargin(studentList, new Insets(0, 0, 15, 0));
        studentList.setEditable(false);

        // Button
        Button refreshTextBtn = new Button("Update");
        refreshTextBtn.setOnAction(_ ->
                studentList.setText(this.course.toString(majorMap, preGradeFactor))
        );
        Button selectNewFileBtn = new Button("new File");
        selectNewFileBtn.setOnAction(_ -> {
            try {
                Path gradesFile = FileChooserFx.selectFileWithExplorerDialog("Select the grades-file", getScene().getWindow());

                DataReader<Course> courseDataReader = CourseDataReaderFactory.create(gradesFile);
                this.course = courseDataReader.readData(gradesFile);
            } catch (Exception e) {
                Alert message = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                message.showAndWait();
                return;
            }
            studentList.setText(course.toString(majorMap, preGradeFactor));
        });

        // Labels
        Label gradeFactorText = new Label("Set the weight for the pregrades:");
        Label gradeFactorValue = new Label(preGradeFactorInPercent());

        // Slider
        Slider gradeFactorSlider = new Slider(0, 1, 1);
        gradeFactorSlider.valueProperty().addListener((_, _, newValue) -> {
            preGradeFactor = newValue.doubleValue();
            gradeFactorValue.setText(preGradeFactorInPercent());
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
        return String.format("%.1f %%", preGradeFactor * 100.0);
    }
}
