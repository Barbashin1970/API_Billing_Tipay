import api.GetProductSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static url.Constants.SKU;

public class GetProductTest {
    GetProductSteps getProd = new GetProductSteps();

    @Test
    @DisplayName("Запрашиваем общий список всех продуктов")
    public void getAllProductListTest() {

        Response response = getProd.getAllProductList();
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

    @Test
    @DisplayName("Запрашиваем уточненный список продуктов по названию sku продукта")
    public void getProductListTest() {

        Response response = getProd.getSkuProductList();
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

    @Test
    @DisplayName("Запрашиваем уточненный список продуктов по названию sku продукта")
    public void getSkuProductListTest() {

        Response response = getProd.getProductListSkuParameter(SKU);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .body("error", equalTo(null))
                .statusCode(200);
    }

}
