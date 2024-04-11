package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.DealerServiceEntriesBL;
import org.fleetopgroup.persistence.bl.IssueAnalysisBL;
import org.fleetopgroup.persistence.bl.VehicleModelBL;
import org.fleetopgroup.persistence.dao.IssueAnalysisRepository;
import org.fleetopgroup.persistence.dao.IssuesRepository;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssueAnalysisDto;
import org.fleetopgroup.persistence.model.IssueAnalysis;
import org.fleetopgroup.persistence.model.IssueBreakDownDetails;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.VehicleModel;
import org.fleetopgroup.persistence.report.dao.IIssueBreakDownService;
import org.fleetopgroup.persistence.serviceImpl.IIssueAnalysisService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("IssueAnalysisService")
@Transactional
public class IssueAnalysisService implements IIssueAnalysisService {
	SimpleDateFormat dateFormatSQL 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired private IIssuesService 				issuesService;
	@Autowired private IssueAnalysisRepository		issueAnalysisRepository;
	@Autowired IssuesRepository   					issuesrepository;
	@Autowired IIssueBreakDownService               issueBreakDownService;
	@Autowired CompanyConfigurationService          companyConfigurationService;
	
	IssueAnalysisBL		issueAnalysisBL 		= new IssueAnalysisBL();
	
	

	@Override
	public ValueObject getIssueAnalysisDetails(ValueObject valueObject) throws Exception {
		IssueAnalysisDto			issueAnalysisDto				= null;
		try {
			
			issueAnalysisDto = getIssueAnalysisDetailsByIssueId(valueObject.getLong("issueId"));
			valueObject.put("issueAnalysisDetails", issueAnalysisDto);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public IssueAnalysisDto getIssueAnalysisDetailsByIssueId(Long issueId) throws Exception {
		try {
			Query query  = entityManager
					.createQuery("SELECT IA.issueAnalysisId, IA.issueId, IA.reason, IA.isAvoidable, IA.tempSolution, IA.fixSolution, IA.futurePlan"
							+ ",IA.isTemporary,IA.permanentIssueId "
							+ " FROM IssueAnalysis AS IA where IA.issueId = "+issueId+" AND IA.markForDelete = 0 ");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			IssueAnalysisDto	issueAnalysisDto = null;
			
				if (result != null) {
					issueAnalysisDto = new IssueAnalysisDto();
					
					issueAnalysisDto.setIssueAnalysisId((Long) result[0]);
					issueAnalysisDto.setIssueId((Long) result[1]);
					issueAnalysisDto.setReason((String) result[2]);
					issueAnalysisDto.setAvoidable((boolean) result[3]);
					issueAnalysisDto.setTempSolution((String) result[4]);
					issueAnalysisDto.setFixSolution((String) result[5]);
					issueAnalysisDto.setFuturePlan((String) result[6]);
					issueAnalysisDto.setTemporary((boolean) result[7]);
					issueAnalysisDto.setPermanentIssueId((Long) result[8]);
				}
				return issueAnalysisDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveIssueAnalysis(ValueObject valueObject) throws Exception {
		IssueAnalysis	issueAnalysis = null;
		IssueAnalysis	validate = null;
		CustomUserDetails userDetails = null;
		Issues savedIssue = null;
		boolean permanantIssue = false;
		boolean isFromMob      = false;
		HashMap<String, Object>         configuration			= null;
		try {
			isFromMob       = valueObject.getBoolean("isFromMob",false);
			if(isFromMob){
				userDetails = Utility.getObject(valueObject);
			}else{
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
			if(valueObject.getLong("issueAnalysisId",0) >0) {
				validate=getAnalysisById(valueObject.getLong("issueAnalysisId",0), userDetails.getCompany_id());
				if(validate != null && validate.getPermanentIssueId() != null && validate.getPermanentIssueId() >0 ) {
					permanantIssue = true;
					valueObject.put("permanantIssueId", validate.getPermanentIssueId());
				}
			}
			if(valueObject.getBoolean("isTemporary",false) && !permanantIssue) {
				ValueObject object = null;
				Issues issue = issuesService.getIssueDetailsByIssueId(valueObject.getLong("issueId", 0), userDetails.getCompany_id());
				IssueBreakDownDetails breakDownDetails= issueBreakDownService.getBreakDownDetailsByIssueId(valueObject.getLong("issueId", 0), userDetails.getCompany_id());
				if(issue != null) {
					valueObject.put("issueDetails", issue);
					valueObject.put("breakDownDetails", breakDownDetails);
					object=prepareIssueValueObject(valueObject);
					object.put("userDetails", userDetails);
					object.put("companyId", userDetails.getCompany_id());
					object=issuesService.saveIssuesDetails(object);
					savedIssue=(Issues) object.get("savedIssues");
					if(savedIssue != null)
					valueObject.put("permanantIssueId",savedIssue.getISSUES_ID());
					valueObject.put("issueDetails","");
					valueObject.put("breakDownDetails","");
				}
			}
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			issueAnalysis = issueAnalysisBL.prepareIssueAnalysis(valueObject);
			issueAnalysis.setPermanentIssueId(valueObject.getLong("permanantIssueId",0));
			issueAnalysisRepository.save(issueAnalysis);
			valueObject.put("configuration", configuration);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject prepareIssueValueObject(ValueObject object) {
		
		ValueObject valueObject = new ValueObject();
		try {
			Issues issues = (Issues) object.get("issueDetails");
			IssueBreakDownDetails breakDownDetails = (IssueBreakDownDetails) object.get("breakDownIssue");
			valueObject.put("issueType", issues.getISSUE_TYPE_ID());
			valueObject.put("vid", issues.getISSUES_VID());
			valueObject.put("vGroup", issues.getVEHICLE_GROUP_ID());
			valueObject.put("driverId", issues.getISSUES_DRIVER_ID() );
			valueObject.put("issuesBranch", issues.getISSUES_BRANCH_ID());
			valueObject.put("issuesDepartnment", issues.getISSUES_DEPARTNMENT_ID() );
			valueObject.put("odometer", issues.getISSUES_ODOMETER());
			valueObject.put("gpsOdometer", issues.getGPS_ODOMETER());
			valueObject.put("timeStampDate", true);
			valueObject.put("reportdDate", issues.getISSUES_REPORTED_DATE());
			valueObject.put("issuesSummary", issues.getISSUES_SUMMARY());
			valueObject.put("issueLabel", issues.getISSUES_LABELS_ID());
			valueObject.put("reportedById", issues.getISSUES_REPORTED_BY_ID());
			valueObject.put("description", issues.getISSUES_DESCRIPTION() +" "+" , Created from issue number I-"+issues.getISSUES_NUMBER());
			valueObject.put("partCategory", issues.getCategoryId());
			valueObject.put("FuelRouteList", issues.getRouteID());
			valueObject.put("subscribe", issues.getISSUES_ASSIGN_TO());
			if(breakDownDetails != null) {
				valueObject.put("breakDownLocation", breakDownDetails.getBreakDownLocation());
				valueObject.put("tripSheetNumber", breakDownDetails.getTripNumber());
				valueObject.put("replacedVehicle", breakDownDetails.getReplacedWithVid());
				valueObject.put("replaceLocation", breakDownDetails.getVehicleReplacedLocation());
				valueObject.put("vehicleReplaced", breakDownDetails.getIsVehicleReplaced());
				valueObject.put("tripCancelled", breakDownDetails.getIsTripCancelled());
				valueObject.put("cancelledKM", breakDownDetails.getCancelledKM());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}
	
	@Transactional(readOnly=true)
	public IssueAnalysis getAnalysisById(Long issueAnalysisId ,Integer companyId) {
		return issueAnalysisRepository.getIssueAnalysisById(issueAnalysisId, companyId);
	}
}