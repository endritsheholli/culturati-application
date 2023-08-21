package com.iotiq.application.directions.domain;

import lombok.Data;

import java.util.List;

@Data
public class RouteResponse {
    BBox bbox;
    Metadata metadata;
    List<Route> routes;
}
