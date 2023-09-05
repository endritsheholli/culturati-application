package com.iotiq.application.wiki;

import com.iotiq.application.domain.QuestionType;
import com.iotiq.application.wiki.domain.QuestionDto;
import com.iotiq.application.wiki.messages.ArtWorkResponse;
import com.iotiq.application.wiki.messages.CategoryResponse;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import com.iotiq.application.wiki.messages.ThemeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WikiDummyDataHolder {
    public static List<CategoryResponse> categories =
            List.of(new CategoryResponse("Art"), new CategoryResponse("History"));

    public static List<DifficultyLevelResponse> difficultyLevels =
            List.of(new DifficultyLevelResponse("child"),
                    new DifficultyLevelResponse("basic"),
                    new DifficultyLevelResponse("intermediate"),
                    new DifficultyLevelResponse("expert"));

    public static List<ThemeResponse> themes =
            List.of(new ThemeResponse("Default", "#FFFFFF", "#000000", ""),
                    new ThemeResponse("Dark Mode", "#1E1E1E", "#FFFFFF", ""),
                    new ThemeResponse("Blue Theme", "#4285F4", "#FFFFFF", ""));

    public static List<QuestionDto> questions =
            List.of(
                    new QuestionDto("Ankara Castle History 1", "Option 1", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("Ankara Castle History 2", "Option 2", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("Ankara Castle History 3", "Option 3", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("Ankara Castle History 4", "Option 4", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("Ankara Castle History 5", "Option 5", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    )
            );
    public static List<ArtWorkResponse> artWorks =
            List.of(
                    new ArtWorkResponse("Mystic Sunset", "Alicia Williams", "Painting",
                            List.of("landscape", "nature", "impressionism"), "Gallery A", 7),
                    new ArtWorkResponse("Sculpted Elegance", "Michael Rodriguez", "Sculpture",
                            List.of("abstract", "modern", "metal"), "Gallery B", 4),
                    new ArtWorkResponse("Harmony in Chaos", "Sophie Chen", "Mixed Media",
                            List.of("contemporary", "colorful", "experimental"), "Gallery C", 9)
            );
}
