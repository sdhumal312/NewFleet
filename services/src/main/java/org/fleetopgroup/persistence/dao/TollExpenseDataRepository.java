package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.TollExpenseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TollExpenseDataRepository extends JpaRepository<TollExpenseData, Long> {
	
	@Query("FROM TollExpenseData where vehicle_no = ?1 AND transaction_datetime between ?2 AND ?3")
	public List<TollExpenseData>   getTollExpenseDataList(String vehicleNumber, Date fromDate, Date toDate) throws Exception;

}
