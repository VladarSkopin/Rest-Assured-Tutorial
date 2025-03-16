package basic_requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestParametrization {

    private static final Logger logger = LoggerFactory.getLogger(TestParametrization.class);

    String baseUrl = "https://reqres.in";
    String path = "/api/users";
    String pathNegative = "/api/unknown/23";
    String parameters = "?page=2";
    int expectedStatusCodePositive = 200;
    int expectedStatusCodeNegative = 404;


    @Test(groups = {"regression"})
    public void getUsersListPositive() {

        logger.info("getUsersListPositive started");

        Response rs = RestAssured.get(baseUrl + path + parameters);

        logger.debug("Response status code: {}", rs.statusCode());

        int statusCode = rs.statusCode();

        Assert.assertEquals(statusCode, expectedStatusCodePositive, "Expected status code = " + expectedStatusCodePositive + ", actual = " + statusCode);

        logger.info("getUsersListPositive ended");
    }


    @Test(groups = "regression")
    public void getUsersListNegative() {

        logger.info("getUsersListNegative started");

        Response rs = RestAssured.get(baseUrl + pathNegative);

        logger.debug("Response status code: {}", rs.statusCode());

        int statusCode = rs.statusCode();

        Assert.assertEquals(statusCode, expectedStatusCodeNegative, "Expected status code = " + expectedStatusCodeNegative + ", actual = " + statusCode);

        logger.info("getUsersListNegative ended");
    }

}
