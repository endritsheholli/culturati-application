package com.iotiq.application.repository;

import com.iotiq.application.domain.FacilityOrEstablishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FacilityOrEstablishmentRepository extends JpaRepository<FacilityOrEstablishment, UUID> {
    Set<FacilityOrEstablishment> findAllByIdIn(List<UUID> ids);
}
