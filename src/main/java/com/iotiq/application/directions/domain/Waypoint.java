package com.iotiq.application.directions.domain;

import org.springframework.data.geo.Point;

public record Waypoint(String hint, Double distance, String name, Point location) {
}
