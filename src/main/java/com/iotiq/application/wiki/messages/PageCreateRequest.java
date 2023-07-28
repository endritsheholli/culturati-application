package com.iotiq.application.wiki.messages;

import jakarta.validation.constraints.NotEmpty;

public record PageCreateRequest(@NotEmpty String path, @NotEmpty String content, @NotEmpty String description, @NotEmpty String title ) {
}
