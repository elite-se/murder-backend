package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.domain.Game;

import java.util.Optional;

public interface GameService {

    Optional<Game> createGame(Game game);

}
