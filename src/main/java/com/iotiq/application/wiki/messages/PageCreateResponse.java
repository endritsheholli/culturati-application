package com.iotiq.application.wiki.messages;

import com.iotiq.application.wiki.domain.PageDto;

public record PageCreateResponse(ResponseResult responseResult, PageDto page) {
}
