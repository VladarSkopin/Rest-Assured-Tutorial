package parsing_response;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import test_data.ClientPojo;
import test_data.devices_data.DataPojo;
import test_data.devices_data.DevicePojo;

import static io.restassured.RestAssured.*;

public class TestParsingUsingPojo {

    @Ignore
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
                .statusCode(200);

        Gson gson = new Gson();

        ClientPojo clientRs = gson.fromJson(response.getBody().asString(), ClientPojo.class);


        String clientRsFirstName = clientRs.getFirstName();
        String clientRsLastname = clientRs.getLastName();
        String clientRsCity = clientRs.getCity();
        String clientRsState = clientRs.getState();

        Assert.assertEquals(clientRsFirstName, "John");
        Assert.assertEquals(clientRsLastname, "Doe");
        Assert.assertEquals(clientRsCity, "San Jose");
        Assert.assertEquals(clientRsState, "CA");
    }

    @Ignore
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
                .statusCode(200);

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
        Assert.assertEquals(deviceId, "7");
        Assert.assertEquals(deviceName, "Apple MacBook Pro 16");
        // Data fields
        Assert.assertEquals(dataYear, 2019);
        Assert.assertEquals(dataPrice, 1849.99);
        Assert.assertEquals(dataCpuModel, "Intel Core i9");
        Assert.assertEquals(dataHardDiskSize, "1 TB");

    }


    @Ignore
    @Test(groups = {"regression"})
    public void testDeserializationWithListOfNestedPojo() {


    }

}
