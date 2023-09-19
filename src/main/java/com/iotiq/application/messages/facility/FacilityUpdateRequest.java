package com.iotiq.application.messages.facility;

import com.iotiq.application.messages.location.LocationRequest;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;

public record FacilityUpdateRequest(
        @NotEmpty
        String name,
        String description,
        LocalTime openingTime,
        LocalTime closingTime,
        LocationRequest location
) {

    @AssertTrue(message = "facilityRequest.openingBeforeClosing")
    public boolean isOpeningBeforeClosing() {
        if (openingTime == null || closingTime == null) {
            return false;
        }
        return openingTime().isBefore(closingTime());
    }
}
