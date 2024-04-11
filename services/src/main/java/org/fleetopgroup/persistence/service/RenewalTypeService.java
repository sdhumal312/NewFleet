package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.dao.RenewalTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalTypeDto;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.model.RenewalTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RenewalTypeService implements IRenewalTypeService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private RenewalTypeRepository RenewalTyperepository;
	
	@Autowired
	private IRenewalTypeHistoryService renewalTypeHistoryService;
	
	@Autowired ICompanyConfigurationService	companyConfigurationService;
	
	RenewalTypeBL renewalTypeBL = new RenewalTypeBL();

	// API

	@Transactional
	public RenewalType registerNewRenewalType(final RenewalType accountDto) throws Exception {

		return RenewalTyperepository.save(accountDto);
	}

	@Transactional
	public void updateRenewalType(RenewalType DocType) throws Exception {

		RenewalTyperepository.save(DocType);
	}

	@Transactional
	public List<RenewalType> findAll() {

		return RenewalTyperepository.findAll();
	}

	@Transactional
	public List<RenewalType> getRenewalType(String verificationToken, Integer companyId) {

		return RenewalTyperepository.getRenewalType(verificationToken, companyId);
	}

	@Transactional
	public void deleteRenewalType(Integer renewal_id, Integer companyId) {

		RenewalTyperepository.deleteRenewalType(renewal_id, companyId);
	}

	@Transactional
	public RenewalType getRenewalTypeByID(Integer renewal_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return RenewalTyperepository.getRenewalTypeByID(renewal_id, userDetails.getCompany_id());
	}

	@Transactional
	public long count() {

		return RenewalTyperepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalTypeService#
	 * findAll_VehicleMandatory_Renewal_Type()
	 */
	@Transactional
	public List<RenewalType> findAll_VehicleMandatory_Renewal_Type(Integer companyId) {

		return RenewalTyperepository.findAll_VehicleMandatory_Renewal_Type(companyId);
	}

	@Override
	public List<RenewalType> findAllByCompanyId(Integer companyId) throws Exception {
		try {
			return RenewalTyperepository.findAllByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<RenewalTypeDto> findAllListByCompanyId(Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;

		query = entityManager.createQuery(
				"SELECT  te.renewal_id, te.renewal_Type, te.expenseId, e.expenseName, te.companyId  "
				+ " from RenewalType te "
				+ " left join TripExpense e on e.expenseID = te.expenseId"
				+ " where te.markForDelete = 0 and (te.companyId = "+companyId+" or te.companyId = 0)",
				Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<RenewalTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalTypeDto>();
			RenewalTypeDto select = null;
			for (Object[] vehicle : results) {

				select = new RenewalTypeDto();
				select.setRenewal_id((Integer) vehicle[0]);
				select.setRenewal_Type((String) vehicle[1]);
				select.setExpenseId((Integer) vehicle[2]);
				select.setTallyExpenseName((String) vehicle[3]);
				select.setCompanyId((Integer) vehicle[4]);

				Dtos.add(select);
			}
		}
		return Dtos;
}
	
	@Override
	public List<RenewalType> mobileRenewalTypeList(ValueObject object) throws Exception {
		List<RenewalType>   renewalTypeList 			= null;
		List<RenewalType>   loadTypes					= null;
		try {
			
			renewalTypeList 	= new ArrayList<RenewalType>();
			loadTypes 		= RenewalTyperepository.findAllByCompanyId(object.getInt("companyId"));
			
			if(loadTypes != null && !loadTypes.isEmpty()) {
				for(RenewalType load : loadTypes) {
					
					RenewalType wadd = new RenewalType();
					wadd.setRenewal_id(load.getRenewal_id());
					wadd.setRenewal_Type(load.getRenewal_Type());
					
					renewalTypeList.add(wadd);
				}
			}

			object.put("renewalTypeList", renewalTypeList);

			return renewalTypeList;
			
		}catch(Exception e) {
			throw e;
		} finally {
			loadTypes 		 = null;
			renewalTypeList  = null;
			object  	 	 = null;
		}
	}

	@Override
	public List<RenewalType> findAllRenewalForComparision(Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;

		query = entityManager.createQuery(
				"SELECT distinct te.renewalTypeId, e.renewal_Type  "
				+ " from RenewalReminder te "
				+ " inner join RenewalType e on e.renewal_id = te.renewalTypeId"
				+ " where te.companyId = "+companyId+" and te.markForDelete = 0 order by e.renewal_Type asc",
				Object[].class);
		
		List<Object[]> results = query.getResultList();

		List<RenewalType> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalType>();
			RenewalType select = null;
			for (Object[] vehicle : results) {

				select = new RenewalType();
				select.setRenewal_id((Integer) vehicle[0]);
				select.setRenewal_Type((String) vehicle[1]);

				Dtos.add(select);
			}
		}
		return Dtos;
}
	
	@Override
	public RenewalTypeDto getRenewalTypeByID(Integer renewal_id, Integer companyId) throws Exception {

		javax.persistence.Query query = entityManager.createQuery("SELECT RT.renewal_Type, RT.expenseId, e.expenseName,RT.allowToAvoid "
				+ " FROM RenewalType RT "
				+ " left join TripExpense e on e.expenseID = RT.expenseId"
				+ " WHERE RT.renewal_id = "+renewal_id+" AND RT.companyId IN ("+companyId+", 0) AND RT.markForDelete = 0");
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		RenewalTypeDto	batteryInvoice = null;
		
			if (result != null) {
				batteryInvoice = new RenewalTypeDto();
				
				batteryInvoice.setRenewal_id(renewal_id);
				batteryInvoice.setRenewal_Type((String) result[0]);
				batteryInvoice.setExpenseId((Integer) result[1]);
				batteryInvoice.setTallyExpenseName((String) result[2]);
				batteryInvoice.setAllowToAvoid((boolean) result[3]);
			}
		
		return batteryInvoice;
	}
	
	@Override
	public ValueObject getRenewalTypeList(ValueObject object) throws Exception {
		List<RenewalTypeDto>   renewalType 				= null;
		CustomUserDetails	   userDetails				= null; 
		try {
			userDetails			 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalType				= findAllListByCompanyId(userDetails.getCompany_id());
			
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			object.put("configuration", configuration);	
			
			if(renewalType != null) {
				if((boolean) configuration.getOrDefault("hideRenewalTypes", false))
					renewalType =renewalType.parallelStream().filter(e->!(e.getRenewal_Type().equalsIgnoreCase("AUTHORISATION") || e.getRenewal_Type().equalsIgnoreCase("R C"))).collect(Collectors.toList());
				object.put("renewalType", renewalType);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalType  		= null;
			object  	 	    = null;
		}
	}
	
	@Override
	public ValueObject saveRenewalTypeList(ValueObject object) throws Exception {
		CustomUserDetails	   userDetails				= null;
		RenewalType 		   DocType					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			List<RenewalType> validate = getRenewalType(object.getString("ReType"), userDetails.getCompany_id());
			
			if (validate != null && !validate.isEmpty()) {
				object.put("alreadyRenewal", true);
				return object;
			} else {
				DocType = new RenewalType();
				DocType.setRenewal_Type(object.getString("ReType"));
				DocType.setExpenseId(object.getInt("tallyExpenseId"));
				DocType.setCreatedOn(toDate);
				DocType.setLastModifiedOn(toDate);
				DocType.setCompanyId(userDetails.getCompany_id());
				DocType.setCreatedById(userDetails.getId());
				DocType.setLastModifiedById(userDetails.getId());
				DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				DocType.setAllowToAvoid(object.getBoolean("avoidAllow",false));
				
				registerNewRenewalType(DocType);
				
				object.put("saveRenewalType", true);

			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			object  	 	    = null;
		}
	}
	
	@Override
	public ValueObject getRenewalTypeById(ValueObject object) throws Exception {
		RenewalTypeDto   	   renewalType 				= null;
		CustomUserDetails	   userDetails				= null; 
		try {
			userDetails			 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalType				= getRenewalTypeByID(object.getInt("renewalId"), userDetails.getCompany_id());
			
			if(renewalType != null) {
				object.put("renewalType", renewalType);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalType  		= null;
			object  	 	    = null;
		}
	}
	
	@Override
	public ValueObject updateRenewalType(ValueObject object) throws Exception {
		RenewalType   	   	   preRenewalType 				= null;
		List<RenewalType>      renewalTypeList 				= null;
		RenewalType   	   	   renewalType 					= null;
		CustomUserDetails	   userDetails					= null;
		RenewalTypeHistory	   renewalTypeHistory			= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			preRenewalType	= getRenewalTypeByID(object.getInt("renewalId"));
			
			if(!preRenewalType.getRenewal_Type().equalsIgnoreCase(object.getString("editReType").trim())) {
				renewalTypeList = getRenewalType(object.getString("editReType"), userDetails.getCompany_id());
			}
			
			if (renewalTypeList == null || renewalTypeList.size() <= 0) {
				renewalType = new RenewalType();
				renewalType.setRenewal_id(object.getInt("renewalId"));
				renewalType.setRenewal_Type(object.getString("editReType"));
				renewalType.setExpenseId(object.getInt("editTallyExpenseId"));
				renewalType.setCreatedOn(toDate);
				renewalType.setLastModifiedOn(toDate);
				renewalType.setLastModifiedById(userDetails.getId());
				renewalType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				renewalType.setCompanyId(userDetails.getCompany_id());
				renewalType.setAllowToAvoid(object.getBoolean("editAvoidAllow",false));
				
				renewalTypeHistory	= renewalTypeBL.prepareHistoryModel(getRenewalTypeByID(object.getInt("renewalId")));
				
				updateRenewalType(renewalType);
				
				renewalTypeHistoryService.registerNewRenewalTypeHistory(renewalTypeHistory);
				
				object.put("renewalTypeUpdated", true);
				
			} else {
				object.put("renewalTypeAlready", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalType  		= null;
			object  	 	    = null;
		}
	}
	
	@Override
	public ValueObject deleteRenewalTypeById(ValueObject object) throws Exception {
		CustomUserDetails	   userDetails				= null;
		RenewalTypeHistory	   renewalTypeHistory		= null;
		try {
			userDetails			 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalTypeHistory	 = renewalTypeBL.prepareHistoryModel(getRenewalTypeByID(object.getInt("renewalId")));
			
			deleteRenewalType(object.getInt("renewalId"), userDetails.getCompany_id());
			
			renewalTypeHistoryService.registerNewRenewalTypeHistory(renewalTypeHistory);
			
			object.put("renewalTypeDeleted", true);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalTypeHistory  		= null;
			object  	 	    		= null;
		}
	}
	
}