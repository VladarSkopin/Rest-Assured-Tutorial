package basic_requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestParametrization {

    String baseUrl = "https://reqres.in";
    String path = "/api/users";
    String pathNegative = "/api/unknown/23";
    String parameters = "?page=2";
    int expectedStatusCodeNegative = 404;
    int expectedStatusCodePositive = 200;


    @Test(groups = {"regression"})
    public void getUsersListPositive() {

        Response rs = RestAssured.get(baseUrl + path + parameters);

        int statusCode = rs.statusCode();

        Assert.assertEquals(statusCode, expectedStatusCodePositive, "Expected status code = " + expectedStatusCodePositive + ", actual = " + statusCode);
    }


    @Test(groups = "regression")
    public void getUsersListNegative() {

        Response rs = RestAssured.get(baseUrl + pathNegative);

        int statusCode = rs.statusCode();

        Assert.assertEquals(statusCode, expectedStatusCodeNegative, "Expected status code = " + expectedStatusCodeNegative + ", actual = " + statusCode);
    }

}
