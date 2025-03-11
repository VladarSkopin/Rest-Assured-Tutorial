package database_tests;

import helpers.DatabaseConfig;
import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TestDatabaseUpdates {

    private static final String DB_URL = DatabaseConfig.getDbUrl();
    private static final String DB_USER = DatabaseConfig.getDbUsername();
    private static final String DB_PASSWORD = DatabaseConfig.getDbPassword();
    private Connection connection;

    @BeforeTest(groups = {"db"})
    public void setUp() {
        DatabaseUtils.connectToDatabase(DB_URL, DB_USER, DB_PASSWORD);
        connection = DatabaseUtils.getConnection();
    }


    @Test(groups = {"db"})
    public void testDbAdvancedQueries() throws SQLException {
        String clientGeneralId = "MK-654";

        // expected db fields in the joined tables
        String expectedTitleShort = "VTB";
        String expectedTitleLong = "VTB Bank";
        String expectedCurrency = "DOLL";
        int expectedAmount = 150000;

        String sql = "SELECT client_general_id, title_short, title_long, currency, amount " +
                "FROM clients " +
                "JOIN financial_data ON clients.client_general_id = financial_data.client_common_id " +
                "WHERE clients.client_general_id = ? " +
                "ORDER BY financial_data.created DESC";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the parameter for the query
            statement.setString(1, clientGeneralId);

            // Execute the query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Validate the results
                boolean hasResults = false;
                while (resultSet.next()) {
                    hasResults = true;
                    String dbTitleShort = resultSet.getString("title_short");
                    String dbTitleLong = resultSet.getString("title_long");
                    String dbCurrency = resultSet.getString("currency");
                    int dbAmount = resultSet.getInt("amount");

                    Assert.assertEquals(dbTitleShort, expectedTitleShort, "Short titles for the client [ " + clientGeneralId + " ] don't match");
                    Assert.assertEquals(dbTitleLong, expectedTitleLong, "Long titles for the client [ " + clientGeneralId + " ] don't match");
                    Assert.assertEquals(dbCurrency, expectedCurrency, "Currencies for the client [ " + clientGeneralId + " ] don't match");
                    Assert.assertEquals(dbAmount, expectedAmount, "Amounts for the client [ " + clientGeneralId + " ] don't match");
                }

                // Ensure that at least one row was returned
                Assert.assertTrue(hasResults, "No results returned for the query");
            }
        }

    }


    @Test(groups = {"db"})
    public void testDbInsertionDeletion() {

        // Clear the old data first, just in case
        String clearQuery = "DELETE FROM financial_data WHERE client_common_id = ?";
        int rowsCleared = DatabaseUtils.executeUpdate(clearQuery, "MK-XXX");
        // TODO: Log cleared data at the start of the test


        String tableName = "financial_data";

        // Before insertion, the number of rows should be = 20
        int rowCountBeforeInsertion = DatabaseUtils.getRowCount(tableName);
        // TODO: Log current rowCount - "Row count BEFORE adding financial data"

        // Insert new financial data
        String insertQuery = "INSERT INTO financial_data (client_common_id, created, currency, amount, year, quarter) VALUES (?, ?, ?, ?, ?, ?)";
        int rowsInserted = DatabaseUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q1");
        rowsInserted += DatabaseUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q2");
        rowsInserted += DatabaseUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q3");
        rowsInserted += DatabaseUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q4");
        rowsInserted += DatabaseUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q1");
        Assert.assertEquals(rowsInserted, 5, "INSERTED rows count does not match the expected value");

        // After insertion, the number of rows should be 25
        int rowCountAfterInsertion = DatabaseUtils.getRowCount(tableName);
        // TODO: Log current rowCount - "Row count AFTER adding financial data"
        //int expectedRowCount = rowCount + rowsInserted;
        int expectedRowCount = rowCountBeforeInsertion + rowsInserted;
        Assert.assertEquals(rowCountAfterInsertion, expectedRowCount, "Row count AFTER adding financial data does not match the expected value");
        
        // Delete the new financial data
        String deleteQuery = "DELETE FROM financial_data WHERE client_common_id = ?";
        int rowsDeleted = DatabaseUtils.executeUpdate(deleteQuery, "MK-XXX");
        Assert.assertEquals(rowsDeleted, 5, "DELETED rows count does not match the expected value");

        // After deletion, the number of rows should be back to 20
        int rowCountAfterDeletion = DatabaseUtils.getRowCount(tableName);
        // TODO: Log current rowCount - "Row count AFTER DELETING financial data"
        Assert.assertEquals(rowCountAfterDeletion, rowCountBeforeInsertion, "Row count AFTER DELETING financial data does not match the expected value");
    }

    @AfterTest(groups = {"db"})
    public void tearDown() {
        DatabaseUtils.closeConnection();
    }
}
