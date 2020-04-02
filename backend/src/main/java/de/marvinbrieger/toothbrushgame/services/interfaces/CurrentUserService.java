package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;

public interface CurrentUserService {
    /**
     * Returns the currently logged in user
     * @return the currently logged in user
     * @throws UserNotFoundException if the token used to authenticate is valid, but its user does not exist any more
     */
    ApplicationUser getCurrentUser() throws UserNotFoundException;
}
