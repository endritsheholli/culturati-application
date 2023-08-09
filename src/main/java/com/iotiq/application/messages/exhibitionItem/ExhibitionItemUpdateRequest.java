package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.messages.location.LocationRequest;

public record ExhibitionItemUpdateRequest(String title, LocationRequest location) {
}
