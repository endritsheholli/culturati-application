package com.iotiq.application.domain;

public enum QuestionStatus {
    CREATED,        // The question is created but not yet part of the game
    PENDING,        // The question is part of the game but hasn't been answered yet
    ANSWERED,       // The question has been answered
    SKIPPED,        // The question has been skipped
    EXPIRED         // The question has expired due to time limit
}
