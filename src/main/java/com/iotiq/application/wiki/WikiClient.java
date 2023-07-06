package com.iotiq.application.wiki;

import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.messages.ItemCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.ItemRequest;
import com.iotiq.application.wiki.messages.ResponseResult;

import java.util.Collection;
import java.util.Optional;

public interface WikiClient {

    Collection<PageDto> getPages(ItemFilter filter);

    Optional<PageDto> getPage(Integer id);

    ItemCreateResponse createPage(ItemRequest request);

    ResponseResult deletePage(String id);

}
