/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.velocity.runtime.directive.Parse;
import org.fleetopgroup.persistence.bl.SubCompanyBL;
import org.fleetopgroup.persistence.dao.SubCompanyRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.SubCompany;
import org.fleetopgroup.persistence.serviceImpl.ISubCompanyService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("SubCompanyService")
@Transactional
public class SubCompanyService implements ISubCompanyService {
	
	@Autowired
	private SubCompanyRepository 		subCompanyRepository;
	
	SubCompanyBL	subCompanyBL 		= new SubCompanyBL();

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject getAllSubCompanies() throws Exception {
		try {
			ValueObject 		valueObject			= new ValueObject();
			CustomUserDetails 	userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<SubCompany> 	subCompanyList 		= subCompanyRepository.findBycompanyId(userDetails.getCompany_id());
			valueObject.put("subCompanyList", subCompanyList);
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public SubCompany findBySubcompanyName(String vehicleModelName , Integer companyId) throws Exception {
		try {
			return subCompanyRepository.findBySubcompanyName(vehicleModelName,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveSubCompany(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails 	userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SubCompany 			validateSubCompany	=	findBySubcompanyName(valueObject.getString("subCompanyName"),userDetails.getCompany_id());
			
			if(validateSubCompany != null) {
				valueObject.put("alreadyExist", true);
			}else {
				SubCompany	subCompany = subCompanyBL.prepareSubCompany(valueObject, userDetails);
				subCompanyRepository.save(subCompany);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getSubCompanyById(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails	userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SubCompany 			subCompany 		= subCompanyRepository.findBySubcompanyId(valueObject.getLong("subCompanyId"), userDetails.getCompany_id());
			valueObject.put("subCompany", subCompany);
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateSubCompany(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails 	userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SubCompany 			subCompanyDetails 	= subCompanyRepository.findBySubcompanyId(valueObject.getLong("subCompanyId"), userDetails.getCompany_id());
			
			if(valueObject.getString("subCompanyName").equalsIgnoreCase(subCompanyDetails.getSubCompanyName().trim())) {
				SubCompany	subCompany = subCompanyBL.prepareSubCompany(valueObject, userDetails);
				subCompanyRepository.save(subCompany);
			}else {
				SubCompany 			validateSubCompany	=	findBySubcompanyName(valueObject.getString("subCompanyName"),userDetails.getCompany_id());
				if(validateSubCompany != null) {
					valueObject.put("alreadyExist", true);
				}else {
					SubCompany	subCompany = subCompanyBL.prepareSubCompany(valueObject, userDetails);
					subCompanyRepository.save(subCompany);
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteSubCompany(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			subCompanyRepository.deleteSubCompany(valueObject.getLong("subCompanyId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public  SubCompany  getSubCompanyByVehicle(Integer subCompanyId) throws Exception {
		try {
			CustomUserDetails	userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SubCompany 			subCompany 		= subCompanyRepository.findSubCompanyBySubCompanyId(subCompanyId.longValue(), userDetails.getCompany_id());
			return subCompany;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
