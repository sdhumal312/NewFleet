/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.fleetopgroup.constant.DealerServiceEntriesConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.fleetopgroup.persistence.model.DealerServiceEntriesLabour;
import org.fleetopgroup.persistence.model.DealerServiceEntriesPart;
import org.fleetopgroup.persistence.model.DealerServiceExtraIssue;
import org.fleetopgroup.persistence.model.DealerServiceRemark;
import org.fleetopgroup.persistence.model.DealerServiceReminderHistory;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fleetop
 *
 */
public class DealerServiceEntriesBL {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	 DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	public DealerServiceEntriesBL() {
		super();
	}

	public DealerServiceEntries prepareDealerServiceEntries(ValueObject valueObject) throws Exception {
		DealerServiceEntries dealerServiceEntries = null;
		try {
			dealerServiceEntries = new DealerServiceEntries();
			dealerServiceEntries.setDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"));
			dealerServiceEntries.setVid(valueObject.getInt("vid"));
			dealerServiceEntries.setVehicleOdometer(valueObject.getInt("vehicleOdometer", 0));
			dealerServiceEntries.setGpsOdometer(valueObject.getDouble("gpsOdometer", 0));
			dealerServiceEntries.setInvoiceNumber(valueObject.getString("invoiceNumber"));
			dealerServiceEntries.setJobNumber(valueObject.getString("jobNumber"));
			if (valueObject.getString("invoiceDate", null) != null) {
				java.util.Date date = dateFormat.parse(valueObject.getString("invoiceDate"));
				java.sql.Date invoiceDate = new java.sql.Date(date.getTime());
				dealerServiceEntries.setInvoiceDate(invoiceDate);
			}
			dealerServiceEntries.setPaymentTypeId(valueObject.getShort("paymentTypeId"));
			dealerServiceEntries.setPayNumber(valueObject.getString("transactionNumber"));
			dealerServiceEntries.setPaidById(valueObject.getLong("paidById"));
			dealerServiceEntries.setTotalInvoiceCost(valueObject.getDouble("totalDSE_Cost", 0));
			dealerServiceEntries.setTotalServiceCost(valueObject.getDouble("totalDSE_Cost", 0));
			dealerServiceEntries.setTotalServiceRoundCost(valueObject.getDouble("totalServiceRoundCost", 0));
			dealerServiceEntries.setStatusId(valueObject.getShort("statusId", (short) 0));

			if (valueObject.getString("completedDate", null) != null) {
				java.util.Date date = dateFormat.parse(valueObject.getString("completedDate"));
				java.sql.Date completedDate = new java.sql.Date(date.getTime());
				dealerServiceEntries.setCompletedDate(completedDate);
			}
			if (valueObject.getString("expectedPaymentDate", null) != null) {
				java.util.Date date = dateFormat.parse(valueObject.getString("expectedPaymentDate"));
				java.sql.Date expectedPaymentDate = new java.sql.Date(date.getTime());
				dealerServiceEntries.setExpectedPaymentDate(expectedPaymentDate);
			}
			dealerServiceEntries.setVendorId(valueObject.getInt("vendorId", 0));

			if (dealerServiceEntries.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				dealerServiceEntries.setVendorPaymentStatusId(ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
			} else {
				dealerServiceEntries.setVendorPaymentStatusId(ServiceEntriesType.PAYMENT_MODE_PAID);
				dealerServiceEntries.setPaidDate(DateTimeUtility.getCurrentTimeStamp());
			}
			dealerServiceEntries.setVendorApprovalId(valueObject.getLong("vendorApprovalId", 0));
			dealerServiceEntries.setPaidAmount(valueObject.getDouble("paidAmount", 0));
			dealerServiceEntries.setBalanceAmount(valueObject.getDouble("totalDSE_Cost", 0));
			dealerServiceEntries.setTaxableAmount(valueObject.getDouble("taxableAmount", 0));
			dealerServiceEntries.setDiscountAmount(valueObject.getDouble("discountAmount", 0));
			dealerServiceEntries.setCreatedById(valueObject.getLong("userId"));
			dealerServiceEntries.setLastModifiedById(valueObject.getLong("userId"));
			dealerServiceEntries.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceEntries.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceEntries.setCompanyId(valueObject.getInt("companyId"));
			dealerServiceEntries.setMarkForDelete(false);
			dealerServiceEntries.setServiceReminderIds(valueObject.getString("serviceReminderId",""));
		//	dealerServiceEntries.setIssueId(Long.parseLong(valueObject.getString("issueId","")));
			dealerServiceEntries.setIssueIds(valueObject.getString("issueId",""));
			dealerServiceEntries.setPartApplicable(valueObject.getBoolean("isPartApplicable",true));
			dealerServiceEntries.setDriverId(valueObject.getInt("driverId",0));
			dealerServiceEntries.setAssignToId(valueObject.getLong("assignToId",0));
			dealerServiceEntries.setAccidentId(valueObject.getLong("accidentId", 0));
			dealerServiceEntries.setMeterNotWorkingId(valueObject.getBoolean("meterNotWorkingId",false));
			
			return dealerServiceEntries;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

		}
	}

	public DealerServiceEntriesLabour prepareDealerServiceEntriesLabour(ValueObject valueObject, ValueObject valueObjectNew) throws Exception {
		DealerServiceEntriesLabour dealerServiceEntriesLabour 	= null;
		double 						totalLabourCost 			= 0; 
		try {
			dealerServiceEntriesLabour = new DealerServiceEntriesLabour();
			dealerServiceEntriesLabour.setDealerServiceEntriesLabourId(valueObject.getLong("dealerServiceEntriesLabourId"));
			dealerServiceEntriesLabour.setDealerServiceEntriesId(valueObjectNew.getLong("dealerServiceEntriesId"));
			dealerServiceEntriesLabour.setLabourId(valueObject.getInt("labourId",0));
			dealerServiceEntriesLabour.setLabourName(valueObject.getString("labourName",""));
			dealerServiceEntriesLabour.setLabour_HSN_SAC_Code(valueObject.getString("labour_HSN_SAC_Code"));
			dealerServiceEntriesLabour.setLabourWorkingHours(valueObject.getDouble("labourWorkingHours", 0));
			dealerServiceEntriesLabour.setLabourPerHourCost(valueObject.getDouble("labourPerHourCost", 0));
			dealerServiceEntriesLabour.setLabourDiscount(valueObject.getDouble("labourDiscount", 0));
			dealerServiceEntriesLabour.setLabourTax(valueObject.getDouble("labourTax", 0));
			if(valueObjectNew.getShort("labourDiscountTaxTypeId") == DealerServiceEntriesConstant.AMOUNT_TYPE_ID) {
				totalLabourCost =  Utility.calculateTotalCostByGstDisAmount(dealerServiceEntriesLabour.getLabourWorkingHours(), dealerServiceEntriesLabour.getLabourPerHourCost(), dealerServiceEntriesLabour.getLabourTax(), dealerServiceEntriesLabour.getLabourDiscount());
			}else {
				totalLabourCost =  Utility.calculateTotalCost(dealerServiceEntriesLabour.getLabourWorkingHours(), dealerServiceEntriesLabour.getLabourPerHourCost(), dealerServiceEntriesLabour.getLabourTax(), dealerServiceEntriesLabour.getLabourDiscount());
			}
			dealerServiceEntriesLabour.setTotalCost(totalLabourCost);
			dealerServiceEntriesLabour.setDescription(valueObject.getString("description"));
			dealerServiceEntriesLabour.setCreatedById(valueObjectNew.getLong("userId"));
			dealerServiceEntriesLabour.setLastModifiedById(valueObjectNew.getLong("userId"));
			dealerServiceEntriesLabour.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceEntriesLabour.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceEntriesLabour.setCompanyId(valueObjectNew.getInt("companyId"));
			dealerServiceEntriesLabour.setLabourDiscountTaxTypeId(valueObjectNew.getShort("labourDiscountTaxTypeId"));
			if(valueObjectNew.getShort("labourDiscountTaxTypeId") == DealerServiceEntriesConstant.AMOUNT_TYPE_ID) {
				dealerServiceEntriesLabour.setLabourDiscountCost(dealerServiceEntriesLabour.getLabourDiscount());
				dealerServiceEntriesLabour.setLabourTaxCost(dealerServiceEntriesLabour.getLabourTax());
			}else if(valueObjectNew.getShort("labourDiscountTaxTypeId") == DealerServiceEntriesConstant.PERCENTAGE_TYPE_ID) {
				dealerServiceEntriesLabour.setLabourDiscountCost(Double.parseDouble(toFixedTwo.format(((dealerServiceEntriesLabour.getLabourWorkingHours()
						* dealerServiceEntriesLabour.getLabourPerHourCost())
						* dealerServiceEntriesLabour.getLabourDiscount()) / 100)));
				dealerServiceEntriesLabour.setLabourTaxCost(Double.parseDouble(toFixedTwo.format(((((dealerServiceEntriesLabour.getLabourWorkingHours()
						* dealerServiceEntriesLabour.getLabourPerHourCost())
						- dealerServiceEntriesLabour.getLabourDiscountCost()) * dealerServiceEntriesLabour.getLabourTax())
						/ 100))));
			}
			dealerServiceEntriesLabour.setMarkForDelete(false);
			return dealerServiceEntriesLabour;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {

		}
	}

	public DealerServiceEntriesPart prepareDealerServiceEntriesPart(ValueObject valueObject, ValueObject valueObjectNew)
			throws Exception {
		DealerServiceEntriesPart 	dealerServiceEntriesPart 	= null;
		double 						totalPartCost 				= 0;
		try {
			dealerServiceEntriesPart = new DealerServiceEntriesPart();
			dealerServiceEntriesPart.setDealerServiceEntriesPartId(valueObject.getLong("dealerServiceEntriesPartId"));
			dealerServiceEntriesPart.setDealerServiceEntriesId(valueObjectNew.getLong("dealerServiceEntriesId"));
			dealerServiceEntriesPart.setPartId(valueObject.getLong("partId", 0));
			dealerServiceEntriesPart.setPartName(valueObject.getString("partName", ""));
			dealerServiceEntriesPart.setPart_HSN_SAC_Code(valueObject.getString("part_HSN_SAC_Code"));
			dealerServiceEntriesPart.setQuantity(valueObject.getDouble("partQty", 0));
			dealerServiceEntriesPart.setPartEchCost(valueObject.getDouble("partCost", 0));
			dealerServiceEntriesPart.setPartDiscount(valueObject.getDouble("partDiscount", 0));
			dealerServiceEntriesPart.setPartTax(valueObject.getDouble("partTax", 0));
			if(valueObjectNew.getShort("partDiscountTaxTypeId") == DealerServiceEntriesConstant.AMOUNT_TYPE_ID) {
				totalPartCost =  Utility.calculateTotalCostByGstDisAmount(dealerServiceEntriesPart.getQuantity(), dealerServiceEntriesPart.getPartEchCost(), dealerServiceEntriesPart.getPartTax(), dealerServiceEntriesPart.getPartDiscount());
			}else {
				totalPartCost =  Utility.calculateTotalCost(dealerServiceEntriesPart.getQuantity(), dealerServiceEntriesPart.getPartEchCost(), dealerServiceEntriesPart.getPartTax(), dealerServiceEntriesPart.getPartDiscount());
			}
			dealerServiceEntriesPart.setPartTotalCost(totalPartCost);
			dealerServiceEntriesPart.setCreatedById(valueObjectNew.getLong("userId"));
			dealerServiceEntriesPart.setLastModifiedById(valueObjectNew.getLong("userId"));
			dealerServiceEntriesPart.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceEntriesPart.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceEntriesPart.setCompanyId(valueObjectNew.getInt("companyId"));
			dealerServiceEntriesPart.setPartDiscountTaxTypeId(valueObjectNew.getShort("partDiscountTaxTypeId"));
			if(valueObjectNew.getShort("partDiscountTaxTypeId") == DealerServiceEntriesConstant.AMOUNT_TYPE_ID) {
				dealerServiceEntriesPart.setPartDiscountCost(dealerServiceEntriesPart.getPartDiscount());
				dealerServiceEntriesPart.setPartTaxCost(dealerServiceEntriesPart.getPartTax());
			}else if(valueObjectNew.getShort("partDiscountTaxTypeId") == DealerServiceEntriesConstant.PERCENTAGE_TYPE_ID) {
				dealerServiceEntriesPart.setPartDiscountCost(
						((dealerServiceEntriesPart.getQuantity() * dealerServiceEntriesPart.getPartEchCost())
								* dealerServiceEntriesPart.getPartDiscount()) / 100);
				dealerServiceEntriesPart.setPartTaxCost(
						((((dealerServiceEntriesPart.getQuantity() * dealerServiceEntriesPart.getPartEchCost())
								- dealerServiceEntriesPart.getPartDiscountCost()) * dealerServiceEntriesPart.getPartTax())
								/ 100));
				
			}
			dealerServiceEntriesPart.setLastOccurredDealerServiceEntriesId(valueObject.getLong("lastOccurredDealerServiceEntriesId",0));
			dealerServiceEntriesPart.setMarkForDelete(false);
			return dealerServiceEntriesPart;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {

		}
	}

	public ServiceReminder dealerServiceToServiceReminder(ServiceReminder serviceReminder, ValueObject valueObject) throws Exception {
		try {
			ServiceReminder service = new ServiceReminder();
			
			service.setService_id(serviceReminder.getService_id());
			service.setService_Number(serviceReminder.getService_Number());
			service.setServiceTypeId(serviceReminder.getServiceTypeId());
			service.setServiceSubTypeId(serviceReminder.getServiceSubTypeId());
			service.setVid(serviceReminder.getVid());
			service.setVehicleGroupId(serviceReminder.getVehicleGroupId());
			service.setMeter_interval(serviceReminder.getMeter_interval());
			service.setTime_interval(serviceReminder.getTime_interval());
			service.setTime_intervalperiodId(serviceReminder.getTime_intervalperiodId());
			service.setMeter_threshold(serviceReminder.getMeter_threshold());
			service.setServiceType(serviceReminder.getServiceType());
			service.setTime_threshold(serviceReminder.getTime_threshold());
			service.setTime_thresholdperiodId(serviceReminder.getTime_thresholdperiodId());
			service.setService_subscribeduser_name(serviceReminder.getService_subscribeduser_name());
			service.setCreated(serviceReminder.getCreated());
			service.setVehicle_currentOdometer(valueObject.getInt("vehicleOdometer",0));
			Integer CurrentOdometer = valueObject.getInt("vehicleOdometer");
			Integer Meter_interval = serviceReminder.getMeter_interval();

			
			if (Meter_interval == null) {
				Meter_interval = 0;
			}
			Integer ServiceOdometer = CurrentOdometer + Meter_interval;
			service.setMeter_serviceodometer(ServiceOdometer);

			if (serviceReminder.getMeter_threshold() != null) {
				Integer meter_threshold = serviceReminder.getMeter_threshold();
				if (ServiceOdometer == 0) {
					meter_threshold = 0;
				}
				Integer ServiceOdometer_threshold = ServiceOdometer - meter_threshold;
				service.setMeter_servicethreshold_odometer(ServiceOdometer_threshold);
			}

			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			service.setTime_interval_currentdate(toDate);

			if (serviceReminder.getTime_interval() != null) {
				Integer time_Intervalperiod = 0;
				if (serviceReminder.getTime_intervalperiodId() >= 0) {
					time_Intervalperiod = serviceReminder.getTime_interval();
				}
				Integer timeserviceDaysPeriod = 0;
				switch (serviceReminder.getTime_intervalperiodId()) {
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

				service.setTime_servicedate(calendar.getTime());

				if (serviceReminder.getTime_threshold() != null) {

					Integer Time_threshold = 0;
					switch (serviceReminder.getTime_thresholdperiodId()) {
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
						Time_threshold = serviceReminder.getTime_threshold();
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
						Time_threshold = serviceReminder.getTime_threshold() * 7;
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
						Time_threshold = serviceReminder.getTime_threshold() * 30;
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
						Time_threshold = serviceReminder.getTime_threshold() * 365;
						break;

					default:
						Time_threshold = serviceReminder.getTime_threshold();
						break;
					}
					final Calendar calendar_advanceThreshold = Calendar.getInstance();
					calendar_advanceThreshold.setTime(calendar.getTime());
					calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
					
					service.setTime_servicethreshold_date(calendar_advanceThreshold.getTime());

				}
			}

			Integer Service_Howtimes = serviceReminder.getService_remider_howtimes() + 1;
			service.setService_remider_howtimes(Service_Howtimes);
			service.setLastModifiedById(valueObject.getLong("userId"));
			service.setLastupdated(toDate);
			service.setLast_servicecompleldid(valueObject.getLong("dealerServiceEntriesId"));
			service.setLast_servicecompleldbyId(valueObject.getLong("userId"));
			service.setLast_servicedate(toDate);
			service.setDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"));
			service.setCompanyId(valueObject.getInt("companyId"));
			service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_INACTIVE);
			service.setServiceProgramId(serviceReminder.getServiceProgramId());
			service.setServiceScheduleId(serviceReminder.getServiceScheduleId());
			service.setServiceType(serviceReminder.getServiceType());
			service.setSkip(serviceReminder.isSkip());
			if(serviceReminder.getSkipRemark() != null)
			service.setSkipRemark(serviceReminder.getSkipRemark());
			return service;

		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public DealerServiceReminderHistory prepareDealerServiceReminderHistory(ServiceReminder	serviceReminder, ValueObject	valueObject)throws Exception{
		DealerServiceReminderHistory		orderHistory	= null;
		try {
			orderHistory	= new DealerServiceReminderHistory();
			
			orderHistory.setService_id(serviceReminder.getService_id());
			orderHistory.setService_Number(serviceReminder.getService_Number());
			orderHistory.setServiceThresholdDate(serviceReminder.getTime_servicethreshold_date());
			orderHistory.setServiceDate(serviceReminder.getTime_servicedate());
			orderHistory.setServiceTheshHoldOdometer(serviceReminder.getMeter_servicethreshold_odometer());
			orderHistory.setServiceOdometer(serviceReminder.getMeter_serviceodometer());
			orderHistory.setDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"));
			orderHistory.setDSE_CompletedOn(new Date());
			orderHistory.setDSE_Odometer(valueObject.getInt("vehicleOdometer"));
			orderHistory.setDSE_CompletedById(valueObject.getLong("dealerServiceEntriesId"));
			orderHistory.setCompanyId(valueObject.getInt("companyId"));
			orderHistory.setVid(serviceReminder.getVid());
			
			return orderHistory;
		} catch (Exception e) {
			throw e;
		}
	}

	public DealerServiceExtraIssue prepareDealerServiceExtraIssue(ValueObject valueObject) throws Exception {
		try {
			DealerServiceExtraIssue dealerServiceExtraIssue = new DealerServiceExtraIssue();
		//	dealerServiceExtraIssue.setDealerServiceExtraIssueId(valueObject.getLong("dealerServiceExtraIssueId"));
			dealerServiceExtraIssue.setDescription(valueObject.getString("description"));
			dealerServiceExtraIssue.setDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"));
			dealerServiceExtraIssue.setCreatedById(valueObject.getLong("userId"));
			dealerServiceExtraIssue.setLastModifiedById(valueObject.getLong("userId"));
			dealerServiceExtraIssue.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceExtraIssue.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceExtraIssue.setCompanyId(valueObject.getInt("companyId"));
			dealerServiceExtraIssue.setMarkForDelete(false);
			return dealerServiceExtraIssue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public DealerServiceRemark prepareDealerServiceEntriesRemark(ValueObject valueObject) throws Exception {
		try {
			DealerServiceRemark dealerServiceRemark = new DealerServiceRemark();
			dealerServiceRemark.setRemark(valueObject.getString("remark"));
			dealerServiceRemark.setDriverId(valueObject.getInt("driverId",0));
			dealerServiceRemark.setAssignee(valueObject.getLong("assignToId",0));
			dealerServiceRemark.setDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"));
			dealerServiceRemark.setRemarkTypeId(valueObject.getShort("statusId"));
			dealerServiceRemark.setCreatedById(valueObject.getLong("userId"));
			dealerServiceRemark.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			dealerServiceRemark.setCompanyId(valueObject.getInt("companyId"));
			dealerServiceRemark.setMarkForDelete(false);
			return dealerServiceRemark;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	

}
