package com.iotiq.application.directions.domain;

import org.springframework.data.geo.Point;

import java.util.List;

public class Intersection {
    Integer out;
    Integer in;
    List<Boolean> entry;
    List<Integer> bearings;
    Point location;
}