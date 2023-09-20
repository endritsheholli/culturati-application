package com.iotiq.application.repository;

import com.iotiq.application.domain.Game;
import com.iotiq.application.domain.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findByGameGamersGamerIdAndStatusNot(UUID gamerId, GameStatus gameStatus);
}
