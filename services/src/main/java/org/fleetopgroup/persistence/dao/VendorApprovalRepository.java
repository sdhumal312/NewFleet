package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.VendorApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorApprovalRepository extends JpaRepository<VendorApproval, Long> {

	@Query("FROM VendorApproval VT where VT.approvalId = ?1 AND VT.companyId = ?2 AND VT.markForDelete = 0")
	public VendorApproval getVendorApproval(Long approvalId, Integer companyId) throws Exception;

	@Modifying
	@Query("update VendorApproval set approvalTotal= ?2 where approvalId= ?1 ")
	public void updateVendorApprovalAmount(Long approvalId, Double approvalAmount) throws Exception;

	@Modifying
	@Query("update VendorApproval set approvalPaymentTypeId= ?2, approvalPayNumber= ?3, approvalDateofpayment= ?4, approvalpaidbyId= ?5, approvalStatusId= ?6, lastModifiedById=?7, lastupdated=?8, approvalTotal=?9 where approvalId= ?1 ")
	public void updateApprovedPaymentDetails(Long approvalId, short paymentType, String paymentNumber,
			Date approvalCreateDate, Long paymentPaidBy, short approvedStutes, Long lastUpdateBy,
			Date lastUpdatedDate, double paidAmount) throws Exception;

	@Modifying
	@Query("update VendorApproval set approvalCreateById=?2, approvalCreateDate= ?3, approvalStatusId= ?4 where approvalId= ?1 ")
	public void updateVendorApprovedByAndDate(Long approvalId, Long approvedBy, Date approvalCreateDate,
			short approvalStatus) throws Exception;

	public List<VendorApproval> findAll();
	
	@Query("FROM VendorApproval VT where VT.companyId = ?1 AND VT.markForDelete = 0")
	public List<VendorApproval> findAllVendorApprovalList(Integer companyId);

	@Modifying
	@Query("UPDATE VendorApproval SET markForDelete = 1 WHERE approvalId = ?1 AND companyId = ?2 ")
	public void deleteVendorApproval(Long approvalId, Integer companyId) throws Exception;

	@Query("select count(*) from VendorApproval where markForDelete = 0")
	public Long countVendorApproval() throws Exception;

	@Query("select count(*) from VendorApproval where approvalCreateDate= ?1 ")
	public Long countVendorApprovedToday(Date today) throws Exception;
	
	@Query("SELECT VT.approvalId FROM VendorApproval VT where VT.approvalStatusId =?1  AND VT.companyId = ?2 AND VT.markForDelete = 0 ORDER BY VT.approvalId desc")
	public Page<VendorApproval> getDeploymentPageVendorApproval(short status, Integer companyId, Pageable pageable);
	
	@Modifying
	@Query("update VendorApproval set tallyCompanyId= ?1 where approvalId= ?2 ")
	public void updateTallyCompany(Long tallyCompanyId, Long approvalId) throws Exception;
}
