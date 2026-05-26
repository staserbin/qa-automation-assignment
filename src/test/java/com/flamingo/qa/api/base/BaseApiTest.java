package com.flamingo.qa.api.base;

import com.flamingo.qa.api.clients.AuthClient;
import com.flamingo.qa.api.clients.BookingClient;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

import static com.flamingo.qa.api.core.rest.RestAssuredConfig.getRequestSpecification;


public class BaseApiTest {

    protected RequestSpecification requestSpec;
    protected String authToken;
    protected BookingClient bookingClient;

    @BeforeEach
    void setUp() {
        requestSpec = getRequestSpecification();
        authToken = new AuthClient().getToken(requestSpec);
        bookingClient = new BookingClient(requestSpec);
    }
}