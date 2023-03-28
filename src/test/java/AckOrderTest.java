import api.GetOrderSteps;
import api.PostOrderSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.Product;

import static api.Auth.getToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AckOrderTest {
    String accessToken;
    String order_id;
    GetOrderSteps getStep = new GetOrderSteps();
    PostOrderSteps postStep = new PostOrderSteps();

    @Test
    @DisplayName("Подтверждаем отгрузку <ack> и проверяем message = OK")
    public void acknowledgeOrderTest() {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        order_id = product.getData().getOrder_id();
        Response response = getStep.ackOrder(accessToken, order_id);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

    @Test
    @DisplayName("Подтверждаем отгрузку <ack> и через 20 секунд проверяем status = CONSUMED")
    public void consumedOrderTest() throws InterruptedException {
        accessToken = getToken(); // получили токен авторизации
        Product product = postStep.getResponseSerial(accessToken); // создали заказ
        order_id = product.getData().getOrder_id(); // достали номер заказа
        getStep.ackOrder(accessToken, order_id); // подтвердили заказ
        Thread.sleep(20000);
        Product newProduct = getStep.getOrderInfo(accessToken, order_id); // получаем инфо по ордеру
        String status = newProduct.getData().getState(); // достаем статус заказа
        assertThat(status, either(is("CONSUMED")).or(is("PAYED")).or(is("PAYMENT_CREATED")));

    }
}
