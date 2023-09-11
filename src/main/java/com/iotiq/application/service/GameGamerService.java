package com.iotiq.application.service;

import com.iotiq.application.domain.*;
import com.iotiq.application.exception.GameException;
import com.iotiq.application.repository.GameGamerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameGamerService {
    private final GameGamerRepository gameGamerRepository;
    
    public GameGamer createGameGamer(Gamer gamer, Game game) {
        GameGamer gameGamer = new GameGamer();
        gameGamer.setGame(game);
        gameGamer.setGamer(gamer);
        gameGamer.setGamerRole(GamerRole.CREATOR);
        gameGamer.setTotalScore(0);

        // Establish bidirectional associations
        game.getGameGamers().add(gameGamer);
        gamer.getGamerGames().add(gameGamer);
        
        return gameGamer;
    }
    public GameGamer getGamerGameByGamerIdAndGameId(UUID gameId, UUID gamerId){
        return gameGamerRepository.findByGameIdAndGamerId(gameId, gamerId)
                .orElseThrow(() ->  new GameException("gameNotJoined"));
    }
    public GameGamer updateGameGamerWithQuestion(GameGamer gameGamer, GamerQuestion gamerQuestion) {
        gameGamer.getGamerQuestion().add(gamerQuestion);
        return gameGamerRepository.save(gameGamer);
    }
}
