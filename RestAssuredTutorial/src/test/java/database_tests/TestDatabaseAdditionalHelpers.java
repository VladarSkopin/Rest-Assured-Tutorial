package database_tests;

import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;


public class TestDatabaseAdditionalHelpers {

    private DatabaseUtils dbUtils;

    @BeforeSuite(groups = {"db"})
    public void setUp() {
        dbUtils = DatabaseUtils.getInstance();
    }

    @Test(groups = {"db"})
    public void testDbColumnBoundaryValues() {

        String expectedFirstTitle = "T-Bank";
        String expectedLastTitle = "Gazprom";

        String sql = "SELECT * FROM clients ORDER BY created DESC";
        String columnName = "title_short";

        List<Object> columnValues = dbUtils.getColumnValues(sql, columnName);
        String firstTitle = columnValues.get(0).toString();
        String lastTitle = columnValues.get(columnValues.size() - 1).toString();

        Assert.assertEquals(firstTitle, expectedFirstTitle);
        Assert.assertEquals(lastTitle, expectedLastTitle);

    }

    @Test(groups = {"db"})
    public void testDbViews() {
        //





    }

    @AfterSuite(groups = {"db"})
    public void tearDown() {
        dbUtils.closeConnection();
    }


}
