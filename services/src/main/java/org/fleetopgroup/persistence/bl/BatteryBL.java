package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.List;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.BatteryTransferDto;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.BatteryTransfer;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

public class BatteryBL {
	
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	public BatteryInvoice	getBatteryInvoice(ValueObject	valueObject, MultipartFile file) throws Exception {
		BatteryInvoice		batteryInvoice		= null;
		CustomUserDetails	userDetails			= null;
		double				balanceAmount		= 0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryInvoice	= new BatteryInvoice();
			
			batteryInvoice.setWareHouseLocation(valueObject.getInt("warehouselocation"));
			batteryInvoice.setPaymentTypeId(valueObject.getShort("paymentType"));
			batteryInvoice.setPaymentNumber(valueObject.getString("paymentNumber"));
			batteryInvoice.setPoNumber(valueObject.getString("poNumber"));
			batteryInvoice.setInvoiceNumber(valueObject.getString("invoiceNumber"));
			batteryInvoice.setInvoiceDate(DateTimeUtility.getDateTimeFromStr(valueObject.getString("invoiceDate"), DateTimeUtility.DD_MM_YYYY));
			batteryInvoice.setInvoiceAmount(valueObject.getDouble("invoiceAmount", 0));
			
			balanceAmount=valueObject.getDouble("invoiceAmount", 0);
			batteryInvoice.setBalanceAmount(balanceAmount);
			batteryInvoice.setDescription(valueObject.getString("description"));
			batteryInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			batteryInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
			batteryInvoice.setCreatedById(userDetails.getId());
			batteryInvoice.setLastModifiedById(userDetails.getId());
			batteryInvoice.setVendorId(valueObject.getInt("vendor", 0));
			batteryInvoice.setTotalBatteryAmount(valueObject.getDouble("totalBatteryAmount", 0.0));
			batteryInvoice.setCompanyId(userDetails.getCompany_id());
			batteryInvoice.setTallyCompanyId(valueObject.getLong("tallyCompanyId",0));
			if (batteryInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				batteryInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
			} else {
				batteryInvoice.setVendorPaymentStatus(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
				try {
					batteryInvoice.setVendorPaymentDate(DateTimeUtility.getCurrentTimeStamp());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(file != null) {
				if (!file.isEmpty()) {
					batteryInvoice.setBattery_document(true);
				} else {
					batteryInvoice.setBattery_document(false);
				}
			} else {
				if (valueObject.getString("base64String",null)  != null) {
					batteryInvoice.setBattery_document(true);
				} else {
					batteryInvoice.setBattery_document(false);
				}
			}
			batteryInvoice.setSubLocationId(valueObject.getInt("subLocationId",0));
			return batteryInvoice;

		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public BatteryAmount getBatteryAmountDetails(ValueObject object, ValueObject inObject) throws Exception{
		BatteryAmount					batteryAmount				= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	= new BatteryAmount();
			batteryAmount.setBatteryManufacturerId(object.getLong("batteryManufacturer", 0));
			batteryAmount.setBatteryTypeId(object.getLong("batteryType", 0));
			batteryAmount.setBatteryQuantity(object.getLong("quantity", 0));
			batteryAmount.setUnitCost(object.getDouble("unitprice", 0));
			batteryAmount.setDiscount(object.getDouble("discount", 0));
			batteryAmount.setTax(object.getDouble("tax", 0));
			batteryAmount.setTotalAmount(object.getDouble("tatalcost", 0));
			batteryAmount.setWareHouseLocation(inObject.getInt("warehouselocation", 0));
			batteryAmount.setBatteryInvoiceId(inObject.getLong("invoiceId", 0));
			batteryAmount.setMarkForDelete(false);
			batteryAmount.setCompanyId(userDetails.getCompany_id());
			batteryAmount.setBatteryAsignNumber(object.getInt("quantity", 0));
			batteryAmount.setBatteryCapacityId(object.getLong("batteryCapacity", 0));
			batteryAmount.setDiscountTaxTypeId(object.getShort("disTaxTypeId",(short)1));
			
			return batteryAmount;
			
		} catch (Exception e) {
			throw e;
		}finally {
			batteryAmount		= null;
			userDetails			= null;
		}
	}

	public BatteryInvoiceDto getfinalBatteryInvoice(BatteryInvoiceDto	batteryInvoiceDto) throws Exception{
		try {
			if(batteryInvoiceDto != null) {
				batteryInvoiceDto.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(batteryInvoiceDto.getVendorPaymentStatus()));
				batteryInvoiceDto.setPaymentStatus(PaymentTypeConstant.getPaymentTypeName(batteryInvoiceDto.getPaymentTypeId()));
				batteryInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
				if(batteryInvoiceDto.getVendorPaymentDate() != null)
				 batteryInvoiceDto.setVendorPaymentDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getVendorPaymentDate(), DateTimeUtility.DD_MM_YYYY));
				batteryInvoiceDto.setCreated(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getCreatedOn(), DateTimeUtility.DD_MM_YYYY));
				batteryInvoiceDto.setLastModified(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getLastModifiedBy(), DateTimeUtility.DD_MM_YYYY));
				batteryInvoiceDto.setBattery_document(batteryInvoiceDto.isBattery_document());
				batteryInvoiceDto.setBattery_document_id(batteryInvoiceDto.getBattery_document_id());
				batteryInvoiceDto.setBatteryLocation(batteryInvoiceDto.getBatteryLocation());
				batteryInvoiceDto.setSubLocationId(batteryInvoiceDto.getSubLocationId());
				batteryInvoiceDto.setSubLocation(batteryInvoiceDto.getSubLocation());
				batteryInvoiceDto.setPurchaseOrderId(batteryInvoiceDto.getPurchaseOrderId());
			}
			
			return batteryInvoiceDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Battery	getBatteryDto(ValueObject	inObject, ValueObject object, BatteryAmount	amount) throws Exception{
		Battery				battery			= null;
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			battery	= new Battery();
			
			Double batteryAmount = 0.0;
			
			battery.setBatterySerialNumber(object.getString("batterySerialNumber"));
			battery.setCompanyId(amount.getCompanyId());
			battery.setBatteryAmountId(amount.getBatteryAmountId());
			battery.setBatteryInvoiceId(amount.getBatteryInvoiceId());
			battery.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE);
			battery.setBatteryUsesStatusId(BatteryConstant.BATTERY_USES_STATUS_NEW);
			
			if (amount.getBatteryQuantity() != null && amount.getBatteryQuantity() != 0.0) {
				batteryAmount = amount.getTotalAmount() / amount.getBatteryQuantity();
			}
			
			battery.setBatteryAmount(batteryAmount);
			battery.setBatteryManufacturerId(amount.getBatteryManufacturerId());
			battery.setBatteryTypeId(amount.getBatteryTypeId());
			battery.setWareHouseLocationId(amount.getWareHouseLocation());
			battery.setBatteryUsesOdometer(0);
			battery.setBatteryPurchaseDate(DateTimeUtility.getCurrentTimeStamp());
			battery.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			battery.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			battery.setCreatedById(userDetails.getId());
			battery.setLastModifiedById(userDetails.getId());
			battery.setUsesNoOfTime((long) 0);
			battery.setBatteryCapacityId(amount.getBatteryCapacityId());
			battery.setSubLocationId(inObject.getInt("subLocationId",0));
			return battery;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public BatteryDto	getFinalBatteryDto(BatteryDto	batteryDto)throws Exception{
		try {
			if(batteryDto != null) {
				batteryDto.setPurchaseDate(DateTimeUtility.getDateFromTimeStamp(batteryDto.getBatteryPurchaseDate(), DateTimeUtility.DD_MM_YYYY));
				if(batteryDto.getBatteryAsignedDate() != null)
					batteryDto.setAsignedDate(DateTimeUtility.getDateFromTimeStamp(batteryDto.getBatteryAsignedDate(), DateTimeUtility.DD_MM_YYYY));
				if(batteryDto.getBatteryScrapedDate() != null)
					batteryDto.setScrapedDate(DateTimeUtility.getDateFromTimeStamp(batteryDto.getBatteryScrapedDate(), DateTimeUtility.DD_MM_YYYY));
				if(batteryDto.getCreatedOn() != null)
					batteryDto.setCreationDate(DateTimeUtility.getDateFromTimeStamp(batteryDto.getCreatedOn(), DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
				if(batteryDto.getLastModifiedOn() != null)
					batteryDto.setLastModifiedDate(DateTimeUtility.getDateFromTimeStamp(batteryDto.getLastModifiedOn(), DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
				if(batteryDto.getBatteryFirstAsignedDate() != null) {
					if(batteryDto.getWarrantyTypeId() == BatteryTypeDto.WARRANTY_TYPE_MONTH) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(batteryDto.getBatteryFirstAsignedDate());
						cal.add(Calendar.MONTH, batteryDto.getWarrantyPeriod());
						
						Period period = Period.between(DateTimeUtility.getCurrentTimeStamp().toLocalDateTime().toLocalDate(), new Timestamp(cal.getTime().getTime()).toLocalDateTime().toLocalDate());
						
						//noOfDays	=  DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getCurrentTimeStamp(), new Timestamp(cal.getTime().getTime()));
						if(period.getDays() == 0) {
							batteryDto.setAvailableWarrantyType("till today");
							batteryDto.setWarrantyStatus("Under warranty");
						}else if(period.getDays() < 0) {
							batteryDto.setAvailableWarrantyType("Warranty Expired !");
							batteryDto.setWarrantyStatus("Out of warranty");
						}else {
							batteryDto.setWarrantyStatus("Under warranty");
							batteryDto.setAvailableWarrantyType(period.getYears()+" Years , "+period.getMonths()+" Month , "+period.getDays()+" Days from now");
						}
						batteryDto.setWarrantyPeriodStr(DateTimeUtility.getDateFromTimeStamp(batteryDto.getBatteryFirstAsignedDate())+" from "+DateTimeUtility.getDateFromTimeStamp(new Timestamp(cal.getTime().getTime())));
						batteryDto.setCostPerDay(Double.parseDouble(toFixedTwo.format(batteryDto.getBatteryAmount()/batteryDto.getUsesNoOfTime())));
						if(batteryDto.getBatteryUsesOdometer() > 0)
							batteryDto.setCostPerOdometer(Double.parseDouble(toFixedTwo.format(batteryDto.getBatteryAmount()/batteryDto.getBatteryUsesOdometer())));
					}else if(batteryDto.getWarrantyTypeId() == BatteryTypeDto.WARRANTY_TYPE_YEAR) {

						Calendar cal = Calendar.getInstance();
						cal.setTime(batteryDto.getBatteryFirstAsignedDate());
						cal.add(Calendar.YEAR, batteryDto.getWarrantyPeriod());
						
						Period period = Period.between(DateTimeUtility.getCurrentTimeStamp().toLocalDateTime().toLocalDate(), new Timestamp(cal.getTime().getTime()).toLocalDateTime().toLocalDate());
						
						//noOfDays	=  DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getCurrentTimeStamp(), new Timestamp(cal.getTime().getTime()));
						if(period.getDays() == 0) {
							batteryDto.setAvailableWarrantyType("till today");
							batteryDto.setWarrantyStatus("Under warranty");
						}else if(period.getDays() < 0) {
							batteryDto.setAvailableWarrantyType("Warranty Expired !");
							batteryDto.setWarrantyStatus("Out of warranty");
						}else {
							batteryDto.setWarrantyStatus("Under warranty");
							batteryDto.setAvailableWarrantyType(period.getYears()+" Years , "+period.getMonths()+" Month , "+period.getDays()+" Days from now");
						}
						batteryDto.setWarrantyPeriodStr(DateTimeUtility.getDateFromTimeStamp(batteryDto.getBatteryFirstAsignedDate())+" from "+DateTimeUtility.getDateFromTimeStamp(new Timestamp(cal.getTime().getTime())));
						batteryDto.setCostPerDay(batteryDto.getBatteryAmount()/batteryDto.getUsesNoOfTime());
						if(batteryDto.getBatteryUsesOdometer() > 0)
							batteryDto.setCostPerOdometer(batteryDto.getBatteryAmount()/batteryDto.getBatteryUsesOdometer());
					
					}
				}
				
				batteryDto.setSubLocationId(batteryDto.getSubLocationId());
				batteryDto.setSubLocation(batteryDto.getSubLocation());
			}
			return batteryDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<BatteryDto>  getListToScrap(List<BatteryDto> list) throws Exception{
		try {
			
			if(list != null && !list.isEmpty()) {
				Timestamp	tillDate 	= null;
				Timestamp	currentDate	= DateTimeUtility.getCurrentTimeStamp();
				for(BatteryDto 	batteryDto : list) {
					if(batteryDto.getWarrantyTypeId() == BatteryTypeDto.WARRANTY_TYPE_MONTH) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(batteryDto.getBatteryFirstAsignedDate());
						cal.add(Calendar.MONTH, batteryDto.getWarrantyPeriod());
						tillDate = new Timestamp(cal.getTime().getTime());
						
					}else if(batteryDto.getWarrantyTypeId() == BatteryTypeDto.WARRANTY_TYPE_YEAR) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(batteryDto.getBatteryFirstAsignedDate());
						cal.add(Calendar.YEAR, batteryDto.getWarrantyPeriod());
						tillDate = new Timestamp(cal.getTime().getTime());
					}
					if(tillDate != null && batteryDto.getBatteryFirstAsignedDate() != null) {
						if(tillDate.after(currentDate)) {
						}else {
						}
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<BatteryInvoiceDto> prepareInventoryBatteryInvoiceList(List<BatteryInvoiceDto> batteryInvoiceList) throws Exception {
		
		try {
			if(batteryInvoiceList != null) {
				for(BatteryInvoiceDto batteryInvoice : batteryInvoiceList) {
					batteryInvoice.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(batteryInvoice.getVendorPaymentStatus()));
					batteryInvoice.setPaymentStatus(PaymentTypeConstant.getPaymentTypeName(batteryInvoice.getPaymentTypeId()));
					batteryInvoice.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoice.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					batteryInvoice.setVendorId(batteryInvoice.getVendorId());
					if(batteryInvoice.getVendorName() == null)
						batteryInvoice.setVendorName("--");
					
					if(batteryInvoice.getVendorPaymentDate() != null) 
						batteryInvoice.setVendorPaymentDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoice.getVendorPaymentDate(), DateTimeUtility.DD_MM_YYYY));					
				}
			}
			
			return batteryInvoiceList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<BatteryDto>  getBatteryListDto(List<BatteryDto> list) throws Exception{
		try {
			if(list != null) {
				for(BatteryDto  batteryDto : list) {
					if(batteryDto.getUsesNoOfTime() != null && batteryDto.getBatteryAsignedDate() != null) {
						if(batteryDto.getBatteryStatusId() == BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE) {
							batteryDto.setUsesNoOfTime(batteryDto.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryDto.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
						}
					}else if(batteryDto.getUsesNoOfTime() == null && batteryDto.getBatteryAsignedDate() != null) {
						batteryDto.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryDto.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}
					
					if(batteryDto.getBatteryStatusId() != BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE) {
						batteryDto.setVehicle_registration("--");
					}
					batteryDto.setWareHouseLocationId(batteryDto.getWareHouseLocationId());
					batteryDto.setSubLocationId(batteryDto.getSubLocationId());
					batteryDto.setSubLocation(batteryDto.getSubLocation());
					
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static BatteryTransfer prepareBatteryTransfer(Battery battery,int fromLocation,int toLocation,
			CustomUserDetails userDetails) {

		BatteryTransfer transfer = new BatteryTransfer();

		transfer.setBatteryId(battery.getBatteryId());

		transfer.setFromLocationId(fromLocation);
		transfer.setToLocationId(toLocation);
		transfer.setTransferQuantity(1);
		//transfer.setTransferViaId(batteryTransferDto.getTransferViaId());

		transfer.setTransferDate(DateTimeUtility.getCurrentTimeStamp());
		//transfer.setTransferReason(batteryTransferDto.getTransferReason());
		transfer.setBatteryInvoiceId(battery.getBatteryInvoiceId());
		transfer.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		transfer.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
		transfer.setCreatedById(userDetails.getId());
		transfer.setLastModifiedById(userDetails.getId());
		transfer.setCompanyId(userDetails.getCompany_id());
		transfer.setTransferById(userDetails.getId());
		//transfer.setReceiveById(batteryTransferDto.getReceiveById());
		transfer.setTransferStausId(InventoryTyreTransferDto.TRANSFER_STATUS_TRANSFER);


		return transfer;
	}
	
	
}
