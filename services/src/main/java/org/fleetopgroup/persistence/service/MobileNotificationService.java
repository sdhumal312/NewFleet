package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.model.MobileNotifications;
import org.fleetopgroup.persistence.serviceImpl.IMobileNotificationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
@Service
public class MobileNotificationService implements IMobileNotificationService {
	@PersistenceContext EntityManager entityManager;
	private static final int PAGE_SIZE		 = 10;
	public ValueObject getNotifications(ValueObject valueObject) throws Exception {
		ValueObject                             valueObjectOut         = null;
		Integer				   					pageNumber			   = null;
		List<MobileNotifications>               notificationList       = null;
		try{
			valueObjectOut      = new ValueObject();
			pageNumber	 		= valueObject.getInt("pageNo",0);
			notificationList    = find_List_MobileNotification(pageNumber,valueObject.getInt("companyId",0),valueObject.getLong("userId",0),valueObject.getShort("moduleIdentifier",(short) 0));
			valueObjectOut.put("mobileNotificationList", notificationList);
			return valueObjectOut;
		}catch(Exception e){
			throw e;
		}
	}
	public List<MobileNotifications> find_List_MobileNotification(Integer pageNumber, Integer companyId,Long userId,short notificationModuleIdentifier)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("select mobileNotificationId,markForDelete,companyId,isViewedNotification,notification,notificationModuleIdentifier,userId from MobileNotifications  where companyId = "+companyId+" and userId = "+userId+"and notificationModuleIdentifier = "+notificationModuleIdentifier + " ORDER BY mobileNotificationId DESC ",
							 Object[].class);
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]>	results = queryt.getResultList();
			
			List<MobileNotifications>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MobileNotifications	mobileNotifications = new MobileNotifications();
					mobileNotifications.setMobileNotificationId((Long) result[0]);
					mobileNotifications.setMarkForDelete((Boolean) result[1]);
					mobileNotifications.setCompanyId((Long) result[2]);
					mobileNotifications.setViewedNotification((Boolean) result[3]);
					mobileNotifications.setNotification((String) result[4]);
					mobileNotifications.setNotificationModuleIdentifier((short) result[5]);
					mobileNotifications.setUserId((Long) result[6]);
					list.add(mobileNotifications);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
}
