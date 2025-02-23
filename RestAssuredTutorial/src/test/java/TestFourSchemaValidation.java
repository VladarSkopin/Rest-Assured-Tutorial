import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

// import io.restassured.module.jsv.JsonSchemaValidator;  // "module" cannot be found
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TestFourSchemaValidation {

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
                .log().status()
                .log().headers()
                .log().body()
                .log().cookies();
                //.log().all();

        //String xmlResponse = response.getBody().asString();


    }
}
