package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CashBookPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CashBookPermissionRepository extends JpaRepository<CashBookPermission, Long>{
	
	@Query("FROM CashBookPermission AS CBP where CBP.user_Id = ?1 AND CBP.companyId = ?2 AND CBP.markForDelete = 0")
	public List<CashBookPermission> getCashBookPermissionList(long userId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("DELETE FROM CashBookPermission WHERE user_Id = ?1 AND companyId = ?2")
	public void deleteCashBookPermissionByUserId(long userId, Integer companyId) throws Exception ;
}
