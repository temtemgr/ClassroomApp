import io.*;
import logic.Course;

import java.nio.file.Path;
import java.util.*;

class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Select grades file:");
            Path gradesFile = FileChooser.selectFileWithExplorerDialog();

            DataReader<Course> courseDataReader = getCourseDataReader(gradesFile);
            Course course = courseDataReader.readData(gradesFile);

            System.out.printf("Select major-map file: %n %n");
            Path mapFile = FileChooser.selectFileWithExplorerDialog();
            Map<String,String> fieldsOfStudy = new MajorMapReader().readData(mapFile);

            course.displayAverageGradesFromCourse(fieldsOfStudy);

            course.addReport(gradesFile);
            System.out.printf("%nReport was created.%n");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }

    public static DataReader<Course> getCourseDataReader(Path file) throws DataReadException {
        if (file.getFileName().toString().endsWith(".csv")) {
            return new CsvCourseDataReader();
        }
        if (file.getFileName().toString().endsWith(".txt")) {
            return new CsvCourseDataReader();
        }
        throw new DataReadException("The selected file format is not supported");
    }
}
