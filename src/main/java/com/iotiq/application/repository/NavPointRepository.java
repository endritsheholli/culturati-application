package com.iotiq.application.repository;

import com.iotiq.application.domain.NavPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NavPointRepository extends JpaRepository<NavPoint, UUID> {
    Set<NavPoint> findAllByIdIn(List<UUID> ids);
}
