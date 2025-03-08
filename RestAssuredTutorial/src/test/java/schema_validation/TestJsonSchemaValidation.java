package schema_validation;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.module.jsv.JsonSchemaValidator;

public class TestJsonSchemaValidation {

    @Test(groups={"smoke"})
    public void testJsonSchemaValidation() {

        baseURI = "https://reqres.in";

        File schemaFile = new File("src/test/resources/api_unknown_json_schema.json");

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/unknown");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Server", "cloudflare")
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile))
                .time(lessThan(3000L));

    }


}
