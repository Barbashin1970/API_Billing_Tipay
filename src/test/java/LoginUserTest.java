import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static api.Auth.loginUser;
import static org.junit.Assert.assertNotNull;

public class LoginUserTest {
    String access_token;

    @Test
    @DisplayName("Проверка работы curl авторизации и получения токена JWT")
    public void loginUserSuccess() {
        Response responseLogin = loginUser();
        access_token = responseLogin.then().extract().body().path("access_token");
        assertNotNull(access_token);
    }

}
