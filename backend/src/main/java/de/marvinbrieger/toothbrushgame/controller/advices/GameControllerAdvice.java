package de.marvinbrieger.toothbrushgame.controller.advices;

import de.marvinbrieger.toothbrushgame.unittests.services.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.unittests.services.exceptions.IncompleteRequestException;
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

    @ResponseBody
    @ExceptionHandler(IncompleteRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String gameNotFoundHandler(IncompleteRequestException ex) {
        return ex.getMessage();
    }

}
