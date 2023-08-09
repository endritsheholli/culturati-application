package com.iotiq.application.messages.navpoint;

import com.iotiq.application.messages.location.LocationRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NavPointUpdateRequest(LocationRequest location,
                                    @NotNull List<UUID> facilityIds,
                                    @NotNull List<UUID> exhibitionItemIds,
                                    @NotNull List<UUID> exhibitIds,
                                    @NotNull List<UUID> edgeIds) {
}
