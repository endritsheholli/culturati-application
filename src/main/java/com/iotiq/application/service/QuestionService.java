package com.iotiq.application.service;

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
        return WikiDummyDataHolder.DUMMY_QUESTIONS(item);
    }
}
