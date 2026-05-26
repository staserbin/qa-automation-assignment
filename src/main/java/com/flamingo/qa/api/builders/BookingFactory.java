package com.flamingo.qa.api.builders;

import com.flamingo.qa.api.models.request.Booking;
import com.flamingo.qa.api.models.request.BookingDates;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingFactory {

    private BookingFactory() {}

    private static final Faker faker = new Faker();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Booking defaultBooking() {
        LocalDate checkIn = LocalDate.now().plusDays(faker.number().numberBetween(1, 30));
        LocalDate checkOut = checkIn.plusDays(faker.number().numberBetween(1, 14));

        return Booking.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(50, 1000))
                .depositpaid(faker.bool().bool())
                .bookingdates(BookingDates.builder()
                        .checkin(checkIn.format(DATE_FORMAT))
                        .checkout(checkOut.format(DATE_FORMAT))
                        .build())
                .additionalneeds(faker.options().option("Breakfast", "Lunch", "Dinner", "Late checkOut",
                        "Early checkIn"))
                .build();
    }

    public static Booking updatedBooking() {
        LocalDate checkIn = LocalDate.now().plusDays(faker.number().numberBetween(31, 60));
        LocalDate checkOut = checkIn.plusDays(faker.number().numberBetween(1, 14));

        return Booking.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(50, 1000))
                .depositpaid(faker.bool().bool())
                .bookingdates(BookingDates.builder()
                        .checkin(checkIn.format(DATE_FORMAT))
                        .checkout(checkOut.format(DATE_FORMAT))
                        .build())
                .additionalneeds(faker.options().option("Breakfast", "Lunch", "Dinner", "Late checkOut",
                        "Early checkIn"))
                .build();
    }
}