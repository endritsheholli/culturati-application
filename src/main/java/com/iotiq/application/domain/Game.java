package com.iotiq.application.domain;

import com.iotiq.application.wiki.domain.GameType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.*;

@Entity
@Setter
@Getter
public class Game extends AbstractPersistable<UUID> {
    private String name;
    @Enumerated(EnumType.STRING)
    private GameType gameType;
    private String audioFileUrl;
    private Date startTime;
    private boolean isPaused;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Set<GamerGameDetails> gamerGameDetails = new HashSet<>();
}
