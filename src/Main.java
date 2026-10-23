import io.*;
import logic.Course;

import javax.xml.crypto.Data;
import java.util.*;

class Main {
    public static void main(String[] args) {
        System.out.println("Select grades file:");
        Map<String,String> filePath = FileChooser.selectFileWithExplorerDialog();
        String filePathStr = filePath.get("directory")+"/"+filePath.get("fileName");

        if (filePath.get("fileName") == null) {
            System.exit(0);
        }

        try {
            DataReader<Course> courseDataReader = getCourseDataReader(filePath.get("fileName"));
            Course course = courseDataReader.readData(filePathStr);

            System.out.printf("Select major-map file: %n %n");

            Map<String,String> filePathMap = FileChooser.selectFileWithExplorerDialog();
            String filePathMapStr = filePathMap.get("directory")+"/"+filePath.get("fileName");
            Map<String,String> fieldsOfStudy = new MajorMapReader().readData(filePathMapStr);

            course.displayAverageGradesFromCourse(fieldsOfStudy);

            course.addReport(filePathStr);
            System.out.printf("%nReport was created.%n");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }

    public static DataReader<Course> getCourseDataReader(String fileName) throws Exception {
        if (fileName.endsWith(".csv")) {
            return new CsvCourseDataReader();
        }
        if (fileName.endsWith(".txt")) {
            return new CsvCourseDataReader();
        }
        throw new Exception("The selected file format is not supported");
    }
}
