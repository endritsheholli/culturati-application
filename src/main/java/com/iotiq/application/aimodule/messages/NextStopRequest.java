package com.iotiq.application.aimodule.messages;

import lombok.Data;

@Data
public class NextStopRequest {
    private String tenantName;
    private String category;
    private String difficultyLevel;
}
