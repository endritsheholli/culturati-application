package com.iotiq.application.service;

import com.iotiq.application.domain.Item;
import com.iotiq.application.repository.ItemRepository;
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
public class ItemService {

    private final WikiClient wikiClient;
    private final ItemRepository itemRepository;

    public Map<Integer, PageDto> getAll() {
        return wikiClient.getPages(new ItemFilter()).stream()
                .collect(Collectors.toMap(PageDto::getId, Function.identity()));
    }

    public Item create(ItemRequest request) throws Exception {
        ItemCreateResponse response = wikiClient.createPage(request);

        if (!response.responseResult().isSucceeded()) {
            throw new Exception(response.responseResult().getMessage());
        }
        PageDto pageDto = response.page();
        Item item = new Item();
        item.setPath(pageDto.getPath());
        item.setWikiId(String.valueOf(pageDto.getId()));
        item.setTitle(pageDto.getTitle());

        return itemRepository.save(item);
    }
}
