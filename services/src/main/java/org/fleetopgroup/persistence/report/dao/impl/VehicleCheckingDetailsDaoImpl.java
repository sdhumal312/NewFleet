package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.VehicleCheckingDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleCheckingDetailsDto;
import org.fleetopgroup.persistence.model.VehicleCheckingDetails;
import org.fleetopgroup.persistence.report.dao.VehicleCheckingDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleCheckingDetailsDaoImpl implements VehicleCheckingDetailsDao{
	
	@PersistenceContext
	public EntityManager entityManager;
	
	@Autowired	ICompanyConfigurationService		companyConfigurationService;
	@Autowired	VehicleCheckingDetailsRepository	vehicleCheckingDetailsRepository;

	private static final int PAGE_SIZE = 10;
	@Override
	public List<VehicleCheckingDetailsDto> getCheckingEntryReport(VehicleCheckingDetailsDto checkingDetailsDto,
			String sqlQuery) throws Exception {
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT VCD.checkingId, VCD.checkingInspectorId, VCD.conductorId, VCD.vid, VCD.checkingDateTime, VCD.checkingTime,"
					+ " VCD.place, VCD.noOfSeat, VCD.remark, D.driver_empnumber, D.driver_firstname, D2.driver_empnumber, D2.driver_firstname, "
					+ " V.vehicle_registration, VCD.checkingOutTime, VCD.outPlace, VCD.vehicleGroupId "
					+ " From VehicleCheckingDetails AS VCD "
					+ " INNER JOIN Driver AS D ON D.driver_id = VCD.checkingInspectorId"
					+ " LEFT JOIN Driver AS D2 ON D2.driver_id = VCD.conductorId"
					+ " INNER JOIN Vehicle AS V ON V.vid = VCD.vid"
					+ " WHERE VCD.checkingDateTime between '"+checkingDetailsDto.getFromDate()+"' AND '"+checkingDetailsDto.getToDate()+"' "
					+ " AND VCD.companyId = "+checkingDetailsDto.getCompanyId()+" "+ sqlQuery+" AND VCD.markForDelete = 0 ORDER BY VCD.checkingId DESC",
					Object[].class);

			
			List<Object[]> results = query.getResultList();

			List<VehicleCheckingDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleCheckingDetailsDto>();
				VehicleCheckingDetailsDto select = null;
				for (Object[] result : results) {
					
					select = new VehicleCheckingDetailsDto();
					select.setCheckingId((Long) result[0]);
					select.setCheckingInspectorId((Integer) result[1]);
					select.setConductorId((Integer) result[2]);
					select.setVid((Integer) result[3]);
					if(result[4] != null)
						select.setCheckingDate(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[4], DateTimeUtility.DD_MM_YYYY));
					
					if(result[5] != null) {
						select.setCheckingTime((String)result[5]);
					}else {
						select.setCheckingTime("--");
					}
						
					if(result[6] != null) {
						select.setPlace((String) result[6]);
					}else {
						select.setPlace("--");
					}
					
					select.setNoOfSeat((Integer) result[7]);
					if(result[8] != null) {
						select.setRemark((String) result[8]);
					}else {
						select.setRemark("--");
					}
					
					select.setCheckingInspectorName((String) result[9]+"_"+(String) result[10]);
					if(result[11] != null) {
						select.setConductorName((String) result[11]+"_"+(String) result[12]);
					}else {
						select.setConductorName("--");
					}
					select.setVehicle_registration((String) result[13]);
					if(result[14] != null) {
						select.setCheckingOutTime((String) result[14]);
					}else {
						select.setCheckingOutTime("--");
					}
					
					if(result[15] != null) {
						select.setOutPlace((String) result[15]);
					}else {
						select.setOutPlace("--");
					}
					
					select.setVehicleGroupId((Long) result[16]);
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<VehicleCheckingDetails> getDeployment_Page_Checking(Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		try {
			@SuppressWarnings("deprecation")
			PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vid");
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return vehicleCheckingDetailsRepository.getDeployment_Page_Checking(userDetails.getCompany_id(), request);
			}
			return vehicleCheckingDetailsRepository.getDeployment_Page_Checking(userDetails.getCompany_id(), userDetails.getId() , request);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleCheckingDetailsDto> getCheckingEntryList(Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT VCD.checkingId, VCD.checkingInspectorId, VCD.conductorId, VCD.vid, VCD.checkingDateTime, VCD.checkingTime,"
					+ " VCD.place, VCD.noOfSeat, VCD.remark, D.driver_empnumber, D.driver_firstname, D2.driver_empnumber, D2.driver_firstname, "
					+ " V.vehicle_registration, VCD.checkingOutTime, VCD.outPlace, VCD.vehicleGroupId "
					+ " From VehicleCheckingDetails AS VCD "
					+ " INNER JOIN Driver AS D ON D.driver_id = VCD.checkingInspectorId"
					+ " LEFT JOIN Driver AS D2 ON D2.driver_id = VCD.conductorId"
					+ " INNER JOIN Vehicle AS V ON V.vid = VCD.vid "
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
					+ " WHERE  VCD.companyId = "+userDetails.getCompany_id()+" AND VCD.markForDelete = 0 ORDER BY VCD.checkingId DESC",
					Object[].class);
			
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			
			List<Object[]> results = query.getResultList();

			List<VehicleCheckingDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleCheckingDetailsDto>();
				VehicleCheckingDetailsDto select = null;
				for (Object[] result : results) {
					
					select = new VehicleCheckingDetailsDto();
					select.setCheckingId((Long) result[0]);
					select.setCheckingInspectorId((Integer) result[1]);
					select.setConductorId((Integer) result[2]);
					select.setVid((Integer) result[3]);
					if(result[4] != null)
						select.setCheckingTime(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[4]) +" "+ (String)result[5]);
					if(result[6] != null) {
						select.setPlace((String) result[6]);
					}else {
						select.setPlace("--");
					}
					
					select.setNoOfSeat((Integer) result[7]);
					if(result[8] != null) {
						select.setRemark((String) result[8]);
					}else {
						select.setRemark("--");
					}
					
					select.setCheckingInspectorName((String) result[9]+"_"+(String) result[10]);
					if(result[11] != null) {
						select.setConductorName((String) result[11]+"_"+(String) result[12]);
					}else {
						select.setConductorName("--");
					}
					select.setVehicle_registration((String) result[13]);
					
					if(result[14] != null) {
						select.setCheckingOutTime((String) result[14]);
					}else {
						select.setCheckingOutTime("--");
					}
					
					if(result[15] != null) {
						select.setOutPlace((String) result[15]);
					}else {
						select.setOutPlace("--");
					}
					
					select.setVehicleGroupId((Long) result[16]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<VehicleCheckingDetailsDto> getConductorHistoryReportList(String query, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT VCD.checkingId, VCD.conductorId, VCD.vid, VCD.checkingDateTime, "
					+ " VCD.route, VCD.description, VCD.punishment, VCD.orderNoAndDate "
					+ " FROM VehicleCheckingDetails VCD "	
					+ " WHERE  VCD.companyId = "+companyId+" AND VCD.markForDelete = 0 "+query+" ORDER BY VCD.checkingId DESC",
					Object[].class);
			
			
			List<Object[]> results = queryt.getResultList();

			List<VehicleCheckingDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleCheckingDetailsDto>();
				VehicleCheckingDetailsDto select = null;
				for (Object[] result : results) {
					
					select = new VehicleCheckingDetailsDto();
					select.setCheckingId((Long) result[0]);
					select.setConductorId((Integer) result[1]);
					select.setVid((Integer) result[2]);
					if(result[3] != null)
					select.setCheckingDate(DateTimeUtility.getDateFromTimeStamp((Timestamp)result[3], DateTimeUtility.DD_MM_YY));
					if(result[4] != null)
					select.setRoute((String) result[4]);
					if(result[5] != null)
					select.setDescription((String) result[5]);
					if(result[6] != null)
					select.setPunishment((String) result[6]);
					if(result[7] != null)
					select.setOrderNoAndDate((String) result[7]);
					
					Dtos.add(select);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
}
