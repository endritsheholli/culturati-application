package com.iotiq.application.wiki.messages;

import com.iotiq.application.domain.Option;

import java.util.List;
import java.util.UUID;

public record QuestionResponse(UUID id,
                               String title, 
                               String hint, 
                               String questionType, 
                               String pointAnswer,
                               String penalty,
                               String moreInformationUrl,
                               List<Option> options) {
}
