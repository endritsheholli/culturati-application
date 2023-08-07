package com.iotiq.application.directions.domain;

import lombok.Value;

import java.util.List;

@Value
public class Leg {
    List<Step> steps;
    String summary;
    Double weight;
    Double duration;
    Double distance;
}
