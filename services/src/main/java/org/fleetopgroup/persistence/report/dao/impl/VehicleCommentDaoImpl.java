package org.fleetopgroup.persistence.report.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.fleetopgroup.persistence.report.dao.VehicleCommentDao;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleCommentDaoImpl implements VehicleCommentDao{

	@PersistenceContext
	public EntityManager entityManager;

	
	@Override
	public List<VehicleCommentDto> getVehicleCommentListByVid(VehicleCommentDto commentDto) throws Exception {
		try {

			TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT C.VEHICLE_COMMENTID, C.VEHICLE_TITLE, C.VEHICLE_ID, C.VEHICLE_COMMENT, U.firstName, U.email, C.CREATEDBYID, C.CREATED_DATE, V.vehicle_registration "
					+ " FROM VehicleComment AS C "
					+ " INNER JOIN Vehicle	V ON V.vid = C.VEHICLE_ID"
					+ " LEFT JOIN User U ON U.id = C.CREATEDBYID"
					+ " WHERE  C.VEHICLE_ID = "+commentDto.getVEHICLE_ID()+" AND C.CREATED_DATE BETWEEN '"+commentDto.getFromDate()+"' AND '"+commentDto.getToDate()+"' AND C.COMPANY_ID = "+commentDto.getCOMPANY_ID()+"  AND C.markForDelete = 0 ORDER BY C.VEHICLE_COMMENTID DESC",
					Object[].class);

			
			List<Object[]> results = query.getResultList();

			List<VehicleCommentDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleCommentDto>();
				VehicleCommentDto select = null;
				for (Object[] result : results) {
					
					select = new VehicleCommentDto();
					
					select.setVEHICLE_COMMENTID((Long) result[0]);
					select.setVEHICLE_TITLE((String) result[1]);
					select.setVEHICLE_ID((Integer) result[2]);
					select.setVEHICLE_COMMENT((String) result[3]);
					select.setCREATEDBY((String) result[4]);
					select.setCREATED_EMAIL((String) result[5]);
					select.setCREATEDBYID((Long) result[6]);
					select.setCREATED_DATE_ON((Date) result[7]);
					select.setVEHICLE_REGISTRATION((String) result[8]);
					
					Dtos.add(select);
				}
			}
			return Dtos;
		
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleCommentDto> getVehicleCommentListByGid(VehicleCommentDto commentDto) throws Exception {
		try {

			TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT C.VEHICLE_COMMENTID, C.VEHICLE_TITLE, C.VEHICLE_ID, C.VEHICLE_COMMENT, U.firstName, U.email, C.CREATEDBYID, C.CREATED_DATE, V.vehicle_registration "
					+ " FROM VehicleComment AS C "
					+ " INNER JOIN Vehicle	V ON V.vid = C.VEHICLE_ID"
					+ " LEFT JOIN User U ON U.id = C.CREATEDBYID"
					+ " WHERE  V.vehicleGroupId = "+commentDto.getVEHICLE_GROUP_ID()+" AND C.CREATED_DATE BETWEEN '"+commentDto.getFromDate()+"' AND '"+commentDto.getToDate()+"' AND C.COMPANY_ID = "+commentDto.getCOMPANY_ID()+"  AND C.markForDelete = 0 ORDER BY C.VEHICLE_COMMENTID DESC",
					Object[].class);

			
			List<Object[]> results = query.getResultList();

			List<VehicleCommentDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleCommentDto>();
				VehicleCommentDto select = null;
				for (Object[] result : results) {
					
					select = new VehicleCommentDto();
					
					select.setVEHICLE_COMMENTID((Long) result[0]);
					select.setVEHICLE_TITLE((String) result[1]);
					select.setVEHICLE_ID((Integer) result[2]);
					select.setVEHICLE_COMMENT((String) result[3]);
					select.setCREATEDBY((String) result[4]);
					select.setCREATED_EMAIL((String) result[5]);
					select.setCREATEDBYID((Long) result[6]);
					select.setCREATED_DATE_ON((Date) result[7]);
					select.setVEHICLE_REGISTRATION((String) result[8]);
					
					Dtos.add(select);
				}
			}
			return Dtos;
		
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleCommentDto> getVehicleCommentListByCompanyId(VehicleCommentDto commentDto) throws Exception {
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT C.VEHICLE_COMMENTID, C.VEHICLE_TITLE, C.VEHICLE_ID, C.VEHICLE_COMMENT, U.firstName, U.email, C.CREATEDBYID, C.CREATED_DATE, V.vehicle_registration "
					+ " FROM VehicleComment AS C "
					+ " INNER JOIN Vehicle	V ON V.vid = C.VEHICLE_ID"
					+ " LEFT JOIN User U ON U.id = C.CREATEDBYID"
					+ " WHERE  C.COMPANY_ID = "+commentDto.getCOMPANY_ID()+" AND C.CREATED_DATE BETWEEN '"+commentDto.getFromDate()+"' AND '"+commentDto.getToDate()+"'  AND C.markForDelete = 0 ORDER BY C.VEHICLE_COMMENTID DESC",
					Object[].class);

			
			List<Object[]> results = query.getResultList();

			List<VehicleCommentDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleCommentDto>();
				VehicleCommentDto select = null;
				for (Object[] result : results) {
					
					select = new VehicleCommentDto();
					
					select.setVEHICLE_COMMENTID((Long) result[0]);
					select.setVEHICLE_TITLE((String) result[1]);
					select.setVEHICLE_ID((Integer) result[2]);
					select.setVEHICLE_COMMENT((String) result[3]);
					select.setCREATEDBY((String) result[4]);
					select.setCREATED_EMAIL((String) result[5]);
					select.setCREATEDBYID((Long) result[6]);
					select.setCREATED_DATE_ON((Date) result[7]);
					select.setVEHICLE_REGISTRATION((String) result[8]);
					
					Dtos.add(select);
				}
			}
			return Dtos;
		
		
		} catch (Exception e) {
			throw e;
		}
	}
}
