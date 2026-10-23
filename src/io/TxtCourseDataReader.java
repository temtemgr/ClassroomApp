package io;

import logic.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtCourseDataReader implements DataReader<Course> {

    @Override
    public Course readData(Path file) throws DataReadException {
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new DataReadException("Could not read file " + file + ": " + e);
        }

        Map<String, String> course = new HashMap<String, String>();
        List<Map<String, String>> studentBlocks = new ArrayList<Map<String, String>>();

        for (String line : lines) {

            String[] parts = line.split(":", 2);
            if (parts.length != 2) {
                throw new DataReadException("Wrong file format");
            }

            String key = parts[0].trim().toLowerCase();
            String value = parts[1].trim();

            if (key.startsWith("course-")) {
                course.put(key, value);
            } else {
                if (key.equals("name")) {
                    studentBlocks.add(new HashMap<String, String>());   // "name:" starts a new student
                }
                if (studentBlocks.isEmpty()) {
                    throw new DataReadException("Wrong file format");
                }
                studentBlocks.getLast().put(key, value);
            }
        }

        List<Student> students = new ArrayList<Student>();
        for (Map<String, String> block : studentBlocks) {
            students.add(parseStudent(block));
        }
        return new Course(course.get("course-id"), course.get("course-name"), students);
    }

    private Student parseStudent(Map<String, String> block) throws DataReadException {
        String name = block.get("name");
        String major = block.get("major");
        double examGrade = parseGrade(block.get("exam-grade"));

        if (block.get("is_repeating").equals("true")) {
            return new RepeatingStudent(name, major, examGrade);
        }

        List<Double> preGrades = new ArrayList<Double>();
        for (String grade : block.get("pre-grade").split(",")) {
            if (!grade.isBlank()) {
                preGrades.add(parseGrade(grade.trim()));
            }
        }
        return new RegularStudent(name, major, examGrade, preGrades);
    }

    private double parseGrade(String value) throws DataReadException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new DataReadException(value + " is not a grade");
        }
    }
}
