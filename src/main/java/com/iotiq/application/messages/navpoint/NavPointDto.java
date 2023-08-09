package com.iotiq.application.messages.navpoint;

import com.iotiq.application.domain.Location;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemDto;
import com.iotiq.application.messages.exhibit.ExhibitDto;
import com.iotiq.application.messages.facility.FacilityDto;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record NavPointDto(UUID id,
                          Location location,
                          Set<ExhibitionItemDto> exhibitionItems,
                          Set<ExhibitDto> exhibits,
                          Set<FacilityDto> facilities,
                          Set<UUID> edgeIds) {

    public static NavPointDto of(NavPoint navPoint) {
        return new NavPointDto(navPoint.getId(),
                navPoint.getLocation(),
                navPoint.getExhibitionItems().stream().map(ExhibitionItemDto::of).collect(Collectors.toSet()),
                navPoint.getExhibits().stream().map(ExhibitDto::of).collect(Collectors.toSet()),
                navPoint.getFacilities().stream().map(FacilityDto::of).collect(Collectors.toSet()),
                navPoint.getEdges().stream().map(AbstractPersistable::getId).collect(Collectors.toSet()));
    }
}
