package ru.auto.db;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.auto.db.MainDB.url;

public class DepartmentTests {

    private static Connection connect;

    @BeforeAll
    public static void createConnect() {
        try {
            connect = DriverManager.getConnection(url);
            if (connect != null) {
                System.out.println("Подключение к БД прошло успешно");
                System.out.println();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @AfterAll
    public static void closeConnect() throws SQLException {
        connect.close();
    }

    @Test
    @DisplayName("Проверка каскадности при удалении департамента")
    public void checkCascadeDeleteDepartment() throws SQLException {
        int idDepartment = 2;

        int beforeDelete = EmployeeQuery.getCountEmployeeInDepartment(connect, idDepartment);
        Assertions.assertTrue(DepartmentQuery.deleteDepartment(connect, idDepartment) > 0, "Отдел не удалился");

        int afterDelete = EmployeeQuery.getCountEmployeeInDepartment(connect, idDepartment);
        Assertions.assertTrue(beforeDelete > afterDelete, "Сотрудники не удалились");
    }
}

