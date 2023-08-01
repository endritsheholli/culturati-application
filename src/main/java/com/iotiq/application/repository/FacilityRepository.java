package com.iotiq.application.repository;

import com.iotiq.application.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacilityRepository extends JpaRepository<Facility, UUID> {
}
