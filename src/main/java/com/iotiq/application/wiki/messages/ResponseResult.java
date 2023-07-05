package com.iotiq.application.wiki.messages;

import lombok.Data;

@Data
public class ResponseResult {
    boolean succeeded;
    int errorCode;
    String slug;
    String message;
}
