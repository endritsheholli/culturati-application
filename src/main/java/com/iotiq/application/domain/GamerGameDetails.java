package com.iotiq.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class GamerGameDetails extends AbstractPersistable<UUID> {
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamer;
    @Enumerated(EnumType.STRING)
    private GamerRole gamerRole;
    private int totalScore;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gamer_game_id")
    private Set<GamerQuestionDetails> gamerQuestionDetails = new HashSet<>();
}
