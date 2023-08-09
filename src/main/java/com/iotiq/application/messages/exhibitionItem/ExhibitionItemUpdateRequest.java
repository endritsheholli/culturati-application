package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.messages.location.LocationDto;

public record ExhibitionItemUpdateRequest(String title, LocationDto location) {
}
