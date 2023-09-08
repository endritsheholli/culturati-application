package com.iotiq.application.domain;

public enum QuestionStatus {
    PENDING,        // The question is part of the game but hasn't been answered yet
    ANSWERED,       // The question has been answered
    SKIPPED,        // The question has been skipped
    EXPIRED         // The question has expired due to time limit
}
