package basic_requests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.google.gson.Gson;
import helpers.AuthHeaderFactory;
import helpers.HeaderFactory;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class TestHeaderFactory {

    private static final Logger logger = LoggerFactory.getLogger(TestHeaderFactory.class);

    @Test(groups={"factory"})
    public void testHeaderFactory() {

        logger.info("testHeaderFactory started");

        HeaderFactory headerFactory = new AuthHeaderFactory("cfbadministrator");

        Headers headers = headerFactory.createHeaders();

        RequestSpecification request = given()
                .headers(headers)
                .baseUri("https://reqres.in")
                .basePath("/api");

        Response response = request.get("users?page=2");
        response.then().statusCode(200).time(lessThan(3000L));

        logger.info("testHeaderFactory ended");
    }


    @Test(groups="regression")
    public void testJsonResponse() {

        logger.info("testJsonResponse started");

        baseURI = "https://reqres.in/api";

        given()
                .get("users?page=2")
                .then()
                .statusCode(200)
                .body("data[1].id", equalTo(8))
                //.body("data.first_name", hasItems("Sasha", "Michael", "Rachel"))
                .body("data.first_name", hasItems("Michael", "Rachel"))
                .body("data[1].avatar", containsString("-image.jpg"))
                .log().all()
                .time(lessThan(3000L));

        logger.info("testJsonResponse ended");
    }


    @Test(groups = "regression")
    public void testPostRequest() {

        logger.info("testPostRequest started");

        String name = "Giang Hương";
        String job = "Sasha's Kitten";

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("job", job);

        System.out.println(map);  // {name=Giang Hương, job=Sasha's Kitten}

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(map);

        System.out.println(jsonPayload);  // {"name":"Giang Hương","job":"Sasha\u0027s Kitten"}

        baseURI = "https://reqres.in/api";

        HeaderFactory headerFactory = new AuthHeaderFactory("cfbadministrator");
        Headers headers = headerFactory.createHeaders();


        Response response = given()
                .headers(headers)
                .body(jsonPayload)
                .when()
                .post("/api/users");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(201)
                .body("name", equalTo(name))
                .body("job", equalTo(job))
                .body("id", notNullValue())
                .time(lessThan(3000L));

        logger.info("testPostRequest ended");
    }
}
