package com.iotiq.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Setter
@Getter
public class Facility extends AbstractPersistable<UUID> {
    private LocalTime openingTime;
    private LocalTime closingTime;
    @Embedded
    private Location location;

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "facilities")
    private Set<NavPoint> navPoints = new HashSet<>();
}
