package com.iotiq.application.aimodule.services;

import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.domain.NavigableObject;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class EntitiesResponse {
    private List<NavPoint> navPoints;
    private List<NavigableObject> navigableObjects;
    private Map<UUID, List<UUID>> edges;
}
