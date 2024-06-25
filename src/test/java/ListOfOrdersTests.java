import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.Order;
import steps.StepsForOrder;

import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTests {

    Order order;
    StepsForOrder stepsForOrder = new StepsForOrder();

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Позитивная проверка статус-кода и тела ответа при получении списка заказо")
    public void getOrdersList() {
        stepsForOrder.getOrdersList()
                .then().assertThat().body("orders", notNullValue());
    }
}