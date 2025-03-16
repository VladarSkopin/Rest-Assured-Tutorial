package database_tests;

import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TestDatabaseAdvanced {

    private static final Logger logger = LoggerFactory.getLogger(TestDatabaseAdvanced.class);

    private DatabaseUtils dbUtils;


    @BeforeSuite(groups = {"db"})
    public void setUp() {
        dbUtils = DatabaseUtils.getInstance();
    }


    @Test(groups = {"db"})
    public void testDbAdvancedQueries() throws SQLException {

        logger.info("testDbAdvancedQueries started");

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

        ResultSet resultSet = dbUtils.executeQuery(sql, clientGeneralId);

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

        logger.info("testDbAdvancedQueries ended");
    }


    @Test(groups = {"db"})
    public void testDbInsertionDeletion() {

        logger.info("testDbInsertionDeletion started");

        // Clear the old data first, just in case
        String clearQuery = "DELETE FROM financial_data WHERE client_common_id = ?";
        int rowsCleared = dbUtils.executeUpdate(clearQuery, "MK-XXX");
        logger.debug("Cleared data at the start of the test - rowsCleared = {}", rowsCleared);

        String tableName = "financial_data";

        // Before insertion, the number of rows should be = 20
        int rowCountBeforeInsertion = dbUtils.getRowCount(tableName);
        logger.debug("Row count BEFORE adding financial data = {}", rowCountBeforeInsertion);

        // Insert new financial data
        String insertQuery = "INSERT INTO financial_data (client_common_id, created, currency, amount, year, quarter) VALUES (?, ?, ?, ?, ?, ?)";
        int rowsInserted = dbUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q1");
        rowsInserted += dbUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q2");
        rowsInserted += dbUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q3");
        rowsInserted += dbUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q4");
        rowsInserted += dbUtils.executeUpdate(insertQuery, "MK-XXX", "2020-07-01 00:00:00", "RUB", 777555, 2020, "Q1");
        Assert.assertEquals(rowsInserted, 5, "INSERTED rows count does not match the expected value");

        // After insertion, the number of rows should be 25
        int rowCountAfterInsertion = dbUtils.getRowCount(tableName);
        logger.debug("Row count AFTER adding financial data = {}", rowCountAfterInsertion);
        //int expectedRowCount = rowCount + rowsInserted;
        int expectedRowCount = rowCountBeforeInsertion + rowsInserted;
        Assert.assertEquals(rowCountAfterInsertion, expectedRowCount, "Row count AFTER adding financial data does not match the expected value");
        
        // Delete the new financial data
        String deleteQuery = "DELETE FROM financial_data WHERE client_common_id = ?";
        int rowsDeleted = dbUtils.executeUpdate(deleteQuery, "MK-XXX");
        Assert.assertEquals(rowsDeleted, 5, "DELETED rows count does not match the expected value");

        // After deletion, the number of rows should be back to 20
        int rowCountAfterDeletion = dbUtils.getRowCount(tableName);
        logger.debug("Row count AFTER DELETING financial data = {}", rowCountAfterDeletion);
        Assert.assertEquals(rowCountAfterDeletion, rowCountBeforeInsertion, "Row count AFTER DELETING financial data does not match the expected value");

        logger.info("testDbInsertionDeletion ended");
    }

    @AfterSuite(groups = {"db"})
    public void tearDown() {
        dbUtils.closeConnection();
    }
}
