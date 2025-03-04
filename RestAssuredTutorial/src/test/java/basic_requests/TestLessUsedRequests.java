package basic_requests;

import com.google.gson.Gson;
import helpers.BasicAuthHeaderFactory;
import helpers.HeaderFactory;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class TestLessUsedRequests {

    HeaderFactory headerFactory = new BasicAuthHeaderFactory("cfbadministrator");
    Headers headers = headerFactory.createHeaders();


    @Test(groups = {"smoke"})
    public void testDeleteRequest() {
        baseURI = "https://petstore.swagger.io";

        Response response = given()
                .headers(headers)
                .when()
                .delete("/v2/store/order/2");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));
    }


    @Test(groups = {"smoke"})
    public void testPutRequest() {
        baseURI = "https://petstore.swagger.io";

        Map<String, Object> map = new HashMap<>();
        map.put("id", 0);
        map.put("username", "garrett");
        map.put("firstName", "william");
        map.put("lastName", "garrett");
        map.put("email", "idonthaveany@yahoo.com");
        map.put("password", "1234");
        map.put("phone", "0987123654");


        Gson gson = new Gson();
        String jsonPayload = gson.toJson(map);


        Response response = given()
                .headers(headers)
                .body(jsonPayload)
                .when()
                .put("/v2/user/Garrett");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));
    }


    @Test(groups = {"smoke"})
    public void testPatchRequest() {
        baseURI = "https://reqres.in";

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Garrett");
        map.put("job", "The Keeper");

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(map);

        Response response = given()
                .headers(headers)
                .body(jsonPayload)
                .when()
                .patch("/api/users/2");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));
    }

}
