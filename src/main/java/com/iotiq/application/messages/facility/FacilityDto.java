package com.iotiq.application.messages.facility;

import com.iotiq.application.domain.Facility;

import java.time.LocalTime;
import java.util.UUID;

public record FacilityDto(UUID id, LocalTime openingTime, LocalTime closingTime) {

    public static FacilityDto of(Facility facility) {
        return new FacilityDto(facility.getId(), facility.getOpeningTime(), facility.getClosingTime());
    }
}
