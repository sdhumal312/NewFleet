
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DealerServiceReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DealerServiceReminderRepository extends JpaRepository<DealerServiceReminder, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE DealerServiceReminder SET markForDelete = 1 where dealerServiceEntriesId =?1 AND companyId = ?2")
	void deleteDealerServiceReminder(long dealerServiceReminderId, int companyId);

	
}
