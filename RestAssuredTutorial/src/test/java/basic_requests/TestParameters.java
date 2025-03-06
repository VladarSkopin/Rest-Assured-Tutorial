package basic_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class TestParameters {

    @Test(groups = {"smoke"})
    public void testRequestWithParameters() {

        // https://reqres.in/api/users?page=2

        baseURI = "https://reqres.in";

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("get_users_path", "users")  // path parameters  === api/users
                .queryParam("page", 2)  // query parameters  === ?page=2
                .log().uri()
                .when()
                .get("api/{get_users_path}");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));
    }

}
