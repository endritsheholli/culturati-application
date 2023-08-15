package com.iotiq.application.service.converter;

import com.iotiq.application.domain.Location;
import com.iotiq.application.messages.location.LocationRequest;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

    public static Location convertToLocation(LocationRequest request) {
        if (request == null) {
            return null;
        }

        Location location = new Location();
        location.setLatitude(request.latitude());
        location.setLongitude(request.longitude());

        return location;
    }
}
