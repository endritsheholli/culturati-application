package com.iotiq.application.wiki.messages;

public record ResponseResult(boolean succeeded, int errorCode, String slug, String message) {
}
