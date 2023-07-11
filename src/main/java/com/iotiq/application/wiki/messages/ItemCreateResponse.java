package com.iotiq.application.wiki.messages;

import com.iotiq.application.wiki.domain.PageDto;

public record ItemCreateResponse(ResponseResult responseResult, PageDto page) {
}
