package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;
import org.fleetopgroup.persistence.dto.DriverLedgerDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IDriverLedgerService {

	public ValueObject addDriverLedgerDetails(ValueObject	valueInObject) throws Exception;
	
	public ValueObject	deleteDataInDriverLedger(ValueObject	valueObject)throws Exception;
	
	public void updateDriverLedgerWhenTripDeleted(long tripSheetId ,long driverId,long tripsheetNo) throws Exception;
	
	public void updateDriverLedgerWhenDriverUpdated(long tripSheetId,long tripNumber,long driverId,long oldDriverId, Date tripOpeningDate) throws Exception ;

	public List<DriverLedgerDto> getDriverLedgerReportList(StringBuilder query, Integer company_id) throws Exception;

	ValueObject getDriverLedgertReportList(ValueObject valueObject) throws Exception;
}
