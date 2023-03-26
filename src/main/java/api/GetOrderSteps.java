package api;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Product;

import static io.qameta.allure.model.Parameter.Mode.MASKED;
import static io.restassured.RestAssured.given;
import static url.Constants.*;

public class GetOrderSteps {

    @Step("Получение списка последних 100 заказов")
    public Response getAllOrdersList(@Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_ORDER)
                .get(GET_ALL_ORDERS);
    }

    @Step("Получение информации о заказе по его order_id")
    public Product getOrderInfo(@Param(mode = MASKED) String accessToken, String order_id) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_ORDER)
                .get(GET_ORDER_INFO + order_id)
                .as(Product.class);
    }

    @Step("Отгрузка заказа по его order_id - CONSUMED")
    public Response ackOrder(@Param(mode = MASKED) String accessToken, String order_id) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_ORDER)
                .post(POST_ORDER_ACK + order_id + "/ack");
    }
    @Step("Проверка заказа - VERIFY - по токену и номеру заказа")
    public Response verifyOrder(@Param(mode = MASKED) String accessToken, String order_id, String order_token) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("order", order_id)
                .queryParam("orderToken", order_token)
                .baseUri(URL_ORDER)
                .get(GET_VERIFY);
    }

}