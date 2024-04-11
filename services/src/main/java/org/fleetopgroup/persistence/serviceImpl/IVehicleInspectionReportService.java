package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleInspectionReportService {

	ValueObject	getVehicleWiseInspctionReport(ValueObject	valueObject) throws Exception;
	
	ValueObject	getGroupWiseInspctionReport(ValueObject	valueObject) throws Exception;
}
