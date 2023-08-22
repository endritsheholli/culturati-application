package com.iotiq.application.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ExhibitionItem extends AbstractPersistable<UUID> {
    private String wikiId;
    private String title;
    private String path;
    @Embedded
    private Location location;
    @ManyToOne
    private Exhibit exhibit;

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "exhibitionItems")
    private Set<NavPoint> navPoints = new HashSet<>();
}
