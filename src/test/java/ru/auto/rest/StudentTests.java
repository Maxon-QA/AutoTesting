package ru.auto.rest;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentTests {

    private final SoftAssertions softAssertions = new SoftAssertions();
    private final List<Integer> ALL_STUDENTS = new ArrayList<>();


    @AfterEach
    public void deleteAllStudent() {
        StudentApi.deleteAllStudent(ALL_STUDENTS);
        ALL_STUDENTS.clear();
    }

    @AfterEach
    public void workSoftAssert() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("1. get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.")
    public void getStudentExist() {
        Integer id = 1;
        String name = "Student_with_id1";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodePOST = 201;
        int respCodeGET = 200;

        ALL_STUDENTS.add(student.getId());
        StudentApi.postStudent(student, respCodePOST);

        ValidatableResponse respond = StudentApi.getStudent(student, respCodeGET);
        Student studentRespond = respond
                .contentType(ContentType.JSON)
                .extract().as(Student.class);

        softAssertions.assertThat(studentRespond.getId())
                .as("id должны совпадать")
                .isEqualTo(id);

        softAssertions.assertThat(studentRespond.getName())
                .as("Имена должны совпадать")
                .isEqualTo(name);

        softAssertions.assertThat(Arrays.toString(studentRespond.getMarks()))
                .as("Оценки должны совпадать")
                .isEqualTo(Arrays.toString(marks));
    }

    @Test
    @DisplayName("2. get /student/{id} возвращает код 404, если студента с данным ID в базе нет.")
    public void getStudentNotExist() {
        Integer id = 2;
        String name = "Student_with_id2";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodeGET = 404;

        ValidatableResponse respondGet = StudentApi.getStudent(student, respCodeGET);

        softAssertions.assertThat(respondGet.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();
    }

    @Test
    @DisplayName("3. post /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201.")
    public void createStudent() {

        Integer id = 3;
        String name = "Student_with_id3";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodePOST = 201;
        int respCodeGET = 200;

        ALL_STUDENTS.add(student.getId());
        ValidatableResponse respondPost = StudentApi.postStudent(student, respCodePOST);

        softAssertions.assertThat(respondPost.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();

        StudentApi.getStudent(student, respCodeGET); //не смог обернуть в софт ассерт
    }

    @Test
    @DisplayName("4. post /student обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.")
    public void updateStudent() {
        Integer id = 4;
        String name = "Student_with_id4";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodePOST = 201;
        int respCodeGET = 200;

        ALL_STUDENTS.add(student.getId());
        StudentApi.postStudent(student, respCodePOST);
        ValidatableResponse respondPost = StudentApi.postStudent(student, respCodePOST);

        softAssertions.assertThat(respondPost.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();

        StudentApi.getStudent(student, respCodeGET); //не смог обернуть в софт ассерт
    }

    @Test
    @DisplayName("5. post /student добавляет студента в базу, если ID null, то возвращается назначенный ID, код 201.")
    public void createStudentWithoutID() {
        Integer id = null;
        String name = "Student_with_new_id";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodePOST = 201;
        int respCodeGET = 200;

        ValidatableResponse respond = StudentApi.postStudent(student, respCodePOST);

        student.setId(respond.extract().as(Integer.class));
        ALL_STUDENTS.add(student.getId()); //должно быть именно тут в данном методе

        softAssertions.assertThat(student.getId())
                .as("Присвоенный id должен быть положительным числом ")
                .isGreaterThan(0);

        StudentApi.getStudent(student, respCodeGET); //не смог обернуть в софт ассерт
    }

    @Test
    @DisplayName("6. post /student возвращает код 400, если имя не заполнено.")
    public void createStudentWithoutName() {
        Integer id = 6;
        String name = null;
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodePOST = 400;
        int respCodeGET = 404;

        ALL_STUDENTS.add(student.getId());
        ValidatableResponse respondPost = StudentApi.postStudent(student, respCodePOST);

        softAssertions.assertThat(respondPost.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();

        StudentApi.getStudent(student, respCodeGET); //не смог обернуть в софт ассерт
    }

    @Test
    @DisplayName("7. delete /student/{id} удаляет студента с указанным ID из базы, код 200.")
    public void deleteStudent() {
        Integer id = 7;
        String name = "Student_with_id7";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodeDelete = 200;
        int respCodePOST = 201;
        int respCodeGET = 404;

        ALL_STUDENTS.add(student.getId());
        StudentApi.postStudent(student, respCodePOST);
        ValidatableResponse respondDelete = StudentApi.deleteStudent(student, respCodeDelete);

        softAssertions.assertThat(respondDelete.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();

        StudentApi.getStudent(student, respCodeGET); //не смог обернуть в софт ассерт
    }

    @Test
    @DisplayName("8. delete /student/{id} возвращает код 404, если студента с таким ID в базе нет.")
    public void deleteStudentNotExist() {
        Integer id = 8;
        String name = "Student_with_id8";
        Integer[] marks = new Integer[]{3, 4, 5};
        Student student = new Student(id, name, marks);

        int respCodeDelete = 404;
        int respCodeGET = 404;

        ValidatableResponse respondDelete = StudentApi.deleteStudent(student, respCodeDelete);

        softAssertions.assertThat(respondDelete.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();

        StudentApi.getStudent(student, respCodeGET); //не смог обернуть в софт ассерт
    }

    @Test
    @DisplayName("9. get /topStudent код 200 и пустое тело, если студентов в базе нет.")
    public void topStudentEmpty() {

        int respCodeGETtop = 200;

        ValidatableResponse respondGetTop = StudentApi.getTopStudent(respCodeGETtop);

        softAssertions.assertThat(respondGetTop.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();
    }

    @Test
    @DisplayName("10. get /topStudent код 200 и пустое тело, если ни у кого из студентов в базе нет оценок.")
    public void topStudentEmptyMarks() {
        Integer id = 100;
        Integer id2 = 101;
        String name = "Student_with_id100-101";
        Integer[] marks = null;

        Student student = new Student(id, name, marks);
        Student student2 = new Student(id2, name, marks);

        int respCodePOST = 201;
        int respCodeGETtop = 200;

        ALL_STUDENTS.add(student.getId());
        StudentApi.postStudent(student, respCodePOST);

        ALL_STUDENTS.add(student2.getId());
        StudentApi.postStudent(student2, respCodePOST);

        ValidatableResponse respondGetTop = StudentApi.getTopStudent(respCodeGETtop);

        softAssertions.assertThat(respondGetTop.extract().asString())
                .as("Строка должна быть пустая")
                .isEmpty();
    }

    @Test
    @DisplayName("11. get /topStudent код 200 и один студент, если у него максимальная средняя оценка, " +
            "либо же среди всех студентов с максимальной средней у него их больше всего.")
    public void topStudentOne() {
        Integer id = 110;
        Integer id2 = 111;
        Integer id3 = 112;
        String name = "Student_with_id110-122";
        Integer[] marks = new Integer[]{3, 3, 4, 4, 5, 5};
        Integer[] mark2 = new Integer[]{2, 2, 2, 2};
        Integer[] mark3 = new Integer[]{3, 4, 5};

        Student student = new Student(id, name, marks);
        Student student2 = new Student(id2, name, mark2);
        Student student3 = new Student(id3, name, mark3);

        int respCodePOST = 201;
        int respCodeGETtop = 200;

        ALL_STUDENTS.add(student.getId());
        StudentApi.postStudent(student, respCodePOST);

        ALL_STUDENTS.add(student2.getId());
        StudentApi.postStudent(student2, respCodePOST);

        ALL_STUDENTS.add(student3.getId());
        StudentApi.postStudent(student3, respCodePOST);

        ValidatableResponse respond = StudentApi.getTopStudent(respCodeGETtop);
        List<Student> studentRespond = respond
                .contentType(ContentType.JSON)
                .extract().jsonPath().getList(".", Student.class);

        softAssertions.assertThat(studentRespond.size())
                .as("Должен был вернуться один студент")
                .isEqualTo(1);

        softAssertions.assertThat(studentRespond.get(0).getId())
                .as("id должны совпадать")
                .isEqualTo(id);

        softAssertions.assertThat(studentRespond.get(0).getName())
                .as("Имена должны совпадать")
                .isEqualTo(name);

        softAssertions.assertThat(Arrays.toString(studentRespond.get(0).getMarks()))
                .as("Оценки должны совпадать")
                .isEqualTo(Arrays.toString(marks));
    }

    @Test
    @DisplayName("12. get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная " +
            "и при этом они равны по количеству оценок.")
    public void topStudentFew() {
        Integer id = 120;
        Integer id2 = 121;
        Integer id3 = 122;
        String name = "Student_with_id120-122";
        Integer[] marks = new Integer[]{3, 3, 4, 4, 5, 5};
        Integer[] mark2 = new Integer[]{5, 5, 4, 4, 3, 3};
        Integer[] mark3 = new Integer[]{3, 4, 5, 2};

        Student student = new Student(id, name, marks);
        Student student2 = new Student(id2, name, mark2);
        Student student3 = new Student(id3, name, mark3);

        int respCodePOST = 201;
        int respCodeGETtop = 200;

        ALL_STUDENTS.add(student.getId());
        StudentApi.postStudent(student, respCodePOST);

        ALL_STUDENTS.add(student2.getId());
        StudentApi.postStudent(student2, respCodePOST);

        ALL_STUDENTS.add(student3.getId());
        StudentApi.postStudent(student3, respCodePOST);

        ValidatableResponse respond = StudentApi.getTopStudent(respCodeGETtop);
        List<Student> studentRespond = respond
                .contentType(ContentType.JSON)
                .extract().jsonPath().getList(".", Student.class);

        softAssertions.assertThat(studentRespond.size())
                .as("В ответе должно быть два студента")
                .isEqualTo(2);

        softAssertions.assertThat(Arrays.toString(studentRespond.get(0).getMarks()))
                .as("Оценки должны совпадать")
                .isEqualTo(Arrays.toString(studentRespond.get(0).getMarks()));

        softAssertions.assertThat(Arrays.toString(marks))
                .as("Оценки должны совпадать")
                .isEqualTo(Arrays.toString(studentRespond.get(0).getMarks()));
    }
}

