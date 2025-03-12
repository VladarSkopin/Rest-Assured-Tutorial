package helpers;

import io.restassured.http.Header;
import io.restassured.http.Headers;


public class AuthHeaderFactory implements HeaderFactory {
    private final String username;

    public AuthHeaderFactory(String username) {
        this.username = username;
    }

    // TODO: return different Content-Type depending on String parameter provided from the outside
    @Override
    public Headers createHeaders() {
        return new Headers(
                new Header("iv-user", username),
                new Header("Content-Type", "application/json"),
                new Header("Accept", "application/json")
        );
    }
}



