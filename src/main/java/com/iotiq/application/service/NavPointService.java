package com.iotiq.application.service;

import com.iotiq.application.domain.*;
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
            throw new EntityNotFoundException("exhibitionItem");
        }

        Set<Exhibit> exhibits = exhibitRepository.findAllByIdIn(request.exhibitIds());
        if (exhibits.size() != request.exhibitIds().size()) {
            throw new EntityNotFoundException("exhibit");
        }

        Set<Facility> facility = facilityRepository.findAllByIdIn(request.facilityIds());
        if (facility.size() != request.facilityIds().size()) {
            throw new EntityNotFoundException("facility");
        }

        Set<NavPoint> edges = navPointRepository.findAllByIdIn(request.edgeIds());
        if (edges.size() != request.edgeIds().size()) {
            throw new EntityNotFoundException("navPoint");
        }

        NavPoint navPoint = new NavPoint();
        Location location = new Location();
        location.setLongitude(request.location().longitude());
        location.setLatitude(request.location().latitude());
        navPoint.setLocation(location);
        navPoint.setExhibits(exhibits);
        navPoint.setFacilities(facility);
        navPoint.setExhibitionItems(exhibitionItems);

        // Set the edges and add the new NavPoint to each edge's edges collection
        for (NavPoint edge : edges) {
            navPoint.addEdge(edge);
        }
        // Save NavPoint entity to the database
        navPointRepository.save(navPoint);
    }

    public List<NavPoint> getAll() {
        return navPointRepository.findAll();
    }

    public NavPoint getOne(UUID id) {
        return navPointRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("navPoint"));
    }

    public void delete(UUID id) {
        navPointRepository.deleteById(id);
    }

    @Transactional
    public void update(UUID id, NavPointUpdateRequest request) {

        NavPoint existingNavPoint = getOne(id);

        Location location = new Location();
        location.setLongitude(request.location().longitude());
        location.setLatitude(request.location().latitude());
        existingNavPoint.setLocation(location);

        // Find the Facility objects related to the ID list from the FacilityIds and associate them with the NavPoint
        Set<Facility> facilities = facilityRepository.findAllByIdIn(request.facilityIds());
        if (facilities.size() != request.facilityIds().size()) {
            throw new EntityNotFoundException("facility");
        }
        existingNavPoint.setFacilities(facilities);

        // Find the ExhibitionItem objects related to the ID list from the ExhibitionItemIds and associate them with the NavPoint
        Set<ExhibitionItem> exhibitionItems = new HashSet<>(exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds()));
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new EntityNotFoundException("exhibitionItem");
        }
        existingNavPoint.setExhibitionItems(exhibitionItems);

        // Find the Exhibit objects related to the ID list from the ExhibitIds and associate them with the NavPoint
        Set<Exhibit> exhibits = exhibitRepository.findAllByIdIn(request.exhibitIds());
        if (exhibits.size() != request.exhibitIds().size()) {
            throw new EntityNotFoundException("exhibit");
        }
        existingNavPoint.setExhibits(exhibits);

        // Find the NavPoint objects related to the ID list from edgeIds and associate them with the NavPoint
        Set<NavPoint> edges = navPointRepository.findAllByIdIn(request.edgeIds());
        if (edges.size() != request.edgeIds().size()) {
            throw new EntityNotFoundException("navPoint");
        }
        // Remove existing edges
        for (NavPoint edge : existingNavPoint.getEdges()) {
            edge.removeEdge(existingNavPoint);
        }
        existingNavPoint.getEdges().clear();

        // Set the edges and add the new NavPoint to each edge's edges collection
        for (NavPoint edge : edges) {
            existingNavPoint.addEdge(edge);
        }
        
        navPointRepository.save(existingNavPoint);
    }
}
