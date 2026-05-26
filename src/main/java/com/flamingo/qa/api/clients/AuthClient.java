package com.flamingo.qa.api.clients;

import com.flamingo.qa.api.core.config.ConfigReader;
import com.flamingo.qa.api.models.request.AuthRequest;
import com.flamingo.qa.api.models.response.AuthResponse;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private static final String AUTH_ENDPOINT = "/auth";

    @Step("POST /auth - obtain auth token")
    public String getToken(RequestSpecification requestSpec) {
        AuthRequest authRequest = AuthRequest.builder()
                .username(ConfigReader.get("auth.username"))
                .password(ConfigReader.get("auth.password"))
                .build();

        AuthResponse response = given()
                .spec(requestSpec)
                .body(authRequest)
                .when()
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);

        return response.getToken();
    }
}