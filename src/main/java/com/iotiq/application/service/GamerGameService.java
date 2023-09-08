package com.iotiq.application.service;

import com.iotiq.application.domain.Game;
import com.iotiq.application.domain.Gamer;
import com.iotiq.application.domain.GamerGame;
import com.iotiq.application.domain.GamerRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamerGameService {

    public GamerGame createGamerGameAndAssociateWithGameAndGamer(Gamer gamer, Game game) {
        GamerGame gamerGame = new GamerGame();
        gamerGame.setGame(game);
        gamerGame.setGamer(gamer);
        gamerGame.setGamerRole(GamerRole.CREATOR);
        gamerGame.setTotalScore(0);

        // Establish bidirectional associations
        game.getGamerGame().add(gamerGame);
        gamer.getGamerGame().add(gamerGame);
        
        return gamerGame;
    }
}
