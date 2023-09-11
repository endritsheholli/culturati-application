package com.iotiq.application.controller;

import com.iotiq.application.messages.game.CreateGameRequest;
import com.iotiq.application.messages.game.UpdateGameStatusRequest;
import com.iotiq.application.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Games", description = "Games API")
@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(@GameAuth.CREATE)")
    public UUID createGame(@RequestBody CreateGameRequest request) {
        return gameService.createGame(request).getId();
    }

    @PutMapping("/{gameId}")
    @PreAuthorize("hasAuthority(@GameAuth.UPDATE)")
    public void updateGameStatus(@PathVariable UUID gameId, @RequestBody UpdateGameStatusRequest request) {
        gameService.updateGameStatus(gameId, request);
    }
}
