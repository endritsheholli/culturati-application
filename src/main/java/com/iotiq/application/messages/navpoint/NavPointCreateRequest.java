package com.iotiq.application.messages.navpoint;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NavPointCreateRequest(String mapId,
                                    @NotNull List<UUID> facilityIds,
                                    @NotNull List<UUID> exhibitionItemIds,
                                    @NotNull List<UUID> exhibitIds,
                                    @NotNull List<UUID> edgeIds) {
}