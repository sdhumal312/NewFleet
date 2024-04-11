package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.constant.TripsheetPickAndDropConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.PickAndDropLocation;
import org.fleetopgroup.persistence.model.TripsheetPickAndDrop;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoice;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoiceSummary;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropPayment;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PickAndDropLocationBL {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

	public PickAndDropLocationBL() {
		super();
	}

	// save the VehicleStatus Model
	public PickAndDropLocation preparePickAndDropLocation(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			PickAndDropLocation pickAndDropLocation = new PickAndDropLocation();
			pickAndDropLocation.setLocationName(valueObject.getString("locationName"));
			pickAndDropLocation.setDescription(valueObject.getString("description"));
			pickAndDropLocation.setCreatedById(userDetails.getId());
			pickAndDropLocation.setLastUpdatedBy(userDetails.getId());
			pickAndDropLocation.setCreationDate(toDate);
			pickAndDropLocation.setLastUpdatedOn(toDate);
			pickAndDropLocation.setCompanyId(userDetails.getCompany_id());
			pickAndDropLocation.setMarkForDelete(false);

			return pickAndDropLocation;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}
	
	public TripsheetPickAndDrop dispatchPickAndDropTrip(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDrop dispatchPickDrop = new TripsheetPickAndDrop();
			
			dispatchPickDrop.setVid(valueObject.getInt("vid"));
			
			if (valueObject.getString("journeyDate") != null && !valueObject.getString("journeyDate").trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				if(valueObject.getString("journeyTime") != null && valueObject.getString("journeyTime") != "") {
					start_time	= valueObject.getString("journeyTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("journeyDate"), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				dispatchPickDrop.setJourneyDate(Reported_Date);
			}
			
			dispatchPickDrop.setTripFristDriverID(valueObject.getInt("tripFristDriverID"));
			dispatchPickDrop.setRate(valueObject.getDouble("rate"));
			
			if(valueObject.getString("newParty") == "" || valueObject.getString("newParty").isEmpty()) {
				dispatchPickDrop.setVendorId(valueObject.getInt("vendorId"));
				dispatchPickDrop.setNewVendorName(valueObject.getString("newParty", null));
			} else {
				dispatchPickDrop.setVendorId(0);
				dispatchPickDrop.setNewVendorName(valueObject.getString("newParty", null));
			}
			
			
			if(valueObject.getString("newRouteName") == "" || valueObject.getString("newRouteName").isEmpty()) {
				
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.PICKUP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.PICKUP);
					dispatchPickDrop.setPickOrDropId(valueObject.getLong("pickId"));
				}
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.DROP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.DROP);
					dispatchPickDrop.setPickOrDropId(valueObject.getLong("dropId"));
				}
				
				dispatchPickDrop.setNewPickOrDropLocationName(valueObject.getString("newRouteName", null));
				
			} else {
				
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.PICKUP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.PICKUP);
					dispatchPickDrop.setPickOrDropId((long)0);
				}
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.DROP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.DROP);
					dispatchPickDrop.setPickOrDropId((long)0);
				}
				
				dispatchPickDrop.setNewPickOrDropLocationName(valueObject.getString("newRouteName", null));
				
			}
			
			dispatchPickDrop.setTripUsageKM(valueObject.getInt("tripUsageKM"));
			dispatchPickDrop.setAmount(valueObject.getDouble("amount"));
			
			if(!valueObject.getString("tripTotalAdvance").isEmpty()) {
				dispatchPickDrop.setTripTotalAdvance(valueObject.getDouble("tripTotalAdvance"));
			} else {
				dispatchPickDrop.setTripTotalAdvance(0.0);
			}
			
			dispatchPickDrop.setRemark(valueObject.getString("remark"));
			dispatchPickDrop.setInvoiceStatus(TripsheetPickAndDropConstant.INVOICE_NOT_MADE);
			dispatchPickDrop.setCreatedById(userDetails.getId());
			dispatchPickDrop.setLastUpdatedBy(userDetails.getId());
			dispatchPickDrop.setCreationDate(toDate);
			dispatchPickDrop.setLastUpdatedOn(toDate);
			dispatchPickDrop.setCompanyId(userDetails.getCompany_id());
			dispatchPickDrop.setMarkForDelete(false);

			return dispatchPickDrop;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
	public TripsheetPickAndDrop updatePickAndDropTrip(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDrop dispatchPickDrop = new TripsheetPickAndDrop();
			TripsheetPickAndDrop oldData		  = (TripsheetPickAndDrop) valueObject.get("oldData");
			
			dispatchPickDrop.setTripsheetPickAndDropId(valueObject.getLong("tripsheetPickAndDropId"));
			dispatchPickDrop.setTripSheetNumber(oldData.getTripSheetNumber());
			dispatchPickDrop.setVid(valueObject.getInt("vid"));
			
			if (valueObject.getString("journeyDate") != null && !valueObject.getString("journeyDate").trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				if(valueObject.getString("journeyTime") != null && valueObject.getString("journeyTime") != "") {
					start_time	= valueObject.getString("journeyTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("journeyDate"), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				dispatchPickDrop.setJourneyDate(Reported_Date);
			}
			
			dispatchPickDrop.setTripFristDriverID(valueObject.getInt("tripFristDriverID"));
			dispatchPickDrop.setRate(valueObject.getDouble("rate"));
			
			if(oldData.getVendorId() > 0) {
				dispatchPickDrop.setVendorId(valueObject.getInt("vendorId"));
				dispatchPickDrop.setNewVendorName(oldData.getNewVendorName());
			} else {
				dispatchPickDrop.setVendorId(oldData.getVendorId());
				dispatchPickDrop.setNewVendorName(valueObject.getString("newParty", null));
			}
			
			if(oldData.getPickOrDropId() > 0) {
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.PICKUP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.PICKUP);
					dispatchPickDrop.setPickOrDropId(valueObject.getLong("pickId"));
				}
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.DROP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.DROP);
					dispatchPickDrop.setPickOrDropId(valueObject.getLong("dropId"));
				}
				
				dispatchPickDrop.setNewPickOrDropLocationName(oldData.getNewPickOrDropLocationName());
			} else {
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.PICKUP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.PICKUP);
					dispatchPickDrop.setPickOrDropId((long)0);
				}
				
				if(valueObject.getShort("pickDropStatus") == TripsheetPickAndDropConstant.DROP) {
					dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.DROP);
					dispatchPickDrop.setPickOrDropId((long)0);
				}
				
				dispatchPickDrop.setNewPickOrDropLocationName(valueObject.getString("newRouteName", null));
			}
			
			dispatchPickDrop.setTripUsageKM(valueObject.getInt("tripUsageKM"));
			dispatchPickDrop.setAmount(valueObject.getDouble("amount"));
			
			if(!valueObject.getString("tripTotalAdvance").isEmpty()) {
				dispatchPickDrop.setTripTotalAdvance(valueObject.getDouble("tripTotalAdvance"));
			} else {
				dispatchPickDrop.setTripTotalAdvance(0.0);
			}
			
			dispatchPickDrop.setRemark(valueObject.getString("remark"));
			dispatchPickDrop.setInvoiceStatus(oldData.getInvoiceStatus());
			dispatchPickDrop.setCreatedById(oldData.getCreatedById());
			dispatchPickDrop.setLastUpdatedBy(userDetails.getId());
			dispatchPickDrop.setCreationDate(oldData.getCreationDate());
			dispatchPickDrop.setLastUpdatedOn(toDate);
			dispatchPickDrop.setCompanyId(userDetails.getCompany_id());
			dispatchPickDrop.setMarkForDelete(false);

			return dispatchPickDrop;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
	public TripsheetPickAndDropInvoiceSummary saveInvoiceSummary(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDropInvoiceSummary invoiceSummary = new TripsheetPickAndDropInvoiceSummary();
			invoiceSummary.setVendorId(valueObject.getInt("vendorId"));
			invoiceSummary.setTotalKm(valueObject.getInt("totalKm"));
			invoiceSummary.setTotalAmount(valueObject.getDouble("totalAmount"));
			invoiceSummary.setBalanceAmount(valueObject.getDouble("totalAmount"));
			invoiceSummary.setTotalAdvance(valueObject.getDouble("totalAdvance"));
			invoiceSummary.setInvoiceDate(dateFormat1.parse(valueObject.getString("invoiceDate")));
			invoiceSummary.setPaymentStatus(TripsheetPickAndDropConstant.INVOICE_PAYMENT_NOT_MADE);
			invoiceSummary.setCreatedById(userDetails.getId());
			invoiceSummary.setLastUpdatedBy(userDetails.getId());
			invoiceSummary.setCreationDate(toDate);
			invoiceSummary.setLastUpdatedOn(toDate);
			invoiceSummary.setCompanyId(userDetails.getCompany_id());
			invoiceSummary.setMarkForDelete(false);
			
			return invoiceSummary;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
	public TripsheetPickAndDropInvoice saveInvoice(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDropInvoice invoice = new TripsheetPickAndDropInvoice();
			invoice.setTripsheetPickAndDropId(valueObject.getLong("tripsheetPickAndDropId"));
			invoice.setCreatedById(userDetails.getId());
			invoice.setLastUpdatedBy(userDetails.getId());
			invoice.setCreationDate(toDate);
			invoice.setLastUpdatedOn(toDate);
			invoice.setCompanyId(userDetails.getCompany_id());
			invoice.setMarkForDelete(false);
			
			return invoice;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
	public TripsheetPickAndDropPayment saveInvoicePayment(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDropPayment invoicePayment = new TripsheetPickAndDropPayment();
			
			invoicePayment.setTripsheetPickAndDropInvoiceSummaryId(valueObject.getLong("invoiceSummaryId"));
			invoicePayment.setPaymentType(valueObject.getShort("paymentType"));
			if(valueObject.getString("transNo") != null) {
				invoicePayment.setTransactionNumber(valueObject.getString("transNo"));
			}else {
				invoicePayment.setTransactionNumber("--");
			}
			invoicePayment.setPaymentMode(valueObject.getShort("paymentMode"));
			invoicePayment.setPaidDate(DateTimeUtility.getTimeStamp(valueObject.getString("paidDate"), DateTimeUtility.DDMMYYYY));
			invoicePayment.setPaidAmount(valueObject.getDouble("paidAmount"));
			invoicePayment.setCreatedById(userDetails.getId());
			invoicePayment.setLastUpdatedBy(userDetails.getId());
			invoicePayment.setCreationDate(toDate);
			invoicePayment.setLastUpdatedOn(toDate);
			invoicePayment.setCompanyId(userDetails.getCompany_id());
			invoicePayment.setMarkForDelete(false);
			
			return invoicePayment;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
	public TripsheetPickAndDrop dispatchPickAndDropTripFromMobile(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDrop dispatchPickDrop = new TripsheetPickAndDrop();
			
			dispatchPickDrop.setVid(valueObject.getInt("vid"));
			
			if (valueObject.getString("journeyDate") != null && !valueObject.getString("journeyDate").trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				if(valueObject.getString("journeyTime") != null && valueObject.getString("journeyTime") != "") {
					start_time	= valueObject.getString("journeyTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("journeyDate"), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				dispatchPickDrop.setJourneyDate(Reported_Date);
			}
			
			dispatchPickDrop.setTripFristDriverID(valueObject.getInt("tripFristDriverID"));
			dispatchPickDrop.setVendorId(valueObject.getInt("vendorId"));
			dispatchPickDrop.setNewVendorName(valueObject.getString("newParty", null));
			dispatchPickDrop.setRate(valueObject.getDouble("rate"));
			
			if(valueObject.getShort("pickOrDropStatus") == TripsheetPickAndDropConstant.PICKUP) {
				dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.PICKUP);
				dispatchPickDrop.setPickOrDropId(valueObject.getLong("pickOrDropId"));
			}else {
				dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.DROP);
				dispatchPickDrop.setPickOrDropId(valueObject.getLong("pickOrDropId"));
			}
			
			dispatchPickDrop.setNewPickOrDropLocationName(valueObject.getString("newRouteName", null));
			dispatchPickDrop.setTripUsageKM(valueObject.getInt("tripUsageKM"));
			dispatchPickDrop.setAmount(valueObject.getDouble("amount"));
			
			if(!valueObject.getString("tripTotalAdvance").isEmpty()) {
				dispatchPickDrop.setTripTotalAdvance(valueObject.getDouble("tripTotalAdvance"));
			} else {
				dispatchPickDrop.setTripTotalAdvance(0.0);
			}
			
			dispatchPickDrop.setRemark(valueObject.getString("remark"));
			dispatchPickDrop.setInvoiceStatus(TripsheetPickAndDropConstant.INVOICE_NOT_MADE);
			dispatchPickDrop.setCreatedById(userDetails.getId());
			dispatchPickDrop.setLastUpdatedBy(userDetails.getId());
			dispatchPickDrop.setCreationDate(toDate);
			dispatchPickDrop.setLastUpdatedOn(toDate);
			dispatchPickDrop.setCompanyId(userDetails.getCompany_id());
			dispatchPickDrop.setMarkForDelete(false);

			return dispatchPickDrop;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
	public TripsheetPickAndDrop updatePickAndDropTripFromMobile(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		Date 		currentDateUpdate  	= null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			TripsheetPickAndDrop dispatchPickDrop = new TripsheetPickAndDrop();
			TripsheetPickAndDrop oldData		  = (TripsheetPickAndDrop) valueObject.get("oldData");
			
			dispatchPickDrop.setTripsheetPickAndDropId(valueObject.getLong("tripsheetPickAndDropId"));
			dispatchPickDrop.setTripSheetNumber(oldData.getTripSheetNumber());
			dispatchPickDrop.setVid(valueObject.getInt("vid"));
			
			if (valueObject.getString("journeyDate") != null && !valueObject.getString("journeyDate").trim().equalsIgnoreCase("")) {
				String start_time = "00:00";
				if(valueObject.getString("journeyTime") != null && valueObject.getString("journeyTime") != "") {
					start_time	= valueObject.getString("journeyTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("journeyDate"), start_time);
				java.sql.Date Reported_Date = new java.sql.Date(date.getTime());
				dispatchPickDrop.setJourneyDate(Reported_Date);
			}
			
			dispatchPickDrop.setTripFristDriverID(valueObject.getInt("tripFristDriverID"));
			dispatchPickDrop.setVendorId(valueObject.getInt("vendorId"));
			dispatchPickDrop.setNewVendorName(valueObject.getString("newParty", null));
			dispatchPickDrop.setRate(valueObject.getDouble("rate"));
			
			if(valueObject.getShort("pickOrDropStatus") == TripsheetPickAndDropConstant.PICKUP) {
				dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.PICKUP);
				dispatchPickDrop.setPickOrDropId(valueObject.getLong("pickOrDropId"));
			}else {
				dispatchPickDrop.setPickOrDropStatus(TripsheetPickAndDropConstant.DROP);
				dispatchPickDrop.setPickOrDropId(valueObject.getLong("pickOrDropId"));
			}
			
			dispatchPickDrop.setNewPickOrDropLocationName(valueObject.getString("newRouteName", null));
			dispatchPickDrop.setTripUsageKM(valueObject.getInt("tripUsageKM"));
			dispatchPickDrop.setAmount(valueObject.getDouble("amount"));
			
			if(!valueObject.getString("tripTotalAdvance").isEmpty()) {
				dispatchPickDrop.setTripTotalAdvance(valueObject.getDouble("tripTotalAdvance"));
			} else {
				dispatchPickDrop.setTripTotalAdvance(0.0);
			}
			
			dispatchPickDrop.setRemark(valueObject.getString("remark"));
			dispatchPickDrop.setInvoiceStatus(oldData.getInvoiceStatus());
			dispatchPickDrop.setCreatedById(oldData.getCreatedById());
			dispatchPickDrop.setLastUpdatedBy(userDetails.getId());
			dispatchPickDrop.setCreationDate(oldData.getCreationDate());
			dispatchPickDrop.setLastUpdatedOn(toDate);
			dispatchPickDrop.setCompanyId(userDetails.getCompany_id());
			dispatchPickDrop.setMarkForDelete(false);

			return dispatchPickDrop;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
		}
	}
	
}
