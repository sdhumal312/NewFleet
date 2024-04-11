package org.fleetopgroup.persistence.dao;


import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.SubscribeBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeBoxRepository extends JpaRepository<SubscribeBox, Long> {
 
	@Query("From SubscribeBox As s where ( s.SUBSCRIBE_DATE =?1 AND s.SUBSCRIBE_USER_EMAIL=?2 OR s.SUBSCRIBE_THRESHOLD_DATE=?1 AND s.SUBSCRIBE_USER_EMAIL=?2 ) AND s.COMPANY_ID = ?3 AND s.markForDelete = 0 ")
	public List<SubscribeBox> list_SubscribeBox_today(Date currentdate, String Useremail, Integer companyId);
	
	
	
	
}
