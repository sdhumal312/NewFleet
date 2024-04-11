package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.RenealReminderStatus;
import org.fleetopgroup.constant.RenewalApprovalStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.dto.RenewalReminderApprovalDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.RenewalReminderHistoryDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.RenewalReminderApproval;
import org.fleetopgroup.persistence.model.RenewalReminderHistory;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.qos.logback.classic.pattern.Util;

public class RenewalReminderBL {

	public RenewalReminderBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat sqlFormatTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	SimpleDateFormat sqlFormatTime2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// get logic in Renewal Reminder Information
	public RenewalReminder prepareRenewalRemider(RenewalReminderDto renewalReminderDto) throws Exception {

		RenewalReminder renewal = new RenewalReminder();
		renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
		renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
		renewal.setVid(renewalReminderDto.getVid());
		//renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
		
		String[] From_TO_Array = null;
		try {

			String From_TO = renewalReminderDto.getRenewal_from();
			From_TO_Array = From_TO.split("  to  ");

			java.util.Date startDate = dateFormat.parse(From_TO_Array[0]);
			java.sql.Date FromDate = new Date(startDate.getTime());
			
			java.util.Date endDate = dateFormat.parse(From_TO_Array[1]);
			java.sql.Date ToDate = new Date(endDate.getTime());
			
			
			renewal.setRenewal_from(FromDate);
			renewal.setRenewal_to(ToDate);
			
			if (renewalReminderDto.getRenewal_dateofpayment() != null) {
				java.util.Date dateofpay = dateFormat.parse(renewalReminderDto.getRenewal_dateofpayment());
				java.sql.Date PayDate = new Date(dateofpay.getTime());
				renewal.setRenewal_dateofpayment(PayDate);
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
		renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());
		
		renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
		renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());
		renewal.setRenewal_number(renewalReminderDto.getRenewal_number());

		renewal.setRenewal_paidbyId(renewalReminderDto.getRenewal_paidbyId());

		renewal.setRenewal_timethreshold(renewalReminderDto.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(renewalReminderDto.getRenewal_periedthreshold());

		/* due end date */
		// get the date from database is 10/12/2015
		String duedate = From_TO_Array[1];

		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		/* due timethreshold and peried of reminder */
		Integer duetimeandperied;

		// calculation of the reminder before date of reminder
		Integer timeandperied = (renewalReminderDto.getRenewal_timethreshold())
				* (renewalReminderDto.getRenewal_periedthreshold());

		// timeandperied=3*0 days; or timeandperied=3*7 weeks or
		// timeandperied=3*28 month

		if (timeandperied == 0) {
			duetimeandperied = renewalReminderDto.getRenewal_timethreshold(); // 3
																				// days
		} else {
			duetimeandperied = timeandperied; // 21 days
		}

		String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
		// save renewal date
		//renewal.setRenewal_dateofRenewal(reminder_dateof);
		renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));

		if (renewalReminderDto.getCreated() != null) {
			java.util.Date date;
			try {
				date = dateFormat.parse(renewalReminderDto.getCreated());
				java.sql.Date FromDate = new Date(date.getTime());
				renewal.setCreated(FromDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		renewal.setRenewal_approvedID(renewalReminderDto.getRenewal_approvedID());
		renewal.setRenewal_approvedbyId(renewalReminderDto.getRenewal_approvedbyId());
		renewal.setRenewal_approvedComment(renewalReminderDto.getRenewal_approvedComment());
		renewal.setRenewal_approveddate(renewalReminderDto.getRenewal_approveddate());

		/** Set Status in Issues */
		renewal.setMarkForDelete(false);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/** Set Created by email Id */
		// renewal.setCreatedBy(create_name);
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());
		renewal.setCreatedById(userDetails.getId());
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);

		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);

		renewal.setRenewal_approvedID((long) 0);
		
		//renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
		//renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
		renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
		//renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
		renewal.setRenewalTypeId(renewalReminderDto.getRenewalTypeId());
		renewal.setRenewal_Subid(renewalReminderDto.getRenewal_Subid());
		renewal.setPaymentTypeId(renewalReminderDto.getPaymentTypeId());
		renewal.setVendorId(renewalReminderDto.getVendorId());
		renewal.setTallyCompanyId(renewalReminderDto.getTallyCompanyId());
		//renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewalReminderDto.getPaymentTypeId()));
		
		return renewal;
	}

	// get logic in Renewal Reminder Information
	public RenewalReminder prepare_Approval_Upload_File_RenewalRemider(RenewalReminderDto renewalReminderDto) {

		RenewalReminder renewal = new RenewalReminder();

		renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
		renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
		renewal.setVid(renewalReminderDto.getVid());
		//renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
		//renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());

		renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
		renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());
		renewal.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
		//renewal.setRenewal_status(RenealReminderStatus.NOT_APPROVED_NAME);

		renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());

		renewal.setRenewal_number(renewalReminderDto.getRenewal_number());

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/** Set Created by email Id */
		// renewal.setCreatedBy(create_name);
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		// renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);

		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);

		renewal.setRenewal_approvedID((long) 0);
		renewal.setRenewalTypeId(renewalReminderDto.getRenewalTypeId());
		renewal.setRenewal_Subid(renewalReminderDto.getRenewal_Subid());

		return renewal;
	}

	/* list of Renewal Remindar */
	public List<RenewalReminderDto> prepareListofRenewalDto(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				if (renewalReminderDto.getRenewal_D_from() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_D_from()));
				}
				if (renewalReminderDto.getRenewal_D_to() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_D_to()));
				}
				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				if (renewalReminderDto.getRenewal_dateofpayment() != null) {
					renewal.setRenewal_dateofpayment(
							dateFormat_Name.format(renewalReminderDto.getRenewal_dateofpayment()));
				}
				renewal.setPaymentTypeId(renewalReminderDto.getPaymentTypeId());
				renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewalReminderDto.getPaymentTypeId()));
				renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());
				renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
				renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());
				renewal.setRenewal_number(renewalReminderDto.getRenewal_number());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (renewalReminderDto.getRenewal_to() != null) {

					int diffInDays = (int) ((renewalReminderDto.getRenewal_D_to().getTime() - toDate.getTime())
							/ (1000 * 60 * 60 * 24));

					// System.out.println(diffInDays);
					String diffenceDays = null;

					switch (diffInDays) {
					case 0:
						diffenceDays = ("Today");
						break;
					case -1:
						diffenceDays = ("YesterDay");
						break;
					case 1:
						diffenceDays = ("Tomorrow");
						break;
					default:
						if (diffInDays < -1) {
							long days = -diffInDays;
							if (days >= 365) {
								diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
							} else if (days > 30) {
								diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
							} else
								diffenceDays = (-diffInDays + " days ago");
						} else if (diffInDays > 1) {
							if (diffInDays >= 365) {
								diffenceDays = (diffInDays / 365 + " years & " + ((diffInDays % 365) / 31)
										+ " months  from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					// System.out.println( diffenceDays);
					renewal.setRenewal_dueDifference(diffenceDays);
				}
				// Overdue and Due soon message code logic

				String diffenceThrsholdOdometer = null;

				// fuel date converted String to date Format
				try {
					java.util.Date date = dateFormat.parse(renewalReminderDto.getRenewal_dateofRenewal());
					java.sql.Date RenewalDueDate = new Date(date.getTime());

					// Due soon message in Time interval
					if (RenewalDueDate != null) {

						int diffInDays_threshold = (int) ((toDate.getTime() - RenewalDueDate.getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println("time "+diffInDays_threshold);

						switch (diffInDays_threshold) {

						case 0:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((renewalReminderDto.getRenewal_D_to().getTime()
										- RenewalDueDate.getTime()) / (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
								}

							}
							break;
						}
						// System.out.println("time "+diffenceThrsholdOdometer);
						if(renewalReminderDto.isNewRRCreated()) {
							diffenceThrsholdOdometer	= "New created";
						}
						renewal.setRenewal_dueRemDate(diffenceThrsholdOdometer);
					}

				} catch (ParseException e) {

					e.printStackTrace();
				}
				
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				renewal.setRenewal_approvedby(renewalReminderDto.getRenewal_approvedby());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}
	
	public List<RenewalReminderDto> prepareListofRenewal(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				if (renewalReminderDto.getRenewal_from_Date() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_from_Date()));
				}
				if (renewalReminderDto.getRenewal_To_Date() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_To_Date()));
				}
				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				if (renewalReminderDto.getRenewal_payment_Date() != null) {
					renewal.setRenewal_dateofpayment(
							dateFormat_Name.format(renewalReminderDto.getRenewal_payment_Date()));
				}
				renewal.setPaymentTypeId(renewalReminderDto.getPaymentTypeId());
				renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewalReminderDto.getPaymentTypeId()));
				renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());
				renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
				renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());
				renewal.setRenewal_number(renewalReminderDto.getRenewal_number());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (renewalReminderDto.getRenewal_To_Date() != null) {

					int diffInDays = (int) ((renewalReminderDto.getRenewal_To_Date().getTime() - toDate.getTime())
							/ (1000 * 60 * 60 * 24));

					// System.out.println(diffInDays);
					String diffenceDays = null;

					switch (diffInDays) {
					case 0:
						diffenceDays = ("Today");
						break;
					case -1:
						diffenceDays = ("YesterDay");
						break;
					case 1:
						diffenceDays = ("Tomorrow");
						break;
					default:
						if (diffInDays < -1) {
							long days = -diffInDays;
							if (days >= 365) {
								diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
							} else if (days > 30) {
								diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
							} else
								diffenceDays = (-diffInDays + " days ago");
						} else if (diffInDays > 1) {
							if (diffInDays >= 365) {
								diffenceDays = (diffInDays / 365 + " years & " + ((diffInDays % 365) / 31)
										+ " months  from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					// System.out.println( diffenceDays);
					renewal.setRenewal_dueDifference(diffenceDays);
				}
				// Overdue and Due soon message code logic

				String diffenceThrsholdOdometer = null;

				// fuel date converted String to date Format
				try {
					java.util.Date date = dateFormat.parse(renewalReminderDto.getRenewal_dateofRenewal());
					java.sql.Date RenewalDueDate = new Date(date.getTime());

					// Due soon message in Time interval
					if (RenewalDueDate != null) {

						int diffInDays_threshold = (int) ((toDate.getTime() - RenewalDueDate.getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println("time "+diffInDays_threshold);

						switch (diffInDays_threshold) {

						case 0:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((renewalReminderDto.getRenewal_To_Date().getTime()
										- RenewalDueDate.getTime()) / (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
								}

							}
							break;
						}
						// System.out.println("time "+diffenceThrsholdOdometer);
						if(renewalReminderDto.isNewRRCreated()) {
							diffenceThrsholdOdometer	= "New created";
						}
						renewal.setRenewal_dueRemDate(diffenceThrsholdOdometer);
					}

				} catch (ParseException e) {

					e.printStackTrace();
				}
				
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				renewal.setRenewal_approvedby(renewalReminderDto.getRenewal_approvedby());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/* list of Renewal Remindar */
	public List<RenewalReminderDto> prepareList_Only_Search(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				if (renewalReminderDto.getRenewal_D_from() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_D_from()));
				}
				if (renewalReminderDto.getRenewal_D_to() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_D_to()));
				}
				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				if (renewalReminderDto.getRenewal_payment_Date() != null) {
					renewal.setRenewal_dateofpayment(
							dateFormat_Name.format(renewalReminderDto.getRenewal_payment_Date()));
				}

				renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/* list of Renewal Remindar */
	public List<RenewalReminderDto> Only_Show_ListofRenewalDto(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setVehicleType(renewalReminderDto.getVehicleType());
				renewal.setBranchName(renewalReminderDto.getBranchName());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_D_from() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_D_from()));
				}
				if (renewalReminderDto.getRenewal_D_to() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_D_to()));
				}

				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());

				renewal.setRenewal_approvedID(renewalReminderDto.getRenewal_approvedID());

				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setRenewal_number(renewalReminderDto.getRenewal_number());
				renewal.setRenewalBase64Document(renewalReminderDto.getRenewalBase64Document());
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (renewalReminderDto.getRenewal_to() != null) {

					// System.out.println( diffenceDays);
					renewal.setRenewal_dueDifference(
							time_DateDifferent(renewalReminderDto.getRenewal_D_to(), currentDate));

					// System.out.println(getDateDifferenceInDDMMYYYY(renewalReminderDto.getRenewal_to(),
					// currentDate));
				}
				// Overdue and Due soon message code logic

				String diffenceThrsholdOdometer = null;

				// fuel date converted String to date Format
				try {
					java.util.Date date = dateFormat.parse(renewalReminderDto.getRenewal_dateofRenewal());
					java.sql.Date RenewalDueDate = new Date(date.getTime());

					// Due soon message in Time interval
					if (RenewalDueDate != null) {

						int diffInDays_threshold = (int) ((toDate.getTime() - RenewalDueDate.getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println("time "+diffInDays_threshold);

						switch (diffInDays_threshold) {

						case 0:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((renewalReminderDto.getRenewal_D_to().getTime()
										- RenewalDueDate.getTime()) / (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
								}

							}
							break;
						}
						if(renewalReminderDto.isNewRRCreated()) {
							diffenceThrsholdOdometer	= "New created";
						}
						renewal.setRenewal_dueRemDate(diffenceThrsholdOdometer);

					}

				} catch (ParseException e) {

					e.printStackTrace();
				}
				if(diffenceThrsholdOdometer == null)
					renewal.setRenewalDueStatus("Active");
				else
					renewal.setRenewalDueStatus(diffenceThrsholdOdometer);
					

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	public List<RenewalReminderDto> Only_Show_ListofRenewal(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_from_Date() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_from_Date()));
				}
				if (renewalReminderDto.getRenewal_To_Date()!= null) {
					renewal.setRenewal_To_Date(renewalReminderDto.getRenewal_To_Date());
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_To_Date()));
				}
				renewal.setRenewalAproval_Number(renewalReminderDto.getRenewalAproval_Number());
				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());

				renewal.setRenewal_approvedID(renewalReminderDto.getRenewal_approvedID());

				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());

				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (renewalReminderDto.getRenewal_To_Date() != null) {

					// System.out.println( diffenceDays);
					renewal.setRenewal_dueDifference(
							time_DateDifferent(renewalReminderDto.getRenewal_To_Date(), currentDate));

					// System.out.println(getDateDifferenceInDDMMYYYY(renewalReminderDto.getRenewal_to(),
					// currentDate));
				}
				// Overdue and Due soon message code logic

				String diffenceThrsholdOdometer = null;

				// fuel date converted String to date Format
				try {
					java.util.Date date = dateFormat.parse(renewalReminderDto.getRenewal_dateofRenewal());
					java.sql.Date RenewalDueDate = new Date(date.getTime());

					// Due soon message in Time interval
					if (RenewalDueDate != null) {
						
						int diffInDays_threshold = (int) ((toDate.getTime() - RenewalDueDate.getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println("diffInDays_threshold "+diffInDays_threshold);

						switch (diffInDays_threshold) {

						case 0:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {
								
								int diffInTimeServicethreshold = (int) ((renewalReminderDto.getRenewal_To_Date().getTime()
										- RenewalDueDate.getTime()) / (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
								}

							}
							break;
						}
						
						if(renewalReminderDto.isNewRRCreated()) {
							diffenceThrsholdOdometer	= "New created";
						}
						if(renewalReminderDto.isIgnored()) {
							diffenceThrsholdOdometer	= "Ignored";
						}
						
						// System.out.println("time "+diffenceThrsholdOdometer);
						renewal.setRenewal_dueRemDate(diffenceThrsholdOdometer);

					}
					renewal.setVehicleGroup(renewalReminderDto.getVehicleGroup());
					renewal.setVehicleStatus(renewalReminderDto.getVehicleStatus());
					renewal.setIgnored(renewalReminderDto.isIgnored());
					renewal.setAllowToIgnored(renewalReminderDto.isAllowToIgnored());

				} catch (ParseException e) {

					e.printStackTrace();
				}

				Dtos.add(renewal);
			}
			
			
		}
		return Dtos;
	}


	
	/* list of Renewal Remindar */
	public List<RenewalReminderDto> Today_Show_ListofRenewal_Reminder_VehicleMandary(
			List<RenewalReminderDto> renewalReminder, List<VehicleMandatoryDto> Mandatory) {
		List<RenewalReminderDto> Dtos = new ArrayList<RenewalReminderDto>();
		RenewalReminderDto renewal = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_from_Date() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_from_Date()));
				}
				if (renewalReminderDto.getRenewal_To_Date() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_To_Date()));
				}

				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());

				renewal.setRenewal_approvedID(renewalReminderDto.getRenewal_approvedID());
				renewal.setRenewalAproval_Number(renewalReminderDto.getRenewalAproval_Number());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (renewalReminderDto.getRenewal_To_Date() != null) {

					// System.out.println( diffenceDays);
					renewal.setRenewal_dueDifference(
							time_DateDifferent(renewalReminderDto.getRenewal_To_Date(), currentDate));

					// System.out.println(getDateDifferenceInDDMMYYYY(renewalReminderDto.getRenewal_to(),
					// currentDate));
				}
				// Overdue and Due soon message code logic

				String diffenceThrsholdOdometer = null;

				// fuel date converted String to date Format
				try {
					java.util.Date date = dateFormat.parse(renewalReminderDto.getRenewal_dateofRenewal());
					java.sql.Date RenewalDueDate = new Date(date.getTime());

					// Due soon message in Time interval
					if (RenewalDueDate != null) {

						int diffInDays_threshold = (int) ((toDate.getTime() - RenewalDueDate.getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println("time "+diffInDays_threshold);

						switch (diffInDays_threshold) {

						case 0:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((renewalReminderDto.getRenewal_To_Date().getTime()
										- RenewalDueDate.getTime()) / (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
								}

							}
							break;
						}
						if(renewalReminderDto.isNewRRCreated()) {
							diffenceThrsholdOdometer	= "New created";
						}
						// System.out.println("time "+diffenceThrsholdOdometer);
						renewal.setRenewal_dueRemDate(diffenceThrsholdOdometer);
						renewal.setVehicleGroup(renewalReminderDto.getVehicleGroup());
					}

				} catch (ParseException e) {

					e.printStackTrace();
				}catch (Exception e) {
				}

				Dtos.add(renewal);
			}
		}

		if (Mandatory != null && !Mandatory.isEmpty()) {
			for (VehicleMandatoryDto vMandatory : Mandatory) {

				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((long) 1);
				renewal.setRenewal_R_Number((long) 1);
				renewal.setVid(vMandatory.getVEHICLE_ID());
				renewal.setVehicle_registration(vMandatory.getVEHICLE_REGISTRATION());
				renewal.setRenewal_type(vMandatory.getMANDATORY_RENEWAL_SUB_NAME());
				renewal.setRenewal_subtype(null);
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				if (currentDate != null) {

					renewal.setRenewal_from(dateFormat_Name.format(currentDate));

					renewal.setRenewal_to(dateFormat_Name.format(currentDate));
				}

				renewal.setRenewal_Amount(0.0);

				renewal.setRenewal_document(false);
				renewal.setRenewal_document_id((long) 0);

				renewal.setRenewal_approvedID((long) 0);

				renewal.setRenewal_status("MANDATORY");
				// get Current days

				// System.out.println( diffenceDays);
				renewal.setRenewal_dueDifference("Mandatory Renewal");

				// System.out.println("time "+diffenceThrsholdOdometer);
				renewal.setRenewal_dueRemDate("Today");

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/* list of Renewal Remindar */
	public List<RenewalReminderDto> Show_Only_ApprovalListofRenewalDto(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_D_from() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_D_from()));
				}
				if (renewalReminderDto.getRenewal_D_to() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_D_to()));
				}

				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());

				renewal.setRenewal_paymentType(renewalReminderDto.getRenewal_paymentType());
				renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
				if (renewalReminderDto.getRenewal_dateofpayment() != null) {
					renewal.setRenewal_dateofpayment(
							dateFormat_Name.format(renewalReminderDto.getRenewal_dateofpayment()));
				}
				renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());

				renewal.setRenewal_status(renewalReminderDto.getRenewal_status());

				renewal.setRenewal_approvedID(renewalReminderDto.getRenewal_approvedID());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}
	
	public List<RenewalReminderDto> Show_Only_ApprovalListofRenewal(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_from_Date() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_from_Date()));
				}
				if (renewalReminderDto.getRenewal_To_Date() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_To_Date()));
				}

				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());

				renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
				if (renewalReminderDto.getRenewal_paymentType()!= null) {
					renewal.setRenewal_dateofpayment(
							dateFormat_Name.format(renewalReminderDto.getRenewal_payment_Date()));
				}
				renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				renewal.setPaymentTypeId(renewalReminderDto.getPaymentTypeId());
				renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewalReminderDto.getPaymentTypeId()));

				renewal.setRenewal_approvedID(renewalReminderDto.getRenewal_approvedID());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/* list of Renewal Remindar */
	public List<RenewalReminderDto> Approval_Show_ListofRenewalDto(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_from_Date() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_from_Date()));
				}
				if (renewalReminderDto.getRenewal_To_Date() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_To_Date()));
				}

				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_document(renewalReminderDto.getRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());
				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));
				renewal.setRenewalAproval_Number(renewalReminderDto.getRenewalAproval_Number());
				renewal.setVehicleGroup(renewalReminderDto.getVehicleGroup());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/* list of Renewal Remindar */
	public List<RenewalReminderDto> Report_Show_ListofRenewalDto(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
				renewal.setRenewal_R_Number(renewalReminderDto.getRenewal_R_Number());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
				renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());
				if (renewalReminderDto.getRenewal_D_from() != null) {
					renewal.setRenewal_from(dateFormat_Name.format(renewalReminderDto.getRenewal_D_from()));
				}
				if (renewalReminderDto.getRenewal_D_to() != null) {
					renewal.setRenewal_to(dateFormat_Name.format(renewalReminderDto.getRenewal_D_to()));
				}

				renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

				renewal.setRenewal_staus_id(renewalReminderDto.getRenewal_staus_id());
				renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminderDto.getRenewal_staus_id()));

				renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
				renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());
				renewal.setRenewal_approvedby(renewalReminderDto.getRenewal_approvedby());
				renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	public Double Total_RenewalReminder_Amount(List<RenewalReminderDto> renewalReminder) {
		Double TotalRenewal = 0.0;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				if (renewalReminderDto.getRenewal_Amount() != null) {
					TotalRenewal += renewalReminderDto.getRenewal_Amount();
				}
			}
		}
		
		return Utility.round(TotalRenewal, 2);
	}

	public Double GET_Total_RenewalReminder_Amount(List<RenewalReminderDto> renewalReminder) {
		Double TotalRenewal = 0.0;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				if (renewalReminderDto.getRenewal_Amount() != null) {
					TotalRenewal += renewalReminderDto.getRenewal_Amount();
				}
			}
		}

		return Utility.round(TotalRenewal, 2);
	}

	
	public Double Total_PartInventory_Quantity(List<InventoryDto> inventoryQuantity) {
		Double TotalQuantity = 0.0;
		if (inventoryQuantity != null && !inventoryQuantity.isEmpty()) {
			for (InventoryDto quantity : inventoryQuantity) {
				if (quantity.getQuantity() != null) {
					TotalQuantity += quantity.getQuantity();
				}
			}
		}

		return  Utility.round(TotalQuantity, 2);
	}

	public Double Total_PartInventoryLocation_Quantity(List<InventoryLocationDto> inventoryQuantity) {
		Double TotalQuantity = 0.0;
		if (inventoryQuantity != null && !inventoryQuantity.isEmpty()) {
			for (InventoryLocationDto quantity : inventoryQuantity) {
				if (quantity.getLocation_quantity() != null && quantity.getParttype() != null
						&& quantity.getParttype().equalsIgnoreCase(PartType.PART_TYPE_QUANTITY_NAME)) {
					TotalQuantity += quantity.getLocation_quantity();
				}
			}
		}

		return Utility.round(TotalQuantity, 2);
	}

	public Double Total_PartInventoryLocation_Liter(List<InventoryLocationDto> inventoryQuantity) {
		Double TotalQuantity = 0.0;
		if (inventoryQuantity != null && !inventoryQuantity.isEmpty()) {
			for (InventoryLocationDto quantity : inventoryQuantity) {
				if (quantity.getLocation_quantity() != null && quantity.getParttype() != null
						&& quantity.getParttype().equalsIgnoreCase(PartType.PART_TYPE_LITER_NAME)) {
					TotalQuantity += quantity.getLocation_quantity();
				}
			}
		}

		return Utility.round(TotalQuantity, 2);
	}

	public Double Total_WorkOrder_Amount(List<WorkOrdersDto> workO) {
		Double TotalAmount = 0.0;
		if (workO != null && !workO.isEmpty()) {
			for (WorkOrdersDto WOAmount : workO) {
				if (WOAmount.getTotalworkorder_cost() != null) {
					TotalAmount += WOAmount.getTotalworkorder_cost();
				}
			}
		}

		return Utility.round(TotalAmount, 2);
	}

	public Double Total_PurchaseOrder_Amount(List<PurchaseOrdersDto> purchase) {
		Double TotalAmount = 0.0;
		if (purchase != null && !purchase.isEmpty()) {
			for (PurchaseOrdersDto POAmount : purchase) {
				if (POAmount.getPurchaseorder_totalcost() != null) {
					TotalAmount += POAmount.getPurchaseorder_totalcost();
				}
			}
		}

		return Utility.round(TotalAmount, 2);
	}

	public Double Total_PurchaseOrder_Balance_Amount(List<PurchaseOrdersDto> purchase) {
		Double TotalBalanceAmount = 0.0;
		if (purchase != null && !purchase.isEmpty()) {
			for (PurchaseOrdersDto POAmount : purchase) {
				if (POAmount.getPurchaseorder_balancecost() != null) {
					TotalBalanceAmount += POAmount.getPurchaseorder_balancecost();
				}
			}
		}

		return Utility.round(TotalBalanceAmount, 2);
	}
	
	public Double Total_PurchaseOrderToParts_Amount(List<PurchaseOrdersToPartsDto> purchaseToParts) {
		Double TotalAmount = 0.0;
		if (purchaseToParts != null && !purchaseToParts.isEmpty()) {
			for (PurchaseOrdersToPartsDto POAmount : purchaseToParts) {
				if (POAmount.getParteachcost() != null) {
					TotalAmount += POAmount.getParteachcost();
				}
			}
		}

		return Utility.round(TotalAmount, 2);
	}

	public Double Total_ServiceEntries_Amount(List<ServiceEntriesDto> Service) {
		Double TotalAmount = 0.0;
		if (Service != null && !Service.isEmpty()) {
			for (ServiceEntriesDto SEAmount : Service) {
				if (SEAmount.getTotalserviceROUND_cost() != null) {
					TotalAmount += SEAmount.getTotalserviceROUND_cost();
				}
			}
		}

		return Utility.round(TotalAmount, 2);
	}

	/*public RenewalReminderDto GetRenewalReminder(RenewalReminder renewalReminder) {

		RenewalReminderDto renewal = new RenewalReminderDto();

		renewal.setRenewal_id(renewalReminder.getRenewal_id());
		renewal.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
		renewal.setVid(renewalReminder.getVid());
		//renewal.setVehicle_registration(renewalReminder.getVehicle_registration());
		//renewal.setRenewal_type(renewalReminder.getRenewal_type());
		//renewal.setRenewal_subtype(renewalReminder.getRenewal_subtype());

		renewal.setRenewal_from(dateFormat.format(renewalReminder.getRenewal_from()));
		renewal.setRenewal_to(dateFormat.format(renewalReminder.getRenewal_to()));
		renewal.setRenewal_receipt(renewalReminder.getRenewal_receipt());
		renewal.setRenewal_Amount(renewalReminder.getRenewal_Amount());
		renewal.setRenewal_dateofpayment(dateFormat.format(renewalReminder.getRenewal_dateofpayment()));
		//renewal.setRenewal_paymentType(renewalReminder.getRenewal_paymentType());
		renewal.setRenewal_PayNumber(renewalReminder.getRenewal_PayNumber());
		renewal.setRenewal_authorization(renewalReminder.getRenewal_authorization());
		renewal.setRenewal_number(renewalReminder.getRenewal_number());
		renewal.setRenewal_dateofRenewal(renewalReminder.getRenewal_dateofRenewal());
		renewal.setRenewal_paidby(renewalReminder.getRenewal_paidby());
		renewal.setRenewal_timethreshold(renewalReminder.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(renewalReminder.getRenewal_periedthreshold());

		renewal.setRenewal_document(renewalReminder.isRenewal_document());
		renewal.setRenewal_document_id(renewalReminder.getRenewal_document_id());

		//renewal.setRenewal_status(renewalReminder.getRenewal_status());
		renewal.setRenewal_approvedID(renewalReminder.getRenewal_approvedID());
		renewal.setRenewal_approvedby(renewalReminder.getRenewal_approvedby());
		renewal.setRenewal_approveddate(renewalReminder.getRenewal_approveddate());
		renewal.setRenewal_approvedComment(renewalReminder.getRenewal_approvedComment());

		// Create and Last updated Display
		renewal.setCreatedBy(renewalReminder.getCreatedBy());
		if (renewalReminder.getCreated() != null) {
			renewal.setCreated(CreatedDateTime.format(renewalReminder.getCreated()));
		}
		renewal.setLastModifiedBy(renewalReminder.getLastModifiedBy());
		if (renewalReminder.getLastupdated() != null) {
			renewal.setLastupdated(CreatedDateTime.format(renewalReminder.getLastupdated()));
		}

		return renewal;
	}
*/	
	public RenewalReminderDto GetRenewalReminder(RenewalReminderDto renewalReminder) {

		RenewalReminderDto renewal = new RenewalReminderDto();
		if(renewalReminder != null) {
			renewal.setRenewal_id(renewalReminder.getRenewal_id());
			renewal.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
			renewal.setVid(renewalReminder.getVid());
			renewal.setVehicle_registration(renewalReminder.getVehicle_registration());
			renewal.setRenewal_type(renewalReminder.getRenewal_type());
			renewal.setRenewal_subtype(renewalReminder.getRenewal_subtype());
			
			renewal.setRenewal_from(dateFormat.format(renewalReminder.getRenewal_from_Date()));
			renewal.setRenewal_to(dateFormat.format(renewalReminder.getRenewal_To_Date()));
			renewal.setRenewal_from_Date(renewalReminder.getRenewal_from_Date());
			renewal.setRenewal_To_Date(renewalReminder.getRenewal_To_Date());
			
			renewal.setRenewal_receipt(renewalReminder.getRenewal_receipt());
			renewal.setRenewal_Amount(renewalReminder.getRenewal_Amount());
			if(renewalReminder.getRenewal_payment_Date()!=null) {
				renewal.setRenewal_dateofpayment(dateFormat.format(renewalReminder.getRenewal_payment_Date()));
			}
			renewal.setPaymentTypeId(renewalReminder.getPaymentTypeId());
			renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewalReminder.getPaymentTypeId()));
			renewal.setRenewal_PayNumber(renewalReminder.getRenewal_PayNumber());
			renewal.setRenewal_authorization(renewalReminder.getRenewal_authorization());
			renewal.setRenewal_number(renewalReminder.getRenewal_number());
			renewal.setRenewal_dateofRenewal(renewalReminder.getRenewal_dateofRenewal());
			renewal.setRenewal_paidby(renewalReminder.getRenewal_paidby());
			renewal.setRenewal_timethreshold(renewalReminder.getRenewal_timethreshold());
			renewal.setRenewal_periedthreshold(renewalReminder.getRenewal_periedthreshold());
			
			renewal.setRenewal_document(renewalReminder.getRenewal_document());
			renewal.setRenewal_document_id(renewalReminder.getRenewal_document_id());
			
			renewal.setRenewal_staus_id(renewalReminder.getRenewal_staus_id());
			renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminder.getRenewal_staus_id()));
			renewal.setRenewal_approvedID(renewalReminder.getRenewal_approvedID());
			renewal.setRenewal_approvedby(renewalReminder.getRenewal_approvedby());
			renewal.setRenewal_approveddate(renewalReminder.getRenewal_approveddate());
			if(renewalReminder.getRenewal_approveddate() != null)
			renewal.setRenewalApproveddate(sqlFormatTime.format(renewalReminder.getRenewal_approveddate()));
			renewal.setRenewal_approvedComment(renewalReminder.getRenewal_approvedComment());
			renewal.setRenewalAproval_Number(renewalReminder.getRenewalAproval_Number());
			
			// Create and Last updated Display
			renewal.setCreatedBy(renewalReminder.getCreatedBy());
			if (renewalReminder.getCreatedOn() != null) {
				renewal.setCreated(CreatedDateTime.format(renewalReminder.getCreatedOn()));
			}
			renewal.setLastModifiedBy(renewalReminder.getLastModifiedBy());
			if (renewalReminder.getLastupdatedOn() != null) {
				renewal.setLastupdated(CreatedDateTime.format(renewalReminder.getLastupdatedOn()));
			}
			renewal.setRenewalTypeId(renewalReminder.getRenewalTypeId());
			renewal.setRenewal_Subid(renewalReminder.getRenewal_Subid());
			renewal.setRenewal_paidbyId(renewalReminder.getRenewal_paidbyId());
			renewal.setRenewal_approvedbyId(renewalReminder.getRenewal_approvedbyId());
			renewal.setVendorId(renewalReminder.getVendorId());
			renewal.setVendorName(renewalReminder.getVendorName());
			renewal.setTallyCompanyName(renewalReminder.getTallyCompanyName());
			renewal.setTallyCompanyId(renewalReminder.getTallyCompanyId());
			renewal.setIgnored(renewalReminder.isIgnored());
			renewal.setIgnoredRemark(renewalReminder.getIgnoredRemark());
			
			return renewal;
		}else {
			return renewal;
		}
	}

	public RenewalReminderDto Get_AppRoval_RenewalReminder_Upload_File(RenewalReminderDto renewalReminder) {

		RenewalReminderDto renewal = new RenewalReminderDto();

		renewal.setRenewal_id(renewalReminder.getRenewal_id());
		renewal.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
		renewal.setVid(renewalReminder.getVid());
		renewal.setVehicle_registration(renewalReminder.getVehicle_registration());
		renewal.setRenewal_type(renewalReminder.getRenewal_type());
		renewal.setRenewal_subtype(renewalReminder.getRenewal_subtype());

		renewal.setRenewal_from(dateFormat.format(renewalReminder.getRenewal_from_Date()));
		renewal.setRenewal_to(dateFormat.format(renewalReminder.getRenewal_To_Date()));
		renewal.setRenewal_receipt(renewalReminder.getRenewal_receipt());
		renewal.setRenewal_Amount(renewalReminder.getRenewal_Amount());
		renewal.setRenewal_dateofpayment(dateFormat.format(renewalReminder.getRenewal_payment_Date()));
		renewal.setRenewal_authorization(renewalReminder.getRenewal_authorization());
		renewal.setRenewal_number(renewalReminder.getRenewal_number());
		renewal.setRenewal_dateofRenewal(renewalReminder.getRenewal_dateofRenewal());
		renewal.setRenewal_paidby(renewalReminder.getRenewal_paidby());
		renewal.setRenewal_timethreshold(renewalReminder.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(renewalReminder.getRenewal_periedthreshold());

		renewal.setRenewal_document(renewalReminder.getRenewal_document());
		renewal.setRenewal_document_id(renewalReminder.getRenewal_document_id());

		renewal.setRenewal_staus_id(renewalReminder.getRenewal_staus_id());
		renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewalReminder.getRenewal_staus_id()));
		renewal.setRenewal_approvedID(renewalReminder.getRenewal_approvedID());
		// Create and Last updated Display
		renewal.setCreatedBy(renewalReminder.getCreatedBy());
		if (renewalReminder.getCreated() != null) {
			renewal.setCreated(CreatedDateTime.format(renewalReminder.getCreatedOn()));
		}
		renewal.setLastModifiedBy(renewalReminder.getLastModifiedBy());
		if (renewalReminder.getLastupdated() != null) {
			renewal.setLastupdated(CreatedDateTime.format(renewalReminder.getLastupdatedOn()));
		}

		return renewal;
	}

	/* delete the Renewal Reminder */
	public RenewalReminderDto deleteRenewalReminder(RenewalReminderDto renewalReminderDto) {

		RenewalReminderDto renewal = new RenewalReminderDto();

		renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
		renewal.setVid(renewalReminderDto.getVid());
		renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
		renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
		renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());

		try {
			// java.util.Date date =
			// dateFormat.parse(renewalReminderDto.getRenewal_from());
			// java.sql.Date FromDate = new Date(date.getTime());
			renewal.setRenewal_from(renewalReminderDto.getRenewal_from());
			// java.util.Date dateTo =
			// dateFormat.parse(renewalReminderDto.getRenewal_to());
			// java.sql.Date ToDate = new Date(dateTo.getTime());
			renewal.setRenewal_to(renewalReminderDto.getRenewal_to());
			// java.util.Date dateofpay =
			// dateFormat.parse(renewalReminderDto.getRenewal_dateofpayment());
			// java.sql.Date PayDate = new Date(dateofpay.getTime());
			renewal.setRenewal_dateofpayment(renewalReminderDto.getRenewal_dateofpayment());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renewal.setRenewal_receipt(renewalReminderDto.getRenewal_receipt());
		renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());
		renewal.setRenewal_paymentType(renewalReminderDto.getRenewal_paymentType());
		renewal.setRenewal_PayNumber(renewalReminderDto.getRenewal_PayNumber());
		renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());
		renewal.setRenewal_number(renewalReminderDto.getRenewal_number());
		renewal.setRenewal_dateofRenewal(renewalReminderDto.getRenewal_dayofRenewal());
		renewal.setRenewal_paidby(renewalReminderDto.getRenewal_paidby());
		renewal.setRenewal_timethreshold(renewalReminderDto.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(renewalReminderDto.getRenewal_periedthreshold());

		renewal.setRenewal_document(renewalReminderDto.getRenewal_document());

		return renewal;
	}

	/* delete the Renewal Reminder History 
	public RenewalReminderHistory deleteRenewalReminderHistory(RenewalReminderHistory renewalReminderDto) {

		RenewalReminderHistory renewal = new RenewalReminderHistory();

		renewal.setRenewalhis_id(renewalReminderDto.getRenewalhis_id());
		renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
		renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
		renewal.setRenewalhis_type(renewalReminderDto.getRenewalhis_type());
		renewal.setRenewalhis_subtype(renewalReminderDto.getRenewalhis_subtype());
		renewal.setRenewalhis_from(renewalReminderDto.getRenewalhis_from());
		renewal.setRenewalhis_to(renewalReminderDto.getRenewalhis_to());
		renewal.setRenewalhis_receipt(renewalReminderDto.getRenewalhis_receipt());
		renewal.setRenewalhis_Amount(renewalReminderDto.getRenewalhis_Amount());
		renewal.setRenewalhis_dateofpayment(renewalReminderDto.getRenewalhis_dateofpayment());
		renewal.setRenewalhis_paymentType(renewalReminderDto.getRenewalhis_paymentType());
		renewal.setRenewalhis_PayNumber(renewalReminderDto.getRenewalhis_PayNumber());
		renewal.setRenewalhis_authorization(renewalReminderDto.getRenewalhis_authorization());
		renewal.setRenewalhis_number(renewalReminderDto.getRenewalhis_number());

		renewal.setRenewalhis_dateofRenewal(renewalReminderDto.getRenewalhis_dateofRenewal());

		renewal.setRenewalhis_paidby(renewalReminderDto.getRenewalhis_paidby());
		renewal.setRenewalhis_timethreshold(renewalReminderDto.getRenewalhis_timethreshold());
		renewal.setRenewalhis_periedthreshold(renewalReminderDto.getRenewalhis_periedthreshold());

		renewal.setRenewal_document(renewalReminderDto.isRenewal_document());

		return renewal;
	}
*/
	/*
	 *****************************************************************************************
	 **********************************************************************************************
	 */

	/*// get logic in Renewal Reminder Information
	public RenewalReminderHistory prepareRenewalRemiderHistory(RenewalReminder renewalReminder) {

		RenewalReminderHistory renewal = new RenewalReminderHistory();
		// String[] From_TO_Array = null;

		renewal.setVid(renewalReminder.getVid());
		//renewal.setVehicle_registration(renewalReminder.getVehicle_registration());
		//renewal.setRenewalhis_type(renewalReminder.getRenewal_type());
		//renewal.setRenewalhis_subtype(renewalReminder.getRenewal_subtype());

		// String From_TO = renewalReminder.getRenewal_from();
		// From_TO_Array =From_TO.split(" to ");
		renewal.setRenewalhis_from(renewalReminder.getRenewal_from());
		renewal.setRenewalhis_to(renewalReminder.getRenewal_to());

		renewal.setRenewalhis_receipt(renewalReminder.getRenewal_receipt());
		renewal.setRenewalhis_Amount(renewalReminder.getRenewal_Amount());
		renewal.setRenewalhis_dateofpayment(renewalReminder.getRenewal_dateofpayment());
		//renewal.setRenewalhis_paymentType(renewalReminder.getRenewal_paymentType());
		renewal.setRenewalhis_PayNumber(renewalReminder.getRenewal_PayNumber());
		renewal.setRenewalhis_authorization(renewalReminder.getRenewal_authorization());
		renewal.setRenewalhis_number(renewalReminder.getRenewal_number());
		renewal.setRenewalhis_paidby(renewalReminder.getRenewal_paidby());
		renewal.setRenewalhis_timethreshold(renewalReminder.getRenewal_timethreshold());
		renewal.setRenewalhis_periedthreshold(renewalReminder.getRenewal_periedthreshold());
		renewal.setRenewalhis_dateofRenewal(renewalReminder.getRenewal_dateofRenewal());

		// Note: Old Revise Renewal Document Change to Renewal History Document
		renewal.setRenewal_document(renewalReminder.isRenewal_document());
		renewal.setRenewal_document_id(renewalReminder.getRenewal_document_id());

		java.util.Date currentDate = new java.util.Date();
		Timestamp crrenttoDate2 = new java.sql.Timestamp(currentDate.getTime());
		renewal.setCreated(crrenttoDate2);
		renewal.setLastupdated(crrenttoDate2);
		return renewal;
	}*/
	
	public RenewalReminderHistory prepareRenewalRemiderHistory(RenewalReminderDto renewalReminder) throws ParseException {

		RenewalReminderHistory renewal = new RenewalReminderHistory();
		// String[] From_TO_Array = null;

		renewal.setVid(renewalReminder.getVid());
		//renewal.setVehicle_registration(renewalReminder.getVehicle_registration());
		renewal.setRenewalhis_typeId(renewalReminder.getRenewalTypeId());
		renewal.setRenewalhis_subtypeId(renewalReminder.getRenewal_Subid());

		// String From_TO = renewalReminder.getRenewal_from();
		// From_TO_Array =From_TO.split(" to ");
		renewal.setRenewalhis_from(renewalReminder.getRenewal_from_Date());
		renewal.setRenewalhis_to(renewalReminder.getRenewal_To_Date());

		renewal.setRenewalhis_receipt(renewalReminder.getRenewal_receipt());
		renewal.setRenewalhis_Amount(renewalReminder.getRenewal_Amount());
		renewal.setRenewalhis_dateofpayment(renewalReminder.getRenewal_payment_Date());
		renewal.setRenewalhis_paymentTypeId(renewalReminder.getPaymentTypeId());
		renewal.setRenewalhis_PayNumber(renewalReminder.getRenewal_PayNumber());
		renewal.setRenewalhis_authorization(renewalReminder.getRenewal_authorization());
		renewal.setRenewalhis_number(renewalReminder.getRenewal_number());
		renewal.setRenewalhis_paidbyId(renewalReminder.getRenewal_paidbyId());
		renewal.setRenewalhis_timethreshold(renewalReminder.getRenewal_timethreshold());
		renewal.setRenewalhis_periedthreshold(renewalReminder.getRenewal_periedthreshold());
		if(renewalReminder.getRenewal_dateofRenewal() != null)
		 renewal.setHis_dateofRenewal(dateFormat.parse(renewalReminder.getRenewal_dateofRenewal()));

		// Note: Old Revise Renewal Document Change to Renewal History Document
		renewal.setRenewal_document(renewalReminder.getRenewal_document());
		renewal.setRenewal_document_id(renewalReminder.getRenewal_document_id());

		java.util.Date currentDate = new java.util.Date();
		Timestamp crrenttoDate2 = new java.sql.Timestamp(currentDate.getTime());
		renewal.setCreated(crrenttoDate2);
		renewal.setLastupdated(crrenttoDate2);
		return renewal;
	}

	/* list of Renewal Remindar History */
	public List<RenewalReminderHistoryDto> prepareListofRenewalHistoryDto(
			List<RenewalReminderHistoryDto> renewalReminder) {
		List<RenewalReminderHistoryDto> Dtos = null;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderHistoryDto>();
			RenewalReminderHistoryDto renewal = null;
			for (RenewalReminderHistoryDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderHistoryDto();
				renewal.setRenewalhis_id(renewalReminderDto.getRenewalhis_id());
				renewal.setVid(renewalReminderDto.getVid());
				renewal.setVehicle_registration(renewalReminderDto.getVehicle_registration());
				renewal.setRenewalhis_subtype(renewalReminderDto.getRenewalhis_subtype());
				renewal.setRenewalhis_type(renewalReminderDto.getRenewalhis_type());
				if (renewalReminderDto.getRenewalhis_fromDate() != null) {
					renewal.setRenewalhis_from(dateFormat_Name.format(renewalReminderDto.getRenewalhis_fromDate()));

				}

				if (renewalReminderDto.getRenewalhis_toDate() != null) {
					renewal.setRenewalhis_to(dateFormat_Name.format(renewalReminderDto.getRenewalhis_toDate()));
				}
				renewal.setRenewalhis_receipt(renewalReminderDto.getRenewalhis_receipt());
				renewal.setRenewalhis_Amount(renewalReminderDto.getRenewalhis_Amount());
				renewal.setRenewalhis_PayNumber(renewalReminderDto.getRenewalhis_PayNumber());
				renewal.setRenewal_document(renewalReminderDto.isRenewal_document());
				renewal.setRenewal_document_id(renewalReminderDto.getRenewal_document_id());
				renewal.setRenewalhis_number(renewalReminderDto.getRenewalhis_number());

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	public String time_DateDifferent(java.util.Date date, java.util.Date currentDate) {

		// in milliseconds
		long timeDifferenceMilliseconds = date.getTime() - currentDate.getTime();

		// long diffSeconds = timeDifferenceMilliseconds / 1000;
		long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
		long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
		long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
		long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
		long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
		long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

		if (diffMinutes > 0) {
			if (diffHours < 1) {
				return diffMinutes + " minutes from now";
			} else if (diffDays < 1) {
				return diffHours + " hours from now";
			} else if (diffWeeks < 1) {
				return diffDays + " days from now";
			} else if (diffMonths < 1) {
				return diffWeeks + " weeks from now";
			} else if (diffYears < 1) {
				return diffMonths + " months from now";
			} else {
				return diffYears + " years from now";
			}
		} else {
			if (-diffHours < 1) {
				return -diffMinutes + " minutes ago";
			} else if (-diffDays < 1) {
				return -diffHours + " hours ago";
			} else if (-diffWeeks < 1) {
				return -diffDays + " days ago";
			} else if (-diffMonths < 1) {
				return -diffWeeks + " weeks ago";
			} else if (-diffYears < 1) {
				return -diffMonths + " months ago";
			} else {
				return -diffYears + " years ago";
			}
		}
	}
	
	public String time_DateDifferentAjax(java.util.Date date, java.util.Date currentDate) {

		// in milliseconds
		long timeDifferenceMilliseconds = date.getTime() - currentDate.getTime();

		// long diffSeconds = timeDifferenceMilliseconds / 1000;
		long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
		long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
		long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
		long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
		long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
		long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

		if (diffMinutes > 0) {
			if (diffHours < 1) {
				return "<span style=\"color: #06b4ff;\">"+diffMinutes + " minutes from now </span>";
			} else if (diffDays < 1) {
				return "<span style=\"color: #06b4ff;\">"+diffHours + " hours from now </span>";
			} else if (diffWeeks < 1) {
				return "<span style=\"color: #06b4ff;\">"+diffDays + " days from now </span>";
			} else if (diffMonths < 1) {
				return "<span style=\"color: #06b4ff;\">"+diffWeeks + " weeks from now </span>";
			} else if (diffYears < 1) {
				return "<span style=\"color: #06b4ff;\">"+diffMonths + " months from now </span>";
			} else {
				return "<span style=\"color: #06b4ff;\">"+diffYears + " years from now </span>";
			}
		} else {
			if (-diffHours < 1) {
				return "<span style=\"color: red;\">"+-diffMinutes + " minutes ago </span>";
			} else if (-diffDays < 1) {
				return "<span style=\"color: red;\">"+-diffHours + " hours ago </span>";
			} else if (-diffWeeks < 1) {
				return "<span style=\"color: red;\">"+-diffDays + " days ago </span>";
			} else if (-diffMonths < 1) {
				return "<span style=\"color: red;\">"+-diffWeeks + " weeks ago </span>";
			} else if (-diffYears < 1) {
				return "<span style=\"color: red;\">"+-diffMonths + " months ago </span>";
			} else {
				return "<span style=\"color: red;\">"+-diffYears + " years ago </span>";
			}
		}
	}

	/**
	 * @param searchDate
	 * @return
	 */
	public RenewalReminderApproval prepare_RenewalReminderApproval(String searchDate) {

		RenewalReminderApproval Approval = new RenewalReminderApproval();

		Approval.setApproval_document_id((long) 0);
		Approval.setApproval_document(false);
		Approval.setApprovalStatusId(RenewalApprovalStatus.OPEN);
	//	Approval.setApproval_Status(RenewalApprovalStatus.OPEN_NAME);

		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		java.util.Date currentDate = new java.util.Date();
		Timestamp approvalCreated_Date = new java.sql.Timestamp(currentDate.getTime());

		Approval.setApprovalCreated_ById(userDetails.getId());
		Approval.setApprovalCreated_Date(approvalCreated_Date);
		Approval.setApprovalPayment_Amount(0.0);
		Approval.setApprovalApproved_Amount(0.0);
		Approval.setApprovalPending_Amount(0.0);
		//Approval.setApprovalPayment_By(null);
		Approval.setApprovalPayment_Date(approvalCreated_Date);

		Approval.setCreatedById(userDetails.getId());
		Approval.setCreated(approvalCreated_Date);
		Approval.setLastModifiedById(userDetails.getId());
		Approval.setLastupdated(approvalCreated_Date);
		Approval.setMarkForDelete(false);
		Approval.setCompanyId(userDetails.getCompany_id());
		
		

		return Approval;
	}

	/**
	 * @param oldRenewal
	 * @return
	 */
	public RenewalReminder prepare_Approval_to_RenewalRemider(RenewalReminderDto oldRenewal) {
		// Note: RenewalReminder to Approval Entries Details

		RenewalReminder renewal = new RenewalReminder();

		renewal.setVid(oldRenewal.getVid());
		// show Vehicle name
		//renewal.setVehicle_registration(oldRenewal.getVehicle_registration());

		try {

			// Note: here Old Renewal Reminder to Get Renewal_to add One to next
			// Date
			// Ex: 16-08-2017 for Old Renewal_to Add one date 17-08-2017
			// this Date is new Approval Renewal_from date
			java.util.Date date = Change_CurrentDate_To_Add_NumberofDate_InReal_Calendar(
					dateFormat.format(oldRenewal.getRenewal_To_Date()), 1);

			// System.out.println("From" + date);

			renewal.setRenewal_from(date);
			renewal.setRenewal_dateofpayment(date);

			long DifferenceOldDate = oldRenewal.getRenewal_To_Date().getTime() - oldRenewal.getRenewal_from_Date().getTime();
			Integer Diff = (int) TimeUnit.DAYS.convert(DifferenceOldDate, TimeUnit.MILLISECONDS);
			
			
			java.util.Date dateTo = Change_CurrentDate_To_Add_NumberofDate_InReal_Calendar(
					dateFormat.format(oldRenewal.getRenewal_To_Date()), 1 + Diff);
			
			// System.out.println("toDate" + dateTo);
			
			Calendar	calendar	= new GregorianCalendar();
			calendar.setTime(dateTo);
			calendar.add(Calendar.HOUR, 23);
			calendar.add(Calendar.MINUTE, 59);
			calendar.add(Calendar.SECOND, 59);
			
			renewal.setRenewal_to(calendar.getTime());

			Integer duetimeandperied;
			// calculation of the reminder before date of reminder
			Integer timeandperied = 0;
			if(oldRenewal.getRenewal_timethreshold() != null && oldRenewal.getRenewal_periedthreshold() != null)
				timeandperied = (oldRenewal.getRenewal_timethreshold()) * (oldRenewal.getRenewal_periedthreshold());


			if (timeandperied == 0) {
				duetimeandperied = oldRenewal.getRenewal_timethreshold(); // 3
																			// days
			} else {
				duetimeandperied = timeandperied; // 21 days
			}

			String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(dateFormat.format(dateTo),
					duetimeandperied);

			// System.out.println("reminder_dateof" + reminder_dateof);
			// save renewal date
			//renewal.setRenewal_dateofRenewal(reminder_dateof);
			renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));

		} catch (Exception e) {
			System.err.println("Exception "+e);
			e.printStackTrace();
		}
		//renewal.setRenewal_type(oldRenewal.getRenewal_type());
		//renewal.setRenewal_subtype(oldRenewal.getRenewal_subtype());

		renewal.setRenewal_receipt(null);
		renewal.setRenewal_Amount(oldRenewal.getRenewal_Amount());

		//renewal.setRenewal_paymentType(null);
		renewal.setRenewal_PayNumber(null);
		renewal.setRenewal_authorization(oldRenewal.getRenewal_authorization());
		renewal.setRenewal_number(null);
		renewal.setRenewal_paidbyId(null);
		renewal.setRenewal_timethreshold(oldRenewal.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(oldRenewal.getRenewal_periedthreshold());
		/* due end date */

		/** Set Status in Issues */
		renewal.setMarkForDelete(false);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/** Set Created by email Id */
		renewal.setCreatedById(userDetails.getId());
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());


		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);

		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);

		renewal.setRenewal_document(false);
		
		renewal.setRenewal_staus_id(RenealReminderStatus.OPEN);
		/** Set Status Not Approved Date */
		//renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(RenealReminderStatus.OPEN));
		
		renewal.setRenewalTypeId(oldRenewal.getRenewalTypeId());
		renewal.setRenewal_Subid(oldRenewal.getRenewal_Subid());

		return renewal;

	}

	public Date Change_CurrentDate_To_Add_NumberofDate_InReal_Calendar(String duedate, Integer duetimeandperied) {

		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		
		
		String due_EndDate[] = duedate.split("-");

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
		// System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, +duetimeandperied);
	/*	due_endcalender.add(Calendar.HOUR, +23);
		due_endcalender.add(Calendar.MINUTE, +59);
		due_endcalender.add(Calendar.SECOND, +59);*/
		// System.out.println(duetimeandperied+"days ago: " +
		// due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		// Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		java.util.Date add_date = due_endcalender.getTime();

		java.sql.Date fromDate = new Date(add_date.getTime());

		return fromDate;
	}

	public String Change_CurrentDate_To_RenewalDate_SubTrackDate(String duedate, Integer duetimeandperied) {

		// get the date from database is 10-12-2015
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		String due_EndDate[] = duedate.split("-");

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
		// System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, -duetimeandperied);
		// System.out.println(duetimeandperied+"days ago: " +
		// due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		String reminder_dateof = "" + due_endcalender.get(Calendar.DATE) + "-" + month + "-"
				+ due_endcalender.get(Calendar.YEAR);

		return reminder_dateof;
	}

	/**
	 * @param find_listRenewalReminderApproval
	 * @return
	 */
	public List<RenewalReminderApprovalDto> Only_Show_ListofRenewalReminderApprovalDto(
			List<RenewalReminderApprovalDto> RenewalReminderApproval) {
		List<RenewalReminderApprovalDto> Dtos = null;
		if (RenewalReminderApproval != null && !RenewalReminderApproval.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderApprovalDto>();
			RenewalReminderApprovalDto renewal = null;
			for (RenewalReminderApprovalDto renewalReminderDto : RenewalReminderApproval) {

				renewal = new RenewalReminderApprovalDto();

				renewal.setRenewalApproval_id(renewalReminderDto.getRenewalApproval_id());
				renewal.setRenewalApproval_Number(renewalReminderDto.getRenewalApproval_Number());
				renewal.setApprovalCreated_By(renewalReminderDto.getApprovalCreated_By());
				renewal.setApprovalPayment_Amount(renewalReminderDto.getApprovalPayment_Amount());
				renewal.setApproval_document_id(renewalReminderDto.getApproval_document_id());
				renewal.setApproval_document(renewalReminderDto.isApproval_document());
				if (renewalReminderDto.getApprovalCreated_DateOn() != null) {
					renewal.setApprovalCreated_Date(
							dateFormat_Name.format(renewalReminderDto.getApprovalCreated_DateOn()));
				}
				renewal.setApprovalStatusId(renewalReminderDto.getApprovalStatusId());
				renewal.setApproval_Status(RenewalApprovalStatus.getRenewalStatusName(renewalReminderDto.getApprovalStatusId()));
				renewal.setPaymentTypeId(renewalReminderDto.getPaymentTypeId());
				renewal.setApprovalPayment_Type(PaymentTypeConstant.getPaymentTypeName(renewalReminderDto.getPaymentTypeId()));
				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/**
	 * @param inven
	 * @return
	 */
	public Double[] Total_Part_AVAILABLE_Inventory_Quantity(List<InventoryDto> inven) {

		Double[] Total = new Double[2];
		Double TotalQuantity = 0.0, TotalHistoryQuantity = 0.0;
		if (inven != null && !inven.isEmpty()) {
			for (InventoryDto quantity : inven) {
				if (quantity.getQuantity() != null) {
					TotalQuantity += quantity.getQuantity();
				}

				if (quantity.getHistory_quantity() != null) {
					TotalHistoryQuantity += quantity.getHistory_quantity();
				}
			}

			Total[0] = round(TotalQuantity, 2);
			Total[1] = round(TotalHistoryQuantity, 2);
		}
		return Total;
	}
	
	
	public Double Total_PartInventoryTranfer_Quantity(List<InventoryTransferDto> inventoryQuantity) {
		Double TotalQuantity = 0.0;
		if (inventoryQuantity != null && !inventoryQuantity.isEmpty()) {
			for (InventoryTransferDto quantity : inventoryQuantity) {
				if (quantity.getTransfer_quantity() != null) {
					TotalQuantity += quantity.getTransfer_quantity();
				}
			}
		}

		return TotalQuantity;
	}
	
	public RenewalReminderApprovalDto getApprovalDetails(RenewalReminderApprovalDto renewalReminderApprovalDto) {
		RenewalReminderApprovalDto renewal = new RenewalReminderApprovalDto();
		if(renewalReminderApprovalDto != null) {
			
			
			renewal.setRenewalApproval_id(renewalReminderApprovalDto.getRenewalApproval_id());
			renewal.setRenewalApproval_Number(renewalReminderApprovalDto.getRenewalApproval_Number());
			renewal.setApprovalCreated_By(renewalReminderApprovalDto.getApprovalCreated_By());
			renewal.setApprovalCreated_ById(renewalReminderApprovalDto.getApprovalCreated_ById());
			if(renewalReminderApprovalDto.getApprovalCreated_DateOn() != null)
				renewal.setApprovalCreated_Date(dateFormat_Name.format(renewalReminderApprovalDto.getApprovalCreated_DateOn()));
			renewal.setPaymentTypeId(renewalReminderApprovalDto.getPaymentTypeId());
			renewal.setApprovalPayment_Type(PaymentTypeConstant.getPaymentTypeName(renewalReminderApprovalDto.getPaymentTypeId()));
			renewal.setApprovalStatusId(renewalReminderApprovalDto.getApprovalStatusId());
			renewal.setApproval_Status(RenewalApprovalStatus.getRenewalStatusName(renewalReminderApprovalDto.getApprovalStatusId()));
			renewal.setApprovalPay_Number(renewalReminderApprovalDto.getApprovalPay_Number());
			renewal.setApprovalPayment_By(renewalReminderApprovalDto.getApprovalPayment_By());
			renewal.setApprovalPayment_ById(renewalReminderApprovalDto.getApprovalPayment_ById());
			if(renewalReminderApprovalDto.getApprovalPayment_DateOn() != null)
				renewal.setApprovalPayment_Date(dateFormat_Name.format(renewalReminderApprovalDto.getApprovalPayment_DateOn()));
			renewal.setApprovalPayment_Amount(renewalReminderApprovalDto.getApprovalPayment_Amount());
			renewal.setApprovalApproved_Amount(renewalReminderApprovalDto.getApprovalApproved_Amount());
			renewal.setApprovalPending_Amount(renewalReminderApprovalDto.getApprovalPending_Amount());
			renewal.setApprovalCancel_Amount(renewalReminderApprovalDto.getApprovalCancel_Amount());
			renewal.setApproval_document(renewalReminderApprovalDto.isApproval_document());
			renewal.setApproval_document_id(renewalReminderApprovalDto.getApproval_document_id());
			renewal.setCreatedBy(renewalReminderApprovalDto.getCreatedBy());
			renewal.setLastModifiedBy(renewalReminderApprovalDto.getLastModifiedBy());
			renewal.setCreatedById(renewalReminderApprovalDto.getCreatedById());
			renewal.setLastModifiedById(renewalReminderApprovalDto.getLastModifiedById());
			if(renewalReminderApprovalDto.getCreatedOn() != null)
				renewal.setCreated(CreatedDateTime.format(renewalReminderApprovalDto.getCreatedOn()));
			if(renewalReminderApprovalDto.getLastupdatedOn() != null)
				renewal.setLastupdated(CreatedDateTime.format(renewalReminderApprovalDto.getLastupdatedOn()));
		}
				
		return renewal;
	
	}

	public org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory getRenewalDocHistory(org.fleetopgroup.persistence.document.RenewalReminderDocument	reminderDocument, RenewalReminder renewalReminder) {
		org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory	rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory();
		
		rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
		rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
		
		try {

			rewalDoc.setRenewal_filename(reminderDocument.getRenewal_filename());
			rewalDoc.setRenewal_content(reminderDocument.getRenewal_content());
			rewalDoc.setRenewal_contentType(reminderDocument.getRenewal_contentType());

			/** Set Status in Issues */
			rewalDoc.setMarkForDelete(false);

			// when Add Renewal Reminder to file Renewal
			// Reminder History id is null
			rewalDoc.setRenewalHis_id((long) 0);

			/** Set Created by email Id */
			rewalDoc.setCreatedById(reminderDocument.getCreatedById());
			rewalDoc.setLastModifiedById(reminderDocument.getLastModifiedById());
			rewalDoc.setCompanyId(reminderDocument.getCompanyId());

			/** Set Created Current Date */
			rewalDoc.setCreated(reminderDocument.getCreated());
			rewalDoc.setLastupdated(reminderDocument.getLastupdated());
		
			return rewalDoc;
		}catch (Exception e) {
			throw e;
		}
	}
	
	
	public Long  Only_Show_ListofRenewalOverdue(List<RenewalReminderDto> renewalReminder) {
		List<RenewalReminderDto> Dtos = null;
		long finalCount = 0;
		if (renewalReminder != null && !renewalReminder.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			int count = 1;
			for (RenewalReminderDto renewalReminderDto : renewalReminder) {
				renewal = new RenewalReminderDto();
				
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				String diffenceThrsholdOdometer = null;
				
				try {
					java.util.Date date = dateFormat.parse(renewalReminderDto.getRenewal_dateofRenewal());
					java.sql.Date RenewalDueDate = new Date(date.getTime());

					// Due soon message in Time interval
					if (RenewalDueDate != null) {
						
						int diffInDays_threshold = (int) ((toDate.getTime() - RenewalDueDate.getTime())
								/ (1000 * 60 * 60 * 24));

						switch (diffInDays_threshold) {

						case 0:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {
								
								int diffInTimeServicethreshold = (int) ((renewalReminderDto.getRenewal_To_Date().getTime()
										- RenewalDueDate.getTime()) / (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
									finalCount = count++;
								}

							}
							break;
						}
						if(renewalReminderDto.isNewRRCreated()) {
							diffenceThrsholdOdometer	= "New created";
						}
						renewal.setRenewal_dueRemDate(diffenceThrsholdOdometer);
					}
					renewal.setVehicleGroup(renewalReminderDto.getVehicleGroup());

				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Dtos.add(renewal);
			}
			
		}
		return finalCount;
	}
	
	public RenewalReminder saveRRDetailsFromMobile(ValueObject object) throws Exception {
		
		RenewalReminder 	renewal 	 = new RenewalReminder();
		CustomUserDetails 	userDetails  = 	(CustomUserDetails) object.get("userDetails");	

		renewal.setVid(object.getInt("vehicleId"));
		renewal.setRenewalTypeId(object.getInt("renewalTypeId"));
		renewal.setRenewal_Subid(object.getInt("renewalSubTypeId"));
		renewal.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
		renewal.setPaymentTypeId(object.getShort("paymentTypeId"));
		renewal.setRenewal_receipt(object.getString("renewalReceiptNo","0"));
		renewal.setRenewal_Amount(object.getDouble("renewalAmount"));
		renewal.setVendorId(object.getInt("vendorId",0));

		java.util.Date date = dateFormat.parse(object.getString("validityFrom"));
		java.sql.Date fromDate = new Date(date.getTime());
		renewal.setRenewal_from(fromDate);
		renewal.setRenewal_to(sqlFormatTime.parse(object.getString("validityTo") + DateTimeUtility.DAY_END_TIME));
			
		if(object.getString("paidDate") != null && object.getString("paidDate").equals("")) {
		java.util.Date datePay = dateFormat.parse(object.getString("paidDate"));
		java.sql.Date dateofPay = new Date(datePay.getTime());
		renewal.setRenewal_dateofpayment(dateofPay);
		} else {
			renewal.setRenewal_dateofpayment(fromDate);
		}
		
		renewal.setRenewal_PayNumber(object.getString("renewalPayNo","0"));
		renewal.setRenewal_authorization(object.getString("renewalAuthorization","-"));
		renewal.setRenewal_number(object.getString("remark","-"));
		
		if(object.getString("renewalPaidById") != null && !object.getString("renewalPaidById").trim().equals("")) {
			renewal.setRenewal_paidbyId(object.getLong("renewalPaidById",0));
		}
		
		renewal.setRenewal_timethreshold(object.getInt("timeThreshold",1));
		renewal.setRenewal_periedthreshold(object.getInt("renewalPeriodThreshold",0));
		
		Integer duetimeandperied;
		String  duedate       = object.getString("validityTo");
		Integer timeandperied = (renewal.getRenewal_timethreshold()	* renewal.getRenewal_periedthreshold());


		if (timeandperied == 0) {
			duetimeandperied = renewal.getRenewal_timethreshold(); 
		} else {
			duetimeandperied = timeandperied; 
		}

		String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
		renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));
		
		renewal.setCreatedById(userDetails.getId());
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());
		renewal.setMarkForDelete(false);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);
		renewal.setRenewal_approvedID((long) 0);
		
		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);
		renewal.setTallyCompanyId(object.getLong("tallyCompanyId",0));

		return renewal;
	}
	
	public RenewalReminder updateRenewalReminderDetails(ValueObject object) throws Exception {
		
		RenewalReminder 	renewal 	 = new RenewalReminder();
		CustomUserDetails 	userDetails  = 	(CustomUserDetails) object.get("userDetails");
		
		renewal.setRenewal_id(object.getLong("renewal_id"));
		renewal.setVid(object.getInt("vehicleId"));
		renewal.setRenewalTypeId(object.getInt("renewalTypeId"));
		renewal.setRenewal_Subid(object.getInt("renewalSubTypeId"));
		if(object.getBoolean("createApproval", false) == true) {
			renewal.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
		}else {
			renewal.setRenewal_staus_id(RenealReminderStatus.APPROVED);
		}
		renewal.setPaymentTypeId(object.getShort("paymentTypeId"));
		renewal.setRenewal_receipt(object.getString("renewalReceiptNo","0"));
		renewal.setRenewal_Amount(object.getDouble("renewalAmount"));
		renewal.setVendorId(object.getInt("vendorId",0));

		java.util.Date date = dateFormat.parse(object.getString("validityFrom"));
		java.sql.Date fromDate = new Date(date.getTime());
		renewal.setRenewal_from(fromDate);
		renewal.setRenewal_to(sqlFormatTime.parse(object.getString("validityTo") + DateTimeUtility.DAY_END_TIME));
			
		if(object.getString("paidDate") != null && object.getString("paidDate") != "") {
		java.util.Date datePay = dateFormat.parse(object.getString("paidDate"));
		java.sql.Date DateofPay = new Date(datePay.getTime());
		renewal.setRenewal_dateofpayment(DateofPay);
		} else {
			renewal.setRenewal_dateofpayment(fromDate);
		}
		
		renewal.setRenewal_PayNumber(object.getString("renewalPayNo","0"));
		renewal.setRenewal_authorization(object.getString("renewalAuthorization","-"));
		renewal.setRenewal_number(object.getString("remark","-"));
		
		if(object.getString("renewalPaidById") != null && !object.getString("renewalPaidById").trim().equals("")) {
			renewal.setRenewal_paidbyId(object.getLong("renewalPaidById",0));
		}
		
		renewal.setRenewal_timethreshold(object.getInt("timeThreshold",1));
		renewal.setRenewal_periedthreshold(object.getInt("renewalPeriodThreshold",0));
		
		Integer duetimeandperied;
		String  duedate       = object.getString("validityTo");
		Integer timeandperied = (renewal.getRenewal_timethreshold()	* renewal.getRenewal_periedthreshold());

		// timeandperied=3*0 days; or timeandperied=3*7 weeks or timeandperied=3*28 month

		if (timeandperied == 0) {
			duetimeandperied = renewal.getRenewal_timethreshold(); 
		} else {
			duetimeandperied = timeandperied; 
		}

		String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
		renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));
		
		renewal.setCreatedById(userDetails.getId());
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());
		renewal.setMarkForDelete(false);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);
		renewal.setRenewal_approvedID((long) 0);
		
		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);
		renewal.setTallyCompanyId(object.getLong("tallyCompanyId",0));

		return renewal;
	}
}