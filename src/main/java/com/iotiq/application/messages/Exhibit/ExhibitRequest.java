package com.iotiq.application.messages.Exhibit;

import com.iotiq.application.domain.ExhibitionItem;

import java.util.List;

public record ExhibitRequest(String name, List<ExhibitionItem> items) {
}
