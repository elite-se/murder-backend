package de.marvinbrieger.toothbrushgame.exceptions;

import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class GameInWrongStateException extends RuntimeException {
    public GameInWrongStateException(GameStatus requiredState, GameStatus actualState) {
        super(String.format("Game must be in state %s but is in state %s", requiredState, actualState));
    }
}
