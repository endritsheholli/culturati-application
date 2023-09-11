package com.iotiq.application.wiki;

import com.iotiq.application.domain.QuestionType;
import com.iotiq.application.wiki.domain.QuestionDto;
import com.iotiq.application.wiki.messages.ArtWorkResponse;
import com.iotiq.application.wiki.messages.CategoryResponse;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import com.iotiq.application.wiki.messages.ThemeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WikiDummyDataHolder {
    private static final List<String> usedItems = new ArrayList<>(); // List to keep track of previously used "item" values

    public static String suggestNextItem() {
        if (usedItems.size() >= 5) {
            // If all items have been used, reset the list
            usedItems.clear();
            return "GameOver";
        }

        String newItem;
        Random random = new Random();

        do {
            int randomNumber = random.nextInt(5) + 1;
            newItem = String.valueOf(randomNumber);
        } while (usedItems.contains(newItem));

        usedItems.add(newItem);

        return newItem;
    }

    public static List<CategoryResponse> DUMMY_CATEGORIES =
            List.of(new CategoryResponse("Art"), new CategoryResponse("History"));

    public static List<DifficultyLevelResponse> DUMMY_DIFFICULTY_LEVEL =
            List.of(new DifficultyLevelResponse("child"),
                    new DifficultyLevelResponse("basic"),
                    new DifficultyLevelResponse("intermediate"),
                    new DifficultyLevelResponse("expert"));

    public static List<ThemeResponse> DUMMY_THEMES =
            List.of(new ThemeResponse("Default", "#FFFFFF", "#000000", ""),
                    new ThemeResponse("Dark Mode", "#1E1E1E", "#FFFFFF", ""),
                    new ThemeResponse("Blue Theme", "#4285F4", "#FFFFFF", ""));

    public static List<QuestionDto> DUMMY_QUESTIONS =
            List.of(
                    new QuestionDto("1", "Ankara Castle History 1", "Option 1", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("2", "Ankara Castle History 2", "Option 2", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("3", "Ankara Castle History 3", "Option 3", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("4", "Ankara Castle History 4", "Option 4", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    ),
                    new QuestionDto("5", "Ankara Castle History 5", "Option 5", "Hint for the basic question.", QuestionType.MULTIPLE_CHOICE,
                            "10", "%30", "https://en.wikipedia.org/wiki/Ankara_Castle",
                            List.of("Option 1", "Option 2", "Option 3", "Option 4")
                    )
            );

    public static QuestionDto DUMMY_NEXT_QUESTION(String item) {

        switch (item) {
            case "1":
                return DUMMY_QUESTIONS.get(0);
            case "2":
                return DUMMY_QUESTIONS.get(1);
            case "3":
                return DUMMY_QUESTIONS.get(2);
            case "4":
                return DUMMY_QUESTIONS.get(3);
            case "5":
                return DUMMY_QUESTIONS.get(4);
            default:
                return null;
        }
    }

    public static List<ArtWorkResponse> DUMMY_ARTWORKS =
            List.of(
                    new ArtWorkResponse("Mystic Sunset", "Alicia Williams", "Painting",
                            List.of("landscape", "nature", "impressionism"), "Gallery A", 7),
                    new ArtWorkResponse("Sculpted Elegance", "Michael Rodriguez", "Sculpture",
                            List.of("abstract", "modern", "metal"), "Gallery B", 4),
                    new ArtWorkResponse("Harmony in Chaos", "Sophie Chen", "Mixed Media",
                            List.of("contemporary", "colorful", "experimental"), "Gallery C", 9)
            );
}
