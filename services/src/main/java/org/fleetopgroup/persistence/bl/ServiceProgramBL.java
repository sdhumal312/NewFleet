package org.fleetopgroup.persistence.bl;

import java.util.Date;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.persistence.model.ServiceProgramAsignmentDetails;
import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.fleetopgroup.persistence.model.VehicleServiceProgram;
import org.fleetopgroup.web.util.ValueObject;

public class ServiceProgramBL {

	public static VehicleServiceProgram getVehicleServiceProgramDTO(ValueObject valueObject) throws Exception{
		VehicleServiceProgram		vehicleServiceProgram		= null;
		
		try {
			vehicleServiceProgram =new VehicleServiceProgram();
			
			vehicleServiceProgram.setCompanyId(valueObject.getInt("companyId", 0));
			vehicleServiceProgram.setProgramName(valueObject.getString("programName"));
			vehicleServiceProgram.setDescription(valueObject.getString("description"));
			vehicleServiceProgram.setCreatedById(valueObject.getLong("userId",0));
			vehicleServiceProgram.setCreatedOn(new Date());
			vehicleServiceProgram.setLastModifiedById(valueObject.getLong("userId",0));
			vehicleServiceProgram.setLastModifiedOn(new Date());
			if(vehicleServiceProgram.getCompanyId().equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
				vehicleServiceProgram.setVendorProgram(true);
			}else {
				vehicleServiceProgram.setVendorProgram(false);
			}
			
			
			return vehicleServiceProgram;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleServiceProgram		= null;
		}
	}
	
	public static ServiceProgramSchedules getServiceProgramSchedules(ValueObject valueObject)throws Exception{
		ServiceProgramSchedules		serviceProgramSchedules		= null;
		try {
			serviceProgramSchedules	= new ServiceProgramSchedules();
			
			serviceProgramSchedules.setVehicleServiceProgramId(valueObject.getLong("vehicleServiceProgramId", 0));
			serviceProgramSchedules.setJobTypeId(valueObject.getInt("jobTypeId",0));
			serviceProgramSchedules.setJobSubTypeId(valueObject.getInt("jobSubTypeId",0));
			serviceProgramSchedules.setMeterInterval(valueObject.getInt("meter_interval",0));
			serviceProgramSchedules.setTimeInterval(valueObject.getInt("time_interval",0));
			serviceProgramSchedules.setMeterThreshold(valueObject.getInt("meter_threshold",0));
			serviceProgramSchedules.setTimeThreshold(valueObject.getInt("time_threshold",0));
			serviceProgramSchedules.setTimeIntervalType(valueObject.getShort("time_intervalperiod"));
			serviceProgramSchedules.setTimeThresholdType(valueObject.getShort("time_thresholdperiod"));
			serviceProgramSchedules.setCompanyId(valueObject.getInt("companyId",0));
			serviceProgramSchedules.setServiceTypeId(valueObject.getShort("serviceTypeIds"));
			serviceProgramSchedules.setService_subScribedUserId(valueObject.getString("subscribe"));
			
			return serviceProgramSchedules;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static ServiceProgramAsignmentDetails getServiceProgramAsignmentDetailsDTO(ValueObject	valueObject) {
		ServiceProgramAsignmentDetails	asignmentDetails = new ServiceProgramAsignmentDetails();
		
		asignmentDetails.setServiceProgramId(valueObject.getLong("serviceProgramId",0));
		asignmentDetails.setVehicleTypeId(valueObject.getLong("vehicleType",0));
		asignmentDetails.setVehicleModalId(valueObject.getLong("vehicleModal",0));
		asignmentDetails.setBranchId(valueObject.getInt("branchId",0));
		asignmentDetails.setVid(valueObject.getInt("vid",0));
		asignmentDetails.setCompanyId(valueObject.getInt("companyId",0));
		asignmentDetails.setCreatedById(valueObject.getLong("userId",0));
		asignmentDetails.setCreatedOn(new Date());
		
		return asignmentDetails;
	}
}
