package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceProgramSchedulesDto;
import org.fleetopgroup.persistence.dto.VehicleServiceProgramDto;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.fleetopgroup.persistence.model.VehicleServiceProgram;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IServiceProgramService {

	ValueObject	getServiceProgramList(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveServiceProgram(ValueObject	valueObject) throws Exception;
	
	ValueObject	getServiceProgramDetailsById(ValueObject	valueObject	) throws Exception; 
	
	ValueObject	saveServiceProramSchedule(ValueObject	valueObject	)throws Exception;
	
	ValueObject	deleteServiceSchedule(ValueObject	valueObject	)throws Exception;
	
	ValueObject	deleteServiceProgram(ValueObject	valueObject	)throws Exception;
	
	ValueObject	getVehicleServiceProgramDetailsById(ValueObject	valueObject	) throws Exception;
	
	ValueObject	updateServiceProgram(ValueObject	valueObject) throws Exception;
	
	List<VehicleServiceProgram>  getVehicleServiceProgram(String term, Integer companyId) throws Exception;
	
	List<VehicleTypeDto>  getVehicleServiceProgramByTypeId(Long typeId, Integer companyId) throws Exception;
	
	List<VehicleServiceProgram> getServiceProgramListByCompanyId(Integer companyId) throws Exception;
	
	
	public List<ServiceProgramSchedulesDto> getServiceProgramSchedulesBtId(Long id) throws Exception;
	
	public List<VehicleServiceProgramDto>  getServiceProgramList(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	VehicleServiceProgram getVehicleServiceProgramDetailsByName(String programName, Integer companyId) throws Exception;

	public void saveServiceProgramList(List<VehicleServiceProgram> saveServiceProgramList) throws Exception;

	public XSSFSheet getHssfSheetOfServiceProgram(DataValidationHelper validationHelper, XSSFSheet hssfSheet)throws Exception;

	public VehicleServiceProgram saveServiceProgram(VehicleServiceProgram vehicleServiceProgram) throws Exception;

	public void saveServiceProramSchedule(ServiceProgramSchedules serviceProgramSchedules) throws Exception;
	
	ValueObject	saveServiceProramAsign(ValueObject	valueObject	)throws Exception;
	
	ValueObject	deleteServiceProgramAssignment(ValueObject	valueObject	)throws Exception;
	
	List<VehicleTypeDto>  getVehicleServiceProgramByTypeId(Long typeId, Long modalId, Integer companyId) throws Exception;

	public ValueObject getVehicleSheduleProgramList(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleReminderList(ValueObject valueObject) throws Exception;
	
	public Map<Long, VehicleServiceProgram>  getServiceProgramHM(Integer companyId) throws Exception;
	
	public Map<Long, ServiceProgramSchedulesDto>  getServiceProgramSchedulesHM(Integer companyId) throws Exception;
	

}
