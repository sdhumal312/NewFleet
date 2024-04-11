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
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.VehicleAcType;
import org.fleetopgroup.constant.VehicleFuelType;
import org.fleetopgroup.constant.VehicleFuelUnit;
import org.fleetopgroup.constant.VehicleMeterUnit;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleHistory;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class VehicleBL {

	SimpleDateFormat sqldateTime = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");

	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Vehicle prepareModel(Vehicle vehicleDto) throws Exception {
		Vehicle vehicle = new Vehicle();

		vehicle.setVid(vehicleDto.getVid());
		vehicle.setVehicle_registration(vehicleDto.getVehicle_registration());
		vehicle.setVehicle_chasis(vehicleDto.getVehicle_chasis());
		vehicle.setVehicle_engine(vehicleDto.getVehicle_engine());
		
		//vehicle.setVehicle_Type(vehicleDto.getVehicle_Type());
		vehicle.setVehicleTypeId(vehicleDto.getVehicleTypeId());
		
		vehicle.setVehicle_year(vehicleDto.getVehicle_year());
		vehicle.setVehicle_maker(vehicleDto.getVehicle_maker());
		vehicle.setVehicle_Model(vehicleDto.getVehicle_Model());
		vehicle.setVehicle_registrationState(vehicleDto.getVehicle_registrationState());
		vehicle.setVehicle_RegisterDate(vehicleDto.getVehicle_RegisterDate());
		vehicle.setVehicle_Registeredupto(vehicleDto.getVehicle_Registeredupto());
		
		//vehicle.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicle.getvStatusId()));
		vehicle.setvStatusId(vehicleDto.getvStatusId());
		
		vehicle.setVehicleGroupId(vehicleDto.getVehicleGroupId());
		//vehicle.setVehicle_Group(vehicleDto.getVehicle_Group());
		
		vehicle.setRouteID(vehicleDto.getRouteID());
		//vehicle.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		
		vehicle.setVehicleOwnerShipId(vehicleDto.getVehicleOwnerShipId());
		//vehicle.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());
		
		vehicle.setVehicle_Location(vehicleDto.getVehicle_Location());
		vehicle.setVehicle_Color(vehicleDto.getVehicle_Color());
		vehicle.setVehicle_Class(vehicleDto.getVehicle_Class());
		vehicle.setVehicle_body(vehicleDto.getVehicle_body());
		
		//vehicle.setVehicle_actype(vehicleDto.getVehicle_actype());
		vehicle.setAcTypeId(vehicleDto.getAcTypeId());
		
		vehicle.setVehicle_Cylinders(vehicleDto.getVehicle_Cylinders());
		vehicle.setVehicle_CubicCapacity(vehicleDto.getVehicle_CubicCapacity());
		vehicle.setVehicle_Power(vehicleDto.getVehicle_Power());
		vehicle.setVehicle_wheelBase(vehicleDto.getVehicle_wheelBase());
		vehicle.setVehicle_SeatCapacity(vehicleDto.getVehicle_SeatCapacity());
		vehicle.setVehicle_UnladenWeight(vehicleDto.getVehicle_UnladenWeight());
		vehicle.setVehicle_LadenWeight(vehicleDto.getVehicle_LadenWeight());
		
		vehicle.setVehicleFuelId(vehicleDto.getVehicleFuelId());
		//vehicle.setVehicle_Fuel(vehicleDto.getVehicle_Fuel());
		vehicle.setVehicle_FuelTank1(vehicleDto.getVehicle_FuelTank1());
		
		vehicle.setVehicle_Oil(vehicleDto.getVehicle_Oil());
		
		vehicle.setVehicleMeterUnitId(vehicleDto.getVehicleMeterUnitId());
		//vehicle.setVehicle_MeterUnit(vehicleDto.getVehicle_MeterUnit());
		
		vehicle.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
		
		vehicle.setVehicleFuelUnitId(vehicleDto.getVehicleFuelUnitId());
		//vehicle.setVehicle_FuelUnit(vehicleDto.getVehicle_FuelUnit());

		vehicle.setVehicle_ExpectedMileage(vehicleDto.getVehicle_ExpectedMileage());
		vehicle.setVehicle_ExpectedMileage_to(vehicleDto.getVehicle_ExpectedMileage_to());
		
		vehicle.setVehicle_ExpectedOdameter(vehicleDto.getVehicle_ExpectedOdameter());
		
		vehicle.setVehicle_photoid(vehicleDto.getVehicle_photoid());

		vehicle.setTripSheetID((long) 0);

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vehicle.setCreatedById(userDetails.getId());
		vehicle.setLastModifiedById(userDetails.getId());
		/** who Create this date details */
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		vehicle.setCreated(toDate);
		vehicle.setLastupdated(toDate);
		vehicle.setMarkForDelete(false);
		vehicle.setCompany_Id(userDetails.getCompany_id());
		vehicle.setVehicleGPSId(vehicleDto.getVehicleGPSId());
		vehicle.setMobileNumber(vehicleDto.getMobileNumber());
		vehicle.setLedgerName(vehicleDto.getLedgerName());
		vehicle.setGpsVendorId(vehicleDto.getGpsVendorId());
		
		
		return vehicle;
	}

	public Vehicle update_prepareModel(VehicleDto vehicleDto, Vehicle OldVehicle) throws Exception {
		Vehicle vehicle = new Vehicle();

		vehicle.setVid(OldVehicle.getVid());
		vehicle.setVehicle_registration(OldVehicle.getVehicle_registration());
		vehicle.setVehicle_chasis(vehicleDto.getVehicle_chasis());
		vehicle.setVehicle_engine(vehicleDto.getVehicle_engine());
		//vehicle.setVehicle_Type(vehicleDto.getVehicle_Type());
		vehicle.setVehicle_year(vehicleDto.getVehicle_year());
		vehicle.setVehicle_maker(vehicleDto.getVehicle_maker());
		vehicle.setVehicle_Model(vehicleDto.getVehicle_Model());
		vehicle.setVehicle_registrationState(vehicleDto.getVehicle_registrationState());
		vehicle.setVehicle_RegisterDate(vehicleDto.getVehicle_RegisterDate());
		vehicle.setVehicle_Registeredupto(vehicleDto.getVehicle_Registeredupto());
		//vehicle.setVehicle_Status(vehicleDto.getVehicle_Status());
		//vehicle.setVehicle_Group(vehicleDto.getVehicle_Group());
		//vehicle.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		vehicle.setVehicle_Location(vehicleDto.getVehicle_Location());
		vehicle.setVehicle_Color(vehicleDto.getVehicle_Color());
		vehicle.setVehicle_Class(vehicleDto.getVehicle_Class());
		vehicle.setVehicle_body(vehicleDto.getVehicle_body());
		//vehicle.setVehicle_actype(vehicleDto.getVehicle_actype());
		vehicle.setVehicle_Cylinders(vehicleDto.getVehicle_Cylinders());
		vehicle.setVehicle_CubicCapacity(vehicleDto.getVehicle_CubicCapacity());
		vehicle.setVehicle_Power(vehicleDto.getVehicle_Power());
		vehicle.setVehicle_wheelBase(vehicleDto.getVehicle_wheelBase());
		vehicle.setVehicle_SeatCapacity(vehicleDto.getVehicle_SeatCapacity());
		vehicle.setVehicle_UnladenWeight(vehicleDto.getVehicle_UnladenWeight());
		vehicle.setVehicle_LadenWeight(vehicleDto.getVehicle_LadenWeight());
	//	vehicle.setVehicle_Fuel(vehicleDto.getVehicle_Fuel());
		vehicle.setVehicle_FuelTank1(vehicleDto.getVehicle_FuelTank1());
		vehicle.setVehicle_Oil(vehicleDto.getVehicle_Oil());
	//	vehicle.setVehicle_MeterUnit(vehicleDto.getVehicle_MeterUnit());
		vehicle.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
		//vehicle.setVehicle_FuelUnit(vehicleDto.getVehicle_FuelUnit());

	//	vehicle.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());

		vehicle.setVehicle_ExpectedMileage(vehicleDto.getVehicle_ExpectedMileage());
		vehicle.setVehicle_ExpectedMileage_to(vehicleDto.getVehicle_ExpectedMileage_to());

		vehicle.setVehicle_photoid(OldVehicle.getVehicle_photoid());

		vehicle.setTripSheetID(OldVehicle.getTripSheetID());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vehicle.setCreatedById(userDetails.getId());
		vehicle.setLastModifiedById(userDetails.getId());
		/** who Create this date details */
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		vehicle.setCreated(toDate);
		vehicle.setLastupdated(toDate);
		vehicle.setMarkForDelete(false);
		vehicle.setvStatusId(vehicleDto.getvStatusId());
		vehicle.setVehicleGroupId(vehicleDto.getVehicleGroupId());
		//vehicle.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
		vehicle.setVehicleTypeId(vehicleDto.getVehicleTypeId());
		vehicle.setRouteID(vehicleDto.getRouteID());
		vehicle.setAcTypeId(vehicleDto.getAcTypeId());
		vehicle.setVehicleFuelId(vehicleDto.getVehicleFuelId());
		vehicle.setVehicleMeterUnitId(vehicleDto.getVehicleMeterUnitId());
		vehicle.setVehicleFuelUnitId(vehicleDto.getVehicleFuelUnitId());
		vehicle.setVehicleOwnerShipId(vehicleDto.getVehicleOwnerShipId());
		vehicle.setVehicle_ExpectedOdameter(vehicleDto.getVehicle_ExpectedOdameter());
		vehicle.setVehicleGPSId(vehicleDto.getVehicleGPSId());
		vehicle.setMobileNumber(vehicleDto.getMobileNumber());
		vehicle.setLedgerName(vehicleDto.getLedgerName());
		vehicle.setGpsVendorId(vehicleDto.getGpsVendorId());
		
		return vehicle;
	}

	/* list of vehicle */
	public List<Vehicle> prepareListofDto(List<Vehicle> vehicles) {
		List<Vehicle> Dtos = null;
		if (vehicles != null && !vehicles.isEmpty()) {
			Dtos = new ArrayList<Vehicle>();
			Vehicle Dto = null;
			for (Vehicle vehicleDto : vehicles) {
				Dto = new Vehicle();

				Dto.setVid(vehicleDto.getVid());
				Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
				Dto.setVehicle_chasis(vehicleDto.getVehicle_chasis());
				Dto.setVehicle_engine(vehicleDto.getVehicle_engine());
				//Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
				Dto.setVehicle_year(vehicleDto.getVehicle_year());
				Dto.setVehicle_maker(vehicleDto.getVehicle_maker());
				Dto.setVehicle_Model(vehicleDto.getVehicle_Model());
				Dto.setVehicle_registrationState(vehicleDto.getVehicle_registrationState());
				Dto.setVehicle_RegisterDate(vehicleDto.getVehicle_RegisterDate());
				Dto.setVehicle_Registeredupto(vehicleDto.getVehicle_Registeredupto());
				//Dto.setVehicle_Status(vehicleDto.getVehicle_Status());
				//Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
				//Dto.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
				//Dto.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());
				Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
				Dto.setVehicle_Color(vehicleDto.getVehicle_Color());
				Dto.setVehicle_Class(vehicleDto.getVehicle_Class());
				Dto.setVehicle_body(vehicleDto.getVehicle_body());
				Dto.setVehicle_Cylinders(vehicleDto.getVehicle_Cylinders());
				Dto.setVehicle_CubicCapacity(vehicleDto.getVehicle_CubicCapacity());
				Dto.setVehicle_Power(vehicleDto.getVehicle_Power());
				Dto.setVehicle_wheelBase(vehicleDto.getVehicle_wheelBase());
				Dto.setVehicle_SeatCapacity(vehicleDto.getVehicle_SeatCapacity());
				Dto.setVehicle_UnladenWeight(vehicleDto.getVehicle_UnladenWeight());
				Dto.setVehicle_LadenWeight(vehicleDto.getVehicle_LadenWeight());
				//Dto.setVehicle_Fuel(vehicleDto.getVehicle_Fuel());
				Dto.setVehicle_FuelTank1(vehicleDto.getVehicle_FuelTank1());
				Dto.setVehicle_Oil(vehicleDto.getVehicle_Oil());
				//Dto.setVehicle_MeterUnit(vehicleDto.getVehicle_MeterUnit());
				Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
				//Dto.setVehicle_FuelUnit(vehicleDto.getVehicle_FuelUnit());
				Dto.setLastupdated(vehicleDto.getLastupdated());// last update
																// date

				Dto.setVehicle_photoid(vehicleDto.getVehicle_photoid());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	/**
	 * Mail page Vehicle Show only Recent Vehicle /* list of vehicle
	 * @throws Exception 
	 */
	public List<VehicleDto> prepareList_OF_Vehicle_ReCent(List<VehicleDto> vehicles) throws Exception {
		List<VehicleDto> Dtos = null;
		if (vehicles != null && !vehicles.isEmpty()) {
			Dtos = new ArrayList<>();
			VehicleDto Dto = null;
			for (VehicleDto vehicleDto : vehicles) {
				Dto = new VehicleDto();

				Dto.setVid(vehicleDto.getVid());
				Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
				Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
				Dto.setVehicle_maker(vehicleDto.getVehicle_maker());
				Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
				Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
				Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
				Dto.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName(vehicleDto.getVehicleOwnerShipId()));
				Dto.setTripSheetID(vehicleDto.getTripSheetID());
				Dto.setvStatusId(vehicleDto.getvStatusId());
				Dto.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
				Dto.setVehicleGroupId(vehicleDto.getVehicleGroupId());
				Dto.setTripSheetNumber(vehicleDto.getTripSheetNumber());
				Dto.setWorkOrder_Number(vehicleDto.getWorkOrder_Number());
				Dto.setVehicle_chasis(vehicleDto.getVehicle_chasis());
				Dto.setVehicle_engine(vehicleDto.getVehicle_engine());
				Dto.setBodyMakerName(vehicleDto.getBodyMakerName());
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}
	
	public List<VehicleDto> prepareList_OF_Vehicle(List<VehicleDto> vehicles) throws Exception {
		List<VehicleDto> Dtos = null;
		try {
			if (vehicles != null && !vehicles.isEmpty()) {
				Dtos = new ArrayList<VehicleDto>();
				VehicleDto Dto = null;
				for (VehicleDto vehicleDto : vehicles) {
					Dto = new VehicleDto();

					Dto.setVid(vehicleDto.getVid());
					Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
					Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
					Dto.setVehicle_maker(vehicleDto.getVehicle_maker());
					Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
					Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
					Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
					Dto.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName(vehicleDto.getVehicleOwnerShipId()));
					Dto.setTripSheetID(vehicleDto.getTripSheetID());
					Dto.setvStatusId(vehicleDto.getvStatusId());
					Dto.setVehicleGroupId(vehicleDto.getVehicleGroupId());
					
					if(vehicleDto.getvStatusId() != 0) {
						Dto.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
					}
					Dto.setTripSheetNumber(vehicleDto.getTripSheetNumber());
					Dto.setWorkOrder_Number(vehicleDto.getWorkOrder_Number());
					Dtos.add(Dto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
		
		
	}


	/**
	 * Vehicle Show Details in Header Value Option
	 */
	public VehicleDto prepare_Vehicle_Header_Show(VehicleDto vehicleDto) {
		VehicleDto Dto = new VehicleDto();

		Dto.setVid(vehicleDto.getVid());
		Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
		Dto.setVehicle_photoid(vehicleDto.getVehicle_photoid());
		Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
		Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
		Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
		Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
		Dto.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());
		Dto.setVehicle_Status(vehicleDto.getVehicle_Status());
		Dto.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		Dto.setVehicleGroupId(vehicleDto.getVehicleGroupId());

		return Dto;
	}

	/**
	 * Vehicle Show Details in Header Fuel Extries Value Option
	 * @throws Exception 
	 */
	public VehicleDto prepare_Vehicle_Header_Fuel_Entries_Show(VehicleDto vehicleDto) throws Exception {
		VehicleDto Dto = new VehicleDto();

		if(vehicleDto != null){
		Dto.setVid(vehicleDto.getVid());
		Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
		Dto.setVehicle_photoid(vehicleDto.getVehicle_photoid());
		Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
		Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
		Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
		Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
		Dto.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName(vehicleDto.getVehicleOwnerShipId()));
		Dto.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
		Dto.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		Dto.setVehicle_ExpectedMileage(vehicleDto.getVehicle_ExpectedMileage());
		Dto.setVehicle_ExpectedMileage_to(vehicleDto.getVehicle_ExpectedMileage_to());
		Dto.setVehicle_ExpectedOdameter(vehicleDto.getVehicle_ExpectedOdameter());
		}
		return Dto;
	}

	/**
	 * Vehicle Show Details in Header Fuel Extries Value Option
	 * @throws Exception 
	 */
	public VehicleDto prepare_Vehicle_Header_Fuel_ADD_Entries_Show(VehicleDto vehicleDto) throws Exception {
		VehicleDto Dto = new VehicleDto();

		Dto.setVid(vehicleDto.getVid());
		Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
		Dto.setVehicle_photoid(vehicleDto.getVehicle_photoid());
		Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
		Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
		Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());
		Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
		Dto.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName(vehicleDto.getVehicleOwnerShipId()));
		Dto.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
		Dto.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		Dto.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName(vehicleDto.getVehicleFuelId()));
		Dto.setVehicleGroupId(vehicleDto.getVehicleGroupId());
		Dto.setVehicleFuelId(vehicleDto.getVehicleFuelId());
		Dto.setVehicle_ExpectedOdameter(vehicleDto.getVehicle_ExpectedOdameter());
		Dto.setVehicleGPSId(vehicleDto.getVehicleGPSId());

		return Dto;
	}

	/* list of vehicle */
	public List<VehicleDto> prepareListofVehicleName(List<Vehicle> vehicles) {
		List<VehicleDto> Dtos = null;
		if (vehicles != null && !vehicles.isEmpty()) {
			Dtos = new ArrayList<VehicleDto>();
			VehicleDto Dto = null;
			for (Vehicle vehicleB : vehicles) {
				Dto = new VehicleDto();

				Dto.setVid(vehicleB.getVid());
				Dto.setVehicle_registration(vehicleB.getVehicle_registration());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	/* Edit the Vehicle 
	public VehicleDto prepareVehicleDto(Vehicle vehicleDto) throws Exception {
		VehicleDto Dto = new VehicleDto();

		Dto.setVid(vehicleDto.getVid());
		Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
		Dto.setVehicle_chasis(vehicleDto.getVehicle_chasis());
		Dto.setVehicle_engine(vehicleDto.getVehicle_engine());
		//Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
		Dto.setVehicle_year(vehicleDto.getVehicle_year());
		Dto.setVehicle_maker(vehicleDto.getVehicle_maker());
		Dto.setVehicle_Model(vehicleDto.getVehicle_Model());
		Dto.setVehicle_registrationState(vehicleDto.getVehicle_registrationState());
		Dto.setVehicle_RegisterDate(vehicleDto.getVehicle_RegisterDate());
		Dto.setVehicle_Registeredupto(vehicleDto.getVehicle_Registeredupto());
		//Dto.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		//Dto.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());
		Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
		Dto.setVehicle_Color(vehicleDto.getVehicle_Color());
		Dto.setVehicle_Class(vehicleDto.getVehicle_Class());
		Dto.setVehicle_body(vehicleDto.getVehicle_body());
		//Dto.setVehicle_actype(vehicleDto.getVehicle_actype());
		Dto.setVehicle_Cylinders(vehicleDto.getVehicle_Cylinders());
		Dto.setVehicle_CubicCapacity(vehicleDto.getVehicle_CubicCapacity());
		Dto.setVehicle_Power(vehicleDto.getVehicle_Power());
		Dto.setVehicle_wheelBase(vehicleDto.getVehicle_wheelBase());
		Dto.setVehicle_SeatCapacity(vehicleDto.getVehicle_SeatCapacity());
		Dto.setVehicle_UnladenWeight(vehicleDto.getVehicle_UnladenWeight());
		Dto.setVehicle_LadenWeight(vehicleDto.getVehicle_LadenWeight());
		//Dto.setVehicle_Fuel(vehicleDto.getVehicle_Fuel());
		Dto.setVehicle_FuelTank1(vehicleDto.getVehicle_FuelTank1());
		Dto.setVehicle_Oil(vehicleDto.getVehicle_Oil());
		//Dto.setVehicle_MeterUnit(vehicleDto.getVehicle_MeterUnit());
		Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());

		Dto.setVehicle_ExpectedMileage(vehicleDto.getVehicle_ExpectedMileage());
		Dto.setVehicle_ExpectedMileage_to(vehicleDto.getVehicle_ExpectedMileage_to());

		//Dto.setVehicle_FuelUnit(vehicleDto.getVehicle_FuelUnit());
		
		Dto.setvStatusId(vehicleDto.getvStatusId());
		Dto.setVehicleGroupId(vehicleDto.getVehicleGroupId());
		Dto.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
		//Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
		// Create and Last updated Display
		Dto.setCreatedBy(vehicleDto.getCreatedBy());
		if (vehicleDto.getCreated() != null) {
			Dto.setCreated(CreatedDateTime.format(vehicleDto.getCreated()));
		}
		Dto.setLastModifiedBy(vehicleDto.getLastModifiedBy());
		if (vehicleDto.getLastupdated() != null) {
			Dto.setLastupdated(CreatedDateTime.format(vehicleDto.getLastupdated()));
		}
		Dto.setVehicle_photoid(vehicleDto.getVehicle_photoid());

		Dto.setTripSheetID(vehicleDto.getTripSheetID());
		return Dto;
	}
*/	
	public VehicleDto prepareVehicleDto(VehicleDto vehicleDto) throws Exception {
		VehicleDto Dto = new VehicleDto();

		Dto.setVid(vehicleDto.getVid());
		Dto.setVehicle_registration(vehicleDto.getVehicle_registration());
		Dto.setVehicle_chasis(vehicleDto.getVehicle_chasis());
		Dto.setVehicle_engine(vehicleDto.getVehicle_engine());
		Dto.setVehicle_Type(vehicleDto.getVehicle_Type());
		Dto.setVehicle_year(vehicleDto.getVehicle_year());
		Dto.setVehicle_maker(vehicleDto.getVehicle_maker());
		Dto.setVehicle_Model(vehicleDto.getVehicle_Model());
		Dto.setVehicle_registrationState(vehicleDto.getVehicle_registrationState());
		Dto.setVehicle_RegisterDate(vehicleDto.getVehicle_RegisterDate());
		Dto.setVehicle_Registeredupto(vehicleDto.getVehicle_Registeredupto());
		Dto.setVehicle_RouteName(vehicleDto.getVehicle_RouteName());
		Dto.setVehicle_Ownership(vehicleDto.getVehicle_Ownership());
		Dto.setVehicle_Location(vehicleDto.getVehicle_Location());
		Dto.setVehicle_Color(vehicleDto.getVehicle_Color());
		Dto.setVehicle_Class(vehicleDto.getVehicle_Class());
		Dto.setVehicle_body(vehicleDto.getVehicle_body());
		Dto.setVehicle_actype(vehicleDto.getVehicle_actype());
		Dto.setVehicle_Cylinders(vehicleDto.getVehicle_Cylinders());
		Dto.setVehicle_CubicCapacity(vehicleDto.getVehicle_CubicCapacity());
		Dto.setVehicle_Power(vehicleDto.getVehicle_Power());
		Dto.setVehicle_wheelBase(vehicleDto.getVehicle_wheelBase());
		Dto.setVehicle_SeatCapacity(vehicleDto.getVehicle_SeatCapacity());
		Dto.setVehicle_UnladenWeight(vehicleDto.getVehicle_UnladenWeight());
		Dto.setVehicle_LadenWeight(vehicleDto.getVehicle_LadenWeight());
		Dto.setVehicle_Fuel(vehicleDto.getVehicle_Fuel());
		Dto.setVehicle_FuelTank1(vehicleDto.getVehicle_FuelTank1());
		Dto.setVehicle_Oil(vehicleDto.getVehicle_Oil());
		//Dto.setVehicle_MeterUnit(vehicleDto.getVehicle_MeterUnit());
		Dto.setVehicle_Odometer(vehicleDto.getVehicle_Odometer());

		Dto.setVehicle_ExpectedMileage(vehicleDto.getVehicle_ExpectedMileage());
		Dto.setVehicle_ExpectedMileage_to(vehicleDto.getVehicle_ExpectedMileage_to());

		Dto.setVehicle_FuelUnit(vehicleDto.getVehicle_FuelUnit());
		
		Dto.setvStatusId(vehicleDto.getvStatusId());
		Dto.setVehicleGroupId(vehicleDto.getVehicleGroupId());
		Dto.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(vehicleDto.getvStatusId()));
		Dto.setVehicle_Group(vehicleDto.getVehicle_Group());
		// Create and Last updated Display
		Dto.setCreatedBy(vehicleDto.getCreatedBy());
		if (vehicleDto.getCreatedOn() != null) {
			Dto.setCreated(CreatedDateTime.format(vehicleDto.getCreatedOn()));
		}
		Dto.setLastModifiedBy(vehicleDto.getLastModifiedBy());
		if (vehicleDto.getLastupdatedOn() != null) {
			Dto.setLastupdated(CreatedDateTime.format(vehicleDto.getLastupdatedOn()));
		}
		Dto.setVehicle_photoid(vehicleDto.getVehicle_photoid());
		Dto.setTripSheetID(vehicleDto.getTripSheetID());
		
		Dto.setVehicle_actype(VehicleAcType.getVehicleAcTypeName(vehicleDto.getAcTypeId()));
		Dto.setVehicleFuelId(vehicleDto.getVehicleFuelId());
		
		if(!vehicleDto.getVehicleFuelId().isEmpty() &&  vehicleDto.getVehicleFuelId() != "" && vehicleDto.getVehicleFuelId() != null) {
			Dto.setVehicle_Fuel(VehicleFuelType.getVehicleFuelTypeName(vehicleDto.getVehicleFuelId()));
		}
		
		Dto.setVehicle_MeterUnit(VehicleMeterUnit.getVehicleMeterUnitName(vehicleDto.getVehicleMeterUnitId()));
		Dto.setVehicle_Ownership(VehicleOwnerShip.getVehicleMeterUnitName(vehicleDto.getVehicleOwnerShipId()));
		Dto.setVehicle_FuelUnit(VehicleFuelUnit.getVehicleFuelUnitName(vehicleDto.getVehicleFuelUnitId()));
		Dto.setVehicleTypeId(vehicleDto.getVehicleTypeId());
		Dto.setAcTypeId(vehicleDto.getAcTypeId());
		Dto.setVehicleMeterUnitId(vehicleDto.getVehicleMeterUnitId());
		Dto.setVehicleOwnerShipId(vehicleDto.getVehicleOwnerShipId());
		Dto.setVehicleFuelUnitId(vehicleDto.getVehicleFuelUnitId());
		Dto.setRouteID(vehicleDto.getRouteID());
		Dto.setVehicle_ExpectedOdameter(vehicleDto.getVehicle_ExpectedOdameter());
		Dto.setVehicleGPSId(vehicleDto.getVehicleGPSId());
		if(vehicleDto.getDriverMonthlySalary()!= null) {
		Dto.setDriverMonthlySalary(vehicleDto.getDriverMonthlySalary());
		}
		Dto.setMobileNumber(vehicleDto.getMobileNumber());
		Dto.setLedgerName(vehicleDto.getLedgerName());
		Dto.setGpsVendorId(vehicleDto.getGpsVendorId());
		Dto.setGpsVendorName(vehicleDto.getGpsVendorName());
		Dto.setServiceProgramId(vehicleDto.getServiceProgramId());
		Dto.setBranchId(vehicleDto.getBranchId());
		Dto.setVehicleModelId(vehicleDto.getVehicleModelId());
		Dto.setVehicleMakerId(vehicleDto.getVehicleMakerId());
		Dto.setVehicleBodyMakerId(vehicleDto.getVehicleBodyMakerId());
		Dto.setBodyMakerName(vehicleDto.getBodyMakerName());
		Dto.setVehicleTollId(vehicleDto.getVehicleTollId());
		Dto.setSubCompanyName(vehicleDto.getSubCompanyName());
		Dto.setSubCompanyId(vehicleDto.getSubCompanyId());
		return Dto;
	}

	public List<VehicleCommentDto> getVehicleCommentList(List<VehicleCommentDto> list) throws Exception{
		List<VehicleCommentDto>	 vehicleCommentList		= null;
		try {
			vehicleCommentList	= new ArrayList<>();
			if(list != null) {
				for(VehicleCommentDto vehicleCommentDto : list) {
					if(vehicleCommentDto.getCREATEDBY() != null) {
						vehicleCommentDto.setCREATEDBY(vehicleCommentDto.getCREATEDBY());
					}else {
						vehicleCommentDto.setCREATEDBY("--");
					}
						
					vehicleCommentDto.setCREATED_DATE(CreatedDateTime.format(vehicleCommentDto.getCREATED_DATE_ON()));
					vehicleCommentList.add(vehicleCommentDto);
				}
			}
			return vehicleCommentList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Vehicle prepareVehicleDetails(ValueObject valueObject,CustomUserDetails	userDetails) throws Exception {
		Vehicle 			vehicle 		= null;
		Date 				currentDate 	= null;
		Timestamp 			timestamp 		= null;	
		try {
			System.err.println("inside prepareObject Bl");
			currentDate 	= new Date();
			timestamp		= new java.sql.Timestamp(currentDate.getTime());
			vehicle 		= new Vehicle();
			vehicle.setVid(valueObject.getInt("vid"));
			vehicle.setVehicle_registration(valueObject.getString("vehicleRegistrationNumber"));
			vehicle.setVehicle_chasis(valueObject.getString("vehicleChasisNumber"));
			vehicle.setVehicle_engine(valueObject.getString("vehicleEngineNumber"));
			vehicle.setVehicleTypeId(valueObject.getLong("VehicleTypeId", 0));
			vehicle.setVehicle_year(valueObject.getInt("vehicleYear", 0));
			vehicle.setVehicleMakerId(valueObject.getLong("vehicleMaker",0));
			vehicle.setVehicleModalId(valueObject.getLong("vehicleModel",0));
			vehicle.setVehicle_registrationState(valueObject.getString("registrationState"));
			vehicle.setVehicle_RegisterDate(valueObject.getString("vehicleRegisterDate"));
			vehicle.setVehicle_Registeredupto(valueObject.getString("vehicle_Registeredupto"));
			vehicle.setvStatusId(valueObject.getShort("vehicleStatusId", (short)0));
			vehicle.setVehicleGroupId(valueObject.getLong("vehicleGroupId", 0));
			vehicle.setRouteID(valueObject.getInt("vehicleRouteId", 0));
			vehicle.setVehicleOwnerShipId(valueObject.getShort("ownership", (short)0));
			vehicle.setVehicle_Location(valueObject.getString("vehicleLocation",""));
			vehicle.setVehicle_Color(valueObject.getString("vehicleColor"));
			vehicle.setVehicle_Class(valueObject.getString("vehicleClass"));
			vehicle.setVehicle_body(valueObject.getString("vehicleBody"));
			vehicle.setAcTypeId(valueObject.getShort("acTypeId", (short)0));
			vehicle.setVehicle_Cylinders(valueObject.getString("vehicleCylinders"));
			vehicle.setVehicle_CubicCapacity(valueObject.getString("vehicleCubicCapacity"));
			vehicle.setVehicle_Power(valueObject.getString("vehiclePower"));
			vehicle.setVehicle_wheelBase(valueObject.getString("vehicleWheelBase"));
			vehicle.setVehicle_SeatCapacity(valueObject.getString("vehicleSeatCapacity"));
			vehicle.setVehicle_UnladenWeight(valueObject.getString("vehicleUnladenWeight"));
			vehicle.setVehicle_LadenWeight(valueObject.getString("vehicleLadenWeight"));
			vehicle.setVehicleFuelId(valueObject.getString("vehicleFuel"));
			vehicle.setVehicle_FuelTank1(valueObject.getInt("vehicleFuelTank", 0));
			vehicle.setVehicle_Oil(valueObject.getInt("vehicleOilCapacity", 0));
			vehicle.setVehicleMeterUnitId(valueObject.getShort("vehicleMeterUnitId", (short)0));
			vehicle.setVehicle_Odometer(valueObject.getInt("vehicleOdometer", 0));
			vehicle.setVehicleFuelUnitId(valueObject.getShort("vehicleFuelUnitId",  (short)0));
			vehicle.setVehicle_ExpectedMileage(valueObject.getDouble("expectedMileageFrom", 0));
			vehicle.setVehicle_ExpectedMileage_to(valueObject.getDouble("expectedMileageTo", 0));
			vehicle.setVehicle_ExpectedOdameter(valueObject.getInt("maximumOdometer", 0));
			vehicle.setVehicle_photoid(valueObject.getInt("photoId", 0));
			vehicle.setTripSheetID((long) 0);
			vehicle.setVehicleGPSId(valueObject.getString("vehicleGPSId"));
			vehicle.setGpsVendorId(valueObject.getInt("gpsVendorId", 0));
			vehicle.setLedgerName(valueObject.getString("ledgerName"));
			vehicle.setMobileNumber(valueObject.getString("mobileNumber"));
			vehicle.setBranchId(valueObject.getInt("branchId", 0));
			vehicle.setCreatedById(userDetails.getId());
			vehicle.setLastModifiedById(userDetails.getId());
			vehicle.setCreated(timestamp);
			vehicle.setLastupdated(timestamp);
			vehicle.setMarkForDelete(false);
			vehicle.setCompany_Id(userDetails.getCompany_id());
			vehicle.setServiceProgramId(valueObject.getLong("serviceProgramId",0));
			vehicle.setVehicleBodyMakerId(valueObject.getInt("vehiclebodyMaker",0));
			vehicle.setVehicleTollId(valueObject.getString("vehicleTollId"));
			vehicle.setSubCompanyId(valueObject.getInt("subCompanyId",0));
			return vehicle;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	public VehicleHistory prepareVehicleHistory(VehicleDto oldVehicle,ValueObject valueObject,CustomUserDetails	userDetails) throws Exception {
		VehicleHistory 			vehicle 		= null;
		Date 				currentDate 	= null;
		Timestamp 			timestamp 		= null;	
		try {
			currentDate 	= new Date();
			timestamp		= new java.sql.Timestamp(currentDate.getTime());
			vehicle 		= new VehicleHistory();
			vehicle.setVid(oldVehicle.getVid());
			vehicle.setVehicle_registration(oldVehicle.getVehicle_registration());
			vehicle.setVehicle_chasis(oldVehicle.getVehicle_chasis());
			vehicle.setVehicle_engine(oldVehicle.getVehicle_engine());
			vehicle.setVehicleTypeId(oldVehicle. getVehicleTypeId());
			vehicle.setVehicle_year( oldVehicle. getVehicle_year());
			vehicle.setVehicle_maker(oldVehicle. getVehicle_maker());
			vehicle.setVehicle_Model(oldVehicle. getVehicle_Model());
			vehicle.setVehicle_registrationState( oldVehicle. getVehicle_registrationState());
			vehicle.setVehicle_RegisterDate(oldVehicle. getVehicle_RegisterDate());
			vehicle.setVehicle_Registeredupto(oldVehicle. getVehicle_Registeredupto());
			vehicle.setvStatusId(oldVehicle. getvStatusId());
			vehicle.setVehicleGroupId(oldVehicle. getVehicleGroupId());
			vehicle.setOldRouteId(valueObject.getInt("oldvehicleRouteId",0));
			vehicle.setNewRouteId(valueObject.getInt("vehicleRouteId", 0));
			vehicle.setVehicleOwnerShipId(oldVehicle. getVehicleOwnerShipId());
			vehicle.setVehicle_Location(oldVehicle. getVehicle_Location());
			vehicle.setVehicle_Color(oldVehicle. getVehicle_Color());
			vehicle.setVehicle_Class(oldVehicle. getVehicle_Class());
			vehicle.setVehicle_body(oldVehicle. getVehicle_body());
			vehicle.setAcTypeId(oldVehicle. getAcTypeId());
			vehicle.setVehicle_Cylinders(oldVehicle. getVehicle_Cylinders());
			vehicle.setVehicle_CubicCapacity(oldVehicle. getVehicle_CubicCapacity());
			vehicle.setVehicle_Power(oldVehicle. getVehicle_Power());
			vehicle.setVehicle_wheelBase(oldVehicle. getVehicle_wheelBase());
			vehicle.setVehicle_SeatCapacity(oldVehicle. getVehicle_SeatCapacity());
			vehicle.setVehicle_UnladenWeight(oldVehicle. getVehicle_UnladenWeight());
			vehicle.setVehicle_LadenWeight(oldVehicle. getVehicle_LadenWeight());
			vehicle.setVehicleFuelId(oldVehicle. getVehicleFuelId());
			vehicle.setVehicle_FuelTank1(oldVehicle. getVehicle_FuelTank1());
			vehicle.setVehicle_Oil(oldVehicle. getVehicle_Oil());
			vehicle.setVehicleMeterUnitId(oldVehicle. getVehicleMeterUnitId());
			vehicle.setVehicle_Odometer(oldVehicle. getVehicle_Odometer());
			vehicle.setVehicleFuelUnitId(oldVehicle. getVehicleFuelUnitId());
			vehicle.setVehicle_ExpectedMileage(oldVehicle. getVehicle_ExpectedMileage());
			vehicle.setVehicle_ExpectedMileage_to(oldVehicle. getVehicle_ExpectedMileage_to());
			vehicle.setVehicle_ExpectedOdameter(oldVehicle. getVehicle_ExpectedOdameter());
			vehicle.setVehicle_photoid(oldVehicle. getVehicle_photoid());
			vehicle.setTripSheetID(oldVehicle. getTripSheetID());
			vehicle.setVehicleGPSId(oldVehicle. getVehicleGPSId());
			vehicle.setGpsVendorId(oldVehicle. getGpsVendorId());
			vehicle.setLedgerName(oldVehicle. getLedgerName());
			vehicle.setMobileNumber(oldVehicle. getMobileNumber());
			vehicle.setCreatedById(oldVehicle.getCreatedById());
			vehicle.setLastModifiedById(oldVehicle.getModifiedById());
			vehicle.setCreated(oldVehicle.getCreatedOn());
			vehicle.setLastupdated(oldVehicle. getLastupdatedOn());
			vehicle.setMarkForDelete(false);
			vehicle.setCompany_Id(userDetails.getCompany_id());
			vehicle.setServiceProgramId(oldVehicle. getServiceProgramId());
			vehicle.setRouteChanged(valueObject.getBoolean("routeChanged", false));
			vehicle.setvHistorycreatedById(userDetails.getId());
			vehicle.setvHistorycreated(timestamp);
			vehicle.setVehicleBodyMakerId(oldVehicle.getVehicleBodyMakerId());
			
			return vehicle;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	
	
}