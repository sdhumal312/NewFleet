package org.fleetopgroup.persistence.bl;

import java.util.Date;

import org.fleetopgroup.persistence.dto.BatteryManufacturerDto;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BatteryCapacity;
import org.fleetopgroup.persistence.model.BatteryManufacturer;
import org.fleetopgroup.persistence.model.BatteryType;
import org.springframework.security.core.context.SecurityContextHolder;

public class BatteryManufacturerBL {

	public BatteryManufacturer getBatteryManufacturerDto(BatteryManufacturerDto	batteryManufacturerDto) {
		BatteryManufacturer		batteryManufacturer		= null;
		CustomUserDetails		customUserDetails		= null;
		try {
			batteryManufacturer	= new BatteryManufacturer();
			customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			batteryManufacturer.setBatteryManufacturerId(batteryManufacturerDto.getBatteryManufacturerId());
			batteryManufacturer.setManufacturerName(batteryManufacturerDto.getManufacturerName());
			batteryManufacturer.setDescription(batteryManufacturerDto.getDescription());
			batteryManufacturer.setCreatedBy(customUserDetails.getId());
			batteryManufacturer.setLastModifiedBy(customUserDetails.getId());
			batteryManufacturer.setCreationOn(new Date());
			batteryManufacturer.setLastModifiedOn(new Date());
			
			return batteryManufacturer;
		} catch (Exception e) {
			throw e;
		}finally {
			customUserDetails		= null;
		}
	}
	
	public BatteryType getBatteryTypeDto(BatteryTypeDto	batteryTypeDto) {
		BatteryType				batteryType				= null;
		CustomUserDetails		customUserDetails		= null;
		try {
			batteryType	= new BatteryType();
			customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			batteryType.setBatteryTypeId(batteryTypeDto.getBatteryTypeId());
			batteryType.setBatteryType(batteryTypeDto.getBatteryType());
			batteryType.setBatteryManufacturerId(batteryTypeDto.getBatteryManufacturerId());
			batteryType.setDescription(batteryTypeDto.getDescription());
			if(batteryTypeDto.getPartNumber() == null) {
				batteryType.setPartNumber("");
			}else {
				batteryType.setPartNumber(batteryTypeDto.getPartNumber());
			}
			batteryType.setWarrantyPeriod(batteryTypeDto.getWarrantyPeriod());
			batteryType.setWarrantyTypeId(batteryTypeDto.getWarrantyTypeId());
			batteryType.setCreatedBy(customUserDetails.getId());
			batteryType.setLastModifiedBy(customUserDetails.getId());
			batteryType.setCreatedOn(new Date());
			batteryType.setLastModifiedOn(new Date());
			batteryType.setWarrentyterm(batteryTypeDto.getWarrentyterm());
			
			
			return batteryType;
		} catch (Exception e) {
			throw e;
		}finally {
			customUserDetails		= null;
		}
	}
	
	public BatteryCapacity	getBatteryCapacityDto(BatteryCapacity capacity) throws Exception {
		BatteryCapacity				batteryCapacity				= null;
		CustomUserDetails			customUserDetails		= null;
		try {
			batteryCapacity	= new BatteryCapacity();
			customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			batteryCapacity.setBatteryCapacity(capacity.getBatteryCapacity());
			batteryCapacity.setDescription(capacity.getDescription());
			batteryCapacity.setCreatedById(customUserDetails.getId());
			batteryCapacity.setLastModifiedById(customUserDetails.getId());
			batteryCapacity.setCreatedOn(new Date());
			batteryCapacity.setLastModifiedOn(new Date());
			
			return batteryCapacity;
		} catch (Exception e) {
			throw e;
		}finally {
			customUserDetails		= null;
		}
	} 
}
