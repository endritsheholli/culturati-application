package com.iotiq.application.service;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.messages.exhibit.ExhibitRequest;
import com.iotiq.application.repository.ExhibitRepository;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;
    private final ExhibitionItemRepository exhibitionItemRepository;
    private final ExhibitionItemService exhibitionItemService;

    @Transactional
    public void create(ExhibitRequest request) throws Exception {
        Exhibit exhibit = new Exhibit();
        exhibit.setName(request.name());

        // Fetch the ExhibitionItem entities using the provided UUIDs from the request
        List<ExhibitionItem> exhibitionItems = exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds());
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new IllegalArgumentException("One or more ExhibitionItems not found.");
        }

        // Set the ExhibitionItems for the Exhibit
        for (ExhibitionItem item : exhibitionItems) {
            item.setExhibit(exhibit);
        }
        exhibit.setExhibitionItems(exhibitionItems);

        // Save the Exhibit in the database
        exhibitRepository.save(exhibit);
    }

    public List<Exhibit> getAll() {
        return exhibitRepository.findAll();
    }

    public Exhibit getOne(UUID id) {
        return exhibitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("exhibit"));
    }

    @Transactional
    public void update(UUID exhibitId, ExhibitRequest request) throws Exception {
        // Find the existing Exhibit to update
        Optional<Exhibit> optionalExhibit = exhibitRepository.findById(exhibitId);
        if (optionalExhibit.isEmpty()) {
            throw new IllegalArgumentException("Exhibit with ID " + exhibitId + " not found.");
        }
        Exhibit exhibit = optionalExhibit.get();

        // Update the Exhibit name
        exhibit.setName(request.name());

        // Fetch the ExhibitionItem entities using the provided UUIDs from the request
        List<ExhibitionItem> exhibitionItems = exhibitionItemRepository.findAllByIdIn(request.exhibitionItemIds());
        if (exhibitionItems.size() != request.exhibitionItemIds().size()) {
            throw new IllegalArgumentException("One or more ExhibitionItems not found.");
        }

        // Set the updated ExhibitionItems for the Exhibit
        exhibitionItemService.updateWithNullExhibit(exhibitId);
        for (ExhibitionItem item : exhibitionItems) {
            item.setExhibit(exhibit);
        }
        exhibit.setExhibitionItems(exhibitionItems);

        // Save the updated Exhibit in the database
        exhibitRepository.save(exhibit);
    }

    public void delete(UUID id) {
        exhibitionItemService.updateWithNullExhibit(id);
        exhibitRepository.deleteById(id);
    }
}
