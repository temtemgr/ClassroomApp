package logic;

import io.DataReadException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Course(String id, String name, List<Student> students) {

    public void displayAverageGradesFromCourse(Map<String,String>  fieldsOfStudy) {
        System.out.printf("%s %n%s %n %n", id, name); // id -> new line -> name -> 2x new line

        for (Student student: students) {
            String name = student.name();
            String fieldOfStudyAbb = student.major();
            double averageGrade = student.finalGrade(1);

            System.out.printf("The average grade for %s (%s) is: %.2f %n", name, fieldsOfStudy.get(fieldOfStudyAbb), averageGrade);
        }
    }

    public void addReport(Path gradesFile) throws DataReadException {
        List<String> reportText = new ArrayList<>();

        reportText.add(this.name +" ("+ this.id +")");
        for (Student student : this.students) {
            reportText.add(student.name() +": "+ String.format("%.2f", student.finalGrade(1)));
        }

        try {
            Files.write(gradesFile.getParent(), reportText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new DataReadException(e.getMessage());
        }
    }
}
