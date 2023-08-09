package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.messages.location.LocationDto;
import jakarta.validation.constraints.NotNull;

public record ExhibitionItemCreateRequest(String title, String path,@NotNull LocationDto location) {
}
