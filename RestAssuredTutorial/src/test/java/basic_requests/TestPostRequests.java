package basic_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import test_data.petstore.PetOrderPojo;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TestPostRequests {

    @Test(groups = {"regression"})
    public void testPostRequestWithPojo() {

        // https://petstore.swagger.io/v2/store/order

        baseURI = "https://petstore.swagger.io";

        PetOrderPojo petOrderRq = new PetOrderPojo();
        petOrderRq.setId(99);
        petOrderRq.setPetId(202);
        petOrderRq.setQuantity(1);
        petOrderRq.setShipDate("2025-02-28T20:46:00.000Z");
        petOrderRq.setStatus("placed");
        petOrderRq.setComplete(true);

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(petOrderRq);


        Response response = request
                .when()
                .log().uri()
                .log().headers()
                .log().body()
                .post("v2/store/order");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200);

    }

    @Ignore
    @Test(groups = {"regression"})
    public void testPostRequestWithNestedPojo() {

        baseURI = "";

        RequestSpecification request = given()
                .contentType(ContentType.JSON);

    }

    @Ignore
    @Test(groups = {"regression"})
    public void testPostRequestWithExternalFile() {

        baseURI = "";

        RequestSpecification request = given()
                .contentType(ContentType.JSON);

        // parse file to String

        // parse String to JSON object (Gson)

        // change a field in JSON

        // put the JSON in the request body and send it to server

        

    }

}
