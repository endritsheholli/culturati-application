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

        // TODO: Check Eligibility Before Creating a Game issue #82
        
        // Get or create a Gamer entity associated with the user
        Gamer gamer = gamerService.getOrCreateGamerForUser(user);

        // Retrieve a list of questions based on the request parameters
        List<Question> questions = questionService.getQuestions(request.category(), request.difficultyLevel(), request.numberOfQuestions());
        questionRepository.saveAll(questions);
        
        // Create a Game entity
        Game game = mapRequestToEntity(request);

        // Create GamerGame for the gamer as CREATOR and establish bidirectional associations
        GamerGame gamerGame = gamerGameService.createGamerGameAndAssociateWithGameAndGamer(gamer, game);

        // Create GamerQuestion for each question and establish bidirectional associations
        List<GamerQuestion> gamerQuestion = gamerQuestionService.createGamerQuestionListAndAssociateWithQuestions(questions, gamerGame);

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
