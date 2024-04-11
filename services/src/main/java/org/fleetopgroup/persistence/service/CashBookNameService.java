/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.CashBookNameRepository;
import org.fleetopgroup.persistence.dto.CashBookNameDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fleetop
 * @Modified By Manish Singh
 */
@Service("CashBookNameService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CashBookNameService implements ICashBookNameService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CashBookNameRepository CashNameRepository;
	
	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICashBookNameService#add_CashBookName
	 * (org.fleetop.persistence.model.CashBookName)
	 */
	@Transactional
	public void add_CashBookName(CashBookName BookName) throws Exception {

		CashNameRepository.save(BookName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookNameService#
	 * update_CashBookName(org.fleetop.persistence.model.CashBookName)
	 */
	@Transactional
	public void update_CashBookName(CashBookName BookName) throws Exception {

		CashNameRepository.updateTripExpense(BookName.getCASHBOOK_NAME(), BookName.getCASHBOOK_REMARKS(),
				BookName.getLASTMODIFIEDBY(), BookName.getLASTMODIFIEDON() , BookName.getNAMEID(), BookName.getCOMPANY_ID(), BookName.getCASHBOOK_CODE());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICashBookNameService#listTripExpense(
	 * )
	 */
	@Transactional
	public List<CashBookName> list_CashBookName() throws Exception {

		return CashNameRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICashBookNameService#get_CashBookName
	 * (int)
	 */
	@Transactional
	public CashBookName get_CashBookName(int nameID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return CashNameRepository.get_CashBookName(nameID, userDetails.getCompany_id());
	}
	
	@Transactional
	@Override
	public CashBookName get_CashBookName2(int nameID, Integer companayId) throws Exception {
		return CashNameRepository.get_CashBookName(nameID, companayId);
	}
	
	@Override
	public CashBookNameDto get_CashBookName(int nameID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery("SELECT CB.NAMEID, CB.CASHBOOK_NAME, CB.CASHBOOK_REMARKS, CB.CREATEDBY, CB.CREATED,"
				+ "CB.LASTMODIFIEDBY, CB.LASTMODIFIEDON, CB.VEHICLE_GROUP_ID, VG.vGroup, CB.CASHBOOK_CODE "
				+ " FROM CashBookName AS CB "
				+ " LEFT JOIN VehicleGroup VG ON VG.gid = CB.VEHICLE_GROUP_ID"
				+ " WHERE  CB.NAMEID = "+nameID+" AND CB.COMPANY_ID = "+companyId+" AND CB.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		CashBookNameDto select;
		if (result != null) {
			select = new CashBookNameDto();
			
			select.setNAMEID((Integer) result[0]);
			select.setCASHBOOK_NAME((String) result[1]);
			select.setCASHBOOK_REMARKS((String) result[2]);
			select.setCREATEDBY((String) result[3]);
			select.setCREATED((Date) result[4]);
			select.setLASTMODIFIEDBY((String) result[5]);
			select.setLASTMODIFIEDON((Date) result[6]);
			select.setVEHICLE_GROUP_ID((long) result[7]);
			select.setVEHICLE_GROUP((String) result[8]);
			select.setCASHBOOK_CODE((String) result[9]);
			
		} else {
			return null;
		}

		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookNameService#
	 * delete_CashBookName(java.lang.Integer)
	 */
	@Transactional
	public void delete_CashBookName(Integer status, Integer companyId) throws Exception {

		CashNameRepository.delete_CashBookName(status, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookNameService#
	 * listOnly_CashBookName()
	 */
	@Transactional
	public List<CashBookName> listOnly_CashBookName() throws Exception {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookNameService#
	 * Validate_CashBookName(java.lang.String)
	 */
	@Transactional
	public CashBookName Validate_CashBookName(String cashNAME, String THREEWORKD, Integer companyId, long vg) throws Exception {

		return CashNameRepository.Validate_CashBookName(cashNAME, THREEWORKD, companyId, vg);
	}

	@Override
	public CashBookName getCashBookByName(String cashNAME, Integer companyId) throws Exception {
		
		return CashNameRepository.getCashBookByName(cashNAME, companyId);
	}
	
	@Override
	public CashBookName getCashBookByVehicleGroupId(long vehicleGroupID, Integer companyId) throws Exception {
		
		return CashNameRepository.getCashBookByVehicleGroupId(vehicleGroupID, companyId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookNameService#
	 * SearchCashBookName_DropDown(java.lang.String)
	 */
	@Override
	public List<CashBookName> SearchCashBookName_DropDown(String cashNAME) throws Exception {
		
		return null;
	}

	@Override
	public List<CashBookNameDto> list_CashBookName(Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> query =	null;
			query = entityManager.createQuery(
					"SELECT CBN.NAMEID, CBN.CASHBOOK_NAME, CBN.CASHBOOK_REMARKS, CBN.VEHICLE_GROUP_ID, VG.vGroup, CBN.CASHBOOK_CODE from CashBookName CBN "
					+ "LEFT JOIN VehicleGroup VG ON VG.gid = CBN.VEHICLE_GROUP_ID"
					+ " where CBN.COMPANY_ID = "+companyId+" and CBN.markForDelete = 0",
							Object[].class);
			List<Object[]> results = query.getResultList();

			List<CashBookNameDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<CashBookNameDto>();
				CashBookNameDto list = null;
				for (Object[] result : results) {
					list = new CashBookNameDto();

					list.setNAMEID((Integer) result[0]);
					list.setCASHBOOK_NAME((String) result[1]);
					list.setCASHBOOK_REMARKS((String) result[2]);
					list.setVEHICLE_GROUP_ID((long) result[3]);
					list.setVEHICLE_GROUP((String) result[4]);
					list.setCASHBOOK_CODE((String) result[5]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<CashBookName> list_CashBookNameByPermission(CustomUserDetails userDetails) throws Exception {
		try {
			
			TypedQuery<CashBookName> query =	null;
			if(!companyConfigurationService.getCashBookWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT CBN from CashBookName CBN "
								+ " where CBN.COMPANY_ID = "+userDetails.getCompany_id()+" and CBN.markForDelete = 0",
						CashBookName.class);
			}else {
				query = entityManager.createQuery(
						"SELECT CBN from CashBookName CBN "
								+ " INNER JOIN CashBookPermission CBP ON CBP.cashBookId = CBN.NAMEID AND user_Id = "+userDetails.getId()+""
								+ " where CBN.COMPANY_ID = "+userDetails.getCompany_id()+" and CBN.markForDelete = 0",
								CashBookName.class);
			}
			return query.getResultList();
			
			//return CashNameRepository.list_CashBookNameByPermission(userDetails.getId(), userDetails.getCompany_id());
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override 
	public List<CashBookName> list_cashBookByCompanyId(Integer companyId) throws Exception{
		
		return CashNameRepository.list_CashBookName(companyId);
	}
}
