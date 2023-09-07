package com.iotiq.application.repository;

import com.iotiq.application.domain.GamerGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GamerGameRepository extends JpaRepository<GamerGame, UUID> {
}
