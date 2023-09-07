package com.iotiq.application.service;

import com.iotiq.application.domain.*;
import com.iotiq.application.messages.game.CreateGameRequest;
import com.iotiq.application.repository.GameRepository;
import com.iotiq.application.repository.QuestionRepository;
import com.iotiq.application.service.wikiDataService.QuestionService;
import com.iotiq.user.domain.User;
import com.iotiq.user.internal.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final GamerService gamerService;
    private final GamerGameService gamerGameService;
    private final GamerQuestionService gamerQuestionService;

    @Transactional
    public Game createGame(CreateGameRequest request) {
        // Retrieve the currently logged-in user
        User user = userService.getCurrentUser();

        // Get or create a Gamer entity associated with the user
        Gamer gamer = gamerService.getOrCreateGamerForUser(user);

        // Retrieve a list of questions based on the request parameters
        List<Question> questions = questionService.getQuestions(request.category(), request.difficultyLevel(), request.numberOfQuestions());
        questionRepository.saveAll(questions);
        
        // Create a Game entity
        Game game = mapRequestToEntity(request);

        // Create GamerGame for the gamer as CREATOR
        GamerGame gamerGame = gamerGameService.createGamerGame(gamer, game);

        // Establish bidirectional associations
        gamerGameService.associateGameWithGamer(gamer, game, gamerGame);

        // Create GamerQuestion for each question
        List<GamerQuestion> gamerQuestion = gamerQuestionService.createGamerQuestionList(questions);

        // Establish bidirectional associations for questions
        gamerQuestionService.associateQuestionsWithGamer(questions, gamerQuestion, gamerGame);

        // Save the game
        return gameRepository.save(game);
    }

    private Game mapRequestToEntity(CreateGameRequest request) {
        Game game = new Game();
        game.setName(request.name());
        game.setGameType(request.gameType());
        game.setStartTime(new Date());
        game.setStatus(GameStatus.NOT_STARTED);
        // You can uncomment the line below when the audioFileUrl feature is available
        // game.setAudioFileUrl(audioFileUrl());
        return game;
    }
}
