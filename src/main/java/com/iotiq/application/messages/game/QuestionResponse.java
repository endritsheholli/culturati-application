package com.iotiq.application.messages.game;

import com.iotiq.application.domain.Option;

import java.util.List;
import java.util.UUID;

public record QuestionResponse(UUID id,
                               String title, 
                               String hint, 
                               String questionType, 
                               String correctAnswerPoints,
                               String penalty,
                               String moreInformationUrl,
                               List<Option> options) {
}
