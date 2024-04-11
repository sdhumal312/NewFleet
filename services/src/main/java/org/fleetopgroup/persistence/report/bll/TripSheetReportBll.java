package org.fleetopgroup.persistence.report.bll;

import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface TripSheetReportBll {

	ValueObject	getTripSheetStatusCollectionDetails(ValueObject	valueObject)throws Exception;

	ValueObject getTripIncomeReportByIncomeName(ValueObject object)throws Exception;
}
