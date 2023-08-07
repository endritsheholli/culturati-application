package com.iotiq.application.directions.domain;

import org.springframework.data.geo.Point;

import java.util.List;

public record RouteRequest(String service, String profile, List<Point> coordinates, String format) {
}
