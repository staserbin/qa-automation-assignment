package com.flamingo.qa.api.clients;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import com.flamingo.qa.api.core.config.ConfigReader;
import com.flamingo.qa.api.models.graphql.GraphQlRequest;
import com.flamingo.qa.api.models.graphql.GraphQlResponse;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GraphQlClient {

    private static final String GRAPHQL_URL = ConfigReader.get("graphql.url");

    private final RequestSpecification requestSpec;

    public GraphQlClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    @Step("Execute GraphQL query: {description}")
    public GraphQlResponse execute(String description, GraphQlRequest request) {
        return given()
                .spec(requestSpec)
                .baseUri(GRAPHQL_URL)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .as(GraphQlResponse.class);
    }

    @Step("Execute GraphQL query expecting error: {description}")
    public GraphQlResponse executeExpectingError(String description, GraphQlRequest request) {
        return given()
                .spec(requestSpec)
                .baseUri(GRAPHQL_URL)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(400)))
                .extract()
                .as(GraphQlResponse.class);
    }
}