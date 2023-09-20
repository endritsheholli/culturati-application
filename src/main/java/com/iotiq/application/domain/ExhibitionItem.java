package com.iotiq.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ExhibitionItem extends NavigableObject {
    private String wikiId;
    private String title;
    private String path;
    @ManyToOne
    private Exhibit exhibit;

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "exhibitionItems")
    @JsonIgnore
    private Set<NavPoint> navPoints = new HashSet<>();
}
