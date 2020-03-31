package de.marvinbrieger.toothbrushgame.persistence;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByDeviceId(String deviceId);
}
