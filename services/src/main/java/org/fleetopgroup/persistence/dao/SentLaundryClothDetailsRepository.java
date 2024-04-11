package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.SentLaundryClothDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SentLaundryClothDetailsRepository extends JpaRepository<SentLaundryClothDetails, Long>{

	@Modifying
	@Query("UPDATE SentLaundryClothDetails SET markForDelete = 1 where laundryInvoiceId = ?1 AND companyId = ?2")
	public void deleteLaundryClothDetails(Long laundryInvoiceId , Integer companyId) throws Exception;
	
	@Query("FROM SentLaundryClothDetails where laundryInvoiceId = ?1 AND companyId = ?2")
	List<SentLaundryClothDetails>  getSentLaundryClothDetailsList(Long laundryInvoiceId , Integer companyId) throws Exception;
}
