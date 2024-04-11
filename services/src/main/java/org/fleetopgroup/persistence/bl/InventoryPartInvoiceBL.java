/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.web.util.DateTimeUtility;

/**
 * @author fleetop
 *
 */
public class InventoryPartInvoiceBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat SQLdateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public InventoryPartInvoiceBL() {
		super();
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

public List<PartInvoiceDto> prepareInventoryPartInvoiceList(List<PartInvoiceDto> partInvoiceList) throws Exception {
		
		try {
			if(partInvoiceList != null ) {
				for(PartInvoiceDto partInvoice : partInvoiceList) {
					
					partInvoice.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(partInvoice.getVendorPaymentStatus()));
					partInvoice.setPaymentStatus(PaymentTypeConstant.getPaymentTypeName(partInvoice.getPaymentTypeId()));
					partInvoice.setInvoiceDateOn(DateTimeUtility.getDateFromTimeStamp(partInvoice.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					partInvoice.setVendorId(partInvoice.getVendorId());
					if(partInvoice.getVendorName() == null)
						partInvoice.setVendorName("--");
					
					/*if(partInvoice.getVendorPaymentDate() != null) 
						partInvoice.setVendorPaymentDateStr(DateTimeUtility.getDateFromTimeStamp(partInvoice.getVendorPaymentDate(), DateTimeUtility.DD_MM_YYYY));	*/				
				}
			}
			return partInvoiceList;
		} catch (Exception e) {
			throw e;
		}
	}

}
