package com.iotiq.application.aimodule.messages;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class NextStopResponse {
    private UUID navPointId;
    private List<UUID> exhibitionItems;
}
