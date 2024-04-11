package org.fleetopgroup.persistence.report.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dto.MobileUserDto;
import org.fleetopgroup.persistence.report.dao.MobileUserDataDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MobileUserDataDaoImpl implements MobileUserDataDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public MobileUserDto getUserDataForMobileApp(String companyCode, String email) throws Exception {

		MobileUserDto				mobileUserDto 			= null;

		try {

			Query queryt = entityManager.createQuery(
					"SELECT U.email , C.companyCode , U.id , U.company_id , U.firstName , U.lastName , C.company_name , U.enabled , U.password "
							+" FROM User AS U INNER JOIN Company AS C ON U.company_id = C.company_id "
							+" WHERE C.companyCode = '"+companyCode+"' AND U.email = '"+email+"' ");


			Object[] result = null;
			try {
				queryt.setMaxResults(1);
				result = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			if (result != null) {
				mobileUserDto = new MobileUserDto();

				mobileUserDto.setEmail((String) result[0]);
				mobileUserDto.setCompanyCode((String) result[1]);
				mobileUserDto.setUserId((long) result[2]);
				mobileUserDto.setCompanyId((int) result[3]);
				mobileUserDto.setFirstName((String) result[4]);
				mobileUserDto.setLastName((String) result[5]);
				mobileUserDto.setCompanyName((String) result[6]);
				mobileUserDto.setEnabled((boolean) result[7]);
				mobileUserDto.setPassword((String) result[8]);
			}
			return mobileUserDto;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean getValidateUserRegistraion(MobileUserDto mobileUser) throws Exception {

		boolean				sendOTP 			= true;

		try {

			Query queryt = entityManager.createQuery(
					"SELECT mobileAppUserRegistrationId FROM MobileAppUserRegistration WHERE userId = "+mobileUser.getUserId()+"",Long.class);

			Long result = null;
			try {
				queryt.setMaxResults(1);
				result = (Long) queryt.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			if (result != null) {
				sendOTP = false;
			}
			return sendOTP;
		} catch (Exception e) {
			throw e;
		}
	}

}
