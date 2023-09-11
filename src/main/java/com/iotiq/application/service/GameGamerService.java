package com.iotiq.application.service;

import com.iotiq.application.domain.Game;
import com.iotiq.application.domain.GameGamer;
import com.iotiq.application.domain.Gamer;
import com.iotiq.application.domain.GamerRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameGamerService {

    public GameGamer createGameGamerAndAssociateWithGameAndGamer(Gamer gamer, Game game) {
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
}
