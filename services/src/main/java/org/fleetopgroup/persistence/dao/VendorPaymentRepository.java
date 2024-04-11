package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorPaymentRepository extends JpaRepository<VendorPayment, Long> {
	
	@Query("FROM VendorPayment VP where VP.vendorId = ?1 AND VP.companyId = ?2 AND VP.markForDelete = 0")
	public VendorPayment getVendorOpeningBalance(Long vendorId, Integer companyId);

	@Query("SELECT vp.vendorPaymentId From VendorPayment as vp where vp.companyId = ?1 AND vp.markForDelete = 0")
	public Page<VendorPayment> getDeployment_Page_VendorPayment(Integer companyId, Pageable pageable);
	
	@Modifying
	@Query("UPDATE VendorPayment SET markForDelete = 1 WHERE vendorPaymentId = ?1 AND companyId = ?2")
	public void deleteVendorPayment(Long vendorPaymentId, Integer companyId);
	
	@Query("FROM VendorPayment VP where VP.vendorPaymentId = ?1 AND VP.markForDelete = 0")
	VendorPayment findById(long vendorPaymentId);
	
}
