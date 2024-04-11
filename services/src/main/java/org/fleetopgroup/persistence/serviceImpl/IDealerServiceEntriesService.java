package org.fleetopgroup.persistence.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesLabourDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesPartDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.fleetopgroup.persistence.model.DealerServiceExtraIssue;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IDealerServiceEntriesService {

	public ValueObject getDealerServiceEntriesList(ValueObject valueOutObject)throws Exception;

	public ValueObject saveDealerServiceEntries(ValueObject valueObject,  MultipartFile uploadfile)throws Exception;

	public ValueObject getDealerServiceEntriesWithPartAndLabourDetails(ValueObject valueOutObject)throws Exception;

	public ValueObject searchDealerServiceEntriesByNumber(ValueObject valueOutObject)throws Exception;

	public ValueObject searchDealerServiceEntriesByFilter(ValueObject valueOutObject)throws Exception;

	public ValueObject completeDealerServiceEntries(ValueObject valueOutObject)throws Exception;

	public void deleteDealerServiceEntriesPart(ValueObject valueOutObject)throws Exception;
	
	public void deleteDealerServiceEntriesLabour(ValueObject valueOutObject)throws Exception;

	public ValueObject deleteDealerServiceEntries(ValueObject valueOutObject)throws Exception;

	public void addDealerServiceEntriesLabourDetails(ValueObject valueOutObject)throws Exception;
	
	public void addDealerServiceEntriesPartDetails(ValueObject valueOutObject)throws Exception;

	public ValueObject searchDealerServiceEntriesDateWise(ValueObject valueOutObject)throws Exception;

	public ValueObject updateDealerServiceEntries(ValueObject valueObject,MultipartFile uploadfile)throws Exception;

	public List<MonthWiseVehicleExpenseDto> getMonthWiseDealerServiceEntriesExpenseDtoByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception;

	public List<DealerServiceEntriesDto> getDealerServiceEntriesServiceListOfMonth(TripSheetIncomeDto incomeDto) throws Exception;

	List<DateWiseVehicleExpenseDto> getDateWiseDealerServiceEntriesExpenseDtoByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception;

	public void updateApprovalIdToDealerServiceEntries(String transactionIds, Long approvalId, short paymentModeCreateApproval, Integer companyId)throws Exception;

	public void updateVendorPaymentDetails(Long approvalId, short status, String paymentDate)throws Exception;

	public ValueObject uploadDealerServiceEntryDocument(ValueObject valueObjectFormSimpleJsonString,
			MultipartFile uploadfile)throws Exception;

	public List<DealerServiceEntriesDto> getDealerServiceEntriesDetailList(ValueObject valueObject) throws Exception;

	public List<DealerServiceEntriesLabourDto> getDealerServiceEntriesLabourDetailList(ValueObject valueObject)
			throws Exception;

	public List<DealerServiceEntriesPartDto> getDealerServiceEntriesPartDetailList(ValueObject valueObject) throws Exception;

	public ValueObject getLastOccurredDsePartDetails(ValueObject valueInObject)throws Exception;

	public ValueObject saveInvoiceDetails(ValueObject valueInObject)throws Exception;

	public ValueObject saveNewDealerServicePartInMasterPart(ValueObject valueInObject)throws Exception;

	public ValueObject addDealerServiceExtraIssue(ValueObject valueOutObject)throws Exception;

	public List<DealerServiceExtraIssue> getAllDealerServiceExtraIssue(Integer company_id)throws Exception;

	public ValueObject saveNewVendorFromDse(ValueObject valueOutObject)throws Exception;

	public ValueObject changeDseStatus(ValueObject valueOutObject)throws Exception;

	public ValueObject reopenDealerService(ValueObject valueOutObject)throws Exception;

	public ValueObject updateDealerServicePart(ValueObject valueOutObject)throws Exception;

	public ValueObject updateDealerServiceLabour(ValueObject valueOutObject)throws Exception;

	public ValueObject getVendorWisePreviousDsePartRate(ValueObject valueOutObject)throws Exception;

	public ValueObject getDseRemarks(ValueObject valueOutObject)throws Exception;

	public ValueObject getDseExtraIssue(ValueObject valueOutObject)throws Exception;

	public ValueObject deletePreviousPartWarrantyDetails(ValueObject valueObject, List<PartWarrantyDetails> partWarrantyDetailsList) throws Exception;

	public ValueObject updateParWarrantyWhenQtyLess(ValueObject valueObject, List<PartWarrantyDetails> partWarrantyDetailsList) throws Exception;

	public void deleteLimitedUnAssignPartWarrnty(ValueObject valueObject, int limitQty) throws Exception;

	public void addDealerServiceReminder(DealerServiceEntries dealerServiceEntries) throws Exception;

	public ArrayList<String> getDealerServiceReminder(Long DealerServiceEntriesId, Integer companyId ) throws Exception;
	
	public void serviceReminderEmailAlert(List<EmailAlertQueue>  pendingList ,ServiceReminder service) throws Exception;
	
	public void serviceReminderSmsAlert(List<SmsAlertQueue>  smsPendingList ,ServiceReminder service) throws Exception;
	
	public Object[] getUserActivityCount(ValueObject object) throws Exception ;
	
	public List<DealerServiceEntriesDto> getDSEListByUserActivity(String queryT ,String innerQuery, int companyId) throws Exception;
	

	public ValueObject backdateOdometerValidation(ValueObject object);
	
	public Integer caluculateDiffrence(VehicleDto nextIssue , VehicleDto preIssue);
	
	public VehicleDto getPreviousOdoFromHistory(String conditionQ,String orderQ) throws Exception ;
	
	public ValueObject getDSEReport(ValueObject object);
	
	public ValueObject getpartLabourList(ValueObject valueObject);
	
	public HashMap<String,Object> getVehicleWiseDealerServiceDetails(int id);
	
	public List<DealerServiceEntriesDto> getDSEReportList(ValueObject object) throws Exception;
	
	public List<TripSheetExpenseDto> getDealerEntriesListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;

	public List<TripSheetExpenseDto> getDealerEntriesListForTallyImport(String fromDate, String toDate, Integer companyId,
			Long tallyCompanyId)throws Exception ;

}
