import io.CourseDataReaderFactory;
import io.DataReader;
import io.MajorMapReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import logic.Course;
import ui.FileChooserFx;
import ui.MainPane;

import java.nio.file.Path;
import java.util.Map;

public class ClassroomFxApp extends Application {

    @Override
    public void start(Stage stage) {
        Course course = null;
        Map<String,String> fieldsOfStudy = null;

        try {
            Path gradesFile = FileChooserFx.selectFileWithExplorerDialog("Select the grades-file", stage);

            DataReader<Course> courseDataReader = CourseDataReaderFactory.create(gradesFile);
            course = courseDataReader.readData(gradesFile);

            Path mapFile = FileChooserFx.selectFileWithExplorerDialog("Select the major-map file", stage);
            fieldsOfStudy = new MajorMapReader().readData(mapFile);

            // course.addReport(gradesFile);
        }
        catch (Exception e) {
            Alert message = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            message.showAndWait();
            // e.printStackTrace();
            Platform.exit();
            return;
        }

        stage.setTitle("ClassroomApp");
        stage.setScene(new Scene(new MainPane(fieldsOfStudy, course), 700, 350));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
