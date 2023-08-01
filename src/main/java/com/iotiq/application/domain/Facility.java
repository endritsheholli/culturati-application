package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalTime;
import java.util.UUID;
@Entity
@Setter
@Getter
public class Facility extends AbstractPersistable<UUID> {
    private LocalTime openingTime;
    private LocalTime closingTime;
}
