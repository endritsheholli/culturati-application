package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class FacilityOrEstablishment extends AbstractPersistable<UUID> {
    private LocalTime openingTime;
    private LocalTime closingTime;

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(mappedBy = "facilities")
    private Set<NavPoint> navPoints = new HashSet<>();
}
