package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TallyCompanyPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TallyCompanyPermissionRepository extends JpaRepository<TallyCompanyPermission, Long>{

	@Query("FROM TallyCompanyPermission where user_id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TallyCompanyPermission> getTallyCompanyPermissionList(long userId, Integer companyId) throws Exception ;
	
	@Modifying
	@Query("DELETE FROM TallyCompanyPermission where user_id = ?1")
	public void deleteTallyCompanyPermission(long userId) throws Exception ;
}
