package logic;

import java.util.List;

public class Student {
    private String name;
    private String major;
    private List<Double> grades;
    private double averageGrade;

    public Student(String name,String fieldOfStudyAbb, List<Double> grades) {
        this.name = name;
        this.major = fieldOfStudyAbb;
        this.grades = grades;
        this.setAverageGrade();
    }

    public String getName(){
        return this.name;
    }

    public String getMajor(){
        return this.major;
    }

    public double getAverageGrade() {
        return this.averageGrade;
    }

    private void setAverageGrade() {
        List<Double> grades = this.grades;
        double averageGrade;
        double gradeSum = 0;
        double lowestGrade = 6;

        // Finds lowest grade
        for (double grade : grades) {
            if (lowestGrade > grade) {
                lowestGrade = grade;
            }
        }
        if (grades.size() > 1) {
            grades.remove(lowestGrade);
        }

        // Calculates average grade
        for (double grade : grades) {
            gradeSum += grade;
        }
        double gradeAmount = grades.size();
        averageGrade = gradeSum/gradeAmount;

        // Sets averageGrade
        this.averageGrade = averageGrade;
    }
}
