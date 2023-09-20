package com.iotiq.application.repository;

import com.iotiq.application.domain.NavEdge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NavEdgeRepository extends JpaRepository<NavEdge, UUID> {
    boolean existsByStartingPointIdAndEndingPointIdAndDirectedIsTrue(UUID startingPointId, UUID endingPointId);

    boolean existsByStartingPointIdAndEndingPointIdAndDirectedIsFalse(UUID startingPointId, UUID endingPointId);

    boolean existsByStartingPointIdAndEndingPointId(UUID startingPointId, UUID endingPointId);

    void deleteAllByStartingPointIdInAndEndingPointIdInAndIdIsNot(List<UUID> startingPointIds, List<UUID> endingPointIds, UUID id);
}
