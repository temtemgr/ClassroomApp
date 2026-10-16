package logic;

public class RepeatingStudent extends Student{
    public RepeatingStudent(String name, String fieldOfStudyAbb,double examGrade) {
        super(name, fieldOfStudyAbb, examGrade);
    }

    @Override
    public double getFinalGrade(double preGradeFactor) {
        return this.examGrade;
    }
}
