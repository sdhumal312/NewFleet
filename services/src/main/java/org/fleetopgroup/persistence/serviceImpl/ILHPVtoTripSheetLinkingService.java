package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface ILHPVtoTripSheetLinkingService {

	ValueObject	syncDataOfLhpvFromIvCargo(ValueObject	valueObject) throws Exception;
	
	ValueObject	syncLhpvDetailsToAddInTripSheet(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveLhpvDetailsToTripSheet(ValueObject	valueObject) throws Exception;
}
