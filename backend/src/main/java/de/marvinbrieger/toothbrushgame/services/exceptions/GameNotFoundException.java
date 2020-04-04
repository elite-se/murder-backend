package de.marvinbrieger.toothbrushgame.services.exceptions;

import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(Long id) {
        super("Could not find game " + id);
    }

    public GameNotFoundException(String gameCode) {
        super("Could not find game " + gameCode);
    }

    public GameNotFoundException(Long id, GameStatus destState) {
        super("Could not find game " + id + " or game is not in the state " + destState);
    }

}
