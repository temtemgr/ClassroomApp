package ui;

import io.CourseDataReaderFactory;
import io.DataReader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import logic.Course;
import logic.Student;

import java.nio.file.Path;
import java.util.Map;

public class ControlPane extends StackPane implements StateObserver {
    private final StateModel stateModel;
    private final TextArea studentList;
    private final Label numOfStudents;
    private final Label gradeFactorValue;

    public ControlPane(StateModel stateModel) {
        this.stateModel = stateModel;

        // Textarea
        this.studentList = new TextArea();
        VBox.setVgrow(this.studentList, Priority.ALWAYS);
        VBox.setMargin(this.studentList, new Insets(0, 0, 15, 0));
        this.studentList.setEditable(false);

        // Button
        Button selectNewFileBtn = createSelectNewFileBtn();

        // Labels
        this.numOfStudents = new Label();
        this.gradeFactorValue = new Label();

        // Slider
        Slider gradeFactorSlider = new Slider(0, 1, 1);
        gradeFactorSlider.valueProperty().addListener((_, _, newValue) -> {
            stateModel.setPreGradeFactor(newValue.doubleValue());
        });

        // V+HBox
        HBox hb = new HBox();
        VBox.setMargin(hb, new Insets(0, 0, 10, 0));
        VBox vb = new VBox();

        hb.getChildren().addAll(gradeFactorSlider, gradeFactorValue);
        vb.getChildren().addAll(numOfStudents, this.studentList, hb, selectNewFileBtn);
        vb.setMaxHeight(200);
        vb.setMaxWidth(300);

        // Add children to pane and observer to stateModel
        getChildren().addAll(vb);
        stateModel.addObserver(this);
        stateChanged();
    }

    private Button createSelectNewFileBtn() {
        Button selectNewFileBtn = new Button("new File");
        selectNewFileBtn.setOnAction(_ -> {
            try {
                Path gradesFile = FileChooserFx.selectFileWithExplorerDialog("Select the grades-file", getScene().getWindow());

                DataReader<Course> courseDataReader = CourseDataReaderFactory.create(gradesFile);
                stateModel.setCourse(courseDataReader.readData(gradesFile));
            } catch (Exception e) {
                Alert message = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                message.showAndWait();
            }
        });
        return selectNewFileBtn;
    }

    private String preGradeFactorInPercent() {
        double weight = stateModel.getPreGradeFactor() / (1 + stateModel.getPreGradeFactor());
        return String.format("%.1f %%", weight * 100.0);
    }

    private String getCourseString() {
        StringBuilder text = new StringBuilder(String.format("%s %s %n", stateModel.getCourse().id(), stateModel.getCourse().name()));

        for (Student student: stateModel.getSortedStudents()) {
            text.append(String.format("%s: %.2f %n", student.name(), student.finalGrade(stateModel.getPreGradeFactor())));
        }
        return text.toString();
    }

    @Override
    public void stateChanged() {
        this.studentList.setText(getCourseString());
        this.numOfStudents.setText("Number of Students: " + stateModel.getCourse().getNumOfStudents());
        this.gradeFactorValue.setText(preGradeFactorInPercent());
    }
}
