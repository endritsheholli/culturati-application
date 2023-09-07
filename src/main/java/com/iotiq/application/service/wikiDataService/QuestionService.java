package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.domain.Question;
import com.iotiq.application.wiki.WikiDummyDataHolder;
import com.iotiq.application.wiki.domain.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    public List<Question> getQuestions(String categoryName, String difficultyLevel, int numberOfQuestions) {
        List<QuestionDto> questionDtos = WikiDummyDataHolder.questions;
        return convertQuestionDtosToQuestions(questionDtos);
    }

    public List<Question> convertQuestionDtosToQuestions(List<QuestionDto> questionDtos) {
        return questionDtos.stream()
                .map(this::convertQuestionDtoToQuestion)
                .collect(Collectors.toList());
    }

    public Question convertQuestionDtoToQuestion(QuestionDto dto) {
        Question question = new Question();
        question.setTitle(dto.title());
        question.setHint(dto.hint());
        question.setQuestionType(dto.questionType());
        question.setOptions(dto.options());
        question.setRightAnswer(dto.rightAnswer());
        question.setCorrectAnswerPoints(dto.correctAnswerPoints());
        question.setPenalty(dto.penalty());
        question.setMoreInformationUrl(dto.moreInformationUrl());

        return question;
    }
    
}
