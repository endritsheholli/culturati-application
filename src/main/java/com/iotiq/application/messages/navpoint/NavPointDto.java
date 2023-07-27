package com.iotiq.application.messages.navpoint;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.domain.FacilityOrEstablishment;
import com.iotiq.application.domain.NavPoint;

import java.util.Set;
import java.util.UUID;

public record NavPointDto(UUID id,
                          String mapId,
                          Set<ExhibitionItem> exhibitionItems,
                          Set<Exhibit> exhibits,
                          Set<FacilityOrEstablishment> facilities,
                          Set<NavPoint> children) {

    public static NavPointDto of(NavPoint navPoint) {
        return new NavPointDto(navPoint.getId(),
                navPoint.getMapId(),
                navPoint.getExhibitionItems(), 
                navPoint.getExhibits(), 
                navPoint.getFacilities(), 
                navPoint.getChildren());
    }
}
