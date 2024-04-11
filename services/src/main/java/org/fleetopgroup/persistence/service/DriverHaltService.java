package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.DriverHaltPlaceType;
import org.fleetopgroup.persistence.dao.DriverHaltRepository;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.model.DriverHalt;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("DriverHaltService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverHaltService implements IDriverHaltService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private DriverHaltRepository driverHaltDao;

	SimpleDateFormat dateFormat_Name 			= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat driverAttendanceFormat 	= new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat	toFixedTwo 					= new DecimalFormat("##.##");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * register_New_DriverHalt(org.fleetop.persistence.model.DriverHalt)
	 */
	@Transactional
	public DriverHalt register_New_DriverHalt(DriverHalt accountDto) throws Exception {

		return driverHaltDao.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * List_Total_OF_DriverHalt_details(java.lang.Integer)
	 */
	@Transactional
	public List<DriverHalt> List_Total_OF_DriverHalt_details(Integer driver_ID) {

		TypedQuery<DriverHalt> query = entityManager
				.createQuery("from DriverHalt RR where RR.DRIVERID=" + driver_ID + "  ", DriverHalt.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * Validate_DriverHalt(int, java.util.Date)
	 */
	@Transactional
	public DriverHalt Validate_DriverHalt(int driver_id, Date driver_Date) {

		return driverHaltDao.Validate_DriverHalt(driver_id, driver_Date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IDriverHaltService#update_DriverHalt(
	 * org.fleetop.persistence.model.DriverHalt)
	 */
	@Transactional
	public void update_DriverHalt(DriverHalt driverHalt) {

		driverHaltDao.update_DriverHalt(driverHalt.getVID(), driverHalt.getREFERENCE_NO(), driverHalt.getHALT_AMOUNT(),
				driverHalt.getHALT_REASON(), driverHalt.getLASTUPDATED(), driverHalt.getLASTUPDATED_BY_ID(),
				driverHalt.getDHID(), driverHalt.getCOMPANY_ID());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#list_DriverHalt(
	 * int)
	 */
	@Transactional
	public List<DriverHalt> list_DriverHalt(int driver_id) {

		return driverHaltDao.list_DriverHalt(driver_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * list_Date_DriverHalt(int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<DriverHalt> list_Date_DriverHalt(int driver_id, String from, String To) {

		return driverHaltDao.list_Date_DriverHalt(driver_id, from, To);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * list_Date_DriverHalt(int, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<DriverHalt> Validate_list_Date_DriverHalt(int driver_id, String from, String To, Integer companyId) {

		TypedQuery<DriverHalt> query = entityManager.createQuery("from DriverHalt RR where RR.DRIVERID=" + driver_id
				+ " AND RR.HALT_DATE_FROM ='" + from + "'  AND RR.HALT_DATE_TO ='" + To
				+ "' AND RR.markForDelete = 0 AND RR.COMPANY_ID = " + companyId + "", DriverHalt.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#get_DriverHalt(
	 * java.lang.Long)
	 */
	@Transactional
	public DriverHalt get_DriverHalt(Long driver_id) {

		return driverHaltDao.get_DriverHalt(driver_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IDriverHaltService#delete_DriverHalt(
	 * java.lang.Long)
	 */
	@Transactional
	public void delete_DriverHalt(Long driver_id, Integer companyId) {

		driverHaltDao.delete_DriverHalt(driver_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * Find_list_TripSheet_DriverHalt(java.lang.Long)
	 */
	@Transactional
	public List<DriverHaltDto> Find_list_TripSheet_DriverHalt(Long TripSheetID, int companyId) {
		
		// return driverHaltDao.Find_list_TripSheet_DriverHalt(TripSheetID,
		// userDetails.getCompany_id());

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select H.DHID, H.DRIVERID, DH.driver_firstname, H.HALT_AMOUNT, H.HALT_DATE_FROM, H.HALT_DATE_TO, "
						+ "  V.vehicle_registration, H.REFERENCE_NO, B.branch_name, H.HALT_REASON,"
						+ " U.firstName, VG.vGroup, DJT.driJobType, H.TRIPSHEETID, H.HALT_POINT ,DH.driver_Lastname, DH.driver_fathername From DriverHalt AS H"
						+ " INNER JOIN Driver AS DH ON DH.driver_id = H.DRIVERID "
						+ " LEFT JOIN Vehicle AS V ON V.vid = H.VID " 
						+ " INNER JOIN VehicleGroup VG ON VG.gid = DH.vehicleGroupId"
						+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = DH.driJobId "
						+ " LEFT JOIN Branch AS B ON B.branch_id = H.HALT_PLACE_ID " 
						+ " LEFT JOIN User AS U ON U.id = H.HALT_PAIDBY_ID "
						+ " where H.TRIPSHEETID=" + TripSheetID
						+ " AND H.COMPANY_ID =" + companyId
						+ " AND H.markForDelete=0  ORDER BY H.DHID asc",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<DriverHaltDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverHaltDto>();
			DriverHaltDto list = null;
			for (Object[] result : results) {
				list = new DriverHaltDto();
				list.setDHID((Long) result[0]);
				list.setDRIVERID((Integer) result[1]);
				list.setDRIVER_NAME((String) result[2]);
				list.setHALT_AMOUNT((Double) Double.parseDouble(toFixedTwo.format(result[3])));

				if ((Date) result[4] != null) {
					list.setHALT_DATE_FROM(dateFormat.format((Date) result[4]));
					list.setHALT_DATE_FROM_ON((Date) result[4]);
				}
				if ((Date) result[5] != null) {
					list.setHALT_DATE_TO(dateFormat.format((Date) result[5]));
					list.setHALT_DATE_TO_ON((Date) result[5]);
				}
				list.setVEHICLE_NAME((String) result[6]);
				list.setREFERENCE_NO((String) result[7]);
				list.setHALT_PLACE((String) result[8]);
				list.setHALT_REASON((String) result[9]);
				list.setHALT_PAIDBY((String) result[10]);
				/*
				 * This Trip Route is Driver Group to Showing Only Route name In
				 */
				list.setTRIP_ROUTE_NAME((String) result[11]);
				/* This Driver driver_jobtitle To Show in only CreatedBy In */
				list.setCREATEDBY((String) result[12]);
				
				list.setTRIPSHEETID((Long) result[13]);
				list.setHALT_POINT((Double) result[14]);
				if(result[15] != null)
				list.setDRIVER_NAME(list.getDRIVER_NAME()+" "+result[15]);
				if(result[16] != null)
					list.setDRIVER_NAME(list.getDRIVER_NAME()+" - "+result[16]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * Report_OF_Driver_Local_Halt(java.lang.String)
	 */
	@Transactional
	public List<DriverHaltDto> Report_OF_Driver_Local_Halt(String Haltquery) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select H.DHID, H.DRIVERID, DH.driver_firstname, H.HALT_AMOUNT, H.HALT_DATE_FROM, H.HALT_DATE_TO, "
						+ "  V.vehicle_registration, H.REFERENCE_NO, B.branch_name, H.HALT_REASON,"
						+ " U.firstName, VG.vGroup, DJT.driJobType, DH.driver_empnumber, DH.driver_fathername, DH.driver_Lastname From DriverHalt AS H"
						+ " INNER JOIN Driver AS DH ON DH.driver_id = H.DRIVERID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = DH.vehicleGroupId"
						+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = DH.driJobId "
						+ " LEFT JOIN Vehicle AS V ON V.vid = H.VID " 
						+ " LEFT JOIN Branch AS B ON B.branch_id = H.HALT_PLACE_ID " 
						+ " LEFT JOIN User AS U ON U.id = H.HALT_PAIDBY_ID "
						+ " where " + Haltquery + "  ORDER BY H.DHID asc",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<DriverHaltDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverHaltDto>();
			DriverHaltDto list = null;
			for (Object[] result : results) {
				list = new DriverHaltDto();

				list.setDHID((Long) result[0]);
				list.setDRIVERID((Integer) result[1]);
				list.setDRIVER_NAME((String) result[13]+"_"+(String) result[2] );
				list.setDriverLastName((String) result[15]);
				if(result[14] != null && !((String) result[14]).trim().equals(""))
				list.setDriverFatherName(" - "+result[14]);
				
				list.setHALT_AMOUNT((Double) result[3]);

				if ((Date) result[4] != null) {
					list.setHALT_DATE_FROM(dateFormat_Name.format((Date) result[4]));
				}
				if ((Date) result[5] != null) {
					list.setHALT_DATE_TO(dateFormat_Name.format((Date) result[5]));
				}
				list.setVEHICLE_NAME((String) result[6]);
				list.setREFERENCE_NO((String) result[7]);
				list.setHALT_PLACE((String) result[8]);
				list.setHALT_REASON((String) result[9]);
				list.setHALT_PAIDBY((String) result[10]);
				/*
				 * This Trip Route is Driver Group to Showing Only Route name In
				 */
				list.setTRIP_ROUTE_NAME((String) result[11]);
				/* This Driver driver_jobtitle To Show in only CreatedBy In */
				list.setCREATEDBY((String) result[12]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverHaltService#
	 * list_Of_DriverID_to_DriverHalt_BATA_Details(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<DriverHaltDto> list_Of_DriverID_to_DriverHalt_BATA_Details(Integer driver_id, String driverStart,
			String driverEnd, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"Select H.DHID, H.DRIVERID, DH.driver_firstname, H.HALT_AMOUNT, H.HALT_DATE_FROM, H.HALT_DATE_TO, "
						+ "  V.vehicle_registration, H.REFERENCE_NO, B.branch_name, H.HALT_REASON,"
						+ " U.firstName, VG.vGroup, DJT.driJobType, H.HALT_PLACE_TYPE_ID, H.TRIPSHEETID, TS.tripSheetNumber From DriverHalt AS H"
						+ " INNER JOIN Driver AS DH ON DH.driver_id = H.DRIVERID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = DH.vehicleGroupId"
						+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = DH.driJobId "
						+ " LEFT JOIN Vehicle AS V ON V.vid = H.VID "
						+ " LEFT JOIN User AS U ON U.id = H.HALT_PAIDBY_ID "
						+ " LEFT JOIN TripSheet AS TS ON TS.tripSheetID = H.TRIPSHEETID "
						+ " LEFT JOIN Branch AS B ON B.branch_id = H.HALT_PLACE_ID " + " where H.DRIVERID=" + driver_id
						+ " AND H.HALT_DATE_TO  BETWEEN '" + driverStart + "'  AND '" + driverEnd
						+ "' AND H.markForDelete = 0 AND H.COMPANY_ID = " + companyId + " ",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<DriverHaltDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverHaltDto>();
			DriverHaltDto list = null;
			for (Object[] result : results) {
				list = new DriverHaltDto();

				list.setDHID((Long) result[0]);
				list.setDRIVERID((Integer) result[1]);
				list.setDRIVER_NAME((String) result[2]);
				if(result[3] != null)
				list.setHALT_AMOUNT(Double.parseDouble(toFixedTwo.format(result[3])));

				if ((Date) result[4] != null) {
					list.setHALT_DATE_FROM(dateFormat_Name.format((Date) result[4]));
				}
				if ((Date) result[5] != null) {
					list.setHALT_DATE_TO(dateFormat_Name.format((Date) result[5]));
				}
				list.setVEHICLE_NAME((String) result[6]);
				list.setREFERENCE_NO((String) result[7]);
				list.setHALT_PLACE((String) result[8]);
				list.setHALT_REASON((String) result[9]);
				list.setHALT_PAIDBY((String) result[10]);
				/*
				 * This Trip Route is Driver Group to Showing Only Route name In
				 */
				list.setTRIP_ROUTE_NAME((String) result[11]);
				/* This Driver driver_jobtitle To Show in only CreatedBy In */
				list.setCREATEDBY((String) result[12]);

				list.setHALT_PLACE_TYPE_ID((short) result[13]);
				list.setHALT_PLACE_TYPE_NAME(DriverHaltPlaceType.getDriverHaltPlaceTypeName((short) result[13]));

				list.setTRIPSHEETID((Long) result[14]);
				list.setTripSheetNumber((Long) result[15]);

				Dtos.add(list);
			}

		}
		return Dtos;
	}

	@Override
	public DriverHaltDto GET_DriverHalt_Edit_BATA_Details(Long dHID, Integer company_id) {

		Query query = entityManager.createQuery(
				"Select H.DHID, H.DRIVERID, DH.driver_firstname, H.HALT_AMOUNT, H.HALT_DATE_FROM, H.HALT_DATE_TO, H.VID, "
						+ "  V.vehicle_registration, H.REFERENCE_NO, H.HALT_PLACE_ID, B.branch_name, H.HALT_REASON, H.HALT_REASON,"
						+ " U.firstName, VG.vGroup, DJT.driJobType, H.HALT_PLACE_TYPE_ID, H.HALT_POINT From DriverHalt AS H"
						+ " INNER JOIN Driver AS DH ON DH.driver_id = H.DRIVERID "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = DH.vehicleGroupId"
						+ " INNER JOIN DriverJobType DJT ON DJT.driJobId = DH.driJobId "
						+ " LEFT JOIN Vehicle AS V ON V.vid = H.VID "
						+ " LEFT JOIN User AS U ON U.id = H.HALT_PAIDBY_ID "
						+ " LEFT JOIN Branch AS B ON B.branch_id = H.HALT_PLACE_ID "
						+ " where H.DHID=:id AND H.HALT_PLACE_TYPE_ID=:placeId  AND H.markForDelete = 0 AND H.COMPANY_ID = " + company_id + " ");

		query.setParameter("id", dHID);
		query.setParameter("placeId", DriverHaltPlaceType.DRIVERHALT_PALCE_TYPE_LOCALHALT);
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		DriverHaltDto list = null;
		if (result != null) {

			list = new DriverHaltDto();

			list.setDHID((Long) result[0]);
			list.setDRIVERID((Integer) result[1]);
			list.setDRIVER_NAME((String) result[2]);
			if(result[3] != null)
			list.setHALT_AMOUNT(Double.parseDouble(toFixedTwo.format(result[3])));

			if ((Date) result[4] != null) {
				list.setHALT_DATE_FROM(dateFormat_Name.format((Date) result[4]));
			}
			if ((Date) result[5] != null) {
				list.setHALT_DATE_TO(dateFormat_Name.format((Date) result[5]));
			}
			list.setVID((Integer) result[6]);
			list.setVEHICLE_NAME((String) result[7]);
			list.setREFERENCE_NO((String) result[8]);
			list.setHALT_PLACE_ID((Integer) result[9]);
			list.setHALT_PLACE((String) result[10]);
			list.setHALT_REASON((String) result[11]);

			list.setHALT_REASON((String) result[12]);
			list.setHALT_PAIDBY((String) result[13]);
			/*
			 * This Trip Route is Driver Group to Showing Only Route name In
			 */
			list.setTRIP_ROUTE_NAME((String) result[14]);
			/* This Driver driver_jobtitle To Show in only CreatedBy In */
			list.setCREATEDBY((String) result[15]);

			list.setHALT_PLACE_TYPE_ID((short) result[16]);
			list.setHALT_PLACE_TYPE_NAME(DriverHaltPlaceType.getDriverHaltPlaceTypeName((short) result[16]));
			list.setHALT_POINT((Double) result[17]);

		}

		return list;
	}
	
	@Override
	public List<DriverHaltDto> getBataDetails(Integer did, String dateRangeFrom,
			String dateRangeTo) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT DH.DRIVERID, DH.HALT_AMOUNT, DH.TRIPSHEETID"
							+ " FROM DriverHalt DH "
							+ " where DH.DRIVERID = "+did+" AND DH.HALT_DATE_TO BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "'  ORDER BY DH.CREATED desc ",	
							Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DriverHaltDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DriverHaltDto	batahalt = new DriverHaltDto();
					
					
					batahalt.setDRIVERID((Integer) result[0]);
					batahalt.setHALT_AMOUNT((Double) result[1]);
					batahalt.setTRIPSHEETID((Long) result[2]);
					
					list.add(batahalt);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteDriverHaltByTripSheetId(Long tripSheeetId) throws Exception {
		driverHaltDao.deleteDriverHaltByTripSheetId(tripSheeetId);
	}
}
