import api.PostOrderSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.ErrorResponse;
import pojo.Product;

import static api.Auth.getToken;
import static api.PostOrderSteps.printOrder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class CreateOrderTest {
    PostOrderSteps step = new PostOrderSteps();
    private String accessToken;

    @Test
    @DisplayName("Создаем заказ и проверяем что в ответе 200 и message == OK")
    public void createOrderTest() {
        accessToken = getToken();
        Response response = step.createOrder(accessToken);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

    @Test
    @DisplayName("Создаем заказ и проверяем что в ответе объект Product != null")
    public void productTest() {
        accessToken = getToken();
        Product product = step.getResponseSerial(accessToken);
        printOrder(product);
        assertThat(product, notNullValue());
    }

    @Test
    @DisplayName("Создаем заказ и проверяем что статус заказа = CREATED")
    public void statusTest() {
        accessToken = getToken();
        step.createOrder(accessToken);
        Product product = step.getResponseSerial(accessToken);
        String status = product.getData().getState();
        assertEquals("CREATED", status);
    }

    @Test
    @DisplayName("Создаем заказ по невалидному токену - пробел - ожидаем 400")
    public void spaceTokenTest() {
        accessToken = " ";
        Response response = step.createOrder(accessToken);
        ErrorResponse error = step.getErrorResponse(accessToken);
        String errorMessage = error.getError().getMessage();
        assertEquals("Invalid authorization header format!", errorMessage);
        step.tokenIsSpaceUnauthorized(response);

    }

    @Test
    @DisplayName("Создаем заказ по невалидному токену - просто Bearer - ожидаем 403")
    public void shortTokenTest() {
        accessToken = "Bearer ";
        Response response = step.createOrder(accessToken);
        ErrorResponse error = step.getErrorResponse(accessToken);
        String errorMessage = error.getError().getMessage();
        assertEquals("error verifying token", errorMessage);
        step.invalidTokenForbidden(response);
    }

}
