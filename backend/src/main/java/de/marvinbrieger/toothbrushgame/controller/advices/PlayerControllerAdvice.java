package de.marvinbrieger.toothbrushgame.controller.advices;

import de.marvinbrieger.toothbrushgame.controller.exceptions.PlayerAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlayerControllerAdvice {

    @ResponseBody
    @ExceptionHandler(PlayerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String playerAlreadyExistsHandler(PlayerAlreadyExistsException ex) {
        return ex.getMessage();
    }

}
