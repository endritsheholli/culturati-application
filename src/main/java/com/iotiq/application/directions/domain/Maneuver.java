package com.iotiq.application.directions.domain;

import lombok.Value;
import org.springframework.data.geo.Point;

@Value
public class Maneuver {
    Integer bearingAfter;
    Integer bearingBefore;
    Point location;
    String modifier;
    String type;
}
