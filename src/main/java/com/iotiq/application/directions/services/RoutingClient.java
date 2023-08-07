package com.iotiq.application.directions.services;

public interface RoutingClient<R, T> {
    R getRoute(T request);
}
