import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTwo {

    String baseUrl = "https://reqres.in";
    String path = "/api/users?page=2";
    String parameters = "?page=2";
    int expectedStatusCode = 201;

    @Test(groups = "regression")
    public void getUsersList() {

        Response rs = RestAssured.get(baseUrl + path);

        int statusCode = rs.statusCode();

        Assert.assertEquals(statusCode, expectedStatusCode, "Expected status code = " + expectedStatusCode + ", actual = " + statusCode);

    }

}
