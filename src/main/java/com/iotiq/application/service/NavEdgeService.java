package com.iotiq.application.service;

import com.iotiq.application.domain.NavEdge;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavEdgeDto;
import com.iotiq.application.repository.NavEdgeRepository;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NavEdgeService {

    private final NavEdgeRepository navEdgeRepository;
    private final NavPointRepository navPointRepository;

    public UUID create(NavEdgeDto request) {
        NavPoint startingPoint = navPointRepository.findById(request.startingPoint()).orElseThrow(()-> new EntityNotFoundException("NavPoint"));
        NavPoint endingPoint = navPointRepository.findById(request.endingPoint()).orElseThrow(()-> new EntityNotFoundException("NavPoint"));
        Boolean directed = Objects.requireNonNullElse(request.directed(), Boolean.FALSE);

        NavEdge edge = new NavEdge(startingPoint, endingPoint, directed);
        NavEdge saved = navEdgeRepository.save(edge);

        return saved.getId();
    }
}
