package com.iotiq.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Facility extends NavigableObject {
    private LocalTime openingTime;
    private LocalTime closingTime;

    // Many-to-many relationship with NavPoint entity
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "facilities")
    @JsonIgnore
    private Set<NavPoint> navPoints = new HashSet<>();
}
