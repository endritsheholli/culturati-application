package com.iotiq.application.directions.services;

import lombok.Data;
import org.springframework.data.geo.Point;

import java.util.List;

@Data
public class DirectionsRequest {
    List<List<Double>> coordinates;

    public DirectionsRequest(List<Point> coordinates) {
        this.coordinates = coordinates.stream().map(point -> List.of(point.getX(), point.getY())).toList();
    }
}
