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
                .body("root.state", equalTo("CAxxx"));

    }


    @Test(groups = {"smoke"})
    public void testParseXmlUsingXmlPathVariables() {

        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        response.then()
                .log().status()
                .log().headers()
                .log().body();


        XmlPath xmlPath = response.xmlPath();

        String responseState = xmlPath.getString("root.state");
        Assert.assertEquals(responseState, "CA");
    }




}
