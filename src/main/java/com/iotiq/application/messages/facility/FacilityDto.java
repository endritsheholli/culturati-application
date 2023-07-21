package com.iotiq.application.messages.facility;

import com.iotiq.application.domain.FacilityOrEstablishment;

import java.time.LocalTime;
import java.util.UUID;

public record FacilityDto(UUID id, LocalTime openingTime, LocalTime closingTime) {

    public static FacilityDto of(FacilityOrEstablishment facility) {
        return new FacilityDto(facility.getId(), facility.getOpeningTime(), facility.getClosingTime());
    }
}
