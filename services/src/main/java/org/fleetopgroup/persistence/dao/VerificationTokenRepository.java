package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
