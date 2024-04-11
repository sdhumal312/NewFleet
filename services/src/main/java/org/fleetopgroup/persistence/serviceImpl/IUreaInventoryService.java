package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.document.UreaInvoiceDocument;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceDto;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IUreaInventoryService {

	ValueObject	saveUreaInventoryDetails(ValueObject	valueObject,MultipartFile file) throws Exception;
	
	public ValueObject getUreaInvoiceList(ValueObject valueObject) throws Exception;
	
	public List<UreaInvoiceDto> getUreaInvoiceDtoList(Integer pageNumber, Integer companyId) throws Exception ;
	
	public ValueObject getUreaInvoiceDetails(ValueObject valueObject) throws Exception;
	
	public UreaInvoiceDto getUreaInvoiceDetails(Long invoiceId, Integer companyId) throws Exception;
	
	public ValueObject deleteUreaInvoice(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteUreaInventoryDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject searchUreaInvoiceByNumber(ValueObject valueObject) throws Exception;

	public ValueObject searchUreaInvoice(ValueObject valueObject) throws Exception;
	
	public Page<UreaInvoice> getDeployment_Page_UreaInvoice(Integer pageNumber, Integer companyId);
	
	public Page<UreaInvoice> getDeployment_Page_UreaInvoice(String term ,Integer pageNumber, Integer companyId);
	
	public List<UreaInvoiceDto> getUreaInvoiceDtoList(String term ,Integer pageNumber, Integer companyId) throws Exception ;
	
	public ValueObject locationStockDetails(ValueObject valueObject) throws Exception;

	public void updateUreaInvoice(UreaInvoiceDto ureaInvoiceDto)throws Exception;

	public Map<String, Object> addMoreUreaInventoryInvoice(ValueObject valueObject)throws Exception;

	public void updateUreaInvoice(long invoiceId, long finalQuantity, double finalCost, Integer companyId) throws Exception;

	public UreaInvoice getUreaInvoiceByInvoiceId(Long invoiceId, Integer company_id)throws Exception;
	
	public List<TripSheetExpenseDto> getUreaInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	public List<TripSheetExpenseDto> getUreaInvoiceListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;

	public void saveUreaDocument(UreaInvoice urea, MultipartFile file, ValueObject valueObject) throws Exception;

	public org.fleetopgroup.persistence.document.UreaInvoiceDocument getUreaInvoiceDocumentDetails(long UreaInvoiceId, Integer company_id)throws Exception;

	public void updateUreaInvoiceDocumentId(Long get_id, boolean b, Long urea_document_id, Integer company_id)throws Exception;

	public void addUreaInvoiceDocument(UreaInvoiceDocument ureaDoc)throws Exception;

	public UreaInvoiceDocument getUreaInvoiceDocumentDetailsByInvoiceId(Long ureaInvoiceId, Integer company_id)throws Exception;

	public void updateTotalTransferQuantity(Double ureaInventoryTransferQuantity, Long ureaInvoiceId ,Integer locationId)throws Exception;
	
	public void update_Vendor_ApprovalTO_StatusUreaInvoice_ID(String approvalInvoiceID, Long approvalId,
			short approvalStatus, Integer companyId) throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception ;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;
}
