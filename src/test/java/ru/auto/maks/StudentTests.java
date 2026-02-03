package ru.auto.maks;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class StudentTests {

    public final String name = "Maks";

    @BeforeAll
    public static void start() {
        System.out.println("Началось прохождение тестов...");
    }

    @AfterAll
    public static void finish() {
        System.out.println("Прохождение тестов завершено");
    }

    @Test
    @DisplayName("Установки/изменение имени студента")
    public void checkSetName() {
        Student student = new Student(null);
        student.setName(name);
        Assertions.assertEquals(name, student.getName());
    }

    @Test
    @DisplayName("Получения имени студента")
    public void checkGetName() {
        Student student = new Student(name);
        Assertions.assertEquals(name, student.getName());
    }

    @Test
    @DisplayName("Получение списка оценок")
    public void checkGetGrades() {
        Student student = new Student(name);
        student.addGrade(5);
        student.addGrade(4);
        student.addGrade(3);
        List<Integer> grades = new ArrayList<>(List.of(5, 4, 3));
        Assertions.assertEquals(grades, student.getGrades());
    }

    @ParameterizedTest(name = "Проверка добавления допустимых значений оценок")
    @MethodSource("ru.auto.maks.TestDataGenerator#getGradePositive")
    public void checkGradesInRange(int grade) {
        Student student = new Student(name);
        student.addGrade(grade);
        Assertions.assertEquals(grade, student.getGrades().get(0));
    }

    @ParameterizedTest(name = "Проверка добавления недопустимых значений оценок")
    @MethodSource("ru.auto.maks.TestDataGenerator#getGradeNegative")
    public void checkGradesInNotRange(int grade) {
        Student student = new Student(name);
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.addGrade(grade));
    }

    @Test
    @DisplayName("Проверка хеш-кода у одинаковых объектов")
    public void checkHashCodeEquals() {
        Student student1 = new Student(name);
        Student student2 = new Student(name);
        student1.addGrade(5);
        student2.addGrade(5);
        Assertions.assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Проверка хеш-кода у разных объектов")
    public void checkHashCodeNotEquals() {
        Student student1 = new Student(name);
        Student student2 = new Student(name);
        student1.addGrade(5);
        student2.addGrade(4);
        Assertions.assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Проверка эквивалентности одинаковых студентов")
    public void checkEquals() {
        Student student1 = new Student(name);
        Student student2 = new Student(name);
        student1.addGrade(5);
        student2.addGrade(5);
        Assertions.assertEquals(student1, student2);
    }

    @Test
    @DisplayName("Проверка неэквивалентности студентов с разными именами")
    public void checkNotEqualsName() {
        Student student1 = new Student(name);
        Student student2 = new Student(name + "Q");
        student1.addGrade(5);
        student2.addGrade(5);
        Assertions.assertNotEquals(student1, student2);
    }

    @Test
    @DisplayName("Проверка неэквивалентности студентов с разными оценками")
    public void checkNotEqualsGrade() {
        Student student1 = new Student(name);
        Student student2 = new Student(name);
        student1.addGrade(5);
        student2.addGrade(4);
        Assertions.assertNotEquals(student1, student2);
    }

    @Test
    @DisplayName("Установка полей конструктора")
    public void checkConstructorField() {
        Student student = new Student(name);
        Assertions.assertTrue(student.getGrades().isEmpty());
        Assertions.assertEquals(name, student.getName());
    }

    @Test
    @DisplayName("Проверка инкапсуляции списка оценок. Вариант №1")
    public void checkEncapsulateGrades() {
        Student student = new Student(name);
        student.getGrades().add(777);
        Assertions.assertTrue(student.getGrades().isEmpty());
    }

    @Test
    @DisplayName("Проверка инкапсуляции списка оценок. Вариант №2")
    public void checkEncapsulateGrades2() {
        Student student = new Student(name);
        Assertions.assertNotSame(student.getGrades(), student.getGrades());
    }

}
