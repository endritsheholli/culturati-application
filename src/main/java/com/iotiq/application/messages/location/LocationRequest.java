package com.iotiq.application.messages.location;

import com.iotiq.application.domain.Location;

public record LocationRequest(Double latitude, Double longitude) {
    public static Location from(LocationRequest request) {
        if (request == null) {
            return null;
        }
        Location location = new Location();
        location.setLatitude(request.latitude);
        location.setLongitude(request.longitude);
        return location;
    }
}
