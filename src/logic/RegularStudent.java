package logic;

import java.util.List;

public class RegularStudent extends Student{
    private List<Double> preGrades;
    private double averagePreGrade;

    public RegularStudent(String name, String fieldOfStudyAbb, double examGrade, List<Double> grades) {
        super(name, fieldOfStudyAbb, examGrade);
        this.preGrades = grades;
        this.setAveragePreGrade();
    }

    private void setAveragePreGrade() {
        List<Double> grades = this.preGrades;
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
        this.averagePreGrade = averageGrade;
    }

    @Override
    public double getFinalGrade(double preGradeFactor) {
        return ((this.averagePreGrade * preGradeFactor) + this.examGrade)/(1+preGradeFactor);
    }
}
