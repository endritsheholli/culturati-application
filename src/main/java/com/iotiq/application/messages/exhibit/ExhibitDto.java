package com.iotiq.application.messages.exhibit;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemDto;
import com.iotiq.application.messages.location.LocationDto;

import java.util.List;
import java.util.UUID;

public record ExhibitDto(UUID id, String name, List<ExhibitionItemDto> exhibitionItems, LocationDto location) {
    
    public static ExhibitDto of(Exhibit exhibit){
        return new ExhibitDto(
                exhibit.getId(),
                exhibit.getName(),
                exhibit.getExhibitionItems().stream().map(ExhibitionItemDto::of).toList(),
                LocationDto.of(exhibit.getLocation()));
    }
}
