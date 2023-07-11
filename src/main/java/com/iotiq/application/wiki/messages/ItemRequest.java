package com.iotiq.application.wiki.messages;


import jakarta.validation.constraints.NotEmpty;

public record ItemRequest(@NotEmpty String path) {
}
