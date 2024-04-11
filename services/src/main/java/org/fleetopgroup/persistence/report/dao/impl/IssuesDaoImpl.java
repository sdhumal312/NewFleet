package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.report.dao.IssuesDao;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.springframework.stereotype.Repository;

@Repository
public class IssuesDaoImpl implements IssuesDao {

	@PersistenceContext
	EntityManager entityManager;
	
	SimpleDateFormat 						format	 			= new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<IssuesDto> getIssuesList(IssuesDto issuesDto) throws Exception {
		List<IssuesDto>			issuesList			= null;		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_VID, I.ISSUES_REPORTED_DATE,I.ISSUES_STATUS_ID,I.LASTUPDATED_DATE"
							+ "	from Issues I "
							+ " where I.ISSUES_VID = " + issuesDto.getISSUES_VID() 
							+ " AND I.ISSUES_REPORTED_DATE BETWEEN '"+issuesDto.getFromDate()+"' AND '"+issuesDto.getToDate()+"'"
							+ " AND I.COMPANY_ID = "+issuesDto.getCOMPANY_ID()+" AND I.markForDelete  = 0 ORDER BY I.ISSUES_ID ASC",
							Object[].class);
			
			List<Object[]> results = query.getResultList();

			issuesList	= new ArrayList<IssuesDto>();
			if (results != null && !results.isEmpty()) {
				IssuesDto issueDto = null;
				for (Object[] result : results) {
					issueDto = new IssuesDto();
					issueDto.setISSUES_ID((Long) result[0]);
					issueDto.setISSUES_NUMBER((Long) result[1]);
					issueDto.setISSUES_VID((Integer) result[2]);
					issueDto.setISSUES_REPORTED_DATE_ON((java.util.Date) result[3]);
					issueDto.setISSUES_STATUS_ID((Short) result[4]);
					issueDto.setLASTUPDATED_DATE_ON((java.util.Date) result[5]);
					
					issueDto.setISSUES_REPORTED_DATE(format.format(issueDto.getISSUES_REPORTED_DATE_ON()));
					issueDto.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issueDto.getISSUES_ID()));
					issueDto.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issueDto.getISSUES_STATUS_ID()));

					issuesList.add(issueDto);
				}
			}
			
			return issuesList;
		} catch (Exception e) {
			throw e;
		} finally {
			issuesList		= null;
		}
	}
	@Override
	public List<IssuesDto> getIssuesAllList(IssuesDto issuesDto) throws Exception {
		List<IssuesDto>			issuesList			= null;		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_VID, I.ISSUES_REPORTED_DATE,I.ISSUES_STATUS_ID,I.LASTUPDATED_DATE"
							+ "	from Issues I "
							+ " where I.ISSUES_VID = " + issuesDto.getISSUES_VID() 
							//	+ " AND I.ISSUES_REPORTED_DATE BETWEEN '"+issuesDto.getFromDate()+"' AND '"+issuesDto.getToDate()+"'"
							+ " AND I.COMPANY_ID = "+issuesDto.getCOMPANY_ID()+" AND I.markForDelete  = 0 ORDER BY I.ISSUES_ID ASC",
							Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			issuesList	= new ArrayList<IssuesDto>();
			if (results != null && !results.isEmpty()) {
				IssuesDto issueDto = null;
				for (Object[] result : results) {
					issueDto = new IssuesDto();
					issueDto.setISSUES_ID((Long) result[0]);
					issueDto.setISSUES_NUMBER((Long) result[1]);
					issueDto.setISSUES_VID((Integer) result[2]);
					issueDto.setISSUES_REPORTED_DATE_ON((java.util.Date) result[3]);
					issueDto.setISSUES_STATUS_ID((Short) result[4]);
					issueDto.setLASTUPDATED_DATE_ON((java.util.Date) result[5]);
					
					issueDto.setISSUES_REPORTED_DATE(format.format(issueDto.getISSUES_REPORTED_DATE_ON()));
					issueDto.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issueDto.getISSUES_ID()));
					issueDto.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issueDto.getISSUES_STATUS_ID()));
					
					issuesList.add(issueDto);
				}
			}
			
			return issuesList;
		} catch (Exception e) {
			throw e;
		} finally {
			issuesList		= null;
		}
	}
}
