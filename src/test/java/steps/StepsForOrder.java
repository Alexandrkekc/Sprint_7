package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.Order;

import static io.restassured.RestAssured.given;

public class StepsForOrder {

    private Order order;
    private static final String ORDER_ENDPOINT = "api/v1/orders";

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ, проверить тело и код ответа")
    public Response createOrder() {
        Response response =
                given().log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(ORDER_ENDPOINT);
        return response;
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        Response response =
                given().log().all()
                        .get(ORDER_ENDPOINT);
        return response;
    }
}
