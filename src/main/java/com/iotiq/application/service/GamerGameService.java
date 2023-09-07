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

    public GamerGame createGamerGame(Gamer gamer, Game game) {
        GamerGame gamerGame = new GamerGame();
        gamerGame.setGame(game);
        gamerGame.setGamer(gamer);
        gamerGame.setGamerRole(GamerRole.CREATOR);
        gamerGame.setTotalScore(0);
        return gamerGame;
    }
    public void associateGameWithGamer(Gamer gamer, Game game, GamerGame gamerGame) {
        game.getGamerGame().add(gamerGame);
        gamer.getGamerGame().add(gamerGame);
    }
}
