package com.iotiq.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@Entity
@Getter
@Setter
public class GamerQuestion extends AbstractPersistable<UUID> {
    private String questionId;
    private Boolean isCorrect;
    private int score;
}
