package database_tests;

import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.ResultSet;


public class TestDatabaseQueries {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/alextest";
    private static final String DB_USER = "alex";
    private static final String DB_PASSWORD = "_K2Vq*SbuT/$t*w";

    @BeforeTest(groups = {"db"})
    public void setUp() {
        DatabaseUtils.connectToDatabase(DB_URL, DB_USER, DB_PASSWORD);
    }


    @Test(groups = {"db"})
    public void testApiAndDatabase() {

        String expectedClientGeneralId = "MK-123";
        String expectedClientShortName = "Sber";
        String expectedClientLongName = "PAO Sberbank of Russia";

        ResultSet resultSet = DatabaseUtils.executeQuery("SELECT * FROM clients WHERE id = 1");  // TODO: add WHERE clientGeneralId + Assert "created" field

        try {
            if (resultSet.next()) {
                String dbClientGeneralId = resultSet.getString("client_general_id");
                String dbClientShortName = resultSet.getString("title_short");
                String dbClientLongName = resultSet.getString("title_long");

                Assert.assertEquals(dbClientGeneralId, expectedClientGeneralId);
                Assert.assertEquals(dbClientShortName, expectedClientShortName);
                Assert.assertEquals(dbClientLongName, expectedClientLongName);
            } else {
                Assert.fail("Client " + expectedClientLongName + " not found in the database");
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
