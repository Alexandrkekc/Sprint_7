import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class StepsForCourier {
    private Courier courier;
    private static final String CREATE_ENDPOINT = "api/v1/courier";
    private static final String LOGIN_ENDPOINT = "api/v1/courier/login";

    public void setCourier(Courier courier) {
        this.courier = courier;
    }


    @Step("Создать курьера, проверить тело и код ответа")
    public Response createCourier() {
        Response response =
                given().log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(CREATE_ENDPOINT);
        return response;
    }

    @Step("Авторизоваться курьером")
    public Response loginCourier() {
        return given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_ENDPOINT);
    }


    @Step("Удалить курьера")
    public void deleteCourier() {
        Integer id =
                given().log().all()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post(LOGIN_ENDPOINT)
                        .then().extract().body().path("id");
        if (id != null) {
            given()
                    .delete(CREATE_ENDPOINT + "/{id}", id.toString());
        }
    }
}