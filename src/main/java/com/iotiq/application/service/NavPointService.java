package com.iotiq.application.service;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.domain.Facility;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.location.LocationRequest;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.messages.navpoint.NavPointUpdateRequest;
import com.iotiq.application.repository.ExhibitRepository;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.application.repository.FacilityRepository;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.application.service.converter.LocationConverter;
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
    private final LocationConverter converter;


    @Transactional
    public void create(NavPointCreateRequest request) {

        NavPoint navPoint = new NavPoint();

        setPropertiesAndSave(navPoint, request.location(), request.facilityIds(), request.exhibitionItemIds(), request.exhibitIds(), request.edgeIds());
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

        NavPoint navPoint = getOne(id);

        setPropertiesAndSave(navPoint, request.location(), request.facilityIds(), request.exhibitionItemIds(), request.exhibitIds(), request.edgeIds());
    }

    private void setPropertiesAndSave(NavPoint navPoint, LocationRequest location, List<UUID> facilityIds, List<UUID> exhibitionItemIds, List<UUID> exhibitIds, List<UUID> edgeIds) {
        navPoint.setLocation(converter.convert(location));

        if(facilityIds != null) {
            Set<Facility> facility = getFacilities(facilityIds);
            navPoint.setFacilities(facility);
        }

        if(exhibitionItemIds != null) {
            Set<ExhibitionItem> exhibitionItems = getExhibitionItems(exhibitionItemIds);
            navPoint.setExhibitionItems(exhibitionItems);
        }

        if(exhibitIds != null) {
            Set<Exhibit> exhibits = getExhibits(exhibitIds);
            navPoint.setExhibits(exhibits);
        }

        if(edgeIds != null) {
            Set<NavPoint> edges = getEdges(edgeIds);
            resetEdgesOfNavPoint(navPoint, edges);
        }

        navPointRepository.save(navPoint);
    }

    private static void resetEdgesOfNavPoint(NavPoint navPoint, Set<NavPoint> newEdges) {
        // Remove existing edges
        for (NavPoint edge : navPoint.getEdges()) {
            edge.removeEdge(navPoint);
        }
        navPoint.getEdges().clear();

        // Set the edges and add the new NavPoint to each edge's edges collection
        for (NavPoint edge : newEdges) {
            navPoint.addEdge(edge);
        }
    }

    private Set<NavPoint> getEdges(List<UUID> request) {
        // Find the NavPoint objects related to the ID list from edgeIds and associate them with the NavPoint
        Set<NavPoint> edges = navPointRepository.findAllByIdIn(request);
        if (edges.size() != request.size()) {
            throw new EntityNotFoundException("navPoint");
        }
        return edges;
    }

    private Set<Exhibit> getExhibits(List<UUID> request) {
        // Find the Exhibit objects related to the ID list from the ExhibitIds and associate them with the NavPoint
        Set<Exhibit> exhibits = exhibitRepository.findAllByIdIn(request);
        if (exhibits.size() != request.size()) {
            throw new EntityNotFoundException("exhibit");
        }
        return exhibits;
    }

    private Set<ExhibitionItem> getExhibitionItems(List<UUID> request) {
        // Find the ExhibitionItem objects related to the ID list from the ExhibitionItemIds and associate them with the NavPoint
        Set<ExhibitionItem> exhibitionItems = new HashSet<>(exhibitionItemRepository.findAllByIdIn(request));
        if (exhibitionItems.size() != request.size()) {
            throw new EntityNotFoundException("exhibitionItem");
        }
        return exhibitionItems;
    }

    private Set<Facility> getFacilities(List<UUID> request) {
        // Find the Facility objects related to the ID list from the FacilityIds and associate them with the NavPoint
        Set<Facility> facilities = facilityRepository.findAllByIdIn(request);
        if (facilities.size() != request.size()) {
            throw new EntityNotFoundException("facility");
        }
        return facilities;
    }
}
