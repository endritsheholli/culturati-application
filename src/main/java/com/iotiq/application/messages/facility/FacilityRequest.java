package com.iotiq.application.messages.facility;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;



public record FacilityRequest(
        @NotNull(message = "Opening time cannot be null.")
        @NotEmpty(message = "Opening time cannot be empty.")
        @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid opening time format. Use HH:mm.")
        String openingTime,

        @NotNull(message = "Closing time cannot be null.")
        @NotEmpty(message = "Closing time cannot be empty.")
        @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid closing time format. Use HH:mm.")
        String closingTime
) {

    public LocalTime getOpeningTimeAsLocalTime() {
        return LocalTime.parse(openingTime);
    }

    public LocalTime getClosingTimeAsLocalTime() {
        return LocalTime.parse(closingTime);
    }
}

