package ui;

import logic.Course;
import logic.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StateModel {
    private Course course;
    private double preGradeFactor = 1;
    private boolean sortByGrade = false;
    private final List<StateObserver> observers = new ArrayList<>();

    public StateModel(Course initialCourse) {
        this.course = initialCourse;
    }

    // --- Course ---

    public Course getCourse() { return course; }

    public void setCourse(Course course) {
        this.course = course;
        sendStateChangedEvent();
    }

    // --- Factor ---

    public double getPreGradeFactor() {
        return preGradeFactor;
    }

    public void setPreGradeFactor(double preGradeFactor) {
        this.preGradeFactor = preGradeFactor;
        sendStateChangedEvent();
    }

    // --- Sort mode ---

    public void setSortByGrade(boolean sortByGrade) {
        this.sortByGrade = sortByGrade;
        sendStateChangedEvent();
    }

    // --- Sort Students and observer methods ---

    public List<Student> getSortedStudents() {
        List<Student> sorted = new ArrayList<>(course.students());
        if (sortByGrade) {
            sorted.sort(Comparator.comparingDouble((Student s) -> s.finalGrade(preGradeFactor)));
        }
        return sorted;
    }

    public void addObserver(StateObserver observer) {
        observers.add(observer);
    }

    private void sendStateChangedEvent() {
        for (StateObserver observer : observers) {
            observer.stateChanged();
        }
    }
}
