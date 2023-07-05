package com.iotiq.application.service;

import com.iotiq.application.domain.Item;
import com.iotiq.application.repository.ItemRepository;
import com.iotiq.application.wiki.WikiClient;
import com.iotiq.application.wiki.domain.ItemDto;
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

    public Map<Integer, ItemDto> getAll() {
        return wikiClient.getItems(new ItemFilter()).stream()
                .collect(Collectors.toMap(ItemDto::getId, Function.identity()));
    }

    public Item create(ItemRequest request) throws Exception {
        ItemCreateResponse response = wikiClient.createItem(request);
        System.out.println(response);

        if (!response.responseResult().isSucceeded()) {
            throw new Exception("Could not create item in wiki");
        }
        ItemDto itemDto = response.page();
        Item entity = new Item();
        entity.setPath(itemDto.getPath());
        entity.setWikiId(String.valueOf(itemDto.getId()));
        entity.setTitle(itemDto.getTitle());

        return itemRepository.save(entity);
    }
}
