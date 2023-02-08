package org.owasp.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseConfig {
    public static RequestSpecification requestSpecification;
//    public static ResponseSpecification responseSpecification;

    @BeforeAll
    public static void setup() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://juice-shop.herokuapp.com/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

//        responseSpecification = new ResponseSpecBuilder()
//                .expectStatusCode(200)
//                .build();

        RestAssured.requestSpecification = requestSpecification;
//        RestAssured.responseSpecification = responseSpecification;
    }
}
