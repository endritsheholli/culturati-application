package com.iotiq.application.messages.exhibitionItem;

import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.domain.Location;
import com.iotiq.application.messages.location.LocationDto;

import java.util.UUID;

public record ExhibitionItemDto(UUID id, String wikiId, String path, String title, LocationDto location) {
    public static ExhibitionItemDto of(ExhibitionItem exhibitionItem) {
        return new ExhibitionItemDto(
                exhibitionItem.getId(),
                exhibitionItem.getWikiId(),
                exhibitionItem.getPath(),
                exhibitionItem.getTitle(),
                LocationDto.of(exhibitionItem.getLocation()));
    }
}
