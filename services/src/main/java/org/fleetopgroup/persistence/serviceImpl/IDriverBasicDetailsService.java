package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverBasicDetailsDto;
import org.fleetopgroup.persistence.model.DriverBasicDetails;
import org.fleetopgroup.web.util.ValueObject;

public interface IDriverBasicDetailsService {
	
	public ValueObject getDriverAllBasicDetails(ValueObject valueObject)throws Exception;
	
	public List<DriverBasicDetailsDto> getAllDriverBasicDetailsList(ValueObject valueObject) throws Exception;

	public DriverBasicDetails validateDriverBasicDetails(Long detailsType,Integer driverId, Timestamp assignDate, Integer companyId) throws Exception;
	
	public ValueObject saveDriverBasicDetails(ValueObject valueObject) throws Exception;

	public ValueObject getDriverBasicDetails(ValueObject valueInObject)throws Exception;

	public DriverBasicDetailsDto getDriverBasicDetailsById(Long driverBasicDetilsId, Integer companyId) throws Exception;
	
	public ValueObject updateDriverBasicDetails(ValueObject valueInObject)throws Exception;

	public ValueObject deleteDriverBasicDetails(ValueObject valueInObject)throws Exception;

	public List<DriverBasicDetails>  getDriverBasicDetailListByTypeId(ValueObject valueInObject) throws Exception;
	
	public ValueObject getAllDriverTypeBasicDetails(ValueObject valueObject) throws Exception ;

	

	
}