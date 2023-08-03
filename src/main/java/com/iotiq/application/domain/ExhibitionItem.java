package com.iotiq.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@Entity
@Getter
@Setter
public class ExhibitionItem extends AbstractPersistable<UUID> {
    private String wikiId;
    private String title;
    private String path;

    private double latitude;
    private double longitude;
    @ManyToOne
    private Exhibit exhibit;
}
