package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.persistence.dao.VehicleTypeRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("VehicleTypeService")
@Transactional
public class VehicleTypeService implements IVehicleTypeService {

	private static final int PAGE_SIZE = 25;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private VehicleTypeRepository vehicleTyperepository;

	// API

	@Override
	public VehicleType registerNewVehicleType(final VehicleType accountDto) throws Exception {

		return vehicleTyperepository.save(accountDto);
	}

	@Override
	@Transactional
	public void updateVehicleType(String vType, String updateBy, Date updateDate, long tid, Integer companyId, Integer maxAllowedOdometer, Long serviceProgramId)
			throws Exception {
		if(companyId == 0) {
			vehicleTyperepository.updateVehicleTypeST(vType, updateBy, updateDate, tid, companyId, maxAllowedOdometer, serviceProgramId);
		}else {
			vehicleTyperepository.updateVehicleType(vType, updateBy, updateDate, tid, companyId, maxAllowedOdometer, serviceProgramId);

		}
	}

	@Override
	public List<VehicleType> findAll() {

		return vehicleTyperepository.findAll();
	}

	@Override
	public VehicleType getVehicleType(String verificationToken) {
		return null;
	}

	@Override
	public void deleteVehicleType(long tid, Integer companyId) {

		vehicleTyperepository.deleteVehicleType(tid, companyId);
	}

	@Override
	public VehicleType getVehicleTypeByID(long tid, Integer companyId) {

		return vehicleTyperepository.getVehicleTypeByID(tid, companyId);
	}
	
	@Override
	public VehicleTypeDto getVehicleTypeDtoByID(long tid, Integer companyId) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT v.tid, v.vtype, v.maxAllowedOdometer, v.company_Id,"
						+ " SP.vehicleServiceProgramId, SP.programName, SP2.vehicleServiceProgramId, SP2.programName"
						+ " FROM VehicleType as v "
						+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
						+ " LEFT JOIN VehicleServiceProgram SP2 ON SP2.vehicleServiceProgramId = v.superProgramId"
						+ " where v.tid ="+tid+" AND (v.company_Id = " + companyId+" OR v.company_Id = 0)"
						+ " and v.markForDelete = 0 ");
		
			Object[] result = null;
			try {
				query.setMaxResults(1);
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			VehicleTypeDto list;
			if (result != null) {
				list = new VehicleTypeDto();
				list.setTid((Long) result[0]);
				list.setVtype((String) result[1]);
				list.setMaxAllowedOdometer((Integer) result[2]);
				list.setCompanyId((Integer) result[3]);
				list.setServiceProgramId((Long) result[4]);
				list.setProgramName((String) result[5]);
				
				if(list.getServiceProgramId() == null || list.getServiceProgramId() <= 0 || companyId.equals(0) || companyId.equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
					
					list.setServiceProgramId((Long) result[6]);
					list.setProgramName((String) result[7]);
					
				}
	
			} else {
				return null;
			}
			

		return list;
	
	}

	@Override
	public long count() {

		return vehicleTyperepository.count();
	}

	@Override
	public VehicleType findByVtype(String vtype, Integer company_Id) throws Exception {

		return vehicleTyperepository.findByVtype(vtype, company_Id);
	}

	@Override
	public List<VehicleType> findAllVehileTypeByCompanyId(Integer company_Id) throws Exception {
		try {
			return vehicleTyperepository.findAllVehileTypeByCompanyId(company_Id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<VehicleType> findAllVehileTypeByOnlyCompanyId(Integer company_Id) throws Exception {
		try {
			return vehicleTyperepository.findAllVehileTypeByOnlyCompanyId(company_Id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	@Override
	public long vehicleTypeCountForCompany(Integer company_Id) throws Exception {
		try {
			return vehicleTyperepository.vehicleTypeCountForCompany(company_Id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Page<VehicleType> getDeployment_Page_VehileType(Integer pageNumber, CustomUserDetails userDetails) throws Exception {

		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "tid");
		return vehicleTyperepository.getDeployment_Page_VehileType(userDetails.getCompany_id(), request);

	}

	@Override
	public List<VehicleTypeDto> GET_list_Of_VehileType(Integer pageNumber, CustomUserDetails userDetails) {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery("SELECT v.tid, v.vtype, v.maxAllowedOdometer, v.company_Id,"
				+ " SP.vehicleServiceProgramId, SP.programName, SP2.vehicleServiceProgramId, SP2.programName"
				+ " FROM VehicleType as v "
				+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
				+ " LEFT JOIN VehicleServiceProgram SP2 ON SP2.vehicleServiceProgramId = v.superProgramId"
				+ " where (v.company_Id = " + userDetails.getCompany_id()+" OR v.company_Id = 0)"
				+ " and v.markForDelete = 0 ", Object[].class);

		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
		List<Object[]> results = query.getResultList();

		List<VehicleTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTypeDto>();
			VehicleTypeDto list = null;
			for (Object[] result : results) {
				list = new VehicleTypeDto();
				
				list.setTid((Long) result[0]);
				list.setVtype((String) result[1]);
				list.setMaxAllowedOdometer((Integer) result[2]);
				list.setCompanyId((Integer) result[3]);
				list.setServiceProgramId((Long) result[4]);
				list.setProgramName((String) result[5]);
				
				if(list.getServiceProgramId() == null || list.getServiceProgramId() <= 0 || userDetails.getCompany_id().equals(0) || userDetails.getCompany_id().equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
					list.setServiceProgramId((Long) result[6]);
					list.setProgramName((String) result[7]);
					
				}
				
					Dtos.add(list);
			}
		}
		
		return Dtos;
	}


	
	
	@Override
	public List<VehicleTypeDto> getCompanyWiseVehcileType(Integer pageNumber, CustomUserDetails userDetails) {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery("SELECT v.tid, v.vtype, v.maxAllowedOdometer, v.company_Id,"
				+ " SP.vehicleServiceProgramId, SP.programName, SP2.vehicleServiceProgramId, SP2.programName"
				+ " FROM VehicleType as v "
				+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
				+ " LEFT JOIN VehicleServiceProgram SP2 ON SP2.vehicleServiceProgramId = v.superProgramId"
				+ " where (v.company_Id = " + userDetails.getCompany_id()+")"
				+ " and v.markForDelete = 0 ", Object[].class);

		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
		List<Object[]> results = query.getResultList();

		List<VehicleTypeDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTypeDto>();
			VehicleTypeDto list = null;
			for (Object[] result : results) {
				list = new VehicleTypeDto();
				
				list.setTid((Long) result[0]);
				list.setVtype((String) result[1]);
				list.setMaxAllowedOdometer((Integer) result[2]);
				list.setCompanyId((Integer) result[3]);
				list.setServiceProgramId((Long) result[4]);
				list.setProgramName((String) result[5]);
				
				if(list.getServiceProgramId() == null || list.getServiceProgramId() <= 0 || userDetails.getCompany_id().equals(0) || userDetails.getCompany_id().equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
					list.setServiceProgramId((Long) result[6]);
					list.setProgramName((String) result[7]);
					
				}
				
					Dtos.add(list);
			}
		}
		
		return Dtos;
	}

	
	@Override
	public List<VehicleType> getVehicleTypeByName(String term, Integer companyId) throws Exception {
		TypedQuery<VehicleType> queryt = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		queryt = entityManager.createQuery(
				"SELECT CA FROM VehicleType AS CA "
				+ " where ( lower(CA.vtype) Like ('%" + term+"%') ) "
				+ " AND CA.company_Id =  "+companyId + "and CA.markForDelete = 0",
				VehicleType.class);
	
		return 	queryt.getResultList();
		}else {
			return null;
		}
	}
	
	@Override
	public HashMap<Long, VehicleType> getVehicleTypeHMByCompanyId(Integer companyId) throws Exception {

		List<VehicleType> vehicleTypeList = null;
		HashMap<Long, VehicleType> vehicleTypeListHM = null;
		try {
			vehicleTypeListHM = new HashMap<Long, VehicleType>();
			vehicleTypeList=	findAllVehileTypeByCompanyId(companyId);

			if(vehicleTypeList != null && !vehicleTypeList.isEmpty()) {

				for(VehicleType vehicleType : vehicleTypeList) {
					vehicleTypeListHM.put(vehicleType.getTid(), vehicleType);	

				}
			}
			return vehicleTypeListHM;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleTypeList = null;
			vehicleTypeListHM = null;
		}


	}
	
}