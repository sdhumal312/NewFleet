package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.WorkOrderRemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrderRemarkRepository extends JpaRepository<WorkOrderRemark, Long> {

	@Query("FROM WorkOrderRemark where workOrderId = ?1 and markForDelete = 0")
	List<WorkOrderRemark> findByorkOrderId(Long orkOrderId);
}
