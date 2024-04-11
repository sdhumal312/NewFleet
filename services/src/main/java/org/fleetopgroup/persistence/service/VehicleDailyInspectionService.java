package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleInspectionStatus;
import org.fleetopgroup.persistence.bl.VehicleInspectionBL;
import org.fleetopgroup.persistence.dao.VehicleDailyInspectionRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDailyInspectionDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleDailyInspectionService implements IVehicleDailyInspectionService{
	
	@PersistenceContext	public EntityManager entityManager;
	
	private @Autowired VehicleDailyInspectionRepository VehicleDailyInspectionRepository;
	
	VehicleInspectionBL VIBL = new VehicleInspectionBL();
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
	
	
	@Override
	public void saveVehicleDailyInspection(VehicleDailyInspection assignment) throws Exception {
		try {
			VehicleDailyInspectionRepository.save(assignment);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public void saveAllVehicleDailyInspection(List<VehicleDailyInspection> assignment) throws Exception {
		try {
			VehicleDailyInspectionRepository.saveAll(assignment);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	public void getDailyInspectionList() throws Exception {
		List<VehicleInspectionSheetToParameterDto>				inspectionList			= null;
		HashMap<String, VehicleInspectionSheetToParameterDto>	map						= null;
		List<VehicleInspectionSheetToParameterDto>				finalList				= null;
		VehicleDailyInspection									dailyInsection			= null;
		try {
			inspectionList = getVehicleInspectionParameterListToInspect(DateTimeUtility.geTimeStampFromDate(new Date()));
			map = getTodaysInspectionParameterList(inspectionList);
			if(map != null) {
				finalList = new ArrayList<>();
				finalList.addAll(map.values());
			}
			if(finalList != null) {
				long validateExist = 0;
				String todaysDate = format1.format(new Date());
				Date date=format1.parse(todaysDate);
				List<VehicleDailyInspection> saveAllList = new ArrayList<>();
				for(VehicleInspectionSheetToParameterDto inspect :finalList) {
					validateExist = VehicleDailyInspectionRepository.validateDailyInspection(inspect.getVid(), date, inspect.getCompanyId());
					if(validateExist <= 0) {
						dailyInsection = VIBL.saveDailyInspectionList(inspect,date);
						saveAllList.add(dailyInsection);
					}
				}
				if(!saveAllList.isEmpty())
				saveAllVehicleDailyInspection(saveAllList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<VehicleInspectionSheetToParameterDto> getVehicleInspectionParameterListToInspect(Timestamp todaysDate) throws Exception {
		try {
				TypedQuery<Object[]> query = entityManager.createQuery(
						"SELECT VIS.inspectionSheetToParameterId, VIS.inspectionSheetId, VIS.inspectionParameterId, VIS.frequency,"
						+ " VIA.inspectionStartDate, VIA.vid, VIA.companyId "
						+ " FROM VehicleInspectionSheetToParameter AS VIS "
						+ " INNER JOIN VehicleInspectionSheetAssignment VIA ON VIA.inspectionSheetId = VIS.inspectionSheetId "
						+ " where VIA.inspectionStartDate <= '"+todaysDate+"' AND VIA.markForDelete = 0 AND VIS.markForDelete = 0 ",
						Object[].class);
				
				List<Object[]> results = query.getResultList();
				
				List<VehicleInspectionSheetToParameterDto> dtos = null;
				if (results != null && !results.isEmpty()) {
					dtos = new ArrayList<VehicleInspectionSheetToParameterDto>();
					VehicleInspectionSheetToParameterDto parameterDto = null;
					for (Object[] result : results) {
						parameterDto = new VehicleInspectionSheetToParameterDto();
						parameterDto.setInspectionSheetToParameterId((Long) result[0]);
						parameterDto.setInspectionSheetId((Long) result[1]);
						parameterDto.setInspectionParameterId((Long) result[2]);
						parameterDto.setFrequency((int) result[3]);
						parameterDto.setInspectionStartDate((Timestamp) result[4]);
						parameterDto.setVid((int) result[5]);
						parameterDto.setCompanyId((int) result[6]);
						
						long diff =  DateTimeUtility.getExactDayDiffBetweenTwoDates(parameterDto.getInspectionStartDate(), DateTimeUtility.geTimeStampFromDate(new Date()));
						if(diff != 0) {
							if(diff == parameterDto.getFrequency() || parameterDto.getFrequency() == 1) {
								dtos.add(parameterDto);
							}else if(parameterDto.getFrequency() > 0 && diff > parameterDto.getFrequency() &&  (diff % parameterDto.getFrequency()) == 0) {
								dtos.add(parameterDto);
							}
						}else {
							dtos.add(parameterDto);
						}
					}
				}
				return dtos;
				
		} catch (Exception e) {
			throw e;
		}
	}
	
	public HashMap<String, VehicleInspectionSheetToParameterDto> getTodaysInspectionParameterList(List<VehicleInspectionSheetToParameterDto> Dtos) throws Exception{
		HashMap<String, VehicleInspectionSheetToParameterDto> dtosMap = null;
		
		if(Dtos != null && !Dtos.isEmpty()) {
			dtosMap = new HashMap<String, VehicleInspectionSheetToParameterDto>();
			for(VehicleInspectionSheetToParameterDto inspection : Dtos) {
				
				if(dtosMap.containsKey(inspection.getInspectionSheetId()+"_"+inspection.getVid())) {
				} else {
					dtosMap.put(inspection.getInspectionSheetId()+"_"+inspection.getVid(), inspection);
				}
			}
		}
		
		return dtosMap;
	}
	
	@Override
	public List<VehicleDailyInspectionDto> getTodaysVehicleInspectionListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception {
		String branchQuery = " ";
		try {
			if((boolean)configuration.get("branchWiseVehicleInspection") && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				branchQuery = " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id ="+userDetail.getId()+" ";
			}
			
			String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";
			
			TypedQuery<Object[]> queryt = null;
				
				queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VDI.vid, VS.inspectionStartDate, VDI.inspectionSheetId, "
						+ " VS.assignDate, VS.assignById, V.vehicle_registration, VIS.inspectionSheetName, VDI.inspectionStatusId ,VDI.vehicleDailyInspectionId "
						+ " FROM VehicleDailyInspection AS VDI "
						+ " LEFT JOIN VehicleInspectionSheetAssignment VS ON VS.vid = VDI.vid AND VS.markForDelete=0   "
						+ " INNER JOIN Vehicle V ON V.vid = VS.vid  "
						+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId"
						+ " "+branchQuery+""
						+ " WHERE VDI.inspectionDate = '"+todaysDate+"' AND VDI.companyId = "+userDetail.getCompany_id()+" "
						+ " AND VDI.isSkiped = 0 and VDI.markForDelete = 0 AND VDI.inspectionStatusId IN ("+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN+")",
						Object[].class);
			List<Object[]> results = queryt.getResultList();
			
			List<VehicleDailyInspectionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<VehicleDailyInspectionDto>();
				VehicleDailyInspectionDto asingment = null;
				for (Object[] result : results) {
					asingment = new VehicleDailyInspectionDto();
					
					asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
					asingment.setVid((Integer) result[1]);
					asingment.setInspectionStartDate((Timestamp) result[2]);
					asingment.setInspectionSheetId((Long) result[3]);
					asingment.setAssignDate((Timestamp) result[4]);
					asingment.setAssignById((Long) result[5]);
					asingment.setVehicle_registration((String) result[6]);
					asingment.setInspectionSheetName((String) result[7]);
					if(result[8] != null) {
						asingment.setInspectionStatusId((short) result[8]);
						asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
					}else {
						asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
					}
					
					asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
					asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));
					asingment.setVehicleDailyInspectionId((Long) result[9]);
					Dtos.add(asingment);
				}
			}
			return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<VehicleDailyInspectionDto> getTodaysInspectedListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception {
		
		 String branchQuery = " ";
		 try {
			 if((boolean)configuration.get("branchWiseVehicleInspection") && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				 branchQuery = " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id ="+userDetail.getId()+" ";
			 }
			 String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";

			 TypedQuery<Object[]> queryt = null;
			 

			 queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VDI.vid, VS.inspectionStartDate, VDI.inspectionSheetId, "
					 + " VS.assignDate, VS.assignById, V.vehicle_registration, VIS.inspectionSheetName, VDI.inspectionStatusId, VCD.completionDetailsId "
					 + " FROM VehicleDailyInspection AS VDI "
					 + " INNER JOIN VehicleInspectionSheetAssignment VS ON VS.vid = VDI.vid "
					 + " INNER JOIN Vehicle V ON V.vid = VS.vid "
					 + " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId "
					 + " "+branchQuery+""
					 + " INNER JOIN VehicleInspectionCompletionDetails VCD ON VCD.inspectionSheetId = VDI.inspectionSheetId AND VCD.vid = VDI.vid "
					 + "  AND VCD.markForDelete = 0 AND VCD.vehicleInspctionSheetAsingmentId = VS.vehicleInspctionSheetAsingmentId "
					 + " AND VCD.inspectionDate = '"+todaysDate+"' "
					 + " WHERE VDI.inspectionDate = '"+todaysDate+"' AND VDI.companyId = "+userDetail.getCompany_id()+"  AND "
					 + " VDI.inspectionStatusId = "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED +" ",
					 Object[].class);
			 List<Object[]> results = queryt.getResultList();

			 List<VehicleDailyInspectionDto> Dtos = null;
			 if (results != null && !results.isEmpty()) {

				 Dtos = new ArrayList<VehicleDailyInspectionDto>();
				 VehicleDailyInspectionDto asingment = null;
				 for (Object[] result : results) {
					 asingment = new VehicleDailyInspectionDto();

					 asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
					 asingment.setVid((Integer) result[1]);
					 asingment.setInspectionStartDate((Timestamp) result[2]);
					 asingment.setInspectionSheetId((Long) result[3]);
					 asingment.setAssignDate((Timestamp) result[4]);
					 asingment.setAssignById((Long) result[5]);
					 asingment.setVehicle_registration((String) result[6]);
					 asingment.setInspectionSheetName((String) result[7]);
					 if(result[8] != null) {
						 asingment.setInspectionStatusId((short) result[8]);
						 asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
					 }else {
						 asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
					 }
					 asingment.setCompletionDetailsId((Long) result[9]);

					 asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
					 asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));

					 Dtos.add(asingment);
				 }
			 }
			 return Dtos;
		 } catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
			
	}
	
	@Override
	public List<VehicleDailyInspectionDto> getTodaysInspectionPendingListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception {
		
		String branchQuery = " ";
		try {
			if((boolean)configuration.get("branchWiseVehicleInspection")  && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				branchQuery = " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id ="+userDetail.getId()+" ";

			}
			String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";

			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VDI.vid, VS.inspectionStartDate, VDI.inspectionSheetId, "
					+ " VS.assignDate, VS.assignById, V.vehicle_registration, VIS.inspectionSheetName, VDI.inspectionStatusId, VCD.completionDetailsId "
					+ " FROM VehicleDailyInspection AS VDI "
					+ " INNER JOIN VehicleInspectionSheetAssignment VS ON VS.vid = VDI.vid AND VS.markForDelete = 0 "
					+ " INNER JOIN Vehicle V ON V.vid = VS.vid AND VS.markForDelete = 0 "
					+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId"
					+ " "+branchQuery+""
					+ " LEFT JOIN VehicleInspectionCompletionDetails VCD ON VCD.inspectionSheetId = VDI.inspectionSheetId AND VCD.vid = VDI.vid AND VCD.markForDelete = 0 "
					+ " AND VCD.inspectionDate = '"+todaysDate+"' "
					+ " WHERE VDI.inspectionDate = '"+todaysDate+"' AND VDI.companyId = "+userDetail.getCompany_id()+"  AND VDI.isSkiped = 0 and VDI.markForDelete = 0  AND "
					+ " VDI.inspectionStatusId IN ("+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN+", "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_SAVED+")",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<VehicleDailyInspectionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {

				Dtos = new ArrayList<VehicleDailyInspectionDto>();
				VehicleDailyInspectionDto asingment = null;
				for (Object[] result : results) {
					asingment = new VehicleDailyInspectionDto();

					asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
					asingment.setVid((Integer) result[1]);
					asingment.setInspectionStartDate((Timestamp) result[2]);
					asingment.setInspectionSheetId((Long) result[3]);
					asingment.setAssignDate((Timestamp) result[4]);
					asingment.setAssignById((Long) result[5]);
					asingment.setVehicle_registration((String) result[6]);
					asingment.setInspectionSheetName((String) result[7]);
					if(result[8] != null) {
						asingment.setInspectionStatusId((short) result[8]);
						asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
					}else {
						asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
					}
					asingment.setCompletionDetailsId((Long) result[9]);

					asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
					asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));

					Dtos.add(asingment);
				}
			}
			return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<VehicleDailyInspectionDto> getTodaysSavedInspectedListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception {
		
		String branchQuery = " ";
		try {
			if((boolean)configuration.get("branchWiseVehicleInspection")  && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				branchQuery = " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id ="+userDetail.getId()+" ";
				
			}
			String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";
			
			TypedQuery<Object[]> queryt = null;
				
				queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VDI.vid, VS.inspectionStartDate, VDI.inspectionSheetId, "
						+ " VS.assignDate, VS.assignById, V.vehicle_registration, VIS.inspectionSheetName, VDI.inspectionStatusId, VCD.completionDetailsId "
						+ " FROM VehicleDailyInspection AS VDI "
						+ " INNER JOIN VehicleInspectionSheetAssignment VS ON VS.vid = VDI.vid  "
						+ " INNER JOIN Vehicle V ON V.vid = VS.vid "
						+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId "
						+ " "+branchQuery+""
						+ " INNER JOIN VehicleInspectionCompletionDetails VCD ON VCD.inspectionSheetId = VDI.inspectionSheetId AND VCD.vid = VDI.vid AND VCD.markForDelete = 0 "
						+ " AND VCD.vehicleInspctionSheetAsingmentId = VS.vehicleInspctionSheetAsingmentId AND VCD.inspectionDate = '"+todaysDate+"' "
						+ " WHERE VDI.inspectionDate = '"+todaysDate+"' AND VDI.companyId = "+userDetail.getCompany_id()+"  and VDI.markForDelete = 0  AND "
						+ " VDI.inspectionStatusId = "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_SAVED+" ",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<VehicleDailyInspectionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<VehicleDailyInspectionDto>();
				VehicleDailyInspectionDto asingment = null;
				for (Object[] result : results) {
					asingment = new VehicleDailyInspectionDto();
					
					asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
					asingment.setVid((Integer) result[1]);
					asingment.setInspectionStartDate((Timestamp) result[2]);
					asingment.setInspectionSheetId((Long) result[3]);
					asingment.setAssignDate((Timestamp) result[4]);
					asingment.setAssignById((Long) result[5]);
					asingment.setVehicle_registration((String) result[6]);
					asingment.setInspectionSheetName((String) result[7]);
					if(result[8] != null) {
						asingment.setInspectionStatusId((short) result[8]);
						asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
					}else {
						asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
					}
					asingment.setCompletionDetailsId((Long) result[9]);
					
					asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
					asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));

					Dtos.add(asingment);
				}
			}
			return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getMissedInspectionListByVehicle(ValueObject valueObject) throws Exception {
		List<VehicleDailyInspectionDto>		missedInspectionByVehicle	= null;
		CustomUserDetails					userDetails					= null;
		String								dateRange					= null;
		int									vid							= 0;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vid         = valueObject.getInt("vid");
			dateRange	= valueObject.getString("dateRange");
			
			Date date = format2.parse(dateRange);
			
			String inspectionDate = format1.format(date) +" 00:00:00";
			
			missedInspectionByVehicle = getMissedInspectionList(vid, inspectionDate, userDetails.getCompany_id());
			
			if(missedInspectionByVehicle != null)
			valueObject.put("missedInspectionByVehicle", missedInspectionByVehicle);
			valueObject.put("inspectionDate", format1.format(date));
			

			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleDailyInspectionDto> getMissedInspectionList(int vid, String inspectionDate, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> queryt = null;
			
			queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VDI.vid, VS.inspectionStartDate, VDI.inspectionSheetId, "
					+ " VS.assignDate, VS.assignById, V.vehicle_registration, VIS.inspectionSheetName, VDI.inspectionStatusId, VCD.completionDetailsId "
					+ " FROM VehicleDailyInspection AS VDI "
					+ " INNER JOIN VehicleInspectionSheetAssignment VS ON VS.vid = VDI.vid AND VS.markForDelete = 0 "
					+ " INNER JOIN Vehicle V ON V.vid = VS.vid AND VS.markForDelete = 0 "
					+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId"
					+ " LEFT JOIN VehicleInspectionCompletionDetails VCD ON VCD.inspectionSheetId = VDI.inspectionSheetId AND VCD.vid = VDI.vid AND VCD.markForDelete = 0 "
					+ " AND VCD.inspectionDate = '"+inspectionDate+"' "
					+ " WHERE VDI.inspectionDate = '"+inspectionDate+"' AND VDI.companyId = "+companyId+"  and VDI.markForDelete = 0 AND VDI.vid ="+vid+" AND "
					+ " VDI.inspectionStatusId IN ("+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN+", "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_SAVED+")",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleDailyInspectionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			
			Dtos = new ArrayList<VehicleDailyInspectionDto>();
			VehicleDailyInspectionDto asingment = null;
			for (Object[] result : results) {
				asingment = new VehicleDailyInspectionDto();
				
				asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
				asingment.setVid((Integer) result[1]);
				asingment.setInspectionStartDate((Timestamp) result[2]);
				asingment.setInspectionSheetId((Long) result[3]);
				asingment.setAssignDate((Timestamp) result[4]);
				asingment.setAssignById((Long) result[5]);
				asingment.setVehicle_registration((String) result[6]);
				asingment.setInspectionSheetName((String) result[7]);
				if(result[8] != null) {
					asingment.setInspectionStatusId((short) result[8]);
					asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
				}else {
					asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
				}
				asingment.setCompletionDetailsId((Long) result[9]);
				
				asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
				asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));

				Dtos.add(asingment);
			}
		}
		return Dtos;
	}
	
	@Override
	public ValueObject runDailyInspection(ValueObject valueObject) throws Exception {
		getDailyInspectionList();
		return valueObject;
	}
	
	@Override
	public List<VehicleDto> getMissedInspectedVehicleList(CustomUserDetails userDetails, Collection<GrantedAuthority> permission,
			HashMap<String, Object> configuration) throws Exception {
		
		TypedQuery<Object[]> queryt = null;
		String branchQuery = " ";
		try {
			if((boolean)configuration.get("branchWiseVehicleInspection")  && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				branchQuery = " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id ="+userDetails.getId()+" ";
			}
			queryt = entityManager.createQuery("SELECT DISTINCT VDI.vid, V.vehicle_registration"
					+ " FROM VehicleDailyInspection AS VDI "
					+ " INNER JOIN Vehicle V ON V.vid = VDI.vid AND VDI.markForDelete = 0 "
					+ " "+branchQuery+""
					+ " WHERE VDI.companyId = "+userDetails.getCompany_id()+" AND VDI.isSkiped = 0  and VDI.markForDelete = 0 AND "
					+ " VDI.inspectionStatusId IN ("+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN+", "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_SAVED+")",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto asingment = null;
			for (Object[] result : results) {
				asingment = new VehicleDto();
				
				asingment.setVid((Integer) result[0]);
				asingment.setVehicle_registration((String) result[1]);

				Dtos.add(asingment);
			}
		}
		return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
			
	}
	
	@Override
	public ValueObject  getMissedInspectedDatesByVId(ValueObject	valueObject) throws Exception {
		ValueObject	object = new ValueObject();
		TypedQuery<Object[]> queryt = null;
			
			queryt = entityManager.createQuery("SELECT DISTINCT VDI.inspectionDate, VDI.vid"
					+ " FROM VehicleDailyInspection AS VDI "
					+ " WHERE VDI.vid = "+valueObject.getInt("vid",0)+" AND VDI.companyId = "+valueObject.getInt("companyId",0)+" AND VDI.isSkiped = 0 and VDI.markForDelete = 0 AND "
					+ " VDI.inspectionStatusId IN "
					+ "("+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN+", "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_SAVED+") order by VDI.inspectionDate desc",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<String> Dtos = null;
		if (results != null && !results.isEmpty()) {
			
			Dtos = new ArrayList<String>();
			for (Object[] result : results) {
				Dtos.add(format2.format((Timestamp)result[0]));
			}
		}
		object.put("dateLisst", Dtos);
		
		return object;
	}


@Transactional
	public ValueObject  skipInspectionSheet(ValueObject	valueObject) throws Exception {
		CustomUserDetails userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			skipInspection(valueObject.getLong("vehicleDailyInspectionId",0),valueObject.getString("InRemark"," "), userDetails.getCompany_id());
			valueObject.put("success",true);
			return valueObject;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

@Transactional
public Integer skipInspection(Long dailyInspectionId,String skipedRemark ,Integer companyId) throws Exception {
	
	return VehicleDailyInspectionRepository.updateSkipedStatus(dailyInspectionId,skipedRemark, companyId);
}

public List<VehicleDailyInspectionDto> getTodaysInspectionSkipedList(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception {
	String branchQuery = " ";
	try {
		if((boolean)configuration.get("branchWiseVehicleInspection") && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
			branchQuery = " INNER JOIN UserProfile AS UP ON UP.branch_id = V.branchId AND UP.user_id ="+userDetail.getId()+" ";
		}
		
		String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";
		
		TypedQuery<Object[]> queryt = null;
			
			queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VDI.vid, VS.inspectionStartDate, VDI.inspectionSheetId, "
					+ " VS.assignDate, VS.assignById, V.vehicle_registration, VIS.inspectionSheetName, VDI.inspectionStatusId ,VDI.vehicleDailyInspectionId,VDI.skipedRemark "
					+ " FROM VehicleDailyInspection AS VDI "
					+ " LEFT JOIN VehicleInspectionSheetAssignment VS ON VS.vid = VDI.vid AND VS.markForDelete=0   "
					+ " INNER JOIN Vehicle V ON V.vid = VS.vid  "
					+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VDI.inspectionSheetId"
					+ " "+branchQuery+""
					+ " WHERE VDI.inspectionDate = '"+todaysDate+"' AND VDI.companyId = "+userDetail.getCompany_id()+" "
					+ " AND VDI.isSkiped = 1 and VDI.markForDelete = 0 AND VDI.inspectionStatusId IN ("+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN+")",
					Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		List<VehicleDailyInspectionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			
			Dtos = new ArrayList<>();
			VehicleDailyInspectionDto asingment = null;
			for (Object[] result : results) {
				asingment = new VehicleDailyInspectionDto();
				
				asingment.setVehicleInspctionSheetAsingmentId((Long) result[0]);
				asingment.setVid((Integer) result[1]);
				asingment.setInspectionStartDate((Timestamp) result[2]);
				asingment.setInspectionSheetId((Long) result[3]);
				asingment.setAssignDate((Timestamp) result[4]);
				asingment.setAssignById((Long) result[5]);
				asingment.setVehicle_registration((String) result[6]);
				asingment.setInspectionSheetName((String) result[7]);
				if(result[8] != null) {
					asingment.setInspectionStatusId((short) result[8]);
					asingment.setInspectionStatusName(VehicleInspectionStatus.getVehicleInspectionStatusName((short) result[8]));
				}else {
					asingment.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_OPEN_NAME);
				}
				
				asingment.setInspectionStartDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getInspectionStartDate(), DateTimeUtility.DD_MM_YYYY));
				asingment.setAssignDateStr(DateTimeUtility.getDateFromTimeStamp(asingment.getAssignDate(), DateTimeUtility.DD_MM_YYYY));
				asingment.setVehicleDailyInspectionId((Long) result[9]);
				asingment.setSkipedRemark((String) result[10]);
				Dtos.add(asingment);
			}
		}
		return Dtos;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}

}