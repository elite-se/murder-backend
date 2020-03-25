package de.marvinbrieger.toothbrushgame.services.exceptions;

import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GameNotFoundExeception extends RuntimeException {

    public GameNotFoundExeception(Long id) {
        super("Could not find game " + id);
    }

    public GameNotFoundExeception(String gameCode) {
        super("Could not find game " + gameCode);
    }

    public GameNotFoundExeception(Long id, GameStatus destState) {
        super("Could not find game " + id + " or game is not in the state " + destState.getPredGameStatus());
    }

}
