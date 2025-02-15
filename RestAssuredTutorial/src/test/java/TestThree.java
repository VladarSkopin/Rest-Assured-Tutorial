import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;



public class TestThree {

    @Test(groups={"factory"})
    public void testHeaderFactory() {
        HeaderFactory headerFactory = new BasicAuthHeaderFactory("cfbadministrator");

        Headers headers = headerFactory.createHeaders();

        RequestSpecification request = given()
                .headers(headers)
                .baseUri("https://reqres.in")
                .basePath("/api");

        Response response = request.get("users?page=2");
        response.then().statusCode(200);
    }


    @Test(groups="regression")
    public void testJsonResponse() {
        baseURI = "https://reqres.in/api";

        given()
                .get("users?page=2")
                .then()
                .statusCode(200)
                .body("data[1].id", equalTo(8))
                .log();
    }

}
