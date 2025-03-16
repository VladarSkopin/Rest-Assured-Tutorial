package basic_requests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import helpers.AuthHeaderFactory;
import helpers.HeaderFactory;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class TestLessUsedRequests {

    private static final Logger logger = LoggerFactory.getLogger(TestLessUsedRequests.class);

    HeaderFactory headerFactory = new AuthHeaderFactory("cfbadministrator");
    Headers headers = headerFactory.createHeaders();

    Faker faker = new Faker();

    @Test(groups = {"smoke"})
    public void testDeleteRequest() {

        logger.info("testDeleteRequest started");

        baseURI = "https://petstore.swagger.io";

        Response response = given()
                .headers(headers)
                .when()
                .delete("/v2/store/order/2");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(200)
                .time(lessThan(3000L));

        logger.info("testDeleteRequest ended");
    }


    @Test(groups = {"smoke"})
    public void testPutRequest() {

        logger.info("testPutRequest started");

        baseURI = "https://petstore.swagger.io";

        Map<String, Object> map = new HashMap<>();
        map.put("id", 0);
        map.put("username", faker.yoda().quote());
        map.put("firstName", faker.name().firstName());
        map.put("lastName", faker.name().lastName());
        map.put("email", faker.internet().emailAddress());
        map.put("password", faker.internet().password());
        map.put("phone", faker.chuckNorris().fact());


        Gson gson = new Gson();
        String jsonPayload = gson.toJson(map);


        Response response = given()
                .headers(headers)
                .body(jsonPayload)
                .when()
                .log().body()
                .put("/v2/user/Garrett");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(200)
                .time(lessThan(3000L));

        logger.info("testPutRequest ended");
    }


    @Test(groups = {"smoke"})
    public void testPatchRequest() {

        logger.info("testPatchRequest started");

        baseURI = "https://reqres.in";

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Garrett");
        map.put("job", "The Keeper");

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(map);

        Response response = given()
                .headers(headers)
                .body(jsonPayload)
                .when()
                .patch("/api/users/2");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(200)
                .time(lessThan(3000L));

        logger.info("testPatchRequest ended");
    }

}
