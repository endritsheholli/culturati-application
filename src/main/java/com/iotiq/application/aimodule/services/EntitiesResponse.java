package com.iotiq.application.aimodule.services;

import com.iotiq.application.messages.navpoint.NavEdgeDto;
import com.iotiq.application.messages.navpoint.NavPointDto;
import lombok.Data;

import java.util.List;

@Data
public class EntitiesResponse {
    private List<NavPointDto> navPoints;
    private List<NavEdgeDto> edges;
}
