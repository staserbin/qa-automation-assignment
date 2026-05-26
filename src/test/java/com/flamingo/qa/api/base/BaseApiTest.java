package com.flamingo.qa.api.base;

import com.flamingo.qa.core.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}