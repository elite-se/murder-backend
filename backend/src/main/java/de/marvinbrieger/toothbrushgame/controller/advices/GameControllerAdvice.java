package de.marvinbrieger.toothbrushgame.controller.advices;

import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundExeception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GameControllerAdvice {

    @ResponseBody
    @ExceptionHandler(GameNotFoundExeception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String gameNotFoundHandler(GameNotFoundExeception ex) {
        return ex.getMessage();
    }

}
