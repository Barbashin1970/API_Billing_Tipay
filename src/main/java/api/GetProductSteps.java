package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static secret.Secret.URL_ORDER;
import static url.Constants.GET_APP_PRODUCTS;
import static url.Constants.GET_APP_PRODUCTS_SKU;

public class GetProductSteps {

    @Step("Получение списка продуктов по игре")
    public Response getAllProductList() {
        return given()
                .header("accept", "application/json")
                .baseUri(URL_ORDER)
                .get(GET_APP_PRODUCTS);
    }

    @Step("Получение списка продуктов по игре и названию продукта")
    public Response getSkuProductList() {
        return given()
                .header("accept", "application/json")
                .baseUri(URL_ORDER)
                .get(GET_APP_PRODUCTS_SKU);

    }

    @Step("Получение списка продуктов по игре и названию продукта")
    public Response getProductListSkuParameter(String sku) {
        return given()
                .header("accept", "application/json")
                .baseUri(URL_ORDER)
                .get(GET_APP_PRODUCTS + "/" + sku);

    }
}