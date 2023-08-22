package com.iotiq.application.directions.services.impl;

import com.iotiq.application.directions.domain.Coordinates;
import com.iotiq.application.directions.domain.RouteRequest;
import com.iotiq.application.directions.domain.RouteResponse;
import com.iotiq.application.directions.services.DirectionsService;
import com.iotiq.application.directions.services.OSRMRoutingClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectionsServiceImpl implements DirectionsService {

    private final OSRMRoutingClientImpl routingClient;

    @Override
    public RouteResponse findRoute(Coordinates start, Coordinates end) {
        RouteRequest request = new RouteRequest("route", "driving", List.of(start, end), "json");
        return routingClient.getRoute(request);
    }
}
