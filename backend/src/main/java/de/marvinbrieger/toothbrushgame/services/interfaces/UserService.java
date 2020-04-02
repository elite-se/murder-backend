package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.services.exceptions.AlreadySignedUpException;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    /**
     * Signs up an user.
     *
     * @param user the user that will be signed up
     * @throws AlreadySignedUpException if there already exists a user with the same device ID
     */
    void signUp(@RequestBody ApplicationUser user) throws AlreadySignedUpException;


}
