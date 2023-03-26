import api.GetOrderSteps;
import api.PostOrderSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.Product;

import static api.Auth.getToken;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CanselOrderTest {
    PostOrderSteps postStep = new PostOrderSteps();
    GetOrderSteps getStep = new GetOrderSteps();
    private String accessToken;

    @Test
    @DisplayName("Создаем заказ, сразу его отменяем и проверяем message = OK")
    public void cancelOrderOkTest() {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_id = product.getData().getOrder_id();
        Response response = postStep.cancelOrder(order_id);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

    @Test
    @DisplayName("Создаем заказ, отменяем и через 20 секунд проверяем статус = PAYMENT_CANCELED / CANCELED")
    public void cancelOrderStatusTest() throws InterruptedException {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_id = product.getData().getOrder_id();
        postStep.cancelOrder(order_id);
        Thread.sleep(20000);
        Product newProduct = getStep.getOrderInfo(accessToken, order_id);
        String status = newProduct.getData().getState();
        if ("CANCELED".equals(status)) {
            assertEquals("CANCELED", status);
        } else if ("PAYMENT_CANCELED".equals(status)) {
            assertEquals("PAYMENT_CANCELED", status);
        } else {
            assertEquals("CANCELED", status);
        }
    }


    @Test
    @DisplayName("Создаем заказ, отменяем и проверяем статус = PAYMENT_CANCELED")
    public void paymentCancelOrderStatusTest() throws InterruptedException {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_id = product.getData().getOrder_id();
        postStep.cancelOrder(order_id);
        Thread.sleep(2000);
        Product newProduct = getStep.getOrderInfo(accessToken, order_id);
        String status = newProduct.getData().getState();
        assertEquals("PAYMENT_CANCELED", status);
    }

}
