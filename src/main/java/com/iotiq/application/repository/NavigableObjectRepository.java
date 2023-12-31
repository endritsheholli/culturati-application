package com.iotiq.application.repository;

import com.iotiq.application.domain.NavigableObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NavigableObjectRepository extends JpaRepository<NavigableObject, UUID> {
}
