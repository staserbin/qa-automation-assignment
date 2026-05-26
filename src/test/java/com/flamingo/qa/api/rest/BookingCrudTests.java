package com.flamingo.qa.api.rest;

import com.flamingo.qa.api.base.BaseApiTest;
import com.flamingo.qa.api.builders.BookingFactory;
import com.flamingo.qa.api.models.request.Booking;
import com.flamingo.qa.api.models.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@Epic("Restful Booker API")
@Feature("Booking CRUD")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.SAME_THREAD)
class BookingCrudTests extends BaseApiTest {

    private static int bookingId;
    private static Booking createdPayload;

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that 'POST /booking' creates a booking and returns a valid ID with matching data")
    void createBookingSuccessTest() {
        createdPayload = BookingFactory.defaultBooking();

        BookingResponse response = bookingClient.createBooking(createdPayload);

        assertThat(response.getBookingid())
                .as("Booking ID should be a positive number")
                .isPositive();
        assertThat(response.getBooking().getFirstname())
                .as("First name should match the request payload")
                .isEqualTo(createdPayload.getFirstname());
        assertThat(response.getBooking().getLastname())
                .as("Last name should match the request payload")
                .isEqualTo(createdPayload.getLastname());
        assertThat(response.getBooking().getTotalprice())
                .as("Total price should match the request payload")
                .isEqualTo(createdPayload.getTotalprice());
        assertThat(response.getBooking().isDepositpaid())
                .as("Deposit paid flag should match the request payload")
                .isEqualTo(createdPayload.isDepositpaid());
        assertThat(response.getBooking().getBookingdates().getCheckin())
                .as("Check-in date should match the request payload")
                .isEqualTo(createdPayload.getBookingdates().getCheckin());
        assertThat(response.getBooking().getBookingdates().getCheckout())
                .as("Check-out date should match the request payload")
                .isEqualTo(createdPayload.getBookingdates().getCheckout());

        bookingId = response.getBookingid();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that 'GET /booking/{id}' returns the correct booking data for the created booking")
    void getBookingByIdSuccessTest() {
        Booking actual = bookingClient.getBookingById(bookingId);

        assertThat(actual.getFirstname())
                .as("First name should match the created booking")
                .isEqualTo(createdPayload.getFirstname());
        assertThat(actual.getLastname())
                .as("Last name should match the created booking")
                .isEqualTo(createdPayload.getLastname());
        assertThat(actual.getTotalprice())
                .as("Total price should match the created booking")
                .isEqualTo(createdPayload.getTotalprice());
        assertThat(actual.isDepositpaid())
                .as("Deposit paid flag should match the created booking")
                .isEqualTo(createdPayload.isDepositpaid());
        assertThat(actual.getBookingdates().getCheckin())
                .as("Check-in date should match the created booking")
                .isEqualTo(createdPayload.getBookingdates().getCheckin());
        assertThat(actual.getBookingdates().getCheckout())
                .as("Check-out date should match the created booking")
                .isEqualTo(createdPayload.getBookingdates().getCheckout());
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that 'PUT /booking/{id}' fully replaces all booking fields with new values")
    void updateBookingSuccessTest() {
        Booking updated = BookingFactory.updatedBooking();

        Booking result = bookingClient.updateBooking(bookingId, authToken, updated);

        assertThat(result.getFirstname())
                .as("First name should be updated")
                .isEqualTo(updated.getFirstname());
        assertThat(result.getLastname())
                .as("Last name should be updated")
                .isEqualTo(updated.getLastname());
        assertThat(result.getTotalprice())
                .as("Total price should be updated")
                .isEqualTo(updated.getTotalprice());
        assertThat(result.isDepositpaid())
                .as("Deposit paid flag should be updated")
                .isEqualTo(updated.isDepositpaid());
        assertThat(result.getAdditionalneeds())
                .as("Additional needs should be updated")
                .isEqualTo(updated.getAdditionalneeds());
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that 'DELETE /booking/{id}' removes the booking and subsequent GET should return 404")
    void deleteBookingSuccessTest() {
        bookingClient.deleteBooking(bookingId, authToken);

        int statusAfterDelete = bookingClient.getBookingRaw(bookingId)
                .statusCode();

        assertThat(statusAfterDelete)
                .as("GET after DELETE should return '404 Not Found'")
                .isEqualTo(404);
    }
}