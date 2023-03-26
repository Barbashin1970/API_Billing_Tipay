import api.GetProductSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class WrongSkuTest {
    private final String sku;
    GetProductSteps getProd = new GetProductSteps();
    public WrongSkuTest(String sku) {
        this.sku = sku;
    }

    @Parameterized.Parameters(name = "Sku {index} -> sku: {0}")
    public static Object[][] dataForTest() {
        return new Object[][]{
                {"black_rocket"},
                {"white_rocket"},
                {"red_rocket"},
        };
    }
   // @Ignore
    @Test
    @DisplayName("Запрашиваем уточненный список продуктов по названию sku продукта")
    public void getProductListTest() {
        Response response = getProd.getProductListSkuParameter(sku);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(500);
    }

}
