package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.persistence.ApplicationUserRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.AlreadySignedUpException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void signUp(ApplicationUser user) throws AlreadySignedUpException {
        if (applicationUserRepository.findByDeviceId(user.getDeviceId()) != null)
            throw new AlreadySignedUpException(user.getDeviceId());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    private ApplicationUser getLoggedInUser() throws UserNotFoundException {
        String deviceId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ApplicationUser user = applicationUserRepository.findByDeviceId(deviceId);
        if (user == null) throw new UserNotFoundException(deviceId);
        return user;
    }

    @Override
    public void setPushToken(String token) throws UserNotFoundException {
        ApplicationUser user = getLoggedInUser();
        user.setPushToken(token);
        applicationUserRepository.save(user);
    }
}
