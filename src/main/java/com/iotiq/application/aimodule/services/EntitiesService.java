package com.iotiq.application.aimodule.services;

import com.iotiq.application.config.TenantContext;
import com.iotiq.application.domain.NavEdge;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavEdgeDto;
import com.iotiq.application.messages.navpoint.NavPointDto;
import com.iotiq.application.repository.NavEdgeRepository;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.application.util.TenantUtil;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntitiesService {

    private final TenantUtil tenantUtil;
    private final NavPointRepository navPointRepository;
    private final NavEdgeRepository navEdgeRepository;

    public EntitiesResponse get() {
        String tenantName = TenantContext.getCurrentTenant();
        if (!tenantUtil.existsByName(tenantName)) {
            throw new EntityNotFoundException("tenant", tenantName);
        }

        List<NavPoint> navPoints = navPointRepository.findAll();

        EntitiesResponse entitiesResponse = new EntitiesResponse();
        entitiesResponse.setNavPoints(navPoints.stream().map(NavPointDto::of).toList());

        List<NavEdge> navEdges = navEdgeRepository.findAll();
        entitiesResponse.setEdges(navEdges.stream().map(NavEdgeDto::of).toList());
        return entitiesResponse;
    }

}


