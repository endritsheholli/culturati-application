package com.iotiq.application.controller;

import com.iotiq.application.messages.game.*;
import com.iotiq.application.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @GetMapping("/{gameId}/question")
    @PreAuthorize("hasAuthority(@GameAuth.VIEW)")
    public QuestionResponse getQuestion(@PathVariable @Valid UUID gameId) {
        return gameService.getNextQuestion(gameId);
    }

    @GetMapping("/{gameId}/question/{questionId}/check-answer")
    @PreAuthorize("hasAuthority(@GameAuth.VIEW)")
    public UserAnswerResponse checkAnswer(@PathVariable @Valid UUID gameId, @PathVariable String questionId, @RequestBody UserAnswerRequest request) {
        return gameService.checkAnswer(gameId, questionId, request);
    }
}
