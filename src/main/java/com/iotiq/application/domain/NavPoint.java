package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Setter
@Getter
public class NavPoint extends AbstractPersistable<UUID> {
    private String mapId;

    // Many-to-many relationship with itself (NavPoint)
    @ManyToMany
    @JoinTable(
            name = "navpoint_navpoint",
            joinColumns = @JoinColumn(name = "parent_navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "child_navpoint_id")
    )
    private Set<NavPoint> children = new HashSet<>();

    @ManyToMany(mappedBy = "children")
    private Set<NavPoint> parents = new HashSet<>();

    // Many-to-many relationship with FacilityOrEstablishment entity
    @ManyToMany
    @JoinTable(
            name = "navpoint_facility",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<FacilityOrEstablishment> facilities = new HashSet<>();
    
    // Many-to-many relationship with ExhibitionItem entity
    @ManyToMany
    @JoinTable(
            name = "navpoint_exhibitionItem",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "exhibitionItem_id")
    )
    private Set<ExhibitionItem> exhibitionItems = new HashSet<>();
    
    // Many-to-many relationship with Exhibit entity
    @ManyToMany
    @JoinTable(
            name = "navpoint_exhibit",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "exhibit_id")
    )
    private Set<Exhibit> exhibits = new HashSet<>();
    
}
