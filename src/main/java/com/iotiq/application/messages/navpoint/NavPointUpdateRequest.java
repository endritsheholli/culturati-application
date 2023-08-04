package com.iotiq.application.messages.navpoint;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NavPointUpdateRequest(double latitude,
                                    double longitude,
                                    @NotNull List<UUID> facilityIds,
                                    @NotNull List<UUID> exhibitionItemIds,
                                    @NotNull List<UUID> exhibitIds,
                                    @NotNull List<UUID> edgeIds) {
}
