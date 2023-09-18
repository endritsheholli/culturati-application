package com.iotiq.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NavEdge extends AbstractPersistable<UUID> {

    @ManyToOne
    NavPoint startingPoint;
    @ManyToOne
    NavPoint endingPoint;
    Boolean directed;

}
