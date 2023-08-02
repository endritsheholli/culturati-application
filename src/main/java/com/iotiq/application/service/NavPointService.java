package com.iotiq.application.service;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.domain.Facility;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.messages.navpoint.NavPointUpdateRequest;
import com.iotiq.application.repository.ExhibitRepository;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.application.repository.FacilityRepository;
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
    private final FacilityRepository facilityRepository;

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

        Set<Facility> facility = facilityRepository.findAllByIdIn(request.facilityIds());
        if (facility.size() != request.facilityIds().size()) {
            throw new EntityNotFoundException("One or more facility not found.");
        }

        Set<NavPoint> children = navPointRepository.findAllByIdIn(request.edgeIds());
        if (children.size() != request.edgeIds().size()) {
            throw new EntityNotFoundException("One or more nav_point children not found.");
        }

        NavPoint navPoint = new NavPoint();
        navPoint.setMapId(request.mapId());
        navPoint.setEdges(children);
        navPoint.setExhibits(exhibits);
        navPoint.setFacilities(facility);
        navPoint.setExhibitionItems(exhibitionItems);

        // Save NavPoint entity to the database
        navPointRepository.save(navPoint);
    }

    public List<NavPoint> getAll() {
        return navPointRepository.findAll();
    }

    public NavPoint getOne(UUID id) {
        return navPointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("NavPoint"));
    }

    public void delete(UUID id) {
        navPointRepository.deleteById(id);
    }

    @Transactional
    public void update(UUID id, NavPointUpdateRequest request) {

        NavPoint existingNavPoint = getOne(id);

        existingNavPoint.setMapId(request.mapId());

        // Find the Facility objects related to the ID list from the FacilityIds and associate them with the NavPoint
        Set<Facility> facilities = facilityRepository.findAllByIdIn(request.facilityIds());
        if (facilities.size() != request.facilityIds().size()) {
            throw new EntityNotFoundException("One or more facility not found.");
        }
        existingNavPoint.setFacilities(facilities);

        // Find the ExhibitionItem objects related to the ID list from the ExhibitionItemIds and associate them with the NavPoint
        Set<ExhibitionItem> exhibitionItems = new HashSet<>(exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds()));
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new EntityNotFoundException("One or more ExhibitionItems not found.");
        }
        existingNavPoint.setExhibitionItems(exhibitionItems);

        // Find the Exhibit objects related to the ID list from the ExhibitIds and associate them with the NavPoint
        Set<Exhibit> exhibits = exhibitRepository.findAllByIdIn(request.exhibitIds());
        if (exhibits.size() != request.exhibitIds().size()) {
            throw new EntityNotFoundException("One or more Exhibit not found.");
        }
        existingNavPoint.setExhibits(exhibits);

        // Find the NavPoint objects related to the ID list from edgeIds and associate them with the NavPoint
        Set<NavPoint> children = navPointRepository.findAllByIdIn(request.edgeIds());
        if (children.size() != request.edgeIds().size()) {
            throw new EntityNotFoundException("One or more nav_point children not found.");
        }
        existingNavPoint.setEdges(children);

        navPointRepository.save(existingNavPoint);
    }
}
