package com.iotiq.application.directions.domain;

import lombok.Value;

import java.util.List;

@Value
public class Route {
    List<Leg> legs;
    String weightName;
    Double weight;
    Double duration;
    Double distance;
}
