package com.iotiq.application.wiki;

import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.messages.PageCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.PageCreateRequest;
import com.iotiq.application.wiki.messages.ResponseResult;

import java.util.Collection;
import java.util.Optional;

public interface WikiClient {

    Collection<PageDto> getPages(ItemFilter filter);

    Optional<PageDto> getPage(Integer id);

    PageCreateResponse createPage(PageCreateRequest request);

    ResponseResult deletePage(String id);

}
