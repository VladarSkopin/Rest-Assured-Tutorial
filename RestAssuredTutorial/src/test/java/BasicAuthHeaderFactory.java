import io.restassured.http.Header;
import io.restassured.http.Headers;


public class BasicAuthHeaderFactory implements HeaderFactory {
    private final String username;

    public BasicAuthHeaderFactory(String username) {
        this.username = username;
    }

    @Override
    public Headers createHeaders() {
        return new Headers(
                new Header("iv-user", username),
                new Header("Content-Type", "application/json"),
                new Header("Accept", "application/json")
        );
    }
}



