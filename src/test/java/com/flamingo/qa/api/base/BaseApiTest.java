package com.flamingo.qa.api.base;

import com.flamingo.qa.api.clients.AuthClient;
import com.flamingo.qa.api.clients.BookingClient;
import com.flamingo.qa.api.clients.GraphQlClient;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.flamingo.qa.api.core.rest.RestAssuredConfig.getRequestSpecification;

@ExtendWith(AllureJunit5.class)
public class BaseApiTest {

    protected RequestSpecification requestSpec;
    protected String authToken;
    protected BookingClient bookingClient;
    protected GraphQlClient graphQlClient;

    @BeforeEach
    void setUp() {
        requestSpec = getRequestSpecification();
        authToken = new AuthClient().getToken(requestSpec);
        bookingClient = new BookingClient(requestSpec);
        graphQlClient = new GraphQlClient(requestSpec);
    }
}