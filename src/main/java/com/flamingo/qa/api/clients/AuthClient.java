package com.flamingo.qa.api.clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthClient {

    public static String getToken(String baseUri) {
        String body = """
                {
                    "username": "admin",
                    "password": "password123"
                }
                """;

        Response response =
                given()
                        .baseUri(baseUri)
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("/auth")
                        .then()
                        .statusCode(200)
                        .extract().response();

        return response.jsonPath().getString("token");
    }
}