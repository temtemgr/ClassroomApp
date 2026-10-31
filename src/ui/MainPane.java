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
    private final Map<String, String> majorMap;
    private final TextArea studentList;

    public MainPane(Map<String, String> majorMap, Course initialCourse) {
        this.course = initialCourse;
        this.majorMap = majorMap;

        // Textarea
        this.studentList = new TextArea(initialCourse.toString(this.majorMap, preGradeFactor));
        VBox.setVgrow(this.studentList, Priority.ALWAYS);
        VBox.setMargin(this.studentList, new Insets(0, 0, 15, 0));
        this.studentList.setEditable(false);

        // Button
        Button selectNewFileBtn = createSelectNewFileBtn();

        // Labels
        Label gradeFactorText = new Label("Set the weight for the pregrades:");
        Label gradeFactorValue = new Label(preGradeFactorInPercent());

        // Slider
        Slider gradeFactorSlider = new Slider(0, 1, 1);
        gradeFactorSlider.valueProperty().addListener((_, _, newValue) -> {
            preGradeFactor = newValue.doubleValue();
            gradeFactorValue.setText(preGradeFactorInPercent());
            refreshText();
        });

        HBox hb = new HBox();
        VBox.setMargin(hb, new Insets(0, 0, 10, 0));
        VBox vb = new VBox();

        hb.getChildren().addAll(gradeFactorText, gradeFactorSlider, gradeFactorValue);
        vb.getChildren().addAll(this.studentList, hb, selectNewFileBtn);
        vb.setMaxHeight(200);
        vb.setMaxWidth(400);

        getChildren().addAll(vb);
    }

    private Button createSelectNewFileBtn() {
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
            refreshText();
        });
        return selectNewFileBtn;
    }

    private String preGradeFactorInPercent() {
        double weight = preGradeFactor / (1 + preGradeFactor);
        return String.format("%.1f %%", weight * 100.0);
    }

    private void refreshText() {
        this.studentList.setText(this.course.toString(this.majorMap, preGradeFactor));
    }
}