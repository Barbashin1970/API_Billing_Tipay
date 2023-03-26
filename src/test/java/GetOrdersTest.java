import api.GetOrderSteps;
import api.PostOrderSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.Product;

import static api.Auth.getToken;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class GetOrdersTest {

    GetOrderSteps getStep = new GetOrderSteps();
    PostOrderSteps postStep = new PostOrderSteps();
    private String accessToken;

    @Test
    @DisplayName("Запрашиваем список всех заказов и проверяем message = OK")
    public void getAllOrdersListTest() {
        accessToken = getToken();
        Response response = getStep.getAllOrdersList(accessToken);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

    @Test
    @DisplayName("Создаем заказ, запрашиваем данные заказа по order_id и сравниваем номера заказов совпадают")
    public void getOrderInfoTest() throws InterruptedException {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_id = product.getData().getOrder_id();
        Thread.sleep(2000);
        Product newProduct = getStep.getOrderInfo(accessToken, order_id);
        String newOrder_id = newProduct.getData().getOrder_id();
        assertEquals(order_id, newOrder_id);
    }

    @Test
    @DisplayName("Создаем заказ и в теле ответа есть <order_token> = 64 знака")
    public void orderTokenLengthTest() {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_token = product.getData().getOrder_token();
        assertEquals(64, order_token.length());
    }

    @Test
    @DisplayName("Создаем заказ, запрашиваем данные заказа по order_id и проверяем что order_token не отображается в ответе")
    public void orderTokenEscapeTest() throws InterruptedException {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_id = product.getData().getOrder_id();
        Thread.sleep(2000);
        Product newProduct = getStep.getOrderInfo(accessToken, order_id);
        String order_token = newProduct.getData().getOrder_token();
        assertEquals(order_token, "");
    }

    @Test
    @DisplayName("Создаем заказ и проводим верификацию VERIFY токена и номера заказа")
    public void orderTokenAndIdentNumberTest() {
        accessToken = getToken();
        Product product = postStep.getResponseSerial(accessToken);
        String order_token = product.getData().getOrder_token();
        String order_id = product.getData().getOrder_id();
        Response response = getStep.verifyOrder(accessToken, order_id, order_token);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

}
