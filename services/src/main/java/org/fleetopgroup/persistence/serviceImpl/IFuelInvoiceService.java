package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.document.FuelInvoiceDocument;
import org.fleetopgroup.persistence.dto.FuelInvoiceDto;
import org.fleetopgroup.persistence.dto.FuelStockDetailsDto;
import org.fleetopgroup.persistence.model.FuelInvoice;
import org.fleetopgroup.persistence.model.FuelStockDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IFuelInvoiceService {

	ValueObject	saveFuelInventoryDetails(ValueObject	valueInObject) throws Exception;
	
	ValueObject	getPageWiseFuelInvoiceDetails(ValueObject	valueObject) throws Exception;
	
	public Page<FuelInvoice> getDeploymentFuelInvoice(Integer pageNumber, Integer companyId) throws Exception;
	
	public List<FuelInvoiceDto> getFuelInvoiceDtoList(Integer pageNumber, Integer companyId) throws Exception;
	
	public FuelInvoiceDocument getFuelInvoiceDocumentDetails(long documentId, Integer companyId)throws Exception;
	
	ValueObject	getFuelInvoiceDetails(ValueObject	valueObject) throws Exception;
	
	FuelInvoiceDto	getFuelInvoiceDetailsById(Long fuelInvoiceId, Integer companyId) throws Exception;
	
	ValueObject	deleteFuelInvoice(ValueObject	valueObject) throws Exception;
	
	public void saveFuelStockDetails(FuelStockDetails	fuelStockDetails) throws Exception;
	
	ValueObject	getFuelStockDetails(ValueObject	valueObject) throws Exception;
	
	public FuelStockDetails getFuelStockDetailsByPetrolPumpId(Integer petrolPumpId, Integer companyId) throws Exception;
	
	public void updateFuelStockDetails(Integer petrolPumpId, Double rate, Double quantity, Double price) throws Exception;
	
	public List<FuelStockDetailsDto> getMinimumfuelStock(Integer companyId ,Integer threashold) throws Exception;
	
	public FuelInvoice getFuelInvoiceBalanceStockDetails(Integer vendorId, Integer companyId) throws Exception;

	void updateFuelStockDetailsInFuelInvoice(ValueObject valueInObject) throws Exception;

	public FuelInvoice getFuelInvoiceDetailsByFuelInvoiceId(Long fuelInvoiceId, Integer companyId) throws Exception;

	public ValueObject updateShortExcessQuantity(ValueObject object)throws Exception;

	public ValueObject getFuelInvoiceHistoryByInvoiceId(ValueObject valueObject) throws Exception;

	public ValueObject updateFuelInventoryDetails(ValueObject valueInObject) throws Exception;

	public void updateApprovalIdToInvoice(String fuelInvoiceIds, Long approvalId, short status, Integer companyId) throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, short paymentStatus) throws Exception;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;
	
	public List<FuelInvoiceDto> getFuelInvoiceListByPetrolPump(Integer companyId ,long petrolPumpId) throws Exception ;
	
	public ValueObject saveTransferFuelDetails( ValueObject valueObject) throws Exception;
	
	public ValueObject getFuelStockDetail(ValueObject valueObject) throws Exception;
	
	public List<FuelInvoiceDto> getFuelInvoiceDtoList(ValueObject valueObject) throws Exception;
	
	public Double getBalanceStockFromInvoice(Integer vendorId , Integer companyId) throws Exception;
	
}

