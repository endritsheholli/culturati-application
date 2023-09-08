package com.iotiq.application.service;

import com.iotiq.application.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GamerQuestionService {
    
    public List<GamerQuestion> createGamerQuestionListAndAssociateWithQuestions(List<Question> questions, GamerGame gamerGame) {
        List<GamerQuestion> gamerQuestions = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            GamerQuestion gamerQuestion = new GamerQuestion();
            gamerQuestion.setStatus(QuestionStatus.PENDING);
            gamerQuestions.add(gamerQuestion);

            // Establish bidirectional associations
            questions.get(i).getGamerQuestion().add(gamerQuestion);
            gamerGame.getGamerQuestion().add(gamerQuestion);
        }

        return gamerQuestions;
    }
}
