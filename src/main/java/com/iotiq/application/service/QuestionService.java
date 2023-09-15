package com.iotiq.application.service;

import com.iotiq.application.messages.game.UserAnswerRequest;
import com.iotiq.application.messages.game.UserAnswerResponse;
import com.iotiq.application.wiki.WikiDummyDataHolder;
import com.iotiq.application.wiki.domain.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    public QuestionDto getNextQuestion() {
        /**
         * This method suggests the next item for retrieving a question.
         * Currently, it is implemented as a static method within the 'WikiDummyDataHolder' class.
         * However, it is subject to change in the future, so it's important to note
         * that the location and implementation of this method might change.
         *
         * @return A suggested item for retrieving a question.
         */
        String item = WikiDummyDataHolder.suggestNextItem();
        return WikiDummyDataHolder.DUMMY_NEXT_QUESTION(item);
    }

    public QuestionDto getQuestionById(String questionId) {
        return WikiDummyDataHolder.DUMMY_GET_QUESTION_BY_ID(questionId);
    }

    public UserAnswerResponse checkAnswer(String questionId, UserAnswerRequest request) {
        // Find the question by questionId
        QuestionDto question = getQuestionById(questionId);

        // Check if the user's answer is correct
        boolean isCorrect = question.rightAnswer().equals(request.userAnswer());

        double score = 0.0;

        if (isCorrect) {
            score = Double.parseDouble(question.correctAnswerPoints());
            if (request.hintWasUsed() && question.penalty() != null) {
                score *= Double.parseDouble(question.penalty());
            }
        }
        return new UserAnswerResponse(isCorrect, String.valueOf(score));
    }

}
