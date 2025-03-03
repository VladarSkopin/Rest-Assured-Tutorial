package basic_requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import test_data.petstore.CategoryPojo;
import test_data.petstore.PetOrderCategoryTagPojo;
import test_data.petstore.PetOrderPojo;
import test_data.petstore.TagPojo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Ignore
    @Test(groups = {"regression"})
    public void testPostRequestWithNestedPojo() {

        // https://petstore.swagger.io/v2/pet

        baseURI = "https://petstore.swagger.io";


        // Provide nested Pojo for the request body
        CategoryPojo categoryPojoRq = new CategoryPojo();
        categoryPojoRq.setId(1207603199);
        categoryPojoRq.setName("Dogs");

        TagPojo tagPojoRq_1 = new TagPojo();
        TagPojo tagPojoRq_2 = new TagPojo();
        tagPojoRq_1.setId(5);
        tagPojoRq_1.setName("Poodle-Markiz-1993");
        tagPojoRq_2.setId(7);
        tagPojoRq_2.setName("Terrier-Jerry-2005");
        List<TagPojo> tags = new ArrayList<>();
        tags.add(tagPojoRq_1);
        tags.add(tagPojoRq_2);

        // Configure the nested pojo
        PetOrderCategoryTagPojo petOrderCategoryTagPojoRq = new PetOrderCategoryTagPojo();
        petOrderCategoryTagPojoRq.setId(199);
        petOrderCategoryTagPojoRq.setCategory(categoryPojoRq);
        petOrderCategoryTagPojoRq.setName("Markiz");

        String photoUrl_Markiz_1 = "url/1/markizka_01-01-2001";
        String photoUrl_Markiz_2 = "url/1/markizka-12-12-2002";
        String[] photos = new String[]{photoUrl_Markiz_1, photoUrl_Markiz_2};
        petOrderCategoryTagPojoRq.setPhotoUrls(Arrays.stream(photos).toList());

        petOrderCategoryTagPojoRq.setTags(tags);
        petOrderCategoryTagPojoRq.setStatus("available");

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
    public void testPostRequestWithExternalFile() throws IOException {

        // https://petstore.swagger.io/v2/user/createWithArray

        baseURI = "https://petstore.swagger.io";

        File fileRq = new File("src/test/resources/createWithArray.json");

        String jsonFilePath = "src/test/resources/createWithArray.json";
        String jsonBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                //.body(fileRq)
                .body(jsonBody)
                .log().uri()
                .log().headers()
                .log().body();

        Response response = request
                .when()
                .post("v2/user/createWithArray");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200);

    }



    @Test(groups = {"regression"})
    public void testPostRequestWithModifiedExternalFile() throws IOException {

        // https://petstore.swagger.io/v2/user/createWithArray

        baseURI = "https://petstore.swagger.io";

        String jsonFilePath = "src/test/resources/createWithArray.json";
        String jsonBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        // Parse the JSON into a list of Maps
        Gson gson = new Gson();
        List<Map<String, Object>> jsonList = gson.fromJson(jsonBody, new TypeToken<List<Map<String, Object>>>() {}.getType());

        // Modify a specify value in JSON
        Map<String, Object> firstUser = jsonList.get(0);  // Get the first user in the array
        firstUser.put("username", "Sunlight");  // Change the username
        firstUser.put("userStatus", 0);  // Change the status

        // Convert the modified List back to a JSON string
        String modifiedJsonBody = gson.toJson(jsonList);

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(modifiedJsonBody)
                .log().uri()
                .log().headers()
                .log().body();

        Response response = request
                .when()
                .post("v2/user/createWithArray");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200);


    }

}
