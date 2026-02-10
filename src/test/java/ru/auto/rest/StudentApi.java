package ru.auto.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.List;


@Slf4j
public class StudentApi {

    private static final String HOST = "http://localhost:8080";
    private static final String ENDPOINT_STUDENT = "/student";
    private static final String ENDPOINT_TOP_STUDENT = "/topStudent";

    public static ValidatableResponse postStudent(Student student, Integer respCode) {
        return RestAssured.given()
                .log().all()
                .baseUri(HOST + ENDPOINT_STUDENT)
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(respCode);
    }

    public static ValidatableResponse getStudent(Student student, Integer respCode) {
        return RestAssured
                .given()
                .log().all()
                .baseUri(HOST + ENDPOINT_STUDENT + "/" + student.getId())
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(respCode);
    }

    public static ValidatableResponse deleteStudent(Student student, Integer respCode) {
        return
                RestAssured.given()
                        .log().all()
                        .baseUri(HOST + ENDPOINT_STUDENT + "/" + student.getId())
                        .when()
                        .delete()
                        .then()
                        .log().all()
                        .statusCode(respCode);
    }

    public static void deleteAllStudent(List<Integer> list) {
        list.forEach(id ->
                RestAssured.given()
                        .log().all()
                        .baseUri(HOST + ENDPOINT_STUDENT + "/" + id)
                        .when()
                        .delete()
                        .then()
                        .log().all());
        log.info("Очищение списка студентов завершено!");
    }

    public static ValidatableResponse getTopStudent(Integer respCode) {
        return
                RestAssured.given()
                        .log().all()
                        .baseUri(HOST + ENDPOINT_TOP_STUDENT)
                        .when()
                        .get()
                        .then()
                        .log().all()
                        .statusCode(respCode);
    }
}


