package logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Course {
    private String id;
    private String name;
    private List<Student> students;

    public Course(String id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public void displayAverageGradesFromCourse(Map<String,String>  fieldsOfStudy) {
        System.out.printf("%s %n%s %n %n", id, name); // id -> new line -> name -> 2x new line

        for (Student student: students) {
            String name = student.getName();
            String fieldOfStudyAbb = student.getMajor();
            double averageGrade = student.getAverageGrade();

            System.out.printf("The average grade for %s (%s) is: %.2f %n", name, fieldsOfStudy.get(fieldOfStudyAbb), averageGrade);
        }
    }

    public void addReport(String dirPath) {
        List<String> reportText = new ArrayList<>();
        String filePath = dirPath + "/grades-report.txt";

        reportText.add(this.name +" ("+ this.id +")");
        for (Student student : this.students) {
            reportText.add(student.getName() +": "+ String.format("%.2f", student.getAverageGrade()));
        }

        try {
            Files.write(Path.of(filePath), reportText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("An Error occurred: " + e.getMessage());
        }
    }
}
