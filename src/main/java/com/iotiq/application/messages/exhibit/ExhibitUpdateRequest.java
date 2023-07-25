package com.iotiq.application.messages.exhibit;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ExhibitUpdateRequest(@NotEmpty String name, @NotNull List<UUID> exhibitionItemIds)  {
}
