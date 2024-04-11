package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleInspectionStatus;
import org.fleetopgroup.persistence.dao.VehicleInspectionCompletionDetailsRepository;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionCompletionDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleInspectionCompletionDetailsService implements IVehicleInspectionCompletionDetailsService {
	
	@PersistenceContext	public EntityManager entityManager;
	
	private @Autowired	VehicleInspectionCompletionDetailsRepository		vehicleInspectionCompletionDetailsRepository;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public void saveVehicleInspectionCompletionDetails(VehicleInspectionCompletionDetails details) throws Exception {
		try {
			
			vehicleInspectionCompletionDetailsRepository.save(details);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public long getTodaysVehicleInspectedCount(Integer companyId, Timestamp inspectionStartDate) throws Exception {
		return vehicleInspectionCompletionDetailsRepository.getTodaysVehicleInspectionCount(companyId, inspectionStartDate);
	}
	
	//This is the method with data from old Table and not in use. New method name is getTodaysInspectedListNew in VehicleDailyInspectionService.
	@Override
	public List<VehicleInspectionCompletionDetailsDto> getTodaysInspectedList(Integer companyId,
			Timestamp inspectionStartDate) throws Exception {
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery(" SELECT ICP.completionDetailsId, ICP.vid, V.vehicle_registration, VA.vehicleInspctionSheetAsingmentId "
				+ " FROM VehicleInspectionCompletionDetails ICP "
				+ " INNER JOIN Vehicle V ON V.vid = ICP.vid"
				+ " INNER JOIN VehicleInspectionSheetAssignment VA ON VA.vid = ICP.vid AND VA.inspectionSheetId = ICP.inspectionSheetId AND VA.markForDelete = 0"
				+ " WHERE ICP.companyId = "+companyId+" AND ICP.inspectionDate = '"+inspectionStartDate+"' AND ICP.inspectionStatusId = 2 AND ICP.markForDelete = 0",
				Object[].class);
	List<Object[]> results = queryt.getResultList();

	List<VehicleInspectionCompletionDetailsDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		
		Dtos = new ArrayList<VehicleInspectionCompletionDetailsDto>();
		VehicleInspectionCompletionDetailsDto parameterDto = null;
		for (Object[] result : results) {
			parameterDto = new VehicleInspectionCompletionDetailsDto();
			
			parameterDto.setCompletionDetailsId((Long) result[0]);
			parameterDto.setVid( (Integer) result[1]);
			parameterDto.setVehicle_registration( (String) result[2]);
			parameterDto.setVehicleInspctionSheetAsingmentId( (Long) result[3]);
			parameterDto.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED_NAME);

			Dtos.add(parameterDto);
		}
	}
	return Dtos;
}
	
	//This is the method with data from old Table and not in use. New method name is getTodaysInspectionPendingListNew in VehicleDailyInspectionService.
	@Override
	public List<VehicleInspctionSheetAsingmentDto> getTodaysInspectionPendingList(Integer companyId,
			Timestamp inspectionStartDate) throws Exception {
String todaysDate	= DateTimeUtility.getCurrentDate()+" 00:00:00";
		
		TypedQuery<Object[]> queryt = null;
			
			queryt = entityManager.createQuery("SELECT VS.vehicleInspctionSheetAsingmentId, VS.vid, VS.inspectionStartDate, VS.inspectionSheetId, VS.assignDate, VS.assignById,"
					+ " V.vehicle_registration, VIS.inspectionSheetName, VCD.inspectionStatusId, VCD.completionDetailsId"
					+ " FROM VehicleInspectionSheetAssignment AS VS "
					+ " INNER JOIN Vehicle V ON V.vid = VS.vid"
					+ " INNER JOIN VehicleInspectionSheet VIS ON VIS.vehicleInspectionSheetId = VS.inspectionSheetId"
					+ " LEFT JOIN VehicleInspectionCompletionDetails VCD ON VCD.inspectionSheetId = VS.inspectionSheetId AND VCD.vid = VS.vid AND VCD.markForDelete = 0 "
					+ " AND VCD.inspectionDate = '"+todaysDate+"' "
					+ " where VS.inspectionStartDate <= '"+inspectionStartDate+"' AND VS.companyId = "+companyId+"  and VS.markForDelete = 0 ",
					Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleInspctionSheetAsingmentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			
			Dtos = new ArrayList<VehicleInspctionSheetAsingmentDto>();
			VehicleInspctionSheetAsingmentDto asingment = null;
			for (Object[] result : results) {
				asingment = new VehicleInspctionSheetAsingmentDto();
				
				
				if(result[8] == null || (short)result[8] == 1 ) {
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
		}
		return Dtos;
}
	
	//This is the method with data from old Table and not in use. New method name is getTodaysSavedInspectedListNew in VehicleDailyInspectionService.
	@Override
	public List<VehicleInspectionCompletionDetailsDto> getTodaysSavedInspectedList(Integer companyId,
			Timestamp inspectionStartDate) throws Exception {
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery(" SELECT ICP.completionDetailsId, ICP.vid, V.vehicle_registration, VA.vehicleInspctionSheetAsingmentId "
				+ " FROM VehicleInspectionCompletionDetails ICP "
				+ " INNER JOIN Vehicle V ON V.vid = ICP.vid"
				+ " INNER JOIN VehicleInspectionSheetAssignment VA ON VA.vid = ICP.vid AND VA.inspectionSheetId = ICP.inspectionSheetId AND VA.markForDelete = 0"
				+ " WHERE ICP.companyId = "+companyId+" AND ICP.inspectionDate = '"+inspectionStartDate+"' AND ICP.inspectionStatusId = 1 AND ICP.markForDelete = 0",
				Object[].class);
	List<Object[]> results = queryt.getResultList();

	List<VehicleInspectionCompletionDetailsDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		
		Dtos = new ArrayList<VehicleInspectionCompletionDetailsDto>();
		VehicleInspectionCompletionDetailsDto parameterDto = null;
		for (Object[] result : results) {
			parameterDto = new VehicleInspectionCompletionDetailsDto();
			
			parameterDto.setCompletionDetailsId((Long) result[0]);
			parameterDto.setVid( (Integer) result[1]);
			parameterDto.setVehicle_registration( (String) result[2]);
			parameterDto.setVehicleInspctionSheetAsingmentId( (Long) result[3]);
			parameterDto.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_SAVED_NAME);

			Dtos.add(parameterDto);
		}
	}
	return Dtos;
}
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseInspectionPenaltyByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception {
		Object[]	results = null;
		try {
		
			Query queryt = entityManager
					.createQuery("  SELECT  SUM(MVE.totalPenalty),MVE.companyId  " + 
							" FROM VehicleInspectionCompletionDetails MVE " + 
							" where MVE.vid = "+vid+" AND MVE.completionDateTime between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+" " + 
							" AND MVE.markForDelete = 0 AND MVE.inspectionStatusId = "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED+" AND MVE.totalPenalty > 0 ");
			
			  try {
				  results = (Object[]) queryt.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
			}
			List<MonthWiseVehicleExpenseDto>	list	= null;
			if(results != null && results[0] != null ) {
				list	=	new ArrayList<>();
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					mExpenseDto.setExpenseAmount((Double) results[0]);
					mExpenseDto.setExpenseType((short)VehicleExpenseTypeConstant.INSPECTION_PENALTY);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					list.add(mExpenseDto);
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseInspectionPenaltyByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception {
		Object[]	results = null;
		try {
		
			Query queryt = entityManager
					.createQuery("SELECT  SUM(MVE.totalPenalty),MVE.companyId  " + 
							" FROM VehicleInspectionCompletionDetails MVE " + 
							" where MVE.vid = "+vid+" AND MVE.completionDateTime between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+" " + 
							" AND MVE.markForDelete = 0 AND MVE.inspectionStatusId = "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED+" AND MVE.totalPenalty > 0 ");
			
			  try {
				  results = (Object[]) queryt.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
			}
			List<DateWiseVehicleExpenseDto>	list	= null;
			if(results != null && results[0] != null ) {
				list	=	new ArrayList<>();
				DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					mExpenseDto.setExpenseAmount((Double) results[0]);
					mExpenseDto.setExpenseType((short)VehicleExpenseTypeConstant.INSPECTION_PENALTY);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					list.add(mExpenseDto);
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public Map<Integer,Double> getMonthWiseInspectionPenaltyByVGroup(Long vehicleGroupId ,Timestamp fromdate ,Timestamp toDate ,int companyId) throws Exception{
		TypedQuery<Object[]> queryt=null;
		List<Object[]> results = null;
		HashMap<Integer, Double> inspectionPenaltyHM = null;
		try {
			queryt= entityManager.createQuery(" SELECT MVE.vid,SUM(MVE.totalPenalty) FROM  VehicleInspectionCompletionDetails AS MVE  " + 
					"  INNER JOIN Vehicle AS V ON V.vid = MVE.vid " + 
					"  INNER JOIN VehicleGroup AS VG ON VG.gid = V.vehicleGroupId " + 
					"  WHERE V.vehicleGroupId = "+vehicleGroupId+" AND MVE.completionDateTime between '"+fromdate+"' AND '"+toDate+"' AND MVE.companyId = "+companyId+" " + 
					"  AND MVE.inspectionStatusId = "+VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED+" AND MVE.totalPenalty > 0 group by MVE.vid ",Object[].class);
			results = queryt.getResultList();
			
			if(results != null && !results.isEmpty()) {
				inspectionPenaltyHM = new HashMap<>();
				for(Object result[] :results) {
					if(result[0] != null && result[1] != null)
					inspectionPenaltyHM.put((Integer)result[0],(Double) result[1]);
				}
			}
			return inspectionPenaltyHM;
		} catch (Exception e) {
		e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<VehicleInspectionCompletionDetailsDto> getDateWiseInspectedList(Integer companyId ,Integer vid,Timestamp fromdate ,Timestamp toDate 
			) throws Exception {
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery("SELECT ICP.completionDetailsId, ICP.vid, V.vehicle_registration, VA.vehicleInspctionSheetAsingmentId, ICP.totalPenalty , ICP.completionDateTime "+ 
				 " FROM VehicleInspectionCompletionDetails ICP "+
				 " INNER JOIN Vehicle V ON V.vid = ICP.vid "+
				 " INNER JOIN VehicleInspectionSheetAssignment VA ON VA.vid = ICP.vid AND VA.inspectionSheetId = ICP.inspectionSheetId AND VA.vehicleInspctionSheetAsingmentId = ICP.vehicleInspctionSheetAsingmentId "+
				 " WHERE ICP.vid = "+vid+" AND  ICP.companyId = "+companyId+" AND ICP.completionDateTime between '"+fromdate+"' AND '"+toDate+"' AND ICP.inspectionStatusId = 2  AND ICP.totalPenalty > 0 AND ICP.markForDelete = 0 ",
				Object[].class);
	List<Object[]> results = queryt.getResultList();

	List<VehicleInspectionCompletionDetailsDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		
		Dtos = new ArrayList<>();
		VehicleInspectionCompletionDetailsDto parameterDto = null;
		for (Object[] result : results) {
			parameterDto = new VehicleInspectionCompletionDetailsDto();
			
			parameterDto.setCompletionDetailsId((Long) result[0]);
			parameterDto.setVid( (Integer) result[1]);
			parameterDto.setVehicle_registration( (String) result[2]);
			parameterDto.setVehicleInspctionSheetAsingmentId( (Long) result[3]);
			parameterDto.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED_NAME);
			parameterDto.setTotalPenalty((Double) result[4]);
			if(result[5] != null)
			parameterDto.setCompletionDateTimeStr(dateFormat.format(result[5]));
			Dtos.add(parameterDto);
		}
	}
	return Dtos;
}
	

	@Override
	public List<VehicleInspectionCompletionDetailsDto> getInspectionComplitionDetails(String query) throws Exception {
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery("SELECT ICP.completionDetailsId, ICP.vid, V.vehicle_registration, VA.vehicleInspctionSheetAsingmentId, ICP.totalPenalty , ICP.completionDateTime,ICP.inspectionDate "+ 
				 " FROM VehicleInspectionCompletionDetails ICP "+
				 " INNER JOIN Vehicle V ON V.vid = ICP.vid "+
				 " INNER JOIN VehicleInspectionSheetAssignment VA ON VA.vid = ICP.vid AND VA.inspectionSheetId = ICP.inspectionSheetId AND VA.vehicleInspctionSheetAsingmentId = ICP.vehicleInspctionSheetAsingmentId "+
				 " WHERE ICP.markForDelete = 0 "+query+" ",
				Object[].class);
	List<Object[]> results = queryt.getResultList();

	List<VehicleInspectionCompletionDetailsDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		
		Dtos = new ArrayList<>();
		VehicleInspectionCompletionDetailsDto parameterDto = null;
		for (Object[] result : results) {
			parameterDto = new VehicleInspectionCompletionDetailsDto();
			
			parameterDto.setCompletionDetailsId((Long) result[0]);
			parameterDto.setVid( (Integer) result[1]);
			parameterDto.setVehicle_registration( (String) result[2]);
			parameterDto.setVehicleInspctionSheetAsingmentId( (Long) result[3]);
			parameterDto.setInspectionStatusName(VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED_NAME);
			parameterDto.setTotalPenalty((Double) result[4]);
			if(result[5] != null)
			parameterDto.setCompletionDateTimeStr(dateFormat.format(result[5]));
			if(result[6] != null)
			parameterDto.setInspectionDateStr(dateFormat.format(result[6]));
			Dtos.add(parameterDto);
		}
	}
	return Dtos;
}
	
}
