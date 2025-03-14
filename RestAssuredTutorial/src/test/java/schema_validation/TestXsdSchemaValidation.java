package schema_validation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import io.restassured.matcher.RestAssuredMatchers;

public class TestXsdSchemaValidation {

    @Test(groups = {"smoke"})
    public void testXsdSchemaValidation() {
        baseURI = "https://mocktarget.apigee.net";

        File xsdSchema = new File("src\\test\\resources\\get_schema.xsd");

        Response response = given()
                .when()
                .get("/xml");

        response.then()
                //.body(matchesXsdInClasspath("get_schema.xsd"))
                .body(RestAssuredMatchers.matchesXsd(xsdSchema))
                .log().status()
                .log().headers()
                .log().body()
                .time(lessThan(3000L));

    }
}
