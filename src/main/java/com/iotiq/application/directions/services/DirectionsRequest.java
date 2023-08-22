package com.iotiq.application.directions.services;

import com.iotiq.application.directions.domain.Coordinates;
import lombok.Data;

import java.util.List;

@Data
public class DirectionsRequest {
    List<List<Double>> coordinates;

    public DirectionsRequest(List<Coordinates> coordinates) {
        this.coordinates = coordinates.stream().map(point -> List.of(point.getLatitude(), point.getLongitude())).toList();
    }
}
