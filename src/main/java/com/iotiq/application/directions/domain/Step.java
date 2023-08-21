package com.iotiq.application.directions.domain;

import lombok.Value;

import java.util.List;

@Value
public class Step {

    //    String geometry;
//    Maneuver maneuver;
//    String mode;
//    String drivingSide;
    String name;
    //    List<Intersection> intersections;
//    Double weight;
    Double duration;
    Double distance;
    Integer type;
    String instruction;
    List<Integer> waypoints;
}


