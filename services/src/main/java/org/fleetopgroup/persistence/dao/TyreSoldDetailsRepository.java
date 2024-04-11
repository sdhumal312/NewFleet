/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TyreSoldDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TyreSoldDetailsRepository extends JpaRepository<TyreSoldDetails, Integer> {
	
	@Query("From TyreSoldDetails TE where TE.tyreSoldInvoiceId = ?1 AND TE.companyId = ?2 AND TE.markForDelete = 0 ")
	public List<TyreSoldDetails> findByTyreSoldInvoiceId(Long invoiceId , Integer companyId);
	
	@Modifying
	@Query("UPDATE TyreSoldDetails TE SET TE.discount =?1, TE.gst =?2, TE.tyreSoldAmount =?3,  TE.tyreSoldNetAmount =?4 where TE.tyreId = ?5 AND TE.companyId = ?6 AND TE.markForDelete = 0 ")
	public void saveSoldTyreCost(double comission, double gst, double soldAmount, double soldNetAmount,long tyreId,Integer companyId);


}
