package com.iotiq.application.domain;

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
public class NavPoint extends AbstractPersistable<UUID> {
    private String mapId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "navpoint_edges",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "edge_id")
    )
    private Set<NavPoint> edges = new HashSet<>();

    @ManyToMany(mappedBy = "edges")
    private Set<NavPoint> edeges = new HashSet<>();

    // Many-to-many relationship with FacilityOrEstablishment entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "navpoint_facility",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<Facility> facilities = new HashSet<>();

    // Many-to-many relationship with ExhibitionItem entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "navpoint_exhibitionItem",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "exhibitionItem_id")
    )
    private Set<ExhibitionItem> exhibitionItems = new HashSet<>();

    // Many-to-many relationship with Exhibit entity
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "navpoint_exhibit",
            joinColumns = @JoinColumn(name = "navpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "exhibit_id")
    )
    private Set<Exhibit> exhibits = new HashSet<>();

    public void addEdge(NavPoint edge) {
        this.edges.add(edge);
        edge.getEdges().add(this);
    }

    public void removeEdge(NavPoint edge) {
        this.edges.remove(edge);
        edge.getEdges().remove(this);
    }

}
