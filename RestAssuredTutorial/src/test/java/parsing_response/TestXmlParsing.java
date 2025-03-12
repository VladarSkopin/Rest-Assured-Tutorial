package parsing_response;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;



public class TestXmlParsing {

    @Test(groups = {"smoke"})
    public void testParseXmlUsingImplicitXmlPath() {

        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .header("Content-Type", "application/xml; charset=utf-8")
                .body("root.state", equalTo("CA"))
                .time(lessThan(3000L));

    }


    @Test(groups = {"smoke"})
    public void testParseXmlUsingXmlPathWithVariables() {

        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .time(lessThan(3000L));


        XmlPath xmlPath = response.xmlPath();


        String responseCity = xmlPath.getString("root.city");
        String responseFirstName = xmlPath.getString("root.firstName");
        String responseLastName = xmlPath.getString("root.lastName");
        String responseState = xmlPath.getString("root.state");

        Assert.assertEquals(responseCity, "San Jose", "Response city did not match");
        Assert.assertEquals(responseFirstName, "John", "response first name did not match");
        Assert.assertEquals(responseLastName, "Doe", "Response last name did not match");
        Assert.assertEquals(responseState, "CA", "Response state did not match");
    }




}
