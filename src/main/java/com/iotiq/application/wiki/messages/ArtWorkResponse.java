package com.iotiq.application.wiki.messages;

import java.util.List;

public record ArtWorkResponse(
        String name,
        String artist,
        String category,
        List<String> tags,
        String locationName,
        int popularity) {
}
