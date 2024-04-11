package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.DriverPaidAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverPaidAdvanceRepository  extends JpaRepository<DriverPaidAdvance, Long> {

	@Query("FROM DriverPaidAdvance WHERE DSAID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	List<DriverPaidAdvance> GET_DRIVER_PAY_SALARY_ADVACNE_ID(Long dRIVER_SALARYID, Integer companyId);

}
