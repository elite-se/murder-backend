package de.marvinbrieger.toothbrushgame.controller.exceptions;

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

}
