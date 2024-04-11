package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.UpholsteryTransferStatus;
import org.fleetopgroup.persistence.dto.ClothInventoryDetailsDto;
import org.fleetopgroup.persistence.dto.ClothInventoryStockTypeDetailsDto;
import org.fleetopgroup.persistence.dto.ClothTypesDto;
import org.fleetopgroup.persistence.dto.InventoryDamageAndLostHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryUpholsteryTransferDto;
import org.fleetopgroup.persistence.dto.LaundryClothReceiveHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;
import org.fleetopgroup.persistence.report.dao.IUpholsteryReportDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UpholsteryReportDaoImpl implements IUpholsteryReportDao {
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	DecimalFormat toFixedTwo= new DecimalFormat("#.##");
	
	
	@PersistenceContext	EntityManager entityManager;

	
	@Transactional
	public List<ClothInventoryDetailsDto> Upholstery_Purchase_Invoice_Report_List(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					" SELECT CI.clothInvoiceId, CID.clothInventoryDetailsId, CI.clothInvoiceNumber, CI.invoiceNumber,  "
							+ " CI.invoiceDate, CI.invoiceAmount, CI.vendorId, V.vendorName, CID.clothTypesId, CT.clothTypeName, "
							+ " CID.quantity, CID.unitprice, CID.discount, CID.tax, CID.total, CID.discountTaxTypeId "
							+ " FROM ClothInventoryDetails CID "
							+ " INNER JOIN ClothInvoice CI on CI.clothInvoiceId = CID.clothInvoiceId "
							+ " INNER JOIN ClothTypes CT on CT.clothTypesId = CID.clothTypesId "
							+ " LEFT JOIN Vendor V on V.vendorId = CI.vendorId "
							+ " WHERE CI.companyId = "+companyId+" AND CID.markForDelete = 0 AND CI.markForDelete = 0 " + Query + " ",
					Object[].class);
			//queryt.setParameter("comID", companyId);
			List<Object[]> results = queryt.getResultList();

			List<ClothInventoryDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ClothInventoryDetailsDto>();
				ClothInventoryDetailsDto Dto = null;
				for (Object[] result : results) {
					Dto = new ClothInventoryDetailsDto();

					Dto.setClothInvoiceId((long) result[0]);
					Dto.setClothInventoryDetailsId((long) result[1]);
					if(result[2] != null)
					Dto.setClothInvoiceNumber((long) result[2]);
					if(result[3] != null)
					Dto.setInvoiceNumber((String) result[3]);
					Dto.setInvDate(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[4], DateTimeUtility.DD_MM_YY));
					if(result[5] != null)
					Dto.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[5])));
					Dto.setVendor_id((int) result[6]);
					Dto.setVendorName((String) result[7]);
					Dto.setClothTypesId((long) result[8]);
					Dto.setClothTypesName((String) result[9]);
					if(result[10] != null)
					Dto.setQuantity(Double.parseDouble(toFixedTwo.format(result[10])));
					if(result[11] != null)
					Dto.setUnitprice(Double.parseDouble(toFixedTwo.format(result[11])));
					if(result[12] != null)
					Dto.setDiscount(Double.parseDouble(toFixedTwo.format(result[12])));
					if(result[13] != null)
					Dto.setTax(Double.parseDouble(toFixedTwo.format(result[13])));
					if(result[14] != null)
					Dto.setTotal(Double.parseDouble(toFixedTwo.format(result[14])));
					Dto.setDiscountTaxTypeId((short) result[15]);

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
	
	
	//DAO Implementation for Laundry Upholstery receive report Start
	@Transactional
	public List<LaundryClothReceiveHistoryDto> getLaundry_Upholstery_Receive_Report(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
						
			queryt = entityManager.createQuery(
					" SELECT  LCR.clothTypesId, CT.clothTypeName, LCR.receiveDate, LCR.receiveById, LCR.receiveQuantity,  "
							+ " SLCD.clothEachCost, SLCD.clothDiscount, SLCD.clothGst, U.vendorId, V.vendorName, US.firstName "							
							+ " FROM LaundryClothReceiveHistory AS LCR "							
							+ " INNER JOIN ClothTypes AS CT ON CT.clothTypesId = LCR.clothTypesId "							
							+ " INNER JOIN SentLaundryClothDetails AS SLCD ON SLCD.laundryClothDetailsId = LCR.laundryClothDetailsId "
							+ " INNER JOIN UpholsterySendLaundryInvoice AS U ON U.laundryInvoiceId =  LCR.laundryInvoiceId  "								
							+ " LEFT JOIN Vendor  AS V  ON V.vendorId = U.vendorId "	
							+ " INNER JOIN User AS US ON US.id =  LCR.receiveById "
							+ " WHERE LCR.companyId = "+companyId+" AND LCR.markForDelete = 0 " + Query + " ",
					Object[].class);		
			
			List<Object[]> results = queryt.getResultList();

			List<LaundryClothReceiveHistoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<LaundryClothReceiveHistoryDto>();
				LaundryClothReceiveHistoryDto Dto = null;
				for (Object[] result : results) {
					Dto = new LaundryClothReceiveHistoryDto();
					
					
					Dto.setClothTypesId((long) result[0]);					
					if(result[1] != null)
					Dto.setClothTypesName((String) result[1]);					
					if(result[2] != null)					
					Dto.setReceiveDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[2], DateTimeUtility.DD_MM_YY));					
					Dto.setReceiveById((long) result[3]);					
					if(result[4] != null)
					Dto.setReceiveQuantity((Double) result[4]);					
					if(result[5] != null)
					Dto.setClothEachCost((Double) result[5]);					
					if(result[6] != null)
					Dto.setClothDiscount((Double) result[6]);					
					if(result[7] != null)
					Dto.setClothGst((Double) result[7]);					
					//if(result[8] != null)
					//Dto.setClothTotal((Double) result[8]);					
					Dto.setVendorId((Integer) result[8]);					
					if(result[9] != null)
					Dto.setVendorName((String) result[9]);					
					if(result[10] != null)
					Dto.setFirstName((String) result[10]);
					
					double initialtotal = Dto.getReceiveQuantity() * Dto.getClothEachCost();				
					double aftergst = initialtotal + (initialtotal * (Dto.getClothGst()/100));
					double afterdiscount = aftergst - (aftergst * (Dto.getClothDiscount()/100));
					double finaltotal = afterdiscount;
					Dto.setClothTotal((Double) finaltotal);	
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
	
	
	//DAO Implementation for Laundry Upholstery receive report Stop	
	
	//DAO Implementation Upholstery Stock Report Start
	
	public List<ClothInventoryStockTypeDetailsDto> getUpholstery_Stock_Report(String Query, Integer companyId) throws Exception{
		TypedQuery<Object[]> queryt =null;
		try {
			
			queryt = entityManager.createQuery(
					" SELECT  CS.clothTypesId, CT.clothTypeName, CS.wareHouseLocationId, PL.partlocation_name, "
							+ "  CS.newStockQuantity, CS.usedStockQuantity, CS.inServiceQuantity, CS.inWashingQuantity, CS.damagedQuantity, CS.losedQuantity "							
							+ " FROM ClothInventoryStockTypeDetails AS CS "								
							+ " INNER JOIN ClothTypes AS CT ON CT.clothTypesId = CS.clothTypesId "								
							+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = CS.wareHouseLocationId  "				
							+ " WHERE CS.companyId = "+companyId+" AND CS.markForDelete = 0 " + Query + " ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<ClothInventoryStockTypeDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ClothInventoryStockTypeDetailsDto>();
				ClothInventoryStockTypeDetailsDto Dto = null;
				for (Object[] result : results) {
					Dto = new ClothInventoryStockTypeDetailsDto();
					
					Dto.setClothTypesId((Long) result[0]);
					if(result[1] != null)
					Dto.setClothTypeName((String) result[1]);					
					Dto.setWareHouseLocationId((Integer) result[2]);
					if(result[3] != null)
					Dto.setLocationName((String) result[3]);
					if(result[4] != null)
					Dto.setNewStockQuantity((Double) result[4]);
					if(result[5] != null)
					Dto.setUsedStockQuantity((Double) result[5]);
					if(result[6] != null)
					Dto.setInServiceQuantity((Double) result[6]);
					if(result[7] != null)
					Dto.setInWashingQuantity((Double) result[7]);
					if(result[8] != null)
					Dto.setDamagedQuantity((Double) result[8]);
					if(result[9] != null)
					Dto.setLosedQuantity((Double) result[9]);
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
	
	//DAO Implementation Upholstery Stock Report Start
	
	public List<VehicleClothInventoryHistoryDto> getUpholstery_Assignment_Report(String Query, Integer companyId) throws Exception{
		TypedQuery<Object[]> queryt =null;
		try {
			queryt = entityManager.createQuery(
					" SELECT VI.vehicleClothInventoryHistoryId, VI.vid, V.vehicle_registration , VG.gid, VG.vGroup, VI.createdOn, VI.clothTypesId , "
							+ " C.clothTypeName, VI.quantity, VI.createdById, U.firstName , VI.locationId, P.partlocation_name, VI.PreAsignedDate "							
							+ " FROM VehicleClothInventoryHistory AS VI "								
							+ " INNER JOIN Vehicle as V ON V.vid = VI.vid "								
							+ " INNER JOIN VehicleGroup as VG ON VG.gid = V.vehicleGroupId  "				
							+ " INNER JOIN ClothTypes as C ON C.clothTypesId = VI.clothTypesId  "				
							+ " INNER JOIN User as U ON U.id = VI.createdById  "				
							+ " INNER JOIN PartLocations as P on P.partlocation_id = VI.locationId  "	
							+ " WHERE VI.companyId = "+companyId+" AND VI.markForDelete = 0 " + Query + " order by VI.vehicleClothInventoryHistoryId desc ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<VehicleClothInventoryHistoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleClothInventoryHistoryDto>();
				VehicleClothInventoryHistoryDto Dto = null;
				for (Object[] result : results) {
					Dto = new VehicleClothInventoryHistoryDto();
					if(result != null) {
						Dto.setVehicleClothInventoryHistoryId((Long) result[0]);
						Dto.setVid((Integer) result[1]);
						Dto.setVehicleRegistration((String) result[2]);
						Dto.setVehicleGroupId((Long) result[3]);
						Dto.setVehicleGroup((String) result[4]);
						Dto.setCreatedOnStr(sqldateTime.format((Timestamp) result[5]));
						Dto.setClothTypesId((Long) result[6]);
						Dto.setClothTypeName((String) result[7]);
						Dto.setQuantity((Double) result[8]);
						Dto.setCreatedById((Long) result[9]);
						Dto.setCreatedByName((String) result[10]);
						Dto.setLocationId((Integer) result[11]);
						Dto.setLocationName((String) result[12]);
						if(result[13] != null) {
						Dto.setPreAsignedDateStr(sqldateTime.format((Timestamp) result[13]));
						}
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
	//DAO Implementation for Upholstery Sent To Laundry Report Start
	public List<UpholsterySendLaundryInvoiceDto> getUpholstery_Sent_ToLaundryReport(String Query, Integer companyId) throws Exception{
		TypedQuery<Object[]> queryt =null;
		try {
			
			queryt = entityManager.createQuery(
					" SELECT LI.laundryInvoiceId, LI.wareHouseLocationId, PL.partlocation_name, "
						+ " LI.sentDate, SL.clothTypesId, CT.clothTypeName, LI.vendorId, "
						+ " SL.quantity, V.vendorName, SL.clothEachCost, SL.clothTotal, U.firstName , U.lastName "
						+ " FROM UpholsterySendLaundryInvoice AS LI "
						+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = LI.wareHouseLocationId "
						+ " INNER JOIN SentLaundryClothDetails AS SL ON SL.laundryInvoiceId = LI.laundryInvoiceId "
						+ " INNER JOIN ClothTypes AS CT ON CT.clothTypesId = SL.clothTypesId "
						+ " INNER JOIN User AS U ON U.id = LI.createdById "
						+ " LEFT JOIN Vendor AS V ON V.vendorId =  LI.vendorId "
						+ " WHERE LI.companyId = "+companyId+" AND LI.markForDelete = 0 AND SL.markForDelete = 0 "
						+ " "+Query+" ORDER BY LI.laundryInvoiceId DESC ",
							Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<UpholsterySendLaundryInvoiceDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UpholsterySendLaundryInvoiceDto>();
				UpholsterySendLaundryInvoiceDto Dto = null;
				for (Object[] result : results) {
					Dto = new UpholsterySendLaundryInvoiceDto();
						if(result != null) {
								
								Dto.setLaundryInvoiceId((Long) result[0]);
								
								Dto.setWareHouseLocationId((Integer) result[1]);
								
								if(result[2] != null)
								Dto.setPartlocation_name((String) result[2]);
								
								if(result[3] != null)
								Dto.setSentDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
								
								Dto.setClothTypesId((Long) result[4]);
								
								if(result[5] != null)
								Dto.setClothName((String) result[5]);
								
								Dto.setVendorId((Integer) result[6]);
								
								if(result[7] != null)
								Dto.setQuantity((Double) result[7]);
								
								if(result[8] != null)
								Dto.setVendorName((String) result[8]);
								
								if(result[9] != null)
								Dto.setClothEachCost((Double) result[9]);
								
								if(result[10] != null)
								Dto.setClothTotal((Double) result[10]);
								
								if(result[11] != null)
								Dto.setFirstName((String) result[11]);
								
								if(result[12] != null)
								Dto.setLastName((String) result[12]);
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
	//DAO Implementation for Upholstery Sent To Laundry Report Stop
	
	
	public List<InventoryUpholsteryTransferDto> getUpholstery_Stock_Transfer_Report(String Query, Integer companyId) throws Exception{
		TypedQuery<Object[]> queryt =null;
		try {
			queryt = entityManager.createQuery(
					" SELECT  VT.upholsteryTransferId, VT.transferDate, VT.fromLocationId, P1.partlocation_name, VT.toLocationId, "
							+ " P2.partlocation_name, VT.clothTypesId, C.clothTypeName, VT.quantity, VT.transferById, U1.firstName, "
							+ " VT.stockTypeId, VT.transferStatusId, VT.transferReceivedById, U2.firstName, VT.transferReceivedDate "
							+ " FROM InventoryUpholsteryTransfer AS VT "								
							+ " INNER JOIN ClothTypes as C ON C.clothTypesId = VT.clothTypesId  "
							+ " INNER JOIN PartLocations as P1 on P1.partlocation_id = VT.fromLocationId "
							+ " INNER JOIN PartLocations as P2 on P2.partlocation_id = VT.toLocationId 	"			
							+ " INNER JOIN User as U1 ON U1.id = VT.transferById  "
							+ " INNER JOIN User as U2 ON U2.id = VT.transferReceivedById "
							+ " WHERE VT.companyId = "+companyId+" AND VT.markForDelete = 0 " + Query + " order by VT.upholsteryTransferId desc ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<InventoryUpholsteryTransferDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryUpholsteryTransferDto>();
				InventoryUpholsteryTransferDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryUpholsteryTransferDto();
					if(result != null) {
						Dto.setUpholsteryTransferId((Long)result[0]);
						Dto.setTransferDateStr(sqldateTime.format((Timestamp) result[1]));
						Dto.setFromLocationId((Integer)result[2]);
						Dto.setFromLocationStr((String)result[3]);
						Dto.setToLocationId((Integer)result[4]);
						Dto.setToLocationStr((String)result[5]);
						Dto.setClothTypesId((Long) result[6]);
						Dto.setClothTypeName((String) result[7]);
						Dto.setQuantity((Double) result[8]);
						Dto.setTransferById((Long)result[9]);
						Dto.setTransferByIdStr((String)result[10]);
						Dto.setStockTypeId((short)result[11]);
						Dto.setStockTypeStr(ClothInvoiceStockType.getClothInvoiceStockTypeNAme((short)result[11]));
						if(result[12] != null) {
						Dto.setTransferStatusId((short)result[12]);
						Dto.setTransferStatusStr(UpholsteryTransferStatus.getStatusName((short)result[12]));
						}
						Dto.setTransferReceivedById((Long)result[13]);
						if(result[14] != null) {
						Dto.setTransferReceivedByIdStr((String)result[14]);
						}
						if(result[15] != null) {
						Dto.setTransferReceivedDateStr(sqldateTime.format((Timestamp) result[15]));
						}
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
	
	public List<InventoryDamageAndLostHistoryDto> getUpholsteryLossReport(String Query, Integer companyId) throws Exception{
		TypedQuery<Object[]> queryt =null;
		try {
			
			queryt = entityManager.createQuery(
					" SELECT I.vid, V.vehicle_registration, I.driverId, D.driver_firstname, "
					+ "	I.clothTypesId, CT.clothTypeName, I.wareHouseLocation, P.partlocation_name, I.losedQuantity, I.createdDate	"
					+ " FROM InventoryDamageAndLostHistory  AS I "
					+ " INNER JOIN Vehicle AS V ON V.vid = I.vid "
					+ " INNER JOIN Driver AS D ON D.driver_id = I.driverId "
					+ " INNER JOIN ClothTypes AS CT ON CT.clothTypesId = I.clothTypesId "
					+ " INNER JOIN PartLocations AS P ON P.partlocation_id = I.wareHouseLocation"
					+ " WHERE I.companyId = " + companyId + " AND I.markForDelete = 0 "+Query+" ORDER BY I.InventoryDamageAndLostHistoryId DESC ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<InventoryDamageAndLostHistoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryDamageAndLostHistoryDto>();
				InventoryDamageAndLostHistoryDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryDamageAndLostHistoryDto();
						if(result != null) {
								
							Dto.setVid((Integer) result[0]);
							
							if(result[1] != null)
							Dto.setVehicle_registration((String) result[1]);
							
							Dto.setDriverId((Integer) result[2]);
							
							if(result[3] != null)
							Dto.setDriver_firstname((String) result[3]);
							
							Dto.setClothTypesId((long) result[4]);
							
							if(result[5] != null)
							Dto.setClothName((String) result[5]);
							
							Dto.setWareHouseLocation((Integer) result[6]);
							
							if(result[7] != null)
							Dto.setPartlocation_name((String) result[7]);
							
							if(result[8] != null)
							Dto.setLosedQuantity((Double) result[8]);
							
							if(result[9] != null)
							Dto.setCreatedDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[9], DateTimeUtility.DD_MM_YYYY));
								
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