package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    private Exhibit exhibit;

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(mappedBy = "exhibitionItems")
    private Set<NavPoint> navPoints = new HashSet<>();
}
