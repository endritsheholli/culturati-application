package com.iotiq.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Exhibit extends NavigableObject {
    private String name;
    @OneToMany
    @JoinColumn(name = "exhibit_id")
    @JsonIgnore
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
