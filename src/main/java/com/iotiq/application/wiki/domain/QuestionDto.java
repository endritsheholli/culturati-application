package com.iotiq.application.wiki.domain;

import com.iotiq.application.domain.QuestionType;

import java.util.List;

public record QuestionDto(String title,
                          String rightAnswer,
                          String hint,
                          QuestionType questionType,
                          String correctAnswerPoints,
                          String penalty,
                          String moreInformationUrl,
                          List<String> options) {
}
