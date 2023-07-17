package com.iotiq.application.messages.Exhibit;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;

import java.util.List;
import java.util.UUID;

public record ExhibitDto(UUID id, String name, List<ExhibitionItem> items) {
    
    public static ExhibitDto of(Exhibit exhibit){
        return new ExhibitDto(
                exhibit.getId(),
                exhibit.getName(),
                exhibit.getItems()
        );
    }
}
