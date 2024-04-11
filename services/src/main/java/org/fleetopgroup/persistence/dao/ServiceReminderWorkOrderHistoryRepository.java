package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceReminderWorkOrderHistoryRepository extends JpaRepository<ServiceReminderWorkOrderHistory, Long>{

	@Query("FROM ServiceReminderWorkOrderHistory where workOrderId = ?1 AND markForDelete = 0")
	public ServiceReminderWorkOrderHistory getServiceWorkOrder(Long workOrderId) throws Exception;
}
