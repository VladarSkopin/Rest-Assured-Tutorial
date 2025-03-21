package parsing_response;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestJsonParsing {

    private static final Logger logger = LoggerFactory.getLogger(TestJsonParsing.class);

    @Test(groups = {"smoke"})
    public void testParseJsonUsingImplicitJsonPath() {

        logger.info("testParseJsonUsingImplicitJsonPath started");

        baseURI = "https://reqres.in";

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/unknown");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        response.then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .and()
                .header("Server", "cloudflare")
                .body("data[0].year", equalTo(2000));

        logger.info("testParseJsonUsingImplicitJsonPath ended");
    }


    @Test(groups = {"smoke"})
    public void testParseJsonUsingJsonPathWithVariables() {

        logger.info("testParseJsonUsingJsonPathWithVariables started");

        baseURI = "https://reqres.in";

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/unknown");

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        int rsStatusCode = response.statusCode();
        String rsContentType = response.header("Content-Type");
        String rsServer = response.header("Server");

        // assert headers
        Assert.assertEquals(rsStatusCode, 200);
        Assert.assertEquals(rsContentType, "application/json; charset=utf-8");
        Assert.assertEquals(rsServer, "cloudflare");

        // assert body
        int rsYear1 = response.jsonPath().get("data[0].year");
        int rsYear2 = response.jsonPath().get("data[1].year");
        int rsYear3 = response.jsonPath().get("data[2].year");
        int rsYear4 = response.jsonPath().get("data[3].year");
        int rsYear5 = response.jsonPath().get("data[4].year");
        int rsYear6 = response.jsonPath().get("data[5].year");

        Assert.assertEquals(rsYear1, 2000);
        Assert.assertEquals(rsYear2, 2001);
        Assert.assertEquals(rsYear3, 2002);
        Assert.assertEquals(rsYear4, 2003);
        Assert.assertEquals(rsYear5, 2004);
        Assert.assertEquals(rsYear6, 2005);

        logger.info("testParseJsonUsingJsonPathWithVariables ended");
    }


    @Test(groups = {"smoke"})
    public void testParseJsonUsingJsonObject() {

        logger.info("testParseJsonUsingJsonObject started");

        baseURI = "https://reqres.in";

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/unknown")
                .then()
                .extract()
                .response();

        logger.debug("Response status code: {}", response.statusCode());
        logger.debug("Response headers: \n{}", response.headers());
        logger.debug("Response body: \n{}", response.body().asString());

        JSONObject jsonObject = new JSONObject(response.asString());

        String[] names = new String[]{
                "cerulean",
                "fuchsia rose",
                "true red",
                "aqua sky",
                "tigerlily",
                "blue turquoise"};


        JSONArray jsArray = jsonObject.getJSONArray("data");


        for (int i = 0; i < jsArray.length(); i++) {
            String currentName = jsArray.getJSONObject(i).get("name").toString();
            Assert.assertEquals(currentName, names[i]);
        }


        logger.info("testParseJsonUsingJsonObject ended");
    }
}
