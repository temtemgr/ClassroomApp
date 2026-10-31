package logic;

import java.util.List;

public record RegularStudent(String name, String major, double examGrade, List<Double> preGrades) implements Student{

    public RegularStudent {
        preGrades = List.copyOf(preGrades);
    }

    @Override
    public double finalGrade(double preGradeFactor) {
        if (preGrades.isEmpty()) {
            return examGrade;
        }
        double averagePreGrade = averageWithoutLowest(preGrades);
        return (averagePreGrade * preGradeFactor + examGrade) / (1 + preGradeFactor);
    }

    private static double averageWithoutLowest(List<Double> grades) {
        if (grades.size() == 1) {
            return grades.getFirst();
        }

        double sum = 0;
        double lowestGrade = 6;
        for (double grade : grades) {
            if (lowestGrade > grade) {
                lowestGrade = grade;
            }
            sum += grade;
        }
        return (sum - lowestGrade) / (grades.size() - 1);
    }
}
