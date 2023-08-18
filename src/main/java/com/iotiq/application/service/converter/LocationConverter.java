package com.iotiq.application.service.converter;

import com.iotiq.application.domain.Location;
import com.iotiq.application.messages.location.LocationRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter implements Converter<LocationRequest, Location> {

    @Override
    public void convert(LocationRequest source, Location target) {
        if (source == null) {
            return;
        }
        target.setLatitude(source.latitude());
        target.setLongitude(source.longitude());
    }

    @Override
    @NotNull
    public Location convert(LocationRequest source) {
        Location target = new Location();
        convert(source, target);
        return target;
    }
}
