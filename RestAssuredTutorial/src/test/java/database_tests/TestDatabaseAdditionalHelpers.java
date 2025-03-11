package database_tests;

import helpers.DatabaseConfig;
import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;


public class TestDatabaseAdditionalHelpers {

    private static final String DB_URL = DatabaseConfig.getDbUrl();
    private static final String DB_USER = DatabaseConfig.getDbUsername();
    private static final String DB_PASSWORD = DatabaseConfig.getDbPassword();

    @BeforeTest(groups = {"db"})
    public void setUp() {
        DatabaseUtils.connectToDatabase(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Test(groups = {"db"})
    public void testDbColumnBoundaryValues() {

        String expectedFirstTitle = "T-Bank";
        String expectedLastTitle = "Gazprom";

        String sql = "SELECT * FROM clients ORDER BY created DESC";
        String columnName = "title_short";

        List<Object> columnValues = DatabaseUtils.getColumnValues(sql, columnName);
        String firstTitle = columnValues.get(0).toString();
        String lastTitle = columnValues.get(columnValues.size() - 1).toString();

        Assert.assertEquals(firstTitle, expectedFirstTitle);
        Assert.assertEquals(lastTitle, expectedLastTitle);

    }

    @AfterTest(groups = {"db"})
    public void tearDown() {
        DatabaseUtils.closeConnection();
    }


}
