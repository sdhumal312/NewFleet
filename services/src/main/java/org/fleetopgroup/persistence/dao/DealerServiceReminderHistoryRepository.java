package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DealerServiceReminderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DealerServiceReminderHistoryRepository extends JpaRepository<DealerServiceReminderHistory, Long>{

	@Query("FROM DealerServiceReminderHistory where dealerServiceEntriesId = ?1 AND markForDelete = 0")
	public DealerServiceReminderHistory getDealerServiceReminderHistory(Long dealerServiceEntriesId) throws Exception;
}
