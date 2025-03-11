package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    public static void connectToDatabase(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    // Method to execute a query (SELECT)
    public static ResultSet executeQuery(String query) {
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to execute an update (INSERT, UPDATE, DELETE)
    public static int executeUpdate(String query, Object... params) {
        int rowsAffected = 0;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            // Set parameters for the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // Execute the update
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // General method to execute ANY update SQL statement
    public static int executeUpdate(String query) {
        int rowsAffected = 0;
        try {
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int getRowCount(String tableName) {
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM " + tableName;

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    public static List<Object> getColumnValues(String query, String columnName) {
        List<Object> values = new ArrayList<>();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                values.add(resultSet.getObject(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return values;
    }


    public static void closeConnection() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
