package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorSubApprovalDetailsRepository extends JpaRepository<VendorSubApprovalDetails, Long> {
	
	@Query("FROM VendorSubApprovalDetails  where approvalId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VendorSubApprovalDetails> getVendorSubApprovalDetails(Long approvalId, Integer companyId) throws Exception;

	
	@Modifying
	@Query("UPDATE VendorSubApprovalDetails SET markForDelete = 1 WHERE approvalId = ?1 AND companyId = ?2 ")
	public void deleteSubVendorApproval(Long approvalId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE VendorSubApprovalDetails SET markForDelete = 1 WHERE subApprovalId = ?1 AND companyId = ?2 ")
	public void deleteSubVendorApprovalById(Long approvalId, Integer companyId) throws Exception;
	
}
