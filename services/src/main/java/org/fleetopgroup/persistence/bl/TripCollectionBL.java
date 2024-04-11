/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.dto.DriverAdvanceDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDayCollectionDto;
import org.fleetopgroup.persistence.dto.TripGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.DriverAdvance;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.TripDayCollection;
import org.fleetopgroup.persistence.model.TripGroupCollection;

/**
 * @author fleetop
 *
 */
public class TripCollectionBL {

	public TripCollectionBL() {
		super();
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @param tripCol
	 * @param balance
	 * @return
	 */
	public TripGroupCollection Collection_Trip_toAdd_GroupCollection(TripCollectionSheetDto tripCol, Double balance) {

		TripGroupCollection group = new TripGroupCollection();

		group.setTRIP_OPEN_DATE(tripCol.getTRIP_OPEN_DATE_ON());
		//group.setVEHICLE_GROUP(tripCol.getVEHICLE_GROUP());
		group.setVEHICLE_GROUP_ID(tripCol.getVEHICLE_GROUP_ID());
		group.setTOTAL_COLLECTION(tripCol.getTOTAL_INCOME());
		group.setTOTAL_EXPENSE(tripCol.getTOTAL_EXPENSE());
		group.setTOTAL_BALANCE(balance);
		group.setTOTAL_BUS(1);
		group.setTOTAL_DIESEL(tripCol.getTRIP_DIESEL_LITER());
		group.setTOTAL_GROUP_COLLECTION(0.0);
		group.setTOTAL_SINGL(tripCol.getTRIP_SINGL());
		group.setTRIP_CLOSE_STATUS_ID(TripDailySheetStatus.TRIP_STATUS_OPEN);
		return group;
	}

	/**
	 * @param tripCol
	 * @return
	 *//*
	public TripCollectionSheetDto Get_TripCollectionShee_To_DriverAttendance(TripCollectionSheet CollectionDto) {

		TripCollectionSheetDto status = new TripCollectionSheetDto();

		status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
		status.setVID(CollectionDto.getVID());

		if (CollectionDto.getTRIP_OPEN_DATE() != null) {
			status.setTRIP_OPEN_DATE(driverAttendanceFormat.format(CollectionDto.getTRIP_OPEN_DATE()));
		}

		status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
		status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
		status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

		status.setTRIP_ROUTE_ID(CollectionDto.getTRIP_ROUTE_ID());
		status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

		status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
		status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());

		status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
		status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());

		status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
		status.setTRIP_CLEANER_NAME(CollectionDto.getTRIP_CLEANER_NAME());

		status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
		status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

		status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
		status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
		status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());
		status.setTRIP_DRIVER_JAMA(CollectionDto.getTRIP_DRIVER_JAMA());
		status.setTRIP_CONDUCTOR_JAMA(CollectionDto.getTRIP_CONDUCTOR_JAMA());
		status.setDRIVER_ADVANCE_STATUS(CollectionDto.getDRIVER_ADVANCE_STATUS());
		status.setCONDUCTOR_ADVANCE_STATUS(CollectionDto.getCONDUCTOR_ADVANCE_STATUS());

		status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());

		
		 * status.setCREATED(CollectionDto.getCREATED());
		 * status.setlASTUPDATED(CollectionDto.getlASTUPDATED());
		 

		status.setCREATEDBY(CollectionDto.getCREATEDBY());
		status.setLASTMODIFIEDBY(CollectionDto.getLASTMODIFIEDBY());
		status.setMarkForDelete(false);
		return status;
	}
*/
	/**
	 * @param groupSheet
	 * @param collectionSheet
	 * @return
	 */
	public List<TripGroupCollectionDto> CloseDaily_tripCollection_Sheet(List<TripGroupCollectionDto> groupSheet,
			List<TripCollectionSheetDto> collectionSheet) {

		List<TripGroupCollectionDto> DTO = null;
		// for loop trip_Group_sheet
		if (groupSheet != null && !groupSheet.isEmpty()) {
			DTO = new ArrayList<TripGroupCollectionDto>();
			TripGroupCollectionDto group = null;
			for (TripGroupCollectionDto tripGroupCollection : groupSheet) {
				group = new TripGroupCollectionDto();
				// for loop trip_collection_sheet
				if (collectionSheet != null && !collectionSheet.isEmpty()) {
					TripGroupCollectionDto tripCol = null;
					for (TripCollectionSheetDto tripSheet : collectionSheet) {
						tripCol = new TripGroupCollectionDto();
						if (tripGroupCollection.getVEHICLE_GROUP_ID() == tripSheet.getVEHICLE_GROUP_ID()) {

							// set GroupCollection To Show Trip Collection ID
							tripCol.setTRIPGROUPID(tripSheet.getTRIPCOLLID());
							tripCol.setVEHICLE_GROUP(tripSheet.getVEHICLE_GROUP());
							tripCol.setTRIP_DRIVER_NAME(tripSheet.getTRIP_DRIVER_NAME());
							tripCol.setTRIP_CONDUCTOR_NAME(tripSheet.getTRIP_CONDUCTOR_NAME());
							tripCol.setTRIP_ROUTE_NAME(tripSheet.getTRIP_ROUTE_NAME());
							tripCol.setTRIPGROUPID(tripSheet.getTRIPCOLLID());
							tripCol.setTRIP_CLOSE_STATUS(tripSheet.getVEHICLE_REGISTRATION());
							tripCol.setTOTAL_COLLECTION(tripSheet.getTOTAL_INCOME());
							tripCol.setTOTAL_EXPENSE(tripSheet.getTOTAL_EXPENSE());
							tripCol.setTOTAL_DIESEL(tripSheet.getTRIP_DIESEL_LITER());
							tripCol.setTOTAL_BALANCE(tripSheet.getTOTAL_BALANCE());
							tripCol.setTOTAL_SINGL(tripSheet.getTRIP_SINGL());
							tripCol.setTOTAL_BUS(null);

							DTO.add(tripCol);
						}
					} // close for loop trip collection sheet

				} // close if loop trip collection sheet

				group.setVEHICLE_GROUP(tripGroupCollection.getVEHICLE_GROUP());
				group.setTRIP_ROUTE_NAME(null);
				group.setTRIP_CLOSE_STATUS("TOTAL:");
				group.setTOTAL_COLLECTION(tripGroupCollection.getTOTAL_COLLECTION());
				group.setTRIP_DRIVER_NAME(null);
				group.setTRIP_CONDUCTOR_NAME(null);
				group.setTOTAL_EXPENSE(tripGroupCollection.getTOTAL_EXPENSE());
				group.setTOTAL_DIESEL(tripGroupCollection.getTOTAL_DIESEL());
				group.setTOTAL_BALANCE(tripGroupCollection.getTOTAL_BALANCE());
				group.setTOTAL_SINGL(tripGroupCollection.getTOTAL_SINGL());
				group.setTOTAL_BUS(tripGroupCollection.getTOTAL_BUS());

				DTO.add(group);
			}
		}

		return DTO;
	}

	public List<TripGroupCollectionDto> Show_CloseDaily_tripCollection_Sheet(List<TripGroupCollectionDto> groupSheet,
			List<TripCollectionSheetDto> collectionSheet) {

		List<TripGroupCollectionDto> DTO = null;
		// for loop trip_Group_sheet

		if (groupSheet != null && !groupSheet.isEmpty()) {
			DTO = new ArrayList<TripGroupCollectionDto>();
			TripGroupCollectionDto group = null;
			for (TripGroupCollectionDto tripGroupCollection : groupSheet) {

				group = new TripGroupCollectionDto();

				// for loop trip_collection_sheet
				if (collectionSheet != null && !collectionSheet.isEmpty()) {
					TripGroupCollectionDto tripCol = null;
					for (TripCollectionSheetDto tripSheet : collectionSheet) {
						tripCol = new TripGroupCollectionDto();
						if (tripGroupCollection.getVEHICLE_GROUP_ID() == tripSheet.getVEHICLE_GROUP_ID()) {

							tripCol.setVEHICLE_GROUP(tripSheet.getVEHICLE_GROUP());
							tripCol.setTRIP_DRIVER_NAME(tripSheet.getTRIP_DRIVER_NAME());
							tripCol.setTRIP_CONDUCTOR_NAME(tripSheet.getTRIP_CONDUCTOR_NAME());
							tripCol.setTRIP_ROUTE_NAME(tripSheet.getTRIP_ROUTE_NAME());
							tripCol.setTRIPGROUPID(tripSheet.getTRIPCOLLID());
							tripCol.setTRIP_CLOSE_STATUS(tripSheet.getVEHICLE_REGISTRATION());
							tripCol.setTOTAL_COLLECTION(tripSheet.getTOTAL_INCOME());
							tripCol.setTOTAL_EXPENSE(tripSheet.getTOTAL_EXPENSE());
							tripCol.setTOTAL_DIESEL(tripSheet.getTRIP_DIESEL_LITER());
							tripCol.setTOTAL_BALANCE(tripSheet.getTOTAL_BALANCE());
							tripCol.setTOTAL_SINGL(tripSheet.getTRIP_SINGL());
							tripCol.setTOTAL_BUS(null);
							tripCol.setTOTAL_GROUP_COLLECTION(null);

							DTO.add(tripCol);
						}
					} // close for loop trip collection sheet

				} // close if loop trip collection sheet

				group.setVEHICLE_GROUP(tripGroupCollection.getVEHICLE_GROUP());
				group.setTRIP_ROUTE_NAME(null);
				group.setTRIP_CLOSE_STATUS("TOTAL:");
				group.setTOTAL_COLLECTION(tripGroupCollection.getTOTAL_COLLECTION());
				group.setTRIP_DRIVER_NAME(null);
				group.setTRIP_CONDUCTOR_NAME(null);
				group.setTOTAL_EXPENSE(tripGroupCollection.getTOTAL_EXPENSE());
				group.setTOTAL_DIESEL(tripGroupCollection.getTOTAL_DIESEL());
				group.setTOTAL_BALANCE(tripGroupCollection.getTOTAL_BALANCE());
				group.setTOTAL_SINGL(tripGroupCollection.getTOTAL_SINGL());
				group.setTOTAL_BUS(tripGroupCollection.getTOTAL_BUS());
				group.setTOTAL_GROUP_COLLECTION(tripGroupCollection.getTOTAL_GROUP_COLLECTION());

				DTO.add(group);
			}
		}

		return DTO;

	}

	/**
	 * @param groupSheet
	 * @return
	 */
	public Double Total_Collection(List<TripGroupCollectionDto> groupSheet) {
		Double totalCollection = 0.0;
		if (groupSheet != null && !groupSheet.isEmpty()) {

			for (TripGroupCollectionDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTOTAL_COLLECTION() != null) {
					totalCollection += tripGroupCollection.getTOTAL_COLLECTION();
				}
			}
		}
		return totalCollection;
	}

	/**
	 * @param groupSheet
	 * @return
	 */
	public Integer[] Total_Runing_Bus_Details(List<TripGroupCollectionDto> groupSheet) {

		Integer[] total = new Integer[2];
		Integer totalRuningBus = 0, totalRuningSingl = 0;
		if (groupSheet != null && !groupSheet.isEmpty()) {

			for (TripGroupCollectionDto tripGroupCollection : groupSheet) {

				if (tripGroupCollection.getTOTAL_BUS() != null) {
					totalRuningBus += tripGroupCollection.getTOTAL_BUS();
				}

				if (tripGroupCollection.getTOTAL_SINGL() != null) {
					totalRuningSingl += tripGroupCollection.getTOTAL_SINGL();
				}
			}

			total[0] = totalRuningBus;
			total[1] = totalRuningSingl;

		}
		return total;
	}

	/**
	 * @param edit_TripCollection_Sheet
	 * @return
	 *//*
	public TripCollectionSheetDto Update_TripSheet(TripCollectionSheet CollectionDto) {

		TripCollectionSheetDto status = new TripCollectionSheetDto();

		status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
		status.setTRIPCOLLNUMBER(CollectionDto.getTRIPCOLLNUMBER());
		status.setVID(CollectionDto.getVID());

		status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
		status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
		status.setVEHICLE_GROUP_ID(CollectionDto.getVEHICLE_GROUP_ID());
		status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

		if (CollectionDto.getTRIP_OPEN_DATE() != null) {
			status.setTRIP_OPEN_DATE(dateFormat.format(CollectionDto.getTRIP_OPEN_DATE()));
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

		status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
		status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

		status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
		status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
		status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());
		status.setTRIP_DRIVER_JAMA(CollectionDto.getTRIP_DRIVER_JAMA());
		status.setTRIP_CONDUCTOR_JAMA(CollectionDto.getTRIP_CONDUCTOR_JAMA());
		status.setDRIVER_ADVANCE_STATUS(CollectionDto.getDRIVER_ADVANCE_STATUS());
		status.setCONDUCTOR_ADVANCE_STATUS(CollectionDto.getCONDUCTOR_ADVANCE_STATUS());
		status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());

		*//** Set Created Current Date *//*
		if (CollectionDto.getCREATED() != null) {
			*//** Set Created Current Date *//*
			status.setCREATED(CreatedDateTime.format(CollectionDto.getCREATED()));
		}
		if (CollectionDto.getlASTUPDATED() != null) {
			status.setlASTUPDATED(CreatedDateTime.format(CollectionDto.getlASTUPDATED()));
		}

		status.setCREATEDBY(CollectionDto.getCREATEDBY());
		status.setLASTMODIFIEDBY(CollectionDto.getLASTMODIFIEDBY());
		status.setMarkForDelete(CollectionDto.isMarkForDelete());

		return status;
	}
*/
	/**
	 * @param tripCollection
	 * @return
	 */
	public List<TripCollectionSheetDto> prepare_TripCollectionDto(List<TripCollectionSheetDto> tripCollection) {

		List<TripCollectionSheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto status = null;
			for (TripCollectionSheetDto CollectionDto : tripCollection) {
				status = new TripCollectionSheetDto();

				status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
				status.setTRIPCOLLNUMBER(CollectionDto.getTRIPCOLLNUMBER());
				status.setVID(CollectionDto.getVID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
				status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	public List<TripCollectionSheetDto> prepare_TripCollectionList(List<TripCollectionSheetDto> tripCollection) {

		List<TripCollectionSheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto status = null;
			for (TripCollectionSheetDto CollectionDto : tripCollection) {
				status = new TripCollectionSheetDto();

				status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
				status.setTRIPCOLLNUMBER(CollectionDto.getTRIPCOLLNUMBER());
				status.setVID(CollectionDto.getVID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE_ON() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_ON()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
				status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS_ID(CollectionDto.getTRIP_CLOSE_STATUS_ID());
				status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_CLOSE_STATUS_ID()));

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	
	public List<TripCollectionSheetDto> prepare_Driver_JAMA_TripCollectionDto(
			List<TripCollectionSheetDto> tripCollection) {

		List<TripCollectionSheetDto> Dtos = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {
			Dtos = new ArrayList<TripCollectionSheetDto>();
			TripCollectionSheetDto status = null;
			for (TripCollectionSheetDto CollectionDto : tripCollection) {
				status = new TripCollectionSheetDto();

				status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
				status.setTRIPCOLLNUMBER(CollectionDto.getTRIPCOLLNUMBER());
				status.setVID(CollectionDto.getVID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

				if (CollectionDto.getTRIP_OPEN_DATE_ON() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_ON()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
				status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());

				status.setTRIP_CLOSE_STATUS_ID(CollectionDto.getTRIP_CLOSE_STATUS_ID());
				status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_CLOSE_STATUS_ID()));

				status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
				status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
				status.setTRIP_DRIVER_JAMA(CollectionDto.getTRIP_DRIVER_JAMA());

				status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
				status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
				status.setTRIP_CONDUCTOR_JAMA(CollectionDto.getTRIP_CONDUCTOR_JAMA());

				status.setTRIP_CLEANER_ID(CollectionDto.getTRIP_CLEANER_ID());
				status.setTRIP_CLEANER_NAME(CollectionDto.getTRIP_CLEANER_NAME());

				Dtos.add(status);
			}
		}
		return Dtos;
	}

	/**
	 * @param tripCol
	 * @return
	 *//*
	public TripCollectionSheetDto Show_TripCollectionSheet_page(TripCollectionSheet CollectionDto) {

		TripCollectionSheetDto status = new TripCollectionSheetDto();

		status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
		status.setTRIPCOLLNUMBER(CollectionDto.getTRIPCOLLNUMBER());
		status.setVID(CollectionDto.getVID());

		status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
		status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
		status.setVEHICLE_GROUP_ID(CollectionDto.getVEHICLE_GROUP_ID());
		status.setTRIP_OPEN_KM(CollectionDto.getTRIP_OPEN_KM());

		status.setTRIP_CLOSE_KM(CollectionDto.getTRIP_CLOSE_KM());
		status.setTRIP_USAGE_KM(CollectionDto.getTRIP_USAGE_KM());

		if (CollectionDto.getTRIP_OPEN_DATE() != null) {
			status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE()));
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

		status.setTRIP_DIESEL_LITER(CollectionDto.getTRIP_DIESEL_LITER());
		status.setTRIP_SINGL(CollectionDto.getTRIP_SINGL());

		status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
		status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
		status.setTOTAL_INCOME(CollectionDto.getTOTAL_INCOME());
		status.setTRIP_DRIVER_JAMA(CollectionDto.getTRIP_DRIVER_JAMA());
		status.setTRIP_CONDUCTOR_JAMA(CollectionDto.getTRIP_CONDUCTOR_JAMA());
		status.setDRIVER_ADVANCE_STATUS(CollectionDto.getDRIVER_ADVANCE_STATUS());
		status.setCONDUCTOR_ADVANCE_STATUS(CollectionDto.getCONDUCTOR_ADVANCE_STATUS());
		status.setTRIP_CLOSE_STATUS(CollectionDto.getTRIP_CLOSE_STATUS());

		status.setTRIP_REMARKS(CollectionDto.getTRIP_REMARKS());

		status.setCREATEDBY(CollectionDto.getCREATEDBY());
		status.setLASTMODIFIEDBY(CollectionDto.getLASTMODIFIEDBY());
		status.setMarkForDelete(CollectionDto.isMarkForDelete());

		*//** Set Created Current Date *//*
		if (CollectionDto.getCREATED() != null) {
			*//** Set Created Current Date *//*
			status.setCREATED(CreatedDateTime.format(CollectionDto.getCREATED()));
		}
		if (CollectionDto.getlASTUPDATED() != null) {
			status.setlASTUPDATED(CreatedDateTime.format(CollectionDto.getlASTUPDATED()));
		}
		return status;
	}
*/
	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Expense_TripCollection(List<TripCollectionSheetDto> collection) {
		Double Expense = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheetDto tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTOTAL_EXPENSE() != null) {
					Expense += tripCollectionSheet.getTOTAL_EXPENSE();
				}
			}
		}
		return Expense;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Income_TripCollection(List<TripCollectionSheetDto> collection) {

		Double INCOME = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheetDto tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTOTAL_INCOME() != null) {
					INCOME += tripCollectionSheet.getTOTAL_INCOME();
				}
			}
		}
		return INCOME;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Balance_TripCollection(List<TripCollectionSheetDto> collection) {

		Double BALANCE = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheetDto tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTOTAL_BALANCE() != null) {
					BALANCE += tripCollectionSheet.getTOTAL_BALANCE();
				}
			}
		}
		return BALANCE;
	}

	/**
	 * @param collection
	 * @return
	 */
	public List<TripGroupCollectionDto> prepare_Trip_Group_CollectionDto(List<TripGroupCollectionDto> collection) {

		List<TripGroupCollectionDto> Dtos = null;
		if (collection != null && !collection.isEmpty()) {
			Dtos = new ArrayList<TripGroupCollectionDto>();
			TripGroupCollectionDto status = null;
			for (TripGroupCollectionDto CollectionDto : collection) {
				status = new TripGroupCollectionDto();

				status.setTRIPGROUPID(CollectionDto.getTRIPGROUPID());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());
				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_BUS(CollectionDto.getTOTAL_BUS());
				status.setTOTAL_COLLECTION(CollectionDto.getTOTAL_COLLECTION());
				status.setTOTAL_DIESEL(CollectionDto.getTOTAL_DIESEL());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_GROUP_COLLECTION(CollectionDto.getTOTAL_GROUP_COLLECTION());
				status.setTOTAL_SINGL(CollectionDto.getTOTAL_SINGL());
				if (CollectionDto.getTRIP_OPEN_DATE_ON() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_ON()));
				}
				status.setTRIP_CLOSE_STATUS_ID(CollectionDto.getTRIP_CLOSE_STATUS_ID());
				status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_CLOSE_STATUS_ID()));

				Dtos.add(status);
			}
		}

		return Dtos;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Expense_TripGroupCollection(List<TripGroupCollectionDto> collection) {
		Double Expense = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripGroupCollectionDto CollectionDto : collection) {

				if (CollectionDto.getTOTAL_EXPENSE() != null) {
					Expense += CollectionDto.getTOTAL_EXPENSE();
				}
			}
		}
		return Expense;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Income_TripGroupCollection(List<TripGroupCollectionDto> collection) {

		Double Income = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripGroupCollectionDto CollectionDto : collection) {

				if (CollectionDto.getTOTAL_COLLECTION() != null) {
					Income += CollectionDto.getTOTAL_COLLECTION();
				}
			}
		}
		return Income;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Balance_TripGroupCollection(List<TripGroupCollectionDto> collection) {

		Double Balance = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripGroupCollectionDto CollectionDto : collection) {

				if (CollectionDto.getTOTAL_BALANCE() != null) {
					Balance += CollectionDto.getTOTAL_BALANCE();
				}
			}
		}
		return Balance;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_GroupCol_TripGroupCollection(List<TripGroupCollectionDto> collection) {

		Double GroupCollection = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripGroupCollectionDto CollectionDto : collection) {

				if (CollectionDto.getTOTAL_GROUP_COLLECTION() != null) {
					GroupCollection += CollectionDto.getTOTAL_GROUP_COLLECTION();
				}
			}
		}
		return GroupCollection;
	}

	/**
	 * @param collection
	 * @return
	 */
	public List<TripDayCollectionDto> prepare_Trip_Day_CollectionDto(List<TripDayCollection> collection) {

		List<TripDayCollectionDto> Dtos = null;
		if (collection != null && !collection.isEmpty()) {
			Dtos = new ArrayList<TripDayCollectionDto>();
			TripDayCollectionDto status = null;
			for (TripDayCollection CollectionDto : collection) {
				status = new TripDayCollectionDto();

				status.setTRIPDAYID(CollectionDto.getTRIPDAYID());
				status.setTOTAL_BALANCE(CollectionDto.getTOTAL_BALANCE());
				status.setTOTAL_BUS(CollectionDto.getTOTAL_BUS());
				status.setTOTAL_COLLECTION(CollectionDto.getTOTAL_COLLECTION());
				status.setTOTAL_DIESEL(CollectionDto.getTOTAL_DIESEL());
				status.setTOTAL_EXPENSE(CollectionDto.getTOTAL_EXPENSE());
				status.setTOTAL_GROUP_COLLECTION(CollectionDto.getTOTAL_GROUP_COLLECTION());
				status.setTOTAL_SINGL(CollectionDto.getTOTAL_SINGL());
				if (CollectionDto.getTRIP_OPEN_DATE() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE()));
				}
				status.setTRIP_CLOSE_STATUS_ID(CollectionDto.getTRIP_CLOSE_STATUS_ID());
				status.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName(CollectionDto.getTRIP_CLOSE_STATUS_ID()));

				status.setEACH_BUS_COLLECTION(CollectionDto.getEACH_BUS_COLLECTION());
				status.setPER_SINGL_COLLECTION(CollectionDto.getPER_SINGL_COLLECTION());
				Dtos.add(status);
			}
		}
		return Dtos;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Expense_TripDayCollection(List<TripDayCollection> collection) {

		Double expense = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripDayCollection CollectionDto : collection) {

				if (CollectionDto.getTOTAL_EXPENSE() != null) {
					expense += CollectionDto.getTOTAL_EXPENSE();
				}
			}
		}
		return expense;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Income_TripDayCollection(List<TripDayCollection> collection) {

		Double expense = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripDayCollection CollectionDto : collection) {

				if (CollectionDto.getTOTAL_COLLECTION() != null) {
					expense += CollectionDto.getTOTAL_COLLECTION();
				}
			}
		}
		return expense;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_Balance_TripDayCollection(List<TripDayCollection> collection) {

		Double BALANCE = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripDayCollection CollectionDto : collection) {

				if (CollectionDto.getTOTAL_BALANCE() != null) {
					BALANCE += CollectionDto.getTOTAL_BALANCE();
				}
			}
		}
		return BALANCE;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_DriverJAMA_TripCollection(List<TripCollectionSheetDto> collection) {

		Double DRIVER_JAMA = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheetDto tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTRIP_DRIVER_JAMA() != null) {
					DRIVER_JAMA += tripCollectionSheet.getTRIP_DRIVER_JAMA();
				}
			}
		}
		return DRIVER_JAMA;
	}
	
	public Double Get_Total_DriverJAMA_TripCollection(List<TripCollectionSheet> collection) {

		Double DRIVER_JAMA = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheet tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTRIP_DRIVER_JAMA() != null) {
					DRIVER_JAMA += tripCollectionSheet.getTRIP_DRIVER_JAMA();
				}
			}
		}
		return DRIVER_JAMA;
	}

	/**
	 * @param collection
	 * @return
	 */
	public Double Total_ConductorJAMA_TripCollection(List<TripCollectionSheetDto> collection) {

		Double CONDUCTOR_JAMA = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheetDto tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTRIP_CONDUCTOR_JAMA() != null) {
					CONDUCTOR_JAMA += tripCollectionSheet.getTRIP_CONDUCTOR_JAMA();
				}
			}
		}
		return CONDUCTOR_JAMA;
	}
	
	public Double get_Total_ConductorJAMA_TripCollection(List<TripCollectionSheet> collection) {

		Double CONDUCTOR_JAMA = 0.0;
		if (collection != null && !collection.isEmpty()) {
			for (TripCollectionSheet tripCollectionSheet : collection) {
				if (tripCollectionSheet.getTRIP_CONDUCTOR_JAMA() != null) {
					CONDUCTOR_JAMA += tripCollectionSheet.getTRIP_CONDUCTOR_JAMA();
				}
			}
		}
		return CONDUCTOR_JAMA;
	}

	/**
	 * @param driverAdvanvce
	 * @return
	 */
	public Double Driver_Advance_Total_Amount(List<DriverAdvance> driverAdvanvce) {

		Double Advance = 0.0;
		if (driverAdvanvce != null && !driverAdvanvce.isEmpty()) {
			for (DriverAdvance tripCollectionSheet : driverAdvanvce) {
				if (tripCollectionSheet.getDRIVER_ADVANCE_AMOUNT() != null) {
					Advance += tripCollectionSheet.getDRIVER_ADVANCE_AMOUNT();
				}
			}
		}
		return Advance;
	}
	
	public Double Driver_Advance_Total_Amt(List<DriverAdvanceDto> driverAdvanvce) {

		Double Advance = 0.0;
		if (driverAdvanvce != null && !driverAdvanvce.isEmpty()) {
			for (DriverAdvanceDto tripCollectionSheet : driverAdvanvce) {
				if (tripCollectionSheet.getDRIVER_ADVANCE_AMOUNT() != null) {
					Advance += tripCollectionSheet.getDRIVER_ADVANCE_AMOUNT();
				}
			}
		}
		return Advance;
	}

	/**
	 * @param collection
	 * @return
	 * @throws Exception 
	 */
	public List<DriverAdvanceDto> prepare_Driver_ADVANCE_JAMA_TripDto(List<DriverAdvanceDto> collection) throws Exception {

		List<DriverAdvanceDto> DTO = null;
		// for loop trip_Group_sheet
		if (collection != null && !collection.isEmpty()) {
			DTO = new ArrayList<DriverAdvanceDto>();
			DriverAdvanceDto DRIADV = null;
			for (DriverAdvanceDto advance : collection) {
				DRIADV = new DriverAdvanceDto();

				DRIADV.setDRIADVID(advance.getDRIADVID());

				DRIADV.setTRIP_DRIVER_ID(advance.getTRIP_DRIVER_ID());
				DRIADV.setTRIP_DRIVER_NAME(advance.getTRIP_DRIVER_NAME());

				if (advance.getADVANCE_DATE_ON() != null) {
					DRIADV.setADVANCE_DATE(dateFormat_Name.format(advance.getADVANCE_DATE_ON()));
				}
				DRIADV.setADVANCE_PAID_BY(advance.getADVANCE_PAID_BY());
				DRIADV.setADVANCE_PAID_NUMBER(advance.getADVANCE_PAID_NUMBER());
				DRIADV.setADVANCE_PAID_TYPE_ID(advance.getADVANCE_PAID_TYPE_ID());
				DRIADV.setADVANCE_PAID_TYPE(PaymentTypeConstant.getPaymentTypeName(advance.getADVANCE_PAID_TYPE_ID()));
				DRIADV.setADVANCE_STATUS_ID(advance.getADVANCE_STATUS_ID());
				DRIADV.setADVANCE_STATUS(DriverStatus.getDriverStatus(advance.getADVANCE_STATUS_ID()));
				DRIADV.setCREATED(advance.getCREATED());
				DRIADV.setCREATEDBY(advance.getCREATEDBY());
				DRIADV.setDRIVER_ADVANCE_AMOUNT(advance.getDRIVER_ADVANCE_AMOUNT());
				DRIADV.setDRIVER_ADVANCE_REMARK(advance.getDRIVER_ADVANCE_REMARK());
				DRIADV.setDRIVER_JAMA_BALANCE(advance.getDRIVER_JAMA_BALANCE());
				DRIADV.setLASTMODIFIEDBY(advance.getLASTMODIFIEDBY());

				DTO.add(DRIADV);
			}
		}
		return DTO;
	}

	/**
	 * @param collection
	 * @param advanceCollection
	 * @return
	 */
	public List<TripCollectionSheetDto> prepare_Driver_JAMA_AND_Adavance_Report(
			List<TripCollectionSheetDto> tripCollection, List<DriverAdvanceDto> advanceCollection) {

		ArrayList<TripCollectionSheetDto> Dtos = new ArrayList<TripCollectionSheetDto>();

		TripCollectionSheetDto status = null;
		if (tripCollection != null && !tripCollection.isEmpty()) {

			for (TripCollectionSheetDto CollectionDto : tripCollection) {
				status = new TripCollectionSheetDto();

				status.setTRIPCOLLID(CollectionDto.getTRIPCOLLID());
				status.setTRIPCOLLNUMBER(CollectionDto.getTRIPCOLLNUMBER());
				status.setVID(CollectionDto.getVID());

				status.setVEHICLE_REGISTRATION(CollectionDto.getVEHICLE_REGISTRATION());
				status.setVEHICLE_GROUP(CollectionDto.getVEHICLE_GROUP());

				if (CollectionDto.getTRIP_OPEN_DATE_ON() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(CollectionDto.getTRIP_OPEN_DATE_ON()));
				}
				status.setTRIP_ROUTE_NAME(CollectionDto.getTRIP_ROUTE_NAME());

				status.setTRIP_DRIVER_ID(CollectionDto.getTRIP_DRIVER_ID());
				status.setTRIP_DRIVER_NAME(CollectionDto.getTRIP_DRIVER_NAME());
				status.setTRIP_DRIVER_JAMA(CollectionDto.getTRIP_DRIVER_JAMA());

				status.setTRIP_CONDUCTOR_ID(CollectionDto.getTRIP_CONDUCTOR_ID());
				status.setTRIP_CONDUCTOR_NAME(CollectionDto.getTRIP_CONDUCTOR_NAME());
				status.setTRIP_CONDUCTOR_JAMA(CollectionDto.getTRIP_CONDUCTOR_JAMA());

				// Note: Here Total Balance is Driver Advance Amount Value
				status.setTOTAL_BALANCE(null);

				Dtos.add(status);
			}
		}

		// Driver and Conductor Advance Balance Details To showing purpose using
		// Details
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (DriverAdvanceDto advance : advanceCollection) {

				status = new TripCollectionSheetDto();

				status.setTRIPCOLLID(advance.getDRIADVID());
				status.setVID(null);

				// Note: VEHICLE_REGISTRATION to show Advance Table
				// ADVANCE_PAID_NUMBER
				status.setVEHICLE_REGISTRATION(advance.getADVANCE_PAID_NUMBER());

				// Note: VEHICLE_GROUP to show Advance Table ADVANCE_PAID_TYPE
				status.setVEHICLE_GROUP(PaymentTypeConstant.getPaymentTypeName(advance.getADVANCE_PAID_TYPE_ID()));

				// Note: TRIP_OPEN_DATE to show Advance Table ADVANCE_DATE
				if (advance.getADVANCE_DATE_ON() != null) {
					status.setTRIP_OPEN_DATE(dateFormat_Name.format(advance.getADVANCE_DATE_ON()));
				}
				// Note: TRIP_ROUTE_NAME to show Advance Table
				// DRIVER_ADVANCE_REMARK
				status.setTRIP_ROUTE_NAME(advance.getDRIVER_ADVANCE_REMARK());

				status.setTRIP_DRIVER_ID(advance.getTRIP_DRIVER_ID());
				status.setTRIP_DRIVER_NAME(advance.getTRIP_DRIVER_NAME());
				status.setTRIP_DRIVER_JAMA(null);

				status.setTRIP_CONDUCTOR_ID(advance.getTRIP_DRIVER_ID());
				status.setTRIP_CONDUCTOR_NAME(advance.getTRIP_DRIVER_NAME());
				status.setTRIP_CONDUCTOR_JAMA(null);

				// Note: Here Total Balance is Driver Advance Amount Value
				status.setTOTAL_BALANCE(advance.getDRIVER_ADVANCE_AMOUNT());

				Dtos.add(status);

			}
		}

		Collections.sort(Dtos);

		return Dtos;
	}

	/**
	 * @param advanceCollection
	 * @return
	 */
	public Double prepare_Total_Adavance_Amount(List<TripSheetDto> advanceCollection) {

		Double Expense = 0.0;
		if (advanceCollection != null && !advanceCollection.isEmpty()) {
			for (TripSheetDto tripCollectionSheet : advanceCollection) {
				if (tripCollectionSheet.getTripTotalAdvance() != null) {
					Expense += tripCollectionSheet.getTripTotalAdvance();
				}
			}
		}
		return Expense;
	}

	public Double DriverSalaryAdvance_Total_Amount(List<DriverSalaryAdvance> driverAdvanvce) {
		
		Double Expense = 0.0;
		if (driverAdvanvce != null && !driverAdvanvce.isEmpty()) {
			for (DriverSalaryAdvance tripCollectionSheet : driverAdvanvce) {
				if (tripCollectionSheet.getADVANCE_BALANCE() != null) {
					Expense += tripCollectionSheet.getADVANCE_BALANCE();
				}
			}
		}
		return Expense;
	}

}
