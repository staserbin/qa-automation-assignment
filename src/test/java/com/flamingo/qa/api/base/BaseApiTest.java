package com.flamingo.qa.api.base;

import com.flamingo.qa.api.clients.AuthClient;
import com.flamingo.qa.api.clients.BookingClient;
import com.flamingo.qa.api.clients.GraphQlClient;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

import static com.flamingo.qa.api.core.rest.RestAssuredConfig.getRequestSpecification;


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