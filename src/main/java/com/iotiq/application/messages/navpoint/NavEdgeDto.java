package com.iotiq.application.messages.navpoint;

import com.iotiq.application.domain.NavEdge;

import java.util.UUID;

public record NavEdgeDto(UUID startingPoint, UUID endingPoint, Boolean directed) {

    public static NavEdgeDto of(NavEdge navEdge) {
        return new NavEdgeDto(navEdge.getStartingPoint().getId(), navEdge.getEndingPoint().getId(), navEdge.getDirected());
    }
}
