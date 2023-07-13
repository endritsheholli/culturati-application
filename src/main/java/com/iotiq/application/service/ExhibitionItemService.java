package com.iotiq.application.service;

import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.repository.ExhibitionItemRepository;
import com.iotiq.application.wiki.WikiClient;
import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.messages.ItemCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.ItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExhibitionItemService {

    private final WikiClient wikiClient;
    private final ExhibitionItemRepository exhibitionItemRepository;

    public Map<Integer, PageDto> getAll() {
        return wikiClient.getPages(new ItemFilter()).stream()
                .collect(Collectors.toMap(PageDto::id, Function.identity()));
    }

    public ExhibitionItem create(ItemRequest request) throws Exception {
        ItemCreateResponse response = wikiClient.createPage(request);

        if (!response.responseResult().succeeded()) {
            throw new Exception(response.responseResult().message());
        }
        PageDto pageDto = response.page();
        ExhibitionItem exhibitionItem = new ExhibitionItem();
        exhibitionItem.setPath(pageDto.path());
        exhibitionItem.setWikiId(String.valueOf(pageDto.id()));
        exhibitionItem.setTitle(pageDto.title());

        return exhibitionItemRepository.save(exhibitionItem);
    }
}
