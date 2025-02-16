import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

//import io.restassured.module.jsv.JsonSchemaValidator;  // "module" cannot be found


import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class TestFour {

    /*
    @Test(groups={"smoke"})
    public void testJsonSchemaValidation() {

        given()
                .when()
                .get("api/unknown/2")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClassPath("post_schema.json"));  // TODO: "matchesJsonSchemaInClassPath" fails
    }

     */


    @Test(groups = {"smoke"})
    public void testXsdSchemaValidation() {
        baseURI = "https://mocktarget.apigee.net";

        Response response = given()
                .when()
                .get("/xml");

        response.then()
                .body(matchesXsdInClasspath("get_schema.xsd"))
                .log().all();

        //String xmlResponse = response.getBody().asString();

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
