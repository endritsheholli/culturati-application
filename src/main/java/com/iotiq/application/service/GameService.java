package com.iotiq.application.service;

import com.iotiq.application.domain.Game;
import com.iotiq.application.domain.GameStatus;
import com.iotiq.application.domain.Gamer;
import com.iotiq.application.messages.game.CreateGameRequest;
import com.iotiq.application.repository.GameRepository;
import com.iotiq.user.domain.User;
import com.iotiq.user.internal.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final GamerService gamerService;
    private final GameGamerService gameGamerService;

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
}
