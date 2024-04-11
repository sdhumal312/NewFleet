package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.constant.UpholsterySendLaundryInvoiceStatus;
import org.fleetopgroup.persistence.model.UpholsterySendLaundryInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UpholsterySendLaundryInvoiceRepository extends JpaRepository<UpholsterySendLaundryInvoice, Long>{
	
	@Query("SELECT BI.laundryInvoiceId From UpholsterySendLaundryInvoice as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<UpholsterySendLaundryInvoice> getDeployment_Page_UpholsterySendLaundryInvoice(Integer companyId, Pageable pageable);
	
	@Query("SELECT BI.laundryInvoiceId From UpholsterySendLaundryInvoice as BI  "
			+ " INNER JOIN SentLaundryClothDetails SLCD ON SLCD.laundryInvoiceId = BI.laundryInvoiceId "
			+ " where BI.companyId = ?1 AND SLCD.clothTypesId = ?2 AND BI.wareHouseLocationId = ?3 AND BI.laundryInvoiceStatus = "+UpholsterySendLaundryInvoiceStatus.LAUNDRY_INVOICE_STATUS_OPEN+" AND BI.markForDelete = 0")
	public Page<UpholsterySendLaundryInvoice> getDeployment_Page_ShowInWashing(Integer companyId, long clothTypeId, int locationId, Pageable pageable);
	
	@Query("FROM UpholsterySendLaundryInvoice WHERE laundryInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public UpholsterySendLaundryInvoice getLaundryInvoiceDetails(Long laundryInvoiceId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE UpholsterySendLaundryInvoice SET markForDelete = 1 where laundryInvoiceId = ?1 AND companyId = ?2")
	public void deleteLaundryInvoice(Long laundryInvoiceId, Integer companyId)throws Exception;
}
