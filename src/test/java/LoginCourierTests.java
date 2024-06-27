import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.Courier;
import steps.StepsForCourier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests {

    Courier courier = new Courier("kekc", "kekcuk");
    StepsForCourier stepsForCourier = new StepsForCourier();

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Позитивная проверка статус-кода и тела ответа при авторизации курьера")
    public void loginCourierCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier();
        stepsForCourier.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Авторизация курьера с указанием неверного логина")
    @Description("Проверка статус-кода и тела ответа при авторизации курьера с неверным логином")
    public void loginCourierWithWrongLoginCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier();
        stepsForCourier.setCourier(new Courier("kekc1", "kekcuk"));
        stepsForCourier.loginCourier()
                .then().assertThat().body("message", is("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация курьера с указанием неверного пароля")
    @Description("Проверка статус-кода и тела ответа при авторизации курьера с неверным паролем")
    public void loginCourierWithWrongPasswordCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier();
        stepsForCourier.setCourier(new Courier("kekc", "kekcuk1"));
        stepsForCourier.loginCourier()
                .then().assertThat().body("message", is("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка статус-кода и тела ответа при попытке авторизации курьера без логина")
    public void loginCourierWithoutLoginCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier();
        stepsForCourier.setCourier(new Courier("", "kekcuk1"));
        stepsForCourier.loginCourier()
                .then().assertThat().body("message", is("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка статус-кода и тела ответа при попытке авторизации курьера без пароля")
    public void loginCourierWithoutPasswordCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier();
        stepsForCourier.setCourier(new Courier("kekc", ""));
        stepsForCourier.loginCourier()
                .then().assertThat().body("message", is("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }



    @After
    public void delete() {
        stepsForCourier.deleteCourier();
    }
}
