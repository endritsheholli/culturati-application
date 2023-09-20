package com.iotiq.application.messages.navpoint;

import com.iotiq.application.domain.NavEdge;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NavEdgeDto(@NotNull UUID startingPoint, @NotNull UUID endingPoint, Boolean directed) {

    public static NavEdgeDto of(NavEdge navEdge) {
        return new NavEdgeDto(navEdge.getStartingPoint().getId(), navEdge.getEndingPoint().getId(), navEdge.getDirected());
    }
}
