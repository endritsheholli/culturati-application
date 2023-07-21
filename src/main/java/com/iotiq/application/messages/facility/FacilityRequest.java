package com.iotiq.application.messages.facility;

import com.iotiq.commons.exceptions.RequiredFieldMissingException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record FacilityRequest(@NotNull @NotEmpty String openingTime, @NotNull @NotEmpty String closingTime) {
    public FacilityRequest {
        LocalTime openingTimeParsed = LocalTime.parse(openingTime);
        LocalTime closingTimeParsed = LocalTime.parse(closingTime);
        if (openingTimeParsed.isAfter(closingTimeParsed)) {
            throw new RequiredFieldMissingException("The opening time must be before the closing time.");
        }
    }

    public LocalTime getOpeningTimeAsLocalTime() {
        return LocalTime.parse(openingTime);
    }

    public LocalTime getClosingTimeAsLocalTime() {
        return LocalTime.parse(closingTime);
    }
    
}

