package basic_requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import test_data.petstore.CategoryPojo;
import test_data.petstore.PetOrderCategoryTagPojo;
import test_data.petstore.PetOrderPojo;
import test_data.petstore.TagPojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TestPostRequests {

    @Ignore
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


    @Test(groups = {"regression"})
    public void testPostRequestWithNestedPojo() {

        // https://petstore.swagger.io/v2/pet

        baseURI = "https://petstore.swagger.io";


        // Provide nested Pojo for the request body
        CategoryPojo categoryPojoRq = new CategoryPojo();

        TagPojo tagPojoRq_1 = new TagPojo();
        TagPojo tagPojoRq_2 = new TagPojo();
        List<TagPojo> tags = new ArrayList<>();
        tags.add(tagPojoRq_1);
        tags.add(tagPojoRq_2);

        PetOrderCategoryTagPojo petOrderCategoryTagPojoRq = new PetOrderCategoryTagPojo();
        petOrderCategoryTagPojoRq.setId(199);
        petOrderCategoryTagPojoRq.setCategory(categoryPojoRq);
        petOrderCategoryTagPojoRq.setName("Markiz");

        String photoUrl_Markiz_1 = "url/1/markizka_01-01-2001";
        String photoUrl_Markiz_2 = "url/1/markizka-12-12-2002";
        String[] photos = new String[]{photoUrl_Markiz_1, photoUrl_Markiz_2};
        petOrderCategoryTagPojoRq.setPhotoUrls(Arrays.stream(photos).toList());

        petOrderCategoryTagPojoRq.setTags(tags);

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(petOrderCategoryTagPojoRq);

        Response response = request
                .when()
                .log().uri()
                .log().headers()
                .log().body()
                .post("v2/pet");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200);

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
