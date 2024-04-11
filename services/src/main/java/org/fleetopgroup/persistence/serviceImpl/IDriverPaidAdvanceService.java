package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.DriverPaidAdvanceDto;
import org.fleetopgroup.persistence.model.DriverPaidAdvance;

public interface IDriverPaidAdvanceService {

	DriverPaidAdvance register_New_DriverPaidAdvance(DriverPaidAdvance accountDto) throws Exception;

	Double Get_TotalSum_OF_Paid_Advacne_Amount_Total(Long dsaid, Integer companyId);

	List<DriverPaidAdvanceDto> GET_DRIVER_PAY_SALARY_ADVACNE_ID(Long dRIVER_SALARYID, Integer companyId);

}
