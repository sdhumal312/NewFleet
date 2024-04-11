package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dao.DriverPaidAdvanceRepository;
import org.fleetopgroup.persistence.dto.DriverPaidAdvanceDto;
import org.fleetopgroup.persistence.model.DriverPaidAdvance;
import org.fleetopgroup.persistence.serviceImpl.IDriverPaidAdvanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DriverPaidAdvanceService implements IDriverPaidAdvanceService {

	@Autowired
	private DriverPaidAdvanceRepository DriverPaidAdvanceRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public DriverPaidAdvance register_New_DriverPaidAdvance(DriverPaidAdvance accountDto) throws Exception {

		return DriverPaidAdvanceRepository.save(accountDto);
	}

	@Transactional
	public Double Get_TotalSum_OF_Paid_Advacne_Amount_Total(Long dsaid, Integer companyId) {

		Query query = entityManager.createQuery("SELECT SUM(PAID_AMOUNT) FROM DriverPaidAdvance WHERE DSAID = :id AND COMPANY_ID = :companyId AND markForDelete = 0");

		query.setParameter("id", dsaid);
		query.setParameter("companyId", companyId);
		Double amount = 0.0;
		try {
			amount = (Double) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		return amount;

	}

	@Transactional
	public List<DriverPaidAdvanceDto> GET_DRIVER_PAY_SALARY_ADVACNE_ID(Long dRIVER_SALARYID, Integer companyId) {
		//return DriverPaidAdvanceRepository.GET_DRIVER_PAY_SALARY_ADVACNE_ID(dRIVER_SALARYID, companyId);
		TypedQuery<Object[]>  queryt = entityManager.createQuery(
				"SELECT R.DPAID, R.PAID_DATE, R.ADVANCE_PAID_TYPE_ID, R.ADVANCE_PAID_NUMBER, R.PAID_AMOUNT, U.firstName,"
				+ " R.PAID_REMARK "
				+ " FROM DriverPaidAdvance R "
				+ " LEFT JOIN User U ON U.id = R.ADVANCE_RECEIVED_BY_ID"
				+ " where R.DSAID= "+dRIVER_SALARYID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
				Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<DriverPaidAdvanceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<DriverPaidAdvanceDto>();
			DriverPaidAdvanceDto list = null;
			for (Object[] result : results) {
				list = new DriverPaidAdvanceDto();

				list.setDPAID((Long) result[0]);
				list.setPAID_DATE_ON((Date) result[1]);
				if(result[2] != null)
				 list.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName((short) result[2]));
				list.setADVANCE_PAID_NUMBER((String) result[3]);
				list.setADVANCE_PAID_AMOUNT((Double) result[4]);
				list.setADVANCE_RECEIVED_BY((String) result[5]);
				list.setPAID_REMARK((String) result[6]);
				

				Dtos.add(list);
			}
		}
		return Dtos;
	}

}
