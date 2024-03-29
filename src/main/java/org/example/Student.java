package org.example;

import java.util.Objects;

public class Student {
    private int studentId;
    private String studentName;
    private double budget;

    public Student(int studentId, String studentName, double budget) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.budget = budget;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) throws BugetInvalidException {
        if (budget < 0){
            throw new BugetInvalidException("Studentul nu are suficienti bani!");
        }
        this.budget = budget;
    }

    @Override
    public String toString() {
        return studentId+","+studentName+","+budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getStudentId() == student.getStudentId() && Double.compare(student.getBudget(), getBudget()) == 0 && Objects.equals(getStudentName(), student.getStudentName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getStudentName(), getBudget());
    }
}
