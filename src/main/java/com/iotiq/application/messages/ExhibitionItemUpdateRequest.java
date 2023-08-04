package com.iotiq.application.messages;

import com.iotiq.application.domain.Location;

public record ExhibitionItemUpdateRequest(String title, Location location) {
}
