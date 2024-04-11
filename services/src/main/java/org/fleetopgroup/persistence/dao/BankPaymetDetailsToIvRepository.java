package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BankPaymetDetailsToIv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankPaymetDetailsToIvRepository extends JpaRepository<BankPaymetDetailsToIv, Long> {
	
	
	@Query("From BankPaymetDetailsToIv WHERE bankPaymetDetailsToIvId IN (?1) AND markForDelete = 0 ")
	public List<BankPaymetDetailsToIv> getBankPaymentDetailsList(List<Long> idList);

}
