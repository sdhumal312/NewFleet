package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.persistence.dao.VehicleCheckingDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleCheckingDetailsDto;
import org.fleetopgroup.persistence.model.VehicleCheckingDetails;
import org.fleetopgroup.persistence.report.dao.VehicleCheckingDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCheckingDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class VehicleCheckingDetailsService implements IVehicleCheckingDetailsService {
	
	@Autowired private VehicleCheckingDetailsRepository			vehicleCheckingDetailsRepository;
	
	@Autowired private VehicleCheckingDetailsDao				vehicleCheckingDetailsDao;
	
	@Autowired private IUserProfileService						userProfileService;
	
	@Autowired ICompanyConfigurationService 					companyConfigurationService;

	@Override
	public ValueObject saveCheckingDetails(ValueObject valueObject) throws Exception {
		ValueObject						outObject					= null;
		try {
			outObject = new ValueObject();
			
			/*checkingDataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("checkingData");
			if(checkingDataArrayObjColl != null && !checkingDataArrayObjColl.isEmpty()) {
				vehicleCheckingDetailsList	= new ArrayList<>();
				for (ValueObject object : checkingDataArrayObjColl) {
					vehicleCheckingDetailsList.add(getVehicleCheckingDetails(object, valueObject));
				}
				
				vehicleCheckingDetailsRepository.saveAll(vehicleCheckingDetailsList);
				
			}*/
			vehicleCheckingDetailsRepository.save(getVehicleCheckingDetails(valueObject));
			
			outObject.put("saveMessage", "Data Saved Succsessfully!");
			
			return outObject;
		} catch (Exception e) {
			throw e;
		}finally {
			outObject					= null;
		}
	}
	
	/*private VehicleCheckingDetails getVehicleCheckingDetails(ValueObject object, ValueObject inObject) throws Exception{
		VehicleCheckingDetails			vehicleCheckingDetails		= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleCheckingDetails	= new VehicleCheckingDetails();
			
			
			vehicleCheckingDetails.setCheckingInspectorId(inObject.getInt("checkingInspectorId"));
			vehicleCheckingDetails.setCheckingDateTime(DateTimeUtility.getTimeStamp(inObject.getString("checkingDateTime")));
			vehicleCheckingDetails.setVid(object.getInt("vid"));
			vehicleCheckingDetails.setConductorId(object.getInt("conductor",0));
			vehicleCheckingDetails.setCheckingTime(object.getString("time"));
			vehicleCheckingDetails.setPlace(object.getString("place"));
			vehicleCheckingDetails.setNoOfSeat(object.getInt("noOfSeat"));
			vehicleCheckingDetails.setRemark(object.getString("remark"));
			vehicleCheckingDetails.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
			vehicleCheckingDetails.setLastModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			vehicleCheckingDetails.setCreatedById(userDetails.getId());
			vehicleCheckingDetails.setLastModifiedById(userDetails.getId());
			vehicleCheckingDetails.setMarkForDelete(false);
			vehicleCheckingDetails.setCompanyId(userDetails.getCompany_id());
			return vehicleCheckingDetails;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleCheckingDetails		= null;
			userDetails					= null;
		}
	}*/
	private VehicleCheckingDetails getVehicleCheckingDetails(ValueObject inObject) throws Exception{
		VehicleCheckingDetails			vehicleCheckingDetails		= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleCheckingDetails	= new VehicleCheckingDetails();
			
			
			vehicleCheckingDetails.setCheckingInspectorId(inObject.getInt("checkingInspectorId"));
			vehicleCheckingDetails.setCheckingDateTime(DateTimeUtility.getTimeStamp(inObject.getString("checkingDateTime")));
			vehicleCheckingDetails.setVid(inObject.getInt("vid", 0));
			vehicleCheckingDetails.setConductorId(inObject.getInt("conductorId",0));
			vehicleCheckingDetails.setCheckingTime(inObject.getString("checkingTime"));
			vehicleCheckingDetails.setCheckingOutTime(inObject.getString("outTime"));
			vehicleCheckingDetails.setPlace(inObject.getString("place"));
			vehicleCheckingDetails.setOutPlace(inObject.getString("outplace"));
			vehicleCheckingDetails.setNoOfSeat(inObject.getInt("noOfSeat",0));
			vehicleCheckingDetails.setRemark(inObject.getString("remark"));
			vehicleCheckingDetails.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
			vehicleCheckingDetails.setLastModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			vehicleCheckingDetails.setCreatedById(userDetails.getId());
			vehicleCheckingDetails.setLastModifiedById(userDetails.getId());
			vehicleCheckingDetails.setMarkForDelete(false);
			vehicleCheckingDetails.setCompanyId(userDetails.getCompany_id());
			vehicleCheckingDetails.setVehicleGroupId(inObject.getLong("vehicleGroupId", 0));
			if(inObject.getString("route") != null)
			vehicleCheckingDetails.setRoute(inObject.getString("route"));
			if(inObject.getString("description") != null)
			vehicleCheckingDetails.setDescription(inObject.getString("description"));
			if(inObject.getString("punishment") != null)
			vehicleCheckingDetails.setPunishment(inObject.getString("punishment"));
			if(inObject.getString("orderNoAndDate") != null)
			vehicleCheckingDetails.setOrderNoAndDate(inObject.getString("orderNoAndDate"));
			
			return vehicleCheckingDetails;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleCheckingDetails		= null;
			userDetails					= null;
		}
	}
	
	@Override
	public ValueObject getCheckingEntryReport(ValueObject valueObject) throws Exception {
		String 							dateRange				= null;
		CustomUserDetails				userDetails				= null;
		VehicleCheckingDetailsDto		checkingDetailsDto		= null;
		String							query					= "";
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!valueObject.containsKey("dateRange")) {
				return null;
			}
			dateRange	= valueObject.getString("dateRange");
			
			
			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");
			
			checkingDetailsDto	= new VehicleCheckingDetailsDto();
			
			checkingDetailsDto.setCompanyId(userDetails.getCompany_id());
			checkingDetailsDto.setFromDate(From_TO_DateRange[0]);
			checkingDetailsDto.setToDate(From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME);
			checkingDetailsDto.setVid(valueObject.getInt("vid", 0));
			checkingDetailsDto.setCheckingInspectorId(valueObject.getInt("checkingInspectorId", 0));
			checkingDetailsDto.setVehicleGroupId(valueObject.getLong("vehicleGroupId"));
			
			if(checkingDetailsDto.getVid() != null && checkingDetailsDto.getVid() > 0) {
				query	+= " AND VCD.vid = "+checkingDetailsDto.getVid()+" ";
			}
			if(checkingDetailsDto.getCheckingInspectorId() != null && checkingDetailsDto.getCheckingInspectorId() > 0) {
				query	+= " AND VCD.checkingInspectorId = "+checkingDetailsDto.getCheckingInspectorId()+" ";
			}
			if(checkingDetailsDto.getVehicleGroupId() != null && checkingDetailsDto.getVehicleGroupId() > 0) {
				query	+= " AND VCD.vehicleGroupId = "+checkingDetailsDto.getVehicleGroupId()+" ";
			}
			valueObject.clear();
			valueObject.put("SearchDate", dateRange.replace(" ", "_"));
			valueObject.put("list", vehicleCheckingDetailsDao.getCheckingEntryReport(checkingDetailsDto, query));
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			dateRange				= null;	
			userDetails				= null;
			checkingDetailsDto		= null;
		}
	}
	
	@Override
	public Model getCheckingReportList(Integer pageNumber, Model model) throws Exception {
		try {
			CustomUserDetails userDetails = null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Page<VehicleCheckingDetails> page = vehicleCheckingDetailsDao.getDeployment_Page_Checking(pageNumber, userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);

				model.addAttribute("FuelCount", page.getTotalElements());

				List<VehicleCheckingDetailsDto> pageList = vehicleCheckingDetailsDao.getCheckingEntryList(pageNumber, userDetails);

				model.addAttribute("pageList", pageList);

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return model;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void delete(Long id) throws Exception {
		try {
			vehicleCheckingDetailsRepository.deleteById(id);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public ValueObject getConductorHistoryReport(ValueObject valueObject) throws Exception {
		String							dateRange				= null;
		Integer							conductorId				= 0;
		CustomUserDetails				userDetails				= null;
		List<VehicleCheckingDetailsDto>	conductorHistory		= null;
		ValueObject						tableConfig				= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange		= valueObject.getString("dateRange");
			conductorId		= valueObject.getInt("conductorId", 0);
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = From_TO_DateRange[0];
				dateRangeTo = From_TO_DateRange[1]+" "+DateTimeUtility.DAY_END_TIME;
				
				String conductor_Name = "", Date = "";
				
				if(conductorId != 0  )
				{
					conductor_Name = " AND VCD.conductorId = "+ conductorId +" ";
				}
				
				Date =  " AND VCD.creationDateTime between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = " " + conductor_Name + "  " + Date + " ";
				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.CONDUCTOR_HISTORY_TABLE_DATA_FILE_PATH);

				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				
				conductorHistory = vehicleCheckingDetailsDao.getConductorHistoryReportList(query, userDetails.getCompany_id());

			}
			valueObject.put("conductorHistory", conductorHistory);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			conductorHistory	= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
}
