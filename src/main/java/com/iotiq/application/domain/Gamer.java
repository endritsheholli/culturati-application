package com.iotiq.application.domain;

import com.iotiq.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Setter
@Getter
public class Gamer extends AbstractPersistable<UUID> {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gamer_id")
    private Set<GamerGame> gamerGame = new HashSet<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    
    // Badges and Trophies can be added here
}
