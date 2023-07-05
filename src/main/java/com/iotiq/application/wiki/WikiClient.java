package com.iotiq.application.wiki;

import com.iotiq.application.wiki.domain.ItemDto;
import com.iotiq.application.wiki.messages.ItemCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.ItemRequest;
import com.iotiq.application.wiki.messages.ResponseResult;

import java.util.Collection;
import java.util.Optional;

public interface WikiClient {

    Collection<ItemDto> getItems(ItemFilter filter);

    Optional<ItemDto> getItem(Integer id);

    ItemCreateResponse createItem(ItemRequest request);

    ResponseResult deleteItem(String id);

}
