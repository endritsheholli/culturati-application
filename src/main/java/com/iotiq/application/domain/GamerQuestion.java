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
    @ManyToOne
    @JoinColumn(name = "gamer_game_id")
    private GamerGame gamerGame;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    private Boolean isCorrect;
    private int score;
}
