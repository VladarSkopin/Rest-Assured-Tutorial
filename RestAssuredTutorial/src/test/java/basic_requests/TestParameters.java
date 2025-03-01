package basic_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TestParameters {

    @Test(groups = {"smoke"})
    public void testRequestWithParameters() {

        // https://reqres.in/api/users?page=2

        baseURI = "https://reqres.in";

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("get_users_path", "users")  // path parameters
                .queryParam("page", 2)  // query parameters
                .log().uri()
                .when()
                .get("api/{get_users_path}");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200);
    }

}
