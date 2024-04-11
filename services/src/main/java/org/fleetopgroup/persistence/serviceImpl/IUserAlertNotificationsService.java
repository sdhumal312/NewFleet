package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.UserAlertNotificationsDto;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IUserAlertNotificationsService {

	ValueObject	getUserNotificationList(ValueObject	valueObject)throws Exception;
	
	public Long getNotificationCountForUser(Long userId)throws Exception;
	
	public List<UserAlertNotificationsDto> getUserAlertNotificationsList(Long userId) throws Exception;
	
	public List<UserAlertNotificationsDto> getUnReadAlertNotificationsList(Long userId, Integer pageNumber) throws Exception;
	
	ValueObject	getUnreadNotificationList(ValueObject	valueObject)throws Exception;
	
	public Page<UserAlertNotifications> getUnReadPageObject(Integer pageNumber, Long userId) throws Exception;;
	
	ValueObject	markNotificationAsRead(ValueObject	valueObject)throws Exception;
	
	ValueObject	getReadNotificationList(ValueObject	valueObject)throws Exception;
	
	public Page<UserAlertNotifications> getReadPageObject(Integer pageNumber, Long userId)throws Exception;
	
	public List<UserAlertNotificationsDto> getReadAlertNotificationsList(Long userId, Integer pageNumber)
			throws Exception;
	
	ValueObject	getSentNotificationList(ValueObject	valueObject)throws Exception;
	
	public Page<UserAlertNotifications> getSentPageObject(Integer pageNumber, Long userId)throws Exception;
	
	public List<UserAlertNotificationsDto> getSentAlertNotificationsList(Long userId, Integer pageNumber)
			throws Exception;

	public ValueObject getAllRenewalRemiderListByStatus(ValueObject object)throws Exception;

	public ValueObject getRRNotificationCount()throws Exception;
}
