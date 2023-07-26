package com.iotiq.application.repository;

import com.iotiq.application.domain.NavPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NavPointRepository extends JpaRepository<NavPoint, UUID> {
}
