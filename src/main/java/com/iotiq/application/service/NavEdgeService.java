package com.iotiq.application.service;

import com.iotiq.application.domain.NavEdge;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavEdgeDto;
import com.iotiq.application.repository.NavEdgeRepository;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.commons.exceptions.EntityAlreadyExistsException;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import com.iotiq.commons.exceptions.InvalidInputException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NavEdgeService {

    private final NavEdgeRepository navEdgeRepository;
    private final NavPointRepository navPointRepository;

    public UUID create(@Valid NavEdgeDto request) {
        UUID startingPointId = request.startingPoint();
        UUID endingPointId = request.endingPoint();
        Boolean directed = Objects.requireNonNullElse(request.directed(), Boolean.FALSE);

        if(startingPointId == endingPointId)
            throw new InvalidInputException("Starting and ending points should not be the same.");

        if(edgeAlreadyExists(startingPointId, endingPointId, directed))
            throw new EntityAlreadyExistsException("navEdge");

        NavPoint startingPoint = navPointRepository.findById(startingPointId).orElseThrow(()-> new EntityNotFoundException("NavPoint"));
        NavPoint endingPoint = navPointRepository.findById(endingPointId).orElseThrow(()-> new EntityNotFoundException("NavPoint"));

        NavEdge edge = new NavEdge(startingPoint, endingPoint, directed);
        NavEdge saved = navEdgeRepository.save(edge);

        return saved.getId();
    }

    private boolean edgeAlreadyExists(UUID startingPointId, UUID endingPointId, Boolean directed) {
        if(Boolean.TRUE.equals(directed)){
            if(navEdgeRepository.existsByStartingPointIdAndEndingPointIdAndDirectedIsTrue(startingPointId, endingPointId)
                    || navEdgeRepository.existsByStartingPointIdAndEndingPointIdAndDirectedIsFalse(startingPointId, endingPointId)
                    || navEdgeRepository.existsByStartingPointIdAndEndingPointIdAndDirectedIsFalse(endingPointId, startingPointId))
                return true;
        }
        else {
            if(navEdgeRepository.existsByStartingPointIdAndEndingPointId(startingPointId, endingPointId)
                    ||navEdgeRepository.existsByStartingPointIdAndEndingPointId(endingPointId, startingPointId))
                return true;
        }
        return false;
    }

}
