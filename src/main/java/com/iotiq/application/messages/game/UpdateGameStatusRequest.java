package com.iotiq.application.messages.game;

import com.iotiq.application.domain.GameStatus;

public record UpdateGameStatusRequest(GameStatus status) {
}
