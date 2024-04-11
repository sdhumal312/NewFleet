/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssuesCommentDto;
import org.fleetopgroup.persistence.dto.IssuesDocumentDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.IssuesPhotoDto;
import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.fleetopgroup.persistence.model.IssueBreakDownDetails;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesComment;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateDiffrentConvert;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author fleetop
 *
 */
public class IssuesBL {

	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("EEE, d MMM yyyy");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass()); //latest

	DateDiffrentConvert dataconvert = new DateDiffrentConvert();

	public List<IssuesDto> prepare_Issues_ListofDto(List<IssuesDto> issues) {
		List<IssuesDto> Dtos = null;
		if (issues != null && !issues.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto issue = null;
			for (IssuesDto issuesDto : issues) {
				issue = new IssuesDto();

				issue.setISSUES_ID(issuesDto.getISSUES_ID());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				// String originalString2 = "" + 201;
				// String encryptedString2 =
				// AESEncryptDecrypt.encrypt(originalString2);
				// String decryptedString2 =
				// AESEncryptDecrypt.decrypt(encryptedString2);
				// this code change as encrypt in own code
				issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));
				issue.setISSUES_TYPE(issuesDto.getISSUES_TYPE());
				issue.setISSUES_TYPE_ID(issuesDto.getISSUE_TYPE_ID());
				issue.setISSUES_VID(issuesDto.getISSUES_VID());
				issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
				issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());

				issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
				issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());

				issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
				issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
				issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());

				if (issuesDto.getISSUES_REPORTED_DATE() != null) {
					issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE()));
				
				}
				issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
				issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
				issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
				issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
				issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
				issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());

				issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
				issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));

				/** Set Created by email Id */
				issue.setCREATEDBY(issuesDto.getCREATEDBY());
				issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());

				if (issuesDto.getCREATED_DATE() != null) {
					/** Set Created Current Date */
					issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE()));

					switch (issuesDto.getISSUES_STATUS_ID()) {
					case IssuesTypeConstant.ISSUES_STATUS_OPEN:
						// if Status Open Check Differences_date and time Values
						java.util.Date currentDateNow = new java.util.Date();

						issue.setISSUES_DIFFERENCES_DATE(
								getDateDifferenceInDDMMYYYY(issuesDto.getCREATED_DATE_ON(), currentDateNow));
						break;

					default:

						issue.setISSUES_DIFFERENCES_DATE(getDateDifferenceInDDMMYYYY(issuesDto.getCREATED_DATE_ON(),
								issuesDto.getLASTUPDATED_DATE_ON()));
						break;
					}

				}
				if (issuesDto.getLASTUPDATED_DATE() != null) {
					issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE()));
				}

				Dtos.add(issue);
			}
		}
		return Dtos;
	}
	
	public List<IssuesDto> prepare_Issues_ListDto(List<IssuesDto> issues) throws Exception {
		List<IssuesDto> Dtos = null;
		if (issues != null && !issues.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto issue = null;
			for (IssuesDto issuesDto : issues) {
				issue = new IssuesDto();

				issue.setISSUES_ID(issuesDto.getISSUES_ID());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				// String originalString2 = "" + 201;
				// String encryptedString2 =
				// AESEncryptDecrypt.encrypt(originalString2);
				// String decryptedString2 =
				// AESEncryptDecrypt.decrypt(encryptedString2);
				// this code change as encrypt in own code
				issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));
				issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
				issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());

				issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
				issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());

				issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
				issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
				issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
					issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
					issue.setIssuesReportedDateStr(dateFormat.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
				}
				issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
				issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
				issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
				issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
				issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
				issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());

				issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
				issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));

				/** Set Created by email Id */
				issue.setCREATEDBY(issuesDto.getCREATEDBY());
				issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());

				if (issuesDto.getCREATED_DATE_ON() != null) {
					/** Set Created Current Date */
					issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE_ON()));

					switch (issuesDto.getISSUES_STATUS_ID()) {
					case IssuesTypeConstant.ISSUES_STATUS_OPEN:
					case IssuesTypeConstant.ISSUES_STATUS_CLOSED:	
						// if Status Open Check Differences_date and time Values
						java.util.Date currentDateNow = new java.util.Date();

						issue.setISSUES_DIFFERENCES_DATE(
								getDateDifferenceInDDMMYYYY(issuesDto.getCREATED_DATE_ON(), currentDateNow));
						break;

					default:
						java.util.Date currentDateNow1 = new java.util.Date();

						issue.setISSUES_DIFFERENCES_DATE(getDateDifferenceInDDMMYYYY(
								issuesDto.getLASTUPDATED_DATE_ON(),currentDateNow1));
						break;
					}

				}
				if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
					issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
				}
				issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));
				issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
				issue.setISSUES_VID(issuesDto.getISSUES_VID());
				if(issuesDto.getISSUES_WORKORDER_ID() != null) {
					issue.setISSUES_WORKORDER_ID(issuesDto.getISSUES_WORKORDER_ID());
				}else {
					issue.setISSUES_WORKORDER_ID((long) 0);
				}if(issuesDto.getISSUES_SE_ID() != null) {
					issue.setISSUES_SE_ID(issuesDto.getISSUES_SE_ID());
				}else {
					issue.setISSUES_SE_ID((long) 0);
				}
				issue.setCREATEDBYID(issuesDto.getCREATEDBYID());
				issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				issue.setCREATEDBYID(issuesDto.getCREATEDBYID());
				issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
				issue.setVehicleType(issuesDto.getVehicleType());
				issue.setVehicleMaker(issuesDto.getVehicleMaker());
				issue.setIssueAnalysisId(issuesDto.getIssueAnalysisId());
				issue.setIssueAnalysis(issuesDto.isIssueAnalysis());
				issue.setPartCategoryName(issue.getPartCategoryName());
				Dtos.add(issue);
			}
		}
		return Dtos;
	}

	/** This Dto prepare vehicle LIST in show Issues Dto Overdue 
	 * @throws Exception */
	public List<IssuesDto> prepare_Issues_IN_Vehicle_ListofDto(List<IssuesDto> issues) throws Exception {
		List<IssuesDto> Dtos = null;
		if (issues != null && !issues.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto issue = null;
			for (IssuesDto issuesDto : issues) {
				issue = new IssuesDto();

				issue.setISSUES_ID(issuesDto.getISSUES_ID());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				// this code change as encrypt in own code
				issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));
				issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
				issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
				issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));

				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());

				issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());
				issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
				issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));

				issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
				issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
					issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
				}
				issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
				issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());

				issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
				issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));

				/** Set Created by email Id */
				issue.setCREATEDBY(issuesDto.getCREATEDBY());
				issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());

				if (issuesDto.getCREATED_DATE_ON() != null) {
					/** Set Created Current Date */
					issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE_ON()));
				}
				if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
					issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
				}
				issue.setCREATEDBYID(issuesDto.getCREATEDBYID());
				issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());

				Dtos.add(issue);
			}
		}
		return Dtos;
	}

	/** This Dto propare Issues Dto Overdue 
	 * @throws Exception */
	public List<IssuesDto> prepare_Issues_OverDue_ListofDto(List<IssuesDto> issues) throws Exception {
		List<IssuesDto> Dtos = null;
		if (issues != null && !issues.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto issue = null;
			for (IssuesDto issuesDto : issues) {
				issue = new IssuesDto();

				issue.setISSUES_ID(issuesDto.getISSUES_ID());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				// this code change as encrypt in own code
				issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));

				issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));
				issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
				issue.setISSUES_VID(issuesDto.getISSUES_VID());
				issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
				issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());

				issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
				issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());

				issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
				issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
				issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
					issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
				}
				issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
				issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
				issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
				issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
				issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
				issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());

				issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));
				issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());

				/** Set Created by email Id */
				issue.setCREATEDBY(issuesDto.getCREATEDBY());
				issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());

				if (issuesDto.getCREATED_DATE_ON() != null) {
					/** Set Created Current Date */
					issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE_ON()));
				}
				if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
					issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
				}

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {

					int diffInDays = (int) ((issuesDto.getISSUES_REPORTED_DATE_ON().getTime() - toDate.getTime())
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
					issue.setISSUES_REPORTED_DATE_DIFFRENT(diffenceDays);
				}

				Dtos.add(issue);
			}
		}
		return Dtos;
	}

	
	public List<IssuesDto> prepareOverDueList(List<IssuesDto> issues) throws Exception {
		List<IssuesDto> Dtos = null;
		if (issues != null && !issues.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto issue = null;
			for (IssuesDto issuesDto : issues) {
				issue = new IssuesDto();

				issue.setISSUES_ID(issuesDto.getISSUES_ID());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				// this code change as encrypt in own code
				issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));

				issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));
				issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
				issue.setISSUES_VID(issuesDto.getISSUES_VID());
				issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
				issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
				/** issues Odameter */
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());

				issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
				issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());

				issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
				issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
				issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
					issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
				}
				issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
				issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
				issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
				issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
				issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
				issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());

				issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));
				issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());

				/** Set Created by email Id */
				issue.setCREATEDBY(issuesDto.getCREATEDBY());
				issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());
				issue.setVehicleType(issuesDto.getVehicleType());
				issue.setVehicleMaker(issuesDto.getVehicleMaker());

				if (issuesDto.getCREATED_DATE_ON() != null) {
					/** Set Created Current Date */
					issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE_ON()));
				}
				if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
					issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
				}

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {

					int diffInDays = (int) ((issuesDto.getISSUES_REPORTED_DATE_ON().getTime() - toDate.getTime())
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
					issue.setISSUES_REPORTED_DATE_DIFFRENT(diffenceDays);
				}
				long  noOfDaysReported = DateTimeUtility.getExactDayDiffBetweenTwoDates(new Timestamp(issuesDto.getISSUES_REPORTED_DATE_ON().getTime()), 
						  DateTimeUtility.getCurrentTimeStamp());
				
				if(issue.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUES_LABELS_NORMAL && noOfDaysReported >= 4
					|| issue.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUES_LABELS_NORMAL && noOfDaysReported >= 4
					|| issue.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUES_LABELS_HIGH && noOfDaysReported >= 3
					|| issue.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUES_LABELS_URGENT && noOfDaysReported >= 2
					|| issue.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUES_LABELS_VERY_URGENT && noOfDaysReported >= 1
					|| issue.getISSUES_LABELS_ID() == IssuesTypeConstant.ISSUES_LABELS_BREAKDOWN && noOfDaysReported >= 1
						) {
					
					Dtos.add(issue);
				}
			}
		}
		return Dtos;
	}

	
	/** Showing Issues Details to be in IssuesDto */

	public IssuesDto Showing_Issues_Details(Issues issuesDto) {

		IssuesDto issue = null;
		// this code change as encrypt in own code
		if (issuesDto != null) {
			issue = new IssuesDto();
			issue.setISSUES_ID(issuesDto.getISSUES_ID());
			issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
			issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));
			//issue.setISSUES_TYPE(issuesDto.getISSUES_TYPE());
			issue.setISSUES_TYPE_ID(issuesDto.getISSUE_TYPE_ID());
			issue.setISSUES_VID(issuesDto.getISSUES_VID());
			//issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
			//issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
			issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());
			issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
			//issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
			issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
			//issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());
			issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
			//issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());
			if (issuesDto.getISSUES_REPORTED_DATE() != null) {
				issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE()));
			}
			issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
			issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
			//issue.setISSUES_LABELS(issuesDto.getISSUES_LABELS());
			//issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
			issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
			/** Set Created ISSUES_WORKORDER_ID Date */
			issue.setISSUES_WORKORDER_ID(issuesDto.getISSUES_WORKORDER_ID());
			/** Set Created ISSUES_WORKORDER_DATE Date */
			if (issuesDto.getISSUES_WORKORDER_DATE() != null) {
				/** Set Created Current Date */
				issue.setISSUES_WORKORDER_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_WORKORDER_DATE()));
			}
			issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());
			//issue.setISSUES_STATUS(issuesDto.getISSUES_STATUS());
			issue.setMarkForDelete(issuesDto.isMarkForDelete());
			/** Set Created by email Id */
			//issue.setCREATEDBY(issuesDto.getCREATEDBY());
			//issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());
			/** Set Created Current Date */
			if (issuesDto.getCREATED_DATE() != null) {
				/** Set Created Current Date */
				issue.setCREATED_DATE(CreatedDateTime.format(issuesDto.getCREATED_DATE()));
			}
			if (issuesDto.getLASTUPDATED_DATE() != null) {
				issue.setLASTUPDATED_DATE(CreatedDateTime.format(issuesDto.getLASTUPDATED_DATE()));
			}
		}
		return issue;
	}

	public IssuesDto Showing_Issues_Details(IssuesDto issuesDto) throws Exception {

		IssuesDto issue = null;
		// this code change as encrypt in own code
		if (issuesDto != null) {
			issue = new IssuesDto();
			issue.setISSUES_ID(issuesDto.getISSUES_ID());
			issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
			issue.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesDto.getISSUES_ID()));
			issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
			issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));
			issue.setISSUES_VID(issuesDto.getISSUES_VID());
			issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
			issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
			issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());
			issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
			issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
			issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
			issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());
			issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
			issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());
			issue.setCUSTOMER_NAME(issuesDto.getCUSTOMER_NAME());
			if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
				issue.setISSUES_REPORTED_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
			}
			issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
			issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
			issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
			issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
			issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
			issue.setISSUES_REPORTED_BY_ID(issuesDto.getISSUES_REPORTED_BY_ID());
			issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
			/** Set Created ISSUES_WORKORDER_ID Date */
			issue.setISSUES_WORKORDER_ID(issuesDto.getISSUES_WORKORDER_ID());
			/** Set Created ISSUES_WORKORDER_DATE Date */
			if (issuesDto.getISSUES_WORKORDER_DATE_ON() != null) {
				/** Set Created Current Date */
				issue.setISSUES_WORKORDER_DATE(dateFormatonlyDateTime.format(issuesDto.getISSUES_WORKORDER_DATE_ON()));
			}
			issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());
			issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
			issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));
			issue.setMarkForDelete(issuesDto.isMarkForDelete());
			/** Set Created by email Id */
			issue.setCREATEDBY(issuesDto.getCREATEDBY());
			issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());
			issue.setDriver_mobnumber(issuesDto.getDriver_mobnumber());
			issue.setISSUES_WORKORDER_ID(issuesDto.getISSUES_WORKORDER_ID());
			issue.setISSUES_WORKORDER_NUMBER(issuesDto.getISSUES_WORKORDER_NUMBER());
			issue.setGPS_ODOMETER(issuesDto.getGPS_ODOMETER());
			issue.setCategoryId(issuesDto.getCategoryId());
			issue.setPartCategoryName(issuesDto.getPartCategoryName());
			issue.setRouteName(issuesDto.getRouteName());
			
			
			/** Set Created Current Date */
			if (issuesDto.getCREATED_DATE_ON() != null) {
				/** Set Created Current Date */
				issue.setCREATED_DATE(CreatedDateTime.format(issuesDto.getCREATED_DATE_ON()));
			}
			if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
				issue.setLASTUPDATED_DATE(CreatedDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
			}
			
			if(issuesDto.getISSUES_SE_ID() != null && issuesDto.getISSUES_SE_ID() > 0  ) {
				issue.setISSUES_SE_ID(issuesDto.getISSUES_SE_ID());
				issue.setServiceEntries_Number(issuesDto.getServiceEntries_Number());
				issue.setISSUES_SE_DATE(CreatedDateTime.format(issuesDto.getISSUES_SE_DATEON()));
			}
			issue.setCREATEDBYID(issuesDto.getCREATEDBYID());
			issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
			issue.setDealerServiceEntriesId(issuesDto.getDealerServiceEntriesId());
			issue.setLocation(issuesDto.getLocation());
			issue.setVehicleModel(issuesDto.getVehicleModel());
			issue.setVehicleType(issuesDto.getVehicleType());
			issue.setIssueLP_ID(issuesDto.getIssueLP_ID());
			
		}
		return issue;
	}

	
	
	/** Showing Issues Details to be in IssuesDto 
	 * @throws Exception */

	public IssuesDto Edit_Issues_Details(IssuesDto issuesDto) throws Exception {

		IssuesDto issue = new IssuesDto();

		issue.setISSUES_ID(issuesDto.getISSUES_ID());
		issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());

		issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
		issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));

		issue.setISSUES_VID(issuesDto.getISSUES_VID());
		issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
		issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
		issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());
		issue.setGPS_ODOMETER(issuesDto.getGPS_ODOMETER());

		issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
		issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());

		issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
		issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

		issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
		issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());

		if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
			issue.setISSUES_REPORTED_DATE(dateFormat.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
			issue.setIssue_start_time(timeFormat.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
		}
		issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
		issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
		issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
		issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
		issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
		issue.setISSUES_REPORTED_BY_ID(issuesDto.getISSUES_REPORTED_BY_ID());
		issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
		issue.setVEHICLE_GROUP_ID(issuesDto.getVEHICLE_GROUP_ID());
		issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
		issue.setCUSTOMER_NAME(issuesDto.getCUSTOMER_NAME());

		/** Set Created ISSUES_WORKORDER_ID Date */
		issue.setISSUES_WORKORDER_ID(issuesDto.getISSUES_WORKORDER_ID());
		/** Set Created ISSUES_WORKORDER_DATE Date */
		if (issuesDto.getISSUES_WORKORDER_DATE_ON() != null) {
			/** Set Created Current Date */
			issue.setISSUES_WORKORDER_DATE(dateFormat.format(issuesDto.getISSUES_WORKORDER_DATE_ON()));
		}

		issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());

		issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
		issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));

		issue.setMarkForDelete(issuesDto.isMarkForDelete());
		/** Set Created by email Id */

		issue.setCREATEDBY(issuesDto.getCREATEDBY());
		issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());

		/** Set Created Current Date */
		if (issuesDto.getCREATED_DATE_ON() != null) {
			/** Set Created Current Date */
			issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE_ON()));
		}
		if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
			issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
		}
		issue.setVehicleCurrentOdometer(issuesDto.getVehicleCurrentOdometer());
		issue.setCategoryId(issuesDto.getCategoryId());
		issue.setPartCategoryName(issuesDto.getPartCategoryName());
		issue.setRouteID(issuesDto.getRouteID());
		issue.setRouteName(issuesDto.getRouteName());
		issue.setLocation(issuesDto.getLocation());
		
		
		return issue;
	}

	/** Showing IssuesComment Details to be in IssuesDto */
	public IssuesComment Save_IssuesComment_Details(IssuesComment comment) {

		IssuesComment issue = new IssuesComment();

		issue.setISSUES_ID(comment.getISSUES_ID());

		return issue;
	}

	/** Showing IssuesComment Details to be in IssuesCommentDto */
	public List<IssuesCommentDto> prepare_IssuesComment_ListofDto(List<IssuesCommentDto> issuesComment) {
		List<IssuesCommentDto> Dtos = null;
		if (issuesComment != null && !issuesComment.isEmpty()) {
			Dtos = new ArrayList<IssuesCommentDto>();
			IssuesCommentDto issueCom = null;
			for (IssuesCommentDto issuesCOMDto : issuesComment) {
				issueCom = new IssuesCommentDto();

				issueCom.setISSUE_COMMENTID(issuesCOMDto.getISSUE_COMMENTID());
				issueCom.setISSUE_COMMENTID_ENCRYPT(
						AESEncryptDecrypt.encryptBase64("" + issuesCOMDto.getISSUE_COMMENTID()));
				issueCom.setISSUES_ID(issuesCOMDto.getISSUES_ID());
				issueCom.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesCOMDto.getISSUES_ID()));
				if(issuesCOMDto.getISSUE_TITLE() != null) {
					issueCom.setISSUE_TITLE(issuesCOMDto.getISSUE_TITLE());
				}else {
					issueCom.setISSUE_TITLE(issuesCOMDto.getCOMMENT_TYPE_NAME());
				}
				issueCom.setCOMMENT_TYPE_ID(issuesCOMDto.getCOMMENT_TYPE_ID());
				issueCom.setISSUE_COMMENT(issuesCOMDto.getISSUE_COMMENT());

				issueCom.setCREATEDBY(issuesCOMDto.getCREATEDBY());
				issueCom.setCREATED_EMAIL(issuesCOMDto.getCREATED_EMAIL());
				issueCom.setMarkForDelete(false);
				/** Set Created Current Date */
				if (issuesCOMDto.getCREATED_DATEON() != null) {
					issueCom.setCREATED_DATE(dateFormatonlyDateTime.format(issuesCOMDto.getCREATED_DATEON()));
				}

				// System.out.println( diffenceDays);
				try {
					issueCom.setCREATED_DATE_DIFFERENT(dataconvert.DifferentDate(issuesCOMDto.getCREATED_DATEON()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				issueCom.setAssignee(issuesCOMDto.getAssignee());
				issueCom.setAssigneeName(issuesCOMDto.getAssigneeName());
				issueCom.setDriverName(issuesCOMDto.getDriverName());
				issueCom.setDriverFatherName(issuesCOMDto.getDriverFatherName());

				Dtos.add(issueCom);
			}
		}
		return Dtos;
	}

	public static String getDateDifferenceInDDMMYYYY(java.util.Date date, java.util.Date currentDate) {

		// in milliseconds
		long timeDifferenceMilliseconds = currentDate.getTime() - date.getTime();

		long diffSeconds = timeDifferenceMilliseconds / 1000;
		long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
		long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
		long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
		long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
		long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
		long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

		if (diffSeconds < 1) {
			return "less than a second";
		} else if (diffMinutes < 1) {
			return diffSeconds + " seconds";
		} else if (diffHours < 1) {
			return diffMinutes + " minutes";
		} else if (diffDays < 1) {
			return diffHours + " hours";
		} else if (diffWeeks < 1) {
			return diffDays + " days";
		} else if (diffMonths < 1) {
			return diffWeeks + " weeks";
		} else if (diffYears < 1) {
			return diffMonths + " months";
		} else {
			return diffYears + " years";
		}
	}

	/**
	 * @param get_Issues_ID_Document_Details
	 * @return
	 */
	public List<IssuesDocumentDto> Showing_IssuesDocument_Details(List<org.fleetopgroup.persistence.document.IssuesDocument> issuesDocument) {
		List<IssuesDocumentDto> Dtos = null;
		if (issuesDocument != null && !issuesDocument.isEmpty()) {
			Dtos = new ArrayList<IssuesDocumentDto>();
			IssuesDocumentDto issueCom = null;
			for (org.fleetopgroup.persistence.document.IssuesDocument issuesCOMDto : issuesDocument) {
				issueCom = new IssuesDocumentDto();

				issueCom.setISSUE_DOCUMENTID(issuesCOMDto.get_id());

				issueCom.setISSUE_DOCUMENTID_ENCRYPT(
						AESEncryptDecrypt.encryptBase64("" + issuesCOMDto.get_id()));

				issueCom.setISSUE_DOCUMENTNAME(issuesCOMDto.getISSUE_DOCUMENTNAME());
				issueCom.setISSUE_FILENAME(issuesCOMDto.getISSUE_FILENAME());
				issueCom.setISSUE_UPLOADDATE_STR(dateFormat.format(issuesCOMDto.getISSUE_UPLOADDATE()));
				

				issueCom.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesCOMDto.getISSUE_ID()));
				Dtos.add(issueCom);
			}
		}
		return Dtos;
	}

	/**
	 * @param get_Issues_ID_Photo_Details
	 * @return
	 */
	public List<IssuesPhotoDto> Showing_IssuesPhoto_Details(List<org.fleetopgroup.persistence.document.IssuesPhoto> issuesPhoto) {

		List<IssuesPhotoDto> Dtos = null;
		if (issuesPhoto != null && !issuesPhoto.isEmpty()) {
			Dtos = new ArrayList<IssuesPhotoDto>();
			IssuesPhotoDto issueCom = null;
			for (org.fleetopgroup.persistence.document.IssuesPhoto issuesCOMDto : issuesPhoto) {
				issueCom = new IssuesPhotoDto();

				issueCom.setISSUE_PHOTOID(issuesCOMDto.get_id());

				issueCom.setISSUES_PHOTOID_ENCRYPT(
						AESEncryptDecrypt.encryptBase64("" + issuesCOMDto.get_id()));

				issueCom.setISSUE_UPLOADDATE(issuesCOMDto.getISSUE_UPLOADDATE());
				issueCom.setISSUE_UPLOADDATE_STR(dateFormat.format(issuesCOMDto.getISSUE_UPLOADDATE()));
				issueCom.setISSUE_PHOTONAME(issuesCOMDto.getISSUE_PHOTONAME());
				issueCom.setISSUE_FILENAME(issuesCOMDto.getISSUE_FILENAME());

				issueCom.setISSUE_ID(issuesCOMDto.getISSUE_ID());
				issueCom.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + issuesCOMDto.getISSUE_ID()));
				Dtos.add(issueCom);
			}
		}
		return Dtos;
	}

	public IssuesTasks prepareIssueTaskForServiceEnteries(ValueObject valueObject, Issues save_Issues, CustomUserDetails userDetails)throws Exception  {
		try {
			IssuesTasks issuesTasks = new IssuesTasks();
			issuesTasks.setISSUES_TASK_STATUS_ID(valueObject.getShort("ISSUES_STATUS_ID"));
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_SE_CREATED);//checky
			issuesTasks.setISSUES_CREATEBY_ID(userDetails.getId());
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(save_Issues);
			issuesTasks.setCOMPANY_ID(userDetails.getCompany_id());
			issuesTasks.setISSUES_REASON("CREATED via Issues to Service Entries OPEN STATUS"); //checky
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}

	public Issues prepareIssuesDetails(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		Issues 					issue 				= null;
		Date 					currentDate  		= null;
		Timestamp 				currnetTimeStamp 	= null;
		SimpleDateFormat        dateFormat          = null;
		try {
			issue 				= new Issues();
			currentDate 		= new Date();                                             
			currnetTimeStamp 	= new java.sql.Timestamp(currentDate.getTime()); 
			dateFormat			= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			issue.setISSUES_ID(valueObject.getLong("issuesId"));
			issue.setISSUES_NUMBER(valueObject.getLong("issuesNumber",0));
			issue.setISSUE_TYPE_ID(valueObject.getShort("issueType",(short)0));
			issue.setISSUES_VID(valueObject.getInt("vid",0));
			issue.setISSUES_ODOMETER(valueObject.getInt("odometer",0));
			issue.setGPS_ODOMETER(valueObject.getDouble("gpsOdometer",0));
			issue.setVEHICLE_GROUP_ID(valueObject.getLong("vGroup",0));
			issue.setISSUES_DRIVER_ID(valueObject.getInt("driverId",0));
			issue.setCUSTOMER_NAME(valueObject.getString("customerName",""));
			issue.setISSUES_BRANCH_ID(valueObject.getInt("issuesBranch",0));
			issue.setISSUES_DEPARTNMENT_ID(valueObject.getInt("issuesDepartnment",0));
			if(valueObject.getBoolean("timeStampDate",false)) {
				issue.setISSUES_REPORTED_DATE(dateFormat.parse(valueObject.getString("reportdDate")));
			}else {
				issue.setISSUES_REPORTED_DATE(DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("reportdDate"),valueObject.getString("issueStartTime")));
			}
			issue.setISSUES_SUMMARY(valueObject.getString("issuesSummary",""));
			issue.setISSUES_LABELS_ID(valueObject.getShort("issueLabel",(short)0));
			issue.setISSUES_REPORTED_BY_ID(valueObject.getLong("reportedById",0));
			issue.setISSUES_ASSIGN_TO(valueObject.getString("subscribe",""));
			issue.setISSUES_ASSIGN_TO_NAME(valueObject.getString("subscribe",""));
			issue.setISSUES_DESCRIPTION(valueObject.getString("description",""));
			if(valueObject.getShort("issueStatusID",(short)0) > 0) {
				issue.setISSUES_STATUS_ID(valueObject.getShort("issueStatusID"));	
			}else {
				issue.setISSUES_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_OPEN);
			}
			issue.setCREATED_DATE(currnetTimeStamp);
			issue.setLASTUPDATED_DATE(currnetTimeStamp);
			issue.setMarkForDelete(false);
			issue.setCREATEDBYID(userDetails.getId());
			issue.setLASTMODIFIEDBYID(userDetails.getId());
			issue.setCOMPANY_ID(userDetails.getCompany_id());
			
			issue.setISSUES_WORKORDER_DATE(currnetTimeStamp);// just because in the Issue Model nullable is false
			issue.setVehicleCurrentOdometer(valueObject.getInt("vehicleCurrentOdometer",0)); // while saving issue the current odometer set in service file and for editing this will use
			issue.setCompanyReference(valueObject.getString("companyReference","")); // This column is used when Issues has been made from IV Cargo.
			issue.setCategoryId(valueObject.getInt("partCategory",0)); // This column is used when Issues has been made from IV Cargo.
			issue.setRouteID(valueObject.getInt("FuelRouteList",0)); // This column is used when Issues has been made from IV Cargo.
			issue.setLocation(valueObject.getString("breakDownLocation",""));
			issue.setIssueLP_ID(valueObject.getLong("LP_ID"));
			return issue;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
			
		}
		
	}
	
	public IssuesTasks prepareIssuesTaskDetails(Issues save_Issues, CustomUserDetails userDetails)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			
			if(save_Issues.getISSUES_SE_ID() != null) {
				issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
				issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_SE_CREATED);
				issuesTasks.setISSUES_REASON("SE Created Via Issue"); 
			} else if(save_Issues.getISSUES_WORKORDER_ID() != null) {
				issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
				issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_WOCREATED);
				issuesTasks.setISSUES_REASON("WO Created Via Issue"); 
			}
			issuesTasks.setISSUES_CREATEBY_ID(userDetails.getId());
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(save_Issues);
			issuesTasks.setCOMPANY_ID(userDetails.getCompany_id());
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}
	
	
	public IssuesTasks prepareIssuesTaskDetailsForDSE(Issues save_Issues, DealerServiceEntries dealerServiceEntries)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			
			issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED);
			issuesTasks.setISSUES_REASON("DSE Created Via Issue"); 
			issuesTasks.setISSUES_CREATEBY_ID(dealerServiceEntries.getCreatedById());
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(save_Issues);
			issuesTasks.setCOMPANY_ID(dealerServiceEntries.getCompanyId());
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}
	public IssuesTasks prepareIssuesTaskDetailsForDSEDelete(Issues save_Issues, ValueObject valueObject)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			
			issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_DSE_CREATED);
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
			issuesTasks.setISSUES_REASON("DSE Deleted"); 
			issuesTasks.setISSUES_CREATEBY_ID(valueObject.getLong("userId"));
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(save_Issues);
			issuesTasks.setCOMPANY_ID(valueObject.getInt("companyId"));
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}

	public IssuesTasks prepareIssuesTaskDetailsForDSEComplete(Issues issue, ValueObject valueObject)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			
			issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_DSE_CREATED);
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_RESOLVED);
			issuesTasks.setISSUES_REASON("Issue Resolved From DSE"); 
			issuesTasks.setISSUES_CREATEBY_ID(valueObject.getLong("userId"));
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(issue);
			issuesTasks.setCOMPANY_ID(valueObject.getInt("companyId"));
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}
	
	public static IssueBreakDownDetails getIssueBreakDownDetailsDTO(ValueObject	valueObject, Issues	issues) throws Exception {
		IssueBreakDownDetails breakDownDetails  = new IssueBreakDownDetails();
		java.util.Date 		currentDateUpdate 	= new java.util.Date();
		Timestamp 			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		CustomUserDetails 	userDetails         = null; 
		try {
			if(valueObject.get("userDetails") == null) {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}else {
				userDetails  = (CustomUserDetails) valueObject.get("userDetails");               
			}
			breakDownDetails.setIssueId(issues.getISSUES_ID());
			breakDownDetails.setTripNumber(valueObject.getString("tripSheetNumber"));
			breakDownDetails.setBreakDownLocation(valueObject.getString("breakDownLocation"));
			breakDownDetails.setIsVehicleReplaced(valueObject.getBoolean("vehicleReplaced",false));
			breakDownDetails.setReplacedWithVid(valueObject.getInt("replacedVehicle",0));
			breakDownDetails.setVehicleReplacedLocation(valueObject.getString("replaceLocation"));
			breakDownDetails.setIsTripCancelled(valueObject.getBoolean("tripCancelled",false));
			breakDownDetails.setCancelledKM(valueObject.getDouble("cancelledKM",0));
			breakDownDetails.setCompanyId(issues.getCOMPANY_ID());
			if(valueObject.getBoolean("fromSave", false)) {
				breakDownDetails.setCreationOn(toDate);
				breakDownDetails.setCreatedById(userDetails.getId());
			}
			// This is from change issue Type
			if(valueObject.getBoolean("changedToBreakDown",false)) {
				breakDownDetails.setChangedToBreakDown(true);
				breakDownDetails.setCreationOn(toDate);
				breakDownDetails.setCreatedById(userDetails.getId());
			}
			breakDownDetails.setLastUpdatedOn(toDate);
			breakDownDetails.setLastModifiedById(userDetails.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakDownDetails;
		
	}
	
	public List<IssuesDto> prepareIssueListSubscriberWise(List<IssuesDto> issues) throws Exception {
		List<IssuesDto> Dtos = null;
		if (issues != null && !issues.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto issue = null;
			for (IssuesDto issuesDto : issues) {
				issue = new IssuesDto();

				issue.setISSUES_ID(issuesDto.getISSUES_ID());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				issue.setIssuesNumberStr(issuesDto.getIssuesNumberStr());
				issue.setISSUES_VEHICLE_REGISTRATION(issuesDto.getISSUES_VEHICLE_REGISTRATION());
				issue.setISSUES_VEHICLE_GROUP(issuesDto.getISSUES_VEHICLE_GROUP());
				issue.setISSUES_ODOMETER(issuesDto.getISSUES_ODOMETER());

				issue.setISSUES_DRIVER_ID(issuesDto.getISSUES_DRIVER_ID());
				issue.setISSUES_DRIVER_NAME(issuesDto.getISSUES_DRIVER_NAME());
				issue.setDriver_mobnumber(issuesDto.getDriver_mobnumber());

				issue.setISSUES_BRANCH_ID(issuesDto.getISSUES_BRANCH_ID());
				issue.setISSUES_BRANCH_NAME(issuesDto.getISSUES_BRANCH_NAME());

				issue.setISSUES_DEPARTNMENT_ID(issuesDto.getISSUES_DEPARTNMENT_ID());
				issue.setISSUES_DEPARTNMENT_NAME(issuesDto.getISSUES_DEPARTNMENT_NAME());

				if (issuesDto.getISSUES_REPORTED_DATE_ON() != null) {
					issue.setIssuesReportedDateStr(dateFormat.format(issuesDto.getISSUES_REPORTED_DATE_ON()));
				}
				issue.setISSUES_SUMMARY(issuesDto.getISSUES_SUMMARY());
				issue.setISSUES_DESCRIPTION(issuesDto.getISSUES_DESCRIPTION());
				issue.setISSUES_LABELS_ID(issuesDto.getISSUES_LABELS_ID());
				issue.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(issuesDto.getISSUES_LABELS_ID()));
				issue.setISSUES_REPORTED_BY(issuesDto.getISSUES_REPORTED_BY());
				issue.setISSUES_ASSIGN_TO_NAME(issuesDto.getISSUES_ASSIGN_TO_NAME());
				issue.setAssignByName(issuesDto.getAssignByName());
				issue.setISSUES_STATUS_ID(issuesDto.getISSUES_STATUS_ID());
				issue.setISSUES_STATUS(IssuesTypeConstant.getStatusName(issuesDto.getISSUES_STATUS_ID()));

				issue.setCREATEDBY(issuesDto.getCREATEDBY());
				issue.setAssignByName(issuesDto.getCREATEDBY());
				
				issue.setLASTMODIFIEDBY(issuesDto.getLASTMODIFIEDBY());

				if (issuesDto.getCREATED_DATE_ON() != null) {
					issue.setCREATED_DATE(dateFormatonlyDateTime.format(issuesDto.getCREATED_DATE_ON()));

					switch (issuesDto.getISSUES_STATUS_ID()) {
					case IssuesTypeConstant.ISSUES_STATUS_OPEN:
						java.util.Date currentDateNow = new java.util.Date();

						issue.setISSUES_DIFFERENCES_DATE(
								getDateDifferenceInDDMMYYYY(issuesDto.getCREATED_DATE_ON(), currentDateNow));
						break;

					default:

						issue.setISSUES_DIFFERENCES_DATE(getDateDifferenceInDDMMYYYY(issuesDto.getCREATED_DATE_ON(),
								issuesDto.getLASTUPDATED_DATE_ON()));
						break;
					}

				}
				
				if (issuesDto.getLASTUPDATED_DATE_ON() != null) {
					issue.setLASTUPDATED_DATE(dateFormatonlyDateTime.format(issuesDto.getLASTUPDATED_DATE_ON()));
					if(issuesDto.getISSUES_STATUS_ID() == IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED)
						issue.setResolvingDate(dateFormat1.format(issuesDto.getLASTUPDATED_DATE_ON()));
				}
				issue.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(issuesDto.getISSUES_TYPE_ID()));
				issue.setISSUES_TYPE_ID(issuesDto.getISSUES_TYPE_ID());
				issue.setISSUES_VID(issuesDto.getISSUES_VID());
				if(issuesDto.getISSUES_WORKORDER_ID() != null) {
					issue.setISSUES_WORKORDER_ID(issuesDto.getISSUES_WORKORDER_ID());
				}else {
					issue.setISSUES_WORKORDER_ID((long) 0);
				}
				
				issue.setCREATEDBYID(issuesDto.getCREATEDBYID());
				issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
				issue.setISSUES_NUMBER(issuesDto.getISSUES_NUMBER());
				issue.setCREATEDBYID(issuesDto.getCREATEDBYID());
				issue.setISSUES_ASSIGN_TO(issuesDto.getISSUES_ASSIGN_TO());
				issue.setVehicleType(issuesDto.getVehicleType());
				issue.setVehicleMaker(issuesDto.getVehicleMaker());
				issue.setVehicleModel(issuesDto.getVehicleModel());
				issue.setIssueAnalysisId(issuesDto.getIssueAnalysisId());
				issue.setIssueAnalysis(issuesDto.isIssueAnalysis());
				issue.setRouteName(issuesDto.getRouteName());
				issue.setTripNumber(issuesDto.getTripNumber());
				issue.setLocation(issuesDto.getLocation());
				issue.setPartCategoryName(issuesDto.getPartCategoryName());
				issue.setReason(issuesDto.getReason());
				issue.setTempSolution(issuesDto.getTempSolution());
				issue.setFixSolution(issuesDto.getFixSolution());
				issue.setFuturePlan(issuesDto.getFuturePlan());
				if(issuesDto.isAvoidable()) {
					issue.setAvoidableStr("YES");
				}else {
					issue.setAvoidableStr("NO");
				}
				if(issuesDto.getIsVehicleReplaced() != null && issuesDto.getIsVehicleReplaced()) {
					issue.setVehicleStatus("YES");	
				}else {
					issue.setVehicleStatus("NO");	
				}
				issue.setReplacedVehicle(issuesDto.getReplacedVehicle());
				issue.setVehicleReplacedLocation(issuesDto.getVehicleReplacedLocation());
				if(issuesDto.getIsTripCancelled() != null && issuesDto.getIsTripCancelled()) {
					issue.setTriripCancelledStatus("YES");	
				}else {
					issue.setTriripCancelledStatus("NO");	
				}
				issue.setCancelledKM(issuesDto.getCancelledKM());
				issue.setRemark(issuesDto.getRemark());
				if(issuesDto.getIssueChangedOn() != null)
				issue.setIssueChangedDate(dateFormat1.format(issuesDto.getIssueChangedOn()));
				issue.setWoOrDseLink(issuesDto.getWoOrDseLink());
				issue.setVehicleModel(issuesDto.getVehicleModel());
				
				issue.setTripNumber(issuesDto.getTripNumber());
				issue.setBreakDownLocation(issuesDto.getBreakDownLocation());
				issue.setIsVehicleReplaced(issuesDto.getIsVehicleReplaced());
				issue.setReplacedWithVid(issuesDto.getReplacedWithVid());
				issue.setVehicleReplacedLocation(issuesDto.getVehicleReplacedLocation());
				issue.setIsTripCancelled(issuesDto.getIsTripCancelled());
				issue.setCancelledKM(issuesDto.getCancelledKM());
				issue.setReplacedWithVehicle(issuesDto.getReplacedWithVehicle());
				issue.setVehicleReplacedStr(issuesDto.getVehicleReplacedStr());
				issue.setTripCancelledStr(issuesDto.getTripCancelledStr());
				issue.setTransactionNumber(issuesDto.getTransactionNumber());
				
				Dtos.add(issue);
			}
		}
		return Dtos;
	}
	
	
	
	public IssuesTasks prepareTaskDetailsForIssueTypeChange(Issues saveIssues ,CustomUserDetails userDetails)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_TYPE_CHANGED);
			issuesTasks.setISSUES_REASON("Issue Type Changed To VehicleBreakDown"); 
			issuesTasks.setISSUES_CREATEBY_ID(userDetails.getId());
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(saveIssues);
			issuesTasks.setCOMPANY_ID(userDetails.getCompany_id());
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}
	
	public IssuesTasks prepareIssuesTaskDetailsForDSEReopen(Issues issue, ValueObject valueObject)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			
			issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_RESOLVED);
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
			issuesTasks.setISSUES_REASON("DSE Reopen"); 
			issuesTasks.setISSUES_CREATEBY_ID(valueObject.getLong("userId"));
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(issue);
			issuesTasks.setCOMPANY_ID(valueObject.getInt("companyId"));
			return issuesTasks;
		}catch(Exception e){
			LOGGER.error("ERR"+e);
			throw e;
		}
		
	}
}
