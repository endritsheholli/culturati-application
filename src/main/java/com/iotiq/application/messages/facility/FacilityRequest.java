package com.iotiq.application.messages.facility;


import com.iotiq.commons.exceptions.RequiredFieldMissingException;

import java.time.LocalTime;



public record FacilityRequest(
        LocalTime openingTime,
        LocalTime closingTime
) {
    public FacilityRequest {
        if (openingTime.isAfter(closingTime)) {
            throw new RequiredFieldMissingException("The opening time must be before the closing time.");
        }
    }
}
