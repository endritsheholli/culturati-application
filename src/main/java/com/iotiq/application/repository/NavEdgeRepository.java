package com.iotiq.application.repository;

import com.iotiq.application.domain.NavEdge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NavEdgeRepository extends JpaRepository<NavEdge, UUID> {
}
