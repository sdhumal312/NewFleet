package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.Labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface LabourRepository  extends JpaRepository<Labour, Integer> {

	@Query("FROM Labour WHERE companyId =?1 AND markForDelete = 0")
	public List<Labour> getAllLabourMaster(int companyId);
	
	@Query("From Labour L where L.labourName =?1 AND L.companyId = ?2 AND L.markForDelete = 0 ")
	public Labour findByName(String labourName, Integer companyId);
	
	@Query("FROM Labour WHERE labourId =?1 AND companyId =?2 AND markForDelete = 0")
	public Labour getLabourMaster(int labourId, int companyId);

	@Modifying
	@Query("UPDATE Labour SET labourName =?2 , description =?3 WHERE labourId =?1 AND companyId =?4 ")
	public void updateLabour(int labourId, String labourName, String description, int companyId);

	@Modifying
	@Query("UPDATE Labour SET markForDelete = 1  WHERE labourId =?1 AND companyId =?2 ")
	public void deleteLabourMaster(int labourId, int companyId);
	
}
