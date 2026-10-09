import io.CourseDataReader;
import io.MajorMapReader;
import logic.Course;

import java.awt.*;
import java.util.*;

class Main {

    public static void main(String[] args) {
        System.out.println("Select grades file:");
        CourseDataReader courseDataReader = new CourseDataReader();
        Course course = courseDataReader.createCourse();

        System.out.printf("Select major-map file: %n %n");
        Map<String,String> fieldsOfStudy = new MajorMapReader().createFieldsOfStudy();

        course.displayAverageGradesFromCourse(fieldsOfStudy);

        course.addReport(courseDataReader.getDirPath());
        System.out.printf("%nReport was created.%n");

        System.exit(0);
    }
}
