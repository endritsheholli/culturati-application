package com.iotiq.application.repository;

import com.iotiq.application.domain.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ExhibitRepository extends JpaRepository<Exhibit, UUID> , JpaSpecificationExecutor<Exhibit> {
    Set<Exhibit> findAllByIdIn(List<UUID> ids);
}
