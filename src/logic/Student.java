package logic;

// sealed -> can only be interface from the defined permitted classes + they must be final / sealed / non-sealed

public abstract sealed interface Student permits RegularStudent, RepeatingStudent {
    String name();
    String major();
    double examGrade();
    double finalGrade(double preGradeFactor);
}
