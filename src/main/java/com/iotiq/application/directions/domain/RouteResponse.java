package com.iotiq.application.directions.domain;

import java.util.List;

public record RouteResponse(String code, List<Route> routes, List<Waypoint> waypoints) {
}
