package com.iotiq.application.repository;

import com.iotiq.application.domain.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GamerRepository extends JpaRepository<Gamer, UUID> {
}
