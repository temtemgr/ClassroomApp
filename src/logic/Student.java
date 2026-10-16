package logic;

import java.util.List;

public class Student {
    private String name;
    private String major;
    protected double examGrade;

    public Student(String name,String fieldOfStudyAbb, double examGrade) {
        this.name = name;
        this.major = fieldOfStudyAbb;
        this.examGrade = examGrade;
    }

    public String getName(){
        return this.name;
    }

    public String getMajor(){
        return this.major;
    }

    public double getFinalGrade(double preGradeFactor) {
        return 0;
    }
}
