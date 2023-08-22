package com.iotiq.application.messages.exhibit;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemDto;

import java.util.List;
import java.util.UUID;

public record ExhibitDto(UUID id, String name, List<ExhibitionItemDto> exhibitionItems) {
    
    public static ExhibitDto of(Exhibit exhibit){
        return new ExhibitDto(
                exhibit.getId(),
                exhibit.getName(),
                exhibit.getExhibitionItems().stream().map(ExhibitionItemDto::of).toList()
        );
    }
}
