package ru.auto.maks;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class old_StudentTests {

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
        old_Student student = new old_Student(null);
        student.setName(name);
        Assertions.assertEquals(name, student.getName());
    }

    @Test
    @DisplayName("Получения имени студента")
    public void checkGetName() {
        old_Student student = new old_Student(name);
        Assertions.assertEquals(name, student.getName());
    }

    @Test
    @DisplayName("Получение списка оценок")
    public void checkGetGrades() {
        old_Student student = new old_Student(name);
        student.addGrade(5);
        student.addGrade(4);
        student.addGrade(3);
        List<Integer> grades = new ArrayList<>(List.of(5, 4, 3));
        Assertions.assertEquals(grades, student.getGrades());
    }

    @ParameterizedTest(name = "Проверка добавления допустимых значений оценок")
    @MethodSource("ru.auto.maks.TestDataGenerator#getGradePositive")
    public void checkGradesInRange(int grade) {
        old_Student student = new old_Student(name);
        student.addGrade(grade);
        Assertions.assertEquals(grade, student.getGrades().get(0));
    }

    @ParameterizedTest(name = "Проверка добавления недопустимых значений оценок")
    @MethodSource("ru.auto.maks.TestDataGenerator#getGradeNegative")
    public void checkGradesInNotRange(int grade) {
        old_Student student = new old_Student(name);
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.addGrade(grade));
    }

    @Test
    @DisplayName("Проверка хеш-кода у одинаковых объектов")
    public void checkHashCodeEquals() {
        old_Student student1 = new old_Student(name);
        old_Student student2 = new old_Student(name);
        student1.addGrade(5);
        student2.addGrade(5);
        Assertions.assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Проверка хеш-кода у разных объектов")
    public void checkHashCodeNotEquals() {
        old_Student student1 = new old_Student(name);
        old_Student student2 = new old_Student(name);
        student1.addGrade(5);
        student2.addGrade(4);
        Assertions.assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Проверка эквивалентности одинаковых студентов")
    public void checkEquals() {
        old_Student student1 = new old_Student(name);
        old_Student student2 = new old_Student(name);
        student1.addGrade(5);
        student2.addGrade(5);
        Assertions.assertEquals(student1, student2);
    }

    @Test
    @DisplayName("Проверка неэквивалентности студентов с разными именами")
    public void checkNotEqualsName() {
        old_Student student1 = new old_Student(name);
        old_Student student2 = new old_Student(name + "Q");
        student1.addGrade(5);
        student2.addGrade(5);
        Assertions.assertNotEquals(student1, student2);
    }

    @Test
    @DisplayName("Проверка неэквивалентности студентов с разными оценками")
    public void checkNotEqualsGrade() {
        old_Student student1 = new old_Student(name);
        old_Student student2 = new old_Student(name);
        student1.addGrade(5);
        student2.addGrade(4);
        Assertions.assertNotEquals(student1, student2);
    }

    @Test
    @DisplayName("Установка полей конструктора")
    public void checkConstructorField() {
        old_Student student = new old_Student(name);
        Assertions.assertTrue(student.getGrades().isEmpty());
        Assertions.assertEquals(name, student.getName());
    }

    @Test
    @DisplayName("Проверка инкапсуляции списка оценок. Вариант №1")
    public void checkEncapsulateGrades() {
        old_Student student = new old_Student(name);
        student.getGrades().add(777);
        Assertions.assertTrue(student.getGrades().isEmpty());
    }

    @Test
    @DisplayName("Проверка инкапсуляции списка оценок. Вариант №2")
    public void checkEncapsulateGrades2() {
        old_Student student = new old_Student(name);
        Assertions.assertNotSame(student.getGrades(), student.getGrades());
    }

}
