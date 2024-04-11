package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.dao.RenewalSubTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.model.RenewalSubType;
import org.fleetopgroup.persistence.model.RenewalSubTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RenewalSubTypeService implements IRenewalSubTypeService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired 	private RenewalSubTypeRepository 					RenewalSubTyperepository;
	@Autowired	private IRenewalSubTypeHistoryService 				renewalSubTypeHistoryService;
	
	RenewalTypeBL renewalTypeBL = new RenewalTypeBL();

	@Transactional
	public RenewalSubType registerNewRenewalSubType(final RenewalSubType accountDto) throws Exception {

		return RenewalSubTyperepository.save(accountDto);
	}

	@Transactional
	public void updateRenewalSubType(String renewal_SubType, Integer renewal_Subid,Long modifiedBy, Timestamp modifiedOn, Integer companyId, Integer renewal_id)
			throws Exception {

		RenewalSubTyperepository.updateRenewalSubType(renewal_SubType, modifiedBy, modifiedOn, renewal_Subid, companyId, renewal_id);
	}

	@Transactional
	public List<RenewalSubType> findAll() {

		return RenewalSubTyperepository.findAll();
	}

	@Transactional
	@Override
	public RenewalSubType getRenewalSubType(String verificationToken, Integer companyId) {
		return RenewalSubTyperepository.getRenewalSubType(verificationToken, companyId);
	}

	@Transactional
	@Override
	public RenewalSubType getRenewalSubTypeById(Integer renewal_Subid, Integer companyId) {
		return RenewalSubTyperepository.getRenewalSubTypeById(renewal_Subid, companyId);
	}

	@Transactional
	public void deleteRenewalSubType(Integer renewal_Subid, Integer companyId) {

		RenewalSubTyperepository.deleteRenewalSubType(renewal_Subid,companyId);
	}

	@Transactional
	public RenewalSubTypeDto getRenewalSubTypeByID(Integer renewal_Subid, Integer companyId) {
					
		Query query = entityManager.createQuery(
							"SELECT RST.renewal_Subid, RST.renewal_SubType, RT.renewal_Type, RST.renewal_id, RST.isMandatory  "
							+ " From RenewalSubType RST "
							+ " LEFT JOIN RenewalType RT ON RT.renewal_id = RST.renewal_id"
							+ " where RST.renewal_Subid = "+renewal_Subid+" AND RST.companyId IN ("+companyId+", 0) AND RST.markForDelete = 0");
			
					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
					} catch (NoResultException nre) {
						// Ignore this because as per your logic this is ok!
					}
			
					RenewalSubTypeDto select;
					if (result != null) {
						select = new RenewalSubTypeDto();
						select.setRenewal_Subid((Integer) result[0]);
						select.setRenewal_SubType((String) result[1]);
						select.setRenewal_Type((String) result[2]);
						select.setRenewal_id((Integer) result[3]);
						if(result[4] != null)
						select.setMandatory((boolean) result[4]);

					} else {
						return null;
					}
			
					return select;
				
	
	
	}

	@Transactional
	public long count() {

		return RenewalSubTyperepository.count();
	}

	/*@Transactional
	public List<RenewalSubType> listRenewalSubTypeSearch(String renewal_Type, Integer companyId) throws Exception {

		return RenewalSubTyperepository.listRenewalSubTypeSearch(renewal_Type, companyId);
	}*/

	@Override
	public List<RenewalSubType> listRenewalSubTypeSearch(Integer renewal_Type, Integer companyId) throws Exception {
		return RenewalSubTyperepository.listRenewalSubTypeSearch(renewal_Type, companyId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalSubTypeService#
	 * findAll_VehicleMandatory_Renewal_Sub_Type()
	 */
	@Transactional
	public List<RenewalSubTypeDto> findAll_VehicleMandatory_Renewal_Sub_Type(Integer companyId) {
		//return RenewalSubTyperepository.findAll_VehicleMandatory_Renewal_Sub_Type(companyId);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.renewal_Subid, R.renewal_SubType, RT.renewal_Type, R.renewal_id "
				+ " FROM RenewalSubType AS R"
				+ " LEFT JOIN RenewalType RT ON RT.renewal_id = R.renewal_id"
				+ " WHERE R.companyId = "+companyId+" and R.markForDelete = 0 ORDER BY R.renewal_SubType ASC",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalSubTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalSubTypeDto>();
			RenewalSubTypeDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new RenewalSubTypeDto();
				
				Documentto.setRenewal_Subid((Integer) result[0]);
				Documentto.setRenewal_SubType((String) result[1]);
				Documentto.setRenewal_Type((String) result[2]);
				Documentto.setRenewal_id((Integer) result[3]);
				
				Dtos.add(Documentto);
			}
		}
		return Dtos;

	
		
	
	}
	
	@Override
	public List<RenewalSubTypeDto> findAllByCompanyId(Integer companyId) throws Exception {
		try {
			//return RenewalSubTyperepository.findAllByCompanyId(companyId);

			TypedQuery<Object[]> queryt = entityManager.createQuery(
					" SELECT R.renewal_Subid, R.renewal_SubType, RT.renewal_Type, R.renewal_id "
					+ " FROM RenewalSubType AS R"
					+ " LEFT JOIN RenewalType RT ON RT.renewal_id = R.renewal_id"
					+ "  WHERE R.companyId = "+companyId+" and R.markForDelete = 0",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<RenewalSubTypeDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalSubTypeDto>();
				RenewalSubTypeDto Documentto = null;
				for (Object[] result : results) {
					Documentto = new RenewalSubTypeDto();
					
					Documentto.setRenewal_Subid((Integer) result[0]);
					Documentto.setRenewal_SubType((String) result[1]);
					Documentto.setRenewal_Type((String) result[2]);
					Documentto.setRenewal_id((Integer) result[3]);
					
					Dtos.add(Documentto);
				}
			}
			return Dtos;

		
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<RenewalSubTypeDto> findAllMandatorySubTypeRenewalByCompanyId(Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					" SELECT R.renewal_Subid, R.renewal_SubType, RT.renewal_Type, R.renewal_id, R.companyId "
					+ " FROM RenewalSubType AS R "
					+ " LEFT JOIN RenewalType RT ON RT.renewal_id = R.renewal_id "
					+ " WHERE R.companyId IN ("+companyId+", 0) AND R.markForDelete = 0 AND R.isMandatory = true ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<RenewalSubTypeDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalSubTypeDto>();
				RenewalSubTypeDto Documentto = null;
				for (Object[] result : results) {
					Documentto = new RenewalSubTypeDto();
					
					Documentto.setRenewal_Subid((Integer) result[0]);
					Documentto.setRenewal_SubType((String) result[1]);
					Documentto.setRenewal_Type((String) result[2]);
					Documentto.setRenewal_id((Integer) result[3]);
					Documentto.setCompanyId((Integer) result[4]);
					
					Dtos.add(Documentto);
				}
			}
			
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<RenewalSubTypeDto> findAllNonMandatorySubTypeRenewalByCompanyId(Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					" SELECT R.renewal_Subid, R.renewal_SubType, RT.renewal_Type, R.renewal_id "
					+ " FROM RenewalSubType AS R "
					+ " LEFT JOIN RenewalType RT ON RT.renewal_id = R.renewal_id "
					+ " WHERE R.companyId IN ("+companyId+", 0) AND R.markForDelete = 0 AND R.isMandatory = false ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<RenewalSubTypeDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalSubTypeDto>();
				RenewalSubTypeDto Documentto = null;
				for (Object[] result : results) {
					Documentto = new RenewalSubTypeDto();
					
					Documentto.setRenewal_Subid((Integer) result[0]);
					Documentto.setRenewal_SubType((String) result[1]);
					Documentto.setRenewal_Type((String) result[2]);
					Documentto.setRenewal_id((Integer) result[3]);
					
					Dtos.add(Documentto);
				}
			}
			
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*@Override
	public void updateRenewalType(String renewal_type, Integer renewal_id ,Integer companyId) {

		RenewalSubTyperepository.updateRenewalType(renewal_type, renewal_id, companyId);
	}*/
	
	@Override
	public List<RenewalSubType> listRenewalSubTypeSearch(String term, Integer companyId) throws Exception {
		try {
			List<RenewalSubType> Dtos = null;
			//return RenewalSubTyperepository.findAllByCompanyId(companyId);
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					" SELECT R.renewal_Subid, R.renewal_SubType, R.renewal_id "
					+ " FROM RenewalSubType AS R"
					+ "  WHERE lower(R.renewal_SubType) Like ('%" + term +"%') AND R.companyId = "+companyId+" AND R.markForDelete = 0 ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalSubType>();
				RenewalSubType Documentto = null;
				for (Object[] result : results) {
					Documentto = new RenewalSubType();
					
					Documentto.setRenewal_Subid((Integer) result[0]);
					Documentto.setRenewal_SubType((String) result[1]);
					Documentto.setRenewal_id((Integer) result[2]);
					
					Dtos.add(Documentto);
				}
			}
			}
			return Dtos;

		
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject mobileRenewalSubTypeList(ValueObject object) throws Exception {
		List<RenewalSubType>   renewalSubTypeList 		= null;
		List<RenewalSubType>   loadTypes				= null;
		try {
			
			renewalSubTypeList 	= new ArrayList<RenewalSubType>();
			loadTypes 		    = RenewalSubTyperepository.listRenewalSubTypeSearch(object.getInt("renewalId"), object.getInt("companyId"));
			
			if(loadTypes != null && !loadTypes.isEmpty()) {
				for(RenewalSubType load : loadTypes) {
					
					RenewalSubType wadd = new RenewalSubType();
					wadd.setRenewal_Subid(load.getRenewal_Subid());
					wadd.setRenewal_SubType(load.getRenewal_SubType());
					
					renewalSubTypeList.add(wadd);
				}
			}

			object.put("renewalSubTypeList", renewalSubTypeList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			loadTypes 		    = null;
			renewalSubTypeList  = null;
			object  	 	    = null;
		}
	}
	
	@Override
	public ValueObject saveRenewalSubTypeDetails(ValueObject object) throws Exception {
		CustomUserDetails		userDetails				= null; 
		RenewalSubType 		    renewalSubType 			= null;
		RenewalSubType 			GET_DocType 			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalSubType 	= getRenewalSubType(object.getString("renewalSubType"), userDetails.getCompany_id());
			
			if (renewalSubType == null) {
				
				GET_DocType = new RenewalSubType();
				
				GET_DocType.setRenewal_id(object.getInt("renewalId"));
				GET_DocType.setRenewal_SubType(object.getString("renewalSubType"));
				GET_DocType.setMandatory(object.getBoolean("isMandatory"));
				GET_DocType.setCompanyId(userDetails.getCompany_id());
				GET_DocType.setCreatedById(userDetails.getId());
				GET_DocType.setLastModifiedById(userDetails.getId());
				GET_DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				GET_DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				
				registerNewRenewalSubType(GET_DocType);
				object.put("SaveRenewalSubType", true);
				
			} else {
				object.put("RenewalSubTypeAlreadyExists", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			object  	 	    = null;
		}
	}
	
	@Override
	public ValueObject getRenewalSubTypeList(ValueObject object) throws Exception {
		List<RenewalSubTypeDto>   manadatorySubRenewal 					= null;
		List<RenewalSubTypeDto>   nonManadatorySubRenewal 				= null;
		CustomUserDetails	   	  userDetails							= null; 
		try {
			userDetails			 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			manadatorySubRenewal 	= renewalTypeBL.RenewalSubListofDto(findAllMandatorySubTypeRenewalByCompanyId(userDetails.getCompany_id()));
			nonManadatorySubRenewal = renewalTypeBL.RenewalSubListofDto(findAllNonMandatorySubTypeRenewalByCompanyId(userDetails.getCompany_id()));
			
			if(manadatorySubRenewal != null) {
				object.put("ManadatorySubRenewal", manadatorySubRenewal);
			}
			
			if(nonManadatorySubRenewal != null) {
				object.put("NonManadatorySubRenewal", nonManadatorySubRenewal);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			manadatorySubRenewal  		= null;
			nonManadatorySubRenewal  	= null;
			object  	 	    		= null;
		}
	}
	
	@Override
	public ValueObject getRenewalSubTypeById(ValueObject object) throws Exception {
		RenewalSubTypeDto   	  renewalSubType 					= null;
		CustomUserDetails	   	  userDetails						= null; 
		try {
			userDetails	    = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalSubType  = renewalTypeBL.prepareRenewalSubTypeDto(getRenewalSubTypeByID(object.getInt("subTypeId"), userDetails.getCompany_id()));
			
			if(renewalSubType != null) {
				object.put("RenewalSubType", renewalSubType);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalSubType  		= null;
			object  	 	    	= null;
		}
	}
	
	@Override
	public ValueObject updateRenewalSubTypeDetails(ValueObject object) throws Exception {
		CustomUserDetails		userDetails				= null; 
		RenewalSubTypeDto 		renewalSubType 			= null;
		RenewalSubType			duplicate				= null;
		RenewalSubTypeHistory	renewalSubTypeHistory	= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			renewalSubType	= getRenewalSubTypeByID(object.getInt("renewalSubTypeId"), userDetails.getCompany_id());
			
			if(!renewalSubType.getRenewal_SubType().equalsIgnoreCase(object.getString("renewalSubType"))) {
				RenewalSubType Validate = getRenewalSubType(object.getString("renewalSubType"), userDetails.getCompany_id());
				if(Validate != null) {
					object.put("alreadyRenewalSubType", true);
					return object;
				}
			}
			
			duplicate = RenewalSubTyperepository.getRenewalSubTypeDupliacte(object.getInt("renewalId"), object.getString("renewalSubType"), 
							object.getInt("renewalSubTypeId"), userDetails.getCompany_id());
			if(duplicate != null) {
				object.put("alreadyRenewalSubType", true);
				return object;
			}
			
			renewalSubTypeHistory = renewalTypeBL.prepareRenewalSubTypeHistoryModel(getRenewalSubTypeById(object.getInt("renewalSubTypeId"), userDetails.getCompany_id()));
			
			RenewalSubTyperepository.updateRenewalSubTypeDetails(object.getString("renewalSubType"), userDetails.getId(), new Timestamp(System.currentTimeMillis()),
					 object.getInt("renewalSubTypeId"), userDetails.getCompany_id(), object.getInt("renewalId"), object.getBoolean("isMandatory"));
			
			renewalSubTypeHistoryService.registerNewRenewalSubTypeHistory(renewalSubTypeHistory);
			
			object.put("renewalSubTypeUpdated", true);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			object  	 	    = null;
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteRenewalSubTypeById(ValueObject object) throws Exception {
		CustomUserDetails	   	  userDetails						= null; 
		RenewalSubTypeHistory	  renewalSubTypeHistory				= null;
		try {
			userDetails	    = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			renewalSubTypeHistory = renewalTypeBL.prepareRenewalSubTypeHistoryModel(getRenewalSubTypeById(object.getInt("renewalSubTypeId"), userDetails.getCompany_id()));
			
			deleteRenewalSubType(object.getInt("renewalSubTypeId"), userDetails.getCompany_id());
			
			renewalSubTypeHistoryService.registerNewRenewalSubTypeHistory(renewalSubTypeHistory);
			
			object.put("renewalSubTypeDeleted", true);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			object  	 	    	= null;
		}
	}
}