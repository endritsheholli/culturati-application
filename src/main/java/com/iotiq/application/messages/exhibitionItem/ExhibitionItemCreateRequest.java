package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.domain.Location;

public record ExhibitionItemCreateRequest(String title, String path, Location location) {
}
