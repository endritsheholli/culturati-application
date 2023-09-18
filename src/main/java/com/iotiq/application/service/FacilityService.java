package com.iotiq.application.service;

import com.iotiq.application.domain.Facility;
import com.iotiq.application.messages.facility.FacilityCreateRequest;
import com.iotiq.application.messages.facility.FacilityUpdateRequest;
import com.iotiq.application.repository.FacilityRepository;
import com.iotiq.application.service.converter.LocationConverter;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final LocationConverter converter;

    public List<Facility> getAll() {
        return facilityRepository.findAll();
    }

    public Facility getOne(UUID id) {
        return facilityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("facility"));
    }

    @Transactional
    public UUID create(FacilityCreateRequest request) {
        Facility facility = new Facility();
        facility.setOpeningTime(request.openingTime());
        facility.setClosingTime(request.closingTime());
        facility.setLocation(converter.convert(request.location()));
        Facility saved = facilityRepository.save(facility);
        return saved.getId();
    }

    @Transactional
    public void update(UUID id, FacilityUpdateRequest request) {
        Facility facility = getOne(id);
        facility.setOpeningTime(request.openingTime());
        facility.setClosingTime(request.closingTime());
        facility.setLocation(converter.convert(request.location()));
        facilityRepository.save(facility);
    }

    public void delete(UUID id) {
        facilityRepository.deleteById(id);
    }
}
