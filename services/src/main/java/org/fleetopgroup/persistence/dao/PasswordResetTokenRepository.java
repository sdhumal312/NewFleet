package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PasswordResetToken;
import org.fleetopgroup.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

}
