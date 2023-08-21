package com.iotiq.application.directions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Route(List<Leg> legs, Summary summary, List<Segment> segments, BBox bbox, String geometry,
                    @JsonProperty("way_points") List<Integer> waypoints) {
}
