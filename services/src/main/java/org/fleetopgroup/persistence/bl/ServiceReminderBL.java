package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.EmailAlertQueueDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.web.util.DateTimeUtility;

public class ServiceReminderBL {

	public ServiceReminderBL() {
	}

	RenewalReminderBL RRBL = new RenewalReminderBL();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	
	SimpleDateFormat 	dateFormatTime 			= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	
	public List<ServiceReminderDto> prepareListofServiceReminder(List<ServiceReminderDto> ServiceReminder) {
		List<ServiceReminderDto> Dtos = null;
		if (ServiceReminder != null && !ServiceReminder.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto Service = null;
			for (ServiceReminderDto ServiceReminderDto : ServiceReminder) {
				Service = new ServiceReminderDto();

				Service.setService_id(ServiceReminderDto.getService_id());
				Service.setService_Number(ServiceReminderDto.getService_Number());
				Service.setVid(ServiceReminderDto.getVid());
				Service.setVehicle_registration(ServiceReminderDto.getVehicle_registration());
				Service.setVehicle_Group(ServiceReminderDto.getVehicle_Group());
				Service.setService_subtype(ServiceReminderDto.getService_subtype());
				Service.setService_type(ServiceReminderDto.getService_type());

				Service.setMeter_interval(ServiceReminderDto.getMeter_interval());
				Service.setTime_interval(ServiceReminderDto.getTime_interval());
				Service.setTime_intervalperiod(ServiceReminderDto.getTime_intervalperiod());

				Service.setService_subscribeduser_name(ServiceReminderDto.getService_subscribeduser_name());
				Service.setService_subscribeduser(ServiceReminderDto.getService_subScribedUserId());
				Service.setService_subScribedUserId(ServiceReminderDto.getService_subScribedUserId());
				Service.setServicestatus(ServiceReminderDto.getServicestatus());

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (ServiceReminderDto.getTime_servicedate() != null) {


					// System.out.println( diffenceDays);
					Service.setDiffrent_time_days(
							RRBL.time_DateDifferent(ServiceReminderDto.getTime_servicedate(), toDate));
				}

				try {
					Integer vehicleCurrentOdometer = 0;
					if (ServiceReminderDto.getVehicle_currentOdometer() != null) {
						vehicleCurrentOdometer = ServiceReminderDto.getVehicle_currentOdometer();
					}

					Integer Current_vehicleOdmeter = vehicleCurrentOdometer;
					// System.out.println(Current_vehicleOdmeter);
					Integer serviceReminder = 0;
					if (ServiceReminderDto.getMeter_serviceodometer() != null) {
						serviceReminder = ServiceReminderDto.getMeter_serviceodometer();
					}

					Integer diff_Odmeter = serviceReminder - Current_vehicleOdmeter;

					String diffenceOdometer = null;

					switch (diff_Odmeter) {
					case 0:
						diffenceOdometer = ("Current Km Today");
						break;
					case -1:
						diffenceOdometer = (-diff_Odmeter + " Km ago");
						break;
					case 1:
						diffenceOdometer = (diff_Odmeter + " km from now");
						break;
					default:
						if (diff_Odmeter < -1) {

							diffenceOdometer = (-diff_Odmeter + " Km ago");

						} else if (diff_Odmeter > 1) {

							diffenceOdometer = (diff_Odmeter + " km from now");
						}
						break;
					}

					Service.setDiffrent_meter_oddmeter(diffenceOdometer);

					// Overdue and Due soon message code logic

					String diffenceThrsholdOdometer = null;

					if (ServiceReminderDto.getMeter_servicethreshold_odometer() != null) {

						// eg: current 1000 -1500 = -500
						Integer currentThresholdDiff = Current_vehicleOdmeter
								- ServiceReminderDto.getMeter_servicethreshold_odometer();
						Integer meter_Threshold = 0;
						if (ServiceReminderDto.getMeter_threshold() != null) {
							meter_Threshold = ServiceReminderDto.getMeter_threshold();
						}

						// System.out.println("odo"+currentThresholdDiff);
						switch (currentThresholdDiff) {
						/*
						 * case 0: diffenceThrsholdOdometer = "Due Soon"; break;
						 */
						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;

						default:
							if (currentThresholdDiff > 1) {
								if (currentThresholdDiff < meter_Threshold) {
									diffenceThrsholdOdometer = "Due Soon";
								} else if (currentThresholdDiff == meter_Threshold) {
									diffenceThrsholdOdometer = "Today";
								} else {
									diffenceThrsholdOdometer = "Overdue";
								}

							}
							break;
						}
						// System.out.println("odo "+diffenceThrsholdOdometer);
						Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
					}

					// Due soon message in Time interval
					if (ServiceReminderDto.getTime_servicethreshold_date() != null) {

						int diffInDays_threshold = (int) ((toDate.getTime()
								- ServiceReminderDto.getTime_servicethreshold_date().getTime())
								/ (1000 * 60 * 60 * 24));

						// System.out.println("time "+diffInDays_threshold);

						switch (diffInDays_threshold) {
						/*
						 * case 0: diffenceThrsholdOdometer = "Due Soon"; break;
						 */
						case 1:
							diffenceThrsholdOdometer = "Due Soon";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((ServiceReminderDto.getTime_servicedate()
										.getTime() - ServiceReminderDto.getTime_servicethreshold_date().getTime())
										/ (1000 * 60 * 60 * 24));

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
						Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

				Dtos.add(Service);
			}
		}
		return Dtos;
	}
	
	public List<ServiceReminderDto> prepareListofServiceReminderAjax(List<ServiceReminderDto> ServiceReminder) {
		List<ServiceReminderDto> Dtos = null;
		if (ServiceReminder != null && !ServiceReminder.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto Service = null;
			for (ServiceReminderDto ServiceReminderDto : ServiceReminder) {
				Service = new ServiceReminderDto();
				Service.setService_id(ServiceReminderDto.getService_id());
				Service.setService_Number(ServiceReminderDto.getService_Number());
				Service.setVid(ServiceReminderDto.getVid());
				Service.setVehicle_registration(ServiceReminderDto.getVehicle_registration());
				Service.setVehicle_Group(ServiceReminderDto.getVehicle_Group());
				Service.setService_subtype(ServiceReminderDto.getService_subtype());
				Service.setService_type(ServiceReminderDto.getService_type());

				Service.setMeter_interval(ServiceReminderDto.getMeter_interval());
				Service.setTime_interval(ServiceReminderDto.getTime_interval());
				Service.setTime_intervalperiod(ServiceReminderDto.getTime_intervalperiod());

				Service.setService_subscribeduser_name(ServiceReminderDto.getService_subscribeduser_name());
				Service.setService_subscribeduser(ServiceReminderDto.getService_subScribedUserId());
				Service.setService_subScribedUserId(ServiceReminderDto.getService_subScribedUserId());
				Service.setServicestatus(ServiceReminderDto.getServicestatus());
				Service.setService_remider_howtimes(ServiceReminderDto.getService_remider_howtimes());
				Service.setServiceProgramId(ServiceReminderDto.getServiceProgramId());
				Service.setServiceScheduleId(ServiceReminderDto.getServiceScheduleId());
				Service.setServiceSchedule(ServiceReminderDto.getService_type()+"_"+ServiceReminderDto.getService_subtype());
				Service.setServiceProgram(ServiceReminderDto.getServiceProgram());
				Service.setVehicle_currentOdometer(ServiceReminderDto.getVehicle_currentOdometer());
				Service.setMeter_serviceodometer(ServiceReminderDto.getMeter_serviceodometer());
				Service.setMeter_threshold(ServiceReminderDto.getMeter_threshold());
				Service.setMeter_servicethreshold_odometer(ServiceReminderDto.getMeter_servicethreshold_odometer());
				Service.setSkip(ServiceReminderDto.isSkip());
				if(Service.getServiceProgram() == null) {
					Service.setServiceProgram("No Program");
				}
				
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (ServiceReminderDto.getTime_servicedate() != null) {

					// System.out.println( diffenceDays);
					if(ServiceReminderDto.getTime_interval() > 0) {
						Service.setDiffrent_time_days(
								RRBL.time_DateDifferentAjax(ServiceReminderDto.getTime_servicedate(), toDate));
					}else {
						Service.setDiffrent_time_days("");
					}
					
				}

				try {
					Integer vehicleCurrentOdometer = 0;
					if (ServiceReminderDto.getVehicle_currentOdometer() != null) {
						vehicleCurrentOdometer = ServiceReminderDto.getVehicle_currentOdometer();
					}

					Integer Current_vehicleOdmeter = vehicleCurrentOdometer;
					// System.out.println(Current_vehicleOdmeter);
					Integer serviceReminder = 0;
					if (ServiceReminderDto.getMeter_serviceodometer() != null) {
						serviceReminder = ServiceReminderDto.getMeter_serviceodometer();
					}

					Integer diff_Odmeter = serviceReminder - Current_vehicleOdmeter;

					String diffenceOdometer = null;

					switch (diff_Odmeter) {
					case 0:
						diffenceOdometer = ("<span style=\"color: red;\">Current Km Today</span>");
						break;
					case -1:
						diffenceOdometer = ("<span style=\"color: red;\">"+-diff_Odmeter + " Km ago </span>");
						break;
					case 1:
						diffenceOdometer = ("<span style=\"color: #06b4ff;\">"+diff_Odmeter + " km from now");
						break;
					default:
						if (diff_Odmeter < -1) {

							diffenceOdometer = ("<span style=\"color: red;\">"+-diff_Odmeter + " Km ago </span>");

						} else if (diff_Odmeter > 1) {

							diffenceOdometer = ("<span style=\"color: #06b4ff;\">"+diff_Odmeter + " km from now");
						}
						break;
					}
					if(ServiceReminderDto.getMeter_interval() > 0) {
						Service.setDiffrent_meter_oddmeter(diffenceOdometer);
					}else {
						Service.setDiffrent_meter_oddmeter("");
					}
					

					// Overdue and Due soon message code logic

					String diffenceThrsholdOdometer = null;
					boolean overdue	= false;
					

					if (ServiceReminderDto.getMeter_servicethreshold_odometer() != null && ServiceReminderDto.getMeter_interval() > 0) {

						// eg: current 1000 -1500 = -500
						Integer currentThresholdDiff = Current_vehicleOdmeter
								- ServiceReminderDto.getMeter_servicethreshold_odometer();
						Integer meter_Threshold = 0;
						if (ServiceReminderDto.getMeter_threshold() != null) {
							meter_Threshold = ServiceReminderDto.getMeter_threshold();
						}
						// System.out.println("odo"+currentThresholdDiff);
						
						
						switch (currentThresholdDiff) {
						/*
						 * case 0: diffenceThrsholdOdometer = "Due Soon"; break;
						 */
						case 1:
							diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>";
							break;

						default:
							if (currentThresholdDiff > 1) {
								if (currentThresholdDiff < meter_Threshold) {
									diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>";
								} else if (currentThresholdDiff == meter_Threshold) {
									diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Today </span>";
								} else {
									overdue = true;
									diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due </span>";
								}

							}
							break;
						}
						if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equalsIgnoreCase("")) {
							Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
						}else {
							Service.setDiffenceThrsholdOdometer("");
						}
					}

					// Due soon message in Time interval
					if (ServiceReminderDto.getTime_servicethreshold_date() != null && ServiceReminderDto.getTime_interval() > 0) {

						int diffInDays_threshold = (int) ((DateTimeUtility.getCurrentDate().getTime()
								- ServiceReminderDto.getTime_servicethreshold_date().getTime())
								/ (1000 * 60 * 60 * 24));
						

						// System.out.println("time "+diffInDays_threshold);

						switch (diffInDays_threshold) {
						
						 case 0: diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>"; 
						 break;
						 
						case 1:
							diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due Today </span>";
							break;
						default:
							if (diffInDays_threshold > 1) {

								int diffInTimeServicethreshold = (int) ((ServiceReminderDto.getTime_servicedate()
										.getTime() - ServiceReminderDto.getTime_servicethreshold_date().getTime())
										/ (1000 * 60 * 60 * 24));

								if (diffInDays_threshold < diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>";
								} else if (diffInDays_threshold == diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due Today </span>";
								} else if (diffInDays_threshold > diffInTimeServicethreshold) {
									diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due </span>";
								}else {
									diffenceThrsholdOdometer = "";
								}

							}
							break;
						}
						
						if(!overdue || Service.getDiffenceThrsholdOdometer() == null || diffenceThrsholdOdometer.trim().equalsIgnoreCase("") ) {
							if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equalsIgnoreCase("")) {
								Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
							}else {
								Service.setDiffenceThrsholdOdometer("");
							}
						}
					}
//					if(ServiceReminderDto.isSkip()) {
//						diffenceThrsholdOdometer = "<span class=\"label label-default label-info\"> Skipped </span>";
//						Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
//					}
					boolean dueSoonOrOverDue = false;
					StringBuilder due = new StringBuilder("Due");
					if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equals("") && diffenceThrsholdOdometer.contains(due)) {
						dueSoonOrOverDue = true;
					}
					Service.setDueSoonOrOverDue(dueSoonOrOverDue);
					if(Service.getMeter_interval() != null && Service.getMeter_interval() > 0 && Service.getTime_interval() != null && Service.getTime_interval() > 0) {
						Service.setTaskAndSchedule("<a href='#'> "+Service.getService_type()+" - "+Service.getService_subtype()+"</a> Every "+Service.getTime_interval()+" "+Service.getTime_intervalperiod()+" or Every "+Service.getMeter_interval()+" Km");
					}else if(Service.getMeter_interval() != null && Service.getMeter_interval() > 0 && (Service.getTime_interval() == null || Service.getTime_interval() <= 0)) {
						Service.setTaskAndSchedule(" <a href='#'>"+Service.getService_type()+" - "+Service.getService_subtype()+"</a> Every "+Service.getMeter_interval()+" Km");
					}else if((Service.getMeter_interval() == null || Service.getMeter_interval() <= 0) && (Service.getTime_interval() != null && Service.getTime_interval() > 0)) {
						Service.setTaskAndSchedule("<a href='#'>"+Service.getService_type()+" - "+Service.getService_subtype()+"</a> Every "+Service.getTime_interval()+" "+Service.getTime_intervalperiod());
					}
					
					String nextDue = "";
					if(Service.getDiffrent_time_days() != "" && Service.getDiffrent_meter_oddmeter() != ""){
						nextDue = "<i class=\"fa fa-calendar-check-o\"></i> "+Service.getDiffrent_time_days()+"<br> <i class=\"fa fa-road\"></i> "+Service.getDiffrent_meter_oddmeter();
					}else if(Service.getDiffrent_time_days() != "" && Service.getDiffrent_meter_oddmeter() == ""){
						nextDue = "<i class=\"fa fa-calendar-check-o\"></i> "+Service.getDiffrent_time_days();
					}else if(Service.getDiffrent_time_days() == "" && Service.getDiffrent_meter_oddmeter() != ""){
						nextDue = "<i class=\"fa fa-calendar-check-o\"></i> "+Service.getDiffrent_meter_oddmeter();
					}
					
					Service.setNextDue(nextDue);
					Service.setServiceNumberStr("<a style=\"color: blue; background: #ffc;\" target=\"_blank\" href=\"ShowService.in?service_id="+Service.getService_id()+"\">S-"+Service.getService_Number()+"</a>");
					Service.setVehicleNumberLink("<a style=\"color: blue; background: #ffc;\" target=\"_blank\" href=\"showVehicle?vid="+Service.getVid()+"\">"+Service.getVehicle_registration()+"</a>");
					
				} catch (Exception e) {

					e.printStackTrace();
				}
				Service.setServiceTypeId(ServiceReminderDto.getServiceTypeId());
				Service.setServiceSubTypeId(ServiceReminderDto.getServiceSubTypeId());

				Dtos.add(Service);
			}
		}
		return Dtos;
	}
	

	public ServiceReminderDto GetServiceReminder(ServiceReminderDto ServiceReminder) {

		ServiceReminderDto Service = new ServiceReminderDto();

		Service.setService_id(ServiceReminder.getService_id());
		Service.setVid(ServiceReminder.getVid());
		Service.setVehicle_registration(ServiceReminder.getVehicle_registration());
		Service.setService_type(ServiceReminder.getService_type());
		Service.setService_subtype(ServiceReminder.getService_subtype());

		Service.setMeter_interval(ServiceReminder.getMeter_interval());
		Service.setTime_interval(ServiceReminder.getTime_interval());
		Service.setTime_intervalperiod(ServiceReminder.getTime_intervalperiod());
		Service.setMeter_threshold(ServiceReminder.getMeter_threshold());
		Service.setTime_threshold(ServiceReminder.getTime_threshold());
		Service.setTime_thresholdperiod(ServiceReminder.getTime_thresholdperiod());
		Service.setService_subscribeduser(ServiceReminder.getService_subScribedUserId());
		Service.setService_subScribedUserId(ServiceReminder.getService_subScribedUserId());
		Service.setService_subscribeduser_name(ServiceReminder.getService_subscribeduser_name());
		Service.setServiceStatusId(ServiceReminder.getServiceStatusId());
		Service.setServiceTypeId(ServiceReminder.getServiceTypeId());
		Service.setServiceSubTypeId(ServiceReminder.getServiceSubTypeId());
		Service.setTime_thresholdperiodId(ServiceReminder.getTime_thresholdperiodId());
		Service.setTime_intervalperiodId(ServiceReminder.getTime_intervalperiodId());
		Service.setServiceReminderType(ServiceReminder.getServiceReminderType());
		Service.setServiceType(ServiceReminder.getServiceType());
		return Service;
	}

	/**
	 * @param serviceReminder
	 * @return
	 */
	public ServiceReminderDto prepare_ServiceReminderDetail(ServiceReminderDto ServiceReminder) {

		ServiceReminderDto Service = new ServiceReminderDto();

		Service.setService_id(ServiceReminder.getService_id());
		Service.setService_Number(ServiceReminder.getService_Number());
		Service.setVid(ServiceReminder.getVid());
		Service.setVehicle_registration(ServiceReminder.getVehicle_registration());
		Service.setVehicle_Group(ServiceReminder.getVehicle_Group());
		Service.setVehicle_currentOdometer(ServiceReminder.getVehicle_currentOdometer());

		Service.setService_type(ServiceReminder.getService_type());
		Service.setService_subtype(ServiceReminder.getService_subtype());

		Service.setMeter_interval(ServiceReminder.getMeter_interval());
		Service.setTime_interval(ServiceReminder.getTime_interval());
		Service.setTime_intervalperiod(ServiceReminder.getTime_intervalperiod());
		Service.setTime_intervalperiodId(ServiceReminder.getTime_intervalperiodId());
		Service.setMeter_threshold(ServiceReminder.getMeter_threshold());
		Service.setTime_threshold(ServiceReminder.getTime_threshold());
		Service.setTime_thresholdperiod(ServiceReminder.getTime_thresholdperiod());
		Service.setTime_thresholdperiodId(ServiceReminder.getTime_thresholdperiodId());

		Service.setMeter_serviceodometer(ServiceReminder.getMeter_serviceodometer());

		Service.setService_subscribeduser(ServiceReminder.getService_subScribedUserId());
		Service.setService_subScribedUserId(ServiceReminder.getService_subScribedUserId());
		Service.setService_subscribeduser_name(ServiceReminder.getService_subscribeduser_name());

		Service.setService_emailnotification(ServiceReminder.getService_emailnotification());
		Service.setTime_servicedate(ServiceReminder.getTime_servicedate());
		if(ServiceReminder.getTime_servicedate() != null)
		Service.setTimeServiceDate(dateFormatTime.format(ServiceReminder.getTime_servicedate()));
		Service.setLast_servicecompleldby(ServiceReminder.getLast_servicecompleldby());
		Service.setLast_servicecompleldid(ServiceReminder.getLast_servicecompleldid());
		Service.setLast_servicedate(ServiceReminder.getLast_servicedate());
		if(ServiceReminder.getLast_servicedate() != null)
		Service.setLastServiceDate(dateFormatTime.format(ServiceReminder.getLast_servicedate()));
		Service.setService_remider_howtimes(ServiceReminder.getService_remider_howtimes());
		Service.setServicestatus(ServiceReminder.getServicestatus());
		Service.setServiceStatusId(ServiceReminder.getServiceStatusId());
		Service.setServiceTypeId(ServiceReminder.getServiceTypeId());
		Service.setServiceSubTypeId(ServiceReminder.getServiceSubTypeId());
		Service.setServiceReminderType(ServiceReminder.getServiceReminderType());
		Service.setWorkOrderId(ServiceReminder.getWorkOrderId());
		Service.setWorkOrderNumber(ServiceReminder.getWorkOrderNumber());
		Service.setServiceOdometer(ServiceReminder.getServiceOdometer());
		// Create and Last updated Display
		Service.setCreatedBy(ServiceReminder.getCreatedBy());
		if (ServiceReminder.getCreatedOn() != null) {
			Service.setCreated(CreatedDateTime.format(ServiceReminder.getCreatedOn()));
		}
		Service.setLastModifiedBy(ServiceReminder.getLastModifiedBy());
		if (ServiceReminder.getLastupdatedOn() != null) {
			Service.setLastupdated(CreatedDateTime.format(ServiceReminder.getLastupdatedOn()));
		}
		
		Service.setFirstService(ServiceReminder.isFirstService());
		Service.setFirstMeterInterval(ServiceReminder.getFirstMeterInterval());
		Service.setFirstTimeInterval(ServiceReminder.getFirstTimeInterval());
		Service.setFirstTimeIntervalType(ServiceReminder.getFirstTimeIntervalType());
		Service.setFirstTimeIntervalTypeStr(ServiceReminder.getFirstTimeIntervalTypeStr());
		Service.setServiceEntriesId(ServiceReminder.getServiceEntriesId());
		Service.setServiceEntriesNumber(ServiceReminder.getServiceEntriesNumber());
		Service.setDealerServiceEntriesId(ServiceReminder.getDealerServiceEntriesId());
		Service.setDealerServiceEntriesNumber(ServiceReminder.getDealerServiceEntriesNumber());
		Service.setSkip(ServiceReminder.isSkip());
		if(ServiceReminder.getIsSkipRemark() != null)
		Service.setIsSkipRemark(ServiceReminder.getIsSkipRemark());
		

		java.util.Date currentDate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

		if (ServiceReminder.getTime_servicedate() != null) {

			if(ServiceReminder.getTime_interval() > 0) {
				Service.setDiffrent_time_days(
						RRBL.time_DateDifferentAjax(ServiceReminder.getTime_servicedate(), toDate));
			}else {
				Service.setDiffrent_time_days("");
			}
			
		}

		try {
			Integer vehicleCurrentOdometer = 0;
			if (ServiceReminder.getVehicle_currentOdometer() != null) {
				vehicleCurrentOdometer = ServiceReminder.getVehicle_currentOdometer();
			}

			Integer Current_vehicleOdmeter = vehicleCurrentOdometer;
//			Integer serviceReminder = 0;
//			if (ServiceReminder.getMeter_serviceodometer() != null) {
//				serviceReminder = ServiceReminder.getMeter_serviceodometer();
//			}

			//Integer diff_Odmeter = serviceReminder - Current_vehicleOdmeter;


			String diffenceThrsholdOdometer = null;
			boolean overdue	= false;
			boolean Due     = false;

			if (ServiceReminder.getMeter_servicethreshold_odometer() != null && ServiceReminder.getMeter_interval() > 0) {

				Integer currentThresholdDiff = Current_vehicleOdmeter
						- ServiceReminder.getMeter_servicethreshold_odometer();
				Integer meter_Threshold = 0;
				if (ServiceReminder.getMeter_threshold() != null) {
					meter_Threshold = ServiceReminder.getMeter_threshold();
				}
				
				switch (currentThresholdDiff) {
				case 1:
					diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>";
					Due  = true;
					break;

				default:
					if (currentThresholdDiff > 1) {
						if (currentThresholdDiff < meter_Threshold) {
							diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>";
							Due = true;
						} else if (currentThresholdDiff == meter_Threshold) {
							diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Today </span>";
							Due = true;
						} else {
							overdue = true;
							Due = true;
							diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due </span>";
						}

					}
					break;
				}
				if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equalsIgnoreCase("")) {
					Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
				}else {
					Service.setDiffenceThrsholdOdometer("");
				}
			}

			// Due soon message in Time interval
			if (ServiceReminder.getTime_servicethreshold_date() != null && ServiceReminder.getTime_interval() > 0) {

				int diffInDays_threshold = (int) ((DateTimeUtility.getCurrentDate().getTime()
						- ServiceReminder.getTime_servicethreshold_date().getTime())
						/ (1000 * 60 * 60 * 24));
				

				switch (diffInDays_threshold) {
				
				 case 0: diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>"; 
				 Due  = true;
				 break;
				 
				case 1:
					diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due Today </span>";
					Due  = true;
					break;
				default:
					if (diffInDays_threshold > 1) {

						int diffInTimeServicethreshold = (int) ((ServiceReminder.getTime_servicedate()
								.getTime() - ServiceReminder.getTime_servicethreshold_date().getTime())
								/ (1000 * 60 * 60 * 24));

						if (diffInDays_threshold < diffInTimeServicethreshold) {
							diffenceThrsholdOdometer = "<span class=\"label label-default label-warning\"> Due Soon </span>";
						} else if (diffInDays_threshold == diffInTimeServicethreshold) {
							diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due Today </span>";
						} else if (diffInDays_threshold > diffInTimeServicethreshold) {
							diffenceThrsholdOdometer = "<span class=\"label label-default label-danger\"> Over Due </span>";
						}else {
							diffenceThrsholdOdometer = "";
						}
						Due  = true;
					}
					break;
				}
				
				if(!overdue || Service.getDiffenceThrsholdOdometer() == null || diffenceThrsholdOdometer.trim().equalsIgnoreCase("") ) {
					if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equalsIgnoreCase("")) {
						Service.setDiffenceThrsholdOdometer(diffenceThrsholdOdometer);
					}else {
						Service.setDiffenceThrsholdOdometer("");
					}
				}
			}
			boolean dueSoonOrOverDue = false;
			StringBuilder due = new StringBuilder("Due");
			if(diffenceThrsholdOdometer != null && !diffenceThrsholdOdometer.trim().equals("") && diffenceThrsholdOdometer.contains(due)) {
				dueSoonOrOverDue = true;
			}
			Service.setDueSoonOrOverDue(dueSoonOrOverDue);
			Service.setDue(Due);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return Service;
	}
	
	public List<EmailAlertQueueDto> getListOfEmailAlertQueue(List<EmailAlertQueueDto> mail) {
		List<EmailAlertQueueDto> Dtos = null;
		if (mail != null && !mail.isEmpty()) {
			Dtos = new ArrayList<EmailAlertQueueDto>();
			EmailAlertQueueDto email = null;
			for (EmailAlertQueueDto emailAlertQueue : mail) {
				email = new EmailAlertQueueDto();

				
				email.setQueueId(emailAlertQueue.getQueueId());
				email.setAlertAfterDate(emailAlertQueue.getAlertAfterDate());
				email.setAlertAfterValues(emailAlertQueue.getAlertAfterValues());
				email.setAlertBeforeDate(emailAlertQueue.getAlertBeforeDate());
				email.setAlertBeforeValues(emailAlertQueue.getAlertBeforeValues());
				email.setAlertScheduleDate(emailAlertQueue.getAlertScheduleDate());
				email.setAlertType(emailAlertQueue.getAlertType());
				email.setContent(emailAlertQueue.getContent());
				email.setEmailId(emailAlertQueue.getEmailId());
				email.setEmailSent(emailAlertQueue.isEmailSent());
				email.setOverDueAlert(emailAlertQueue.isOverDueAlert());
				email.setServiceDate(emailAlertQueue.getServiceDate());
				email.setTransactionId(emailAlertQueue.getTransactionId());
				email.setTransactionNumber(emailAlertQueue.getTransactionNumber());
				email.setVid(emailAlertQueue.getVid());
				
				Dtos.add(email);
					}

				} 
			
		
		return Dtos;
	}


}
