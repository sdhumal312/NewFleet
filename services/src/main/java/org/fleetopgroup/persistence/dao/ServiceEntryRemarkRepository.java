package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ServiceEntriesRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntryRemarkRepository extends JpaRepository<ServiceEntriesRemark, Long> {

	//@Query("FROM WorkOrderRemark where workOrderId = ?1 and markForDelete = 0")
	//List<WorkOrderRemark> findByorkOrderId(Long orkOrderId);
}
