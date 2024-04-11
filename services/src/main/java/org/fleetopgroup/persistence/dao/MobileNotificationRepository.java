package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.MobileNotifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileNotificationRepository  extends JpaRepository<MobileNotifications, Long> {

}
