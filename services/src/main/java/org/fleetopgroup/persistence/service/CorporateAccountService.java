package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.CorporateAccountBL;
import org.fleetopgroup.persistence.dao.CorporateAccountRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CorporateAccount;
import org.fleetopgroup.persistence.serviceImpl.ICorporateAccountService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CorporateAccountService implements ICorporateAccountService {
	
	@PersistenceContext
	EntityManager entityManager;
	@Autowired	CorporateAccountRepository		corporateAccountRepository;
	CorporateAccountBL	corporateAccountBL 		= new CorporateAccountBL();
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public List<CorporateAccount> getPartyListByName(String term, Integer companyId) throws Exception {
		TypedQuery<CorporateAccount> queryt = null;
		queryt = entityManager.createQuery(
				"SELECT CA FROM CorporateAccount AS CA "
				+ " where ( lower(CA.corporateName) Like ('%" + term+"%')  OR  lower(CA.mobileNumber) Like ('%" + term + "%') ) "
				+ " AND CA.companyId =  "+companyId + "and CA.markForDelete = 0",
				CorporateAccount.class);
	
		return 	queryt.getResultList();
	}
	
	
	@Override
	public ValueObject getAllPartyMaster() throws Exception {
		CustomUserDetails			userDetails						= null;
		List<CorporateAccount>		corporateAccountList				= null;
		ValueObject 				valueObject						= null;
		try {
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			corporateAccountList 		= corporateAccountRepository.findByCmpanyId(userDetails.getCompany_id());
			
			valueObject.put("partMasterList", corporateAccountList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			corporateAccountList				= null;     
			valueObject						= null;     
		}
	}
	
	
	@Override
	public ValueObject savePartyMaster(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		CorporateAccount			partyMaster						= null;
		CorporateAccount 			validatePartyMaster			= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			validatePartyMaster		=	validatePartyMaster(valueObject.getString("corporateName"),userDetails.getCompany_id());
			
			if(validatePartyMaster != null) {
				valueObject.put("alreadyExist", true);
			}else {
				partyMaster = corporateAccountBL.preparePartyMaster(valueObject, userDetails);
				corporateAccountRepository.save(partyMaster);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			partyMaster					= null;  
			validatePartyMaster			= null;  
		}
	}
	
	@Transactional
	public CorporateAccount validatePartyMaster(String corporateName , Integer companyId) throws Exception {
		try {
			return corporateAccountRepository.findByName(corporateName,companyId);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getPartyMasterByCorporateAccountId(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		CorporateAccount			partyMaster					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partyMaster 				= corporateAccountRepository.findByCorporateAccountId(valueObject.getLong("corporateAccountId"), userDetails.getCompany_id());
			valueObject.put("partyMaster", partyMaster);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
			partyMaster					= null;     
		}
	}
	
	@Override
	@Transactional
	public ValueObject updatePartyMaster(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		CorporateAccount 			validatepartyMaster				= null;
		CorporateAccount			partyMaster						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partyMaster					= corporateAccountRepository.findByCorporateAccountId(valueObject.getLong("editCorporateAccountId"),userDetails.getCompany_id());
			
			if(valueObject.getString("editCorporateName").equalsIgnoreCase(partyMaster.getCorporateName().trim())) {
				corporateAccountRepository.updatePartyMasterByCorporateAccountId(valueObject.getLong("editCorporateAccountId"),partyMaster.getCorporateName(), valueObject.getString("editAddress"),valueObject.getString("editMobileNumber"),valueObject.getString("editAlternateMobileNumber"),valueObject.getString("editGstNumber"),valueObject.getDouble("editPerKMRate"),userDetails.getCompany_id());
			}else {
				validatepartyMaster		= validatePartyMaster(valueObject.getString("editCorporateName"),userDetails.getCompany_id());
				if(validatepartyMaster != null) {
					valueObject.put("alreadyExist", true);
				}else {
					corporateAccountRepository.updatePartyMasterByCorporateAccountId(valueObject.getLong("editCorporateAccountId"),valueObject.getString("editCorporateName"), valueObject.getString("editAddress"),valueObject.getString("editMobileNumber"),valueObject.getString("editAlternateMobileNumber"),valueObject.getString("editGstNumber"),valueObject.getDouble("editPerKMRate"),userDetails.getCompany_id());
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;   
			validatepartyMaster			= null;   
			partyMaster						= null;   
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject deletePartyMaster(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			corporateAccountRepository.deletePartyMasterByCorporateAccountId(valueObject.getLong("corporateAccountId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
		@Override
		public ValueObject mobilePartyNameList(ValueObject object) throws Exception {
			List<CorporateAccount>   partyNameList 				= null;
			List<CorporateAccount>   loadTypes					= null;
			try {
				partyNameList 	= new ArrayList<CorporateAccount>();
				loadTypes 		= getPartyListByName(object.getString("term"), object.getInt("companyId"));
				
				if(loadTypes != null && !loadTypes.isEmpty()) {
					for(CorporateAccount load : loadTypes) {
						partyNameList.add(load);
					}
				}

				object.put("partyNameList", partyNameList);

				return object;
				
			}catch(Exception e) {
				throw e;
			} finally {
				loadTypes 		 = null;
				partyNameList 	 = null;
				object  	 	 = null;
			}
		}
}
