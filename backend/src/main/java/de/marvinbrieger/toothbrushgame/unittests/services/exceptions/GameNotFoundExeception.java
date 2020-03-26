package de.marvinbrieger.toothbrushgame.unittests.services.exceptions;

import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundExeception extends RuntimeException {

    public GameNotFoundExeception(Long id) {
        super("Could not find game " + id);
    }

    public GameNotFoundExeception(String gameCode) {
        super("Could not find game " + gameCode);
    }

    public GameNotFoundExeception(Long id, GameStatus destState) {
        super("Could not find game " + id + " or game is not in the state " + destState);
    }

}
