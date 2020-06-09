package de.marvinbrieger.toothbrushgame.webservice;

import java.io.File;
import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) throws IOException {
        userService.signUp(user);
    }

    @PutMapping("/push-token")
    public void setPushToken(@RequestBody String token) {
        userService.setPushToken(token);
    }

    @PutMapping("/locale")
    public void setLocale(@RequestBody String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        userService.setLocale(locale);
    }
}
