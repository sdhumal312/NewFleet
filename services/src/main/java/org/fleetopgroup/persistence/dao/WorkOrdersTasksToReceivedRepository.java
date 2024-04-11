package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */

import org.fleetopgroup.persistence.model.WorkOrdersTasksToReceived;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrdersTasksToReceivedRepository extends JpaRepository<WorkOrdersTasksToReceived, Long> {

	// create WorkOrdersTasksTo_Received_Part_id
	//public void addWorkOrdersToReceived(WorkOrdersTasksToReceived workOrders) throws Exception;

}
