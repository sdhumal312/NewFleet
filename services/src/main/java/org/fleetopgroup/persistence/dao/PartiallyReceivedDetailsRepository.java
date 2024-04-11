package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PartiallyReceivedDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartiallyReceivedDetailsRepository extends JpaRepository<PartiallyReceivedDetails, Long> {
	
	@Query("FROM PartiallyReceivedDetails WHERE approvedId =?1 AND companyId=?2  AND markForDelete = 0 ")
	public List<PartiallyReceivedDetails> getPartialReceiveData(long approvedId,int companyId);

}
