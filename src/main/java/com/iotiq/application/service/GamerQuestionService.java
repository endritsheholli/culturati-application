package com.iotiq.application.service;

import com.iotiq.application.domain.GamerQuestion;
import com.iotiq.application.wiki.domain.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamerQuestionService {

    public GamerQuestion createGamerQuestion(QuestionDto question) {
        if (question == null){
            return null;
        }
        GamerQuestion gamerQuestion = new GamerQuestion();
        gamerQuestion.setQuestionId(question.id());

        return gamerQuestion;
    }
}
