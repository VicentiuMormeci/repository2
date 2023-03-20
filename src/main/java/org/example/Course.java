package org.example;

import java.time.LocalDate;
import java.util.Objects;

public class Course {

    private int courseId;
    private String courseName;
    private Double price;
    private LocalDate startDate;


    public Course(int courseId, String courseName, Double price, LocalDate startDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.price = price;
        this.startDate = startDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return courseId+","+courseName+","+price+","+startDate;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCourseId() == course.getCourseId() && Objects.equals(getCourseName(), course.getCourseName()) && Objects.equals(getPrice(), course.getPrice()) && Objects.equals(getStartDate(), course.getStartDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getCourseName(), getPrice(), getStartDate());
    }
}
