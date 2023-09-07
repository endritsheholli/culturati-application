package com.iotiq.application.directions.services;

import org.springframework.data.geo.Point;

import java.util.List;

public interface DirectionsService {
    List<Point> findRoute(Point start, Point end);
}
