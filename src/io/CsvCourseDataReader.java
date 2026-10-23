package io;

import logic.Course;
import logic.RegularStudent;
import logic.RepeatingStudent;
import logic.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CsvCourseDataReader implements DataReader<Course> {

    @Override
    public Course readData(String path) {
        List<String> text = new ArrayList<>();
        File dataFile = new File(path);

        // Scans file
        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNextLine()) {
                text.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return this.parseCourse(text);

    }

    public Course parseCourse(List<String> text) {
        String[] courseArray = { text.get(0), text.get(1) };
        text.removeFirst();
        text.removeFirst();

        List<Student> students = parseStudents(text);
        return new Course(courseArray[0].trim(), courseArray[1].trim(), students);
    }

    private List<Student> parseStudents(List<String> studentsText) {
        List<Student> students = new ArrayList<>();

        for (String student: studentsText) {
            String[] parts = student.split(",");

            if (parts.length < 4) {
                System.out.println("Not enough grades found in this file.");
                System.exit(1);
            }

            String name = parts[0];
            String fieldOfStudyAbb = parts[1].trim();
            boolean repeatingStudent = parts[2].trim().equals("r");
            double examGrade = Double.parseDouble(parts[3].trim());

            if (repeatingStudent) {
                students.add(new RepeatingStudent(name, fieldOfStudyAbb, examGrade));
                continue;
            }

            List<Double> grades = new ArrayList<>();
            for (int i = 4; i < parts.length; i++) {
                double value;

                try {
                    value = Double.parseDouble(parts[i]);
                }
                catch (NumberFormatException e) {
                    System.out.printf("Error: A grade is not a number - %s", e.getMessage());
                    System.exit(1);
                    break;
                }
                grades.add(value);
            }

            students.add(new RegularStudent(name, fieldOfStudyAbb, examGrade, grades));
        }
        return students;
    }
}
