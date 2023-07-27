package com.iotiq.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @JsonIgnore
    @JoinColumn(name = "exhibit_id")
    private List<ExhibitionItem> exhibitionItems = new ArrayList<>();

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "exhibits")
    @JsonIgnore
    private Set<NavPoint> navPoints = new HashSet<>();
}
