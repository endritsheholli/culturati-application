package com.iotiq.application.repository;

import com.iotiq.application.domain.ExhibitionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExhibitionItemRepository extends JpaRepository<ExhibitionItem, UUID> {
}
