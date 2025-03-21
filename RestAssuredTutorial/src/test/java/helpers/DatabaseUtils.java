package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    private static DatabaseUtils instance;

    private Connection connection;


    // Private constructor to prevent instantiation
    private DatabaseUtils() {
        final String DB_URL = DatabaseConfig.getDbUrl();
        final String DB_USER = DatabaseConfig.getDbUsername();
        final String DB_PASSWORD = DatabaseConfig.getDbPassword();

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Database connection established.");
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils constructor occurred: ", e);
        }
    }


    // Thread-safe method to get the Singleton instance
    public static synchronized DatabaseUtils getInstance() {
        if (instance == null) {
            instance = new DatabaseUtils();
        }
        return instance;
    }


    // Method to execute a query (SELECT) with parameters
    public ResultSet executeQuery(String query, Object... params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"executeQuery(String query, Object... params)\" method occurred: ", e);
        }
        return resultSet;
    }

    // Method to execute ANY query (SELECT)
    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"executeQuery(String query)\" method occurred: ", e);
        }
        return resultSet;
    }

    // Method to execute an update (INSERT, UPDATE, DELETE) with parameters
    public int executeUpdate(String query, Object... params) {
        int rowsAffected = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // Execute the update
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"executeUpdate(String query, Object... params)\" method occurred: ", e);
        }

        return rowsAffected;
    }

    // General method to execute ANY update SQL statement
    public int executeUpdate(String query) {
        int rowsAffected = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            rowsAffected = preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"executeUpdate(String query)\" method occurred: ", e);
        }
        return rowsAffected;
    }

    public int getRowCount(String tableName) {
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM " + tableName;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"getRowCount(String tableName)\" method occurred: ", e);
        }

        return rowCount;
    }

    public List<Object> getColumnValues(String query, String columnName) {
        List<Object> values = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                values.add(resultSet.getObject(columnName));
            }
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"getColumnValues(String query, String columnName)\" method occurred: ", e);
        }

        return values;
    }


    public void closeConnection() {
        try {
            if (connection != null) connection.close();
            logger.info("Database connection closed.");
        } catch (SQLException e) {
            logger.error("Error in DatabaseUtils \"closeConnection()\" method occurred: ", e);
        }
    }
}
