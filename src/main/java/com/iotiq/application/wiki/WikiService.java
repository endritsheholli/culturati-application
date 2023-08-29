package com.iotiq.application.wiki;

import com.iotiq.application.domain.QuestionType;
import com.iotiq.application.wiki.domain.QuestionDto;
import com.iotiq.application.wiki.messages.CategoryResponse;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import com.iotiq.application.wiki.messages.ThemeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WikiService {
    public List<CategoryResponse> getCategories() {
        return List.of(new CategoryResponse("Art"), new CategoryResponse("History"));
    }

    public List<DifficultyLevelResponse> getDifficultyLevels() {
        return List.of(new DifficultyLevelResponse("child"),
                new DifficultyLevelResponse("basic"),
                new DifficultyLevelResponse("intermediate"),
                new DifficultyLevelResponse("expert"));
    }

    public List<ThemeResponse> getThemes() {
        return List.of(new ThemeResponse("Default", "#FFFFFF", "#000000", ""),
                new ThemeResponse("Dark Mode", "#1E1E1E", "#FFFFFF", ""),
                new ThemeResponse("Blue Theme", "#4285F4", "#FFFFFF", ""));
    }

    public List<QuestionDto> getQuestions(String category, String difficultyLevel, int numberOfQuestions) {
        return List.of(
                new QuestionDto("Ankara Castle History 1", "Option 1","Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                        "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                        List.of("Option 1", "Option 2", "Option 3", "Option 4")
                ),
                new QuestionDto("Ankara Castle History 2", "Option 2","Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                        "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                        List.of("Option 1", "Option 2", "Option 3", "Option 4")
                ),
                new QuestionDto("Ankara Castle History 3", "Option 3","Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                        "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                        List.of("Option 1", "Option 2", "Option 3", "Option 4")
                ),
                new QuestionDto("Ankara Castle History 4", "Option 4","Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                        "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                        List.of("Option 1", "Option 2", "Option 3", "Option 4")
                ),
                new QuestionDto("Ankara Castle History 5", "Option 5","Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                        "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                        List.of("Option 1", "Option 2", "Option 3", "Option 4")
                )
        );
    }
}
