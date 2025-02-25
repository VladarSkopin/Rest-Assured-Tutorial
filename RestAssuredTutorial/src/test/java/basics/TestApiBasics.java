package basics;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import java.util.Map;

public class TestApiBasics {

    String url_get_1 = "https://reqres.in/api/users?page=2";

    @Test(groups = {"smoke"})
    public void testApiBasics() {

        Response rs = RestAssured.get(url_get_1);
        System.out.println("Response = " + rs);  // io.restassured.internal.RestAssuredResponseImpl@3f049056

        int statusCode = rs.statusCode();
        System.out.println("Status code = " + statusCode);  // 200

        String rsStatus = rs.statusLine();
        System.out.println("statusLine = " + rsStatus);  // HTTP/1.1 200 OK

        ResponseBody body = rs.getBody();
        System.out.println("ResponseBody = " + body);  // io.restassured.internal.RestAssuredResponseImpl@3f049056

        String rsString = rs.toString();
        System.out.println("rsString = " + rsString);  // io.restassured.internal.RestAssuredResponseImpl@61a1ea2c

        String contentType = rs.contentType();
        System.out.println("Content Type = " + contentType);  // application/json; charset=utf-8


        Map<String, String> cookies = rs.cookies();
        System.out.println("-----");
        System.out.println("Cookies: ");  // no cookies!
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            System.out.println("    cooke = " + cookie.getKey() + ", value = " + cookie.getValue());
        }


        Cookies detailedCookies = rs.detailedCookies();
        System.out.println("-----");
        System.out.println("Detailed cookies: ");  // no cookies!
        for (Cookie cookie : detailedCookies) {
            System.out.println("    cooke = " + cookie.getName() + ", value = " + cookie.getValue());
        }


        Headers headers = rs.headers();
        System.out.println("-----");
        System.out.println("Headers: \n" + headers);


        Headers headersAdditional = rs.getHeaders();
        System.out.println("-----");
        System.out.println("Headers .getHeaders(): \n" + headersAdditional);

        System.out.println("-----");
        System.out.println("X-Powered-By = " + headers.get("X-Powered-By"));  // X-Powered-By=Express

        String sessionId = rs.getSessionId();
        System.out.println("Session ID = "  +sessionId);  // null

        long time = rs.time();
        System.out.println("Time = " + time);  // 1151

        long timeAdditional = rs.getTime();
        System.out.println("getTime() = " + timeAdditional);  // 1151


    }




}
