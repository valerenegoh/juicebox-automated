package org.owasp.api;

import org.junit.jupiter.api.Test;
import org.owasp.api.datamodels.*;

import static io.restassured.RestAssured.given;

class JuiceShopTest extends BaseConfig {
    @Test
    void loginToJuiceApp() {
        Authentication authentication = given()
                .body(loginDataBuilder()).
                when()
                .post("rest/user/login").
                then()
                .log().all()
                .statusCode(200)
                .extract().as(LoginResponse.class).getAuthentication();

        System.out.println("\ntoken = " + authentication.getToken());
    }

    @Test
    void register() {
        given()
                .body(registerCustomerDataBuilder()).
                when()
                .post("api/Users/").
                then()
                .log().all()
                .statusCode(201);
    }

    private LoginData loginDataBuilder() {
        return LoginData.builder()
                .email("shweta1@gmail.com")
                .password("Password@123")
                .build();
    }

    private RegisterCustomerData registerCustomerDataBuilder() {
        return RegisterCustomerData.builder()
                .email("shweta@gmail.com")
                .password("Password123")
                .passwordRepeat("Password123")
                .securityQuestion(SecurityQuestion.builder()
                        .updatedAt("2021-03-09T03:30:41.854Z")
                        .createdAt("2021-03-09T03:30:41.854Z")
                        .question("Your eldest siblings middle name?")
                        .build())
                .securityAnswer("xyz").build();
    }

}
