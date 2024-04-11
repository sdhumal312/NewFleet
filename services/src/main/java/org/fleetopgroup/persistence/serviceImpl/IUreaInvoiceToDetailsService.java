package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.web.util.ValueObject;

public interface IUreaInvoiceToDetailsService {

	List<UreaInvoiceToDetailsDto> getUreaInvoiceToDetailsDtoList(Long invoiceId, Integer companyId) throws Exception;
	
	public void removeUreaInventoryDetailsFromInvoice(UreaInvoiceToDetails ureaInvoiceToDetails) throws Exception;
	
	List<UreaInvoiceToDetailsDto>  getAllLocationUreaStockDetails(Integer companyId , String query) throws Exception;
	
	List<UreaInvoiceToDetailsDto>  getLocationUreaStockDetails(Integer locationId , Integer companyId) throws Exception;
	
	List<UreaInvoiceToDetailsDto>  getLocationUreaStockDetailsForEntry(String term , Integer companyId) throws Exception;
	
	public void updateStockQuantityOnUreaEntry(UreaEntries	ureaEntries) throws Exception;
	
	public void updateStockQuantityOnEditUreaEntry(UreaEntries ureaEntries, double stckQty) throws Exception;
	
	public void updateStckQtyOnEditDiffrentUreaEntry(long oldUreaInvoiceToDetailsId, double oldUreaLiters,
		long newUreaInvoiceToDetailsId, double newUreaLiters) throws Exception;
	
	public void updateStockQuantityOnDeleteUreaEntry(long ureaEntriesId) throws Exception;

	public ValueObject getUreastockDetailsByManufactureIdAndLocation(ValueObject object)throws Exception;

	public List<UreaInvoiceToDetailsDto> getLocationWiseUreaInvoiceDetails(String term, Integer locationId, Integer company_id)throws Exception;

	public void updateUreaInvoiceToDetailsQuantity(ValueObject valueInObject)throws Exception;

	public ValueObject getUreastockDetailsOfOtherLocationByManufactureId(ValueObject object)throws Exception;

	public List<UreaInvoiceToDetailsDto> getsubLocationUreaDetails(Integer mainLocationId)throws Exception;

	public List<UreaInvoiceToDetails> getUreaInvoiceToDetailsByUreaInvoiceId(Long ureaInvoiceId)throws Exception;

	public void updateSubLocationInUreaInvoiceToDetails(Integer subLocationId, Long ureaInvoiceId, Integer company_id)throws Exception;
	
	public UreaInvoiceToDetails getUreaInvoiceToDetailsById(Long ureaInvoiceToDetailsId)throws Exception;

	public void updateUreaTransferQuantityAndUpdateStockQuantityInUreaInvoiceToDetails(ValueObject valueInObject)throws Exception;

	public List<UreaInvoiceToDetailsDto> getLocationUreaStockDetailsByLocationId(String term, Integer companyId)throws Exception;
	
	public void updateUreaTransferQuantity(Double quantity,long InvoiceToDetailsId,long userId,int companyId) throws Exception ;
	
}
