package com.iotiq.application.service;

import com.iotiq.application.domain.*;
import com.iotiq.application.exception.GameException;
import com.iotiq.application.messages.game.*;
import com.iotiq.application.repository.GameRepository;
import com.iotiq.application.wiki.domain.QuestionDto;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import com.iotiq.user.domain.User;
import com.iotiq.user.internal.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final GamerService gamerService;
    private final GameGamerService gameGamerService;
    private final QuestionService questionService;
    private final GamerQuestionService gamerQuestionService;

    @Transactional
    public Game createGame(CreateGameRequest request) {
        // Retrieve the currently logged-in user
        User user = userService.getCurrentUser();

        // TODO: Check Eligibility Before Creating a Game issue #82

        // Get or create a Gamer entity associated with the user
        Gamer gamer = gamerService.getOrCreateGamerForUser(user);

        // Create a Game entity
        Game game = mapRequestToEntity(request);

        // Create GameGamer for the gamer as CREATOR
        gameGamerService.createGameGamer(gamer, game);

        // Save the game
        return gameRepository.save(game);
    }

    private Game mapRequestToEntity(CreateGameRequest request) {
        Game game = new Game();
        game.setName(request.name());
        game.setGameType(request.gameType());
        game.setDifficultyLevel(request.difficultyLevel());
        game.setStartTime(new Date());
        game.setStatus(GameStatus.NOT_STARTED);
        // You can uncomment the line below when the audioFileUrl feature is available
        // game.setAudioFileUrl(audioFileUrl());
        return game;
    }

    public void updateGameStatus(UUID id, UpdateGameStatusRequest request) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("game"));

        // TODO: Calculate game time if status transitions from PAUSED to IN_PROGRESS.
        game.setStatus(request.status());
        gameRepository.save(game);
    }

    @Transactional
    public QuestionResponse getNextQuestion(UUID gameId) {
        // Retrieve the currently logged-in user
        User user = userService.getCurrentUser();
        // Get or create a Gamer entity associated with the user
        Gamer gamer = gamerService.getOrCreateGamerForUser(user);

        // Validation checks
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("game"));
        validateGameInProgress(game);

        // Get the next question
        QuestionDto questionDto = questionService.getNextQuestion();
        
        // If there are no more questions, end the game
        if (questionDto == null) {
            game.setStatus(GameStatus.ENDED);
            return null;
        }

        // Create GamerQuestion
        GamerQuestion gamerQuestion = gamerQuestionService.createGamerQuestion(questionDto);

        // Update GameGamer with GamerQuestion
        GameGamer gameGamer = gameGamerService.getGamerGameByGamerIdAndGameId(gameId, gamer.getId());
        gameGamerService.updateGameGamerWithQuestion(gameGamer, gamerQuestion);

        return QuestionResponse.of(questionDto);
    }

    public void validateGameInProgress(Game game) {
        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new GameException("gameNotProgress");
        }
    }
}
