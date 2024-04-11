package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.persistence.dao.BatteryHistoryRepository;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.VehicleBatteryLayout;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatteryHistoryService implements IBatteryHistoryService {
	
	@Autowired private BatteryHistoryRepository		batteryHistoryRepository;
	
	@PersistenceContext EntityManager entityManager;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	SimpleDateFormat SQLdate 			= new SimpleDateFormat("dd-MM-yyyy");
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	public void save(BatteryHistory batteryHistory) throws Exception {

		batteryHistoryRepository.save(batteryHistory);
	}
	
	@Override
	public List<BatteryHistoryDto> getBatteryHistoryList(Long batteryId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT BH.batteryId, BH.batteryHistoryId, BH.batterySerialNumber, BH.vid, BH.layoutPosition, BH.batteryStatusId,"
							+ " BH.openOdometer, BH.batteryUseage, BH.batteryCost, BH.batteryAsignDate, BH.batteryComment,"
							+ " V.vehicle_registration, BH.usesNoOfDay "
							+ " FROM BatteryHistory AS BH "
							+ " LEFT JOIN Vehicle V ON V.vid = BH.vid"
							+ " where BH.batteryId = "+batteryId+" AND BH.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryHistoryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryHistoryDto	batteryInvoice = new BatteryHistoryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatteryHistoryId((Long) result[1]);
					batteryInvoice.setBatterySerialNumber((String) result[2]);
					batteryInvoice.setVid((Integer) result[3]);
					batteryInvoice.setLayoutPosition((short) result[4]);
					batteryInvoice.setBatteryStatusId((short) result[5]);
					batteryInvoice.setOpenOdometer((Integer) result[6]);
					batteryInvoice.setBatteryUseage((Integer) result[7]);
					batteryInvoice.setBatteryCost((Double) result[8]);
					batteryInvoice.setBatteryAsignDate((Timestamp) result[9]);
					//batteryInvoice.setBatteryDismountDate((Timestamp) result[10]);
					batteryInvoice.setBatteryComment((String) result[10]);
					batteryInvoice.setVehicle_registration((String) result[11]);
					batteryInvoice.setUsesNoOfDay((Long) result[12]);
					
					if(batteryInvoice.getBatteryAsignDate() != null) {
						batteryInvoice.setBatteryAsignDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoice.getBatteryAsignDate(), DateTimeUtility.DD_MM_YYYY));
					}else {
						batteryInvoice.setBatteryAsignDateStr("--");
					}
					if(batteryInvoice.getUsesNoOfDay() == null) {
						batteryInvoice.setUsesNoOfDay((long) 0);
					}
					
					if(batteryInvoice.getOpenOdometer() == null) {
						batteryInvoice.setOpenOdometer(0);
					}
					
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName(batteryInvoice.getBatteryStatusId()));
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	public Map<Integer, List<BatteryHistoryDto>> getAllBatteryAssignmentDetails(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 									typedQuery  	= null;
		Map<Integer, List<BatteryHistoryDto>>					companyHM		= null;
		List<BatteryHistoryDto>									companyLIst 	= null;
		try {
			typedQuery = entityManager.createQuery(
					" Select BH.batteryId, V.vehicle_registration,BH.openOdometer, BH.batteryAsignDate, BM.manufacturerName, BH.layoutPosition, "
							+ " BH.batterySerialNumber, BH.batteryStatusId ,BH.companyId, BH.preBatteryAsignDate, BH.preBatterySerialNumber "
							+ " FROM BatteryHistory BH "
							+ " INNER JOIN Battery B on B.batteryId = BH.batteryId "
							+ " INNER JOIN Vehicle V on V.vid = BH.vid "
							+ " INNER JOIN BatteryManufacturer BM on BM.batteryManufacturerId = B.batteryManufacturerId "
							+ " WHERE BH.markForDelete = 0 AND BH.batteryAsignDate BETWEEN '"+startDate+"' AND '"+endDate+"' "
							,Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			if (results != null && !results.isEmpty()) {
				
				companyHM	= new HashMap<Integer, List<BatteryHistoryDto>>();
				
				BatteryHistoryDto	batteryHistoryDto = null;
				for (Object[] result : results) {
					batteryHistoryDto	= new BatteryHistoryDto();
					batteryHistoryDto.setBatteryId((Long) result[0]);
					batteryHistoryDto.setVehicle_registration((String) result[1]);
					batteryHistoryDto.setOpenOdometer((Integer) result[2]);
					batteryHistoryDto.setBatteryAsignDate((Timestamp)result[3]);
					batteryHistoryDto.setBatteryAsignDateStr(SQLdate.format((Timestamp)result[3]));
					batteryHistoryDto.setBatteryManufacturerName((String) result[4]);
					batteryHistoryDto.setLayoutPosition((short) result[5]);
					batteryHistoryDto.setBatterySerialNumber((String) result[6]);
					batteryHistoryDto.setBatteryStatusId((short)result[7]);
					batteryHistoryDto.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short)result[7]));
					batteryHistoryDto.setCompanyId((Integer)result[8]);
					if(result[9] != null) {
					batteryHistoryDto.setPreBatteryAsignDate((Timestamp)result[9]);
					batteryHistoryDto.setPreBatteryAsignDateStr(SQLdate.format((Timestamp)result[9]));
					}else {
						batteryHistoryDto.setPreBatteryAsignDateStr("-");
					}
					if(result[10] != null) {
					batteryHistoryDto.setPreBatterySerialNumber((String)result[10]);
					}else {
						batteryHistoryDto.setPreBatterySerialNumber("-");
					}
					
					if(batteryHistoryDto.getBatteryStatusId() == BatteryConstant.BATTERY_ASIGNED_STATUS_IN_DISMOUNT) {
						if(batteryHistoryDto.getPreBatteryAsignDate() != null && batteryHistoryDto.getBatteryAsignDate() != null) {
							batteryHistoryDto.setUsesNoOfDay(DateTimeUtility.getDayDiffBetweenTwoDates(batteryHistoryDto.getPreBatteryAsignDate(), batteryHistoryDto.getBatteryAsignDate()));
						}else {
							long l = 0;
							batteryHistoryDto.setUsesNoOfDay(l);
						}
					}else {
						if(batteryHistoryDto.getBatteryAsignDate() != null) {
							batteryHistoryDto.setUsesNoOfDay(DateTimeUtility.getDayDiffBetweenTwoDates(batteryHistoryDto.getBatteryAsignDate(), DateTimeUtility.getCurrentTimeStamp()));
						}else {
							long l = 0;
							batteryHistoryDto.setUsesNoOfDay(l);
						}
					}
					companyLIst	= companyHM.get(batteryHistoryDto.getCompanyId());
					if(companyLIst == null) {
						companyLIst	= new ArrayList<>();
					}
					companyLIst.add(batteryHistoryDto);
					
					companyHM.put(batteryHistoryDto.getCompanyId(), companyLIst);
				
				}
				
			}
			return companyHM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public BatteryHistoryDto getPreBatteryHistory(VehicleBatteryLayout vehicleBatteryLayout, long layoutPosition,short batterySatus, Integer companyID) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					" Select BH.batteryHistoryId, BH.batterySerialNumber, BH.batteryAsignDate FROM BatteryHistory AS BH "
							+ " WHERE  BH.vid = "+vehicleBatteryLayout.getVid()+" AND BH.batteryStatusId = "+batterySatus+" AND BH.layoutPosition = "+layoutPosition+" "
							+ " AND BH.companyId = "+ companyID+" AND BH.markForDelete = 0 ORDER BY BH.batteryHistoryId desc ");
							queryt.setMaxResults(1);
			
			Object[] result = null;
			BatteryHistoryDto select  = new BatteryHistoryDto();
			try {
				result = (Object[]) queryt.getSingleResult();
				
			} catch (NoResultException nre) {
				
				return null;
			}
			
			if (result != null) {
				
				select.setBatteryHistoryId((Long)result[0]);
				select.setBatterySerialNumber((String)result[1]);
				select.setBatteryAsignDate((Timestamp)result[2]);
			}
			return select;
			} catch (Exception e) {
				LOGGER.error("Excep", e);
				throw e;
			}
		}
	@Transactional
	public BatteryHistory getBatteryMountCreatedBetweenDates(String startDate, String endDate,Integer companyId) throws Exception {
		
		TypedQuery<BatteryHistory> query = entityManager.createQuery(
				" SELECT T "
						+ " From BatteryHistory as T "
						+ " WHERE T.createdOn between '"+ startDate +"' AND '"+ endDate + "' AND T.companyId = " +companyId+" "						
						+ " AND T.markForDelete = 0 ",BatteryHistory.class);
		
		BatteryHistory	batteryHistory = null;
		try {
				query.setMaxResults(1);
				batteryHistory = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return batteryHistory;
	}
	public List<BatteryHistoryDto> getBatteryConsumptionList(ValueObject object) throws Exception {
		TypedQuery<Object[]> 									typedQuery  	= null;
		List<BatteryHistoryDto>									batteryList 	= null;
		try {
			typedQuery = entityManager.createQuery(
					" Select BH.batteryId, V.vehicle_registration,BH.openOdometer, BH.batteryAsignDate, BM.manufacturerName, BH.layoutPosition, "
							+ " BH.batterySerialNumber, BH.batteryStatusId ,BH.companyId, BH.preBatteryAsignDate, BH.preBatterySerialNumber,BH.vid, B.batteryAmount "
							+ " FROM BatteryHistory BH "
							+ " INNER JOIN Battery B on B.batteryId = BH.batteryId "
							+ " INNER JOIN Vehicle V on V.vid = BH.vid "
							+ " INNER JOIN BatteryManufacturer BM on BM.batteryManufacturerId = B.batteryManufacturerId "
							+ " WHERE BH.markForDelete = 0 AND "+object.getString("queryStr")+" AND BH.companyId ="+object.getInt("companyId")+" ORDER BY BH.batteryAsignDate DESC "
							,Object[].class);
			/*typedQuery.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);*/
			
			List<Object[]> results = typedQuery.getResultList();
			batteryList	= new ArrayList<>();
			if (results != null && !results.isEmpty()) {
				
				BatteryHistoryDto	batteryHistoryDto = null;
				for (Object[] result : results) {
					batteryHistoryDto	= new BatteryHistoryDto();
					batteryHistoryDto.setBatteryId((Long) result[0]);
					batteryHistoryDto.setVehicle_registration((String) result[1]);
					batteryHistoryDto.setOpenOdometer((Integer) result[2]);
					batteryHistoryDto.setBatteryAsignDate((Timestamp)result[3]);
					batteryHistoryDto.setBatteryAsignDateStr(SQLdate.format((Timestamp)result[3]));
					batteryHistoryDto.setBatteryManufacturerName((String) result[4]);
					batteryHistoryDto.setLayoutPosition((short) result[5]);
					batteryHistoryDto.setBatterySerialNumber((String) result[6]);
					batteryHistoryDto.setBatteryStatusId((short)result[7]);
					batteryHistoryDto.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short)result[7]));
					batteryHistoryDto.setCompanyId((Integer)result[8]);
					if(result[9] != null) {
					batteryHistoryDto.setPreBatteryAsignDate((Timestamp)result[9]);
					batteryHistoryDto.setPreBatteryAsignDateStr(SQLdate.format((Timestamp)result[9]));
					}else {
						batteryHistoryDto.setPreBatteryAsignDateStr("-");
					}
					if(result[10] != null) {
					batteryHistoryDto.setPreBatterySerialNumber((String)result[10]);
					}else {
						batteryHistoryDto.setPreBatterySerialNumber("-");
					}
					
					if(batteryHistoryDto.getBatteryStatusId() == BatteryConstant.BATTERY_ASIGNED_STATUS_IN_DISMOUNT) {
						if(batteryHistoryDto.getPreBatteryAsignDate() != null && batteryHistoryDto.getBatteryAsignDate() != null) {
							batteryHistoryDto.setUsesNoOfDay(DateTimeUtility.getDayDiffBetweenTwoDates(batteryHistoryDto.getPreBatteryAsignDate(), batteryHistoryDto.getBatteryAsignDate()));
						}else {
							long l = 0;
							batteryHistoryDto.setUsesNoOfDay(l);
						}
					}else {
						if(batteryHistoryDto.getBatteryAsignDate() != null) {
							batteryHistoryDto.setUsesNoOfDay(DateTimeUtility.getDayDiffBetweenTwoDates(batteryHistoryDto.getBatteryAsignDate(), DateTimeUtility.getCurrentTimeStamp()));
						}else {
							long l = 0;
							batteryHistoryDto.setUsesNoOfDay(l);
						}
					}
					batteryHistoryDto.setVid((Integer) result[11]);
					if(batteryHistoryDto.getBatteryStatusId() == BatteryConstant.BATTERY_ASIGNED_STATUS_IN_MOUNT) {
						batteryHistoryDto.setBatteryCost((Double) result[12]);
					}else {
						batteryHistoryDto.setBatteryCost(0.0);
					}
					batteryList.add(batteryHistoryDto);
				
				}
				
			}
			return batteryList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
