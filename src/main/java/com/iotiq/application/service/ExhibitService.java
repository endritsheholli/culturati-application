package com.iotiq.application.service;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.messages.Exhibit.ExhibitRequest;
import com.iotiq.application.repository.ExhibitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;

    public void create(ExhibitRequest request) {
        Exhibit exhibit = new Exhibit();
        exhibit.setName(request.name());
        exhibit.setItems(request.items());
        exhibitRepository.save(exhibit);
    }

    public List<Exhibit> getAll() {
        return exhibitRepository.findAll();
    }
    
}
