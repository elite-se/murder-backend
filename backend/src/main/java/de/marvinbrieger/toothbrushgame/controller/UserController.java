package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.services.exceptions.AlreadySignedUpException;
import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) throws AlreadySignedUpException {
        userService.signUp(user);
    }
}
