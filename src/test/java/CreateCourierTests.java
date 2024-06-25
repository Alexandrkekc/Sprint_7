import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.Courier;
import steps.StepsForCourier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests {

    Courier courier = new Courier("kekc", "kekcuk", "Alex17031994");
    StepsForCourier stepsForCourier = new StepsForCourier();

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Позитивная проверка статус-кода и тела ответа при создании курьера")
    public void createCourierCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Создание курьера-дубликата")
    @Description("Проверка статус-кода и тела ответа при создании курьера-дубликата")
    public void createCourierDuplicateCheck() {
        stepsForCourier.setCourier(courier);
        stepsForCourier.createCourier();
        stepsForCourier.createCourier()
                .then().assertThat().body("message", is("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без указания логина")
    @Description("Проверка статус-кода и тела ответа при создании курьера без логина")
    public void createCourierWithoutLoginCheck() {
        stepsForCourier.setCourier(new Courier("", "kekcuk", "Alex17031994"));
        stepsForCourier.createCourier()
                .then().assertThat().body("message", is("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без указания пароля")
    @Description("Проверка статус-кода и тела ответа при создании курьера без пароля")
    public void createCourierWithoutPasswordCheck() {
        stepsForCourier.setCourier(new Courier("kekc", "", "Alex17031994"));
        stepsForCourier.createCourier()
                .then().assertThat().body("message", is("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без указания имени")
    @Description("Проверка статус-кода и тела ответа при создании курьера без имени")
    public void createCourierWithoutNameCheck() {
        stepsForCourier.setCourier(new Courier("kekc", "kekcuk", ""));
        stepsForCourier.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @After
    public void delete() {
        stepsForCourier.deleteCourier();
    }

}