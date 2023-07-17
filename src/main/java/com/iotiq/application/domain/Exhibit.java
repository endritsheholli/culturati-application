package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Exhibit extends AbstractPersistable<UUID> {
    private String name;
    @OneToMany(mappedBy = "exhibit")
    private List<ExhibitionItem> items;
}
