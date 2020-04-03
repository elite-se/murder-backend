package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.services.exceptions.AlreadySignedUpException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;

public interface UserService {
    /**
     * Signs up an user.
     *
     * @param user the user that will be signed up
     * @throws AlreadySignedUpException if there already exists a user with the same device ID
     */
    void signUp(@RequestBody ApplicationUser user) throws AlreadySignedUpException;

    /**
     * Sets the expo push token for the currently logged in user.
     *
     * @param token the expo push token of the logged in user
     * @throws UserNotFoundException if the token used to authenticate is valid, but its user does not exist any more
     */
    void setPushToken(String token) throws UserNotFoundException;

    /**
     * Sets the preferred locale of the currently logged in user.
     *
     * @param locale new preferred locale of the user
     * @throws UserNotFoundException if the token used to authenticate is valid, but its user does not exist any more
     */
    void setLocale(Locale locale) throws UserNotFoundException;
}
