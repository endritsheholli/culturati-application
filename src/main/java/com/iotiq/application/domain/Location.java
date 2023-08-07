package com.iotiq.application.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Location {
    private Double latitude;
    private Double longitude;
}
