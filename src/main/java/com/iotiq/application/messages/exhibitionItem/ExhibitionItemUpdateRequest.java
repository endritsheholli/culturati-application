package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.domain.Location;

public record ExhibitionItemUpdateRequest(String title, Location location) {
}
