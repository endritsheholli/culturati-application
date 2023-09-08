package com.iotiq.application.service;

import com.iotiq.application.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GamerQuestionService {

    public List<GamerQuestion> createGamerQuestionList(List<Question> questions) {
        List<GamerQuestion> gamerQuestion = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            GamerQuestion detail = new GamerQuestion();
            detail.setStatus(QuestionStatus.CREATED);
            gamerQuestion.add(detail);
        }
        return gamerQuestion;
    }

    public void associateQuestionsWithGamer(List<Question> questions, List<GamerQuestion> gamerQuestion, GamerGame gamerGame) {
        for (int i = 0; i < questions.size(); i++) {
            gamerQuestion.get(i).setStatus(QuestionStatus.CREATED);
            questions.get(i).getGamerQuestion().add(gamerQuestion.get(i));
            gamerGame.getGamerQuestion().add(gamerQuestion.get(i));
        }
    }
    public List<GamerQuestion> createGamerQuestionListAndAssociateWithQuestions(List<Question> questions, GamerGame gamerGame) {
        List<GamerQuestion> gamerQuestions = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            GamerQuestion gamerQuestion = new GamerQuestion();
            gamerQuestion.setStatus(QuestionStatus.CREATED);
            gamerQuestions.add(gamerQuestion);

            // Establish bidirectional associations
            questions.get(i).getGamerQuestion().add(gamerQuestion);
            gamerGame.getGamerQuestion().add(gamerQuestion);
        }

        return gamerQuestions;
    }
}
