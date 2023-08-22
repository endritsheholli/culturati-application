package com.iotiq.application.directions.domain;

import java.util.List;

public record RouteRequest(String service, String profile, List<Coordinates> coordinates, String format) {
}
