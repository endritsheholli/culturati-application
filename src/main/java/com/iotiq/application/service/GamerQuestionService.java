package com.iotiq.application.service;

import com.iotiq.application.domain.GameGamer;
import com.iotiq.application.domain.GamerQuestion;
import com.iotiq.application.exception.GameException;
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
    public GamerQuestion findGamerQuestion(GameGamer gameGamer, String questionId) {
        return gameGamer.getGamerQuestion()
                .stream()
                .filter(gq -> gq.getQuestionId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new GameException("questionNotAvailable"));
    }
}
