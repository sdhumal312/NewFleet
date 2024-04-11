package org.fleetopgroup.service;

import java.util.Date;
import java.util.HashMap;

public interface ITallyIntegrationService {

	public HashMap<String, Object>  getExpenseVoucherDataWthinRange(Date fromDate, Date toDate, String selectedCompany) throws Exception;
}
