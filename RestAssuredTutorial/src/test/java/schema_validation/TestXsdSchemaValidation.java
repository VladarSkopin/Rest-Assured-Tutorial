package schema_validation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import io.restassured.matcher.RestAssuredMatchers;


public class TestXsdSchemaValidation {

    private static final Logger logger = LoggerFactory.getLogger(TestXsdSchemaValidation.class);

    @Test(groups = {"smoke"})
    public void testXsdSchemaValidation() {

        logger.info("testXsdSchemaValidation started");

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

        logger.info("testXsdSchemaValidation ended");
    }
}
