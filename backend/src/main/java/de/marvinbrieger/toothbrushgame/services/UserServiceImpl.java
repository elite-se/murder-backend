package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.persistence.ApplicationUserRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.AlreadySignedUpException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private CurrentUserService currentUserService;

    @Override
    public void signUp(ApplicationUser user) throws AlreadySignedUpException {
        if (applicationUserRepository.findByDeviceId(user.getDeviceId()) != null)
            throw new AlreadySignedUpException(user.getDeviceId());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    @Override
    public void setPushToken(String token) {
        ApplicationUser user = currentUserService.getCurrentUser();
        user.setPushToken(token);
        applicationUserRepository.save(user);
    }

    @Override
    public void setLocale(Locale locale) {
        ApplicationUser user = currentUserService.getCurrentUser();
        user.setLocale(locale);
        applicationUserRepository.save(user);
    }
}
