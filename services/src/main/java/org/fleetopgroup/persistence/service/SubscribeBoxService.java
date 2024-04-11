package org.fleetopgroup.persistence.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.SubscribeBoxRepository;
import org.fleetopgroup.persistence.model.SubscribeBox;
import org.fleetopgroup.persistence.serviceImpl.ISubscribeBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SubscribeBoxService implements ISubscribeBoxService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private SubscribeBoxRepository subscribeBoxRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ISubscribeBoxService#
	 * insert_Subscribe_Box(org.fleetop.persistence.model.Subscribe)
	 */
	@Transactional
	public SubscribeBox insert_Subscribe_Box(SubscribeBox subscribe) throws Exception {

		return subscribeBoxRepository.save(subscribe);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ISubscribeBoxService#
	 * list_SubscribeBox_today(java.util.Date, java.lang.String)
	 */
	@Transactional
	public List<SubscribeBox> list_SubscribeBox_today(Date currentdate, String Useremail, Integer companyId) {

		return subscribeBoxRepository.list_SubscribeBox_today(currentdate, Useremail, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ISubscribeBoxService#
	 * countTotal_Subscrible_MailBox(java.lang.String)
	 */
	@Transactional
	public Object[] countTotal_Subscrible_MailBox(String mail_id, String toDate, Integer companyId) throws Exception {

		Query queryt = entityManager.createQuery("SELECT COUNT(co),"
				+ "( SELECT  COUNT(S) FROM ServiceReminder AS S where ( S.time_servicedate <= '" + toDate + "'  "
				+ " OR S.meter_serviceodometer <= S.vehicle_currentOdometer" + " OR S.time_servicethreshold_date <= '"
				+ toDate + "' AND  S.time_servicedate >= '" + toDate + "'  "
				+ " OR S.meter_servicethreshold_odometer <= S.vehicle_currentOdometer AND S.meter_serviceodometer >= S.vehicle_currentOdometer) AND S.companyId = "+companyId+" AND S.markForDelete = 0 ), "
				+ "(SELECT  COUNT(R) FROM RenewalReminder AS R where R.renewal_to between '" + toDate + "' AND '"
				+ toDate + "' AND R.companyId = "+companyId+" AND R.markForDelete = 0 )," + "(SELECT  COUNT(DR) FROM DriverReminder AS DR where DR.driver_dlto= '" + toDate
				+ "' AND DR.companyId = "+companyId+" AND DR.markForDelete = 0 )" + " FROM SubscribeBox As co  Where ( co.SUBSCRIBE_DATE ='" + toDate
				+ "' AND co.SUBSCRIBE_USER_EMAIL='" + mail_id + "' " + "OR co.SUBSCRIBE_THRESHOLD_DATE='" + toDate
				+ "' AND co.SUBSCRIBE_USER_EMAIL='" + mail_id + "' ) AND co.COMPANY_ID = "+companyId+" AND co.markForDelete = 0 ");

		Object[] count = (Object[]) queryt.getSingleResult();

		/*
		 * Long cnt1 = (Long) count[1]; System.out.println(cnt1);
		 */
		return count;
	}


}