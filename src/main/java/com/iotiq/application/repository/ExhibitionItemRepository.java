package com.iotiq.application.repository;

import com.iotiq.application.domain.ExhibitionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ExhibitionItemRepository extends JpaRepository<ExhibitionItem, UUID>, JpaSpecificationExecutor<ExhibitionItem> {
    List<ExhibitionItem> findAllByIdIn(List<UUID> ids);
    List<ExhibitionItem> findByExhibitId(UUID exhibitId);

}
