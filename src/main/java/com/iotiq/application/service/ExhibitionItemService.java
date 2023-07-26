package com.iotiq.application.service;

import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.messages.ExhibitionItemCreateRequest;
import com.iotiq.application.messages.ExhibitionItemFilter;
import com.iotiq.application.messages.ExhibitionItemUpdateRequest;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.application.wiki.WikiClient;
import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.exception.CreatePageException;
import com.iotiq.application.wiki.messages.PageCreateRequest;
import com.iotiq.application.wiki.messages.PageCreateResponse;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExhibitionItemService {

    private final ExhibitionItemRepository exhibitionItemRepository;
    private final WikiClient wikiClient;

    public Page<ExhibitionItem> getAll(ExhibitionItemFilter filter, Sort sort) {
        return exhibitionItemRepository.findAll(filter.buildSpecification(), filter.buildPageable(sort));
    }

    public ExhibitionItem getOne(UUID id) {
        return exhibitionItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("exhibitionItem", id.toString()));
    }

    @Transactional
    public void create(ExhibitionItemCreateRequest request) throws Exception {
        PageCreateResponse response = wikiClient.createPage(new PageCreateRequest(request.path(), "", "",request.title() ));

        if (!response.responseResult().succeeded()) {
            throw new CreatePageException();
        }
        PageDto pageDto = response.page();
        ExhibitionItem exhibitionItem = new ExhibitionItem();
        exhibitionItem.setPath(pageDto.path());
        exhibitionItem.setWikiId(String.valueOf(pageDto.id()));
        exhibitionItem.setTitle(pageDto.title());

        exhibitionItemRepository.save(exhibitionItem);
    }

    @Transactional
    public void update(UUID id, ExhibitionItemUpdateRequest request) {
        ExhibitionItem exhibitionItem = getOne(id);

        exhibitionItem.setTitle(request.title());
    }

    public void delete(UUID id) {
        exhibitionItemRepository.deleteById(id);
    }
}
