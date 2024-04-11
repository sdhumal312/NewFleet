package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.DepartmentHistory;

public interface IDepartmentHistoryService {

    public void registerNewDepartmentHistory(DepartmentHistory departmentHistory) throws Exception;
}
