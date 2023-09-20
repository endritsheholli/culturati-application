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
import java.util.Optional;
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
        
        // Get or create a Gamer entity associated with the user
        Gamer gamer = gamerService.getOrCreateGamerForUser(user);

        // Check Eligibility Before Creating a Game
        checkIfEligibleToCreateGame(gamer.getId());

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

    @Transactional
    public UserAnswerResponse checkAnswer(UUID gameId, String questionId, UserAnswerRequest request) {
        // Retrieve the currently logged-in user
        User user = userService.getCurrentUser();

        // Get the Gamer entity associated with the user
        Gamer gamer = gamerService.getOrCreateGamerForUser(user);

        // Validation checks
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("game"));
        validateGameInProgress(game);

        // Find the GameGamer entity for the gameId and gamerId
        GameGamer gameGamer = gameGamerService.getGamerGameByGamerIdAndGameId(gameId, gamer.getId());

        // Find the GamerQuestion by gameGamer
        GamerQuestion gamerQuestion = gamerQuestionService.findGamerQuestion(gameGamer, questionId);
        checkIfQuestionAnswerIsIncorrect(gamerQuestion);

        UserAnswerResponse answerResponse = questionService.checkAnswer(questionId, request);
        String totalScore = calculateTotalScore(gameGamer, answerResponse);

        updateGamerQuestionAndGameGamer(gamerQuestion, gameGamer, answerResponse, totalScore);

        return new UserAnswerResponse(answerResponse.isCorrect(), totalScore);
    }

    private void checkIfQuestionAnswerIsIncorrect(GamerQuestion gamerQuestion) {
        String scoreStr = gamerQuestion.getScore();

        // Check if "scoreStr" is not null, indicating that the question has been attempted.
        if (scoreStr != null) {

            // Check whether "isCorrect" is false, indicating that the player answered incorrectly.
            if (!gamerQuestion.getIsCorrect()) {
                throw new GameException("questionAnswered");
            }
        }
    }

    private String calculateTotalScore(GameGamer gameGamer, UserAnswerResponse answerResponse) {
        double currentTotalScore = Double.parseDouble(gameGamer.getTotalScore());
        double answerScore = Double.parseDouble(answerResponse.totalScore());
        return String.valueOf(currentTotalScore + answerScore);
    }

    private void updateGamerQuestionAndGameGamer(
            GamerQuestion gamerQuestion, GameGamer gameGamer, UserAnswerResponse answerResponse, String totalScore) {

        gamerQuestion.setIsCorrect(answerResponse.isCorrect());
        gamerQuestion.setScore(answerResponse.totalScore());
        gameGamer.setTotalScore(totalScore);
    }

    private void checkIfEligibleToCreateGame(UUID gamerId) {
        GameStatus gameStatus = GameStatus.ENDED;

        Optional<Game> game = gameRepository.findByGameGamersGamerIdAndStatusNot(gamerId, gameStatus);

        if (game.isPresent()) {
            // If the game status is not "ENDED" the user is not eligible to create a game.
            throw new GameException("userIneligibleToCreateGame", game.get().getId());
        }
    }
}
