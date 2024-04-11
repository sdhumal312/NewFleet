package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.SubscribeBox;

public interface ISubscribeBoxService {

	/** The value is create Subscribe user id insert */
	public SubscribeBox insert_Subscribe_Box(SubscribeBox mailboc) throws Exception;

	/** The value is find subscribe value list of one user subscribe box*/
	public List<SubscribeBox> list_SubscribeBox_today(Date currentdate, String Useremail, Integer companyId);
	
	/** The Value Of count All Subscrible mail in user */
	public Object[] countTotal_Subscrible_MailBox(String mail_id, String currentdate, Integer companyId) throws Exception;


}
