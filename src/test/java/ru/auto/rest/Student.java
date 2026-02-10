package ru.auto.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    Integer id;
    String name;
    Integer[] marks;

    public Student() {
    }

    public Student(Integer id, String name, Integer[] marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public Integer[] getMarks() {
        return marks;
    }

    public void setMarks(Integer[] marks) {
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.deepEquals(marks, student.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, Arrays.hashCode(marks));
    }
}
