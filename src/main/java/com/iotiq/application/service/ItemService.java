package com.iotiq.application.service;

import com.iotiq.application.domain.Item;
import com.iotiq.application.exception.EntityNotFoundException;
import com.iotiq.application.repository.ItemRepository;
import com.iotiq.application.wiki.WikiClient;
import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.messages.ItemCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.ItemRequest;
import com.iotiq.application.wiki.messages.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final WikiClient wikiClient;
    private final ItemRepository itemRepository;

    public Map<UUID, PageDto> getAll() {
        Map<Integer, UUID> wikiIdToItemId = itemRepository.findAll().stream()
                .collect(Collectors.toMap(item -> Integer.parseInt(item.getWikiId()), Item::getId));

        BiConsumer<HashMap<UUID, PageDto>, PageDto> hashMapPageDtoBiConsumer = (map, pageDto) -> {
            UUID uuid = wikiIdToItemId.get(pageDto.getId());
            if (uuid != null) {
                pageDto.setPath("http://localhost/en/" + pageDto.getPath());
                map.put(uuid, pageDto);
            }
        };
        return wikiClient.getPages(new ItemFilter()).stream()
                .collect(HashMap::new, hashMapPageDtoBiConsumer, Map::putAll);
    }

    public Item create(ItemRequest request) throws Exception {
        ItemCreateResponse response = wikiClient.createPage(request);

        if (!response.responseResult().succeeded()) {
            throw new Exception(response.responseResult().message());
        }
        PageDto pageDto = response.page();
        Item item = new Item();
        item.setPath(pageDto.path());
        item.setWikiId(String.valueOf(pageDto.id()));
        item.setTitle(pageDto.title());

        return itemRepository.save(item);
    }

    public void delete(UUID id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("item"));

        ResponseResult responseResult = wikiClient.deletePage(item.getWikiId());
        if (responseResult.isSucceeded()) {
            itemRepository.deleteById(id);
        }
    }
}
