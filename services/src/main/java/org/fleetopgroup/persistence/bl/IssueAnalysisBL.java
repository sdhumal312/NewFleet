package org.fleetopgroup.persistence.bl;

import org.fleetopgroup.persistence.model.IssueAnalysis;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class IssueAnalysisBL {

	public IssueAnalysis prepareIssueAnalysis(ValueObject valueObject) throws Exception {
		IssueAnalysis issueAnalysis = null;
		try {
			issueAnalysis = new IssueAnalysis();
			issueAnalysis.setIssueAnalysisId(valueObject.getLong("issueAnalysisId",0));
			issueAnalysis.setIssueId(valueObject.getLong("issueId"));
			issueAnalysis.setAvoidable(valueObject.getBoolean("isAvoidable"));
			issueAnalysis.setTemporary(valueObject.getBoolean("isTemporary"));
			issueAnalysis.setReason(valueObject.getString("reason"));
			issueAnalysis.setTempSolution(valueObject.getString("tempSolution",""));
			issueAnalysis.setFixSolution(valueObject.getString("fixSolution",""));
			issueAnalysis.setFuturePlan(valueObject.getString("futurePlan",""));
			issueAnalysis.setCreatedById(valueObject.getLong("userId"));
			issueAnalysis.setLastModifiedById(valueObject.getLong("userId"));
			issueAnalysis.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			issueAnalysis.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			issueAnalysis.setCompanyId(valueObject.getInt("companyId"));
			issueAnalysis.setMarkForDelete(false);
			return issueAnalysis;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
