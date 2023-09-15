package com.iotiq.application.messages.game;

public record UserAnswerResponse(boolean isCorrect, String totalScore) {
}
