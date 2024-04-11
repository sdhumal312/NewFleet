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

import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.VehicleOdometerHistoryDto;
import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;

public class VehicleOdometerHistoryBL {
	public VehicleOdometerHistoryBL() {
	}

	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm ");

	// save Vehicle odoMeter History in VehicLe Meter
	public VehicleOdometerHistory prepareOdometerGetHistoryToVehicle(Vehicle vehicle) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(vehicle.getVid());
	//	odometerHistory.setVehicle_registration(vehicle.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(vehicle.getVehicle_Odometer());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("Vehicle Entry");
		odometerHistory.setVoh_updateId((long) vehicle.getVid());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in VehicLe Meter
	public VehicleOdometerHistory prepareOdometerGetHistoryToFuel(Fuel fuel) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(fuel.getVid());
		//odometerHistory.setVehicle_registration(fuel.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(fuel.getFuel_meter());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("Fuel Entry");
		odometerHistory.setVoh_updateId(fuel.getFuel_id());

		return odometerHistory;
	}
	
	public VehicleOdometerHistory prepareOdometerGetHistoryToUrea(UreaEntries ureaEntries) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(ureaEntries.getVid());
		//odometerHistory.setVehicle_registration(fuel.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(ureaEntries.getUreaOdometer().intValue());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("Urea Entry");
		odometerHistory.setVoh_updateId(ureaEntries.getUreaEntriesId());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in TripSheet
	public VehicleOdometerHistory prepareOdometerGetHistoryToTripsheet(TripSheet tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVid());
		//odometerHistory.setVehicle_registration(tripsheet.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(tripsheet.getTripOpeningKM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripSheet Open Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTripSheetID());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in TripSheet
	public VehicleOdometerHistory prepareOdometerGetHistoryToTripCollectionSheet(TripCollectionSheetDto tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVID());
	//	odometerHistory.setVehicle_registration(tripsheet.getVEHICLE_REGISTRATION());
		odometerHistory.setVehicle_Odometer(tripsheet.getTRIP_OPEN_KM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripCollection Open Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTRIPCOLLID());

		return odometerHistory;
	}

	public VehicleOdometerHistory prepareOdometerGetHistoryToTripCollectionSheet(TripCollectionSheet tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVID());
	//	odometerHistory.setVehicle_registration(tripsheet.getVEHICLE_REGISTRATION());
		odometerHistory.setVehicle_Odometer(tripsheet.getTRIP_OPEN_KM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripCollection Open Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTRIPCOLLID());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in TripSheet Dto
	public VehicleOdometerHistory prepareOdometerGetHistoryToTripsheet(TripSheetDto tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVid());
		//odometerHistory.setVehicle_registration(tripsheet.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(tripsheet.getTripOpeningKM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripSheet Open Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTripSheetID());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in TripSheet Dto
	public VehicleOdometerHistory prepareOdometerGetHistoryToTripsheetCloseOdometer(TripSheetDto tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVid());
	//	odometerHistory.setVehicle_registration(tripsheet.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(tripsheet.getTripClosingKM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripSheet Close Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTripSheetID());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in TripSheet Dto
	public VehicleOdometerHistory prepareOdometerGetHistoryToWorkOrder(WorkOrders workOrder) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(workOrder.getVehicle_vid());
		//odometerHistory.setVehicle_registration(workOrder.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(workOrder.getVehicle_Odometer());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("WorkOrder Entry");
		odometerHistory.setVoh_updateId(workOrder.getWorkorders_id());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in TripSheet Dto
	public VehicleOdometerHistory prepareOdometerGetHistoryToServiceEntries(ServiceEntries ServiceEntries) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(ServiceEntries.getVid());
		//odometerHistory.setVehicle_registration(ServiceEntries.getVehicle_registration());
		odometerHistory.setVehicle_Odometer(ServiceEntries.getVehicle_Odometer());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("Service Entry");
		odometerHistory.setVoh_updateId(ServiceEntries.getServiceEntries_id());

		return odometerHistory;
	}

	/** save Vehicle odoMeter History in Issues */
	public VehicleOdometerHistory prepareOdometerGetHistoryToIssues(Issues issues) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(issues.getISSUES_VID());
		//odometerHistory.setVehicle_registration(issues.getISSUES_VEHICLE_REGISTRATION());
		odometerHistory.setVehicle_Odometer(issues.getISSUES_ODOMETER());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("ISSUES_ENTRY");
		odometerHistory.setVoh_updateId(issues.getISSUES_ID());

		return odometerHistory;
	}

	// save Vehicle odoMeter History in Tyre Assign Dto
	public VehicleOdometerHistory prepareOdometerGetHistoryToTyreAssign(Integer VEHICLE_ID, String VEHICLE_REG,
			Integer Odometer, Long TYRE_ID) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(VEHICLE_ID);
		//odometerHistory.setVehicle_registration(VEHICLE_REG);
		odometerHistory.setVehicle_Odometer(Odometer);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TYRE_ENTRY");
		odometerHistory.setVoh_updateId(TYRE_ID);

		return odometerHistory;
	}

	public List<VehicleOdometerHistoryDto> vehicleToHistoryOdometerDetails(
			List<VehicleOdometerHistoryDto> vehicleOdometerHistory, Integer Current_vehicleOdmeter) {
		List<VehicleOdometerHistoryDto> Dtos = null;
		if (vehicleOdometerHistory != null && !vehicleOdometerHistory.isEmpty()) {
			Dtos = new ArrayList<VehicleOdometerHistoryDto>();
			VehicleOdometerHistoryDto History = null;
			for (VehicleOdometerHistoryDto odometer : vehicleOdometerHistory) {
				History = new VehicleOdometerHistoryDto();

				History.setVoh_id(odometer.getVoh_id());
				History.setVid(odometer.getVid());
				History.setVehicle_registration(odometer.getVehicle_registration());
				if (odometer.getVoh_date_On() != null) {
					History.setVoh_date(dateFormatonlyDateTime.format(odometer.getVoh_date_On()));
				}
				History.setVehicle_Odometer(odometer.getVehicle_Odometer());
				History.setVoh_updateId(odometer.getVoh_updateId());
				History.setVoh_updatelocation(odometer.getVoh_updatelocation());
				History.setVoh_updateNumber(odometer.getVoh_updateNumber());
				
				String checkLocation = odometer.getVoh_updatelocation();
				
				if(odometer.getVoh_updatelocation().equals("ISSUES_ENTRY")) {	
					History.setVoh_updateIdStr(AESEncryptDecrypt.encryptBase64("" +odometer.getVoh_updateId()));
				}else {
					History.setVoh_updateId(odometer.getVoh_updateId());
				}

				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (odometer.getVoh_date_On() != null) {

					int diffInDays = (int) ((odometer.getVoh_date_On().getTime() - toDate.getTime())
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
					History.setDiffrent_time_day(diffenceDays);
				}

				try {
					Integer HistoryOdometer = 0;
					if (odometer.getVehicle_Odometer() != null) {
						HistoryOdometer = odometer.getVehicle_Odometer();
					}

					Integer diff_Odmeter = HistoryOdometer - Current_vehicleOdmeter;

					String diffenceOdometer = null;

					switch (diff_Odmeter) {
					case 0:
						diffenceOdometer = ("Current Km Today");
						break;
					case -1:
						diffenceOdometer = ("YesterDay");
						break;
					case 1:
						diffenceOdometer = ("Tomorrow");
						break;
					default:
						if (diff_Odmeter < -1) {

							diffenceOdometer = (-diff_Odmeter + " Km ago");

						} else if (diff_Odmeter > 1) {

							diffenceOdometer = (diff_Odmeter + " km from now");
						}
						break;
					}

					History.setDiffrent_meter_oddmeter(diffenceOdometer);

				} catch (Exception e) {

					e.printStackTrace();
				}

				Dtos.add(History);
			}
		}
		return Dtos;
	}

	/*******************************************************************/
	/*********** SRI DURGAMBA CASHBOOK LOGIC TRIP DAILY ***************/
	/*****************************************************************/

	// save Vehicle odoMeter History in TripSheet
	public VehicleOdometerHistory prepareOdometerGetHistoryToTripDailySheet(TripDailySheet tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVEHICLEID());
		//odometerHistory.setVehicle_registration(tripsheet.getVEHICLE_REGISTRATION());
		odometerHistory.setVehicle_Odometer(tripsheet.getTRIP_OPEN_KM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripDaily Open Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTRIPDAILYID());

		return odometerHistory;
	}
	
	public VehicleOdometerHistory prepareOdometerGetHistoryToTripDailySheet(TripDailySheetDto tripsheet) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		odometerHistory.setVid(tripsheet.getVEHICLEID());
		//odometerHistory.setVehicle_registration(tripsheet.getVEHICLE_REGISTRATION());
		odometerHistory.setVehicle_Odometer(tripsheet.getTRIP_OPEN_KM());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		odometerHistory.setVoh_date(toDate);

		odometerHistory.setVoh_updatelocation("TripDaily Open Entry");
		odometerHistory.setVoh_updateId(tripsheet.getTRIPDAILYID());

		return odometerHistory;
	}
	
	public VehicleOdometerHistory prepareOdometerGetHistoryToDealerServiceEntries(DealerServiceEntries dealerServiceEntries) {
		VehicleOdometerHistory odometerHistory = new VehicleOdometerHistory();
		
		odometerHistory.setVid(dealerServiceEntries.getVid());
		odometerHistory.setVehicle_Odometer(dealerServiceEntries.getVehicleOdometer());
		odometerHistory.setVoh_date(DateTimeUtility.getCurrentTimeStamp());
		odometerHistory.setVoh_updatelocation("Dealer Service Entry");
		odometerHistory.setVoh_updateId(dealerServiceEntries.getDealerServiceEntriesId());

		return odometerHistory;
	}

}
