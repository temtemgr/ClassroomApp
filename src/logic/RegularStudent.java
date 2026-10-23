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
        double gradeAmount = grades.size();

        // Finds lowest grade
        for (double grade : grades) {
            if (lowestGrade > grade) {
                lowestGrade = grade;
            }
        }
        if (grades.size() > 1) {
            lowestGrade = 0;
            gradeAmount += 1;
        }

        // Calculates average grade
        for (double grade : grades) {
            gradeSum += grade;
        }
        averagePreGrade = (gradeSum-lowestGrade)/(gradeAmount-1);

        return ((averagePreGrade * preGradeFactor) + this.examGrade)/(1+preGradeFactor);
    }
}
