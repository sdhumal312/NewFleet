package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;

public interface IUpholsterySendLaundryInvoiceService {

	public UpholsterySendLaundryInvoiceDto getLaundryInvoiceDetails(Long invoiceId, Integer companyId) throws Exception;
	
	public void updateInvoiceDetailsToReceive(Long invoiceId, Double receiveQuantity) throws Exception;
	
	public void updateInvoiceDetailsToDamage(Long invoiceId, Double damageQuantity) throws Exception;
	
	public void updateInvoiceDetailsToLost(Long invoiceId, Double lostQuantity) throws Exception;
	
	public UpholsterySendLaundryInvoiceDto getLaundryInvoiceStatus(Long invoiceId, Integer companyId) throws Exception;
	
	public void updateLaundryInvoiceSatus(Long invoiceId, Integer companyId) throws Exception;
	
	public List<UpholsterySendLaundryInvoiceDto> getInWashingDetailsList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception;
}
