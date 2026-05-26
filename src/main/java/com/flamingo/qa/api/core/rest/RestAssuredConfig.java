package com.flamingo.qa.api.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flamingo.qa.api.core.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredConfig {

    private RestAssuredConfig() {}

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("base.url"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new AllureRestAssured())
                .setConfig(io.restassured.config.RestAssuredConfig.config()
                        .objectMapperConfig(new ObjectMapperConfig()
                                .jackson2ObjectMapperFactory((cls, charset) -> {
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                                    return mapper;
                                })))
                .build();
    }
}