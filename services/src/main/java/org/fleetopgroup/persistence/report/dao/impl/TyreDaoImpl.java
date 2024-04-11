package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.InventoryDamageAndLostHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.report.dao.ITyreDaoImpl;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TyreDaoImpl implements ITyreDaoImpl {
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@PersistenceContext	EntityManager entityManager;

	
	@Transactional
	public List<InventoryTyreRetreadAmountDto> TyreRetreadInvoiceReportList(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					" SELECT ITR.TRID, ITRA.TR_AMOUNT_ID, ITR.TRNUMBER, ITR.TR_INVOICE_NUMBER, ITR.TR_INVOICE_DATE, "
							+ " ITR.TR_AMOUNT, ITR.TR_VENDOR_ID, V.vendorName, ITRA.TYRE_NUMBER, ITRA.RETREAD_COST, "
							+ " ITRA.RETREAD_DISCOUNT, ITRA.RETREAD_TAX, ITRA.RETREAD_AMOUNT, ITRA.TRA_STATUS_ID, ITRA.TYRE_ID, "
							+ " VTS.TYRE_SIZE, VTMST.TYRE_MODEL_SUBTYPE, PL.partlocation_name "
							+ " FROM InventoryTyreRetreadAmount ITRA "
							+ " INNER JOIN InventoryTyreRetread ITR on ITR.TRID = ITRA.inventoryTyreRetread.TRID "
							+ " INNER JOIN  VehicleTyreSize VTS on VTS.TS_ID = ITRA.TYRE_SIZE_ID "
							+ " INNER JOIN InventoryTyre IT on IT.TYRE_ID = ITRA.TYRE_ID "
							+ " INNER JOIN VehicleTyreModelSubType VTMST on VTMST.TYRE_MST_ID = IT.TYRE_MODEL_ID "
							+ " INNER JOIN PartLocations PL on PL.partlocation_id = IT.WAREHOUSE_LOCATION_ID "
							+ " LEFT JOIN Vendor V on V.vendorId = ITR.TR_VENDOR_ID "
							+ " WHERE ITR.COMPANY_ID = "+companyId+" AND ITR.TR_STATUS_ID = "+InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_COMPLETED+" "
							+ " AND ITRA.markForDelete = 0 AND IT.markForDelete = 0 AND ITR.markForDelete = 0 " + Query + " "
							+ " ORDER BY ITR.TRID DESC ",
					Object[].class);
			//queryt.setParameter("comID", companyId);
			List<Object[]> results = queryt.getResultList();

			List<InventoryTyreRetreadAmountDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreRetreadAmountDto>();
				InventoryTyreRetreadAmountDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryTyreRetreadAmountDto();

					Dto.setTRID((long) result[0]);
					Dto.setTR_AMOUNT_ID((long) result[1]);
					Dto.setTRNUMBER((long) result[2]);
					if(result[3] != null)
					Dto.setTR_INVOICE_NUMBER((String) result[3]);
					if(result[4] != null)
					Dto.setTR_INVOICE_DATE_STR(sqldateTime.format((Date) result[4]));
					Dto.setTR_AMOUNT((double) result[5]);
					Dto.setTR_VENDOR_ID((int) result[6]);
					Dto.setVENDOR_NAME((String) result[7]);
					if(result[8] != null)
					Dto.setTYRE_NUMBER((String) result[8]);
					if(result[9] != null)
					Dto.setRETREAD_COST((double) result[9]);
					if(result[10] != null)
					Dto.setRETREAD_DISCOUNT((double) result[10]);
					if(result[11] != null)
					Dto.setRETREAD_TAX((double) result[11]);
					if(result[12] != null)
					Dto.setRETREAD_AMOUNT((double) result[12]);
					if(result[13] != null) {
						Dto.setTRA_STATUS_ID((short) result[13]);
						Dto.setTRA_STATUS(InventoryTyreRetreadAmountDto.getTraStatusName((short) result[13]));
					}
					Dto.setTYRE_ID((long) result[14]);
					Dto.setTYRE_SIZE((String) result[15]);
					Dto.setTYRE_MODEL_SUBTYPE((String) result[16]);
					Dto.setLOCATION((String) result[17]);

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
	
	public List<InventoryTyreHistoryDto> getVehicleTyreAssignmentHistoryReportList(String Query, Integer companyId) throws Exception{
		
		TypedQuery<Object[]> queryt = null;
		NumberFormat	formatter   = new DecimalFormat("0.00");
		double costPerKM			= 0.0;
		try {
			
			queryt = entityManager.createQuery(
					" SELECT ITH.TYRE_HIS_ID, ITH.TYRE_NUMBER ,ITH.TYRE_STATUS_ID , ITH.TYRE_ASSIGN_DATE , "
					+ " IT.TYRE_AMOUNT, ITH.TYRE_USEAGE, IT.TYRE_ASSIGN_STATUS_ID, V.vehicle_Odometer,"
					+ " IT.OPEN_ODOMETER,PL.partlocation_name,IT.TYRE_ASSIGN_DATE, ITH.AXLE, ITH.POSITION "
					+ " FROM InventoryTyreHistory AS ITH "
					+ " INNER JOIN InventoryTyre AS IT ON IT.TYRE_ID = ITH.TYRE_ID "
					+ " INNER JOIN Vehicle V ON V.vid = IT.VEHICLE_ID "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.WAREHOUSE_LOCATION_ID"
					+ " WHERE " +Query+" AND ITH.COMPANY_ID = "+companyId+" AND ITH.markForDelete = 0  ORDER BY ITH.TYRE_HIS_ID DESC ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<InventoryTyreHistoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreHistoryDto>();
				
				InventoryTyreHistoryDto Dto = null;
				
				for (Object[] result : results) {
					Dto = new InventoryTyreHistoryDto();
						if(result != null) {
								
							if(result[0] != null)
							Dto.setTYRE_HIS_ID((Long) result[0]);
							
							if(result[1] != null)
							Dto.setTYRE_NUMBER((String) result[1]);
							
							Dto.setTYRE_STATUS_ID((short) result[2]);
							
							if(Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT || Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION) {
								if(result[3] != null)
								Dto.setTYRE_MOUNT_DATE(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
							}
							
							if(Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT) {
								if(result[3] != null)
								Dto.setTYRE_DIS_MOUNT_DATE(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
							}
							if(result[4] != null) {
								if(Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT || Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION) {
									Dto.setTYRE_COST((Double) result[4]);
								}else if(Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT){
									Dto.setTYRE_COST(0.0);
								}
							}
							
							if(result[5] != null)
							Dto.setTYRE_USEAGE((Integer) result[5]);
							
							if(result[6] != null)
							Dto.setTYRE_ASSIGN_STATUS_ID((short) result[6]);
							
							if(result[7] != null)
							Dto.setVEHICLE_ODOMETER((Integer) result[7]);
							
							if(result[8] != null)
							Dto.setOPEN_ODOMETER((Integer) result[8]);
							
							if(result[9] != null)
							Dto.setWAREHOUSE_LOCATION((String) result[9]);
							
							if((Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT) || (Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION)) {
								Dto.setTYRE_USEAGE(Dto.getTYRE_USEAGE());
							}
							
							if(Dto.getTYRE_STATUS_ID()==InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT) {
								Dto.setTYRE_USEAGE(Dto.getTYRE_USEAGE());
							}
						
							if(Dto.getTYRE_ASSIGN_STATUS_ID()==InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
								if(Dto.getTYRE_USEAGE() != null && Dto.getVEHICLE_ODOMETER() != null && Dto.getOPEN_ODOMETER() != null)
									Dto.setTYRE_USEAGE(Dto.getTYRE_USEAGE() + (Dto.getVEHICLE_ODOMETER() - Dto.getOPEN_ODOMETER()));
							}
							
							if((Double) result[4] > 0) {
								costPerKM	= Dto.getTYRE_USEAGE()/((Double) result[4]);
							
								costPerKM	= Double.parseDouble(formatter.format(costPerKM));
							
								Dto.setCOST_PER_KM(costPerKM);
							}
							
							if(result[11] != null)
								Dto.setAXLE((String) result[11]);
							if(result[12] != null)
								Dto.setPOSITION((String) result[12]);
							
					}
					Dtos.add(Dto);
				}
			}
			return Dtos;
		}
		catch(Exception e) {
			LOGGER.error("Exception",e);
			throw e;
		}
		finally {
			queryt = null;
		}
	}
	
}	