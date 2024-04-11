/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.persistence.bl.DriverBasicDetailsBL;
import org.fleetopgroup.persistence.dao.DriverBasicDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverBasicDetailsDto;
import org.fleetopgroup.persistence.model.DriverBasicDetails;
import org.fleetopgroup.persistence.serviceImpl.IDriverBasicDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("DriverBasicDetailsService")
@Transactional
public class DriverBasicDetailsService implements IDriverBasicDetailsService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DriverBasicDetailsRepository 		driverBasicDetailsRepository;
	SimpleDateFormat dateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL 	= new SimpleDateFormat("yyyy-MM-dd");
	
	DriverBasicDetailsBL	driverBasicDetailsBL 		= new DriverBasicDetailsBL();

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public ValueObject getDriverAllBasicDetails(ValueObject valueInObject) throws Exception {
		List<DriverBasicDetailsDto>		driverBasicDetailsList				= null;
		ValueObject 					valueOutObject						= null;
		try {
			valueOutObject					= new ValueObject();
			driverBasicDetailsList 		= getAllDriverBasicDetailsList(valueInObject);
			valueOutObject.put("driverBasicDetailsList", driverBasicDetailsList);
			
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			driverBasicDetailsList				= null;     
		}
	}
	
	@Override
	public List<DriverBasicDetailsDto> getAllDriverBasicDetailsList(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<DriverBasicDetailsDto>	driverBasicDetailsDtoList	= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT DBD.driverBasicDetailsId, DBD.detailsTypeId,  "
							+ " DBDT.driverBasicDetailsTypeName, DBD.quantity, DBD.assignDate, DBD.remark"
							+ " FROM DriverBasicDetails AS DBD "
							+ " INNER JOIN DriverBasicDetailsType DBDT ON DBDT.driverBasicDetailsTypeId = DBD.detailsTypeId "
							+ " where DBD.driverId= "+valueObject.getInt("driverId")+" AND DBD.companyId="+userDetails.getCompany_id()+" AND DBD.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			
			if(results != null && !results.isEmpty()) {
				driverBasicDetailsDtoList	=	new ArrayList<>();
				for (Object[] result : results) {
					DriverBasicDetailsDto	driverBasicDetailsDto = new DriverBasicDetailsDto();
					
					driverBasicDetailsDto.setDriverBasicDetailsId((Long)result[0]);
					driverBasicDetailsDto.setDetailsTypeId((Long)result[1]);
					driverBasicDetailsDto.setDetailsType((String)result[2]);
					driverBasicDetailsDto.setQuantity((Double)result[3]);
					driverBasicDetailsDto.setAssignDate((Date)result[4]);
					driverBasicDetailsDto.setAssignDateStr(dateFormat.format(driverBasicDetailsDto.getAssignDate()));
					driverBasicDetailsDto.setRemark((String)result[5]);
					driverBasicDetailsDtoList.add(driverBasicDetailsDto);
				}
			}
			
			return driverBasicDetailsDtoList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public DriverBasicDetails validateDriverBasicDetails(Long detailTypeId , Integer driverId, Timestamp assignDate, Integer companyId) throws Exception {
		try {
			return driverBasicDetailsRepository.findByDetailTypeId(detailTypeId,driverId,assignDate, companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveDriverBasicDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		DriverBasicDetails				validateDriverBasicDetails					= null;
		DriverBasicDetails 				driverBasicDetails			= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validateDriverBasicDetails		=	validateDriverBasicDetails(valueObject.getLong("detailsTypeId"),valueObject.getInt("driverId"),DateTimeUtility.getTimeStamp(valueObject.getString("assignDate")),userDetails.getCompany_id());
			
			if(validateDriverBasicDetails != null) {
				valueObject.put("alreadyExist", true);
			}else {
				driverBasicDetails = driverBasicDetailsBL.prepareDriverBasicDetails(valueObject, userDetails);
				driverBasicDetailsRepository.save(driverBasicDetails);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			driverBasicDetails					= null;  
			validateDriverBasicDetails			= null;  
		}
	}
	
	@Override
	public ValueObject getDriverBasicDetails(ValueObject valueInObject) throws Exception {
		DriverBasicDetailsDto  driverBasicDetailsDto = null;
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverBasicDetailsDto 		= getDriverBasicDetailsById(valueInObject.getLong("driverBasicDetailsId"),userDetails.getCompany_id());
			valueInObject.put("driverBasicDetails", driverBasicDetailsDto);
			return valueInObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public DriverBasicDetailsDto getDriverBasicDetailsById(Long driverBasicDetailsId, Integer companyId) throws Exception {
		Object[] 				result 					= null;
		DriverBasicDetailsDto	driverBasicDetailsDto 	= null;
		try {
			Query	query = entityManager
					.createQuery("SELECT DBD.driverBasicDetailsId, DBD.detailsTypeId,  "
							+ " DBDT.driverBasicDetailsTypeName, DBD.quantity, DBD.assignDate, DBD.remark"
							+ " FROM DriverBasicDetails AS DBD "
							+ " INNER JOIN DriverBasicDetailsType DBDT ON DBDT.driverBasicDetailsTypeId = DBD.detailsTypeId "
							+ " where DBD.driverBasicDetailsId = "+driverBasicDetailsId+" AND DBD.companyId="+companyId+" AND DBD.markForDelete = 0",  Object[].class);
				
					try {
						result = (Object[]) query.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
			
			
				if (result != null) {
					driverBasicDetailsDto = new DriverBasicDetailsDto();
					
					driverBasicDetailsDto.setDriverBasicDetailsId((Long)result[0]);
					driverBasicDetailsDto.setDetailsTypeId((Long)result[1]);
					driverBasicDetailsDto.setDetailsType((String)result[2]);
					driverBasicDetailsDto.setQuantity((Double)result[3]);
					driverBasicDetailsDto.setAssignDate((Date)result[4]);
					driverBasicDetailsDto.setAssignDateStr(dateFormat.format(driverBasicDetailsDto.getAssignDate()));
					driverBasicDetailsDto.setRemark((String)result[5]);
				}
			
			return driverBasicDetailsDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject updateDriverBasicDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		DriverBasicDetails			validateDriverBasicDetails					= null;
		DriverBasicDetails 			driverBasicDetails					= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverBasicDetails				= driverBasicDetailsRepository.findByDriverBasicDetailsId(valueObject.getLong("driverBasicDetailsId"),userDetails.getCompany_id());
			
			validateDriverBasicDetails		=	validateDriverBasicDetails(valueObject.getLong("detailsTypeId"),valueObject.getInt("driverId"),DateTimeUtility.getTimeStamp(valueObject.getString("assignDate")),userDetails.getCompany_id());
			if(validateDriverBasicDetails != null && (driverBasicDetails.getDetailsTypeId() != valueObject.getShort("detailsTypeId") || !driverBasicDetails.getAssignDate().equals(DateTimeUtility.getTimeStamp(valueObject.getString("assignDate"))))) {
				valueObject.put("alreadyExist", true);
			}else {
				driverBasicDetails = driverBasicDetailsBL.prepareDriverBasicDetails(valueObject, userDetails);
				driverBasicDetailsRepository.save(driverBasicDetails);
				valueObject.put("update", true);
			}
		
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;   
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteDriverBasicDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			driverBasicDetailsRepository.deleteDriverBasicDetails(valueObject.getLong("driverBasicDetailsId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	@Override
	public List<DriverBasicDetails> getDriverBasicDetailListByTypeId(ValueObject valueInObject) throws Exception {
		List<DriverBasicDetails>  driverBasicDetailsList = null;
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverBasicDetailsList = driverBasicDetailsRepository.getDetailListByTypeId(valueInObject.getLong("driverBasicDetailsTypeId"),userDetails.getCompany_id());
			return driverBasicDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getAllDriverTypeBasicDetails(ValueObject valueObject) throws Exception  {
		CustomUserDetails			userDetails						= null;
		
		List<DriverBasicDetailsDto>	driverBasicDetailsDtoList = null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int driverJobType = valueObject.getInt("driJobId",0);
			int driverId = valueObject.getInt("driverTypeList",0);
			long detailTypeId = valueObject.getLong("addDetailTypeId",0);
			String date = valueObject.getString("date","");
			ValueObject					tableConfig				= null;
			String [] datearr= date.split(" to ");
			String fromDate = dateFormatSQL.format(dateFormat.parse(datearr[0]));
			String toDate = dateFormatSQL.format(dateFormat.parse(datearr[1]));
			String query = "";
			String driverJobTypeQ = "",driverIdQ = "",detailTypeIdQ = ""; 
			
			if(driverJobType>0) {
				driverJobTypeQ=" AND D.driJobId = "+driverJobType+" ";
			}
			if(driverId>0) {
				driverIdQ=" AND DBD.driverId = "+driverId+" ";
			}
			if(detailTypeId>0) {
				detailTypeIdQ=" AND DBD.detailsTypeId = "+detailTypeId+" ";
			}
			query = driverJobTypeQ+""+driverIdQ+""+detailTypeIdQ;
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_BASIC_DETAILS_REPORT);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			driverBasicDetailsDtoList		= getAllDriverTypeBasicDetailsList(query, fromDate, toDate, userDetails.getCompany_id());
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("driverBasicDetailsList", driverBasicDetailsDtoList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}
	
	public List<DriverBasicDetailsDto> getAllDriverTypeBasicDetailsList(String query ,String fromDate,String toDate,int companyId ) throws Exception {
		List<DriverBasicDetailsDto>	driverBasicDetailsDtoList	= null;
		try {
			
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT DBD.driverBasicDetailsId, DBD.detailsTypeId,  "
							+ " DBDT.driverBasicDetailsTypeName, DBD.quantity, DBD.assignDate, DBD.remark ,D.driver_firstname ,D.driver_Lastname,D.driver_fathername,D.driver_id "
							+ " FROM DriverBasicDetails AS DBD "
							+ " INNER JOIN DriverBasicDetailsType DBDT ON DBDT.driverBasicDetailsTypeId = DBD.detailsTypeId "
							+ " INNER JOIN Driver D ON D.driver_id = DBD.driverId "
							+ " where  DBD.companyId="+companyId+" "+query+" AND DBD.assignDate between '"+fromDate+"' AND '"+toDate+"' AND DBD.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			if(results != null && !results.isEmpty()) {
				driverBasicDetailsDtoList	=	new ArrayList<>();
				for (Object[] result : results) {
					DriverBasicDetailsDto	driverBasicDetailsDto = new DriverBasicDetailsDto();
					
					driverBasicDetailsDto.setDriverBasicDetailsId((Long)result[0]);
					driverBasicDetailsDto.setDetailsTypeId((Long)result[1]);
					driverBasicDetailsDto.setDetailsType((String)result[2]);
					driverBasicDetailsDto.setQuantity((Double)result[3]);
					driverBasicDetailsDto.setAssignDate((Date)result[4]);
					driverBasicDetailsDto.setAssignDateStr(dateFormat.format(driverBasicDetailsDto.getAssignDate()));
					driverBasicDetailsDto.setRemark((String)result[5]);
					driverBasicDetailsDto.setDriverName((String)result[6]);
					if(result[7] != null && !((String)result[7]).trim().equals(""))
						driverBasicDetailsDto.setDriverName(driverBasicDetailsDto.getDriverName()+" "+(String)result[7]);
					if(result[8] != null && !((String)result[8]).trim().equals(""))
						driverBasicDetailsDto.setDriverName(driverBasicDetailsDto.getDriverName()+" - "+(String)result[8]);
					driverBasicDetailsDto.setDriverId((int) result[9]);
					driverBasicDetailsDto.setDriverName("<a target=\"_blank\" style=\"color: blue; background: #ffc;\" href=\"showDriver.in?driver_id="+driverBasicDetailsDto.getDriverId()+" \"> "+(driverBasicDetailsDto.getDriverName())+" </a>");
					driverBasicDetailsDtoList.add(driverBasicDetailsDto);
				}
			}
			
			return driverBasicDetailsDtoList;
		} catch (Exception e) {
			throw e;
		}
	}

}
