package database_tests;

import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class TestDatabaseAdditionalHelpers {

    private static final Logger logger = LoggerFactory.getLogger(TestDatabaseAdditionalHelpers.class);

    private DatabaseUtils dbUtils;

    @BeforeSuite(groups = {"db"})
    public void setUp() {
        dbUtils = DatabaseUtils.getInstance();
    }

    @Test(groups = {"db"})
    public void testDbColumnBoundaryValues() {

        logger.info("testDbColumnBoundaryValues started");

        String expectedFirstTitle = "T-Bank";
        String expectedLastTitle = "Gazprom";

        String sql = "SELECT * FROM clients ORDER BY created DESC";
        String columnName = "title_short";

        List<Object> columnValues = dbUtils.getColumnValues(sql, columnName);
        String firstTitle = columnValues.get(0).toString();
        String lastTitle = columnValues.get(columnValues.size() - 1).toString();

        Assert.assertEquals(firstTitle, expectedFirstTitle);
        Assert.assertEquals(lastTitle, expectedLastTitle);

        logger.info("testDbColumnBoundaryValues ended");
    }

    @Test(groups = {"db"})
    public void testDbViews() throws SQLException {

        logger.info("testDbViews started");

        String clientGeneralId = "MK-777";
        String sqlView = "SELECT * FROM view_clients_finances WHERE client_common_id = ?";
        ResultSet resultSet = dbUtils.executeQuery(sqlView, clientGeneralId);

        resultSet.next();

        String expectedCurrency = "EUR";
        int expectedAmount = 9000;

        String dbCurrency = resultSet.getString("currency");
        int dbAmount = resultSet.getInt("amount");


        Assert.assertEquals(dbCurrency, expectedCurrency, "Currencies for the client [ " + clientGeneralId + " ] don't match");
        Assert.assertEquals(dbAmount, expectedAmount, "Amounts for the client [ " + clientGeneralId + " ] don't match");

        logger.info("testDbViews ended");
    }

    @AfterSuite(groups = {"db"})
    public void tearDown() {
        dbUtils.closeConnection();
    }


}
