package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.services.exceptions.AlreadySignedUpException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) throws AlreadySignedUpException {
        userService.signUp(user);
    }

    @PutMapping("/push-token")
    public void setPushToken(@RequestBody String token) throws UserNotFoundException {
        userService.setPushToken(token);
    }

    @PutMapping("/locale")
    public void setLocale(@RequestBody String languageTag) throws UserNotFoundException {
        Locale locale = Locale.forLanguageTag(languageTag);
        userService.setLocale(locale);
    }
}
