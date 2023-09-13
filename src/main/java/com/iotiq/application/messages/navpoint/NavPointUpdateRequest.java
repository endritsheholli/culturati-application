package com.iotiq.application.messages.navpoint;

import com.iotiq.application.messages.location.LocationRequest;

import java.util.List;
import java.util.UUID;

public record NavPointUpdateRequest(LocationRequest location,
                                    List<UUID> facilityIds,
                                    List<UUID> exhibitionItemIds,
                                    List<UUID> exhibitIds,
                                    List<UUID> edgeIds) {
}
