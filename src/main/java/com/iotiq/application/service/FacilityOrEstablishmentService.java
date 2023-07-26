package com.iotiq.application.service;

import com.iotiq.application.domain.FacilityOrEstablishment;
import com.iotiq.application.messages.facility.FacilityRequest;
import com.iotiq.application.repository.FacilityOrEstablishmentRepository;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import com.iotiq.commons.exceptions.RequiredFieldMissingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacilityOrEstablishmentService {
    private final FacilityOrEstablishmentRepository facilityRepository;

    public List<FacilityOrEstablishment> getAll() {
        return facilityRepository.findAll();
    }

    public FacilityOrEstablishment getOne(UUID id) {
        return facilityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("facility"));
    }

    @Transactional
    public void create(FacilityRequest request) {
        FacilityOrEstablishment facility = new FacilityOrEstablishment();
        facility.setOpeningTime(request.openingTime());
        facility.setClosingTime(request.closingTime());
        facilityRepository.save(facility);
    }

    @Transactional
    public void update(UUID id, FacilityRequest request) {
        FacilityOrEstablishment facility = getOne(id);
        facility.setOpeningTime(request.openingTime());
        facility.setClosingTime(request.closingTime());
        facilityRepository.save(facility);
    }

    public void delete(UUID id) {
        getOne(id);
        facilityRepository.deleteById(id);
    }
}
