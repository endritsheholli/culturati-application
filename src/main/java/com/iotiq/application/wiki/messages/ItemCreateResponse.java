package com.iotiq.application.wiki.messages;

import com.iotiq.application.wiki.domain.ItemDto;

public record ItemCreateResponse(ResponseResult responseResult, ItemDto page) {
}
