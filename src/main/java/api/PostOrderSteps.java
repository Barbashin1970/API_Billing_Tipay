package api;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.ErrorResponse;
import pojo.Product;

import static io.qameta.allure.model.Parameter.Mode.MASKED;
import static io.restassured.RestAssured.given;
import static secret.Secret.URL_ORDER;
import static url.Constants.CANCEL_ORDER;
import static url.Constants.POST_CREATE_ORDER;


public class PostOrderSteps {


    @Step("Печать данных по заказу")
    public static void printOrder(Product product) {
        // можно извлечь геттерами всю информацию по заказу
        System.out.println("================== отчет по заказу ========================");
        String app = product.getData().getApp();
        System.out.println("Название игры app " + app);
        String sku = product.getData().getCart().get(0).getSku();
        System.out.println("Краткое название продукта sku " + sku);
        String full_name = product.getData().getCart().get(0).getFull_name();
        System.out.println("Полное название full_name " + full_name);
        int amount = product.getData().getCart().get(0).getAmount();
        System.out.println("Количество в штуках amount " + amount);
        int price = product.getData().getCart().get(0).getPrice();
        System.out.println("Цена по прайсу price " + price);
        String order_id = product.getData().getOrder_id();
        System.out.println("order_id " + order_id);
        String order_token = product.getData().getOrder_token();
        System.out.println("order_token " + order_token);
        String status = product.getData().getState();
        System.out.println("Текущий статус заказа state " + status);
        System.out.println("==========================================================");

    }

    @Step("Создание заказа")
    public Response createOrder(@Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_ORDER)
                .post(POST_CREATE_ORDER);
    }


    @Step("Десериализация ответа сервера")
    public Product getResponseSerial(@Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_ORDER)
                .post(POST_CREATE_ORDER)
                .as(Product.class);
    }

    @Step("Десериализация сообщения об ошибке")
    public ErrorResponse getErrorResponse(@Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_ORDER)
                .post(POST_CREATE_ORDER)
                .as(ErrorResponse.class);
    }

    @Step("Вместо токена пробел - ожидаем 400")
    public void tokenIsSpaceUnauthorized(Response response) {
        response.then()
                .statusCode(400);
    }

    @Step("Просроченный токен - просто Bearer пока отправляем - ожидаем 403")
    public void invalidTokenForbidden(Response response) {
        response.then()
                .statusCode(403);
    }

    @Step("Помечаем заказ на удаление <CANCEL>")
    public Response cancelOrder(String order_id) {
        return given()
                .header("accept", "application/json")
                .baseUri(URL_ORDER)
                .post(CANCEL_ORDER + order_id);
    }

}
