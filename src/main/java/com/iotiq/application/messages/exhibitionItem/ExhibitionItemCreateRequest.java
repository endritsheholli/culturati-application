package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.messages.location.LocationRequest;

public record ExhibitionItemCreateRequest(String title, String path, LocationRequest location) {
}
