package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.DriverSalaryRepository;
import org.fleetopgroup.persistence.dto.DriverSalaryDto;
import org.fleetopgroup.persistence.model.DriverSalary;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("DriverSalaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriverSalaryService implements IDriverSalaryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private DriverSalaryRepository driverSalaryDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void Add_Driver_Salary(DriverSalary driver) {

		driverSalaryDao.save(driver);
	}

	@Transactional
	public List<DriverSalary> ValidateDriverSalary(Date fromDate, Date toDate, Integer Driver_id, long DriverGroup, Integer companyId) {

		return driverSalaryDao.ValidateDriverSalary(fromDate, toDate, Driver_id, DriverGroup, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryService#
	 * Driver_MONTH_WISE_ESI_PF_ReportDate(java.lang.String)
	 */
	@Transactional
	public List<DriverSalaryDto> Driver_MONTH_WISE_ESI_PF_ReportDate(String query, Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DSID, R.DRIVER_ID, D.driver_firstname, D.driver_Lastname, D.driver_empnumber, VG.vGroup, R.ESIAMOUNT_NO,"
				+ " R.MONTH_PERDAYSALARY, R.MONTH_SALARY, R.MONTH_EXTRA_SALARY, R.PFAMOUNT_NO, R.SALARY_FROM_DATE, R.SALARY_TO_DATE,"
				+ " R.TOTAL_PREVIOUS_ADVANCE, R.TOTAL_ADVANCE, R.TOTAL_PENALTY, R.TOTAL_ADVANCE_PENALTY, R.TOTAL_ADVANCE_BALANCE,"
				+ " R.TOTAL_ADVANCE_DEDUCTION, R.TOTAL_PENALTY_DEDUCTION, R.TOTAL_ALLOWANCE, R.TOTAL_OTHEREXTRA, R.TOTAL_ESIAMOUNT,"
				+ " R.TOTAL_PFAMOUNT, R.TOTAL_NETSALARY, R.TOTAL_EXTRA_NETSALARY, R.TOTAL_WORKINGDAY,"
				+ " R.TOTAL_EXTRA_WORKINGDAY, R.TOTAL_HANDSALARY, R.SALARY_STATUS_ID, R.LASTUPDATED_DATE, R.CREATED_DATE, U.email,"
				+ " U2.email, D.driver_esino, D.driver_pfno, D.driver_banknumber ,D.driver_fathername "
				+ " FROM DriverSalary AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.DRIVER_ID "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
				+ " LEFT JOIN User U ON U.id = R.LASTMODIFIED_BY_ID"
				+ " LEFT JOIN User U2 ON U2.id = R.CREATED_BY_ID"
				+ " Where R.markForDelete=0  AND R.COMPANY_ID = "+companyId+" " + query + " ORDER BY D.driver_empnumber asc ", Object[].class);
		List<Object[]> results = null;
		try {
			results = queryt.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<DriverSalaryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverSalaryDto>();
			DriverSalaryDto list = null;
			for (Object[] result : results) {
				list = new DriverSalaryDto();

				list.setDSID((Long) result[0]);
				list.setDRIVER_ID((Integer) result[1]);
				list.setDRIVER_FIRSTNAME((String) result[2]);
				list.setDRIVER_LASTNAME((String) result[3]);
				list.setDRIVER_EMPNUMBER((String) result[4]);
				list.setDRIVER_GROUP((String) result[5]);
				list.setESIAMOUNT_NO((Double) result[6]);
				list.setMONTH_PERDAYSALARY((Double) result[7]);
				list.setMONTH_SALARY((Double) result[8]);
				list.setMONTH_EXTRA_SALARY((Double) result[9]);
				list.setPFAMOUNT_NO((Double) result[10]);
				list.setSALARY_FROM_DATE_ON((Date) result[11]);
				list.setSALARY_TO_DATE_ON((Date) result[12]);
				list.setTOTAL_PREVIOUS_ADVANCE((Double) result[13]);
				list.setTOTAL_ADVANCE((Double) result[14]);
				list.setTOTAL_PENALTY((Double) result[15]);
				list.setTOTAL_ADVANCE_PENALTY((Double) result[16]);
				list.setTOTAL_ADVANCE_BALANCE((Double) result[17]);
				list.setTOTAL_ADVANCE_DEDUCTION((Double) result[18]);
				list.setTOTAL_PENALTY_DEDUCTION((Double) result[19]);
				list.setTOTAL_ALLOWANCE((Double) result[20]);
				list.setTOTAL_OTHEREXTRA((Double) result[21]);
				list.setTOTAL_ESIAMOUNT((Double) result[22]);
				list.setTOTAL_PFAMOUNT((Double) result[23]);
				list.setTOTAL_NETSALARY((Double) result[24]);
				list.setTOTAL_EXTRA_NETSALARY((Double) result[25]);
				list.setTOTAL_WORKINGDAY((Long) result[26]);
				list.setTOTAL_EXTRA_WORKINGDAY((Long) result[27]);
				list.setTOTAL_HANDSALARY((Double) result[28]);
				list.setSALARY_STATUS_ID((short) result[29]);
				list.setLASTUPDATED_DATE_ON((Date) result[30]);
				list.setCREATED_DATE_ON((Date) result[31]);
				list.setLASTMODIFIEDBY((String) result[32]);
				list.setCREATEDBY((String) result[33]);
				list.setDRIVER_ESINO((String) result[34]);
				list.setDRIVER_PFNO((String) result[35]);
				list.setDRIVER_BANKNUMBER((String) result[36]);
				if(result[37] != null && !((String) result[37]).trim().equals(""))
				list.setDriverFatherName(" - "+result[37]);
				Dtos.add(list);
			}

		}
		return Dtos;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IDriverSalaryService#
	 * Driver_MONTH_WISE_ESI_PF_ReportDate(java.lang.String)
	 */
	@Transactional
	public List<DriverSalaryDto> Driver_MONTH_WISE_ATTENDANCE_ReportDate(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT R.DSID, R.DRIVER_ID, D.driver_firstname, D.driver_Lastname, D.driver_empnumber, DDT.driJobType, R.TOTAL_WORKINGDAY,"
						+ " R.TOTAL_EXTRA_WORKINGDAY, R.DRIVER_SALARY_TYPE_ID, R.TRIP_SHEET_COUNT, R.TOTAL_KM_USAGE, D.driver_fathername "
						+ " From DriverSalary AS R "
						+ " LEFT JOIN Driver AS D ON R.DRIVER_ID=D.driver_id "
						+ " LEFT JOIN DriverJobType DDT ON DDT.driJobId = D.driJobId"
						+ " where R.markForDelete=0  AND R.SALARY_TO_DATE between '" + dateRangeFrom + "' AND '"
						+ dateRangeTo + "'   " + dRIVER_GROUP + " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0  ORDER BY D.driver_empnumber asc ", Object[].class);

		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<DriverSalaryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverSalaryDto>();
			DriverSalaryDto list = null;
			for (Object[] result : results) {
				list = new DriverSalaryDto();

				list.setDSID((Long) result[0]);
				list.setDRIVER_ID((Integer) result[1]);
				list.setDRIVER_FIRSTNAME((String) result[2]);
				list.setDRIVER_LASTNAME((String) result[3]);
				list.setDRIVER_EMPNUMBER((String) result[4]);
				list.setDRIVER_JOBTITLE((String) result[5]);

				Long TOTAL_WORKINGDAY 				= (long) 0;
				Long TOTAL_EXTRA_WORKINGDAY 		= (long) 0;
				Long TOTAL_ALL_WORKINGDAY 			= (long) 0;
				if((Long) result[6] != null) {
					TOTAL_WORKINGDAY = ((Long) result[6]);
				}
				if((Long) result[7] != null) {
					TOTAL_EXTRA_WORKINGDAY = ((Long) result[7]);
				}
				list.setTOTAL_WORKINGDAY(TOTAL_WORKINGDAY);

				list.setTOTAL_EXTRA_WORKINGDAY(TOTAL_EXTRA_WORKINGDAY);
				
				TOTAL_ALL_WORKINGDAY =  TOTAL_WORKINGDAY + TOTAL_EXTRA_WORKINGDAY ;
				list.setTOTAL_ALL_WORKINGDAY(TOTAL_ALL_WORKINGDAY);

				list.setDRIVER_SALARY_TYPE_ID((Short) result[8]);
				list.setTRIP_SHEET_COUNT(((Long) result[9]) + "(Trip)");
				list.setTOTAL_KM_USAGE(((Long) result[10]) + "(KM)");
				
						System.err.println(result[11]);
				if(result[11] != null)
				list.setDriverFatherName(" - "+ result[11]);
				
				Dtos.add(list);
			}

		}
		return Dtos;
	}
	

	@Transactional
	public Object[] GET_TOTAL_MONTHBASED_SALARY_ESI_PF_AMOUNT(String lastMonthFROM, String lastMonthTo) {

		Query queryt = entityManager.createQuery(
				"SELECT SUM(co.TOTAL_SALARY),(SELECT SUM(Pho.TOTAL_ESIAMOUNT) FROM DriverSalary AS Pho where  Pho.SALARY_FROM_DATE >= '"
						+ lastMonthFROM + "' AND Pho.SALARY_TO_DATE <= '" + lastMonthTo + "' ), "

						+ " (SELECT  SUM(FE.TOTAL_PFAMOUNT) FROM DriverSalary AS FE where  FE.SALARY_FROM_DATE >= '"
						+ lastMonthFROM + "' AND FE.SALARY_TO_DATE <= '" + lastMonthTo + "' )"
						+ " FROM DriverSalary As co Where co.SALARY_FROM_DATE >= '" + lastMonthFROM
						+ "' AND co.SALARY_TO_DATE <= '" + lastMonthTo + "' ");
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	@Transactional
	public List<DriverSalaryDto> List_Total_OF_DriverSalary_details(Integer dRIVER_ID, Integer companyId) {
		//return driverSalaryDao.List_Total_OF_DriverSalary_details(dRIVER_ID, companyId);
		


		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.DSID, R.DRIVER_ID, D.driver_firstname, D.driver_Lastname, D.driver_empnumber, VG.vGroup, R.ESIAMOUNT_NO,"
				+ " R.MONTH_PERDAYSALARY, R.MONTH_SALARY, R.MONTH_EXTRA_SALARY, R.PFAMOUNT_NO, R.SALARY_FROM_DATE, R.SALARY_TO_DATE,"
				+ " R.TOTAL_PREVIOUS_ADVANCE, R.TOTAL_ADVANCE, R.TOTAL_PENALTY, R.TOTAL_ADVANCE_PENALTY, R.TOTAL_ADVANCE_BALANCE,"
				+ " R.TOTAL_ADVANCE_DEDUCTION, R.TOTAL_PENALTY_DEDUCTION, R.TOTAL_ALLOWANCE, R.TOTAL_OTHEREXTRA, R.TOTAL_ESIAMOUNT,"
				+ " R.TOTAL_PFAMOUNT, R.TOTAL_NETSALARY, R.TOTAL_EXTRA_NETSALARY, R.TOTAL_WORKINGDAY,"
				+ " R.TOTAL_EXTRA_WORKINGDAY, R.TOTAL_HANDSALARY, R.SALARY_STATUS_ID, R.LASTUPDATED_DATE, R.CREATED_DATE, U.email,"
				+ " U2.email "
				+ " FROM DriverSalary AS R "
				+ " INNER JOIN Driver D ON D.driver_id = R.DRIVER_ID "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
				+ " LEFT JOIN User U ON U.id = R.LASTMODIFIED_BY_ID"
				+ " LEFT JOIN User U2 ON U2.id = R.CREATED_BY_ID"
				+ " where R.DRIVER_ID= "+dRIVER_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.DSID ASC", Object[].class);
		List<Object[]> results = null;
		try {
			results = queryt.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		List<DriverSalaryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverSalaryDto>();
			DriverSalaryDto list = null;
			for (Object[] result : results) {
				list = new DriverSalaryDto();

				list.setDSID((Long) result[0]);
				list.setDRIVER_ID((Integer) result[1]);
				list.setDRIVER_FIRSTNAME((String) result[2]);
				list.setDRIVER_LASTNAME((String) result[3]);
				list.setDRIVER_EMPNUMBER((String) result[4]);
				list.setDRIVER_GROUP((String) result[5]);
				list.setESIAMOUNT_NO((Double) result[6]);
				list.setMONTH_PERDAYSALARY((Double) result[7]);
				list.setMONTH_SALARY((Double) result[8]);
				list.setMONTH_EXTRA_SALARY((Double) result[9]);
				list.setPFAMOUNT_NO((Double) result[10]);
				list.setSALARY_FROM_DATE_ON((Date) result[11]);
				list.setSALARY_TO_DATE_ON((Date) result[12]);
				list.setTOTAL_PREVIOUS_ADVANCE((Double) result[13]);
				list.setTOTAL_ADVANCE((Double) result[14]);
				list.setTOTAL_PENALTY((Double) result[15]);
				list.setTOTAL_ADVANCE_PENALTY((Double) result[16]);
				list.setTOTAL_ADVANCE_BALANCE((Double) result[17]);
				list.setTOTAL_ADVANCE_DEDUCTION((Double) result[18]);
				list.setTOTAL_PENALTY_DEDUCTION((Double) result[19]);
				list.setTOTAL_ALLOWANCE((Double) result[20]);
				list.setTOTAL_OTHEREXTRA((Double) result[21]);
				list.setTOTAL_ESIAMOUNT((Double) result[22]);
				list.setTOTAL_PFAMOUNT((Double) result[23]);
				list.setTOTAL_NETSALARY((Double) result[24]);
				list.setTOTAL_EXTRA_NETSALARY((Double) result[25]);
				list.setTOTAL_WORKINGDAY((Long) result[26]);
				list.setTOTAL_EXTRA_WORKINGDAY((Long) result[27]);
				list.setTOTAL_HANDSALARY((Double) result[28]);
				list.setSALARY_STATUS_ID((short) result[29]);
				list.setLASTUPDATED_DATE_ON((Date) result[30]);
				list.setCREATED_DATE_ON((Date) result[31]);
				list.setLASTMODIFIEDBY((String) result[32]);
				list.setCREATEDBY((String) result[33]);
				Dtos.add(list);
			}

		}
		return Dtos;
	
	}

	@Transactional
	public DriverSalaryDto Get_DriverSalary_details_SALARY_ID(Long sALARY_ADVANCE_ID, Integer companyId) throws Exception {
		/*CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return driverSalaryDao.Get_DriverSalary_details_SALARY_ID(sALARY_ADVANCE_ID, userDetails.getCompany_id());*/
		


		Query query = entityManager.createQuery(
				" SELECT R.DSID, R.DRIVER_ID, D.driver_firstname, D.driver_Lastname, D.driver_empnumber, VG.vGroup,"
				+ " R.ESIAMOUNT_NO, R.MONTH_PERDAYSALARY, R.MONTH_SALARY, R.MONTH_EXTRA_SALARY, R.PFAMOUNT_NO, R.SALARY_FROM_DATE,"
				+ " R.SALARY_TO_DATE, R.TOTAL_PREVIOUS_ADVANCE, R.TOTAL_ADVANCE, R.TOTAL_PENALTY, R.TOTAL_ADVANCE_PENALTY,"
				+ " R.TOTAL_ADVANCE_BALANCE, R.TOTAL_HANDSALARY, R.TOTAL_ESIAMOUNT, R.TOTAL_PFAMOUNT, R.TOTAL_NETSALARY, R.TOTAL_EXTRA_NETSALARY,"
				+ " R.TOTAL_WORKINGDAY, R.TOTAL_EXTRA_WORKINGDAY, R.SALARY_STATUS_ID, R.LASTUPDATED_DATE, U.email, R.CREATED_DATE,"
				+ " U2.email "
				+ " From DriverSalary R "
				+ " INNER JOIN Driver D ON D.driver_id = R.DRIVER_ID "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = D.vehicleGroupId"
				+ " LEFT JOIN User U ON U.id = R.LASTMODIFIED_BY_ID"
				+ " LEFT JOIN User U2 ON U2.id = R.CREATED_BY_ID"
				+ " where R.DSID= "+sALARY_ADVANCE_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		DriverSalaryDto select;
		if (result != null) {
			select = new DriverSalaryDto();
			
			select.setDSID((Long) result[0]);
			select.setDRIVER_ID((Integer) result[1]);
			select.setDRIVER_FIRSTNAME((String) result[2]);
			select.setDRIVER_LASTNAME((String) result[3]);
			select.setDRIVER_EMPNUMBER((String) result[4]);
			select.setDRIVER_GROUP((String) result[5]);
			select.setESIAMOUNT_NO((Double) result[6]);
			select.setMONTH_PERDAYSALARY((Double) result[7]);
			select.setMONTH_SALARY((Double) result[8]);
			select.setMONTH_EXTRA_SALARY((Double) result[9]);
			select.setPFAMOUNT_NO((Double) result[10]);
			select.setSALARY_FROM_DATE_ON((Date) result[11]);
			select.setSALARY_TO_DATE_ON((Date) result[12]);
			select.setTOTAL_PREVIOUS_ADVANCE((Double) result[13]);
			select.setTOTAL_ADVANCE((Double) result[14]);
			select.setTOTAL_PENALTY((Double) result[15]);
			select.setTOTAL_ADVANCE_PENALTY((Double) result[16]);
			select.setTOTAL_ADVANCE_BALANCE((Double) result[17]);
			select.setTOTAL_HANDSALARY((Double) result[18]);
			select.setTOTAL_ESIAMOUNT((Double) result[19]);
			select.setTOTAL_PFAMOUNT((Double) result[20]);
			select.setTOTAL_NETSALARY((Double) result[21]);
			select.setTOTAL_EXTRA_NETSALARY((Double) result[22]);
			select.setTOTAL_WORKINGDAY((Long) result[23]);
			select.setTOTAL_EXTRA_WORKINGDAY((Long) result[24]);
			select.setSALARY_STATUS_ID((short) result[25]);
			select.setLASTUPDATED_DATE_ON((Date) result[26]);
			select.setLASTMODIFIEDBY((String) result[27]);
			select.setCREATED_DATE_ON((Date) result[28]);
			select.setCREATEDBY((String) result[29]);
			
		} else {
			return null;
		}

		return select;
	
		
	}

	@Transactional
	public void UPDATE_DriverSalary_AdvacneAmount_HandAmount_Status(Double netPayAdvance,  Double TOTAL_ADVANCE_DEDUCTION,  Double TOTAL_PENALTY_DEDUCTION,  Double netInHandSalary,
			short Status, Double previousAdvance, Double totalPenaly, Double advanceBalance, Long sALARY_ADVANCE_ID, Integer companyId) {
		
		driverSalaryDao.UPDATE_DriverSalary_AdvacneAmount_HandAmount_Status(netPayAdvance, TOTAL_ADVANCE_DEDUCTION, TOTAL_PENALTY_DEDUCTION, netInHandSalary, Status, previousAdvance,totalPenaly, advanceBalance, sALARY_ADVANCE_ID, companyId);
	}

}
