package logic;

public record RepeatingStudent(String name, String major,double examGrade) implements Student {

    @Override
    public double finalGrade(double preGradeFactor) {
        return examGrade;
    }
}
