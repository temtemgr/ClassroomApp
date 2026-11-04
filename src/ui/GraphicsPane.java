package ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import logic.Student;

import java.util.List;

public class GraphicsPane extends Pane {

    private List<Student> students;
    private double preGradeFactor;
    private final int GRADE_THRESHOLD = 4;

    public GraphicsPane(List<Student> students) {
        this.students = students;
        this.preGradeFactor =  1;
        drawGraphic(preGradeFactor);
        widthProperty().addListener((_, _, _) -> drawGraphic(preGradeFactor));
        heightProperty().addListener((_, _, _) -> drawGraphic(preGradeFactor));
    }

    public void drawGraphic(double preGradeFactor) {
        this.preGradeFactor = preGradeFactor;
        this.getChildren().clear();
        double barWidth = (getWidth() / students.size());

        for (int i = 0; students.size() > i; i++) {
            Student student = students.get(i);
            double barHeight = student.finalGrade(preGradeFactor) / 6.0 * getHeight();
            double x = i * barWidth;
            double y = getHeight() - barHeight;

            Rectangle gradeBar = new Rectangle(x, y, barWidth, barHeight);

            if (student.finalGrade(preGradeFactor) >= GRADE_THRESHOLD) {
                gradeBar.setFill(Color.DARKSEAGREEN);
            } else {
                gradeBar.setFill(Color.SALMON);
            }
            getChildren().add(gradeBar);
            getChildren().add(createStudentNameText(student, x, barWidth));
        }
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    private Text createStudentNameText(Student student, double x, double barWidth) {
        Text nameText = new Text(student.name());
        nameText.getTransforms().add(new Translate(x + barWidth/2, getHeight() - 10));
        nameText.getTransforms().add(new Rotate(-90));

        return nameText;
    }
}
