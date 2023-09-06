package com.iotiq.application.repository;

import com.iotiq.application.domain.GamerGameDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GamerGameDetailsRepository extends JpaRepository<GamerGameDetails, UUID> {
}
