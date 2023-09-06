package com.iotiq.application.messages.game;

import com.iotiq.application.wiki.domain.GameType;

public record GameRequest(String name,
                          GameType gameType,
                          String category,
                          String difficultyLevel,
                          int numberOfQuestions) {
}
