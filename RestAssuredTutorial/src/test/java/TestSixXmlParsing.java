import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;



public class TestSixXmlParsing {

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
                .body("root.state", equalTo("CA"));

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
                .log().body();


        XmlPath xmlPath = response.xmlPath();


        String responseCity = xmlPath.getString("root.city");
        String responseFirstName = xmlPath.getString("root.firstName");
        String responseLastName = xmlPath.getString("root.lastName");
        String responseState = xmlPath.getString("root.state");

        Assert.assertEquals(responseCity, "San Jose");
        Assert.assertEquals(responseFirstName, "John");
        Assert.assertEquals(responseLastName, "Doe");
        Assert.assertEquals(responseState, "CA");
    }




}
