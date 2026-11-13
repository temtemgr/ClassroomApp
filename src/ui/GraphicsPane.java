package ui;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import logic.Student;

import javax.swing.border.Border;
import java.util.List;

public class GraphicsPane extends BorderPane implements StateObserver {

    private final StateModel stateModel;
    private final Pane barsPane = new Pane();
    private final CheckBox sortCheckBox;
    private final int GRADE_THRESHOLD = 4;

    public GraphicsPane(StateModel stateModel) {
        this.stateModel = stateModel;
        stateModel.addObserver(this);

        // Checkbox
        this.sortCheckBox = new CheckBox("Sort by Grade");
        sortCheckBox.setOnAction(_ -> stateModel.setSortByGrade(sortCheckBox.isSelected()));

        // Layout
        HBox topBox = new HBox(sortCheckBox);
        topBox.setPadding(new Insets(10));
        setTop(topBox);
        setCenter(barsPane);

        // Redraw when the drawing area changes size
        barsPane.widthProperty().addListener((_, _, _) -> drawGraphic());
        barsPane.heightProperty().addListener((_, _, _) -> drawGraphic());
    }

    public void drawGraphic() {
        barsPane.getChildren().clear();
        double preGradeFactor = stateModel.getPreGradeFactor();
        List<Student> students = stateModel.getSortedStudents();

        double paneHeight = barsPane.getHeight();
        double paneWidth = barsPane.getWidth();
        double barWidth = (paneWidth / students.size());

        for (int i = 0; students.size() > i; i++) {
            Student student = students.get(i);
            double barHeight = student.finalGrade(preGradeFactor) / 6.0 * barsPane.getHeight();
            double x = i * barWidth;
            double y = barsPane.getHeight() - barHeight;

            Rectangle gradeBar = new Rectangle(x, y, barWidth, barHeight);

            if (student.finalGrade(preGradeFactor) >= GRADE_THRESHOLD) {
                gradeBar.setFill(Color.DARKSEAGREEN);
            } else {
                gradeBar.setFill(Color.SALMON);
            }
            barsPane.getChildren().add(gradeBar);
            barsPane.getChildren().add(createStudentNameText(student, x, barWidth));
        }

        // Threshold line – added last so it's drawn on top of the bars
        double thresholdY = paneHeight - (GRADE_THRESHOLD / 6.0 * paneHeight);
        Line thresholdLine = new Line(0, thresholdY, paneWidth, thresholdY);
        thresholdLine.setStroke(Color.RED);
        thresholdLine.setStrokeWidth(2);
        barsPane.getChildren().add(thresholdLine);
    }

    private Text createStudentNameText(Student student, double x, double barWidth) {
        Text nameText = new Text(student.name());
        nameText.getTransforms().add(new Translate(x + barWidth/2, barsPane.getHeight() - 10));
        nameText.getTransforms().add(new Rotate(-90));

        return nameText;
    }

    @Override
    public void stateChanged() {
        drawGraphic();
    }
}
