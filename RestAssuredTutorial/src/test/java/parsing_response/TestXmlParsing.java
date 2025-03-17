package parsing_response;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TestXmlParsing {

    private static final Logger logger = LoggerFactory.getLogger(TestParsingUsingPojo.class);

    @Test(groups = {"smoke"})
    public void testParseXmlUsingImplicitXmlPath() {

        logger.info("testParseXmlUsingImplicitXmlPath started");

        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(200)
                .header("Content-Type", "application/xml; charset=utf-8")
                .body("root.state", equalTo("CA"))
                .time(lessThan(3000L));

        logger.info("testParseXmlUsingImplicitXmlPath ended");
    }


    @Test(groups = {"smoke"})
    public void testParseXmlUsingXmlPathWithVariables() {

        logger.info("testParseXmlUsingXmlPathWithVariables started");

        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .time(lessThan(3000L));


        XmlPath xmlPath = response.xmlPath();


        String responseCity = xmlPath.getString("root.city");
        String responseFirstName = xmlPath.getString("root.firstName");
        String responseLastName = xmlPath.getString("root.lastName");
        String responseState = xmlPath.getString("root.state");

        Assert.assertEquals(responseCity, "San Jose", "Response city did not match");
        Assert.assertEquals(responseFirstName, "John", "Response first name did not match");
        Assert.assertEquals(responseLastName, "Doe", "Response last name did not match");
        Assert.assertEquals(responseState, "CA", "Response state did not match");

        logger.info("testParseXmlUsingXmlPathWithVariables ended");
    }

}
