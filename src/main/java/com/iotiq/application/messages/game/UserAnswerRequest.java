package com.iotiq.application.messages.game;

import jakarta.validation.constraints.NotEmpty;

public record UserAnswerRequest(
        @NotEmpty String userAnswer,
        @NotEmpty boolean hintWasUsed) {
}
