package org.fleetopgroup.persistence.report.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.report.dao.DriverCommentDao;
import org.springframework.stereotype.Repository;

@Repository
public class DriverCommentDaoImpl implements DriverCommentDao{

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<DriverCommentDto> getDriverCommentListByDriverId(DriverCommentDto commentDto) throws Exception {
		//return driverCommentRepository.listDriverComment(diverComment, companyId);
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.driver_commentid, R.driver_title, R.driver_id, R.driver_comment, U.email, R.createdById, D.driver_firstname, D.driver_empnumber,R.created,D.driver_Lastname,D.driver_fathername "
				+ " FROM DriverComment AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.driver_id"
				+ " LEFT JOIN User U ON U.id = R.createdById"
				+ " WHERE  R.driver_id= "+commentDto.getDriver_id()+" AND  R.created BETWEEN '"+commentDto.getFromDate()+"' AND '"+commentDto.getToDate()+"'  "
				+ " AND R.companyId = "+commentDto.getCompanyId()+" AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverCommentDto>();
			DriverCommentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverCommentDto();
				
				Documentto.setDriver_commentid((Long) result[0]);
				Documentto.setDriver_title((String) result[1]);
				Documentto.setDriver_id((Integer) result[2]);
				Documentto.setDriver_comment((String) result[3]);
				Documentto.setCreatedBy((String) result[4]);
				Documentto.setCreatedById((Long) result[5]);
				Documentto.setDriver_firstname((String) result[6]);
				Documentto.setDriver_empnumber((String) result[7]);
				Documentto.setCreated((Date) result[8]);
				Documentto.setDriver_Lastname((String) result[9]);
				if(result[10] != null && !((String)result[10]).trim().equals("")) {
					Documentto.setDriverFatherName(" - "+result[10]);
				}else {
					Documentto.setDriverFatherName((String)result[10]);
				}
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	
	}

	@Override
	public List<DriverCommentDto> getDriverCommentListByGid(DriverCommentDto commentDto) throws Exception {
		//return driverCommentRepository.listDriverComment(diverComment, companyId);
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.driver_commentid, R.driver_title, R.driver_id, R.driver_comment, U.email, R.createdById, D.driver_firstname, D.driver_empnumber, R.created,D.driver_fathername,D.driver_Lastname "
				+ " FROM DriverComment AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.driver_id"
				+ " LEFT JOIN User U ON U.id = R.createdById"
				+ " WHERE  D.vehicleGroupId= "+commentDto.getVehicleGroupId()+" AND  R.created BETWEEN '"+commentDto.getFromDate()+"' AND '"+commentDto.getToDate()+"'  "
				+ " AND R.companyId = "+commentDto.getCompanyId()+" AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverCommentDto>();
			DriverCommentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverCommentDto();
				
				Documentto.setDriver_commentid((Long) result[0]);
				Documentto.setDriver_title((String) result[1]);
				Documentto.setDriver_id((Integer) result[2]);
				Documentto.setDriver_comment((String) result[3]);
				Documentto.setCreatedBy((String) result[4]);
				Documentto.setCreatedById((Long) result[5]);
				Documentto.setDriver_firstname((String) result[6]);
				Documentto.setDriver_empnumber((String) result[7]);
				Documentto.setCreated((Date) result[8]);
				if(result[9] != null && ((String)result[9]).trim().equals("")) {
					Documentto.setDriverFatherName(" - "+ result[9]);
				}else {
					Documentto.setDriverFatherName((String)result[9]);
				}
				Documentto.setDriver_Lastname((String) result[10]);
				Dtos.add(Documentto);
			}
		}
		return Dtos;
	
	}

	@Override
	public List<DriverCommentDto> getDriverCommentListByCompanyId(DriverCommentDto commentDto) throws Exception {
		//return driverCommentRepository.listDriverComment(diverComment, companyId);
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT R.driver_commentid, R.driver_title, R.driver_id, R.driver_comment, U.email, R.createdById, D.driver_firstname, D.driver_empnumber, R.created ,D.driver_fathername,D.driver_Lastname "
				+ " FROM DriverComment AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.driver_id"
				+ " LEFT JOIN User U ON U.id = R.createdById"
				+ " WHERE R.created BETWEEN '"+commentDto.getFromDate()+"' AND '"+commentDto.getToDate()+"'  "
				+ " AND R.companyId = "+commentDto.getCompanyId()+" AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<DriverCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverCommentDto>();
			DriverCommentDto Documentto = null;
			for (Object[] result : results) {
				Documentto = new DriverCommentDto();
				
				Documentto.setDriver_commentid((Long) result[0]);
				Documentto.setDriver_title((String) result[1]);
				Documentto.setDriver_id((Integer) result[2]);
				Documentto.setDriver_comment((String) result[3]);
				Documentto.setCreatedBy((String) result[4]);
				Documentto.setCreatedById((Long) result[5]);
				Documentto.setDriver_firstname((String) result[6]);
				Documentto.setDriver_empnumber((String) result[7]);
				Documentto.setCreated((Date) result[8]);
				if(result[9] != null && !((String)result[9]).trim().equals("")) {
					Documentto.setDriverFatherName(" - "+ result[9]);
				}else {
					Documentto.setDriverFatherName((String) result[9]);
				}
				Documentto.setDriver_Lastname((String) result[10]);

				Dtos.add(Documentto);
			}
		}
		return Dtos;
	
	}

	
	
}
