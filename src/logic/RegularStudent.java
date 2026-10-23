package logic;

import java.util.List;

public record RegularStudent(String name, String major, double examGrade, List<Double> preGrades) implements Student{

    public RegularStudent {
        preGrades = List.copyOf(preGrades);
    }

    @Override
    public double finalGrade(double preGradeFactor) {

        List<Double> grades = this.preGrades;
        double averagePreGrade;
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
        averagePreGrade = gradeSum/gradeAmount;

        return ((averagePreGrade * preGradeFactor) + this.examGrade)/(1+preGradeFactor);
    }
}
