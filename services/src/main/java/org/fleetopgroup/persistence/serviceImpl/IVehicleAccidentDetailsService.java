package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.document.VehicleAccidentDocument;
import org.fleetopgroup.persistence.document.VehicleExpenseDocument;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.model.AccidentQuotationDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IVehicleAccidentDetailsService {

	ValueObject	saveVehicleAccidentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	updateVehicleAccidentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	getPageWiseVehicleAccidentDetails(ValueObject	valueObject) throws Exception;
	
	public Page<VehicleAccidentDetails> getVehicleAccidentDetailsPageDetails(Integer pageNumber, Integer companyId);
	
	public List<VehicleAccidentDetailsDto> getVehicleAccidentDetailsDtoList(Integer pageNumber, Integer companyId) throws Exception;
	
	ValueObject	showVehicleAccidentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	getVehicleAccidentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveIncidentDocument(ValueObject	valueObject, MultipartFile file) throws Exception;
	
	public List<VehicleAccidentDocument> getVehicleAccidentDocumentById(long accidentId, int companyId) throws Exception ;
	
	ValueObject	getIncidentDocumentList(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveServeyorDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveServeyCompletionDetails(ValueObject	valueObject) throws Exception;
	
	VehicleAccidentDetailsDto getVehicleAccidentDetailsById(Long id, Integer companyId, Long userId) throws Exception;
	
	public void updateAccidentDetailsStatus(Long	accidentId, short status) throws Exception;
	
	public void updateAccidentDetailsServiceDetails(Long	serviceId, short type, Long accidentId) throws Exception;
	
	ValueObject	saveFinalServeyForDamage(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveQuotationApprovalDetails(ValueObject	valueObject) throws Exception;

	VehicleAccidentDocument getVehicleAccidentDocument(Integer documentId, Integer companyId) throws Exception;
	
	ValueObject	saveAdvanceDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	getAdvanceListByAccidentId(ValueObject	valueObject) throws Exception;
	
	ValueObject	saveExpenseDetails(ValueObject valueObject, MultipartFile file) throws Exception;
	
	ValueObject	getExpenseListByAccidentId(ValueObject	valueObject) throws Exception;
	
	public Map<Long, VehicleExpenseDocument> getVehicleExpenseDocumentById(long accidentId, int companyId) throws Exception ;
	
	ValueObject	saveFinalInspectionDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	savePaymentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	removeIncidentDoc(ValueObject	valueObject) throws Exception;
	
	public long getAccidentDocumentCount(Long accidentId) throws Exception;
	
	ValueObject	removeAdvanceDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	removeExpense(ValueObject	valueObject) throws Exception;
	
	public long getVehicleAccidentExpenseCount(Long accidentId) throws Exception;
	
	public long getVehicleAccidentAdvanceCount(Long accidentId) throws Exception;
	
	ValueObject	deleteAccidentDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	searchAccidentByNumber(ValueObject	valueObject) throws Exception;
	
	public VehicleAccidentDetails getVehicleAccidentDetailsById(Long accidentId) throws Exception;
	
	ValueObject	checkForOldServeyDetails(ValueObject	valueObject) throws Exception;
	
	public VehicleAccidentDetails getVehicleAccidentDetailsByTripSheetId(Long tripSheetId) throws Exception;
	
	public List<VehicleAccidentDetails> validateVehicleAccidentDetails(Integer vid) throws Exception;

	public ValueObject getAccidentTypeDetails(ValueObject object)throws Exception;

	ValueObject saveUpdateAccidentTypeDetails(ValueObject object)throws Exception;

	public void saveAccidentPersonDetails(ValueObject valueObject) throws Exception;

	public void saveAccidentTypeDetails(ValueObject valueObject) throws Exception;

	public ValueObject removeAccidentPersonDetails(ValueObject valueObject) throws Exception;

	public ValueObject saveUpdateSpotSurveyorDetails(ValueObject valueObject) throws Exception;

	public ValueObject getSpotSurveyorDetails(ValueObject object)throws Exception;

	public ValueObject saveKeepOpenDetails(ValueObject valueObject) throws Exception;

	public ValueObject saveBeforeEstimate(ValueObject valueObject) throws Exception;
	
	public ValueObject checkDocumentExist(ValueObject object);
	
	public void saveAccedentServiceDetails(Long serviceId,Long serviceNum,Long accidentId ,short type,int companyId) throws Exception;
	
	public void deleteQuotationDetailsByservice(short typeId,long serviceId,int companyId) throws Exception;
	
	public boolean checkAllServiceComplete(long accidentDetailsId,int companyId) throws Exception ;
	
	public List<AccidentQuotationDetails> getQuotationDetailsList(long accidentDetailsId,int companyId) throws Exception;
	
	public List<VehicleAccidentDetailsDto> getVehicleAccidentDetailsDtoList(String query);

}
