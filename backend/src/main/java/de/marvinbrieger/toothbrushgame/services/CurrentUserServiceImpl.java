package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.persistence.ApplicationUserRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public ApplicationUser getCurrentUser() {
        String deviceId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ApplicationUser user = applicationUserRepository.findByDeviceId(deviceId);
        if (user == null) throw new UserNotFoundException(deviceId);
        return user;
    }
}
