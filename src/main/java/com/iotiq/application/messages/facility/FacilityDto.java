package com.iotiq.application.messages.facility;

import com.iotiq.application.domain.Facility;
import com.iotiq.application.messages.location.LocationDto;

import java.time.LocalTime;
import java.util.UUID;

public record FacilityDto(UUID id, String name, String description, LocalTime openingTime, LocalTime closingTime, LocationDto location) {

    public static FacilityDto of(Facility facility) {
        return new FacilityDto(
                facility.getId(),
                facility.getName(),
                facility.getDescription(),
                facility.getOpeningTime(),
                facility.getClosingTime(),
                LocationDto.of(facility.getLocation()));
    }
}
