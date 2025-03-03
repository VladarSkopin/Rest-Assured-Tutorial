package schema_validation;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TestXsdSchemaValidation {

    @Test(groups = {"smoke"})
    public void testXsdSchemaValidation() {
        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        response.then()
                .body(matchesXsdInClasspath("get_schema.xsd"))
                .log().status()
                .log().headers()
                .log().body()
                .time(lessThan(3000L));

    }
}
