/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.dto.TripDailyAllGroupDayDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.fleetopgroup.persistence.model.TripDailyGroupCollection;
import org.fleetopgroup.persistence.model.TripDailyRouteSheet;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fleetop
 *
 */
public class TripDailyBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");

	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static double round(Double value, int places) {
		if(value == null) {
			value = 0.0;
		}
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public TripDailyBL() {
		super();
	}

	public TripDailySheet prepareModel_Save_TripDailySheet(TripDailySheetDto CollectionDto) {

		TripDailySheet status = new TripDailySheet();

		status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
		status.setVEHICLEID(CollectionDto.getVEHICLEID());

		try {

			status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

			java.util.Date date = dateFormat.parse(CollectionDto.getTRIP_OPEN_DATE());
			java.sql.Date fromDate = new Date(date.getTime());
			status.setTRIP_OPEN_DATE(fromDate);

			if (CollectionDto.getTRIP_ROUTE_ID() != 0) {
				status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
			} else {
				status.setTRIP_ROUTE_ID(0);
			}

			if (CollectionDto.getTRIP_DRIVER_ID() != 0) {
				status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
			} else {
				status.setTRIP_DRIVER_ID(0);
			}

			if (CollectionDto.getTRIP_CONDUCTOR_ID() != 0) {
				status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
			} else {
				status.setTRIP_CONDUCTOR_ID(0);
			}

			if (CollectionDto.getTRIP_CLEANER_ID() != 0) {
				status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
			} else {
				status.setTRIP_CLEANER_ID(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (CollectionDto.getTRIP_TOTALPASSNGER() != null) {
			status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
		} else {
			status.setTRIP_TOTALPASSNGER(0);
		}
		if (CollectionDto.getTRIP_PASS_PASSNGER() != null) {
			status.setTRIP_PASS_PASSNGER(CollectionDto.getTRIP_PASS_PASSNGER());
		} else {
			status.setTRIP_PASS_PASSNGER(0);
		}

		if (CollectionDto.getTRIP_RFIDPASS() != null) {
			status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());
		} else {
			status.setTRIP_RFIDPASS(0);
		}
		if (CollectionDto.getTRIP_RFID_AMOUNT() != null) {
			status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());
		} else {
			status.setTRIP_RFID_AMOUNT(0.0);
		}
		if (CollectionDto.getTRIP_OVERTIME() != null) {
			status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());
		} else {
			status.setTRIP_OVERTIME(0.0);
		}
		status.setTRIP_DIESEL(0.0);
		status.setTRIP_DIESELKMPL(0.0);

		status.setTOTAL_BALANCE(0.0);
		status.setTOTAL_EXPENSE(0.0);

		status.setTOTAL_INCOME_COLLECTION(0.0);
		status.setTOTAL_INCOME(0.0);

		status.setTOTAL_NET_INCOME(0.0);
		status.setTOTAL_WT(0.0);

		status.setTRIP_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_OPEN);

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		status.setCREATED(toDate);
		status.setLASTUPDATED(toDate);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		status.setCREATED_BY_ID(userDetails.getId());
		status.setLASTMODIFIED_BY_ID(userDetails.getId());

		status.setMarkForDelete(false);
		status.setCOMPANY_ID(userDetails.getCompany_id());

		return status;
	}

	/**
	 * @param collectionSheetDto
	 * @param oldCollectionSheet
	 * @return
	 */
	public TripDailySheet prepare_Model_UPDATE_TripCollectionSheet(TripDailySheetDto CollectionDto,
			TripDailySheetDto OLDCollectionDto) {
		// update the TripSheet Model

		TripDailySheet status = new TripDailySheet();

		status.setTRIPDAILYID(OLDCollectionDto.getTRIPDAILYID());
		status.setTRIPDAILYNUMBER(OLDCollectionDto.getTRIPDAILYNUMBER());
		status.setVEHICLEID(CollectionDto.getVEHICLEID());

		try {

			status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

			java.util.Date date = dateFormat.parse(CollectionDto.getTRIP_OPEN_DATE());
			java.sql.Date fromDate = new Date(date.getTime());
			status.setTRIP_OPEN_DATE(fromDate);

			if (CollectionDto.getTRIP_ROUTE_ID() != 0) {
				status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
			} else {
				status.setTRIP_ROUTE_ID(0);
			}

			if (CollectionDto.getTRIP_DRIVER_ID() != 0) {
				status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
			} else {
				status.setTRIP_DRIVER_ID(0);
			}

			if (CollectionDto.getTRIP_CONDUCTOR_ID() != 0) {
				status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
			} else {
				status.setTRIP_CONDUCTOR_ID(0);
			}

			if (CollectionDto.getTRIP_CLEANER_ID() != 0) {
				status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
			} else {
				status.setTRIP_CLEANER_ID(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
		status.setTRIP_PASS_PASSNGER(CollectionDto.getTRIP_PASS_PASSNGER());

		status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());
		status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());

		if (CollectionDto.getTRIP_OVERTIME() != null) {
			status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());
		} else {
			status.setTRIP_OVERTIME(OLDCollectionDto.getTRIP_OVERTIME());
		}

		status.setTRIP_SUBROUTE_ID(OLDCollectionDto.getTRIP_SUBROUTE_ID());

		status.setTRIP_DIESEL(OLDCollectionDto.getTRIP_DIESEL());
		status.setTRIP_DIESELKMPL(OLDCollectionDto.getTRIP_DIESELKMPL());

		status.setTOTAL_BALANCE(OLDCollectionDto.getTOTAL_BALANCE());
		status.setTOTAL_EXPENSE(OLDCollectionDto.getTOTAL_EXPENSE());

		status.setTOTAL_INCOME_COLLECTION(OLDCollectionDto.getTOTAL_INCOME_COLLECTION());
		status.setTOTAL_INCOME(OLDCollectionDto.getTOTAL_INCOME());
		status.setTOTAL_WT(OLDCollectionDto.getTOTAL_WT());

		status.setTOTAL_NET_INCOME(OLDCollectionDto.getTOTAL_NET_INCOME());

		status.setTRIP_STATUS_ID(OLDCollectionDto.getTRIP_STATUS_ID());

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		status.setCREATED(OLDCollectionDto.getCREATEDON());
		status.setLASTUPDATED(toDate);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		status.setCOMPANY_ID(userDetails.getCompany_id());
		status.setLASTMODIFIED_BY_ID(userDetails.getId());
		status.setCREATED_BY_ID(OLDCollectionDto.getCREATED_BY_ID());
		status.setMarkForDelete(false);

		return status;
	}

	/**
	 * @param tripCollection
	 * @return
	 */
	public List<TripDailySheetDto> prepare_TripDailySheetDto(List<TripDailySheetDto> tripCollection) {

		List<TripDailySheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto status = null;
			for (TripDailySheetDto CollectionDto : tripCollection) {
				status = new TripDailySheetDto();

				status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
				status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
				status.setVEHICLEID(CollectionDto.getVEHICLEID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_D()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
				status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS(
						TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_STATUS_ID()));

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	/**
	 * @param tripCollection
	 * @return
	 */
	public List<TripDailySheetDto> prepare_TripDailySheetDto_GET(List<TripDailySheet> tripCollection) {

		List<TripDailySheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto status = null;
			for (TripDailySheet CollectionDto : tripCollection) {
				status = new TripDailySheetDto();

				status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
				status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
				status.setVEHICLEID(CollectionDto.getVEHICLEID());

				// status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				// status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE()));
				}
				// status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
				status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS(
						TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_STATUS_ID()));

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	public List<TripDailySheetDto> prepare_TripDailySheetList(List<TripDailySheetDto> tripCollection) {

		List<TripDailySheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto status = null;
			for (TripDailySheetDto CollectionDto : tripCollection) {
				status = new TripDailySheetDto();

				status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
				status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
				status.setVEHICLEID(CollectionDto.getVEHICLEID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_D()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
				status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS(
						TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_STATUS_ID()));

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	public List<TripDailySheetDto> prepare_TripDailySheetDtoList(List<TripDailySheetDto> tripCollection) {

		List<TripDailySheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto status = null;
			for (TripDailySheetDto CollectionDto : tripCollection) {
				status = new TripDailySheetDto();

				status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
				status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
				status.setVEHICLEID(CollectionDto.getVEHICLEID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_D()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
				status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());
				status.setTRIP_STATUS_ID(CollectionDto.getTRIP_STATUS_ID());

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	/**
	 * @param tripCol
	 * @return
	 */
	/*
	 * public TripDailySheetDto Show_TripDailySheet_page(TripDailySheet
	 * CollectionDto) {
	 * 
	 * TripDailySheetDto status = new TripDailySheetDto();
	 * 
	 * status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
	 * status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
	 * status.setVEHICLEID(CollectionDto.getVEHICLEID());
	 * 
	 * status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
	 * status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
	 * status.setVEHICLE_GROUP_ID(CollectionDto.getVEHICLE_GROUP_ID());
	 * status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());
	 * 
	 * status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
	 * status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());
	 * 
	 * if (CollectionDto.getTRIP_OPEN_DATE() != null) {
	 * status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.
	 * getTRIP_OPEN_DATE())); }
	 * status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
	 * status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());
	 * status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
	 * status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
	 * 
	 * status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
	 * status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
	 * 
	 * // System.out.println(TripSheetDto.getTripCleanerID());
	 * status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
	 * status.setTRIP_CLEANER_NAME(CollectionDto.getTRIP_CLEANER_NAME());
	 * 
	 * status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
	 * status.setTRIP_PASS_PASSNGER(CollectionDto.getTRIP_PASS_PASSNGER());
	 * 
	 * status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());
	 * status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());
	 * 
	 * status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());
	 * 
	 * status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
	 * status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL());
	 * 
	 * status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
	 * status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
	 * 
	 * status.setTOTAL_INCOME_COLLECTION(CollectionDto.getTOTAL_INCOME_COLLECTION())
	 * ; status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());
	 * 
	 * status.setTOTAL_NET_INCOME(CollectionDto.getTOTAL_NET_INCOME());
	 * 
	 * status.setTOTAL_WT(CollectionDto.getTOTAL_WT());
	 * 
	 * status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());
	 * 
	 * status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());
	 * 
	 * status.setCREATEDBY(CollectionDto.getCREATEDBY());
	 * status.setLASTMODIFIEDBY(CollectionDto.getLASTMODIFIEDBY());
	 * status.setmarkForDelete(CollectionDto.ismarkForDelete());
	 * 
	 *//** Set Created Current Date */

	/*
	 * if (CollectionDto.getCREATED() != null) {
	 *//** Set Created Current Date *//*
										 * status.setCREATED(CreatedDateTime.format(CollectionDto.getCREATED())); } if
										 * (CollectionDto.getLASTUPDATED() != null) {
										 * status.setLASTUPDATED(CreatedDateTime.format(CollectionDto.getLASTUPDATED()))
										 * ; } return status; }
										 * 
										 */
	public TripDailySheetDto Show_TripDailySheet_page(TripDailySheetDto CollectionDto) {
		TripDailySheetDto status = null;
		if(CollectionDto != null) {
			status = new TripDailySheetDto();

			status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
			status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
			status.setVEHICLEID(CollectionDto.getVEHICLEID());

			status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
			status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
			status.setVEHICLE_GROUP_ID(CollectionDto.getVEHICLE_GROUP_ID());
			status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

			status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
			status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());

			if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
				status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_D()));
				status.setTripDate(dateFormat.format(CollectionDto.getTRIP_OPEN_DATE_D()));
			}
			status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
			status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());
			status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
			status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());

			status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
			status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());

			// System.out.println(TripSheetDto.getTripCleanerID());
			status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
			status.setTRIP_CLEANER_NAME(CollectionDto.getTRIP_CLEANER_NAME());

			status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
			status.setTRIP_PASS_PASSNGER(CollectionDto.getTRIP_PASS_PASSNGER());

			status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());
			status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());

			status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

			status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
			status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL());

			status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
			status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());

			status.setTOTAL_INCOME_COLLECTION(CollectionDto.getTOTAL_INCOME_COLLECTION());
			status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

			status.setTOTAL_NET_INCOME(CollectionDto.getTOTAL_NET_INCOME());

			status.setTOTAL_WT(CollectionDto.getTOTAL_WT());

			status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());

			status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());
				status.setCREATEDBY(CollectionDto.getCREATEDBY());
			status.setLASTMODIFIEDBY(CollectionDto.getLASTMODIFIEDBY());
			status.setMarkForDelete(CollectionDto.isMarkForDelete());
			status.setNoOfRoundTrip(CollectionDto.getNoOfRoundTrip());
			status.setCHALO_KM(CollectionDto.getCHALO_KM());
			status.setCHALO_AMOUNT(CollectionDto.getCHALO_AMOUNT());

		/** Set Created Current Date */
			/** Set Created Current Date */
			if (CollectionDto.getCREATEDON() != null) {
				/** Set Created Current Date */
				status.setCREATED(CreatedDateTime.format(CollectionDto.getCREATEDON()));
			}
			if (CollectionDto.getLASTUPDATEDON() != null) {
				status.setLASTUPDATED(CreatedDateTime.format(CollectionDto.getLASTUPDATEDON()));
			}
	}
		return status;
		
	}

	/**
	 * @param tripCol
	 * @param balance
	 * @return
	 */
	public TripDailyRouteSheet Collection_Trip_toAdd_RouteCollection(TripDailySheetDto tripCol, Integer TRIP_USAGE_KM,
			Double TOTAL_NET_INCOME, Double balance) {

		TripDailyRouteSheet group = new TripDailyRouteSheet();

		group.setTRIP_OPEN_DATE(tripCol.getTRIP_OPEN_DATE_D());
		group.setTRIP_ROUTE_ID(tripCol.getTRIP_ROUTE_ID());
		// group.setTRIP_ROUTE_NAME(tripCol.getTRIP_ROUTE_NAME());
		// group.setVEHICLE_GROUP(tripCol.getVEHICLE_GROUP());
		group.setVEHICLE_GROUP_ID(tripCol.getVEHICLE_GROUP_ID());

		group.setTOTAL_WT(tripCol.getTOTAL_WT());
		group.setTOTAL_COLLECTION(tripCol.getTOTAL_INCOME());
		group.setTOTAL_NET_COLLECTION(round(TOTAL_NET_INCOME, 2));

		group.setTOTAL_EXPENSE(round(tripCol.getTOTAL_EXPENSE(), 2));
		group.setTOTAL_OVERTIME(tripCol.getTRIP_OVERTIME());
		group.setTOTAL_BALANCE(round(balance, 2));

		group.setTOTAL_RFIDPASS(tripCol.getTRIP_RFIDPASS());
		group.setTOTAL_RFID_AMOUNT(tripCol.getTRIP_RFID_AMOUNT());

		group.setTOTAL_TOTALPASSNGER(tripCol.getTRIP_TOTALPASSNGER());
		group.setTOTAL_PASS_PASSNGER(tripCol.getTRIP_PASS_PASSNGER());

		group.setTOTAL_DIESEL(round(tripCol.getTRIP_DIESEL(), 2));
		group.setTOTAL_USAGE_KM(TRIP_USAGE_KM);
		group.setTOTAL_BUS(1);
		// group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.TRIP_STATUS_OPEN_NAME);
		group.setTRIP_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_OPEN);
		return group;
	}

	/**
	 * @param groupSheet
	 * @param collectionSheet
	 * @return
	 */
	public List<TripDailyRouteSheetDto> CloseDaily_tripDailyRoute_Sheet(List<TripDailyRouteSheetDto> groupSheet,
			List<TripDailySheetDto> collectionSheet) {

		List<TripDailyRouteSheetDto> DTO = null;
		// for loop trip_Group_sheet
		if (groupSheet != null && !groupSheet.isEmpty()) {
			DTO = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto group = null;
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {
				group = new TripDailyRouteSheetDto();
				// for loop trip_collection_sheet
				if (collectionSheet != null && !collectionSheet.isEmpty()) {
					TripDailyRouteSheetDto tripCol = null;
					for (TripDailySheetDto tripSheet : collectionSheet) {
						tripCol = new TripDailyRouteSheetDto();
						if (Integer.parseInt(tripGroupCollection.getTRIP_ROUTE_ID()+"") == Integer.parseInt(tripSheet.getTRIP_ROUTE_ID()+"")) {

							tripCol.setTRIPROUTEID(tripSheet.getTRIPDAILYID());
							tripCol.setTRIP_ROUTE_NAME(tripSheet.getTRIP_ROUTE_NAME());

							tripCol.setTRIP_DRIVER_NAME(tripSheet.getTRIP_DRIVER_NAME());
							tripCol.setTRIP_CONDUCTOR_NAME(tripSheet.getTRIP_CONDUCTOR_NAME());

							tripCol.setTRIP_CLOSE_STATUS(tripSheet.getVEHICLE_REGISTRATION());
							if (tripSheet.getTOTAL_INCOME() != null) {
								tripCol.setTOTAL_COLLECTION(round(tripSheet.getTOTAL_INCOME(), 2));
							}
							if(tripSheet.getTOTAL_WT() != null) {
								tripCol.setTOTAL_WT(round(tripSheet.getTOTAL_WT(), 2));
							}
							
							if(tripSheet.getTOTAL_NET_INCOME() != null)
								tripCol.setTOTAL_NET_COLLECTION(round(tripSheet.getTOTAL_NET_INCOME(), 2));

							Double Total_collection = 0.0, Total_EPK = 0.0;

							Integer Total_KM = 0;

							if (tripSheet.getTOTAL_INCOME() != null) {
								Total_collection = tripSheet.getTOTAL_INCOME();
							}

							if (tripSheet.getTRIP_USAGE_KM() != null) {
								Total_KM = tripSheet.getTRIP_USAGE_KM();
							}
							if (Total_KM != 0.0) {
								Total_EPK = Total_collection / Total_KM;
							}
							tripCol.setTOTAL_EPK(round(Total_EPK, 2));
							if(tripSheet.getTOTAL_EXPENSE() != null) {
							tripCol.setTOTAL_EXPENSE(tripSheet.getTOTAL_EXPENSE());
							}
							tripCol.setTOTAL_BALANCE(tripSheet.getTOTAL_BALANCE());
							tripCol.setTOTAL_OVERTIME(tripSheet.getTRIP_OVERTIME());

							tripCol.setTOTAL_RFIDPASS(tripSheet.getTRIP_RFIDPASS());
							tripCol.setTOTAL_RFID_AMOUNT(tripSheet.getTRIP_RFID_AMOUNT());

							tripCol.setTOTAL_TOTALPASSNGER(tripSheet.getTRIP_TOTALPASSNGER());
							tripCol.setTOTAL_PASS_PASSNGER(tripSheet.getTRIP_PASS_PASSNGER());

							tripCol.setTOTAL_USAGE_KM(tripSheet.getTRIP_USAGE_KM());
							tripCol.setTOTAL_DIESEL(tripSheet.getTRIP_DIESEL());
							// this Vehicle_Gruop Change Diesel KML
							tripCol.setTOTAL_DIESELKML(tripSheet.getTRIP_DIESELKMPL());
							tripCol.setTOTAL_BUS(null);
							if(tripSheet.getCHALO_KM() != null) {
							tripCol.setCHALO_KM(tripSheet.getCHALO_KM());
							}
							if(tripSheet.getCHALO_AMOUNT() != null) {
							tripCol.setCHALO_AMOUNT(tripSheet.getCHALO_AMOUNT());
							}

							DTO.add(tripCol);
						}
					} // close for loop trip collection sheet

				} // close if loop trip collection sheet

				group.setTRIPROUTEID(null);
				// here Route name Show Vehicle Group in total
				group.setTRIP_ROUTE_NAME(tripGroupCollection.getTRIP_ROUTE_NAME());

				group.setTRIP_DRIVER_NAME(null);
				group.setTRIP_CONDUCTOR_NAME(null);

				group.setTRIP_CLOSE_STATUS("TOTAL:");
				if(tripGroupCollection.getTOTAL_COLLECTION() != null)
					group.setTOTAL_COLLECTION(round(tripGroupCollection.getTOTAL_COLLECTION(), 2));
				if(tripGroupCollection.getTOTAL_WT() != null)
					group.setTOTAL_WT(round(tripGroupCollection.getTOTAL_WT(), 2));
				if(tripGroupCollection.getTOTAL_NET_COLLECTION() != null)
					group.setTOTAL_NET_COLLECTION(round(tripGroupCollection.getTOTAL_NET_COLLECTION(), 2));

				Double Total_collection_group = 0.0, Total_EPK_group = 0.0;

				Integer Total_KM_group = 0;

				if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
					Total_collection_group = tripGroupCollection.getTOTAL_COLLECTION();
				}

				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					Total_KM_group = tripGroupCollection.getTOTAL_USAGE_KM();
				}

				if (Total_KM_group != 0.0) {
					Total_EPK_group = Total_collection_group / Total_KM_group;
				}
				group.setTOTAL_EPK(round(Total_EPK_group, 2));
				if(tripGroupCollection.getTOTAL_EXPENSE() != null)
					group.setTOTAL_EXPENSE(round(tripGroupCollection.getTOTAL_EXPENSE(), 2));
				if(tripGroupCollection.getTOTAL_BALANCE() != null)
					group.setTOTAL_BALANCE(round(tripGroupCollection.getTOTAL_BALANCE(), 2));
				group.setTOTAL_OVERTIME(tripGroupCollection.getTOTAL_OVERTIME());

				group.setTOTAL_RFIDPASS(tripGroupCollection.getTOTAL_RFIDPASS());
				group.setTOTAL_RFID_AMOUNT(tripGroupCollection.getTOTAL_RFID_AMOUNT());

				group.setTOTAL_TOTALPASSNGER(tripGroupCollection.getTOTAL_TOTALPASSNGER());
				group.setTOTAL_PASS_PASSNGER(tripGroupCollection.getTOTAL_PASS_PASSNGER());

				group.setTOTAL_USAGE_KM(tripGroupCollection.getTOTAL_USAGE_KM());
				if(tripGroupCollection.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripGroupCollection.getTOTAL_DIESEL(), 2));
				// this Vehicle_Gruop Change Diesel KML total null
				Double KMPL = 0.0, TOTAL_DIESEL = 0.0;
				Integer TOTAL_USAGE_KM = 0;
				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					TOTAL_USAGE_KM = tripGroupCollection.getTOTAL_USAGE_KM();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null) {

					TOTAL_DIESEL = tripGroupCollection.getTOTAL_DIESEL();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null && tripGroupCollection.getTOTAL_DIESEL() != 0.0) {
					KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
				}
				group.setTOTAL_DIESELKML(round(KMPL, 2));
				group.setTOTAL_BUS(null);

				DTO.add(group);
			}
		}

		return DTO;
	}

	public List<TripDailyRouteSheetDto> CloseDaily_tripDailyRoute_Sheet(List<TripDailyRouteSheetDto> groupSheet,
			List<TripDailySheetDto> collectionSheet, HashMap<String, TripDailySheetDto>  fuelAmountHm) {

		List<TripDailyRouteSheetDto> DTO = null;
		// for loop trip_Group_sheet
		if (groupSheet != null && !groupSheet.isEmpty()) {
			DTO = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto group = null;
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {
				group = new TripDailyRouteSheetDto();
				// for loop trip_collection_sheet
				if (collectionSheet != null && !collectionSheet.isEmpty()) {
					TripDailyRouteSheetDto tripCol = null;
					for (TripDailySheetDto tripSheet : collectionSheet) {
						tripCol = new TripDailyRouteSheetDto();
						if (Integer.parseInt(tripGroupCollection.getTRIP_ROUTE_ID()+"") == Integer.parseInt(tripSheet.getTRIP_ROUTE_ID()+"")) {

							tripCol.setTRIPROUTEID(tripSheet.getTRIPDAILYID());
							tripCol.setTRIP_ROUTE_NAME(tripSheet.getTRIP_ROUTE_NAME());

							tripCol.setTRIP_DRIVER_NAME(tripSheet.getTRIP_DRIVER_NAME());
							tripCol.setTRIP_CONDUCTOR_NAME(tripSheet.getTRIP_CONDUCTOR_NAME());

							tripCol.setTRIP_CLOSE_STATUS(tripSheet.getVEHICLE_REGISTRATION());
							if (tripSheet.getTOTAL_INCOME() != null) {
								tripCol.setTOTAL_COLLECTION(round(tripSheet.getTOTAL_INCOME(), 2));
							}
							if(tripSheet.getTOTAL_WT() != null) {
								tripCol.setTOTAL_WT(round(tripSheet.getTOTAL_WT(), 2));
							}
							
							if(tripSheet.getTOTAL_NET_INCOME() != null)
								tripCol.setTOTAL_NET_COLLECTION(round(tripSheet.getTOTAL_NET_INCOME(), 2));

							Double Total_collection = 0.0, Total_EPK = 0.0;

							Integer Total_KM = 0;

							if (tripSheet.getTOTAL_INCOME() != null) {
								Total_collection = tripSheet.getTOTAL_INCOME();
							}

							if (tripSheet.getTRIP_USAGE_KM() != null) {
								Total_KM = tripSheet.getTRIP_USAGE_KM();
							}
							if (Total_KM != 0.0) {
								Total_EPK = Total_collection / Total_KM;
							}
							tripCol.setTOTAL_EPK(round(Total_EPK, 2));
							tripCol.setTOTAL_EXPENSE(tripSheet.getTOTAL_EXPENSE());
							if(tripSheet.getTOTAL_EXPENSE() != null) {
								tripCol.setTOTAL_BALANCE(round(Total_collection - tripSheet.getTOTAL_EXPENSE(), 2));
							}else {
								tripCol.setTOTAL_BALANCE(round(Total_collection, 2));
							}
								
							tripCol.setTOTAL_OVERTIME(tripSheet.getTRIP_OVERTIME());
							
							if(fuelAmountHm.get(tripSheet.getTRIPDAILYID()+"") != null) {
								tripCol.setTOTAL_DIESEL_AMOUNT(fuelAmountHm.get(tripSheet.getTRIPDAILYID()+"").getTRIP_DIESEL_AMOUNT());
							}else {
								tripCol.setTOTAL_DIESEL_AMOUNT(0.0);
							}
							
							tripCol.setTOTAL_RFIDPASS(tripSheet.getTRIP_RFIDPASS());
							tripCol.setTOTAL_RFID_AMOUNT(tripSheet.getTRIP_RFID_AMOUNT());

							tripCol.setTOTAL_TOTALPASSNGER(tripSheet.getTRIP_TOTALPASSNGER());
							tripCol.setTOTAL_PASS_PASSNGER(tripSheet.getTRIP_PASS_PASSNGER());

							tripCol.setTOTAL_USAGE_KM(tripSheet.getTRIP_USAGE_KM());
							tripCol.setTOTAL_DIESEL(tripSheet.getTRIP_DIESEL());
							// this Vehicle_Gruop Change Diesel KML
							tripCol.setTOTAL_DIESELKML(tripSheet.getTRIP_DIESELKMPL());
							tripCol.setTOTAL_BUS(null);
							if(tripSheet.getCHALO_KM() != null) {
							tripCol.setCHALO_KM(tripSheet.getCHALO_KM());
							}
							if(tripSheet.getCHALO_AMOUNT() != null) {
							tripCol.setCHALO_AMOUNT(tripSheet.getCHALO_AMOUNT());
							}

							DTO.add(tripCol);
						}
					} // close for loop trip collection sheet

				} // close if loop trip collection sheet

				group.setTRIPROUTEID(null);
				// here Route name Show Vehicle Group in total
				group.setTRIP_ROUTE_NAME(tripGroupCollection.getTRIP_ROUTE_NAME());

				group.setTRIP_DRIVER_NAME(null);
				group.setTRIP_CONDUCTOR_NAME(null);

				group.setTRIP_CLOSE_STATUS("TOTAL:");
				if(tripGroupCollection.getTOTAL_COLLECTION() != null)
					group.setTOTAL_COLLECTION(round(tripGroupCollection.getTOTAL_COLLECTION(), 2));
				if(tripGroupCollection.getTOTAL_WT() != null)
					group.setTOTAL_WT(round(tripGroupCollection.getTOTAL_WT(), 2));
				if(tripGroupCollection.getTOTAL_NET_COLLECTION() != null)
					group.setTOTAL_NET_COLLECTION(round(tripGroupCollection.getTOTAL_NET_COLLECTION(), 2));

				Double Total_collection_group = 0.0, Total_EPK_group = 0.0;

				Integer Total_KM_group = 0;

				if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
					Total_collection_group = tripGroupCollection.getTOTAL_COLLECTION();
				}

				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					Total_KM_group = tripGroupCollection.getTOTAL_USAGE_KM();
				}

				if (Total_KM_group != 0.0) {
					Total_EPK_group = Total_collection_group / Total_KM_group;
				}
				group.setTOTAL_EPK(round(Total_EPK_group, 2));
				if(tripGroupCollection.getTOTAL_EXPENSE() != null)
					group.setTOTAL_EXPENSE(round(tripGroupCollection.getTOTAL_EXPENSE(), 2));
				if(tripGroupCollection.getTOTAL_BALANCE() != null)
					group.setTOTAL_BALANCE(round(tripGroupCollection.getTOTAL_BALANCE(), 2));
				group.setTOTAL_OVERTIME(tripGroupCollection.getTOTAL_OVERTIME());

				group.setTOTAL_RFIDPASS(tripGroupCollection.getTOTAL_RFIDPASS());
				group.setTOTAL_RFID_AMOUNT(tripGroupCollection.getTOTAL_RFID_AMOUNT());

				group.setTOTAL_TOTALPASSNGER(tripGroupCollection.getTOTAL_TOTALPASSNGER());
				group.setTOTAL_PASS_PASSNGER(tripGroupCollection.getTOTAL_PASS_PASSNGER());

				group.setTOTAL_USAGE_KM(tripGroupCollection.getTOTAL_USAGE_KM());
				if(tripGroupCollection.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripGroupCollection.getTOTAL_DIESEL(), 2));
				// this Vehicle_Gruop Change Diesel KML total null
				Double KMPL = 0.0, TOTAL_DIESEL = 0.0;
				Integer TOTAL_USAGE_KM = 0;
				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					TOTAL_USAGE_KM = tripGroupCollection.getTOTAL_USAGE_KM();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null) {

					TOTAL_DIESEL = tripGroupCollection.getTOTAL_DIESEL();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null && tripGroupCollection.getTOTAL_DIESEL() != 0.0) {
					KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
				}
				group.setTOTAL_DIESELKML(round(KMPL, 2));
				group.setTOTAL_BUS(null);

				DTO.add(group);
			}
		}

		return DTO;
	}

	
	
	/**
	 * @param groupSheet
	 * @return
	 */
	public Double[] Group_Total_details_to_TripDaily_all_total(List<TripDailyRouteSheetDto> groupSheet) {

		Double[] totalCollection = new Double[13];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalKMPL = 0.0, TotalPassenger = 0.0, TotalRFID = 0.0,
				TotalRFID_AMOUNT = 0.0, TotalCollection = 0.0, TotalWT = 0.0, Total_NET_Collection = 0.0,
				TotalEPK = 0.0, TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				}
				if (tripGroupCollection.getTOTAL_TOTALPASSNGER() != null) {
					TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				}

				if (tripGroupCollection.getTOTAL_RFIDPASS() != null) {
					TotalRFID += tripGroupCollection.getTOTAL_RFIDPASS();
				}
				if (tripGroupCollection.getTOTAL_RFID_AMOUNT() != null) {
					TotalRFID_AMOUNT += tripGroupCollection.getTOTAL_RFID_AMOUNT();
				}
				if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
					TotalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				}
				if (tripGroupCollection.getTOTAL_WT() != null) {
					TotalWT += tripGroupCollection.getTOTAL_WT();
				}
				if (tripGroupCollection.getTOTAL_NET_COLLECTION() != null) {
					Total_NET_Collection += tripGroupCollection.getTOTAL_NET_COLLECTION();
				}
				if (tripGroupCollection.getTOTAL_EXPENSE() != null) {
					TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				}
				if (tripGroupCollection.getTOTAL_OVERTIME() != null) {
					TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				}
				if (tripGroupCollection.getTOTAL_BALANCE() != null) {
					TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
				}
			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TotalKMPL = TotalUsageKM / TotalDiesel;
		totalCollection[2] = round(TotalKMPL, 2);
		totalCollection[3] = round(TotalPassenger, 2);
		totalCollection[4] = round(TotalRFID, 2);
		totalCollection[5] = round(TotalRFID_AMOUNT, 2);
		totalCollection[6] = round(TotalCollection, 2);
		totalCollection[7] = round(TotalWT, 2);
		totalCollection[8] = round(Total_NET_Collection, 2);

		TotalEPK = Total_NET_Collection / TotalUsageKM;
		totalCollection[9] = round(TotalEPK, 2);
		totalCollection[10] = round(TotalExpense, 2);
		totalCollection[11] = round(TotalOT, 2);
		totalCollection[12] = round(TotalBalance, 2);

		return totalCollection;
	}

	/**
	 * @param groupSheet
	 * @return
	 */
	public TripDailyGroupCollection prepare_TripDailyGroupCollection_to_TripDaily_all_total(
			List<TripDailyRouteSheetDto> groupSheet) {
		TripDailyGroupCollection GroupCollection = new TripDailyGroupCollection();

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalPassenger = 0.0, TotalPass = 0.0, TotalRFID = 0.0,
				TotalRFIDAmount = 0.0, TotalCollection = 0.0, TotalWT = 0.0, TotalNetCollection = 0.0,
				TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {
				if(tripGroupCollection.getTOTAL_USAGE_KM() != null)
					TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				if(tripGroupCollection.getTOTAL_DIESEL() != null)
					TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				if(tripGroupCollection.getTOTAL_TOTALPASSNGER() != null)
					TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				
				if(tripGroupCollection.getTOTAL_PASS_PASSNGER() != null)
					TotalPass += tripGroupCollection.getTOTAL_PASS_PASSNGER();
				if(tripGroupCollection.getTOTAL_RFIDPASS() != null)
					TotalRFID += tripGroupCollection.getTOTAL_RFIDPASS();
				if(tripGroupCollection.getTOTAL_RFID_AMOUNT() != null)
					TotalRFIDAmount += tripGroupCollection.getTOTAL_RFID_AMOUNT();
				if(tripGroupCollection.getTOTAL_COLLECTION() != null)
					TotalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				if(tripGroupCollection.getTOTAL_WT() != null)
					TotalWT += tripGroupCollection.getTOTAL_WT();
				if(tripGroupCollection.getTOTAL_NET_COLLECTION() != null)
					TotalNetCollection += tripGroupCollection.getTOTAL_NET_COLLECTION();
				
				if(tripGroupCollection.getTOTAL_EXPENSE() != null)
					TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				if(tripGroupCollection.getTOTAL_OVERTIME() != null)
					TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				if(tripGroupCollection.getTOTAL_BALANCE() != null)
					TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
			}
		}
		GroupCollection.setTOTAL_USAGE_KM(Integer.valueOf(TotalUsageKM.intValue()));
		GroupCollection.setTOTAL_DIESEL(round(TotalDiesel, 2));
		GroupCollection.setTOTAL_TOTALPASSNGER(Integer.valueOf(TotalPassenger.intValue()));
		GroupCollection.setTOTAL_PASS_PASSNGER(Integer.valueOf(TotalPass.intValue()));

		GroupCollection.setTOTAL_RFIDPASS(Integer.valueOf(TotalRFID.intValue()));

		GroupCollection.setTOTAL_RFID_AMOUNT(round(TotalRFIDAmount, 2));

		GroupCollection.setTOTAL_COLLECTION(round(TotalCollection, 2));
		GroupCollection.setTOTAL_WT(round(TotalWT, 2));
		GroupCollection.setTOTAL_NET_COLLECTION(round(TotalNetCollection, 2));

		GroupCollection.setTOTAL_EXPENSE(round(TotalExpense, 2));
		GroupCollection.setTOTAL_OVERTIME(TotalOT);
		GroupCollection.setTOTAL_BALANCE(round(TotalBalance, 2));

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		GroupCollection.setCREATED(toDate);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		// GroupCollection.setCREATEDBY(userDetails.getEmail());
		GroupCollection.setCREATED_BY_ID(userDetails.getId());
		GroupCollection.setmarkForDelete(false);

		return GroupCollection;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double[] Tota_TripDaily_Sheet_total(List<TripDailySheet> groupSheet) {

		Double[] totalCollection = new Double[13];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TatalKMPL = 0.0, TotalPassenger = 0.0, TotalRFID = 0.0,
				TotalRFIDAmount = 0.0, TotalCollection = 0.0, TotalWT = 0.0, TotalNetCollection = 0.0, TotalEPK = 0.0,
				TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailySheet tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTRIP_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTRIP_USAGE_KM();
				}
				if (tripGroupCollection.getTRIP_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTRIP_DIESEL();
				}
				if (tripGroupCollection.getTRIP_TOTALPASSNGER() != null) {
					TotalPassenger += tripGroupCollection.getTRIP_TOTALPASSNGER();
				}
				if (tripGroupCollection.getTRIP_RFIDPASS() != null) {
					TotalRFID += tripGroupCollection.getTRIP_RFIDPASS();
				}
				if (tripGroupCollection.getTRIP_RFID_AMOUNT() != null) {
					TotalRFIDAmount += tripGroupCollection.getTRIP_RFID_AMOUNT();
				}
				if (tripGroupCollection.getTOTAL_INCOME() != null) {
					TotalCollection += tripGroupCollection.getTOTAL_INCOME();
				}
				if (tripGroupCollection.getTOTAL_WT() != null) {
					TotalWT += tripGroupCollection.getTOTAL_WT();
				}
				if (tripGroupCollection.getTOTAL_NET_INCOME() != null) {
					TotalNetCollection += tripGroupCollection.getTOTAL_NET_INCOME();
				}

				if (tripGroupCollection.getTOTAL_EXPENSE() != null) {
					TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				}
				if (tripGroupCollection.getTRIP_OVERTIME() != null) {
					TotalOT += tripGroupCollection.getTRIP_OVERTIME();
				}
				if (tripGroupCollection.getTOTAL_BALANCE() != null) {
					TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
				}
			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TatalKMPL = TotalUsageKM / TotalDiesel;
		totalCollection[2] = round(TatalKMPL, 2);
		totalCollection[3] = round(TotalPassenger, 2);
		totalCollection[4] = round(TotalRFID, 2);
		totalCollection[5] = round(TotalRFIDAmount, 2);
		totalCollection[6] = round(TotalCollection, 2);
		totalCollection[7] = round(TotalWT, 2);
		totalCollection[8] = round(TotalNetCollection, 2);
		TotalEPK = TotalCollection / TotalUsageKM;
		totalCollection[9] = round(TotalEPK, 2);
		totalCollection[10] = round(TotalExpense, 2);
		totalCollection[11] = round(TotalOT, 2);
		totalCollection[12] = round(TotalBalance, 2);

		return totalCollection;
	}

	/**
	 * @param collection
	 * @return
	 *//*
		 * public List<TripDailySheetDto>
		 * prepare_TripDailySheetDto_Report(List<TripDailySheet> tripCollection) {
		 * 
		 * List<TripDailySheetDto> Dtos = null; if (tripCollection != null &&
		 * !tripCollection.isEmpty()) { Dtos = new ArrayList<TripDailySheetDto>();
		 * TripDailySheetDto status = null; for (TripDailySheet CollectionDto :
		 * tripCollection) { status = new TripDailySheetDto();
		 * 
		 * status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
		 * status.setVEHICLEID(CollectionDto.getVEHICLEID());
		 * 
		 * status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
		 * status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
		 * status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
		 * status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
		 * status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());
		 * status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
		 * status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());
		 * 
		 * status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
		 * status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL()); if
		 * (CollectionDto.getTRIP_OPEN_DATE() != null) {
		 * status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.
		 * getTRIP_OPEN_DATE())); }
		 * status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());
		 * 
		 * status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
		 * status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());
		 * 
		 * status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());
		 * status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());
		 * status.setTOTAL_WT(CollectionDto.getTOTAL_WT());
		 * status.setTOTAL_NET_INCOME(CollectionDto.getTOTAL_NET_INCOME());
		 * 
		 * status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());
		 * 
		 * status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
		 * status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
		 * 
		 * Double Total_collection_group = 0.0, Total_RFIDAmount_group = 0.0,
		 * Total_EPK_group = 0.0;
		 * 
		 * Integer Total_KM_group = 0;
		 * 
		 * if (CollectionDto.getTOTAL_INCOME() != null) { Total_collection_group =
		 * CollectionDto.getTOTAL_INCOME(); }
		 * 
		 * if (CollectionDto.getTRIP_RFID_AMOUNT() != null) { Total_RFIDAmount_group =
		 * CollectionDto.getTRIP_RFID_AMOUNT(); }
		 * 
		 * if (CollectionDto.getTRIP_USAGE_KM() != null) { Total_KM_group =
		 * CollectionDto.getTRIP_USAGE_KM(); } if (Total_KM_group != 0.0) {
		 * Total_EPK_group = (Total_collection_group + Total_RFIDAmount_group) /
		 * Total_KM_group; } status.setTOTAL_EPK(round(Total_EPK_group, 2));
		 * 
		 * status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());
		 * 
		 * Dtos.add(status); } } return Dtos; }
		 */

	public List<TripDailySheetDto> prepare_TripDailySheetDto_ReportList(List<TripDailySheetDto> tripCollection) {

		List<TripDailySheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto status = null;
			for (TripDailySheetDto CollectionDto : tripCollection) {
				status = new TripDailySheetDto();

				status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
				status.setVEHICLEID(CollectionDto.getVEHICLEID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
				status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());
				status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
				status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());

				status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
				status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL());
				
				status.setDAY_OF_MONTH(simpleDateFormat.format(CollectionDto.getTRIP_OPEN_DATE_D()).toUpperCase());//dev y
			
				if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_D()));
				}
				
		
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
				status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());

				status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());
				status.setTOTAL_WT(CollectionDto.getTOTAL_WT());
				status.setTOTAL_NET_INCOME(CollectionDto.getTOTAL_NET_INCOME());

				status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());

				Double Total_collection_group = 0.0, Total_RFIDAmount_group = 0.0, Total_EPK_group = 0.0;

				Integer Total_KM_group = 0;

				if (CollectionDto.getTOTAL_INCOME() != null) {
					Total_collection_group = CollectionDto.getTOTAL_INCOME();
				}

				if (CollectionDto.getTRIP_RFID_AMOUNT() != null) {
					Total_RFIDAmount_group = CollectionDto.getTRIP_RFID_AMOUNT();
				}

				if (CollectionDto.getTRIP_USAGE_KM() != null) {
					Total_KM_group = CollectionDto.getTRIP_USAGE_KM();
				}
				if (Total_KM_group != 0.0) {
					Total_EPK_group = (Total_collection_group + Total_RFIDAmount_group) / Total_KM_group;
				}
				status.setTOTAL_EPK(round(Total_EPK_group, 2));

				status.setTRIP_CLOSE_STATUS(
						TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_STATUS_ID()));
				status.setTRIP_STATUS_ID(CollectionDto.getTRIP_STATUS_ID());

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	/**
	 * @param get_Showing_TripDaily_Sheet
	 * @return
	 */
	public TripDailySheetDto Update_TripSheet(TripDailySheetDto CollectionDto) {

		TripDailySheetDto status = new TripDailySheetDto();

		status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
		status.setTRIPDAILYNUMBER(CollectionDto.getTRIPDAILYNUMBER());
		status.setVEHICLEID(CollectionDto.getVEHICLEID());

		status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
		status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
		status.setVEHICLE_GROUP_ID(CollectionDto.getVEHICLE_GROUP_ID());
		status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

		status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
		status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());

		if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
			status.setTRIP_OPEN_DATE(dateFormat.format(CollectionDto.getTRIP_OPEN_DATE_D()));
		}
		status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
		status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());
		status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
		status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());

		status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
		status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());

		// System.out.println(TripSheetDto.getTripCleanerID());
		status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
		status.setTRIP_CLEANER_NAME(CollectionDto.getTRIP_CLEANER_NAME());

		status.setTRIP_TOTALPASSNGER(CollectionDto.getTRIP_TOTALPASSNGER());
		status.setTRIP_PASS_PASSNGER(CollectionDto.getTRIP_PASS_PASSNGER());

		status.setTRIP_RFIDPASS(CollectionDto.getTRIP_RFIDPASS());
		status.setTRIP_RFID_AMOUNT(CollectionDto.getTRIP_RFID_AMOUNT());

		status.setTRIP_OVERTIME(CollectionDto.getTRIP_OVERTIME());

		status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
		status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL());

		status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
		status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
		status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

		status.setTOTAL_WT(CollectionDto.getTOTAL_WT());
		status.setTOTAL_NET_INCOME(CollectionDto.getTOTAL_NET_INCOME());

		status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());

		status.setCREATEDBY(CollectionDto.getCREATEDBY());
		status.setLASTMODIFIEDBY(CollectionDto.getLASTMODIFIEDBY());
		status.setMarkForDelete(CollectionDto.isMarkForDelete());

		/** Set Created Current Date */
		if (CollectionDto.getCREATEDON() != null) {
			/** Set Created Current Date */
			status.setCREATED(CreatedDateTime.format(CollectionDto.getCREATEDON()));
		}
		if (CollectionDto.getLASTUPDATEDON() != null) {
			status.setLASTUPDATED(CreatedDateTime.format(CollectionDto.getLASTUPDATEDON()));
		}
		return status;
	}

	/**
	 * @param collection
	 * @return
	 */
	public List<TripDailyRouteSheetDto> prepare_TripDailyRouteSheet_CollectionDto(
			List<TripDailyRouteSheetDto> tripCollection) {

		List<TripDailyRouteSheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto group = null;
			for (TripDailyRouteSheetDto tripCol : tripCollection) {
				group = new TripDailyRouteSheetDto();
				
				if (tripCol.getTRIP_OPEN_DATE_ON() != null) {
					group.setTRIP_OPEN_DATE(dateFormat_Name.format(tripCol.getTRIP_OPEN_DATE_ON()));
				}
				group.setTRIPROUTEID(tripCol.getTRIPROUTEID());
				group.setTRIP_ROUTE_NAME(tripCol.getTRIP_ROUTE_NAME());
				group.setVEHICLE_GROUP(tripCol.getVEHICLE_GROUP());

				if(tripCol.getTOTAL_EXPENSE() != null)
					group.setTOTAL_EXPENSE(round(tripCol.getTOTAL_EXPENSE(), 2));
				if(tripCol.getTOTAL_OVERTIME() != null)
					group.setTOTAL_OVERTIME(round(tripCol.getTOTAL_OVERTIME(), 2));
				if(tripCol.getTOTAL_BALANCE() != null)
					group.setTOTAL_BALANCE(round(tripCol.getTOTAL_BALANCE(), 2));

				group.setTOTAL_RFIDPASS(tripCol.getTOTAL_RFIDPASS());
				if(tripCol.getTOTAL_RFID_AMOUNT() != null)
					group.setTOTAL_RFID_AMOUNT(round(tripCol.getTOTAL_RFID_AMOUNT(), 2));
				if(tripCol.getTOTAL_COLLECTION() != null)
					group.setTOTAL_COLLECTION(round(tripCol.getTOTAL_COLLECTION(), 2));
				if(tripCol.getTOTAL_WT() != null)
					group.setTOTAL_WT(round(tripCol.getTOTAL_WT(), 2));
				if(tripCol.getTOTAL_NET_COLLECTION() != null)
					group.setTOTAL_NET_COLLECTION(round(tripCol.getTOTAL_NET_COLLECTION(), 2));

				group.setTOTAL_TOTALPASSNGER(tripCol.getTOTAL_TOTALPASSNGER());
				if(tripCol.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripCol.getTOTAL_DIESEL(), 2));
				group.setTOTAL_USAGE_KM(tripCol.getTOTAL_USAGE_KM());

				Double KMPL = 0.0, TOTAL_DIESEL = 0.0, TOTAL_COLLECTION = 0.0, TOTAL_EPK = 0.0;
				Integer TOTAL_USAGE_KM = 0;
				if (tripCol.getTOTAL_USAGE_KM() != null) {
					TOTAL_USAGE_KM = tripCol.getTOTAL_USAGE_KM();
				}
				if (tripCol.getTOTAL_DIESEL() != null) {

					TOTAL_DIESEL = tripCol.getTOTAL_DIESEL();
				}
				if (tripCol.getTOTAL_DIESEL() != null && tripCol.getTOTAL_DIESEL() != 0.0) {
					KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
				}

				if (tripCol.getTOTAL_COLLECTION() != null) {
					TOTAL_COLLECTION = tripCol.getTOTAL_COLLECTION();
				}

				TOTAL_EPK = TOTAL_COLLECTION / TOTAL_USAGE_KM;

				group.setTOTAL_EPK(round(TOTAL_EPK, 2));
				group.setTOTAL_DIESELKML(round(KMPL, 2));
				group.setTOTAL_BUS(1);
				group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(tripCol.getTRIP_STATUS_ID()));
				group.setTRIP_STATUS_ID(tripCol.getTRIP_STATUS_ID());

				Dtos.add(group);
			}
		}

		return Dtos;
	}

	/**
	 * @param totalGroupSheet
	 * @return
	 */
	public TripDailyAllGroupDay prepare_TD_GroupCollection_To_All_TD_Group_Day_Collection(
			TripDailyGroupCollection totalGroupSheet) {

		TripDailyAllGroupDay AllGroup = new TripDailyAllGroupDay();

		if (totalGroupSheet != null) {
			AllGroup.setADVANCE(0);
			AllGroup.setBOOKING(0.0);

			// Below BUSCOLLECTION Amount is All Group Location One day Balance
			// Amount
			AllGroup.setBUSCOLLECTION(totalGroupSheet.getTOTAL_BALANCE());

			AllGroup.setRFIDCARD(0.0);
			AllGroup.setRFIDRCG(0.0);
			AllGroup.setRFIDUSAGE(0.0);

			AllGroup.setTOTAL_RFIDPASS(totalGroupSheet.getTOTAL_RFIDPASS());
			AllGroup.setTOTAL_RFID_AMOUNT(totalGroupSheet.getTOTAL_RFID_AMOUNT());

			AllGroup.setTOTAL_DIESELEXPENSE(0.0);

			// below Collection_Of Balance Is All Group Location Collection Of
			// One day BALANCE = BusCollection + RFID_RRED + RFID_Card +
			// RFID_Usage + Booking Amount ;
			// One Day Expense = Diesel_Expene + OverTime Amount + Advance
			// Amount ;
			// One Day COLLECTION_BALANCE = BALANCE - Expense;
			AllGroup.setCOLLECTION_BALANCE(0.0);
			AllGroup.setEXPENSE_DAY(0.0);
			AllGroup.setTOTAL_BALANCE(0.0);
			AllGroup.setTOTAL_DIESEL(totalGroupSheet.getTOTAL_DIESEL());
			AllGroup.setTOTAL_DIESEL_MILAGE(totalGroupSheet.getTOTAL_DIESEL_MILAGE());
			AllGroup.setTOTAL_OVERTIME(totalGroupSheet.getTOTAL_OVERTIME());

			AllGroup.setTOTAL_TOTALPASSNGER(totalGroupSheet.getTOTAL_TOTALPASSNGER());
			AllGroup.setTOTAL_PASS_PASSNGER(totalGroupSheet.getTOTAL_PASS_PASSNGER());

			AllGroup.setTOTAL_USAGE_KM(totalGroupSheet.getTOTAL_USAGE_KM());

			AllGroup.setTOTAL_WT(null);
			// AllGroup.setTRIP_CLOSE_STATUS(TripDailySheetStatus.TRIP_STATUS_OPEN_NAME);
			AllGroup.setTRIP_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_OPEN);
			AllGroup.setTRIP_OPEN_DATE(totalGroupSheet.getTRIP_OPEN_DATE());
			AllGroup.setTRIP_REMARKS("");
			AllGroup.setmarkForDelete(false);

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// AllGroup.setCREATEDBY(userDetails.getEmail());
			// AllGroup.setLASTMODIFIEDBY(userDetails.getEmail());
			AllGroup.setCREATED_BY_ID(userDetails.getId());
			AllGroup.setLASTMODIFIED_BY_ID(userDetails.getId());
			AllGroup.setCOMPANY_ID(userDetails.getCompany_id());

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp cREATED = new java.sql.Timestamp(currentDateUpdate.getTime());

			AllGroup.setCREATED(cREATED);
			AllGroup.setLASTUPDATED(cREATED);
		} else {
			return null;
		}

		return AllGroup;
	}

	/**
	 * @param pageList
	 * @return
	 */
	public List<TripDailyAllGroupDayDto> prepare_TripDailyAllGroupDayDto(List<TripDailyAllGroupDayDto> tripCollection) {

		List<TripDailyAllGroupDayDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailyAllGroupDayDto>();
			TripDailyAllGroupDayDto group = null;
			for (TripDailyAllGroupDayDto tripCol : tripCollection) {
				group = new TripDailyAllGroupDayDto();

				group.setTRIPALLGROUPID(tripCol.getTRIPALLGROUPID());
				group.setTRIPALLGROUPNUMBER(tripCol.getTRIPALLGROUPNUMBER());
				if (tripCol.getTRIP_OPEN_DATE_ON() != null) {
					group.setTRIP_OPEN_DATE(dateFormat_Name.format(tripCol.getTRIP_OPEN_DATE_ON()));
				}
				group.setADVANCE(tripCol.getADVANCE());
				group.setBOOKING(tripCol.getBOOKING());
				group.setBUSCOLLECTION(tripCol.getBUSCOLLECTION());
				group.setEXPENSE_DAY(tripCol.getEXPENSE_DAY());
				group.setRFIDCARD(tripCol.getRFIDCARD());
				group.setRFIDRCG(tripCol.getRFIDRCG());
				group.setRFIDUSAGE(tripCol.getRFIDUSAGE());
				group.setmarkForDelete(tripCol.ismarkForDelete());
				group.setTOTAL_BALANCE(tripCol.getTOTAL_BALANCE());
				group.setTOTAL_DIESEL(tripCol.getTOTAL_DIESEL());
				group.setTOTAL_DIESEL_MILAGE(tripCol.getTOTAL_DIESEL_MILAGE());
				group.setTOTAL_DIESELEXPENSE(tripCol.getTOTAL_DIESELEXPENSE());
				group.setTOTAL_OVERTIME(tripCol.getTOTAL_OVERTIME());
				group.setTOTAL_TOTALPASSNGER(tripCol.getTOTAL_TOTALPASSNGER());
				group.setTOTAL_USAGE_KM(tripCol.getTOTAL_USAGE_KM());
				group.setTOTAL_WT(tripCol.getTOTAL_WT());
				group.setTRIP_REMARKS(tripCol.getTRIP_REMARKS());
				group.setTRIP_CLOSE_STATUS(tripCol.getTRIP_CLOSE_STATUS());
				group.setTRIP_STATUS_ID(tripCol.getTRIP_STATUS_ID());

				Dtos.add(group);
			}
		}

		return Dtos;
	}

	/**
	 * @param dailyGroupCollection
	 * @return
	 */
	public List<TripDailyGroupCollectionDto> prepare_LIST_TripDailyGroupCollectionDto(
			List<TripDailyGroupCollectionDto> dailyGroupCollection) {

		List<TripDailyGroupCollectionDto> Dtos = null;
		if (dailyGroupCollection != null && !dailyGroupCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailyGroupCollectionDto>();
			TripDailyGroupCollectionDto group = null;
			for (TripDailyGroupCollectionDto tripCol : dailyGroupCollection) {
				group = new TripDailyGroupCollectionDto();

				group.setTRIPGROUPID(tripCol.getTRIPGROUPID());
				if (tripCol.getTRIP_OPEN_DATE_ON() != null) {
					group.setTRIP_OPEN_DATE(dateFormat_Name.format(tripCol.getTRIP_OPEN_DATE_ON()));
				}
				group.setVEHICLE_GROUP(tripCol.getVEHICLE_GROUP());
				group.setTOTAL_BALANCE(tripCol.getTOTAL_BALANCE());
				group.setTOTAL_BUS(tripCol.getTOTAL_BUS());
				if(tripCol.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripCol.getTOTAL_DIESEL(), 2));
				
				Double KMPL = 0.0, TOTAL_DIESEL = 0.0;
				Integer TOTAL_USAGE_KM = 0;
				if (tripCol.getTOTAL_USAGE_KM() != null) {
					TOTAL_USAGE_KM = tripCol.getTOTAL_USAGE_KM();
				}
				if (tripCol.getTOTAL_DIESEL() != null) {

					TOTAL_DIESEL = tripCol.getTOTAL_DIESEL();
				}
				if (tripCol.getTOTAL_DIESEL() != 0.0) {
					KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
				}
				group.setTOTAL_DIESEL_MILAGE(round(KMPL, 2));

				if(tripCol.getTOTAL_EXPENSE() != null)
					group.setTOTAL_EXPENSE(round(tripCol.getTOTAL_EXPENSE(), 2));
				if(tripCol.getTOTAL_OVERTIME() != null)
					group.setTOTAL_OVERTIME(round(tripCol.getTOTAL_OVERTIME(), 2));

				group.setTOTAL_RFIDPASS(tripCol.getTOTAL_RFIDPASS());
				group.setTOTAL_RFID_AMOUNT(tripCol.getTOTAL_RFID_AMOUNT());
				
				if(tripCol.getTOTAL_COLLECTION() != null)
					group.setTOTAL_COLLECTION(round(tripCol.getTOTAL_COLLECTION(), 2));
				if(tripCol.getTOTAL_WT() != null)
					group.setTOTAL_WT(round(tripCol.getTOTAL_WT(), 2));
				if(tripCol.getTOTAL_NET_COLLECTION() != null)
					group.setTOTAL_NET_COLLECTION(round(tripCol.getTOTAL_NET_COLLECTION(), 2));

				Double Total_collection_group = 0.0, Total_EPK_group = 0.0;

				Integer Total_KM_group = 0;

				if (tripCol.getTOTAL_COLLECTION() != null) {
					Total_collection_group = tripCol.getTOTAL_COLLECTION();
				}

				if (tripCol.getTOTAL_USAGE_KM() != null) {
					Total_KM_group = tripCol.getTOTAL_USAGE_KM();
				}
				Total_EPK_group = Total_collection_group / Total_KM_group;

				group.setTOTAL_EPK(round(Total_EPK_group, 2));

				group.setmarkForDelete(tripCol.ismarkForDelete());
				group.setTOTAL_RFIDPASS(tripCol.getTOTAL_RFIDPASS());
				group.setTOTAL_TOTALPASSNGER(tripCol.getTOTAL_TOTALPASSNGER());
				group.setTOTAL_USAGE_KM(tripCol.getTOTAL_USAGE_KM());
				group.setTRIP_CLOSE_REMARKS(tripCol.getTRIP_CLOSE_REMARKS());
				group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(tripCol.getTRIP_STATUS_ID()));
				group.setTRIP_STATUS_ID(tripCol.getTRIP_STATUS_ID());

				Dtos.add(group);
			}
		}
		return Dtos;
	}

	/**
	 * @param allGroupSheet
	 * @return
	 */
	public TripDailyAllGroupDayDto prepare_TripDailyAllGroupDay_Dto(TripDailyAllGroupDayDto allGroupSheet) {

		TripDailyAllGroupDayDto group = new TripDailyAllGroupDayDto();
		group.setTRIPALLGROUPID(allGroupSheet.getTRIPALLGROUPID());
		group.setTRIPALLGROUPNUMBER(allGroupSheet.getTRIPALLGROUPNUMBER());
		if (allGroupSheet.getTRIP_OPEN_DATE_ON() != null) {
			group.setTRIP_OPEN_DATE(dateFormat_SQL.format(allGroupSheet.getTRIP_OPEN_DATE_ON()));
		}
		group.setADVANCE(allGroupSheet.getADVANCE());
		group.setBOOKING(allGroupSheet.getBOOKING());
		if(allGroupSheet.getBUSCOLLECTION() != null)
			group.setBUSCOLLECTION(round(allGroupSheet.getBUSCOLLECTION(), 2));
		group.setEXPENSE_DAY(allGroupSheet.getEXPENSE_DAY());
		group.setRFIDCARD(allGroupSheet.getRFIDCARD());
		group.setRFIDRCG(allGroupSheet.getRFIDRCG());
		group.setRFIDUSAGE(allGroupSheet.getRFIDUSAGE());
		group.setmarkForDelete(allGroupSheet.ismarkForDelete());
		group.setCOLLECTION_BALANCE(allGroupSheet.getCOLLECTION_BALANCE());
		group.setTOTAL_BALANCE(allGroupSheet.getTOTAL_BALANCE());
		if(allGroupSheet.getTOTAL_DIESEL() != null)
			group.setTOTAL_DIESEL(round(allGroupSheet.getTOTAL_DIESEL(), 2));
		group.setTOTAL_DIESEL_MILAGE(allGroupSheet.getTOTAL_DIESEL_MILAGE());
		group.setTOTAL_DIESELEXPENSE(allGroupSheet.getTOTAL_DIESELEXPENSE());
		group.setTOTAL_OVERTIME(allGroupSheet.getTOTAL_OVERTIME());
		group.setTOTAL_TOTALPASSNGER(allGroupSheet.getTOTAL_TOTALPASSNGER());
		group.setTOTAL_USAGE_KM(allGroupSheet.getTOTAL_USAGE_KM());
		group.setTOTAL_WT(allGroupSheet.getTOTAL_WT());
		group.setTRIP_REMARKS(allGroupSheet.getTRIP_REMARKS());
		group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(allGroupSheet.getTRIP_STATUS_ID()));
		group.setTRIP_STATUS_ID(allGroupSheet.getTRIP_STATUS_ID());

		group.setTOTAL_DIESEL_MILAGE(allGroupSheet.getTOTAL_DIESEL_MILAGE());
		// Create and Last updated Display
		group.setCREATEDBY(allGroupSheet.getCREATEDBY());
		if (allGroupSheet.getCREATED_ON() != null) {
			group.setCREATED(CreatedDateTime.format(allGroupSheet.getCREATED_ON()));
		}
		group.setLASTMODIFIEDBY(allGroupSheet.getLASTMODIFIEDBY());
		if (allGroupSheet.getLASTUPDATED_ON() != null) {
			group.setLASTUPDATED(CreatedDateTime.format(allGroupSheet.getLASTUPDATED_ON()));
		}

		return group;
	}

	/**
	 * @param dailyAllGroupDayDto
	 * @return
	 */
	public TripDailyAllGroupDay prepare_Update_TD_GroupAll_Collection_Total(TripDailyAllGroupDayDto allGroupSheet) {

		Double Total_Income = 0.0, Total_expence = 0.0, Collection_Balance = 0.0, Total_Balance = 0.0;

		TripDailyAllGroupDay group = new TripDailyAllGroupDay();

		group.setTRIPALLGROUPID(allGroupSheet.getTRIPALLGROUPID());
		group.setTRIPALLGROUPNUMBER(allGroupSheet.getTRIPALLGROUPNUMBER());

		// Entries UI To Database Details

		// Below Calculation to Day Balance Details
		// Collection Balance = (BUS_COLLECTION + RFID_RCG + RFID_CARD +
		// RFID_USAGE + BOOKING ) -
		// (DIESEL_EXPENCE + OVER_TIME + ADVANCE )
		// Total Balance = ( Collection Balance - EXPENCE_of_DAY )
		Double BUS_COLLECTION = 0.0, RFID_RCG = 0.0, RFID_CARD = 0.0, RFID_USAGE = 0.0, BOOKING = 0.0,
				EXPENSE_DAY = 0.0, DIESEL_EXPENCE = 0.0, OVER_TIME = 0.0;
		Integer ADVANCE = 0;

		if (allGroupSheet.getBUSCOLLECTION() != null) {
			BUS_COLLECTION = allGroupSheet.getBUSCOLLECTION();
		}

		if (allGroupSheet.getRFIDRCG() != null) {
			RFID_RCG = allGroupSheet.getRFIDRCG();
		}

		if (allGroupSheet.getRFIDRCG() != null) {
			RFID_CARD = allGroupSheet.getRFIDRCG();
		}

		if (allGroupSheet.getRFIDUSAGE() != null) {
			RFID_USAGE = allGroupSheet.getRFIDUSAGE();
		}

		if (allGroupSheet.getBOOKING() != null) {
			BOOKING = allGroupSheet.getBOOKING();
		}

		if (allGroupSheet.getTOTAL_DIESELEXPENSE() != null) {
			DIESEL_EXPENCE = allGroupSheet.getTOTAL_DIESELEXPENSE();
		}

		if (allGroupSheet.getTOTAL_OVERTIME() != null) {
			OVER_TIME = allGroupSheet.getTOTAL_OVERTIME();
		}

		if (allGroupSheet.getADVANCE() != null) {
			ADVANCE = allGroupSheet.getADVANCE();
		}
		group.setRFIDCARD(RFID_RCG);
		group.setRFIDRCG(RFID_CARD);
		group.setRFIDUSAGE(RFID_USAGE);
		group.setBOOKING(BOOKING);

		group.setTOTAL_DIESELEXPENSE(DIESEL_EXPENCE);
		group.setTOTAL_OVERTIME(OVER_TIME);
		group.setADVANCE(ADVANCE);

		group.setTOTAL_WT(allGroupSheet.getTOTAL_WT());
		group.setTRIP_REMARKS(allGroupSheet.getTRIP_REMARKS());

		// Total_Income = (BUS_COLLECTION + RFID_RCG + RFID_CARD + RFID_USAGE +
		// BOOKING )
		Total_Income = BUS_COLLECTION + RFID_RCG + RFID_CARD + RFID_USAGE + BOOKING;
		// Total_expence = (DIESEL_EXPENCE + OVER_TIME + ADVANCE )
		Total_expence = DIESEL_EXPENCE + OVER_TIME + ADVANCE;

		// Collection Balance = Total_Income - Total_expence
		Collection_Balance = Total_Income - Total_expence;

		group.setCOLLECTION_BALANCE(round(Collection_Balance, 2));

		if (allGroupSheet.getEXPENSE_DAY() != null) {
			EXPENSE_DAY = allGroupSheet.getEXPENSE_DAY();
		}
		group.setEXPENSE_DAY(EXPENSE_DAY);
		// Total Balance = ( Collection Balance - EXPENCE_of_DAY )
		Total_Balance = Collection_Balance - EXPENSE_DAY;
		group.setTOTAL_BALANCE(round(Total_Balance, 2));

		group.setTOTAL_DIESEL_MILAGE(allGroupSheet.getTOTAL_DIESEL_MILAGE());

		// group.setTRIP_CLOSE_STATUS(allGroupSheet.getTRIP_CLOSE_STATUS());
		group.setTRIP_STATUS_ID(allGroupSheet.getTRIP_STATUS_ID());

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		// group.setLASTMODIFIEDBY(userDetails.getEmail());
		group.setCOMPANY_ID(userDetails.getCompany_id());

		group.setLASTMODIFIED_BY_ID(userDetails.getId());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp lASTUPDATED = new java.sql.Timestamp(currentDateUpdate.getTime());
		group.setLASTUPDATED(lASTUPDATED);

		return group;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double[] Tota_TripDailyGroupCollection_total(List<TripDailyGroupCollectionDto> collection) {

		Double[] totalCollection = new Double[8];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalPassenger = 0.0, TotalRFID = 0.0, TotalCollection = 0.0,
				TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripDailyGroupCollectionDto tripGroupCollection : collection) {

				TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				TotalRFID += tripGroupCollection.getTOTAL_RFIDPASS();
				TotalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
			}
		}

		totalCollection[0] = TotalUsageKM;
		totalCollection[1] = TotalDiesel;
		totalCollection[2] = TotalPassenger;
		totalCollection[3] = TotalRFID;
		totalCollection[4] = TotalCollection;
		totalCollection[5] = TotalExpense;
		totalCollection[6] = TotalOT;
		totalCollection[7] = TotalBalance;

		return totalCollection;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double[] Tota_TripDailyAllGroupDay_total(List<TripDailyAllGroupDay> collection) {

		Double[] totalCollection = new Double[10];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalPassenger = 0.0, TotalRFIDCARD = 0.0, TotalRFIDRCG = 0.0,
				TotalRFIDUSAGE = 0.0, TotalCollection = 0.0, TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripDailyAllGroupDay tripGroupCollection : collection) {

				TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				TotalRFIDCARD += tripGroupCollection.getRFIDCARD();
				TotalRFIDRCG += tripGroupCollection.getRFIDRCG();
				TotalRFIDUSAGE += tripGroupCollection.getRFIDUSAGE();
				TotalCollection += tripGroupCollection.getBUSCOLLECTION();
				TotalExpense += tripGroupCollection.getEXPENSE_DAY();
				TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
			}
		}

		totalCollection[0] = TotalUsageKM;
		totalCollection[1] = TotalDiesel;
		totalCollection[2] = TotalPassenger;
		totalCollection[3] = TotalRFIDCARD;
		totalCollection[4] = TotalRFIDRCG;
		totalCollection[5] = TotalRFIDUSAGE;
		totalCollection[6] = TotalCollection;
		totalCollection[7] = TotalExpense;
		totalCollection[8] = TotalOT;
		totalCollection[9] = TotalBalance;

		return totalCollection;
	}

	/**
	 * @param collection
	 * @return
	 */
	public List<TripDailyAllGroupDayDto> prepare_LIST_TripDailyAllGroupDayDto(
			List<TripDailyAllGroupDay> collectionAllDay) {

		List<TripDailyAllGroupDayDto> Dtos = null;
		if (collectionAllDay != null && !collectionAllDay.isEmpty()) {
			Dtos = new ArrayList<TripDailyAllGroupDayDto>();
			TripDailyAllGroupDayDto group = null;
			for (TripDailyAllGroupDay tripCol : collectionAllDay) {
				group = new TripDailyAllGroupDayDto();

				group.setTRIPALLGROUPID(tripCol.getTRIPALLGROUPID());
				if (tripCol.getTRIP_OPEN_DATE() != null) {
					group.setTRIP_OPEN_DATE(dateFormat_Name.format(tripCol.getTRIP_OPEN_DATE()));
				}
				if(tripCol.getTOTAL_BALANCE() != null)
					group.setTOTAL_BALANCE(round(tripCol.getTOTAL_BALANCE(), 2));
				if(tripCol.getBUSCOLLECTION() != null)
					group.setBUSCOLLECTION(round(tripCol.getBUSCOLLECTION(), 2));
				if(tripCol.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripCol.getTOTAL_DIESEL(), 2));
				group.setTOTAL_DIESEL_MILAGE(tripCol.getTOTAL_DIESEL_MILAGE());
				if(tripCol.getEXPENSE_DAY() != null)
					group.setEXPENSE_DAY(round(tripCol.getEXPENSE_DAY(), 2));
				group.setTOTAL_OVERTIME(tripCol.getTOTAL_OVERTIME());
				group.setmarkForDelete(tripCol.ismarkForDelete());
				group.setRFIDUSAGE(round(tripCol.getRFIDUSAGE(), 2));
				if(tripCol.getRFIDRCG() != null)
					group.setRFIDRCG(round(tripCol.getRFIDRCG(), 2));
				if(tripCol.getRFIDCARD() != null)
					group.setRFIDCARD(round(tripCol.getRFIDCARD(), 2));
				group.setTOTAL_TOTALPASSNGER(tripCol.getTOTAL_TOTALPASSNGER());
				group.setTOTAL_USAGE_KM(tripCol.getTOTAL_USAGE_KM());
				group.setTRIP_REMARKS(tripCol.getTRIP_REMARKS());
				group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(tripCol.getTRIP_STATUS_ID()));

				Dtos.add(group);
			}
		}
		return Dtos;
	}

	/**
	 * @param tDGroupCol
	 * @return
	 */
	public Double[] Tota_TripDailyGroupCollection_Sheet_total(List<TripDailyGroupCollectionDto> tDGroupCol) {
		// Note Value of Group Collection

		Double[] totalCollection = new Double[13];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalDieselKMPL = 0.0, TotalPassenger = 0.0, TotalRFID = 0.0,
				TotalRFIDAmount = 0.0, TotalCollection = 0.0, TotalWT = 0.0, TotalNetCollection = 0.0, TotalEPK = 0.0,
				TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (tDGroupCol != null && !tDGroupCol.isEmpty()) {
			for (TripDailyGroupCollectionDto tripGroupCollection : tDGroupCol) {

				TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				TotalRFID += tripGroupCollection.getTOTAL_RFIDPASS();
				TotalRFIDAmount += tripGroupCollection.getTOTAL_RFID_AMOUNT();
				TotalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				TotalWT += tripGroupCollection.getTOTAL_WT();
				TotalNetCollection += tripGroupCollection.getTOTAL_NET_COLLECTION();
				TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		if (TotalDiesel != 0.0) {
			TotalDieselKMPL = TotalUsageKM / TotalDiesel;
		}
		totalCollection[2] = round(TotalDieselKMPL, 2);
		totalCollection[3] = round(TotalPassenger, 2);
		totalCollection[4] = round(TotalRFID, 2);
		totalCollection[5] = round(TotalRFIDAmount, 2);
		totalCollection[6] = round(TotalCollection, 2);
		totalCollection[7] = round(TotalWT, 2);
		totalCollection[8] = round(TotalNetCollection, 2);
		TotalEPK = TotalCollection / TotalUsageKM;
		totalCollection[9] = round(TotalEPK, 2);
		totalCollection[10] = round(TotalExpense, 2);
		totalCollection[11] = round(TotalOT, 2);
		totalCollection[12] = round(TotalBalance, 2);

		return totalCollection;
	}

	/**
	 * @param collection
	 * @return
	 *//*
		 * public List<TripDailySheetDto>
		 * prepare_TripDaily_FuelMileage_Report(List<TripDailySheet> tripCollection) {
		 * // This Vehicle Fuel Mileage Report
		 * 
		 * List<TripDailySheetDto> Dtos = null; if (tripCollection != null &&
		 * !tripCollection.isEmpty()) { Dtos = new ArrayList<TripDailySheetDto>();
		 * TripDailySheetDto status = null; for (TripDailySheet CollectionDto :
		 * tripCollection) { status = new TripDailySheetDto();
		 * 
		 * status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
		 * status.setVEHICLEID(CollectionDto.getVEHICLEID());
		 * 
		 * status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
		 * status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
		 * status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
		 * status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
		 * status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());
		 * status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
		 * status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());
		 * 
		 * status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
		 * status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL()); if
		 * (CollectionDto.getTRIP_OPEN_DATE() != null) {
		 * status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.
		 * getTRIP_OPEN_DATE())); }
		 * status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());
		 * 
		 * status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());
		 * 
		 * Dtos.add(status); } } return Dtos; }
		 */
	public List<TripDailySheetDto> prepare_TripDaily_FuelMileage_ReportList(List<TripDailySheetDto> tripCollection) {
		// This Vehicle Fuel Mileage Report

		List<TripDailySheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailySheetDto>();
			TripDailySheetDto status = null;
			for (TripDailySheetDto CollectionDto : tripCollection) {
				status = new TripDailySheetDto();

				status.setTRIPDAILYID(CollectionDto.getTRIPDAILYID());
				status.setVEHICLEID(CollectionDto.getVEHICLEID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
				status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());
				status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
				status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());

				status.setTRIP_DIESEL(CollectionDto.getTRIP_DIESEL());
				status.setTRIP_DIESELKMPL(CollectionDto.getTRIP_DIESELKMPL());
				if (CollectionDto.getTRIP_OPEN_DATE_D() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_D()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_CLOSE_STATUS(
						TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_STATUS_ID()));
				status.setTRIP_STATUS_ID(CollectionDto.getTRIP_STATUS_ID());
				status.setVehicle_ExpectedMileage(CollectionDto.getVehicle_ExpectedMileage());

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double[] Tota_FuelMilage_TripDaily_Sheet_total(List<TripDailySheet> groupSheet) {

		Double[] totalCollection = new Double[3];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalBKMPL = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailySheet tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTRIP_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTRIP_USAGE_KM();
				}
				if (tripGroupCollection.getTRIP_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTRIP_DIESEL();
				}

			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TotalBKMPL = (TotalUsageKM / TotalDiesel);
		totalCollection[2] = round(TotalBKMPL, 2);

		return totalCollection;
	}

	public Double[] FuelMilage_TripDaily_Sheet_total(List<TripDailySheetDto> groupSheet) {

		Double[] totalCollection = new Double[3];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalBKMPL = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailySheetDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTRIP_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTRIP_USAGE_KM();
				}
				if (tripGroupCollection.getTRIP_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTRIP_DIESEL();
				}

			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TotalBKMPL = (TotalUsageKM / TotalDiesel);
		totalCollection[2] = round(TotalBKMPL, 2);

		return totalCollection;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double[] Total_Conducter_TripDaily_Collection_total(List<TripDailySheetDto> groupSheet) {
		// Note Conductor Trip Daily collection

		Double[] totalCollection = new Double[5];

		Double TotalUsageKM = 0.0, TotalPassenger = 0.0, TotalRFID = 0.0, TotalCollection = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailySheetDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTRIP_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTRIP_USAGE_KM();
				}
				if (tripGroupCollection.getTRIP_TOTALPASSNGER() != null) {
					TotalPassenger += tripGroupCollection.getTRIP_TOTALPASSNGER();
				}
				if (tripGroupCollection.getTRIP_RFIDPASS() != null) {
					TotalRFID += tripGroupCollection.getTRIP_RFIDPASS();
				}
				if (tripGroupCollection.getTOTAL_INCOME() != null) {
					TotalCollection += tripGroupCollection.getTOTAL_INCOME();
				}
			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalPassenger, 2);
		totalCollection[2] = round(TotalRFID, 2);
		totalCollection[3] = round(TotalCollection, 2);
		Double Total_EPK_group = 0.0;

		if (TotalUsageKM != 0.0) {
			Total_EPK_group = TotalCollection / TotalUsageKM;
		}
		totalCollection[4] = round(Total_EPK_group, 2);

		return totalCollection;
	}

	/**
	 * @param get_TripDailyGroupCollection_CloseDate
	 * @return
	 */
	public TripDailyGroupCollectionDto CLOSED_DAILY_GROUP_SHEET(TripDailyGroupCollectionDto TripDailyGroup, List<TripDailySheetDto> CollectionSheet) {
		TripDailyGroupCollectionDto GroupDto = new TripDailyGroupCollectionDto();
		if (TripDailyGroup != null) {

			GroupDto.setTOTAL_USAGE_KM(TripDailyGroup.getTOTAL_USAGE_KM());
			GroupDto.setTOTAL_DIESEL(round(TripDailyGroup.getTOTAL_DIESEL(), 2));

			Double KMPL = 0.0, TOTAL_DIESEL = 0.0;
			Integer TOTAL_USAGE_KM = 0; Integer totalChaloKm = 0; Double totalChaloAmount = 0.0 ;
			if (TripDailyGroup.getTOTAL_USAGE_KM() != null) {
				TOTAL_USAGE_KM = TripDailyGroup.getTOTAL_USAGE_KM();
			}
			if (TripDailyGroup.getTOTAL_DIESEL() != null) {

				TOTAL_DIESEL = TripDailyGroup.getTOTAL_DIESEL();
			}
			if (TripDailyGroup.getTOTAL_DIESEL() != 0.0) {
				KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
			}
			GroupDto.setTOTAL_DIESEL_MILAGE(round(KMPL, 2));

			GroupDto.setTOTAL_TOTALPASSNGER(TripDailyGroup.getTOTAL_TOTALPASSNGER());
			GroupDto.setTOTAL_PASS_PASSNGER(TripDailyGroup.getTOTAL_PASS_PASSNGER());

			GroupDto.setTOTAL_RFIDPASS(TripDailyGroup.getTOTAL_RFIDPASS());
			GroupDto.setTOTAL_RFID_AMOUNT(TripDailyGroup.getTOTAL_RFID_AMOUNT());

			GroupDto.setTOTAL_COLLECTION(round(TripDailyGroup.getTOTAL_COLLECTION(), 2));
			GroupDto.setTOTAL_WT(round(TripDailyGroup.getTOTAL_WT(), 2));
			
			
			GroupDto.setTOTAL_NET_COLLECTION(round(TripDailyGroup.getTOTAL_NET_COLLECTION(), 2));

			Double Total_collection_group = 0.0, Total_EPK_group = 0.0;

			Integer Total_KM_group = 0;

			if (TripDailyGroup.getTOTAL_COLLECTION() != null) {
				Total_collection_group = TripDailyGroup.getTOTAL_COLLECTION();
			}

			if (TripDailyGroup.getTOTAL_USAGE_KM() != null) {
				Total_KM_group = TripDailyGroup.getTOTAL_USAGE_KM();
			}
			Total_EPK_group = Total_collection_group / Total_KM_group;

			GroupDto.setTOTAL_EPK(round(Total_EPK_group, 2));

			GroupDto.setTOTAL_EXPENSE(round(TripDailyGroup.getTOTAL_EXPENSE(), 2));
			GroupDto.setTOTAL_OVERTIME(round(TripDailyGroup.getTOTAL_OVERTIME(), 2));
			GroupDto.setTOTAL_BALANCE(round(TripDailyGroup.getTOTAL_BALANCE(), 2));
			GroupDto.setDiesel_Amount(round(TripDailyGroup.getDiesel_Amount(),2));
			GroupDto.setTRIP_CLOSE_REMARKS(TripDailyGroup.getTRIP_CLOSE_REMARKS());
			GroupDto.setCREATEDBY(TripDailyGroup.getCREATEDBY());
			if (TripDailyGroup.getCREATED_ON() != null) {
				GroupDto.setCREATED(dateFormat_Name.format(TripDailyGroup.getCREATED_ON()));
			}
			if(CollectionSheet != null) {
			for(TripDailySheetDto dto:CollectionSheet) {
				if(dto!= null) {
					if(dto.getCHALO_KM()!=null) {
				totalChaloKm += dto.getCHALO_KM();
					}
					if(dto.getCHALO_AMOUNT()!=null) {
				totalChaloAmount += dto.getCHALO_AMOUNT();
					}
				}
			}
			GroupDto.setTotalCHALO_KM(totalChaloKm);
			GroupDto.setTotalCHALO_AMOUNT(round(totalChaloAmount, 2));
		}
	}
		
		return GroupDto;
	}

	/**
	 * @param credit_Debit
	 * @return
	 */
	public Double[] Total_DebitTotal_CreditTotal_CashBook(List<CashBookDto> credit_Debit) {

		Double[] totalCollection = new Double[3];

		Double TotalBalance = 0.0, DebitTotal = 0.0, CreditTotal = 0.0;
		if (credit_Debit != null && !credit_Debit.isEmpty()) {
			for (CashBookDto book : credit_Debit) {

				if (book.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {
					DebitTotal += book.getCASH_AMOUNT();
				} else {
					CreditTotal += book.getCASH_AMOUNT();
				}

			}
		}

		totalCollection[0] = round(DebitTotal, 2);
		totalCollection[1] = round(CreditTotal, 2);
		TotalBalance = CreditTotal - DebitTotal;
		totalCollection[2] = round(TotalBalance, 2);

		return totalCollection;

	}

	public Double[] Total_DebitTotal_CreditTotal_OF_CashBook(List<CashBookDto> credit_Debit) {

		Double[] totalCollection = new Double[3];

		Double TotalBalance = 0.0, DebitTotal = 0.0, CreditTotal = 0.0;
		if (credit_Debit != null && !credit_Debit.isEmpty()) {
			for (CashBookDto book : credit_Debit) {

				if (book.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {
					DebitTotal += book.getCASH_AMOUNT();
				} else {
					CreditTotal += book.getCASH_AMOUNT();
				}

			}
		}

		totalCollection[0] = round(DebitTotal, 2);
		totalCollection[1] = round(CreditTotal, 2);
		TotalBalance = CreditTotal - DebitTotal;
		totalCollection[2] = round(TotalBalance, 2);

		return totalCollection;

	}

	
	
	public Double[] Total_TripDaily_collection_Time_Report(List<TripDailySheetDto> dtos) {

		Double[] totalCollection = new Double[11];

		Double TRIP_USAGE_KM = 0.0, TRIP_DIESEL = 0.0, TRIP_RFIDPASS = 0.0, TRIP_RFID_AMOUNT = 0.0, TOTAL_INCOME = 0.0, TOTAL_EXPENSE = 0.0, TOTAL_BALANCE = 0.0, TRIP_DIESEL_AMOUNT = 0.0 ;
		         
		if (dtos != null && !dtos.isEmpty()) {
			for (TripDailySheetDto tripDTo : dtos) {

				if (tripDTo.getTRIP_USAGE_KM() != null) {

					TRIP_USAGE_KM += tripDTo.getTRIP_USAGE_KM();
				}
				if (tripDTo.getTRIP_DIESEL() != null) {
					TRIP_DIESEL += tripDTo.getTRIP_DIESEL();
				}
				
				if (tripDTo.getTRIP_DIESEL_AMOUNT() != null) {
					TRIP_DIESEL_AMOUNT += tripDTo.getTRIP_DIESEL_AMOUNT();
				}
				
				if (tripDTo.getTRIP_RFIDPASS() != null) {
					TRIP_RFIDPASS += tripDTo.getTRIP_RFIDPASS();
				}
				if (tripDTo.getTRIP_RFID_AMOUNT() != null) {
					TRIP_RFID_AMOUNT += tripDTo.getTRIP_RFID_AMOUNT();
				}

				if (tripDTo.getTOTAL_INCOME() != null) {
					TOTAL_INCOME += tripDTo.getTOTAL_INCOME();
				}
				if (tripDTo.getTOTAL_EXPENSE() != null) {
					TOTAL_EXPENSE += tripDTo.getTOTAL_EXPENSE();
				}
				if (tripDTo.getTOTAL_BALANCE() != null) {
					TOTAL_BALANCE += tripDTo.getTOTAL_BALANCE();
				}
			}
		}

		Double KMPL = 0.0, TOTAL_COLLECTION = 0.0 , NET_BALANCE = 0.0 ;

		if (TRIP_DIESEL != 0.0) {
			KMPL = (TRIP_USAGE_KM / TRIP_DIESEL);
		}
		totalCollection[0] = round(TRIP_USAGE_KM, 2);
		totalCollection[1] = round(TRIP_DIESEL, 2);
		totalCollection[2] = round(KMPL, 2);
		totalCollection[3] = round(TRIP_RFIDPASS, 2);
		totalCollection[4] = round(TRIP_RFID_AMOUNT, 2);
		totalCollection[5] = round(TOTAL_INCOME, 2);
		totalCollection[6] = round(TOTAL_EXPENSE, 2);
		totalCollection[7] = round(TOTAL_BALANCE, 2);
		
		if (TRIP_DIESEL != 0.0) {
		TOTAL_COLLECTION = (TRIP_RFID_AMOUNT + TOTAL_INCOME) ;
		}
		totalCollection[8] = round(TOTAL_COLLECTION, 2);
		
		if (TRIP_DIESEL != 0.0) {
			NET_BALANCE = TOTAL_COLLECTION - TOTAL_EXPENSE;
		}
		totalCollection[9] = round(NET_BALANCE, 2);
		
		totalCollection[10] = round(TRIP_DIESEL_AMOUNT, 2);
		
		return totalCollection;
	}

	public Double[] Group_Total_details_to_TripDaily_all_total_CLOSED_VALUE(List<TripDailyRouteSheetDto> groupSheet, List<TripDailySheetDto> CollectionSheet) {

		Double[] totalCollection = new Double[15];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalKMPL = 0.0, TotalPassenger = 0.0, TotalPass = 0.0,
				TotalRFID = 0.0, TotalRFID_AMOUNT = 0.0, TotalCollection = 0.0, TotalWT = 0.0,
				Total_NET_Collection = 0.0, TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0, TotalChalokm = 0.0,TotalChaloAmount =0.0 ;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				}
				if (tripGroupCollection.getTOTAL_TOTALPASSNGER() != null) {
					TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				}

				if (tripGroupCollection.getTOTAL_PASS_PASSNGER() != null) {
					TotalPass += tripGroupCollection.getTOTAL_PASS_PASSNGER();
				}

				if (tripGroupCollection.getTOTAL_RFIDPASS() != null) {
					TotalRFID += tripGroupCollection.getTOTAL_RFIDPASS();
				}
				if (tripGroupCollection.getTOTAL_RFID_AMOUNT() != null) {
					TotalRFID_AMOUNT += tripGroupCollection.getTOTAL_RFID_AMOUNT();
				}
				if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
					TotalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				}
				if (tripGroupCollection.getTOTAL_WT() != null) {
					TotalWT += tripGroupCollection.getTOTAL_WT();
				}
				if (tripGroupCollection.getTOTAL_NET_COLLECTION() != null) {
					Total_NET_Collection += tripGroupCollection.getTOTAL_NET_COLLECTION();
				}
				if (tripGroupCollection.getTOTAL_EXPENSE() != null) {
					TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				}
				if (tripGroupCollection.getTOTAL_OVERTIME() != null) {
					TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				}
				if (tripGroupCollection.getTOTAL_BALANCE() != null) {
					TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
				}
				
			}
			
		}
		if(CollectionSheet != null  && !CollectionSheet.isEmpty()) {
		for(TripDailySheetDto dto :CollectionSheet) {
			if (dto.getCHALO_KM() != null) {
				TotalChalokm += dto.getCHALO_KM();				
			}
			if (dto.getCHALO_AMOUNT() != null) {
				TotalChaloAmount += dto.getCHALO_AMOUNT();				
			}
			
		}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TotalKMPL = TotalUsageKM / TotalDiesel;
		totalCollection[2] = round(TotalKMPL, 2);
		totalCollection[3] = round(TotalPassenger, 2);
		totalCollection[4] = round(TotalPass, 2);
		totalCollection[5] = round(TotalRFID, 2);
		totalCollection[6] = round(TotalRFID_AMOUNT, 2);
		totalCollection[7] = round(TotalCollection, 2);
		totalCollection[8] = round(TotalWT, 2);
		totalCollection[9] = round(Total_NET_Collection, 2);
		totalCollection[10] = round(TotalExpense, 2);
		totalCollection[11] = round(TotalOT, 2);
		totalCollection[12] = round(TotalBalance, 2);
		totalCollection[13] = round(TotalChalokm, 2);
		totalCollection[14] = round(TotalChaloAmount, 2);

		return totalCollection;
	}

	public Double[] Group_Total_details_to_TripDaily_all_total_VALUE(List<TripDailyRouteSheetDto> groupSheet, List<TripDailySheetDto> CollectionSheet) {

		Double[] totalCollection = new Double[15];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TotalKMPL = 0.0, TotalPassenger = 0.0, TotalPass = 0.0,
				TotalRFID = 0.0, TotalRFID_AMOUNT = 0.0, TotalCollection = 0.0, TotalWT = 0.0,
				Total_NET_Collection = 0.0, TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0 ,TotalChalokm = 0.0 ,TotalChaloAmount =0.0 ;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTOTAL_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTOTAL_USAGE_KM();
				}
				if (tripGroupCollection.getTOTAL_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTOTAL_DIESEL();
				}
				if (tripGroupCollection.getTOTAL_TOTALPASSNGER() != null) {
					TotalPassenger += tripGroupCollection.getTOTAL_TOTALPASSNGER();
				}

				if (tripGroupCollection.getTOTAL_PASS_PASSNGER() != null) {
					TotalPass += tripGroupCollection.getTOTAL_PASS_PASSNGER();
				}

				if (tripGroupCollection.getTOTAL_RFIDPASS() != null) {
					TotalRFID += tripGroupCollection.getTOTAL_RFIDPASS();
				}
				if (tripGroupCollection.getTOTAL_RFID_AMOUNT() != null) {
					TotalRFID_AMOUNT += tripGroupCollection.getTOTAL_RFID_AMOUNT();
				}
				if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
					TotalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				}
				if (tripGroupCollection.getTOTAL_WT() != null) {
					TotalWT += tripGroupCollection.getTOTAL_WT();
				}
				if (tripGroupCollection.getTOTAL_NET_COLLECTION() != null) {
					Total_NET_Collection += tripGroupCollection.getTOTAL_NET_COLLECTION();
				}
				if (tripGroupCollection.getTOTAL_EXPENSE() != null) {
					TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				}
				if (tripGroupCollection.getTOTAL_OVERTIME() != null) {
					TotalOT += tripGroupCollection.getTOTAL_OVERTIME();
				}
				if(tripGroupCollection.getTOTAL_NET_COLLECTION() == null) {
					tripGroupCollection.setTOTAL_NET_COLLECTION(0.0);
				}if(tripGroupCollection.getTOTAL_EXPENSE() == null) {
					tripGroupCollection.setTOTAL_EXPENSE(0.0);
				}
				if (tripGroupCollection.getTOTAL_BALANCE() != null) {
					
					TotalBalance += (tripGroupCollection.getTOTAL_NET_COLLECTION() - tripGroupCollection.getTOTAL_EXPENSE() ) ;
				}
				
			}
		}
		
		if(CollectionSheet != null  && !CollectionSheet.isEmpty()) {
			for(TripDailySheetDto dto :CollectionSheet) {
				if (dto.getCHALO_KM() != null) {
					TotalChalokm += dto.getCHALO_KM();					
				}
				if (dto.getCHALO_AMOUNT() != null) {
					TotalChaloAmount += dto.getCHALO_AMOUNT();					
				}
				
			}
			}


		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TotalKMPL = TotalUsageKM / TotalDiesel;
		totalCollection[2] = round(TotalKMPL, 2);
		totalCollection[3] = round(TotalPassenger, 2);
		totalCollection[4] = round(TotalPass, 2);
		totalCollection[5] = round(TotalRFID, 2);
		totalCollection[6] = round(TotalRFID_AMOUNT, 2);
		totalCollection[7] = round(TotalCollection, 2);
		totalCollection[8] = round(TotalWT, 2);
		totalCollection[9] = round(Total_NET_Collection, 2);
		totalCollection[10] = round(TotalExpense, 2);
		totalCollection[11] = round(TotalOT, 2);
		totalCollection[12] = round(TotalBalance, 2);
		totalCollection[13] = round(TotalChalokm, 2);
		totalCollection[14] = round(TotalChaloAmount, 2);
		
		return totalCollection;
	}

	
	/**
	 * @param collection
	 * @return
	 */

	public Double[] Total_TripDaily_Sheet_total_Details(List<TripDailySheetDto> groupSheet) {

		Double[] totalCollection = new Double[13];

		Double TotalUsageKM = 0.0, TotalDiesel = 0.0, TatalKMPL = 0.0, TotalPassenger = 0.0, TotalRFID = 0.0,
				TotalRFIDAmount = 0.0, TotalCollection = 0.0, TotalWT = 0.0, TotalNetCollection = 0.0, TotalEPK = 0.0,
				TotalExpense = 0.0, TotalOT = 0.0, TotalBalance = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailySheetDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTRIP_USAGE_KM() != null) {
					TotalUsageKM += tripGroupCollection.getTRIP_USAGE_KM();
				}
				if (tripGroupCollection.getTRIP_DIESEL() != null) {
					TotalDiesel += tripGroupCollection.getTRIP_DIESEL();
				}
				if (tripGroupCollection.getTRIP_TOTALPASSNGER() != null) {
					TotalPassenger += tripGroupCollection.getTRIP_TOTALPASSNGER();
				}
				if (tripGroupCollection.getTRIP_RFIDPASS() != null) {
					TotalRFID += tripGroupCollection.getTRIP_RFIDPASS();
				}
				if (tripGroupCollection.getTRIP_RFID_AMOUNT() != null) {
					TotalRFIDAmount += tripGroupCollection.getTRIP_RFID_AMOUNT();
				}
				if (tripGroupCollection.getTOTAL_INCOME() != null) {
					TotalCollection += tripGroupCollection.getTOTAL_INCOME();
				}
				if (tripGroupCollection.getTOTAL_WT() != null) {
					TotalWT += tripGroupCollection.getTOTAL_WT();
				}
				if (tripGroupCollection.getTOTAL_NET_INCOME() != null) {
					TotalNetCollection += tripGroupCollection.getTOTAL_NET_INCOME();
				}

				if (tripGroupCollection.getTOTAL_EXPENSE() != null) {
					TotalExpense += tripGroupCollection.getTOTAL_EXPENSE();
				}
				if (tripGroupCollection.getTRIP_OVERTIME() != null) {
					TotalOT += tripGroupCollection.getTRIP_OVERTIME();
				}
				if (tripGroupCollection.getTOTAL_BALANCE() != null) {
					TotalBalance += tripGroupCollection.getTOTAL_BALANCE();
				}
			}
		}

		totalCollection[0] = round(TotalUsageKM, 2);
		totalCollection[1] = round(TotalDiesel, 2);
		TatalKMPL = TotalUsageKM / TotalDiesel;
		totalCollection[2] = round(TatalKMPL, 2);
		totalCollection[3] = round(TotalPassenger, 2);
		totalCollection[4] = round(TotalRFID, 2);
		totalCollection[5] = round(TotalRFIDAmount, 2);
		totalCollection[6] = round(TotalCollection, 2);
		totalCollection[7] = round(TotalWT, 2);
		totalCollection[8] = round(TotalNetCollection, 2);
		if (TotalUsageKM != 0.0) {
			TotalEPK = (TotalCollection + TotalRFIDAmount) / TotalUsageKM;
		}
		totalCollection[9] = round(TotalEPK, 2);
		totalCollection[10] = round(TotalExpense, 2);
		totalCollection[11] = round(TotalOT, 2);
		totalCollection[12] = round(TotalBalance, 2);

		return totalCollection;
	}

	public Double[] Total_Driver_Advance_Penalty_Report(List<DriverSalaryAdvanceDto> salary) {
		Double[] totalCollection = new Double[2];

		Double ADVANCE_AMOUNT = 0.0, ADVANCE_BALANCE = 0.0;
		if (salary != null && !salary.isEmpty()) {
			for (DriverSalaryAdvanceDto tripDTo : salary) {

				if (tripDTo.getADVANCE_AMOUNT() != null) {

					ADVANCE_AMOUNT += tripDTo.getADVANCE_AMOUNT();
				}
				if (tripDTo.getADVANCE_BALANCE() != null) {
					ADVANCE_BALANCE += tripDTo.getADVANCE_BALANCE();
				}

			}
		}

		totalCollection[0] = round(ADVANCE_AMOUNT, 2);
		totalCollection[1] = round(ADVANCE_BALANCE, 2);

		return totalCollection;
	}
	
	
	
	/*for Deisel Amount only*/
	public List<TripDailyGroupCollectionDto> prepare_LIST_TripDailyGroupCollectionDto(
			List<TripDailyGroupCollectionDto> dailyGroupCollection ,HashMap<String, TripDailySheetDto>  fuelAmountHm, HashMap<String, TripDailySheetDto>  chalohm) {

		List<TripDailyGroupCollectionDto> Dtos = null;
		if (dailyGroupCollection != null && !dailyGroupCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailyGroupCollectionDto>();
			TripDailyGroupCollectionDto group = null;
			for (TripDailyGroupCollectionDto tripCol : dailyGroupCollection) {
				group = new TripDailyGroupCollectionDto();

				group.setTRIPGROUPID(tripCol.getTRIPGROUPID());
				if (tripCol.getTRIP_OPEN_DATE_ON() != null) {
					group.setTRIP_OPEN_DATE(dateFormat_Name.format(tripCol.getTRIP_OPEN_DATE_ON()));
				}
				
				group.setVEHICLE_GROUP(tripCol.getVEHICLE_GROUP());
				group.setTOTAL_BALANCE(tripCol.getTOTAL_BALANCE());
				group.setTOTAL_BUS(tripCol.getTOTAL_BUS());
				if(tripCol.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripCol.getTOTAL_DIESEL(), 2));
				
				Double KMPL = 0.0, TOTAL_DIESEL = 0.0;
				Integer TOTAL_USAGE_KM = 0;
				if (tripCol.getTOTAL_USAGE_KM() != null) {
					TOTAL_USAGE_KM = tripCol.getTOTAL_USAGE_KM();
				}
				if (tripCol.getTOTAL_DIESEL() != null) {

					TOTAL_DIESEL = tripCol.getTOTAL_DIESEL();
				}
				if (tripCol.getTOTAL_DIESEL() != 0.0) {
					KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
				}
				group.setTOTAL_DIESEL_MILAGE(round(KMPL, 2));

				if(tripCol.getTOTAL_EXPENSE() != null)
					group.setTOTAL_EXPENSE(round(tripCol.getTOTAL_EXPENSE(), 2));
				if(tripCol.getTOTAL_OVERTIME() != null)
					group.setTOTAL_OVERTIME(round(tripCol.getTOTAL_OVERTIME(), 2));

				group.setTOTAL_RFIDPASS(tripCol.getTOTAL_RFIDPASS());
				group.setTOTAL_RFID_AMOUNT(tripCol.getTOTAL_RFID_AMOUNT());
				
				if(tripCol.getTOTAL_COLLECTION() != null)
					group.setTOTAL_COLLECTION(round(tripCol.getTOTAL_COLLECTION(), 2));
				if(tripCol.getTOTAL_WT() != null)
					group.setTOTAL_WT(round(tripCol.getTOTAL_WT(), 2));
				if(tripCol.getTOTAL_NET_COLLECTION() != null)
					group.setTOTAL_NET_COLLECTION(round(tripCol.getTOTAL_NET_COLLECTION(), 2));

				Double Total_collection_group = 0.0, Total_EPK_group = 0.0;

				Integer Total_KM_group = 0;

				if (tripCol.getTOTAL_COLLECTION() != null) {
					Total_collection_group = tripCol.getTOTAL_COLLECTION();
				}

				if (tripCol.getTOTAL_USAGE_KM() != null) {
					Total_KM_group = tripCol.getTOTAL_USAGE_KM();
				}
				if(fuelAmountHm != null) {
				if(fuelAmountHm.get(tripCol.getTRIP_OPEN_DATE_ON()+"") != null) {
					group.setDiesel_Amount(fuelAmountHm.get(tripCol.getTRIP_OPEN_DATE_ON()+"").getTRIP_DIESEL_AMOUNT());
					if(fuelAmountHm != null && fuelAmountHm.size() > 0) {
						Double totalDieselAmount = 0.0;
						 for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
							 totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
						 }
					}
				}else {
					group.setDiesel_Amount(0.0);
				}
				}
				
				if(chalohm != null) {
					if(chalohm.get(tripCol.getTRIP_OPEN_DATE_ON()+"") != null) {
					
						if(chalohm.get(tripCol.getTRIP_OPEN_DATE_ON()+"").getCHALO_KM() != null) {
						group.setCHALO_KM(chalohm.get(tripCol.getTRIP_OPEN_DATE_ON()+"").getCHALO_KM());
						}
						if(chalohm.get(tripCol.getTRIP_OPEN_DATE_ON()+"").getCHALO_AMOUNT() != null) {
						group.setCHALO_AMOUNT(chalohm.get(tripCol.getTRIP_OPEN_DATE_ON()+"").getCHALO_AMOUNT());
						}
					}
				}
				
				
				Total_EPK_group = Total_collection_group / Total_KM_group;

				group.setTOTAL_EPK(round(Total_EPK_group, 2));

				group.setmarkForDelete(tripCol.ismarkForDelete());
				group.setTOTAL_RFIDPASS(tripCol.getTOTAL_RFIDPASS());
				group.setTOTAL_TOTALPASSNGER(tripCol.getTOTAL_TOTALPASSNGER());
				group.setTOTAL_USAGE_KM(tripCol.getTOTAL_USAGE_KM());
				group.setTRIP_CLOSE_REMARKS(tripCol.getTRIP_CLOSE_REMARKS());
				group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(tripCol.getTRIP_STATUS_ID()));
				group.setTRIP_STATUS_ID(tripCol.getTRIP_STATUS_ID());

				Dtos.add(group);
			}
		}
		return Dtos;
	}
	
	
	
	public List<TripDailyRouteSheetDto> prepare_TripDailyRouteSheet_CollectionDto(
			List<TripDailyRouteSheetDto> tripCollection, HashMap<String, TripDailySheetDto>  fuelAmountHm) {

		List<TripDailyRouteSheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripDailyRouteSheetDto>();
			TripDailyRouteSheetDto group = null;
			for (TripDailyRouteSheetDto tripCol : tripCollection) {
				group = new TripDailyRouteSheetDto();
				
				if (tripCol.getTRIP_OPEN_DATE_ON() != null) {
					group.setTRIP_OPEN_DATE(dateFormat_Name.format(tripCol.getTRIP_OPEN_DATE_ON()));
				}
				group.setTRIPROUTEID(tripCol.getTRIPROUTEID());
				group.setTRIP_ROUTE_NAME(tripCol.getTRIP_ROUTE_NAME());
				group.setVEHICLE_GROUP(tripCol.getVEHICLE_GROUP());

				if(tripCol.getTOTAL_EXPENSE() != null)
					group.setTOTAL_EXPENSE(round(tripCol.getTOTAL_EXPENSE(), 2));
				if(tripCol.getTOTAL_OVERTIME() != null)
					group.setTOTAL_OVERTIME(round(tripCol.getTOTAL_OVERTIME(), 2));
				if(tripCol.getTOTAL_BALANCE() != null)
					group.setTOTAL_BALANCE(round(tripCol.getTOTAL_BALANCE(), 2));

				group.setTOTAL_RFIDPASS(tripCol.getTOTAL_RFIDPASS());
				if(tripCol.getTOTAL_RFID_AMOUNT() != null)
					group.setTOTAL_RFID_AMOUNT(round(tripCol.getTOTAL_RFID_AMOUNT(), 2));
				if(tripCol.getTOTAL_COLLECTION() != null)
					group.setTOTAL_COLLECTION(round(tripCol.getTOTAL_COLLECTION(), 2));
				if(tripCol.getTOTAL_WT() != null)
					group.setTOTAL_WT(round(tripCol.getTOTAL_WT(), 2));
				if(tripCol.getTOTAL_NET_COLLECTION() != null)
					group.setTOTAL_NET_COLLECTION(round(tripCol.getTOTAL_NET_COLLECTION(), 2));

				group.setTOTAL_TOTALPASSNGER(tripCol.getTOTAL_TOTALPASSNGER());
				if(tripCol.getTOTAL_DIESEL() != null)
					group.setTOTAL_DIESEL(round(tripCol.getTOTAL_DIESEL(), 2));
				group.setTOTAL_USAGE_KM(tripCol.getTOTAL_USAGE_KM());

				Double KMPL = 0.0, TOTAL_DIESEL = 0.0, TOTAL_COLLECTION = 0.0, TOTAL_EPK = 0.0;
				Integer TOTAL_USAGE_KM = 0;
				if (tripCol.getTOTAL_USAGE_KM() != null) {
					TOTAL_USAGE_KM = tripCol.getTOTAL_USAGE_KM();
				}
				if (tripCol.getTOTAL_DIESEL() != null) {

					TOTAL_DIESEL = tripCol.getTOTAL_DIESEL();
				}
				if (tripCol.getTOTAL_DIESEL() != null && tripCol.getTOTAL_DIESEL() != 0.0) {
					KMPL = (TOTAL_USAGE_KM / TOTAL_DIESEL);
				}

				if (tripCol.getTOTAL_COLLECTION() != null) {
					TOTAL_COLLECTION = tripCol.getTOTAL_COLLECTION();
				}
				
/*				System.err.println("date >"+tripCol.getTRIP_OPEN_DATE_ON()+"condition > "+(fuelAmountHm.get(tripCol.getTRIP_OPEN_DATE_ON()+"") != null));
*/				if(fuelAmountHm.get(tripCol.getTRIP_OPEN_DATE_ON()+"") != null) {
					group.setDIESEL_Amount(fuelAmountHm.get(tripCol.getTRIP_OPEN_DATE_ON()+"").getTRIP_DIESEL_AMOUNT());
					
				}else {
					group.setDIESEL_Amount(0.0);
				}

				TOTAL_EPK = TOTAL_COLLECTION / TOTAL_USAGE_KM;

				group.setTOTAL_EPK(round(TOTAL_EPK, 2));
				group.setTOTAL_DIESELKML(round(KMPL, 2));
				group.setTOTAL_BUS(1);
				group.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(tripCol.getTRIP_STATUS_ID()));
				group.setTRIP_STATUS_ID(tripCol.getTRIP_STATUS_ID());

				Dtos.add(group);
			}
		}

		return Dtos;
	}
	
	public Double[] Route_Wise_Collection_totalDieselAmount(List<TripDailyRouteSheetDto> groupSheet ,HashMap<String, TripDailySheetDto>  fuelAmountHm) {

		Double[] totalCollection = new Double[1];

		TripDailyRouteSheetDto group = null;
		
		
		Double TotalDieselAmt = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {
			for (TripDailyRouteSheetDto tripGroupCollection : groupSheet) {
				group = new TripDailyRouteSheetDto();
				if(fuelAmountHm.get(tripGroupCollection.getTRIP_OPEN_DATE_ON()+"") != null) {
					group.setDIESEL_Amount(fuelAmountHm.get(tripGroupCollection.getTRIP_OPEN_DATE_ON()+"").getTRIP_DIESEL_AMOUNT());
					
					TotalDieselAmt += group.getDIESEL_Amount();
					 group.setTOTAL_DIESEL_AMOUNT(TotalDieselAmt);
					
				}else {
					group.setDIESEL_Amount(0.0);
				}
				
			}
		}

		totalCollection[0] = round(TotalDieselAmt, 2);
		
		return totalCollection;
	}


}
