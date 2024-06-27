import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.Order;
import steps.StepsForOrder;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    Order order;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public CreateOrderTests(Order order) {
        this.order = order;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Parameterized.Parameters
    public static Object[][] getOrdersNumbers() {
        return new Object[][] {
                {new Order("Александр", "Демешко", "Самарская, 1", 1, "89997771122", 1, "2024-08-08", "<3", new String[]{"BLACK", "GREY"})},
                {new Order("Александр", "Демешко", "Самарская, 1", 1, "89997771122", 1, "2024-08-08", "<3", new String[]{"BLACK"})},
                {new Order("Александр", "Демешко", "Самарская, 1", 1, "89997771122", 1, "2024-08-08", "<3", new String[]{"GREY"})},
                {new Order("Александр", "Демешко", "Самарская, 1", 1, "89997771122", 1, "2024-08-08", "<3", new String[]{})}
        };
    }

    StepsForOrder stepsForOrder = new StepsForOrder();

    @Test
    @DisplayName("Создание заказа")
    @Description("Позитивная проверка статус-кода и тела ответа при создании заказа")
    public void createdOrderCheck() {
        stepsForOrder.setOrder(order);
        stepsForOrder.createOrder()
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }
}