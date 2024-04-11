package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.TripsheetPickAndDropDto;
import org.fleetopgroup.persistence.model.PickAndDropLocation;
import org.fleetopgroup.web.util.ValueObject;

public interface IPickAndDropLocationService {

	public ValueObject getPickAndDropLocationAutoComplete(String term)throws Exception;
	
	public ValueObject getAllPickAndDropLocation()throws Exception;
	
	public PickAndDropLocation validatePickAndDropLocation(String tyreExpenseName, Integer companyId) throws Exception;
	
	public ValueObject savePickAndDropLocation(ValueObject valueObject) throws Exception;

	public ValueObject getPickAndDropLocationById(ValueObject valueInObject)throws Exception;

	public ValueObject updatePickAndDropLocation(ValueObject valueInObject)throws Exception;

	public ValueObject deletePickAndDropLocation(ValueObject valueInObject)throws Exception;
	
	public ValueObject searchTripsheetNumber(ValueObject valueObject) throws Exception;
	
	public ValueObject getTripSheetPickAndDropDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleWiseTripSheetPickAndDropDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject dispatchPickAndDropTrip(ValueObject valueObject) throws Exception;
	
	public ValueObject getTripsheetPickDropDispatchDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject updatePickAndDropTrip(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteTripsheetPickAndDrop(ValueObject valueObject) throws Exception;
	
	public ValueObject getPartyWiseTripReport(ValueObject valueObject) throws Exception; 
	
	public ValueObject getInvoiceList(ValueObject valueObject) throws Exception;
	
	public ValueObject savePickOrDropInvoice(ValueObject valueObject) throws Exception;
	
	public ValueObject getInvoiceDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getInvoicePaymentList(ValueObject valueObject) throws Exception;
	
	public ValueObject savePickOrDropInvoicePayment(ValueObject valueObject) throws Exception;
	
	public ValueObject getPartyWiseInvoicePaymentReport(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteInvoice(ValueObject valueObject) throws Exception;

	public ValueObject sendTripDetailsBySMS(String mobileNo, String content) throws Exception;
	
	public ValueObject locationNameList(ValueObject object) throws Exception;
	
	public ValueObject savePickOrDropDispatchMobileData(ValueObject valueObject) throws Exception;
	
	public ValueObject editTripsheetPickDropDispatchDetailsFromMobile(ValueObject valueObject) throws Exception;
	
	public ValueObject updateTripsheetPickDropDispatchDetailsFromMobile(ValueObject valueObject) throws Exception;
	
	public ValueObject searchPickAndDropTripByVehicle(ValueObject object) throws Exception;
	
	public HashMap<Integer, Long> pickAndDropCreatedBetweenDates(String startDate, String endDate) throws Exception;
	
	public long pickAndDropCreatedBetweenDatesByCompanyId(String startDate, String endDate, int companyId) throws Exception;
	
	public long vehiclesWithPickAndDropBetweenDates(String startDate, String endDate, int companyId) throws Exception;
	
	public double pickAndDropTotalAmountBetweenDates(String startDate, String endDate, int companyId) throws Exception;
	
	public long totalPickUpsBetweenDates(String startDate, String endDate, int companyId) throws Exception;
	
	public long totalDropsBetweenDates(String startDate, String endDate, int companyId) throws Exception;
	
	public List<TripsheetPickAndDropDto> pickAndDropCreatedBetweenDatesList(Integer companyID, String issueStatusQuery) throws Exception;
	
	public List<TripsheetPickAndDropDto> vehiclesWithPickAndDropBetweenDatesList(Integer companyID, String issueStatusQuery) throws Exception;
	
	public List<TripsheetPickAndDropDto> vehiclesWithPickAndDropBetweenDatesWithUniqueVehicleId(String startDate, String endDate, int companyId) throws Exception;
}
