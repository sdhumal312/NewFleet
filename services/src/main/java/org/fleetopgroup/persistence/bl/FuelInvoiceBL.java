package org.fleetopgroup.persistence.bl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.constant.FuelInvoiceConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelInvoiceDto;
import org.fleetopgroup.persistence.model.FuelInvoice;
import org.fleetopgroup.persistence.model.FuelInvoiceHistory;
import org.fleetopgroup.persistence.model.FuelInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.FuelStockDetails;
import org.fleetopgroup.persistence.model.FuelStockHistory;
import org.fleetopgroup.persistence.model.FuelTransferDetails;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

public class FuelInvoiceBL {
	
	public static final short	DISCOUNT_TYPE_PERCENTAGE	= 1;
	public static final short	DISCOUNT_TYPE_AMOUNT		= 2;
	
	public static final String	DISCOUNT_TYPE_PERCENTAGE_NAME	= "Percentage";
	public static final String	DISCOUNT_TYPE_AMOUNT_NAME		= "Amount";

	public static FuelInvoice	getFuelInvoice(ValueObject	valueObject) throws Exception{
		FuelInvoice		fuelInvoice		= null;
		try {
			
			SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");
			
			fuelInvoice		= new FuelInvoice();
			fuelInvoice.setFuelInvoiceId(valueObject.getLong("fuelInvoiceId"));
			fuelInvoice.setQuantity(valueObject.getDouble("quantity",0));
			fuelInvoice.setRate(valueObject.getDouble("unitprice",0));
			fuelInvoice.setDiscount(valueObject.getDouble("discount",0));
			fuelInvoice.setGst(valueObject.getDouble("tax",0));
			fuelInvoice.setDiscountGstTypeId(valueObject.getShort("discountTaxTypId"));
			fuelInvoice.setTotalAmount(valueObject.getDouble("tatalcost",0));
			fuelInvoice.setInvoiceDate(dateFormat.parse(valueObject.getString("invoiceDate")));
			fuelInvoice.setInvoiceNumber(valueObject.getString("invoiceNumber"));
			fuelInvoice.setInvoiceAmount(valueObject.getDouble("invoiceAmount",0));
			fuelInvoice.setDescription(valueObject.getString("description"));
			fuelInvoice.setCompanyId(valueObject.getInt("companyId",0));
			fuelInvoice.setCreatedById(valueObject.getLong("userId",0));
			fuelInvoice.setLastUpdatedById(valueObject.getLong("userId",0));
			fuelInvoice.setCreatedOn(new Date());
			fuelInvoice.setLastUpdatedOn(new Date());
			fuelInvoice.setPetrolPumpId(valueObject.getInt("petrolPumpId",0));
			fuelInvoice.setVendorId(valueObject.getInt("vendorId",0));
			fuelInvoice.setPaymentTypeId(valueObject.getShort("paymentTypeId"));
			fuelInvoice.setPaymentNumber(valueObject.getString("paymentNumber"));
			fuelInvoice.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			fuelInvoice.setPoNumber(valueObject.getString("poNumber"));
			fuelInvoice.setBalanceStock(valueObject.getDouble("balanceStock",0));
			if(valueObject.getBoolean("fuelInvoiceEdit",false)) {
				fuelInvoice.setStockTypeId(FuelInvoiceConstant.STOCK_TYPE_FI_EDIT);
				fuelInvoice.setRemark(FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_FI_EDIT));
				fuelInvoice.setUpdatedStock(valueObject.getDouble("quantity"));
				fuelInvoice.setTransferedFrom(valueObject.getLong("transferedFromInvoiceId",0));
			}else {
				fuelInvoice.setStockTypeId(FuelInvoiceConstant.STOCK_TYPE_DEFAULT);
				fuelInvoice.setRemark(FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_DEFAULT));
				fuelInvoice.setUpdatedStock(0.0);
			}
			return fuelInvoice;
		} catch (Exception e) {
			 throw e;
		}
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	
	public  static FuelStockDetails  getFuelStockDetailsDTO(FuelStockDetails	preStockDetails, ValueObject	valueObject) throws Exception{
		try {
			DecimalFormat	decimalFormat	= new DecimalFormat("#.00");
			if(preStockDetails != null) {
				preStockDetails.setStockQuantity(valueObject.getDouble("quantity",0) + preStockDetails.getStockQuantity());
				preStockDetails.setTotalFuelCost(valueObject.getDouble("tatalcost",0) + preStockDetails.getTotalFuelCost());
			}else {
				preStockDetails	= new FuelStockDetails();
				
				preStockDetails.setPetrolPumpId(valueObject.getInt("petrolPumpId",0));
				preStockDetails.setStockQuantity(valueObject.getDouble("quantity",0));
				preStockDetails.setTotalFuelCost(valueObject.getDouble("tatalcost",0));
				preStockDetails.setCompanyId(valueObject.getInt("companyId",0));
			}
			preStockDetails.setAvgFuelRate(Double.parseDouble(decimalFormat.format(preStockDetails.getTotalFuelCost()/preStockDetails.getStockQuantity())));
			
			return preStockDetails;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static FuelStockHistory getFuelStockHistoryDTO(FuelStockDetails	fuelStockDetails, Long fuelInvoiceId) throws Exception{
		
		try {
			FuelStockHistory	fuelStockHistory	= new FuelStockHistory();
			
			fuelStockHistory.setFuelStockDetailsId(fuelStockDetails.getFuelStockDetailsId());
			fuelStockHistory.setPetrolPumpId(fuelStockDetails.getPetrolPumpId());
			fuelStockHistory.setStockQuantity(fuelStockDetails.getStockQuantity());
			fuelStockHistory.setAvgFuelRate(fuelStockDetails.getAvgFuelRate());
			fuelStockHistory.setCompanyId(fuelStockDetails.getCompanyId());
			fuelStockHistory.setUpdatedByInvoiceId(fuelInvoiceId);
			fuelStockHistory.setTotalFuelCost(fuelStockDetails.getTotalFuelCost());
			
			return fuelStockHistory;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public static String getDiscountTypeName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DISCOUNT_TYPE_PERCENTAGE:
			  statusName	= DISCOUNT_TYPE_PERCENTAGE_NAME;
		        break;
		  case DISCOUNT_TYPE_AMOUNT: 
			  statusName	= DISCOUNT_TYPE_AMOUNT_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}

	
	public static FuelInvoiceHistory	prepareFuelInvoiceHistory(FuelInvoice	fuelInvoice) throws Exception{
		FuelInvoiceHistory		fuelInvoiceHistory		= null;
		try {
			
			fuelInvoiceHistory		= new FuelInvoiceHistory();
			fuelInvoiceHistory.setFuelInvoiceId(fuelInvoice.getFuelInvoiceId());
			fuelInvoiceHistory.setFuelInvoiceNumber(fuelInvoice.getFuelInvoiceNumber());
			fuelInvoiceHistory.setQuantity(fuelInvoice.getQuantity());
			fuelInvoiceHistory.setRate(fuelInvoice.getRate());
			fuelInvoiceHistory.setDiscount(fuelInvoice.getDiscount());
			fuelInvoiceHistory.setGst(fuelInvoice.getGst());
			fuelInvoiceHistory.setDiscountGstTypeId(fuelInvoice.getDiscountGstTypeId());
			fuelInvoiceHistory.setTotalAmount(fuelInvoice.getTotalAmount());
			fuelInvoiceHistory.setInvoiceDate(fuelInvoice.getInvoiceDate());
			fuelInvoiceHistory.setInvoiceNumber(fuelInvoice.getInvoiceNumber());
			fuelInvoiceHistory.setInvoiceAmount(fuelInvoice.getInvoiceAmount());
			fuelInvoiceHistory.setVendorId(fuelInvoice.getVendorId());
			fuelInvoiceHistory.setPaymentTypeId(fuelInvoice.getPaymentTypeId());
			fuelInvoiceHistory.setTallyCompanyId(fuelInvoice.getTallyCompanyId());
			fuelInvoiceHistory.setPaymentNumber(fuelInvoice.getPaymentNumber());
			fuelInvoiceHistory.setPoNumber(fuelInvoice.getPoNumber());
			fuelInvoiceHistory.setDescription(fuelInvoice.getDescription());
			fuelInvoiceHistory.setCompanyId(fuelInvoice.getCompanyId());
			fuelInvoiceHistory.setDocumentId(fuelInvoice.getDocumentId());
			fuelInvoiceHistory.setCreatedById(fuelInvoice.getCreatedById());
			fuelInvoiceHistory.setLastUpdatedById(fuelInvoice.getLastUpdatedById());
			fuelInvoiceHistory.setCreatedOn(fuelInvoice.getCreatedOn());
			fuelInvoiceHistory.setLastUpdatedOn(fuelInvoice.getLastUpdatedOn());
			fuelInvoiceHistory.setMarkForDelete(fuelInvoice.isMarkForDelete());
			fuelInvoiceHistory.setPetrolPumpId(fuelInvoice.getPetrolPumpId());
			fuelInvoiceHistory.setBalanceStock(fuelInvoice.getBalanceStock());
			fuelInvoiceHistory.setUpdatedStock(fuelInvoice.getUpdatedStock());
			fuelInvoiceHistory.setStockTypeId(fuelInvoice.getStockTypeId());
			fuelInvoiceHistory.setRemark(fuelInvoice.getRemark());
			return fuelInvoiceHistory;
		} catch (Exception e) {
			 throw e;
		}
	}
	
	public static FuelInvoice prepareFuelinvoice(ValueObject valueObject,FuelInvoiceDto  fuelInvoice,CustomUserDetails userDetails) throws Exception {
		FuelInvoice newFuelInvoice		= new FuelInvoice();
		newFuelInvoice.setQuantity(valueObject.getDouble("quantity",0));
		newFuelInvoice.setRate(fuelInvoice.getRate());
		newFuelInvoice.setDiscount(fuelInvoice.getDiscount());
		newFuelInvoice.setGst(fuelInvoice.getGst());
		newFuelInvoice.setDiscountGstTypeId(fuelInvoice.getDiscountGstTypeId());
		newFuelInvoice.setInvoiceDate(fuelInvoice.getInvoiceDate());
		newFuelInvoice.setInvoiceNumber(fuelInvoice.getInvoiceNumber());
		newFuelInvoice.setDescription(fuelInvoice.getDescription());
		newFuelInvoice.setCompanyId(fuelInvoice.getCompanyId());
		newFuelInvoice.setCreatedById(userDetails.getId());
		newFuelInvoice.setLastUpdatedById(userDetails.getId());
		newFuelInvoice.setCreatedOn(new Date());
		newFuelInvoice.setLastUpdatedOn(new Date());
		newFuelInvoice.setPetrolPumpId(valueObject.getInt("toPetrolPumpId",0));
		newFuelInvoice.setVendorId(fuelInvoice.getVendorId());
		newFuelInvoice.setPaymentTypeId((short) 1);
		newFuelInvoice.setPaymentNumber(fuelInvoice.getPaymentNumber());
		newFuelInvoice.setPoNumber(fuelInvoice.getPoNumber());
		newFuelInvoice.setBalanceStock(valueObject.getDouble("quantity",0));
		newFuelInvoice.setStockTypeId(FuelInvoiceConstant.STOCK_TYPE_DEFAULT);
		newFuelInvoice.setRemark(FuelInvoiceConstant.getStockType(FuelInvoiceConstant.STOCK_TYPE_DEFAULT));
		newFuelInvoice.setUpdatedStock(0.0);
		newFuelInvoice.setTransferedFrom(fuelInvoice.getFuelInvoiceId());
		return newFuelInvoice;
		
	} 
	
	
	public static FuelTransferDetails prepareFuelTransfer(ValueObject valueObject,FuelInvoiceDto fuelInvoice,CustomUserDetails userDetails) throws Exception {
		FuelTransferDetails fuelTransferDetails = new FuelTransferDetails();
		fuelTransferDetails.setToPetrolPumpId(valueObject.getInt("toPetrolPumpId",0));
		fuelTransferDetails.setFromPetrolPumpId(valueObject.getInt("fromPetrolPumpId",0));
		fuelTransferDetails.setTransferQuantity(valueObject.getDouble("quantity",0));
		fuelTransferDetails.setCompanyId(userDetails.getCompany_id());
		fuelTransferDetails.setFuelInvoiceId(valueObject.getLong("invoiceList",0));
		fuelTransferDetails.setDiscount(fuelInvoice.getDiscount());
		fuelTransferDetails.setDiscountGstTypeId(fuelInvoice.getDiscountGstTypeId());
		fuelTransferDetails.setGst(fuelInvoice.getGst());
		fuelTransferDetails.setRate(fuelInvoice.getRate());
		fuelTransferDetails.setTransferedById(userDetails.getId());
		fuelTransferDetails.setTransferedOn(new Date());
		fuelTransferDetails.setRemark(valueObject.getString("remark",""));
		return fuelTransferDetails;
	} 

	public static FuelStockDetails prepareFuelStockDetails(ValueObject valueObject,int toPetrolPumpId,Double quantity) throws Exception {
		
		FuelStockDetails preStockDetails	= null;
			try {
					preStockDetails	= new FuelStockDetails();
					preStockDetails.setPetrolPumpId(toPetrolPumpId);
					preStockDetails.setStockQuantity(quantity);
					preStockDetails.setTotalFuelCost(valueObject.getDouble("tatalcost",0));
					preStockDetails.setCompanyId(valueObject.getInt("companyId",0));
					preStockDetails.setAvgFuelRate(Utility.round((preStockDetails.getTotalFuelCost()/preStockDetails.getStockQuantity()),2));
				}
				
			 catch (Exception e) {
				throw e;
			}
		return preStockDetails;
		
	}
}

