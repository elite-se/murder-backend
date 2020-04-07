package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;

public interface CurrentUserService {
    /**
     * Returns the currently logged in user
     * @return the currently logged in user
     */
    ApplicationUser getCurrentUser();
}
