package com.flamingo.qa.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flamingo.qa.api.models.request.Booking;
import lombok.Data;

@Data
public class BookingResponse {

    @JsonProperty("bookingid")
    private int bookingid;

    @JsonProperty("booking")
    private Booking booking;
}