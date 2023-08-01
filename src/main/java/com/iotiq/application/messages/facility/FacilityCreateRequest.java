package com.iotiq.application.messages.facility;


import jakarta.validation.constraints.AssertTrue;

import java.time.LocalTime;

public record FacilityCreateRequest(
        LocalTime openingTime,
        LocalTime closingTime
) {

    @AssertTrue(message = "facilityRequest.openingBeforeClosing")
    public boolean isOpeningBeforeClosing() {
        if (openingTime == null || closingTime == null) {
            return false;
        }
        return openingTime().isBefore(closingTime());
    }
}
