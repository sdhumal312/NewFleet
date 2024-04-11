package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
@Service
public interface IMobileNotificationService {
	public ValueObject	getNotifications(ValueObject valueObject) throws Exception;
}
