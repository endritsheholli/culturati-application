package com.iotiq.application.messages.location;

import com.iotiq.application.domain.Location;

public record LocationDto(Double latitude, Double longitude) {

    public static LocationDto of(Location location) {
        if (location == null) {
            return null;
        }
        return new LocationDto(location.getLatitude(), location.getLongitude());
    }
}
