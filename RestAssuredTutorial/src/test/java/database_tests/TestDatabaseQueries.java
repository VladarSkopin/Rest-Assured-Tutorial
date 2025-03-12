package database_tests;

import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.Date;
import java.sql.ResultSet;


public class TestDatabaseQueries {

    private DatabaseUtils dbUtils;

    @BeforeSuite(groups = {"db"})
    public void setUp() {
        dbUtils = DatabaseUtils.getInstance();
    }


    @Test(groups = {"db"})
    public void testClientDbConsistency() {

        String clientGeneralId = "'MK-123'";
        // expected db fields in alex.clients table
        String expectedClientShortName = "Sber";
        String expectedClientLongName = "PAO Sberbank of Russia";
        Date expectedClientCreated = Date.valueOf("2023-12-06");


        ResultSet resultSet = dbUtils.executeQuery("SELECT * FROM clients WHERE client_general_id = " + clientGeneralId);

        try {
            if (resultSet.next()) {
                String dbClientShortName = resultSet.getString("title_short");
                String dbClientLongName = resultSet.getString("title_long");
                Date dbCreated = resultSet.getDate("created");

                Assert.assertEquals(dbClientShortName, expectedClientShortName, "Short name for client [ " + clientGeneralId + " ] did not match");
                Assert.assertEquals(dbClientLongName, expectedClientLongName, "Long name for client [ " + clientGeneralId + " ] did not match");
                Assert.assertEquals(dbCreated, expectedClientCreated, "Date for client [ " + clientGeneralId + " ] did not match");
            } else {
                Assert.fail("Client " + expectedClientLongName + " not found in the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(groups = {"db"})
    public void testFinancialDataDbConsistency() {

        String clientCommonId = "'MK-123'";
        // expected db fields in alex.financial_data table
        String expectedCurrency = "RUB";
        int expectedAmount = 999999;

        int clientYear = 2021;
        String clientQuarter = "'Q1'";

        ResultSet resultSet = dbUtils.executeQuery("SELECT * FROM financial_data "
                + "WHERE client_common_id = " + clientCommonId
                + " AND year = " + clientYear
                + " AND quarter = " + clientQuarter);

        try {
            if (resultSet.next()) {
                String dbCurrency = resultSet.getString("currency");
                int dbAmount = resultSet.getInt("amount");

                Assert.assertEquals(dbCurrency, expectedCurrency, "Currency for client [ " + clientCommonId + " ] did not match");
                Assert.assertEquals(dbAmount, expectedAmount, "Money amount for client [ " + clientCommonId + " ] did not match");
            } else {
                Assert.fail("Financial data for client [ " + clientCommonId + " ] not found in the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterSuite(groups = {"db"})
    public void tearDown() {
        dbUtils.closeConnection();
    }


}
