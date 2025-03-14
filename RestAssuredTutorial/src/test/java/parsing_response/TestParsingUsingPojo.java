package parsing_response;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_data.ClientPojo;
import test_data.devices_data.DataPojo;
import test_data.devices_data.DevicePojo;
import test_data.gadgets.GadgetPojo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

public class TestParsingUsingPojo {


    @Test(groups = {"regression"})
    public void testDeserializationWithPojo() {

        baseURI = "https://mocktarget.apigee.net";

        RequestSpecification request = given()
                .contentType(ContentType.JSON);

        Response response = request
                .when()
                .get("/json");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));

        Gson gson = new Gson();

        ClientPojo clientRs = gson.fromJson(response.getBody().asString(), ClientPojo.class);


        String clientRsFirstName = clientRs.getFirstName();
        String clientRsLastname = clientRs.getLastName();
        String clientRsCity = clientRs.getCity();
        String clientRsState = clientRs.getState();

        Assert.assertEquals(clientRsFirstName, "John", "Client first name did not match");
        Assert.assertEquals(clientRsLastname, "Doe", "Client last name did not match");
        Assert.assertEquals(clientRsCity, "San Jose", "Client's city did not match");
        Assert.assertEquals(clientRsState, "CA", "Client's state did not match");
    }


    @Test(groups = {"regression"})
    public void testDeserializationWithNestedPojo() {

        // https://api.restful-api.dev/objects/7

        baseURI = "https://api.restful-api.dev";

        RequestSpecification request = given()
                .contentType(ContentType.JSON);

        Response response = request
                .when()
                .get("/objects/7");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));

        Gson gson = new Gson();

        DevicePojo deviceRs = gson.fromJson(response.getBody().asString(), DevicePojo.class);

        String deviceId = deviceRs.getId();
        String deviceName = deviceRs.getName();

        DataPojo dataRs = deviceRs.getData();

        int dataYear = dataRs.getYear();
        double dataPrice = dataRs.getPrice();
        String dataCpuModel = dataRs.getCpuModel();
        String dataHardDiskSize = dataRs.getHardDiskSize();

        // Device fields
        Assert.assertEquals(deviceId, "7", "Device ID did not match");
        Assert.assertEquals(deviceName, "Apple MacBook Pro 16", "Device name did not match");
        // Data fields
        Assert.assertEquals(dataYear, 2019, "Device year did not match");
        Assert.assertEquals(dataPrice, 1849.99, "Device price did not match");
        Assert.assertEquals(dataCpuModel, "Intel Core i9", "Device CPU model did not match");
        Assert.assertEquals(dataHardDiskSize, "1 TB", "Device HDD size did not match");

    }



    @Test(groups = {"regression"})
    public void testDeserializationWithListOfNestedPojo() {

        // https://api.restful-api.dev/objects

        baseURI = "https://api.restful-api.dev";

        RequestSpecification request = given()
                .contentType(ContentType.JSON);

        Response response = request
                .when()
                .get("/objects");

        response.then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .time(lessThan(3000L));

        Gson gson = new Gson();

        // The type for the list of POJOs
        Type listType = new TypeToken<List<GadgetPojo>>() {}.getType();

        // Get the list of all gadgets
        List<GadgetPojo> gadgetsRs = gson.fromJson(response.getBody().asString(), listType);

        // Filter the list - get only gadgets that have "price" and "color" (should be at least 2)
        List<GadgetPojo> gadgetsFiltered = gadgetsRs.stream()
                .filter(product -> product.getData() != null &&
                        product.getData().getPrice() != null &&
                        product.getData().getColor() != null).collect(Collectors.toList());

        System.out.println(gadgetsFiltered);

        Assert.assertEquals(gadgetsRs.size(), 13, "The amount of UNFIlTERED gadgets is wrong");
        Assert.assertEquals(gadgetsFiltered.size(), 2, "The amount of FIlTERED gadgets is wrong");
    }

}
