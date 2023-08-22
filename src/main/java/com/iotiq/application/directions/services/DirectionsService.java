package com.iotiq.application.directions.services;

import com.iotiq.application.directions.domain.Coordinates;
import com.iotiq.application.directions.domain.RouteResponse;

public interface DirectionsService {
    RouteResponse findRoute(Coordinates start, Coordinates end);
}
