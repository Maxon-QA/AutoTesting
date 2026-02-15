package ru.auto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainDB {
    static String url = "jdbc:h2:../Office/Office;IFEXISTS=TRUE";

    public static void main(String[] args) {

        try (Connection connect = DriverManager.getConnection(url)) {
            if (connect != null) {
                System.out.println("Подключение к БД прошло успешно");
                System.out.println();
            }

            /**
             * Задание 1-1 Найдите ID сотрудника с именем Ann.
             * Если такой сотрудник только один, то установите его департамент в HR.
             */
            EmployeeQuery.editUniqEmployeeByName(connect, "Ann", "HR");
            System.out.println("--------------------------------------------");

            /**
             * Задание 1-2 Проверьте имена всех сотрудников. Если чьё-то имя написано с маленькой буквы, исправьте её на большую.
             * Выведите на экран количество исправленных имён.
             */
            EmployeeQuery.setNameEmployeeUpperCase(connect);
            System.out.println("--------------------------------------------");

            /**
             * Задание 1-3 Выведите на экран количество сотрудников в IT-отделе
             */
            EmployeeQuery.printCountEmployeeInDepartment(connect, "IT");
            System.out.println("--------------------------------------------");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
