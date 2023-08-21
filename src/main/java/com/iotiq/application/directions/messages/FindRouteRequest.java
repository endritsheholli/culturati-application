package com.iotiq.application.directions.messages;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.geo.Point;

public record FindRouteRequest(@NotNull Point from, @NotNull Point to) {
}
