package com.iotiq.application.repository;

import com.iotiq.application.domain.GameGamer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameGamerRepository extends JpaRepository<GameGamer, UUID> {
    Optional<GameGamer> findByGameIdAndGamerId(UUID gameId, UUID gamerId);
}
