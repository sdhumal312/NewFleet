package org.fleetopgroup.persistence.serviceImpl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.document.TripSheetDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ITripSheetMobileService {
	
	public ValueObject searchTripsheet(ValueObject object) throws Exception;
	
	public ValueObject vehicleStatusCheckWhileCreatingTripSheet(ValueObject object) throws Exception;
	
	public ValueObject createTripsheet(ValueObject object) throws Exception;
	
	public ValueObject saveOrDispatchTripsheet(ValueObject object,HttpServletRequest request,MultipartFile file) throws Exception;
	
	public ValueObject showTripSheetDetails(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetAdvanceDetails(ValueObject object) throws Exception;
	
	public ValueObject saveTripSheetAdvanceDetails(ValueObject object) throws Exception;
	
	public ValueObject removeTripSheetAdvanceDetails(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetExpenseDetails(ValueObject object) throws Exception; 
	
	public ValueObject saveTripSheetExpenseDetails(ValueObject object, MultipartFile[] file) throws Exception;
	
	public ValueObject removeTripSheetExpenseDetails(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetIncomeDetails(ValueObject object) throws Exception;
	
	public ValueObject saveTripSheetIncomeDetails(ValueObject object) throws Exception;
	
	public ValueObject removeTripSheetIncomeDetails(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetDriverHaltDetails(ValueObject object) throws Exception;
	
	public ValueObject saveTripSheetDriverHaltDetails(ValueObject object) throws Exception;
	
	public ValueObject removeTripSheetDriverHaltDetails(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetFuelDetails(ValueObject object) throws Exception;
	
	public ValueObject removeTripSheetFuelDetails(ValueObject object) throws Exception;
	
	public ValueObject editTripSheetDetails(ValueObject object) throws Exception;
	
	public ValueObject updateTripSheetDetails(ValueObject object) throws Exception;
	
	public ValueObject updateTripSheetEditData(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetCloseDetails(ValueObject object) throws Exception;
	
	public ValueObject saveTripSheetCloseDetails(ValueObject object) throws Exception;
	
	public ValueObject saveTripComment(ValueObject object) throws Exception;
	
	public ValueObject removeTripComment(ValueObject object) throws Exception;
	
	public ValueObject addTripSheetPODDetails(ValueObject object) throws Exception;
	
	public ValueObject saveTripSheetPODDetails(ValueObject object) throws Exception;
	
	public ValueObject deleteTripSheetDetails(ValueObject object) throws Exception;
	
	public ValueObject addcloseAccountTripSheet(ValueObject object) throws Exception;
	
	public ValueObject savecloseAccountTripSheet(ValueObject object) throws Exception;
	
	public ValueObject showCloseAccountTripSheet(ValueObject object) throws Exception;
	
	public ValueObject getTripSheetAdvanceList(ValueObject object) throws Exception;
	
	public ValueObject getTripSheetExpenseList(ValueObject object) throws Exception;
	
	public ValueObject searchTripSheetByNumber(ValueObject object) throws Exception;
	
	public ValueObject searchTripSheetDiffStatus(ValueObject object) throws Exception;
	
	public ValueObject searchVehCurTSShow(ValueObject object) throws Exception;
	
	public ValueObject superUserTripSheetDelete(ValueObject object) throws Exception;
	
	public void saveTripExpenseDocument(TripSheetExpense tripSheetExpense, MultipartFile file, ValueObject valueObject) throws Exception;

	public org.fleetopgroup.persistence.document.TripSheetExpenseDocument getTripSheetExpenseDocumentDetails(long batteryInvoiceId, Integer company_id)throws Exception;

	public ValueObject getAccessToken(HttpServletRequest request) throws Exception;
	
	public ValueObject getManualVsGpsKMComparison(ValueObject valueObject) throws Exception;
	
	public void addTripSheetDocument(org.fleetopgroup.persistence.document.TripSheetDocument tripSheetDoc) throws Exception;

	public void saveTripDocument(TripSheet tripSheet, ValueObject valueObject, MultipartFile file) throws Exception;

	public org.fleetopgroup.persistence.document.TripSheetDocument getTripSheetDocumentDetails(Long tripSheetDocumentId, Integer company_id) throws Exception;
	
	public ValueObject updateTripSheetDocument(ValueObject object, MultipartFile uploadfile)throws Exception;
	
	public ValueObject saveChangeStatusTripSheet(ValueObject object) throws Exception;

	public ValueObject saveTripSheetRouteWiseWeightDetails(ValueObject object) throws Exception;

	public ValueObject getTripSheetRouteWiseWeightList(ValueObject valueObject) throws Exception;

	public ValueObject removeTripSheetRouteWiseWightDetails(ValueObject object) throws Exception;

	public ValueObject saveTripsheetIncomeTocashBook(Long tripsheetId, Integer companyId, CustomUserDetails	userDetails	) throws Exception;

	public ValueObject saveTripsheetExpensesFromClosTripApi(ArrayList<ValueObject> expensearray, ValueObject object) throws Exception;
}