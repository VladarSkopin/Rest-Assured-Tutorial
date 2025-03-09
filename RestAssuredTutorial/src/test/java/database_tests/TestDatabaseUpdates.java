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


    // TODO: implement COUNT, INSERT and DELETE
    @Test(groups = {"db"})
    public void testDbInsertionDeletion() {


        ResultSet resultSet = DatabaseUtils.executeQuery("SELECT * FROM financial_data");

        try {
            if (resultSet.next()) {

            } else {
                Assert.fail("Financial data not found in the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterTest(groups = {"db"})
    public void tearDown() {
        DatabaseUtils.closeConnection();
    }
}
