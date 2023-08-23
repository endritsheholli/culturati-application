package com.iotiq.application.messages.exhibit;

import com.iotiq.application.messages.location.LocationRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ExhibitUpdateRequest(@NotEmpty String name, @NotNull List<UUID> exhibitionItemIds, LocationRequest location)  {
}
