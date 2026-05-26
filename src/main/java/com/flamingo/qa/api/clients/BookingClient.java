package com.flamingo.qa.api.clients;

import com.flamingo.qa.api.models.request.Booking;
import com.flamingo.qa.api.models.response.BookingResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookingClient {

    private static final String BOOKING_ENDPOINT = "/booking";
    private static final String BOOKING_BY_ID    = "/booking/{id}";

    private final RequestSpecification requestSpec;

    public BookingClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    @Step("POST /booking - create new booking")
    public BookingResponse createBooking(Booking booking) {
        return given()
                .spec(requestSpec)
                .body(booking)
                .when()
                .post(BOOKING_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }

    @Step("GET /booking/{id} - retrieve booking by ID: {id}")
    public Booking getBookingById(int id) {
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get(BOOKING_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);
    }

    @Step("PUT /booking/{id} - full update of booking ID: {id}")
    public Booking updateBooking(int id, String token, Booking booking) {
        return given()
                .spec(requestSpec)
                .cookie("token", token)
                .pathParam("id", id)
                .body(booking)
                .when()
                .put(BOOKING_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);
    }

    @Step("DELETE /booking/{id} - delete booking ID: {id}")
    public Response deleteBooking(int id, String token) {
        return given()
                .spec(requestSpec)
                .cookie("token", token)
                .pathParam("id", id)
                .when()
                .delete(BOOKING_BY_ID)
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    @Step("GET /booking/{id} - raw response (no status assertion)")
    public Response getBookingRaw(int id) {
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get(BOOKING_BY_ID)
                .then()
                .extract()
                .response();
    }
}