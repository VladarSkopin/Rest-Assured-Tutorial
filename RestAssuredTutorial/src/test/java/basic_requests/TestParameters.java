package basic_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class TestParameters {

    private static final Logger logger = LoggerFactory.getLogger(TestParameters.class);

    @Test(groups = {"smoke"})
    public void testRequestWithParameters() {

        logger.info("testRequestWithParameters started");

        // https://reqres.in/api/users?page=2

        baseURI = "https://reqres.in";

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("get_users_path", "users")  // path parameters  === api/users
                .queryParam("page", 2)  // query parameters  === ?page=2
                .log().uri()
                .when()
                .get("api/{get_users_path}");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(200)
                .time(lessThan(3000L));

        logger.info("testRequestWithParameters ended");
    }

}
