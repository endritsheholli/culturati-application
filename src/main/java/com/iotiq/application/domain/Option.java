package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;
@Entity
@Setter
@Getter
public class Option extends AbstractPersistable<UUID> {
    private String text;
    @ManyToOne
    private Question question;
}
