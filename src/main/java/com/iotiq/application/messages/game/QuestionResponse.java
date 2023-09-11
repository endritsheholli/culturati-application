package com.iotiq.application.messages.game;

import com.iotiq.application.domain.QuestionType;
import com.iotiq.application.wiki.domain.QuestionDto;

import java.util.List;

public record QuestionResponse(String id,
                               String title, 
                               String hint,
                               QuestionType questionType, 
                               String correctAnswerPoints,
                               String penalty,
                               String moreInformationUrl,
                               List<String> options) {
    public static QuestionResponse of(QuestionDto question) {
        if (question == null) {
            return null;
        }
        return new QuestionResponse(
                question.id(),
                question.title(),
                question.hint(),
                question.questionType(),
                question.correctAnswerPoints(),
                question.penalty(),
                question.moreInformationUrl(),
                question.options());
    }
}
