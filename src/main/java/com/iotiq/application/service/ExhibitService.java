package com.iotiq.application.service;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.messages.exhibit.ExhibitFilter;
import com.iotiq.application.messages.exhibit.ExhibitCreateRequest;
import com.iotiq.application.messages.exhibit.ExhibitUpdateRequest;
import com.iotiq.application.repository.ExhibitRepository;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.application.service.converter.LocationConverter;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;
    private final ExhibitionItemRepository exhibitionItemRepository;
    private final LocationConverter converter;

    @Transactional
    public UUID create(ExhibitCreateRequest request) {
        List<ExhibitionItem> exhibitionItems = exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds());
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new EntityNotFoundException("One or more ExhibitionItems not found.");
        }
        
        Exhibit exhibit = new Exhibit();
        exhibit.setName(request.name());
        exhibit.setExhibitionItems(exhibitionItems);
        exhibit.setLocation(converter.convert(request.location()));

        Exhibit saved = exhibitRepository.save(exhibit);
        return saved.getId();
    }

    public Page<Exhibit> getAll(ExhibitFilter filter, Sort sort) {
        return exhibitRepository.findAll(filter.buildSpecification(), filter.buildPageable(sort));
    }

    public Exhibit getOne(UUID id) {
        return exhibitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("exhibit"));
    }

    @Transactional
    public void update(UUID exhibitId, ExhibitUpdateRequest request) {
        List<ExhibitionItem> exhibitionItems = exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds());
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new EntityNotFoundException("One or more ExhibitionItems not found.");
        }

        Exhibit exhibit = getOne(exhibitId);
        exhibit.setName(request.name());
        exhibit.setLocation(converter.convert(request.location()));
        
        // Set the updated ExhibitionItems for the Exhibit
        for (ExhibitionItem item : exhibitionItems) {
            item.setExhibit(exhibit);
        }
        // clear and add the exhibitionItems list to the Exhibit, if you prefer to refresh the association:
        exhibit.getExhibitionItems().clear();
        exhibit.getExhibitionItems().addAll(exhibitionItems);
        
        exhibitRepository.save(exhibit);
    }

    public void delete(UUID id) {
        exhibitRepository.deleteById(id);
    }
}
