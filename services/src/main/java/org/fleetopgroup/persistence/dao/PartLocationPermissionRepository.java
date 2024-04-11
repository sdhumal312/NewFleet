package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PartLocationPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartLocationPermissionRepository extends JpaRepository<PartLocationPermission, Long>{
	
	@Query("FROM PartLocationPermission AS CBP where CBP.user_Id = ?1 AND CBP.companyId = ?2 AND CBP.markForDelete = 0")
	public List<PartLocationPermission> getPartLocationPermissionList(long userId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("DELETE FROM PartLocationPermission WHERE user_Id = ?1 AND companyId = ?2")
	public void deletePartLocationPermissionByUserId(long userId, Integer companyId) throws Exception ;
}
