package io;

import logic.Course;
import logic.RegularStudent;
import logic.RepeatingStudent;
import logic.Student;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CsvCourseDataReader implements DataReader<Course> {

    @Override
    public Course readData(Path file) throws DataReadException {

        List<String> text;
        try {
            text = Files.readAllLines(file);
        } catch (IOException e) {
            throw new DataReadException(e.getMessage());
        }

        String[] courseArray = { text.get(0), text.get(1) };
        List<Student> students = parseStudents(text);
        return new Course(courseArray[0].trim(), courseArray[1].trim(), students);
    }

    private List<Student> parseStudents(List<String> studentsText) throws DataReadException {
        List<Student> students = new ArrayList<>();

        for (String student: studentsText) {
            String[] parts = student.split(",");

            if (parts.length < 4) {
                throw new DataReadException("Not enough grades found in this file.");
            }

            String name = parts[0];
            String fieldOfStudyAbb = parts[1].trim();
            boolean repeatingStudent = parts[2].trim().equals("r");
            double examGrade;
            try {
                examGrade = Double.parseDouble(parts[3].trim());
            } catch (NumberFormatException e) {
                throw new DataReadException("The exam grade is not a number - " + e.getMessage());
            }

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
                    throw new DataReadException("A grade is not a number - " + e.getMessage());
                }
                grades.add(value);
            }

            students.add(new RegularStudent(name, fieldOfStudyAbb, examGrade, grades));
        }
        return students;
    }
}
