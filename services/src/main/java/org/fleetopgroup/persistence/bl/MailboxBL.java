package org.fleetopgroup.persistence.bl;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fleetopgroup.persistence.dto.MailboxDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.SubscribeBoxDto;
import org.fleetopgroup.persistence.model.Mailbox;
import org.fleetopgroup.persistence.model.SubscribeBox;
import org.springframework.stereotype.Controller;

/**
 * @author fleetop
 *
 *
 *
 */

@Controller
public class MailboxBL {

	public MailboxBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	/* Branch Document List */
	/* List of showing branchDocument details */
	public List<MailboxDto> prepareMailboxDto(List<Mailbox> mailbox) {
		List<MailboxDto> beans = null;
		if (mailbox != null && !mailbox.isEmpty()) {
			beans = new ArrayList<MailboxDto>();

			MailboxDto mail = null;

			for (Mailbox mailDto : mailbox) {

				mail = new MailboxDto();

				Base64.Encoder encoder = Base64.getEncoder();
				String normalMailID = "" + mailDto.getMAILBOX_ID();
				String encodedMailId = encoder.encodeToString(normalMailID.getBytes(StandardCharsets.UTF_8));
				/** encode long Mailbox-Id value */
				mail.setMAILBOX_ID_Encode(encodedMailId);

				mail.setMAILBOX_ID(mailDto.getMAILBOX_ID());
				mail.setMAILBOX_FROM_USER_ID(mailDto.getMAILBOX_FROM_USER_ID());
				mail.setMAILBOX_FROM_USER_NAME(mailDto.getMAILBOX_FROM_USER_NAME());
				mail.setMAILBOX_FROM_EMAIL(mailDto.getMAILBOX_FROM_EMAIL());
				mail.setMAILBOX_NAMESPACE(mailDto.getMAILBOX_NAMESPACE());

				mail.setMAIL_IS_SEEN(mailDto.isMAIL_IS_SEEN());
				mail.setMAIL_IS_FLAGGED(mailDto.isMAIL_IS_FLAGGED());

				mail.setMAIL_CONTENT_OCTETS_COUNT(mailDto.getMAIL_CONTENT_OCTETS_COUNT());

				mail.setMAIL_TEXTUAL_COUNT(mailDto.getMAIL_TEXTUAL_COUNT());

				mail.setMAILBOX_FROM_USER_NAME(mailDto.getMAILBOX_FROM_USER_NAME());

				mail.setMAILBOX_TO_USER_NAME(mailDto.getMAILBOX_TO_USER_NAME());
				
				mail.setMAIL_DOCUMENT(mailDto.isMAIL_DOCUMENT());

				/** Date diffrent Date */
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				long diff = toDate.getTime() - mailDto.getMAIL_DATE().getTime();// as
				Integer diffInDays = (int) TimeUnit.MILLISECONDS.toDays(diff);// given

				/*
				 * long seconds = TimeUnit.MILLISECONDS.toSeconds(diff); long
				 * minutes = TimeUnit.MILLISECONDS.toMinutes(diff); long hours =
				 * TimeUnit.MILLISECONDS.toHours(diff);
				 */
				/*
				 * System.out.println("seconds = " + seconds);
				 * System.out.println("minutes = " + minutes);
				 * System.out.println("hours = " + hours); System.out.println(
				 * "Days = " + diffInDays);
				 */

				String diffenceDays = null;
				switch (diffInDays) {
				case 0:
					DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
					// System.out.println(outputFormat.format(mailDto.getMAIL_DATE()));
					diffenceDays = (outputFormat.format(mailDto.getMAIL_DATE()));

					break;

				default:
					diffenceDays = new SimpleDateFormat("MMM").format(mailDto.getMAIL_DATE()) + " "
							+ new SimpleDateFormat("dd").format(mailDto.getMAIL_DATE());
					// System.out.println((new
					// SimpleDateFormat("MMM").format(mailDto.getMAIL_DATE())+"
					// "+new
					// SimpleDateFormat("dd").format(mailDto.getMAIL_DATE())));
					break;
				}

				mail.setMAIL_DATE(diffenceDays);

				beans.add(mail);
			}
		}
		return beans;
	}

	/* List of showing branchDocument details */
	public List<MailboxDto> prepareMessageDto(List<Mailbox> mailbox) {
		List<MailboxDto> beans = null;
		if (mailbox != null && !mailbox.isEmpty()) {
			beans = new ArrayList<MailboxDto>();

			MailboxDto mail = null;

			for (Mailbox mailDto : mailbox) {

				mail = new MailboxDto();

				mail.setMAILBOX_ID(mailDto.getMAILBOX_ID());

				mail.setMAILBOX_FROM_USER_ID(mailDto.getMAILBOX_FROM_USER_ID());
				mail.setMAILBOX_FROM_USER_NAME(mailDto.getMAILBOX_FROM_USER_NAME());
				mail.setMAILBOX_FROM_EMAIL(mailDto.getMAILBOX_FROM_EMAIL());
				mail.setMAILBOX_NAMESPACE(mailDto.getMAILBOX_NAMESPACE());

				mail.setMAIL_IS_SEEN(mailDto.isMAIL_IS_SEEN());
				mail.setMAIL_IS_FLAGGED(mailDto.isMAIL_IS_FLAGGED());

				mail.setMAIL_CONTENT_OCTETS_COUNT(mailDto.getMAIL_CONTENT_OCTETS_COUNT());

				mail.setMAIL_TEXTUAL_COUNT(mailDto.getMAIL_TEXTUAL_COUNT());

				mail.setMAILBOX_FROM_USER_NAME(mailDto.getMAILBOX_FROM_USER_NAME());

				/** Date diffrent Date */
				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				long diff = toDate.getTime() - mailDto.getMAIL_DATE().getTime();// as
				Integer diffInDays = (int) TimeUnit.MILLISECONDS.toDays(diff);// given

				/*
				 * long seconds = TimeUnit.MILLISECONDS.toSeconds(diff); long
				 * minutes = TimeUnit.MILLISECONDS.toMinutes(diff); long hours =
				 * TimeUnit.MILLISECONDS.toHours(diff);
				 */
				/*
				 * System.out.println("seconds = " + seconds);
				 * System.out.println("minutes = " + minutes);
				 * System.out.println("hours = " + hours); System.out.println(
				 * "Days = " + diffInDays);
				 */

				String diffenceDays = null;
				switch (diffInDays) {
				case 0:
					DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
					// System.out.println(outputFormat.format(mailDto.getMAIL_DATE()));
					diffenceDays = (outputFormat.format(mailDto.getMAIL_DATE()));

					break;

				default:
					diffenceDays = new SimpleDateFormat("MMM").format(mailDto.getMAIL_DATE()) + " "
							+ new SimpleDateFormat("dd").format(mailDto.getMAIL_DATE());
					// System.out.println((new
					// SimpleDateFormat("MMM").format(mailDto.getMAIL_DATE())+"
					// "+new
					// SimpleDateFormat("dd").format(mailDto.getMAIL_DATE())));
					break;
				}

				mail.setMAIL_DATE(diffenceDays);

				beans.add(mail);
			}
		}
		return beans;
	}

	/* List of showing branchDocument details */
	public MailboxDto prepareReadEncodeDto(Mailbox mailDto) {

		MailboxDto mail = new MailboxDto();

		Base64.Encoder encoder = Base64.getEncoder();
		String normalMailID = "" + mailDto.getMAILBOX_ID();
		String encodedMailId = encoder.encodeToString(normalMailID.getBytes(StandardCharsets.UTF_8));
		/** encode long Mailbox-Id value */
		mail.setMAILBOX_ID_Encode(encodedMailId);

		mail.setMAILBOX_ID(mailDto.getMAILBOX_ID());
		
		mail.setMAILBOX_FROM_USER_ID(mailDto.getMAILBOX_FROM_USER_ID());
		mail.setMAILBOX_FROM_USER_NAME(mailDto.getMAILBOX_FROM_USER_NAME());
		mail.setMAILBOX_FROM_EMAIL(mailDto.getMAILBOX_FROM_EMAIL());
		mail.setMAILBOX_NAMESPACE(mailDto.getMAILBOX_NAMESPACE());

		mail.setMAILBOX_SENTER_MAILBOX_ID(mailDto.getMAILBOX_SENTER_MAILBOX_ID());

		mail.setMAIL_IS_SEEN(mailDto.isMAIL_IS_SEEN());
		mail.setMAIL_IS_FLAGGED(mailDto.isMAIL_IS_FLAGGED());

		mail.setMAIL_CONTENT_OCTETS_COUNT(mailDto.getMAIL_CONTENT_OCTETS_COUNT());

		mail.setMAIL_TEXTUAL_COUNT(mailDto.getMAIL_TEXTUAL_COUNT());

		mail.setMAILBOX_FROM_USER_NAME(mailDto.getMAILBOX_FROM_USER_NAME());

		mail.setMAIL_MIME_TYPE(mailDto.getMAIL_MIME_TYPE());

		mail.setMAIL_CONTENT_OCTETS_COUNT(mailDto.getMAIL_CONTENT_OCTETS_COUNT());

		mail.setMAILBOX_TO_EMAIL(mailDto.getMAILBOX_TO_EMAIL());

		mail.setMAIL_DOCUMENT(mailDto.isMAIL_DOCUMENT());
		mail.setMAIL_DOCUMENT_ID(mailDto.getMAIL_DOCUMENT_ID());
		
		String format = "EEE, MMM dd, yyyy h:mm a ";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			mail.setMAIL_DATE(sdf.format(mailDto.getMAIL_DATE()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mail;
	}

	/** Subscribe box business logic and value */

	public List<SubscribeBoxDto> prepareListof_Subscribe_List(List<SubscribeBox> subscribe) {
		List<SubscribeBoxDto> Dtos = null;
		if (subscribe != null && !subscribe.isEmpty()) {
			Dtos = new ArrayList<SubscribeBoxDto>();
			SubscribeBoxDto subscribeDto = null;
			for (SubscribeBox subscribeService : subscribe) {
				subscribeDto = new SubscribeBoxDto();

				subscribeDto.setSUBSCRIBE_LOCATION_ID(subscribeService.getSUBSCRIBE_LOCATION_ID());
				subscribeDto.setSUBSCRIBE_LOCATION(subscribeService.getSUBSCRIBE_LOCATION());
			
				subscribeDto.setSUBSCRIBE_VEHICLE_ID(subscribeService.getSUBSCRIBE_VEHICLE_ID());
				subscribeDto.setSUBSCRIBE_VEHICLE_NAME(subscribeService.getSUBSCRIBE_VEHICLE_NAME());
				subscribeDto.setSUBSCRIBE_TYPE(subscribeService.getSUBSCRIBE_TYPE());
				subscribeDto.setSUBSCRIBE_SUBTYPE(subscribeService.getSUBSCRIBE_SUBTYPE());

				if (subscribeService.getSUBSCRIBE_DATE() != null) {
					subscribeDto.setSUBSCRIBE_DATE(dateFormat.format(subscribeService.getSUBSCRIBE_DATE()));
				}
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (subscribeService.getSUBSCRIBE_DATE() != null) {

					int diffInDays = (int) ((subscribeService.getSUBSCRIBE_DATE().getTime() - toDate.getTime())
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
								diffenceDays = (diffInDays / 365 + " years & " + (diffInDays % 365)
										+ " months from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					// System.out.println( diffenceDays);
					subscribeDto.setSUBSCRIBE_THRESHOLD_DATE(diffenceDays);
				}

				
				Dtos.add(subscribeDto);
			}// close for loop
		} // close if condition
		return Dtos;
	}
	
	
	public List<ServiceReminderDto> prepareListofServiceDto(List<ServiceReminderDto> ServiceReminder) {
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
				Service.setService_type(ServiceReminderDto.getService_type());
				Service.setService_subtype(ServiceReminderDto.getService_subtype());

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (ServiceReminderDto.getTime_servicedate() != null) {

					int diffInDays = (int) ((ServiceReminderDto.getTime_servicedate().getTime() - toDate.getTime())
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
								diffenceDays = (diffInDays / 365 + " years & " + (diffInDays % 365)
										+ " months from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					// System.out.println( diffenceDays);
					Service.setDiffrent_time_days(diffenceDays);
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

				} catch (Exception e) {

					e.printStackTrace();
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
			}// close for loop
		} // close if condition
		return Dtos;
	}
}