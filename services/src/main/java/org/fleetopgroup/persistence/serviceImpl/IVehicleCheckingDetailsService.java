package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface IVehicleCheckingDetailsService {

	public ValueObject saveCheckingDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getCheckingEntryReport(ValueObject valueObject) throws Exception;
	
	public Model getCheckingReportList(Integer pageNumber, Model model) throws Exception;
	
	public void delete(Long id) throws Exception;
	
	public ValueObject getConductorHistoryReport(ValueObject valueObject) throws Exception;
}
