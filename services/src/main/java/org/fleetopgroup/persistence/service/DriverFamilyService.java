/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.DriverFamilyRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverFamilyDto;
import org.fleetopgroup.persistence.model.DriverFamily;
import org.fleetopgroup.persistence.serviceImpl.IDriverFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fleetop
 *
 */
@Service("DriverFamilyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverFamilyService implements IDriverFamilyService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private DriverFamilyRepository driverFamilyDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverFamilyService#
	 * Registered_DriverFamily_Details(org.fleetop.persistence.model.
	 * DriverFamily)
	 */
	@Transactional
	public void Registered_DriverFamily_Details(DriverFamily driverFam) {

		driverFamilyDao.save(driverFam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverFamilyService#
	 * Validate_DriverFamily_Details(java.lang.String)
	 */
	@Transactional
	public DriverFamily Validate_DriverFamily_Details(String DF_Name) {

		return driverFamilyDao.Validate_DriverFamily_Details(DF_Name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverFamilyService#
	 * list_Of_DriverID_to_DriverFamily(java.lang.Integer)
	 */
	@Transactional
	public List<DriverFamilyDto> list_Of_DriverID_to_DriverFamily(Integer DriverId, Integer companyId) {
		//return driverFamilyDao.list_Of_DriverID_to_DriverFamily(DriverId, companyId);
		
		TypedQuery<Object[]> queryt = entityManager
				.createQuery(" SELECT DF.DFID, DF.DRIVERID, DF.DF_NAME, DF.DF_RELATIONSHIP_ID, DF.DF_SEX_ID, DF.DF_AGE, DF.CREATED_DATE "
						+ " FROM DriverFamily AS DF "
						+ " WHERE DF.DRIVERID= "+DriverId+" AND DF.COMPANY_ID = "+companyId+" AND DF.markForDelete = 0 ", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverFamilyDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverFamilyDto>();
			DriverFamilyDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverFamilyDto();
				
				Documentto.setDFID((long) result[0]);
				Documentto.setDRIVERID((int) result[1]);
				Documentto.setDF_NAME((String) result[2]);
				Documentto.setDF_RELATIONSHIP_ID((short) result[3]);
				Documentto.setDF_SEX_ID((short) result[4]);
				Documentto.setDF_AGE((Integer) result[5]);
				Documentto.setCREATED_DATE((Date) result[6]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;

	
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverFamilyService#
	 * Delete_DriverFamily_Details(int)
	 */
	@Transactional
	public void Delete_DriverFamily_Details(Long DFID) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		driverFamilyDao.Delete_DriverFamily_Details(DFID, userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverFamilyService#
	 * Validate_Driver_Family_Member_Name(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<DriverFamily> Validate_Driver_Family_Member_Name(String string, Integer driverID) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return driverFamilyDao.Validate_Driver_Family_Member_Name(string, driverID, userDetails.getCompany_id());
	}

}
