package com.iotiq.application.service;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.domain.FacilityOrEstablishment;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.repository.ExhibitRepository;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.application.repository.FacilityOrEstablishmentRepository;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NavPointService {
    
    private final NavPointRepository navPointRepository;
    private final ExhibitionItemRepository exhibitionItemRepository;
    private final ExhibitRepository exhibitRepository;
    private final FacilityOrEstablishmentRepository facilityRepository;
    @Transactional
    public void create(NavPointCreateRequest request) {
        
        Set<ExhibitionItem> exhibitionItems = new HashSet<>(exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds()));
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new EntityNotFoundException("One or more ExhibitionItems not found.");
        }
        
        Set<Exhibit> exhibits = exhibitRepository.findAllByIdIn(request.exhibitIds());
        if (exhibits.size() != request.exhibitIds().size()) {
            throw new EntityNotFoundException("One or more Exhibit not found.");
        }
                
        Set<FacilityOrEstablishment> facility = facilityRepository.findAllByIdIn(request.facilityIds());
        if (facility.size() != request.facilityIds().size()) {
            throw new EntityNotFoundException("One or more facility not found.");
        }

        Set<NavPoint> children = navPointRepository.findAllByIdIn(request.childrenIds());
        if (children.size() != request.childrenIds().size()) {
            throw new EntityNotFoundException("One or more nav_point children not found.");
        }
        
        NavPoint navPoint = new NavPoint();
        navPoint.setMapId(request.mapId());
        navPoint.setChildren(children);
        navPoint.setExhibits(exhibits);
        navPoint.setFacilities(facility);
        navPoint.setExhibitionItems(exhibitionItems);

        // Save NavPoint entity to the database
        navPointRepository.save(navPoint);
    }
    
}
