package com.iotiq.application.repository;

import com.iotiq.application.domain.GamerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GamerQuestionRepository extends JpaRepository<GamerQuestion, UUID> {
}
