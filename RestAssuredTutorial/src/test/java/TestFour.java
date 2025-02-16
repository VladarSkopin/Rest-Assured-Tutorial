import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

//import io.restassured.module.jsv.JsonSchemaValidator;  // "module" cannot be found


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

        given()
                .when()
                .get("/xml")
                .then()
                //.body(notNullValue())
                .body(matchesXsdInClasspath("get_schema.xsd"))
                .log().all();
    }


}
