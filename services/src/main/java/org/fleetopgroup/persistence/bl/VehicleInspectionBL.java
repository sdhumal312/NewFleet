package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.fleetopgroup.constant.VehicleInspectionStatus;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetRepository;
import org.fleetopgroup.persistence.document.InspectionToParameterDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InspectionCompletionToParameterDto;
import org.fleetopgroup.persistence.dto.InspectionParameterDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionReportDetails;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.dto.VehicleTypeAssignmebtDetailsDto;
import org.fleetopgroup.persistence.model.InspectionCompletionToParameter;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;
import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.service.VehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.service.VehicleTypeAssignmentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeAssignmentDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class VehicleInspectionBL {
	
	SimpleDateFormat 			dateFormat 				= new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat			dateViewFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	private @Autowired	IVehicleInspectionSheetService					vehicleInspectionSheetService;
	
	@Autowired private VehicleInspectionSheetRepository					vehicleInspectionSheetRepository;
	
	@Autowired private IVehicleInspctionSheetAsingmentService    vehicleInspctionSheetAsingmentService;
	
	@Autowired private IVehicleTypeAssignmentDetailsService    vehicleTypeAssignmentDetailsService;
	
	
	public VehicleInspectionSheet getVehicleInspectionSheet(VehicleInspectionSheet	inspectionSheet, CustomUserDetails	userDetails) throws Exception {
		
		try {
			
			inspectionSheet.setCreatedById(userDetails.getId());
			inspectionSheet.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			inspectionSheet.setLastModifiedById(userDetails.getId());
			inspectionSheet.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			inspectionSheet.setNoOfVehicleAsigned(0);
			inspectionSheet.setCompanyId(userDetails.getCompany_id());
			inspectionSheet.setVehicleTypeId("");
			
			return inspectionSheet;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<VehicleInspectionSheetDto>  getVehicleInspectionSheetDtoList(HashMap<Long, VehicleGroup>	vehicleGroupHM, List<VehicleInspectionSheet>	inspectionSheetList) throws Exception{
		List<VehicleInspectionSheetDto>		inspectionSheetDtoList		= null;
		VehicleInspectionSheetDto			vehicleInspectionSheetDto	= null;
		String[] 							vehicleGroupArray			= null;
		try {
				if(inspectionSheetList != null && !inspectionSheetList.isEmpty()) {
					inspectionSheetDtoList	= new ArrayList<>();
					for(VehicleInspectionSheet  inspectionSheet : inspectionSheetList) {
						vehicleInspectionSheetDto	= new VehicleInspectionSheetDto();
						
						vehicleInspectionSheetDto.setVehicleInspectionSheetId(inspectionSheet.getVehicleInspectionSheetId());
						vehicleInspectionSheetDto.setInspectionSheetName(inspectionSheet.getInspectionSheetName());
						vehicleInspectionSheetDto.setNoOfVehicleAsigned(inspectionSheet.getNoOfVehicleAsigned());
						
							if(inspectionSheet.getVehicleGroupId() != null) {
								vehicleGroupArray	= inspectionSheet.getVehicleGroupId().split(",");
								String								vehicleGroup				= "";
									for (int i = 0; i < vehicleGroupArray.length; i++) {
										if(vehicleGroupHM != null && vehicleGroupHM.get(Long.parseLong(vehicleGroupArray[i]+"")) != null)
											vehicleGroup	+= vehicleGroupHM.get(Long.parseLong(vehicleGroupArray[i]+"")).getvGroup() +" , " ;
									}
								vehicleInspectionSheetDto.setVehicleGroup(vehicleGroup);
							}
							inspectionSheetDtoList.add(vehicleInspectionSheetDto);
						//vehicleInspectionSheetDto.set
					}
				}
			return inspectionSheetDtoList;
		} catch (Exception e) {
			throw e;
		}finally {
			inspectionSheetDtoList		= null;
			vehicleInspectionSheetDto	= null;
		}
	}
	
	public	VehicleInspectionSheetDto  getVehicleInspectionSheetDto(HashMap<Long, VehicleGroup>	vehicleGroupHM, VehicleInspectionSheet	inspectionSheet) throws Exception{
		VehicleInspectionSheetDto			vehicleInspectionSheetDto	= null;
		String[] 							vehicleGroupArray			= null;
		
		try {
				if(inspectionSheet != null) {
						vehicleInspectionSheetDto	= new VehicleInspectionSheetDto();
						
						vehicleInspectionSheetDto.setVehicleInspectionSheetId(inspectionSheet.getVehicleInspectionSheetId());
						vehicleInspectionSheetDto.setInspectionSheetName(inspectionSheet.getInspectionSheetName());
						vehicleInspectionSheetDto.setNoOfVehicleAsigned(inspectionSheet.getNoOfVehicleAsigned());
						
							if(inspectionSheet.getVehicleGroupId() != null) {
								vehicleGroupArray	= inspectionSheet.getVehicleGroupId().split(",");
								String								vehicleGroup				= "";
									for (int i = 0; i < vehicleGroupArray.length; i++) {
										vehicleGroup	+= vehicleGroupHM.get(Long.parseLong(vehicleGroupArray[i]+"")).getvGroup() +" , " ;
									}
								vehicleInspectionSheetDto.setVehicleGroup(vehicleGroup);
							}
				}
			return vehicleInspectionSheetDto;
		} catch (Exception e) {
			throw e;
		}finally {
			inspectionSheet				= null;
			vehicleInspectionSheetDto	= null;
		}
	}
	
	public VehicleInspectionCompletionDetails getVehicleInspectionCompletionDetailsModel(VehicleInspectionCompletionDetails details , CustomUserDetails	userDetails, UserProfileDto	profile, String dateRange) throws Exception{
		try {
				
			details.setCompletionDateTime(DateTimeUtility.getCurrentTimeStamp());
			details.setInspectedById(userDetails.getId());
			details.setCompanyId(userDetails.getCompany_id());
			details.setBranchId(profile.getBranch_id());
			details.setInspectionDate(DateTimeUtility.getTimeStamp(dateRange, DateTimeUtility.YYYY_MM_DD));
			
			return details;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public HashMap<Long, InspectionCompletionToParameter> getInspectionCompletionToParameterHM(List<InspectionCompletionToParameter>	toParameterList)throws Exception{
		HashMap<Long, InspectionCompletionToParameter> 		hashMap		= null;
		try {
				hashMap	= new HashMap<>();
				
				if(toParameterList != null && !toParameterList.isEmpty()) {
					for(InspectionCompletionToParameter parameter : toParameterList) {
						hashMap.put(parameter.getCompletionToParameterId(), parameter);
					}
				}
			
			return hashMap;
		} catch (Exception e) {
			throw e;
		}
	}
	

	// save the InspectionParameter Model
		public InspectionParameter prepareModel(InspectionParameterDto inspectionParameterDto) {
			Date 		currentDateUpdate 	= new Date();
			Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			InspectionParameter inPar = new InspectionParameter();
			
			inPar.setParameterName(inspectionParameterDto.getParameterName());
			inPar.setCreatedOn(toDate);
			inPar.setLastModifiedOn(toDate);
			inPar.setCompanyId(userDetails.getCompany_id());
			
							
			return inPar;
		}
		
		// MasterParts Photo
		public List<org.fleetopgroup.persistence.document.InspectionParameterDocument> prepareListMasterParameterPhoto(List<org.fleetopgroup.persistence.document.InspectionParameterDocument> paramaterPhoto) {
			List<org.fleetopgroup.persistence.document.InspectionParameterDocument> beans = null;
			if (paramaterPhoto != null && !paramaterPhoto.isEmpty()) {
				beans = new ArrayList<>();
				org.fleetopgroup.persistence.document.InspectionParameterDocument MasterParameter = null;

				for (org.fleetopgroup.persistence.document.InspectionParameterDocument MasterParameterBean : paramaterPhoto) {
					MasterParameter = new org.fleetopgroup.persistence.document.InspectionParameterDocument();
					if(MasterParameterBean != null) {
						
						if(MasterParameterBean.getId()!= null) {
							MasterParameter.setId(MasterParameterBean.getId());
						}
						MasterParameter.setFilename(MasterParameterBean.getFilename());
						MasterParameter.setContent(MasterParameterBean.getContent());
						MasterParameter.setContentType(MasterParameterBean.getContentType());
						MasterParameter.setCreatedById(MasterParameterBean.getCreatedById());
						MasterParameter.setCompanyId(MasterParameterBean.getCompanyId());
						beans.add(MasterParameter);
					}
					
				}
			}
			return beans;
		}
		
		public VehicleInspectionReportDetails	getVehicleInspectionReportDetails(List<InspectionCompletionToParameterDto>		parameterList, ValueObject	valueObject) throws Exception{
			VehicleInspectionReportDetails		details		= null;
			try {
					if(parameterList != null && !parameterList.isEmpty()) {
						details		= new VehicleInspectionReportDetails();
						details.setNoOfRecord(parameterList.size());
						
						for (InspectionCompletionToParameterDto		toParameterDto : parameterList) {
								
								if(toParameterDto.getIsInspectionSuccess() == 1) {
									details.setNoOfPassRecord(details.getNoOfPassRecord() + 1);
								}else if(toParameterDto.getIsInspectionSuccess() == 2) {
									details.setNoOfFailRecord(details.getNoOfFailRecord() + 1);
								}else {
									details.setNoOfNotTestedRecord(details.getNoOfNotTestedRecord() + 1);
								}
						}
						
						details.setNoOfTestedRecord(details.getNoOfRecord() - details.getNoOfNotTestedRecord());
						
						double passPer		= (double) (details.getNoOfPassRecord() * 100 ) / details.getNoOfTestedRecord();
						double failPer		= (double) (details.getNoOfFailRecord() * 100 )/ details.getNoOfTestedRecord();
						double notTestPer	= (double) (details.getNoOfNotTestedRecord() * 100 )/ details.getNoOfRecord();
						
						if(Double.isNaN(passPer)) {
							details.setTestPassPercentage(0.0);
						} else {
							details.setTestPassPercentage(passPer);
						}
						
						if(Double.isNaN(failPer)) {
							details.setTestFailPercentage(0.0);
						} else {
							details.setTestFailPercentage(failPer);
						}
						
						details.setNotTestPercentage(notTestPer);
						
					}
				return details;
			} catch (Exception e) {
				throw e;
			}
		}
		
		public VehicleDailyInspection saveDailyInspectionList(VehicleInspectionSheetToParameterDto parameterList,Date date) throws Exception {

			VehicleDailyInspection dailyInspection = new VehicleDailyInspection();
			
			dailyInspection.setInspectionSheetId(parameterList.getInspectionSheetId());
			dailyInspection.setVid(parameterList.getVid());
			dailyInspection.setInspectionDate(date);
			dailyInspection.setInspectionStatusId(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN);
			dailyInspection.setCompanyId(parameterList.getCompanyId());
							
			return dailyInspection;
		}
		
		
		public List<VehicleInspectionSheetDto>  getVehicleTypeInspectionSheetDtoList(HashMap<Long, VehicleType>	vehicleTypeHM, List<VehicleInspectionSheet>	inspectionSheetList) throws Exception{
			List<VehicleInspectionSheetDto>		inspectionSheetDtoList		= null;
			VehicleInspectionSheetDto			vehicleInspectionSheetDto	= null;
			String[] 							vehicleGroupArray			= null;
			try {
					if(inspectionSheetList != null && !inspectionSheetList.isEmpty()) {
						inspectionSheetDtoList	= new ArrayList<>();
						for(VehicleInspectionSheet  inspectionSheet : inspectionSheetList) {
							vehicleInspectionSheetDto	= new VehicleInspectionSheetDto();
							
							vehicleInspectionSheetDto.setVehicleInspectionSheetId(inspectionSheet.getVehicleInspectionSheetId());
							vehicleInspectionSheetDto.setInspectionSheetName(inspectionSheet.getInspectionSheetName());
							vehicleInspectionSheetDto.setNoOfVehicleAsigned(inspectionSheet.getNoOfVehicleAsigned());
							
								if(inspectionSheet.getVehicleTypeId() != null) {
									vehicleGroupArray	= inspectionSheet.getVehicleTypeId().split(",");
									String								vehicleGroup				= "";
										for (int i = 0; i < vehicleGroupArray.length; i++) {
											if(!(vehicleGroupArray[i].trim().equals(""))&&vehicleTypeHM != null && vehicleTypeHM.get(Long.parseLong(vehicleGroupArray[i]+"")) != null)
												vehicleGroup	+= (vehicleTypeHM.get(Long.parseLong(vehicleGroupArray[i]+""))).getVtype()  +" , " ;
										}
									vehicleInspectionSheetDto.setVehicleGroup(vehicleGroup);
								}
								inspectionSheetDtoList.add(vehicleInspectionSheetDto);
						}
					}
				return inspectionSheetDtoList;
			} catch (Exception e) {
				throw e;
			}finally {
				inspectionSheetDtoList		= null;
				vehicleInspectionSheetDto	= null;
			}
		}
		
	public Map<Long, List<InspectionToParameterDocument>> getImageHashMap(List <InspectionToParameterDocument> imageList ){
		 Map<Long, List<InspectionToParameterDocument>> docMap = null;
		 if(imageList != null && !imageList.isEmpty())
		 docMap=imageList.parallelStream().collect(Collectors.groupingBy(InspectionToParameterDocument::get_id));
		
		return docMap;
	}
		
}
