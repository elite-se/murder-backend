package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;

public interface UserService {
    /**
     * Signs up an user.
     *
     * @param user the user that will be signed up
     */
    void signUp(@RequestBody ApplicationUser user);

    /**
     * Sets the expo push token for the currently logged in user.
     *
     * @param token the expo push token of the logged in user
     */
    void setPushToken(String token);

    /**
     * Sets the preferred locale of the currently logged in user.
     *
     * @param locale new preferred locale of the user
     */
    void setLocale(Locale locale);
}
