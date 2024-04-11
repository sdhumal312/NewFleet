

package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.AccidentQuotationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccidentQuotationDetailsRepository extends JpaRepository<AccidentQuotationDetails, Long> {
	
	@Modifying
	@Query(" UPDATE AccidentQuotationDetails SET markForDelete = 1 where serviceType=?1 AND serviceId=?2 AND companyId=?3")
	public void deleteQuotationDetails(Short typeId,long serviceId,int companyId);
	
	
	@Query(" From AccidentQuotationDetails where accidentDetailsId=?1 AND companyId= ?2 AND markForDelete = 0 ")
	public List<AccidentQuotationDetails> getQuotationDetailsList(long accidentDetailsId,int companyId);
	
	@Query("SELECT A From AccidentQuotationDetails As A "
			+ " INNER join WorkOrders as W on W.workorders_id = A.serviceId AND A.serviceType =2  AND W.workorders_statusId <> 4 AND W.companyId=?2  AND W.markForDelete = 0 "
			+ " where A.accidentDetailsId=?1 AND A.companyId= ?2 AND A.markForDelete = 0 ")
	public List<AccidentQuotationDetails> getincompleteWOList(long accidentDetailsId,int companyId);
	
	@Query("SELECT A From AccidentQuotationDetails As A "
			+ " join DealerServiceEntries as D on D.dealerServiceEntriesId = A.serviceId AND A.serviceType =3 AND D.statusId  NOT IN(3,4) AND D.markForDelete =0 "
			+ " where A.accidentDetailsId=?1 AND A.companyId= ?2 AND A.markForDelete = 0 ")
	public List<AccidentQuotationDetails> getincompleteDSEList(long accidentDetailsId,int companyId);

	
}

