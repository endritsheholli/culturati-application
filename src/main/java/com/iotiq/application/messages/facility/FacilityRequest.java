package com.iotiq.application.messages.facility;


import jakarta.validation.constraints.AssertTrue;

import java.time.LocalTime;

public record FacilityRequest(
        LocalTime openingTime,
        LocalTime closingTime
) {

    @AssertTrue(message = "facilityRequest.openingBeforeClosing")
    public boolean isOpeningBeforeClosing() {
        return openingTime().isBefore(closingTime());
    }
}
