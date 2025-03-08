package database_tests;

import helpers.DatabaseUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.Date;
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
    public void testClientDbConsistency() {

        String clientGeneralId = "'MK-123'";
        // expected db fields in alex.clients table
        String expectedClientShortName = "Sber";
        String expectedClientLongName = "PAO Sberbank of Russia";
        Date expectedClientCreated = Date.valueOf("2023-12-06");


        ResultSet resultSet = DatabaseUtils.executeQuery("SELECT * FROM clients WHERE client_general_id = " + clientGeneralId);

        try {
            if (resultSet.next()) {

                String dbClientShortName = resultSet.getString("title_short");
                String dbClientLongName = resultSet.getString("title_long");
                Date dbCreated = resultSet.getDate("created");

                Assert.assertEquals(dbClientShortName, expectedClientShortName);
                Assert.assertEquals(dbClientLongName, expectedClientLongName);
                Assert.assertEquals(dbCreated, expectedClientCreated);
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
