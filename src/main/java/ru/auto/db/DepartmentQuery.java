package ru.auto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentQuery {

    static String queryFinDepartmentByName = "SELECT * FROM Department WHERE name = ?";
    static String queryDeleteDepartment = "DELETE FROM Department WHERE id = ?";

    public static int getIdDepartmentByName(Connection connect, String nameDepartment) throws SQLException {
        PreparedStatement preparedStatement = connect.prepareStatement(queryFinDepartmentByName);
        preparedStatement.setString(1, nameDepartment);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("ID");
    }

    public static int deleteDepartment(Connection connect, int id) throws SQLException {
        PreparedStatement preparedStatement = connect.prepareStatement(queryDeleteDepartment);
        preparedStatement.setInt( 1,id);
        return preparedStatement.executeUpdate();
    }
}
