package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DepartmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentHistoryRepository extends JpaRepository<DepartmentHistory, Integer> {

}
