package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IPartReportService {

	ValueObject	getPartWiseConsumtionReport(ValueObject		valueObject) throws Exception;

	ValueObject getPartPurchaseInvoiceReport(ValueObject valueObject) throws Exception;

	ValueObject getTechnicianWisePartReport(ValueObject object) throws Exception;

	ValueObject getPartStockReport(ValueObject object)throws Exception;

	ValueObject getPartRequisitionStatusWiseReport(ValueObject object) throws Exception;


	
}
