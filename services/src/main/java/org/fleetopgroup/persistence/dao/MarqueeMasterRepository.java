package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.fleetopgroup.persistence.model.BatteryTransfer;
import org.fleetopgroup.persistence.model.MarqueeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MarqueeMasterRepository extends JpaRepository<MarqueeMaster, Long> {
	
	/*@Query("FROM MarqueeMaster where  markForDelete = 0 ORDER BY companyId asc ")
	public ArrayList<MarqueeMaster> getAllMarqueeMessagesList() throws Exception;*/
	
	@Transactional
	@Modifying
	@Query("UPDATE MarqueeMaster SET markForDelete = 1  where marquee_id = ?1")
	public void deleteMarqueeMessage(long marquee_id)throws Exception;
	
	@Transactional
	@Modifying
	@Query("UPDATE MarqueeMaster SET marquee_message =?1 where marquee_id = ?2 AND markForDelete = 0")
	public void updateMarqueeMessage(String marqueeMessage, long marquee_id)throws Exception;
	
}
