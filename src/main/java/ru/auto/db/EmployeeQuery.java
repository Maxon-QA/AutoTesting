package ru.auto.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeQuery {

    private static String queryFindEmployeeByName = "SELECT * FROM Employee WHERE name = ?";
    private static String queryFindAllEpEmployee = "SELECT * FROM Employee";
    private static String queryFindEmployeeByDepartmentName =
            """
            SELECT count(*) FROM Employee JOIN Department
            ON Employee.DepartmentID = Department.ID
            WHERE Department.Name = ?
            """;

    public static void editUniqEmployeeByName(Connection connect, String nameEmployee, String nameDepartment) throws SQLException {
        List<Integer> idEmployee = new ArrayList<>();

        PreparedStatement preparedStatement = connect.prepareStatement(
                queryFindEmployeeByName
                , ResultSet.TYPE_SCROLL_INSENSITIVE
                , ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setString(1, nameEmployee);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            idEmployee.add(resultSet.getInt("ID"));
            if (idEmployee.size() > 1) {
                System.out.println("Сотрудников " + nameEmployee + " больше чем 1");
                return;
            }
        }

        if (idEmployee.size() == 1) {
            int idDepartment = DepartmentQuery.getIdDepartmentByName(connect, nameDepartment);
            resultSet.first();
            resultSet.updateInt("DepartmentID", idDepartment);
            resultSet.updateRow();
            System.out.println("Обновление департамента прошло успешно");
        } else System.out.println("Сотрудники с именем " + nameEmployee + " не найдены");
    }

    public static void setNameEmployeeUpperCase(Connection connect) throws SQLException {
        Statement statement = connect.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE
                , ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(queryFindAllEpEmployee);
        int count = 0;
        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            if (Character.isLowerCase(name.charAt(0))) {
                resultSet.updateString("Name", name.substring(0, 1).toUpperCase() + name.substring(1));
                resultSet.updateRow();
                count++;
            }
        }
        System.out.println("Количество исправленных имен: " + count);
    }

    public static void printCountEmployeeInDepartment(Connection connect, String nameDepartment) throws SQLException {
        PreparedStatement preparedStatement = connect.prepareStatement(queryFindEmployeeByDepartmentName);
        preparedStatement.setString(1, nameDepartment);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.println("Количество сотрудников в отделе " + nameDepartment + " = " + resultSet.getString(1));
    }
}


