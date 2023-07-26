package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.*;

@Entity
@Setter
@Getter
public class Exhibit extends AbstractPersistable<UUID> {
    private String name;
    @OneToMany
    @JoinColumn(name = "exhibit_id")
    private List<ExhibitionItem> exhibitionItems = new ArrayList<>();

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(mappedBy = "exhibits")
    private Set<NavPoint> navPoints = new HashSet<>();
}
