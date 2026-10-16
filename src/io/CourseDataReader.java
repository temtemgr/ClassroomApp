package io;

import logic.Course;
import logic.RegularStudent;
import logic.RepeatingStudent;
import logic.Student;

import java.util.ArrayList;
import java.util.List;

public class CourseDataReader extends DataReader{

    public CourseDataReader() {
        super();
    }

    public Course createCourse() {
        List<String> text = this.dataText;
        String[] courseArray = { text.get(0), text.get(1) };
        text.removeFirst();
        text.removeFirst();

        List<Student> students = createStudents(text);
        return new Course(courseArray[0].trim(), courseArray[1].trim(), students);
    }

    private List<Student> createStudents(List<String> studentsText) {
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
