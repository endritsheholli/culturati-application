package com.iotiq.application.messages.exhibit;

import com.iotiq.application.domain.ExhibitionItem;

import java.util.List;
import java.util.UUID;

public record ExhibitRequest(String name, List<UUID> exhibitionItemIds) {
}
