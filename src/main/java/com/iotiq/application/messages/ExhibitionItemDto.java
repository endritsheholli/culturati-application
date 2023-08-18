package com.iotiq.application.messages;

import com.iotiq.application.domain.ExhibitionItem;

import java.util.UUID;

public record ExhibitionItemDto(UUID id, String wikiId, String path, String title) {
    public static ExhibitionItemDto of(ExhibitionItem exhibitionItem) {
        return new ExhibitionItemDto(exhibitionItem.getId(), exhibitionItem.getWikiId(), exhibitionItem.getPath(),
                exhibitionItem.getTitle());
    }
}