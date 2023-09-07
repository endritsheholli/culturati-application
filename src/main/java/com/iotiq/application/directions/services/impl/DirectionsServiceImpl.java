package com.iotiq.application.directions.services.impl;

import com.iotiq.application.directions.services.DirectionsService;
import org.springframework.data.geo.Point;

import java.util.List;

public class DirectionsServiceImpl implements DirectionsService {
    @Override
    public List<Point> findRoute(Point start, Point end) {
        return null;
    }
}
