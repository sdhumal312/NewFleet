package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesRemarkDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceEntriesRemark;
import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToLabor;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToParts;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ServiceEntriesBL {

	public ServiceEntriesBL() {
	}

	Date currentDate = new Date();
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass()); //latest
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat toFixedTwo 	= new DecimalFormat("##.##");

	VehicleBL VBL = new VehicleBL();
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// get ServiceEntries information in database
	public ServiceEntriesDto Show_ServiceEntries(ServiceEntries ServiceEntries) throws Exception {

		ServiceEntriesDto work = new ServiceEntriesDto();

		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
		work.setVid(ServiceEntries.getVid());

		work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
		if (ServiceEntries.getInvoiceDate() != null) {
			work.setInvoiceDate(dateFormat_Name.format(ServiceEntries.getInvoiceDate()));
		}
		if (ServiceEntries.getService_paiddate() != null) {
			work.setService_paiddate(dateFormat_Name.format(ServiceEntries.getService_paiddate()));
		}

		if (ServiceEntries.getCompleted_date() != null) {
			work.setCompleted_date(dateFormat_Name.format(ServiceEntries.getCompleted_date()));
		}

		work.setDriver_id(ServiceEntries.getDriver_id());

		work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
		work.setJobNumber(ServiceEntries.getJobNumber());
		work.setService_PayNumber(ServiceEntries.getService_PayNumber());

		work.setVendor_id(ServiceEntries.getVendor_id());

		work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());
		work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost());

		work.setService_document(ServiceEntries.isService_document());
		work.setService_document_id(ServiceEntries.getService_document_id());

		if (ServiceEntries.getCreated() != null) {
			work.setCreated(CreatedDateTime.format(ServiceEntries.getCreated()));
		}

		if (ServiceEntries.getLastupdated() != null) {
			work.setLastupdated(CreatedDateTime.format(ServiceEntries.getLastupdated()));
		}

		return work;
	}
	
	
	public ServiceEntriesDto Show_ServiceEntries(ServiceEntriesDto ServiceEntries) throws Exception {

		ServiceEntriesDto work = new ServiceEntriesDto();

		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
		work.setVid(ServiceEntries.getVid());
		work.setVehicle_registration(ServiceEntries.getVehicle_registration());
		work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
		if (ServiceEntries.getInvoiceDateOn() != null) {
			work.setInvoiceDate(dateFormat_Name.format(ServiceEntries.getInvoiceDateOn()));
		}
		if (ServiceEntries.getService_paiddateOn() != null) {
			work.setService_paiddate(dateFormat_Name.format(ServiceEntries.getService_paiddateOn()));
		}

		if (ServiceEntries.getCompleted_dateOn() != null) {
			work.setCompleted_dateOn(ServiceEntries.getCompleted_dateOn());
			work.setCompleted_date(dateFormat_Name.format(ServiceEntries.getCompleted_dateOn()));
		}

		work.setDriver_id(ServiceEntries.getDriver_id());
		work.setDriver_name(ServiceEntries.getDriver_name());

		work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
		work.setJobNumber(ServiceEntries.getJobNumber());
		work.setServiceEntries_status(ServiceEntries.getServiceEntries_status());
		work.setServiceEntries_statusId(ServiceEntries.getServiceEntries_statusId());
		work.setService_paymentType(ServiceEntries.getService_paymentType());
		work.setService_paymentTypeId(ServiceEntries.getService_paymentTypeId());
		work.setService_PayNumber(ServiceEntries.getService_PayNumber());
		work.setService_paidby(ServiceEntries.getService_paidby());
		work.setService_paidbyId(ServiceEntries.getService_paidbyId());
		work.setVendor_id(ServiceEntries.getVendor_id());
		work.setVendor_name(ServiceEntries.getVendor_name());
		work.setVendor_location(ServiceEntries.getVendor_location());

		work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());
		if(ServiceEntries.getWorkshopInvoiceAmount() != null) {
		work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost()+ServiceEntries.getWorkshopInvoiceAmount());
		}else {
			work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost());
		}
		work.setGpsOdometer(ServiceEntries.getGpsOdometer());
		work.setGpsOdometer(ServiceEntries.getGpsOdometer());
		if(ServiceEntries.getWorkshopInvoiceAmount() != null) {
			work.setWorkshopInvoiceAmount(ServiceEntries.getWorkshopInvoiceAmount());
		}else {
			work.setWorkshopInvoiceAmount(0.0);
		}
		work.setService_document_id(ServiceEntries.getService_document_id());

		// Create and Last updated Display
		work.setCreatedBy(ServiceEntries.getCreatedBy());
		if (ServiceEntries.getCreatedOn() != null) {
			work.setCreated(CreatedDateTime.format(ServiceEntries.getCreatedOn()));
		}
		work.setLastModifiedBy(ServiceEntries.getLastModifiedBy());
		if (ServiceEntries.getLastupdatedOn() != null) {
			work.setLastupdated(CreatedDateTime.format(ServiceEntries.getLastupdatedOn()));
		}
		work.setTallyCompanyName(ServiceEntries.getTallyCompanyName());
		work.setTallyExpenseName(ServiceEntries.getTallyExpenseName());
		work.setTripSheetId(ServiceEntries.getTripSheetId());
		work.setTripSheetNumber(ServiceEntries.getTripSheetNumber());
		work.setCompanyId(ServiceEntries.getCompanyId());
		
		return work;
	}

	// get ServiceEntries information in database
	public ServiceEntriesDto getServiceEntries(ServiceEntriesDto ServiceEntries) throws Exception {

		ServiceEntriesDto work = new ServiceEntriesDto();

		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
		work.setVid(ServiceEntries.getVid());
		work.setVehicle_registration(ServiceEntries.getVehicle_registration());
		work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
		if (ServiceEntries.getInvoiceDate() != null) {
			work.setInvoiceDate(dateFormat.format(ServiceEntries.getInvoiceDate()));
		}
		if (ServiceEntries.getService_paiddate() != null) {
			work.setService_paiddate(dateFormat.format(ServiceEntries.getService_paiddate()));
		}

		if (ServiceEntries.getCompleted_date() != null) {
			work.setCompleted_date(dateFormat.format(ServiceEntries.getCompleted_date()));
		}

		work.setDriver_id(ServiceEntries.getDriver_id());
		work.setDriver_name(ServiceEntries.getDriver_name());

		work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
		work.setJobNumber(ServiceEntries.getJobNumber());
		work.setServiceEntries_status(ServiceEntries.getServiceEntries_status());
		work.setService_paymentType(ServiceEntries.getService_paymentType());
		work.setService_PayNumber(ServiceEntries.getService_PayNumber());
		work.setService_paidby(ServiceEntries.getService_paidby());

		work.setVendor_id(ServiceEntries.getVendor_id());
		work.setVendor_name(ServiceEntries.getVendor_name());
		work.setVendor_location(ServiceEntries.getVendor_location());

		work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());
		work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost());

		work.setService_document(ServiceEntries.isService_document());
		work.setService_document_id(ServiceEntries.getService_document_id());

		return work;
	}
	
	public ServiceEntriesDto getServiceEntriesDetails(ServiceEntriesDto ServiceEntries) throws Exception {

		ServiceEntriesDto work = new ServiceEntriesDto();

		work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
		work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
		work.setVid(ServiceEntries.getVid());
		work.setVehicle_registration(ServiceEntries.getVehicle_registration());
		work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
		if (ServiceEntries.getInvoiceDateOn() != null) {
			work.setInvoiceDate(dateFormat.format(ServiceEntries.getInvoiceDateOn()));
		}
		if (ServiceEntries.getService_paiddateOn() != null) {
			work.setService_paiddate(dateFormat.format(ServiceEntries.getService_paiddateOn()));
		}

		if (ServiceEntries.getCompleted_dateOn() != null) {
			work.setCompleted_date(dateFormat.format(ServiceEntries.getCompleted_dateOn()));
		}

		work.setDriver_id(ServiceEntries.getDriver_id());
		work.setDriver_name(ServiceEntries.getDriver_name());

		work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
		work.setJobNumber(ServiceEntries.getJobNumber());
		work.setServiceEntries_status(ServiceEntries.getServiceEntries_status());
		work.setService_paymentType(ServiceEntries.getService_paymentType());
		work.setServiceEntries_statusId(ServiceEntries.getServiceEntries_statusId());
		work.setService_paymentTypeId(ServiceEntries.getService_paymentTypeId());
		work.setService_PayNumber(ServiceEntries.getService_PayNumber());
		work.setService_paidby(ServiceEntries.getService_paidby());
		work.setService_paidbyId(ServiceEntries.getService_paidbyId());

		work.setVendor_id(ServiceEntries.getVendor_id());
		work.setVendor_name(ServiceEntries.getVendor_name());
		work.setVendor_location(ServiceEntries.getVendor_location());

		work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());
		work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost());

		work.setService_document(ServiceEntries.isService_document());
		work.setService_document_id(ServiceEntries.getService_document_id());
		work.setGpsOdometer(ServiceEntries.getGpsOdometer());
		work.setWorkshopInvoiceAmount(ServiceEntries.getWorkshopInvoiceAmount());
		work.setTallyCompanyId(ServiceEntries.getTallyCompanyId());
		work.setTallyCompanyName(ServiceEntries.getTallyCompanyName());
		work.setTallyExpenseName(ServiceEntries.getTallyExpenseName());
		work.setTallyExpenseId(ServiceEntries.getTallyExpenseId());
		work.setTripSheetId(ServiceEntries.getTripSheetId());
		work.setTripSheetNumber(ServiceEntries.getTripSheetNumber());
		work.setISSUES_ID(ServiceEntries.getISSUES_ID());
		work.setAccidentId(ServiceEntries.getAccidentId());
		work.setIssueNumber(ServiceEntries.getIssueNumber());
		work.setIssueSummary(ServiceEntries.getIssueSummary());
		return work;
	}

	/* list of ServiceEntries get and Display to open ServiceEntries */
	public List<ServiceEntriesDto> prepareListofServiceEntries(List<ServiceEntriesDto> ServiceEntriesList) {
		List<ServiceEntriesDto> Dtos = null;
		if (ServiceEntriesList != null && !ServiceEntriesList.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto work = null;
			for (ServiceEntriesDto ServiceEntries : ServiceEntriesList) {
				work = new ServiceEntriesDto();

				work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
				work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
				work.setVid(ServiceEntries.getVid());
				work.setVehicle_registration(ServiceEntries.getVehicle_registration());
				work.setVehicle_Group(ServiceEntries.getVehicle_Group());
				work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
				work.setVendor_name(ServiceEntries.getVendor_name());
				work.setVendor_location(ServiceEntries.getVendor_location());
				work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
				work.setJobNumber(ServiceEntries.getJobNumber());

				if (ServiceEntries.getInvoiceDate() != null) {
					work.setInvoiceDate(dateFormat.format(ServiceEntries.getInvoiceDate()));
				}

				work.setServiceEntries_status(ServiceEntries.getServiceEntries_status());

				work.setService_paidby(ServiceEntries.getService_paidby());
				work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());
				work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost());

				work.setService_paymentType(ServiceEntries.getService_paymentType());

				work.setService_document(ServiceEntries.isService_document());
				work.setService_document_id(ServiceEntries.getService_document_id());
				Dtos.add(work);
			}
		}
		return Dtos;
	}
	
	
	public List<ServiceEntriesDto> prepareListServiceEntries(List<ServiceEntriesDto> ServiceEntriesList) {
		List<ServiceEntriesDto> Dtos = null;
		if (ServiceEntriesList != null && !ServiceEntriesList.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto work = null;
			for (ServiceEntriesDto ServiceEntries : ServiceEntriesList) {
				work = new ServiceEntriesDto();

				work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
				work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
				work.setVid(ServiceEntries.getVid());
				work.setVehicle_registration(ServiceEntries.getVehicle_registration());
				work.setVehicle_Group(ServiceEntries.getVehicle_Group());
				work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
				work.setVendor_name(ServiceEntries.getVendor_name());
				work.setVendor_location(ServiceEntries.getVendor_location());
				work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());
				work.setJobNumber(ServiceEntries.getJobNumber());

				if (ServiceEntries.getInvoiceDateOn() != null) {
					work.setInvoiceDate(dateFormat.format(ServiceEntries.getInvoiceDateOn()));
				}
				if (ServiceEntries.getService_paiddateOn() != null) {
					work.setService_paiddate(dateFormat.format(ServiceEntries.getService_paiddateOn()));
				}

				work.setServiceEntries_status(ServiceEntries.getServiceEntries_status());
				work.setServiceEntries_statusId(ServiceEntries.getServiceEntries_statusId());

				work.setService_paidby(ServiceEntries.getService_paidby());
				work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());
				work.setTotalserviceROUND_cost(ServiceEntries.getTotalserviceROUND_cost());

				work.setService_paymentType(ServiceEntries.getService_paymentType());
				work.setService_paymentTypeId(ServiceEntries.getService_paymentTypeId());

				work.setService_document(ServiceEntries.isService_document());
				work.setService_document_id(ServiceEntries.getService_document_id());
				
				work.setLastModifiedById(ServiceEntries.getLastModifiedById());
				work.setLastModifiedBy(ServiceEntries.getLastModifiedBy());
				
				Dtos.add(work);
			}
		}
		return Dtos;
	}

	/*
	 * list of ServiceEntries get and Display to Filter Approval List
	 * ServiceEntries
	 */
	public List<ServiceEntriesDto> prepareListofServiceEntries_Filter_Approval_OTHERE(
			List<ServiceEntriesDto> ServiceEntriesList, List<PurchaseOrdersDto> PurchaseOrdersList,
			List<InventoryTyreInvoiceDto> TyreEntries, List<InventoryTyreRetreadDto> TyreRetreadEntries, 
			List<BatteryInvoiceDto> batteryInvoiceList, List<PartInvoiceDto> partInvoiceList, List<ClothInvoiceDto> clothInvoiceList,
			List<UpholsterySendLaundryInvoiceDto>   laundryInvoiceList) {
		List<ServiceEntriesDto> Dtos = new ArrayList<ServiceEntriesDto>();
		ServiceEntriesDto work = null;
		// ServiceEntriesList List
		if (ServiceEntriesList != null && !ServiceEntriesList.isEmpty()) {

			for (ServiceEntriesDto ServiceEntries : ServiceEntriesList) {
				work = new ServiceEntriesDto();

				work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
				work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
				work.setVid(ServiceEntries.getVid());
				work.setVehicle_registration(ServiceEntries.getVehicle_registration());
				work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());

				if (ServiceEntries.getInvoiceDateOn() != null) {
					work.setInvoiceDate(dateFormat_Name.format(ServiceEntries.getInvoiceDateOn()));
				}

				work.setJobNumber(ServiceEntries.getJobNumber());

				work.setTotalservice_cost(ServiceEntries.getTotalservice_cost());

				work.setServiceEntries_status("SE");
				work.setPaidAmount(ServiceEntries.getPaidAmount());

				Dtos.add(work);
			}
		}

		if (PurchaseOrdersList != null && !PurchaseOrdersList.isEmpty()) {

			for (PurchaseOrdersDto PurchaseOrders : PurchaseOrdersList) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(PurchaseOrders.getPurchaseorder_id());
				work.setServiceEntries_Number(PurchaseOrders.getPurchaseorder_Number());
				work.setVid(0);
				work.setVehicle_registration(PurchaseOrders.getPurchaseorder_vendor_name());
				work.setVendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
				work.setVendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
				work.setInvoiceNumber(PurchaseOrders.getPurchaseorder_invoiceno());

				if (PurchaseOrders.getPurchaseorder_invoice_date_On() != null) {
					work.setInvoiceDate(dateFormat_Name.format(PurchaseOrders.getPurchaseorder_invoice_date_On()));
				}

				work.setJobNumber(PurchaseOrders.getPurchaseorder_indentno());

				work.setTotalservice_cost(PurchaseOrders.getPurchaseorder_balancecost());

				work.setServiceEntries_status("PO");
				work.setPaidAmount(PurchaseOrders.getPaidAmount());

				Dtos.add(work);
			}
		}

		// TyreEntries List
		if (TyreEntries != null && !TyreEntries.isEmpty()) {
			for (InventoryTyreInvoiceDto TyreInv : TyreEntries) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(TyreInv.getITYRE_ID());
				work.setServiceEntries_Number(TyreInv.getITYRE_NUMBER());
				work.setVid(0);
				work.setVehicle_registration(TyreInv.getVENDOR_NAME());
				work.setVendor_name(TyreInv.getVENDOR_NAME());
				work.setVendor_location(TyreInv.getVENDOR_LOCATION());
				work.setInvoiceNumber(TyreInv.getINVOICE_NUMBER());

				if (TyreInv.getINVOICE_DATE_ON() != null) {
					work.setInvoiceDate(dateFormat_Name.format(TyreInv.getINVOICE_DATE_ON()));
				}

				work.setJobNumber(TyreInv.getPO_NUMBER());

				work.setTotalservice_cost(TyreInv.getINVOICE_AMOUNT());

				work.setServiceEntries_status("TI");
				
				work.setPaidAmount(TyreInv.getPaidAmount());

				Dtos.add(work);
			}
		}

		// TyreRetreadEntries List
		if (TyreRetreadEntries != null && !TyreRetreadEntries.isEmpty()) {
			for (InventoryTyreRetreadDto TyreRetread : TyreRetreadEntries) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(TyreRetread.getTRID());
				work.setServiceEntries_Number(TyreRetread.getTRNUMBER());
				work.setVid(0);
				work.setVehicle_registration(TyreRetread.getTR_VENDOR_NAME());
				work.setVendor_name(TyreRetread.getTR_VENDOR_NAME());
				work.setInvoiceNumber(TyreRetread.getTR_INVOICE_NUMBER());

				if (TyreRetread.getTR_INVOICE_DATE_ON() != null) {
					work.setInvoiceDate(dateFormat_Name.format(TyreRetread.getTR_INVOICE_DATE_ON()));
				}

				work.setJobNumber(TyreRetread.getTR_MANUAL_NO());

				work.setServiceEntries_status(InventoryTyreRetreadDto.getVendorPaymentModeName(TyreRetread.getTR_VENDOR_PAYMODE_STATUS_ID()));

				work.setTotalservice_cost(TyreRetread.getTR_ROUNT_AMOUNT());

				work.setServiceEntries_status("TR");
				work.setPaidAmount(TyreRetread.getPaidAmount());

				Dtos.add(work);
			}
		}

		// batteryInvoiceList List
		if (batteryInvoiceList != null && !batteryInvoiceList.isEmpty()) {
			for (BatteryInvoiceDto batteryInvoice : batteryInvoiceList) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(batteryInvoice.getBatteryInvoiceId());
				work.setServiceEntries_Number(batteryInvoice.getBatteryInvoiceNumber());
				work.setVid(0);
				work.setVehicle_registration(batteryInvoice.getVendorName());
				work.setVendor_name(batteryInvoice.getVendorName());
				work.setVendor_location(batteryInvoice.getVendorLocation());
				work.setInvoiceNumber(batteryInvoice.getInvoiceNumber());
				
				if (batteryInvoice.getInvoiceDate() != null) {
					work.setInvoiceDate(dateFormat_Name.format(batteryInvoice.getInvoiceDate()));
				}
				
				work.setJobNumber(batteryInvoice.getPoNumber());
				
				work.setTotalservice_cost(batteryInvoice.getInvoiceAmount());
				
				work.setServiceEntries_status("BI");
				work.setPaidAmount(batteryInvoice.getPaidAmount());
				
				Dtos.add(work);
			}
		}
		
		if (partInvoiceList != null && !partInvoiceList.isEmpty()) {
			for (PartInvoiceDto partInvoice : partInvoiceList) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(partInvoice.getPartInvoiceId());
				work.setServiceEntries_Number(partInvoice.getPartInvoiceNumber());
				work.setVid(0);
				work.setVehicle_registration(partInvoice.getVendorName());
				work.setVendor_name(partInvoice.getVendorName());
				work.setVendor_location(partInvoice.getVendorLocation());
				work.setInvoiceNumber(partInvoice.getInvoiceNumber());
				
				if (partInvoice.getInvoiceDate() != null) {
					work.setInvoiceDate(dateFormat_Name.format(partInvoice.getInvoiceDate()));
				}
				
				//work.setJobNumber(partInvoice.getPoNumber());
				
				work.setTotalservice_cost(Double.parseDouble(partInvoice.getInvoiceAmount()));
				
				work.setServiceEntries_status("PI");
				work.setPaidAmount(partInvoice.getPaidAmount());
				work.setBalanceAmount(partInvoice.getBalanceAmount());
				
				Dtos.add(work);
			}
		}
		
		if (clothInvoiceList != null && !clothInvoiceList.isEmpty()) {
			for (ClothInvoiceDto clothInvoice : clothInvoiceList) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(clothInvoice.getClothInvoiceId());
				work.setServiceEntries_Number(clothInvoice.getClothInvoiceNumber());
				work.setVid(0);
				work.setVehicle_registration(clothInvoice.getVendorName());
				work.setVendor_name(clothInvoice.getVendorName());
				work.setVendor_location(clothInvoice.getVendorLocation());
				work.setInvoiceNumber(clothInvoice.getInvoiceNumber());
				
				if (clothInvoice.getInvoiceDate() != null) {
					work.setInvoiceDate(dateFormat_Name.format(clothInvoice.getInvoiceDate()));
				}
				
				work.setTotalservice_cost(clothInvoice.getInvoiceAmount());
				
				work.setServiceEntries_status("CI");
				work.setPaidAmount(clothInvoice.getPaidAmount());
				
				Dtos.add(work);
			}
		} 
		
		if (laundryInvoiceList != null && !laundryInvoiceList.isEmpty()) {
			for (UpholsterySendLaundryInvoiceDto clothInvoice : laundryInvoiceList) {
				work = new ServiceEntriesDto();
				work.setServiceEntries_id(clothInvoice.getLaundryInvoiceId());
				work.setServiceEntries_Number(clothInvoice.getLaundryInvoiceNumber());
				work.setVid(0);
				work.setVehicle_registration(clothInvoice.getVendorName());
				work.setVendor_name(clothInvoice.getVendorName());
				work.setVendor_location(clothInvoice.getVendorLocation());
				work.setInvoiceNumber(clothInvoice.getPaymentNumber());
				
				if (clothInvoice.getSentDate() != null) {
					work.setInvoiceDate(dateFormat_Name.format(clothInvoice.getSentDate()));
				}
				work.setTotalservice_cost(clothInvoice.getTotalCost());
				
				work.setServiceEntries_status("LI");
				if(clothInvoice.getPaidAmount() != null)
					work.setPaidAmount(clothInvoice.getPaidAmount());
				if(clothInvoice.getBalanceAmount() != null)
					work.setBalanceAmount(clothInvoice.getBalanceAmount());
				
				Dtos.add(work);
			}
		} 
		
		return Dtos;
	}

	public List<ServiceEntriesDto> prepareListofServiceEntries_Filter_Approval(
			List<ServiceEntriesDto> ServiceEntriesList) {
		List<ServiceEntriesDto> Dtos = null;

		// ServiceEntriesList List
		if (ServiceEntriesList != null && !ServiceEntriesList.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto work = null;
			for (ServiceEntriesDto ServiceEntries : ServiceEntriesList) {
				work = new ServiceEntriesDto();

				work.setServiceEntries_id(ServiceEntries.getServiceEntries_id());
				work.setServiceEntries_Number(ServiceEntries.getServiceEntries_Number());
				work.setVid(ServiceEntries.getVid());
				work.setVehicle_registration(ServiceEntries.getVehicle_registration());
				work.setVehicle_Group(ServiceEntries.getVehicle_Group());
				work.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());
				work.setVendor_name(ServiceEntries.getVendor_name());
				work.setVendor_location(ServiceEntries.getVendor_location());
				work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());

				if (ServiceEntries.getInvoiceDateOn() != null) {
					work.setInvoiceDate(dateFormat_Name.format(ServiceEntries.getInvoiceDateOn()));
				}
				work.setJobNumber(ServiceEntries.getJobNumber());
				work.setServiceEntries_status(ServiceEntries.getServiceEntries_status());
				
				work.setTotalservice_cost(Double.parseDouble(toFixedTwo.format(ServiceEntries.getTotalservice_cost())));
				work.setPaidAmount(Double.parseDouble(toFixedTwo.format(ServiceEntries.getPaidAmount())));
				work.setBalanceAmount(Double.parseDouble(toFixedTwo.format(ServiceEntries.getBalanceAmount())));

				work.setService_paymentType(ServiceEntries.getService_paymentType());

				work.setService_vendor_paymode(InventoryTyreInvoiceDto.getPaymentModeName(ServiceEntries.getService_vendor_paymodeId()));
				work.setService_document_id(ServiceEntries.getService_document_id());

				Dtos.add(work);
			}
		}

		return Dtos;
	}

	// get ServiceEntries information in database
	public ServiceEntriesTasksDto getServiceEntriesTask(ServiceEntriesTasks ServiceEntriesTask) {

		ServiceEntriesTasksDto ServiceEntries = new ServiceEntriesTasksDto();

		/*
		 * ServiceEntries.setWorkordertaskid(ServiceEntriesTask.
		 * getWorkordertaskid());
		 * ServiceEntries.setServiceEntries_id(ServiceEntriesTask.
		 * getServiceEntries());
		 */

		return ServiceEntries;
	}

	/* delete the ServiceEntries */
	public ServiceEntries deleteServiceEntries(ServiceEntries ServiceEntries) {

		/*
		 * ServiceEntries ServiceEntries=new ServiceEntries();
		 * 
		 * 
		 * ServiceEntries.setIssue_id(ServiceEntries.getIssue_id());
		 * 
		 * 
		 * ServiceEntries.setIssue_vehicle(ServiceEntries.getIssue_vehicle());
		 * ServiceEntries.setIssue_reportdate(ServiceEntries.getIssue_reportdate
		 * ());
		 * ServiceEntries.setIssue_summary(ServiceEntries.getIssue_summary());
		 * ServiceEntries.setIssue_description(ServiceEntries.
		 * getIssue_description());
		 * ServiceEntries.setIssue_odometer(ServiceEntries.getIssue_odometer());
		 * ServiceEntries.setIssue_odometer_attributes(ServiceEntries.
		 * getIssue_odometer_attributes());
		 * ServiceEntries.setIssue_labelNo(ServiceEntries.getIssue_labelNo());
		 * ServiceEntries.setIssue_flag(ServiceEntries.getIssue_flag());
		 * ServiceEntries.setIssue_reportedby(ServiceEntries.getIssue_reportedby
		 * ());
		 * ServiceEntries.setIssue_assignedto(ServiceEntries.getIssue_assignedto
		 * ());
		 * 
		 * ServiceEntries.setCreated(ServiceEntries.getCreated());
		 * ServiceEntries.setLastupdated(ServiceEntries.getLastupdated());
		 */

		return ServiceEntries;
	}
	
	
	// get WorkOrders To Update Service Reminder information in database
		public ServiceReminder ServiceEntriesTOServiceReminder(ServiceReminder serviceReminderFindSameWorkorder,
				ServiceEntriesDto work) throws Exception {

			ServiceReminder service = new ServiceReminder();
			// show Vehicle name
			service.setService_id(serviceReminderFindSameWorkorder.getService_id());
			service.setService_Number(serviceReminderFindSameWorkorder.getService_Number());
			
			service.setServiceTypeId(serviceReminderFindSameWorkorder.getServiceTypeId());
			service.setServiceSubTypeId(serviceReminderFindSameWorkorder.getServiceSubTypeId());
			// show Vehicle name
			service.setVid(serviceReminderFindSameWorkorder.getVid());
			service.setVehicleGroupId(serviceReminderFindSameWorkorder.getVehicleGroupId());

			service.setMeter_interval(serviceReminderFindSameWorkorder.getMeter_interval());
			service.setTime_interval(serviceReminderFindSameWorkorder.getTime_interval());
			service.setTime_intervalperiodId(serviceReminderFindSameWorkorder.getTime_intervalperiodId());

			service.setMeter_threshold(serviceReminderFindSameWorkorder.getMeter_threshold());
			service.setServiceType(serviceReminderFindSameWorkorder.getServiceType());

			service.setTime_threshold(serviceReminderFindSameWorkorder.getTime_threshold());
			service.setTime_thresholdperiodId(serviceReminderFindSameWorkorder.getTime_thresholdperiodId());

			// subscribeduser name in old
			service.setService_subscribeduser_name(serviceReminderFindSameWorkorder.getService_subscribeduser_name());

			service.setCreated(serviceReminderFindSameWorkorder.getCreated());

			// System.out.println("Odometer" + work.getVehicle_Odometer());
			// workOrder to Get odometer
			service.setVehicle_currentOdometer(work.getVehicle_Odometer());

			// get meter interval calculation to service meter
			// CurrentOdometer + Meter interval eg: 5000+500
			// =5500
			// service

			// workOrder to Get odometer
			Integer CurrentOdometer = work.getVehicle_Odometer();
			Integer Meter_interval = serviceReminderFindSameWorkorder.getMeter_interval();

			if (CurrentOdometer == null) {
				CurrentOdometer = 0;
			}
			if (Meter_interval == null) {
				Meter_interval = 0;
			}
			Integer ServiceOdometer = CurrentOdometer + Meter_interval;
			// save Service meter_Odometer reading
			service.setMeter_serviceodometer(ServiceOdometer);

			if (serviceReminderFindSameWorkorder.getMeter_threshold() != null) {

				Integer meter_threshold = serviceReminderFindSameWorkorder.getMeter_threshold();
				if (ServiceOdometer == 0) {
					meter_threshold = 0;
				}

				Integer ServiceOdometer_threshold = ServiceOdometer - meter_threshold;
				// save advance Meter Threshold time and period
				service.setMeter_servicethreshold_odometer(ServiceOdometer_threshold);
			}

			// get Current days
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			// save current days
			service.setTime_interval_currentdate(toDate);
			// System.out.println("current date =" + toDate);

			// time interval not equeal to null only enter the
			// value
			if (serviceReminderFindSameWorkorder.getTime_interval() != null) {

				// get time interval calculation to service to
				// get
				// service time interval days
				Integer time_Intervalperiod = 0;
				if (serviceReminderFindSameWorkorder.getTime_intervalperiodId() >= 0) {
					time_Intervalperiod = serviceReminderFindSameWorkorder.getTime_interval();
				}
				Integer timeserviceDaysPeriod = 0;
				switch (serviceReminderFindSameWorkorder.getTime_intervalperiodId()) {
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
					timeserviceDaysPeriod = time_Intervalperiod;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
					timeserviceDaysPeriod = time_Intervalperiod * 7;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
					timeserviceDaysPeriod = time_Intervalperiod * 30;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
					timeserviceDaysPeriod = time_Intervalperiod * 365;
					break;

				default:
					timeserviceDaysPeriod = time_Intervalperiod;
					break;
				}

				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				// System.out.println("Service time_date =" +
				// format.format(calendar.getTime()));

				// save Service Time_interval Reminder
				service.setTime_servicedate(calendar.getTime());

				if (serviceReminderFindSameWorkorder.getTime_threshold() != null) {

					Integer Time_threshold = 0;
					switch (serviceReminderFindSameWorkorder.getTime_thresholdperiodId()) {
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
						Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold();
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
						Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold() * 7;
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
						Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold() * 30;
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
						Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold() * 365;
						break;

					default:
						Time_threshold = serviceReminderFindSameWorkorder.getTime_threshold();
						break;
					}
					final Calendar calendar_advanceThreshold = Calendar.getInstance();
					// System.out.println("Service =" +
					// Time_servicedate);
					// this Time_servicedate is service time Day
					// so that
					// day to minus advance
					// time threshold time
					calendar_advanceThreshold.setTime(calendar.getTime());
					calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
					/*
					 * System.out.println( "Service time_advancedate =" +
					 * format.format(calendar_advanceThreshold. getTime() ));
					 */
					// fuel date converted String to date Format

					// save Service Time_interval Advance
					// Threshold
					// dateReminder
					
					
					service.setTime_servicethreshold_date(calendar_advanceThreshold.getTime());

				}
			}

			// service time set FIRST times
			Integer Service_Howtimes = serviceReminderFindSameWorkorder.getService_remider_howtimes() + 1;
			service.setService_remider_howtimes(Service_Howtimes);
			//service.setServicestatus("ACTIVE");

			/**
			 * who Create this vehicle get email id to user profile details
			 */
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			//service.setLastModifiedBy(userDetails.getEmail());
			service.setLastModifiedById(userDetails.getId());
			service.setLastupdated(toDate);

			service.setLast_servicecompleldid(work.getServiceEntries_id());
			//service.setLast_servicecompleldby(work.getAssignee());
			service.setLast_servicecompleldbyId(work.getLastModifiedById());
			service.setLast_servicedate(toDate);
			//service.setWorkorders_id(work.getWorkorders_id());
			service.setServiceEntries_id(work.getServiceEntries_id());
			
			service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE);
			service.setDealerServiceEntriesId(serviceReminderFindSameWorkorder.getDealerServiceEntriesId());
			service.setServiceProgramId(serviceReminderFindSameWorkorder.getServiceProgramId());
			service.setServiceScheduleId(serviceReminderFindSameWorkorder.getServiceScheduleId());
			service.setServiceType(serviceReminderFindSameWorkorder.getServiceType());
			service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE);

			return service;

		}
	
		public ServiceEntriesDto getServEntInvoiceDetail(ServiceEntriesDto ServiceEntries) throws Exception {

			ServiceEntriesDto work = new ServiceEntriesDto();
			work.setInvoiceNumber(ServiceEntries.getInvoiceNumber());

			if (ServiceEntries.getInvoiceDateOn() != null) {
				work.setInvoiceDate(dateFormat.format(ServiceEntries.getInvoiceDateOn()));
			}
			
			if (ServiceEntries.getService_paiddateOn() != null) {
				work.setService_paiddate(dateFormat.format(ServiceEntries.getService_paiddateOn()));
			}

			return work;
		}
		
		//Issues Service Entries 
		public ServiceEntries prepareServiceEntriesInfo(ValueObject valueObject) throws Exception {
			ServiceEntries 				serviceEntries 			= null;
			CustomUserDetails 			userDetails 			= null;
			Date 						currnetDate				= null;
			Timestamp 					currentTimestamp		= null;
			
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				currnetDate 		= new Date();
				currentTimestamp 	= new java.sql.Timestamp(currnetDate.getTime());
				
				serviceEntries 		= new ServiceEntries();
				
				serviceEntries.setVid(valueObject.getInt("vid", 0));
				serviceEntries.setVehicle_Odometer(valueObject.getInt("vehicleOdometerId", 0));  
				
				if (valueObject.getString("invoiceDateId")!= null && !valueObject.getString("invoiceDateId").isEmpty()) {
					java.util.Date date = dateFormat.parse(valueObject.getString("invoiceDateId"));
					java.sql.Date start_date = new java.sql.Date(date.getTime());
					serviceEntries.setInvoiceDate(start_date);
				}
				
				if (valueObject.getString("paidDateId") != null && !valueObject.getString("paidDateId").isEmpty()) {
					java.util.Date date2 = dateFormat.parse(valueObject.getString("paidDateId"));
					java.sql.Date due_date = new java.sql.Date(date2.getTime());
					serviceEntries.setService_paiddate(due_date);
				}

				serviceEntries.setDriver_id(valueObject.getInt("driverId"));
				serviceEntries.setVendor_id(valueObject.getInt("vendorId"));
				serviceEntries.setInvoiceNumber(valueObject.getString("invoiceNumberId"));
				serviceEntries.setJobNumber(valueObject.getString("jobNumberId"));					
				serviceEntries.setService_paymentTypeId(valueObject.getShort("modeOfPaymentId"));   
				
				if (valueObject.getShort("modeOfPaymentId") == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {    
					serviceEntries.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
				} else {
					serviceEntries.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_PAID);
					serviceEntries.setService_vendor_paymentdate(currentTimestamp);
					serviceEntries.setService_vendor_approvalID((long) 0);
				}

				serviceEntries.setService_PayNumber(valueObject.getString("cashReceiptNoId"));				
				serviceEntries.setService_paidbyId(valueObject.getLong("service_paidbyId"));				 
				serviceEntries.setTotalservice_cost(0.0);
				serviceEntries.setTotalserviceROUND_cost(0.0);
				serviceEntries.setServiceEntries_statusId(ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN);
				serviceEntries.setService_document(false);
				serviceEntries.setService_document_id((long) 0);
				serviceEntries.setMarkForDelete(false);
				serviceEntries.setCreatedById(userDetails.getId());
				serviceEntries.setCreated(currentTimestamp);
				serviceEntries.setLastupdated(currentTimestamp);
				serviceEntries.setLastModifiedById(userDetails.getId());
				if(valueObject.getDouble("workshopInvoiceAmountId") > 0.0)				
					serviceEntries.setWorkshopInvoiceAmount(valueObject.getDouble("workshopInvoiceAmountId"));	
				serviceEntries.setISSUES_ID(valueObject.getLong("ISSUES_ID"));
				serviceEntries.setCompanyId(userDetails.getCompany_id());
				

				return serviceEntries;
			
			
			} catch (Exception e) {
				LOGGER.error("err"+e);
				throw e;
			}
			
			
		}


		public ServiceEntriesTasks prepareServiceEntriesTaskInfo(ValueObject object,ServiceEntries serviceEntries, Integer companyID)throws Exception {
			ServiceEntriesTasks serviceEntriesTask = null;
			try {
				serviceEntriesTask = new ServiceEntriesTasks();
				serviceEntriesTask.setTaskRemark(object.getString("remark"));
				serviceEntriesTask.setService_typetaskId(object.getInt("jobType"));
				serviceEntriesTask.setVid(serviceEntries.getVid());
				serviceEntriesTask.setServiceEntries(serviceEntries);//serviceEntries_id
				serviceEntriesTask.setTotalpart_cost(0.0);
				serviceEntriesTask.setTotallaber_cost(0.0);
				serviceEntriesTask.setTotaltask_cost(0.0);
				serviceEntriesTask.setCompanyId(companyID);
				
				return serviceEntriesTask;
				
			} catch (Exception e) {
				LOGGER.error("ERR"+e);
				throw e;
			}
		}


		public ServiceEntriesTasks prepareServiceEntriesTaskForReminder(ServiceReminderDto serviceReminderDto,
				ServiceEntries serviceEntries, CustomUserDetails userDetails, String serviceReminderId)throws Exception {
			try {
				ServiceEntriesTasks serviceEntriesTaskForReminder = new ServiceEntriesTasks();
				
				if(serviceReminderDto.getServiceTypeId() != null) {
					serviceEntriesTaskForReminder.setService_typetaskId(serviceReminderDto.getServiceTypeId());	
				}
				if(serviceReminderDto.getServiceSubTypeId() != null) {
					serviceEntriesTaskForReminder.setService_subtypetask_id(serviceReminderDto.getServiceSubTypeId());	
				}
				
				serviceEntriesTaskForReminder.setServiceEntries(serviceEntries);
				serviceEntriesTaskForReminder.setVid(serviceEntries.getVid());
				serviceEntriesTaskForReminder.setTotalpart_cost(0.0);
				serviceEntriesTaskForReminder.setTotallaber_cost(0.0);
				serviceEntriesTaskForReminder.setTotaltask_cost(0.0);
				serviceEntriesTaskForReminder.setCompanyId(userDetails.getCompany_id());
				serviceEntriesTaskForReminder.setService_id(Long.parseLong(serviceReminderId+""));
				
				return serviceEntriesTaskForReminder;
			} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			}
			
		}
		
		public ServiceEntries createServiceEntries(ValueObject valueObject) throws Exception {
			ServiceEntries 				serviceEntries 			= null;
			CustomUserDetails 			userDetails 			= null;
			Date 						currnetDate				= null;
			Timestamp 					currentTimestamp		= null;
			try {
				userDetails 		= (CustomUserDetails) valueObject.get("userDetails");
				currnetDate 		= new Date();
				currentTimestamp 	= new java.sql.Timestamp(currnetDate.getTime());
				serviceEntries 		= new ServiceEntries();
				
				serviceEntries.setVid(valueObject.getInt("vid", 0));
				serviceEntries.setDriver_id(valueObject.getInt("driverId",0));
				serviceEntries.setVehicle_Odometer(valueObject.getInt("vehicleOdometerId", 0));
				serviceEntries.setGpsOdometer(valueObject.getDouble("gpsOdometerId",0));
				serviceEntries.setVendor_id(valueObject.getInt("vendorId",0));
				serviceEntries.setInvoiceNumber(valueObject.getString("invoiceNumber",""));
				
				if (valueObject.getString("invoiceDate")!= null && !valueObject.getString("invoiceDate").isEmpty()) {
					java.util.Date date = dateFormat.parse(valueObject.getString("invoiceDate"));
					java.sql.Date start_date = new java.sql.Date(date.getTime());
					serviceEntries.setInvoiceDate(start_date);
				}
				
				serviceEntries.setTripSheetId(valueObject.getLong("tripSheetId",0));
				serviceEntries.setJobNumber(valueObject.getString("jobNumber"));
				serviceEntries.setService_paymentTypeId(valueObject.getShort("modeOfPaymentId"));
				
				if (valueObject.getShort("modeOfPaymentId") == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {    
					serviceEntries.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
				} else {
					serviceEntries.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_PAID);
					serviceEntries.setService_vendor_paymentdate(currentTimestamp);
					serviceEntries.setService_vendor_approvalID((long) 0);
				}
				
				if (valueObject.getString("paidDate") != null && !valueObject.getString("paidDate").isEmpty()) {
					java.util.Date date2 = dateFormat.parse(valueObject.getString("paidDate"));
					java.sql.Date due_date = new java.sql.Date(date2.getTime());
					serviceEntries.setService_paiddate(due_date);
				}

				serviceEntries.setService_PayNumber(valueObject.getString("payNumber"));				
				serviceEntries.setService_paidbyId(valueObject.getLong("service_paidbyId",0));				 
				serviceEntries.setTotalservice_cost(0.0);
				serviceEntries.setTotalserviceROUND_cost(0.0);
				serviceEntries.setServiceEntries_statusId(ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN);
				serviceEntries.setService_document(false);
				serviceEntries.setService_document_id((long) 0);
				serviceEntries.setISSUES_ID(valueObject.getLong("issueId",0));
				serviceEntries.setTallyCompanyId(valueObject.getLong("tallyCompanyId"));
				serviceEntries.setTallyExpenseId(valueObject.getInt("tallyExpenseId",0));
				
				if(valueObject.getDouble("workshopInvoiceAmountId") > 0.0)				
					serviceEntries.setWorkshopInvoiceAmount(valueObject.getDouble("workshopInvoiceAmountId"));	
				
				serviceEntries.setMarkForDelete(false);
				serviceEntries.setCreatedById(userDetails.getId());
				serviceEntries.setCreated(currentTimestamp);
				serviceEntries.setLastupdated(currentTimestamp);
				serviceEntries.setLastModifiedById(userDetails.getId());
				serviceEntries.setCompanyId(userDetails.getCompany_id());

				return serviceEntries;
			
			} catch (Exception e) {
				LOGGER.error("err"+e);
				throw e;
			}
			
		}
		
		public ServiceEntriesTasksToParts saveServiceEntryPartDetails(ValueObject object) throws Exception {
			ServiceEntriesTasksToParts workPart = new ServiceEntriesTasksToParts();
			try {
				workPart.setServiceEntries_id(object.getLong("serviceEntryId"));
				workPart.setServicetaskid(object.getLong("serviceEntrytaskId"));
				workPart.setPartid(object.getLong("partsId"));

				Double Quantity = 0.0;
				Double Parteachcost = 0.0;
				Double Partdisc = 0.0;
				Double Parttax = 0.0;
				Double partIGST	= 0.0;
				Double partCGST	= 0.0;
				Double partSGST	= 0.0;

				
				if (object.getString("partsQuantity") != null) {
					Quantity = (object.getDouble("partsQuantity"));
				}
				if (object.getString("partsEachCost") != null) {
					Parteachcost = (object.getDouble("partsEachCost"));
				}
				if (object.getString("partsDiscount") != null) {
					Partdisc = object.getDouble("partsDiscount");
				}
				if (object.getString("partsTax") != null) {
					Parttax = object.getDouble("partsTax");
				}
				if (object.getString("partIGST") != null) {
					partIGST = object.getDouble("partIGST");
				}
				if (object.getString("partCGST") != null) {
					partCGST = object.getDouble("partCGST");
				}
				if (object.getString("partSGST") != null) {
					partSGST = object.getDouble("partSGST");
				}
				
				
				Double discountCost = 0.0;
				Double TotalCost = 0.0;

				discountCost = (Quantity * Parteachcost) - ((Quantity * Parteachcost) * (Partdisc / 100));
				
				if(object.getBoolean("allowGSTbifurcation")) {
					TotalCost = round(((discountCost) + (discountCost * ( (partIGST+partCGST+partSGST) / 100))), 2);
					workPart.setParttax(partIGST+partCGST+partSGST);
				}else {
					TotalCost = round(((discountCost) + (discountCost * (Parttax / 100))), 2);
					workPart.setParttax(Parttax);
				}
				workPart.setPartIGST(partIGST);
				workPart.setPartCGST(partCGST);
				workPart.setPartSGST(partSGST);
				workPart.setQuantity(Quantity);
				workPart.setParteachcost(Parteachcost);
				workPart.setPartdisc(Partdisc);

				workPart.setTotalcost(TotalCost);

				return workPart;
				
			} catch (Exception e) {
				LOGGER.error("err"+e);
				throw e;
			}
			
		}
		
		public ServiceEntriesTasksToLabor prepareServiceEntriesTaskToLabor(ValueObject object) throws Exception  {
			try {
				ServiceEntriesTasksToLabor workPart = new ServiceEntriesTasksToLabor();

				workPart.setServiceEntries_id(object.getLong("serviceEntryId"));
				workPart.setServicetaskid(object.getLong("serviceEntrytaskId"));
				workPart.setLabername(object.getString("labername"));

				Double Laberhourscost = 0.0;
				Double Eachlabercost = 0.0;
				Double laberdisc = 0.0;
				Double labertax = 0.0;
				Double labourIGST = 0.0;
				Double labourCGST = 0.0;
				Double labourSGST = 0.0;

				if (object.getString("laberhourscost") != null) {
					Laberhourscost = (object.getDouble("laberhourscost"));
				}
				if (object.getString("eachlabercost") != null) {
					Eachlabercost = (object.getDouble("eachlabercost"));
				}
				if (object.getString("laberdiscount") != null) {
					laberdisc = object.getDouble("laberdiscount");
				}
				if (object.getString("labertax") != null) {
					labertax = object.getDouble("labertax");
				}
				if (object.getString("labourIGST") != null) {
					labourIGST = object.getDouble("labourIGST");
				}
				if (object.getString("labourCGST") != null) {
					labourCGST = object.getDouble("labourCGST");
				}
				if (object.getString("labourSGST") != null) {
					labourSGST = object.getDouble("labourSGST");
				}

				Double discountCost = 0.0;
				Double TotalCost = 0.0;

				discountCost = (Laberhourscost * Eachlabercost) - ((Laberhourscost * Eachlabercost) * (laberdisc / 100));
				
				if(object.getBoolean("allowGSTbifurcation")) {
					TotalCost = round(((discountCost) + (discountCost * ( (labourIGST+labourCGST+labourSGST) / 100))), 2);
					workPart.setLabertax(labourIGST+labourCGST+labourSGST);
				}else {
					TotalCost = round(((discountCost) + (discountCost * (labertax / 100))), 2);
					workPart.setLabertax(labertax);
				}
				
				workPart.setLabourIGST(labourIGST);
				workPart.setLabourCGST(labourCGST);
				workPart.setLabourSGST(labourSGST);
				
				workPart.setLaberhourscost(Laberhourscost);
				workPart.setEachlabercost(Eachlabercost);
				workPart.setLaberdiscount(laberdisc);

				workPart.setTotalcost(TotalCost);

				return workPart;
				
			} catch (Exception e) {
				LOGGER.error("err"+e);
				throw e;
			}

		}
		
		public ServiceEntries updateServiceEntry(ValueObject object) throws Exception {
			CustomUserDetails 		userDetails 			= null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				ServiceEntries work = new ServiceEntries();
				
				work.setServiceEntries_id(object.getLong("serviceEntryId"));
				work.setServiceEntries_Number(object.getLong("serviceNumber"));
				work.setVid(object.getInt("vid"));
				work.setVehicle_Odometer(object.getInt("vehicleOdometerId"));

				if (object.getString("invoiceDate") != null && !object.getString("invoiceDate").trim().equals("")) {
					java.util.Date date = dateFormat.parse(object.getString("invoiceDate"));
					java.sql.Date start_date = new java.sql.Date(date.getTime());
					work.setInvoiceDate(start_date);
				}
				
				if (object.getString("paidDate") != null && !object.getString("paidDate").trim().equals("")) {
					java.util.Date date2 = dateFormat.parse(object.getString("paidDate"));
					java.sql.Date due_date = new java.sql.Date(date2.getTime());
					work.setService_paiddate(due_date);
				}
				
				if (object.getInt("driverId") != 0) {
					work.setDriver_id(object.getInt("driverId"));
				} else {
					work.setDriver_id(0);
				}
				
				if (object.getInt("vendorId") != 0) {
					work.setVendor_id(object.getInt("vendorId"));
				} else {
					work.setVendor_id(0);
				}
				
				if(object.getString("invoiceNumber") != null)
				work.setInvoiceNumber(object.getString("invoiceNumber"));
				
				work.setJobNumber(object.getString("jobNumber"));

				// payment Type is credit set vendor pay mode is NOTPAID
				work.setService_paymentTypeId(object.getShort("modeOfPaymentId"));
				if (object.getShort("modeOfPaymentId") == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					work.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
				} else {
					work.setService_vendor_paymodeId(ServiceEntriesType.PAYMENT_MODE_PAID);
					Date currentDateUpdate = new Date();
					Timestamp toDatePayment = new java.sql.Timestamp(currentDateUpdate.getTime());
					work.setService_vendor_paymentdate(toDatePayment);
				}

				work.setService_PayNumber(object.getString("service_PayNumber"));
				work.setService_paidbyId(object.getLong("service_paidbyId"));
				work.setGpsOdometer(object.getDouble("gpsOdometerId",0));
				work.setTripSheetId(object.getLong("tripSheetId",0));
				work.setAccidentId(object.getLong("accidentId",0));
				
				if(object.getString("workshopInvoiceAmountId") != null) {
					work.setWorkshopInvoiceAmount(object.getDouble("workshopInvoiceAmountId"));
				}else {
					work.setWorkshopInvoiceAmount(0.0);
				}
				
				if(object.getString("tallyCompanyId") != null) {
					work.setTallyCompanyId(object.getLong("tallyCompanyId"));
				}
				work.setTallyExpenseId(object.getInt("tallyExpenseId",0));
				Date currentDateUpdate = new Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				work.setCreated(toDate);
				work.setLastupdated(toDate);
				work.setCompanyId(userDetails.getCompany_id());
				work.setCreatedById(userDetails.getId());
				work.setLastModifiedById(userDetails.getId());
				work.setMarkForDelete(false);

				return work;
				
			} catch (Exception e) {
				LOGGER.error("err"+e);
				throw e;
			}
			
		}
		
		public static boolean getInvoiceDateToValidate(ServiceEntriesDto	serviceEntries, HashMap<String, Object>    config	) {
			boolean validate  = false;
			if(serviceEntries.getService_paymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				if((serviceEntries.getInvoiceNumber().isEmpty()) || (serviceEntries.getService_paiddateOn() == null && (boolean)config.get("showPaidDateForServEnt")) || (serviceEntries.getInvoiceDateOn() == null)) {
					validate = true;
				}
			}
			return validate;
			
		}
		
		public static ServiceEntriesRemark ServiceEntryRemarkDto(ServiceEntriesDto SERemarkDto, ValueObject valueObject) throws Exception
		{
			ServiceEntriesRemark  SERemark  = new ServiceEntriesRemark();
			SERemark.setServiceEntryId(SERemarkDto.getServiceEntries_id());
			SERemark.setCompanyId(valueObject.getInt("companyId",0));
			SERemark.setCreatedById(valueObject.getLong("userId",0));
			SERemark.setDriverId(valueObject.getInt("driverId",0));
			SERemark.setRemark(valueObject.getString("SEremark"));
			SERemark.setMarkForDelete(false);
			SERemark.setCreatedOn(new Date());
			return SERemark;
		}
		
}
