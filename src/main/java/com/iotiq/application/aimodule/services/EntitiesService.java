package com.iotiq.application.aimodule.services;

import com.iotiq.application.config.TenantContext;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.domain.NavigableObject;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.application.repository.NavigableEntityRepository;
import com.iotiq.application.util.TenantUtil;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntitiesService {

    private final TenantUtil tenantUtil;
    private final NavigableEntityRepository repository;
    private final NavPointRepository navPointRepository;

    public EntitiesResponse get() {
        String tenantName = TenantContext.getCurrentTenant();
        if (!tenantUtil.existsByName(tenantName)) {
            throw new EntityNotFoundException("tenant", tenantName);
        }

        List<NavPoint> navPoints = navPointRepository.findAll();
        List<NavigableObject> navigableObjects = repository.findAll();

        EntitiesResponse entitiesResponse = new EntitiesResponse();
        entitiesResponse.setNavPoints(navPoints);
        entitiesResponse.setNavigableObjects(navigableObjects);

        Map<UUID, List<UUID>> collect = navPoints.stream()
                .collect(Collectors.toMap(NavPoint::getId, navPoint -> {
                    ArrayList<UUID> objects = new ArrayList<>();
                    objects.addAll(collectIds(navPoint.getEdges()));
                    objects.addAll(collectIds(navPoint.getExhibits()));
                    objects.addAll(collectIds(navPoint.getExhibitionItems()));
                    objects.addAll(collectIds(navPoint.getFacilities()));
                    return objects;
                }));

        entitiesResponse.setEdges(collect);
        return entitiesResponse;
    }

    private List<UUID> collectIds(Collection<? extends AbstractPersistable<UUID>> objects) {
        return objects.stream().map(AbstractPersistable::getId).toList();
    }
}


