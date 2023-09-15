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
public class GameGamer extends AbstractPersistable<UUID> {
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamer;
    @Enumerated(EnumType.STRING)
    private GamerRole gamerRole;
    private String totalScore;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_gamer_id")
    private Set<GamerQuestion> gamerQuestion = new HashSet<>();
}
