package com.iotiq.application.messages.exhibit;

import com.iotiq.application.domain.ExhibitionItem;

import java.util.List;

public record ExhibitRequest(String name, List<ExhibitionItem> items) {
}
