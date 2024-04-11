package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.report.dao.IVehicleDao;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VehicleDaoImpl implements IVehicleDao {
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext	EntityManager entityManager;
	
	
	@Transactional
	public List<BatteryDto> getVehicleWiseBatteryReportList(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					" SELECT BT.batteryId, BM.manufacturerName, BT.batterySerialNumber, BT.openOdometer, BT.batteryAmount,  "
							+ " BT.batteryAsignedDate, V.vehicle_registration, V.vid, VBL.layoutPosition "
							+ " FROM Battery BT "
							+ " INNER JOIN Vehicle V on V.vid = BT.vid "
							+ " INNER JOIN BatteryManufacturer BM on BM.batteryManufacturerId = BT.batteryManufacturerId "
							+ " INNER JOIN VehicleBatteryLayout	VBL on VBL.batteryId =  BT.batteryId "
							+ " WHERE BT.companyId = "+companyId+" AND BT.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE+" "
							+ " AND BT.markForDelete = 0 " + Query + " ",
					Object[].class);
			//queryt.setParameter("comID", companyId);
			List<Object[]> results = queryt.getResultList();

			List<BatteryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<BatteryDto>();
				BatteryDto Dto = null;
				for (Object[] result : results) {
					Dto = new BatteryDto();

					Dto.setBatteryId((long) result[0]);
					Dto.setManufacturerName((String) result[1]);
					Dto.setBatterySerialNumber((String) result[2]);
					Dto.setOpenOdometer((int) result[3]);
					Dto.setBatteryAmount((double) result[4]);
					Dto.setAsignedDate(sqldateTime.format((Date) result[5]));
					Dto.setVehicle_registration((String) result[6]);
					Dto.setVid((int) result[7]);
					Dto.setLayoutPosition((short) result[8]);
					
					Dto.setPreviousBatteryManufacturer("-");
					Dto.setPreviousSerialNumber("-");
					Dto.setPreviousAssignDate("-");
					
					Dtos.add(Dto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
			queryt = null;
		}
	}
	
	@Transactional
	public BatteryDto getBatteryHistoryList(int vid, short layoutPosition, long batteryId, Integer companyId) throws Exception {
		try {
			
			Query queryt = entityManager.createQuery(
					" Select BH.batteryId, BM.manufacturerName, BH.batterySerialNumber, BH.vid, BH.layoutPosition,  "
							+ " BH.batteryAsignDate, BH.openOdometer  "
							+ " FROM BatteryHistory BH "
							+ " INNER JOIN Battery B on B.batteryId = BH.batteryId "
							+ " INNER JOIN BatteryManufacturer BM on BM.batteryManufacturerId = B.batteryManufacturerId "
							+ " WHERE BH.markForDelete = 0 AND BH.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_IN_MOUNT+" "
							+ "	AND BH.vid = "+vid+" AND BH.layoutPosition = "+layoutPosition+" "
							+ " AND BH.batteryId != "+batteryId+" AND BH.companyId = "+companyId+" ORDER BY BH.batteryHistoryId desc ");
			queryt.setMaxResults(1);
			
			
			Object[] result = null;
			BatteryDto select  = new BatteryDto();
			
			try {
				result = (Object[]) queryt.getSingleResult();
				
			} catch (NoResultException nre) {
				
				select.setPreviousBatteryManufacturer("-");
				select.setPreviousSerialNumber("-");
				select.setVid(vid);
				select.setLayoutPosition(layoutPosition);
				select.setPreviousAssignDate("-");
				select.setPreviousUsesOdometer((int) 0);
				
				return select;
			}
			
			if (result != null) {
				
				select.setBatteryId((long) result[0]);
				select.setPreviousBatteryManufacturer((String) result[1]);
				select.setPreviousSerialNumber((String) result[2]);
				select.setVid((int) result[3]);
				select.setLayoutPosition((short) result[4]);
				select.setPreviousAssignDate(sqldateTime.format((Timestamp) result[5]));
				select.setPreviousUsesOdometer((int) result[6]);
			}

			return select;

		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}	
	}
	
	@Transactional
	public List<InventoryTyreDto> getVehicleWiseTyreReportList(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					" SELECT IT.TYRE_ID, VM.TYRE_MODEL, IT.TYRE_NUMBER, IT.OPEN_ODOMETER, IT.TYRE_AMOUNT,  "
							+ " IT.TYRE_ASSIGN_DATE, V.vehicle_registration, V.vid, VTL.POSITION, VTL.AXLE, "
							+ " IT.TYRE_USEAGE, IT.TYRE_ASSIGN_STATUS_ID, V.vehicle_Odometer,VG.vGroup "
							+ " FROM InventoryTyre IT "
							+ " INNER JOIN Vehicle V on V.vid = IT.VEHICLE_ID "
							+ " LEFT JOIN VehicleGroup VG on VG.gid = V.vehicleGroupId "
							+ " INNER JOIN VehicleTyreModelType VM on VM.TYRE_MT_ID = IT.TYRE_MANUFACTURER_ID "
							+ " INNER JOIN VehicleTyreLayoutPosition VTL on VTL.TYRE_ID =  IT.TYRE_ID "
							+ " WHERE IT.COMPANY_ID = "+companyId+" AND IT.TYRE_ASSIGN_STATUS_ID = "+InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE+" "
							+ " AND IT.markForDelete = 0 " + Query + " ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<InventoryTyreDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
				InventoryTyreDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryTyreDto();
					
					Dto.setTYRE_ID((long) result[0]);
					Dto.setTYRE_MODEL((String) result[1]);
					Dto.setTYRE_NUMBER((String) result[2]);
					Dto.setOPEN_ODOMETER((int) result[3]);
					Dto.setTYRE_AMOUNT((double) result[4]);
					Dto.setTYRE_ASSIGN_DATE(sqldateTime.format((Date) result[5]));
					Dto.setVEHICLE_REGISTRATION((String) result[6]);
					Dto.setVEHICLE_ID((int) result[7]);
					Dto.setVehicleNumberStr("<a style=\"color:blue\" target=_blank href=\"showVehicle?vid="+Dto.getVEHICLE_ID()+"\">I-"+Dto.getVEHICLE_REGISTRATION()+" </a>");
					String position = (String) result[8];
					String [] positionarray = position.split("-");
					Dto.setPOSITION(positionarray[0]);
					Dto.setPositionStr((String)result[8]);
					Dto.setAXLE((int) result[9]);
					Dto.setTYRE_USEAGE((int) result[10]);
					Dto.setTYRE_ASSIGN_STATUS_ID((short) result[11]);
					Dto.setVEHICLE_ODOMETER((int) result[12]);
					
					if(Dto.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
						if(Dto.getTYRE_USEAGE() != null && Dto.getVEHICLE_ODOMETER() != null && Dto.getOPEN_ODOMETER() != null)
							Dto.setTYRE_USEAGE(Dto.getTYRE_USEAGE() + (Dto.getVEHICLE_ODOMETER() - Dto.getOPEN_ODOMETER()));
					}
					
					Dto.setPreviousTyreManufacturer("-");
					Dto.setPreviousSerialNumber("-");
					Dto.setPreviousAssignedDate("-");
					Dto.setVehicleGroup((String) result[13]);
					if(positionarray[1].equals("1")) {
						if(positionarray[0].equals("RO")) 
							Dto.setPositionT("Front Right");	
						if(positionarray[0].equals("LO")) 
							Dto.setPositionT("Front Left");
					}else if(positionarray[1].equals("0") && positionarray[0].equals("SP")){
						Dto.setPositionT("Bus Spare");	
					}else {
						Dto.setPositionT("Rear "+Dto.getPositionStr());	
					}
					Dtos.add(Dto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
			queryt = null;
		}
	}
	
	@Transactional
	public InventoryTyreDto getTyreHistoryList(int vid, String layoutPosition, long tyreId, String axle, Integer companyId) throws Exception {
		try {
			
			Query queryt = entityManager.createQuery(
					" Select IH.TYRE_ID, VM.TYRE_MODEL, IH.TYRE_NUMBER, IH.VEHICLE_ID, IH.POSITION, IH.AXLE, "
							+ " IH.TYRE_ASSIGN_DATE, IH.OPEN_ODOMETER "
							+ " FROM InventoryTyreHistory IH "
							+ " INNER JOIN InventoryTyre IT on IT.TYRE_ID = IH.TYRE_ID "
							+ " INNER JOIN VehicleTyreModelType VM on VM.TYRE_MT_ID = IT.TYRE_MANUFACTURER_ID "
							+ " WHERE IH.markForDelete = 0 AND IH.TYRE_STATUS_ID = "+InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT+" "
							+ " AND IH.VEHICLE_ID = "+vid+" AND IH.POSITION = '"+layoutPosition+"' "
							+ " AND IH.AXLE = "+axle+" AND IH.TYRE_ID != "+tyreId+" AND IH.COMPANY_ID ="+companyId+" ORDER BY IH.TYRE_HIS_ID desc ");
			queryt.setMaxResults(1);
			
			Object[] result = null;
			InventoryTyreDto select  = new InventoryTyreDto();
			
			try {
				result = (Object[]) queryt.getSingleResult();
				
			} catch (NoResultException nre) {
				
				select.setPreviousTyreManufacturer("-");
				select.setPreviousSerialNumber("-");
				select.setVEHICLE_ID(vid);
				select.setPOSITION(layoutPosition);
				select.setAXLE(Integer.parseInt(axle));
				select.setPreviousAssignedDate("-");
				select.setPreviousTyreOpenOdo((int) 0);
				select.setPreviousTyreUsage((int) 0);
				
				return select;
			}
			
			if (result != null) {
				
				select.setTYRE_ID((long) result[0]);
				select.setPreviousTyreManufacturer((String) result[1]);
				select.setPreviousSerialNumber((String) result[2]);
				select.setVEHICLE_ID((int) result[3]);
				select.setPOSITION((String) result[4]);
				select.setAXLE(Integer.parseInt((String) result[5]));
				select.setPreviousAssignedDate(sqldateTime.format((Date) result[6]));
				select.setPreviousTyreOpenOdo((int) result[7]);
				select.setPreviousTyreUsage(select.getPreviousTyreOpenOdo());
			}

			return select;

		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}	
	}
	
}	