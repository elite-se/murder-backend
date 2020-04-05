package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.services.exceptions.NotGameOwnerException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.EnsureGameOwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EnsureGameOwnerServiceImpl implements EnsureGameOwnerService {
    private CurrentUserService currentUserService;

    @Override
    public void ensureRequestedByGameOwner(Game game) {
        try {
            if (!currentUserService.getCurrentUser().equals(game.getOwner().getUser()))
                throw new NotGameOwnerException();
        } catch (UserNotFoundException e) {
            throw new NotGameOwnerException(e);
        }
    }
}
