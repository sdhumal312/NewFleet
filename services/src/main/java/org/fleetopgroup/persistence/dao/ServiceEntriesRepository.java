package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.ServiceEntries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntriesRepository extends JpaRepository<ServiceEntries, Long> {

	// public void addServiceEntries(ServiceEntries ServiceEntries) throws
	// Exception;

	@Modifying
	@Query("UPDATE From ServiceEntries AS s SET s.driver_id=?1, s.vehicle_Odometer=?2, s.vendor_id=?3, s.invoiceNumber=?4, "
			+ " s.invoiceDate=?5, s.jobNumber=?6, s.service_paymentTypeId=?7,s.service_vendor_paymodeId=?8, s.service_PayNumber=?9, s.service_paidbyId=?10, "
			+ " s.lastModifiedById=?11, s.lastupdated=?12, s.service_paiddate=?13, s.workshopInvoiceAmount=?16, s.tallyCompanyId =?17, s.tallyExpenseId = ?18, s.vid = ?19  WHERE s.serviceEntries_id=?14 ANd companyId = ?15")
	public void updateServiceEntries(Integer driver_id, Integer vehicle_Odometer, Integer vendor_id,
			String invoiceNumber, java.util.Date date, String jobNumber,
			short service_paymentType, short vendorPaymodId, String service_PayNumber, Long service_paidby, Long LastModifiedById,
			java.util.Date Lastupdated, java.util.Date paidDate, Long serviceEntries_id, Integer companyId, Double workShopAmount, Long tallycompanyId, Integer tallyExpenseId, Integer vid);

	@Query("From ServiceEntries where serviceEntries_id = ?1 and companyId = ?2 and markForDelete = 0")
	public ServiceEntries getServiceEntries(long ServiceEntries, Integer companyId) throws Exception;

	// public void getServiceEntriesList(ServiceEntries ServiceEntries);

	public List<ServiceEntries> findAll();

	@Query("From ServiceEntries where issue_statues = ?1 AND markForDelete = 0")
	public List<ServiceEntries> listVehicleServiceEntries(String ServiceEntries_vehiclename);

	@Modifying
	@Query("UPDATE ServiceEntries SET lastupdated=?2, lastModifiedById=?3, markForDelete = 1 WHERE ServiceEntries_id = ?1 and companyId = ?4")
	public void deleteServiceEntries(Long ServiceEntries_id,Timestamp toDate, Long userId, Integer companyid);



	@Modifying
	@Query("UPDATE ServiceEntries SET serviceEntries_statusId= ?1, lastupdated = ?4, lastModifiedById = ?5 where ServiceEntries_id= ?2 AND companyId = ?3 ")
	public void updateServiceEntriesProcess(short Process, Long ServiceEntries_ID, Integer companyId, Date lastupdated, Long lastmodifiedby) throws Exception;


	@Modifying
	@Query("UPDATE ServiceEntriesTasks SET mark_complete = ?1 where ServiceEntriestaskid= ?2 AND companyId = ?3")
	public void updateServiceEntriesTask_Completion(Integer completionvalue, Long ServiceEntriesTasksID, Integer companyId)
			throws Exception;

	@Modifying
	@Query("UPDATE ServiceEntries SET serviceEntries_statusId= ?1, completed_date = ?2, lastModifiedById = ?5  where ServiceEntries_id= ?3 AND companyId = ?4 ")
	public void updateServiceEntriesCOMPLETE_date(short Process, Date COMPLETE_date, Long ServiceEntries_ID, Integer companyId, Long lastmodifiedby)
			throws Exception;

	@Modifying
	@Query("UPDATE ServiceEntries SET totalserviceROUND_cost =?1, ServiceEntries_status= ?2, completed_date = ?3  where ServiceEntries_id= ?4 ")
	public void updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(Double serviceRoundCost, String Process,
			Date COMPLETE_date, Long ServiceEntries_ID) throws Exception;

	// count ServiceEntries
	@Query("select count(*) from ServiceEntries SE where SE.companyId = ?1 AND SE.markForDelete = 0 ")
	public Long countServiceEntries(Integer companyId) throws Exception;

	@Query("select count(*) from ServiceEntries Where ServiceEntries_status= ?1 AND companyId = ?2 and markForDelete = 0")
	public Long countServiceEntriestatues(String Statues, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE ServiceEntries SET totalworktax_cost= ?1, totalServiceEntries_cost = ?2  where ServiceEntries_id= ?3 ")
	public void updateServiceEntriesMainTotalCost_TAX(Double TotalServiceEntriesTAX, Double TotalServiceEntriescost,
			Long ServiceEntries_ID) throws Exception;

	// public List<ServiceEntries> SearchServiceEntries(String
	// ServiceEntries_Search) throws Exception;

	// vehicle Inside to ServiceEntries
	@Query("from ServiceEntries where vid = ?1 AND markForDelete = 0 ")
	public List<ServiceEntries> VehicleToServiceEntriesList(Integer Vehicle_id) throws Exception;

	// validate Vendor name and invoice number
	@Query("from ServiceEntries where ( vendor_id = ?1 and invoiceNumber = ?2 OR vid = ?3 and invoiceNumber = ?2 ) and companyId = ?3 and markForDelete = 0")
	public List<ServiceEntries> validate_ServiceEntries(Integer vendor_Id, String invoice_no, Integer vehicle_Id, Integer companyId);

	// Vendor Approval ID to Show Service Entries
	@Query("from ServiceEntries where service_vendor_approvalID = ?1 AND markForDelete = 0")
	public List<ServiceEntries> getVendorApproval_IN_SERVICE_Entries_List(Long VendorApproval_Id) throws Exception;


	@Modifying
	@Query("UPDATE ServiceEntries SET service_document_id =?1, service_document =?2 where serviceEntries_id= ?3 AND companyId = ?4 ")
	public void Update_ServiceEntries_Docuemnt_AvailableValue(Long service_documentid, boolean service_document,
			Long serviceEntries_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE ServiceEntries SET document_id =?1, service_document =?2 where serviceEntries_id= ?3 AND companyId = ?4 ")
	public void Update_ServiceEntries_Docuemnt_AvailableValue(String service_documentid, boolean service_document,
			Long serviceEntries_id, Integer companyId);
	
	@Query("From ServiceEntries SE "
			+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
			+ "	INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " where SE.serviceEntries_Number = ?1 and SE.companyId = ?2 and SE.markForDelete = 0")
	public ServiceEntries getServiceEntriesByNumber(long ServiceEntries, Integer companyId, Long id) throws Exception;

	@Query("From ServiceEntries SE where SE.serviceEntries_Number = ?1 and SE.companyId = ?2 and SE.markForDelete = 0")
	public ServiceEntries getServiceEntriesByNumber(long ServiceEntries, Integer companyId) throws Exception;
	
	@Query("select v.serviceEntries_id FROM ServiceEntries as v Where  v.serviceEntries_statusId =?1 AND  v.companyId = ?2 AND v.markForDelete = 0 ORDER BY v.serviceEntries_id DESC ")
	public Page<ServiceEntries> getDeployment_Page_ServiceEntries(short Status_ID, Integer companyId, Pageable pageable);
	
	@Query("select SE.serviceEntries_id FROM ServiceEntries as SE "
			+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " Where SE.serviceEntries_statusId =?1 AND  SE.companyId = ?3 AND SE.markForDelete = 0 ORDER BY SE.serviceEntries_id DESC ")
	public Page<ServiceEntries> getDeployment_Page_ServiceEntries(short Status_ID, long userId, Integer companyId, Pageable pageable);
	

	@Query("select count(*) from ServiceEntries SE where SE.serviceEntries_Number = ?1 AND SE.companyId = ?2 AND SE.markForDelete = 0 ")
	public int getServiceEntriesCountByNumber(long serviceEntries_id,Integer companyId) throws Exception;

	@Query("FROM ServiceEntries SE where SE.serviceEntries_id = ?1 AND SE.companyId = ?2 AND SE.markForDelete = 0 ")
	public ServiceEntries deleteServiceEntries(Long serviceEntries_id, Integer companyid);

	
	@Query(nativeQuery = true, value = "select * FROM ServiceEntries SE where SE.invoiceNumber = ?1 AND SE.companyId = ?2 AND SE.markForDelete = 0 ")
	public ServiceEntries getServiceEntriesByInvoiceNumber(String invoiceNumber, Integer companyid);
	
	@Query("SELECT SE.serviceEntries_Number FROM ServiceEntries AS SE WHERE SE.companyId = ?1 AND SE.serviceEntries_id = ?2 AND SE.markForDelete = 0")
	public Long getSE_Number(Integer companyId, Long serviceEntries_id ) throws Exception;
	

}
