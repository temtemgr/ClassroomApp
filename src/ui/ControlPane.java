package ui;

import io.CourseDataReaderFactory;
import io.DataReader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import logic.Course;

import java.nio.file.Path;
import java.util.Map;

public class ControlPane extends StackPane {
    private double preGradeFactor = 1;
    private Course course;
    private final Map<String, String> majorMap;
    private final GraphicsPane graphicsPane;
    private final TextArea studentList;
    private final Label numOfStudents;

    public ControlPane(Map<String, String> majorMap, Course initialCourse, GraphicsPane graphicsPane) {
        this.course = initialCourse;
        this.majorMap = majorMap;
        this.graphicsPane = graphicsPane;

        // Textarea
        this.studentList = new TextArea(initialCourse.toString(preGradeFactor));
        VBox.setVgrow(this.studentList, Priority.ALWAYS);
        VBox.setMargin(this.studentList, new Insets(0, 0, 15, 0));
        this.studentList.setEditable(false);

        // Button
        Button selectNewFileBtn = createSelectNewFileBtn();

        // Labels
        this.numOfStudents = new Label("Number of Students: " + this.course.getNumOfStudents());
        Label gradeFactorText = new Label("Weight for pre grades:");
        Label gradeFactorValue = new Label(preGradeFactorInPercent());

        // Slider
        Slider gradeFactorSlider = new Slider(0, 1, 1);
        gradeFactorSlider.valueProperty().addListener((_, _, newValue) -> {
            preGradeFactor = newValue.doubleValue();
            gradeFactorValue.setText(preGradeFactorInPercent());
            refreshText();
            graphicsPane.drawGraphic(preGradeFactor);
        });

        HBox hb = new HBox();
        VBox.setMargin(hb, new Insets(0, 0, 10, 0));
        VBox vb = new VBox();

        hb.getChildren().addAll(gradeFactorText, gradeFactorSlider, gradeFactorValue);
        vb.getChildren().addAll(numOfStudents, this.studentList, hb, selectNewFileBtn);
        vb.setMaxHeight(200);
        vb.setMaxWidth(300);

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
            // new controls
            refreshText();
            this.numOfStudents.setText("Number of Students: " + this.course.getNumOfStudents());
            // new graphics
            graphicsPane.setStudents(this.course.students());
            graphicsPane.drawGraphic(preGradeFactor);
        });
        return selectNewFileBtn;
    }

    private String preGradeFactorInPercent() {
        double weight = preGradeFactor / (1 + preGradeFactor);
        return String.format("%.1f %%", weight * 100.0);
    }

    private void refreshText() {
        this.studentList.setText(this.course.toString(preGradeFactor));
    }
}
