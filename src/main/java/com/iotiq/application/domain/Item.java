package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Item extends AbstractPersistable<UUID> {
    private String wikiId;
    private String title;
    private String path;
}
