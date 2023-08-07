package com.iotiq.application.directions.services.impl;

import com.iotiq.application.directions.domain.RouteRequest;
import com.iotiq.application.directions.domain.RouteResponse;
import com.iotiq.application.directions.services.DirectionsService;
import com.iotiq.application.directions.services.OSRMRoutingClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectionsServiceImpl implements DirectionsService {

    private final OSRMRoutingClientImpl routingClient;

    @Override
    public List<Point> findRoute(Point start, Point end) {
        RouteRequest request = new RouteRequest("route", "driving", List.of(start, end), "json");
        RouteResponse route = routingClient.getRoute(request);
        return route.routes().get(0).getLegs().get(0).getSteps().stream()
                .map(step -> step.getManeuver().getLocation()).toList();
    }
}
