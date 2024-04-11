package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.AccidentStatus;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleAccidentDetailsRepository extends JpaRepository<VehicleAccidentDetails, Long>{

	@Query("SELECT BI.accidentDetailsId From VehicleAccidentDetails as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<VehicleAccidentDetails> getVehicleAccidentDetailsDtoList(Integer companyId, Pageable pageable);
	
	@Modifying
	@Query("UPDATE VehicleAccidentDetails set markForDelete = 1, lastUpdatedOn = ?2, lastUpdateById = ?3 where accidentDetailsId = ?1")
	public void deleteVehicleAccident(Long accidentId, Date lasteUpdatedOn, Long updatedById) throws Exception;
	
	@Query("SELECT BI.accidentDetailsId From VehicleAccidentDetails as BI where BI.accidentDetailsNumber = ?1 AND BI.companyId = ?2 AND BI.markForDelete = 0")
	public long	getAccidentByNumber(Long accidentNumber, Integer companyId) throws Exception;
	
	@Query("FROM VehicleAccidentDetails where tripSheetId = ?1 and markForDelete = 0")
	public VehicleAccidentDetails getVehicleAccidentDetailsByTripSheetId(Long tripSheetId) throws Exception;
	
	@Query("FROM VehicleAccidentDetails where vid = ?1 AND status < "+AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_DONE+" and markForDelete = 0")
	public List<VehicleAccidentDetails> validateVehicleAccidentDetails(Integer vid) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleAccidentDetails set status = "+AccidentStatus.ACCIDENT_STATUS_PAYMENT_DONE+", lastUpdatedOn = ?2, lastUpdateById = ?3 where accidentDetailsId = ?1")
	public void updateAccidentStatusToDonePayment(Long accidentId, Date lasteUpdatedOn, Long updatedById) throws Exception;
	
}
