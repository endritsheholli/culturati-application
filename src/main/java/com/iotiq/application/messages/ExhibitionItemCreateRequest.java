package com.iotiq.application.messages;

import com.iotiq.application.domain.Location;

public record ExhibitionItemCreateRequest(String title, String path, Location location) {
}
