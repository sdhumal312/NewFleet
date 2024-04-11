package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.persistence.bl.TyreUsageHistoryBL;
import org.fleetopgroup.persistence.dao.TyreUsageHistoryRepository;
import org.fleetopgroup.persistence.dto.TyreUsageHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.TyreUsageHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreLayoutService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TyreUsageHistoryService")
@Transactional
public class TyreUsageHistoryService implements ITyreUsageHistoryService {
	@PersistenceContext EntityManager entityManager;
	@Autowired	private TyreUsageHistoryRepository 		tyreUsageHistoryRepository;
	
	@Autowired private IVehicleTyreLayoutService 		vehicleTyreLayoutService;
	@Autowired private IVehicleOdometerHistoryService 	vehicleOdometerHistoryService;
	@Autowired private IInventoryTyreService 			iventoryTyreService;
	@Autowired private IVehicleService 					vehicleService;
	@Autowired private IIssuesService 					issuesService;
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired private IVehicleTyreAssignmentService 	vehicleTyreAssignmentService;
	SimpleDateFormat 	dateTimeFormat 			= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	SimpleDateFormat 			dateFormat 							= new SimpleDateFormat("dd-MM-yyyy");
	TyreUsageHistoryBL 			tyreUsageHistoryBL 					= new TyreUsageHistoryBL();
	
	@Override
	public ValueObject saveTyreMountUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		TyreUsageHistory 		tyreUsageHistory		= null;
		TyreUsageHistoryDto		tyreUsageHistoryDto		= null;
		int						tyreUsage				= 0;
		try {
			tyreUsageHistoryDto = getPreTyreUsageHistory(object.getLong("newTyreId"), valueObject.getInt("companyId"));
			if(tyreUsageHistoryDto != null) {
				
				tyreUsage = tyreUsageHistoryDto.getTyreUsage();
				valueObject.put("tyreUsage", tyreUsage);
			}
			
			tyreUsageHistory =	tyreUsageHistoryBL.prepareTyreMountUsageHistory(valueObject,object);
			tyreUsageHistoryRepository.save(tyreUsageHistory);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject saveTyreDismountUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		TyreUsageHistory 		tyreUsageHistory		= null;
		TyreUsageHistoryDto		tyreUsageHistoryDto		= null;
		int						tyreUsage				= 0;
		try {
			tyreUsageHistoryDto = getPreTyreUsageHistory(object.getLong("tyreId"), valueObject.getInt("companyId"));
			if(tyreUsageHistoryDto != null) {
				
				tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("dismountOdometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
				valueObject.put("tyreUsage", tyreUsage);
			}
			
			tyreUsageHistory =	tyreUsageHistoryBL.prepareTyreDismountUsageHistory(valueObject,object);
			tyreUsageHistoryRepository.save(tyreUsageHistory);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject saveTyreRotateFromUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		TyreUsageHistory 		tyreUsageHistory		= null;
		TyreUsageHistoryDto		tyreUsageHistoryDto		= null;
		int						tyreUsage				= 0;
		try {
			tyreUsageHistoryDto = getPreTyreUsageHistory(object.getLong("tyreFromId",0), valueObject.getInt("companyId"));
			if(tyreUsageHistoryDto != null) {
				tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("vehicleOdometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
				valueObject.put("tyreUsage", tyreUsage);
			}
			tyreUsageHistory =	tyreUsageHistoryBL.prepareTyreRotateFromUsageHistory(valueObject,object);
			tyreUsageHistoryRepository.save(tyreUsageHistory);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject saveTyreRotateToUsageHistory(ValueObject valueObject,ValueObject object) throws Exception {
		TyreUsageHistory 		tyreUsageHistory		= null;
		TyreUsageHistoryDto		tyreUsageHistoryDto		= null;
		int						tyreUsage				= 0;
		try {
			tyreUsageHistoryDto = getPreTyreUsageHistory(object.getLong("tyreToId",0), valueObject.getInt("companyId"));
			if(tyreUsageHistoryDto != null) {
				tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("vehicleOdometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
				valueObject.put("tyreUsage", tyreUsage);
			}
			tyreUsageHistory =	tyreUsageHistoryBL.prepareTyreRotateToUsageHistory(valueObject,object);
			tyreUsageHistoryRepository.save(tyreUsageHistory);
			
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	public TyreUsageHistoryDto getPreTyreUsageHistory(Long tyreId, Integer companyId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					" SELECT TU.tyreUsageHistoryId, TU.tyreId, TU.tyreUsage, TU.vehicleOdometer  FROM TyreUsageHistory AS TU "
							+ " WHERE  TU.tyreId = "+tyreId+" "
							+ " AND TU.companyId = "+ companyId +" AND TU.markForDelete = 0 ORDER BY TU.tyreUsageHistoryId desc ");
							queryt.setMaxResults(1);
			Object[] result = null;
			TyreUsageHistoryDto dto  = new TyreUsageHistoryDto();
			try {
				result = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
			if (result != null) {
				dto.setTyreUsageHistoryId((Long)result[0]);
				dto.setTyreId((Long)result[1]);
				dto.setTyreUsage((Integer)result[2]);
				dto.setVehicleOdometer((Integer)result[3]);
			}
			return dto;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	
	@Transactional
	public TyreUsageHistoryDto getPreVehicleOdometer(Integer vid, Integer companyId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					" SELECT TU.tyreUsageHistoryId, TU.tyreId, TU.tyreUsage, TU.vehicleOdometer  FROM TyreUsageHistory AS TU "
							+ " WHERE  TU.vid = "+vid+" AND TU.companyId = "+ companyId +" AND TU.markForDelete = 0 ORDER BY TU.tyreUsageHistoryId desc ");
							queryt.setMaxResults(1);
			Object[] result = null;
			TyreUsageHistoryDto dto  = new TyreUsageHistoryDto();
			try {
				result = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
			if (result != null) {
				dto.setTyreUsageHistoryId((Long)result[0]);
				dto.setTyreId((Long)result[1]);
				dto.setTyreUsage((Integer)result[2]);
				dto.setVehicleOdometer((Integer)result[3]);
			}
			return dto;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	
	
	
	@Override
	public void saveTripTyreUsageHistory(ValueObject valueObject) throws Exception {
		TyreUsageHistory 		tyreUsageHistory		= null;
		TyreUsageHistoryDto		tyreUsageHistoryDto		= null;
		int						tyreUsage				= 0;
		List<TyreUsageHistory> 		tyreUsageHistoryList		= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		try {
			
			vehicleTyreLayoutPositionList 		=	vehicleTyreAssignmentService.getAssignedTyreOfVehicle(valueObject);
			tyreUsageHistoryList				= new ArrayList<>();
			
			if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
				
				for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
					
					if(!dto.getPOSITION().equals("SP-0"))	{
						
						tyreUsageHistoryDto = getPreTyreUsageHistory(dto.getTYRE_ID(), valueObject.getInt("companyId"));
						if(tyreUsageHistoryDto != null ) {
							tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("tripClosingKM",0) - tyreUsageHistoryDto.getVehicleOdometer());
							valueObject.put("tyreUsage", tyreUsage);
							valueObject.put("vehiclePreOdometer", tyreUsageHistoryDto.getVehicleOdometer());
						}
						tyreUsageHistory =	tyreUsageHistoryBL.prepareTripTyreUsageHistory(valueObject,dto);
						tyreUsageHistoryList.add(tyreUsageHistory);
					}
				}
				
				tyreUsageHistoryRepository.saveAll(tyreUsageHistoryList);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public void saveFuelTyreUsageHistory(ValueObject valueObject) throws Exception {
		TyreUsageHistory 					tyreUsageHistory				= null;
		TyreUsageHistoryDto					tyreUsageHistoryDto				= null;
		int									tyreUsage						= 0;
		List<TyreUsageHistory> 				tyreUsageHistoryList			= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		TyreUsageHistoryDto					preTyreUsageHistoryDto				= null;
		try {
			
			
			preTyreUsageHistoryDto =	getPreVehicleOdometer(valueObject.getInt("FuelSelectVehicle"),valueObject.getInt("companyId"));
			valueObject.put("vid", valueObject.getInt("FuelSelectVehicle"));
			if(preTyreUsageHistoryDto != null && valueObject.getInt("fuel_meter") > preTyreUsageHistoryDto.getVehicleOdometer()) {
				vehicleTyreLayoutPositionList =	vehicleTyreAssignmentService.getAssignedTyreOfVehicle(valueObject);
				tyreUsageHistoryList			= new ArrayList<>();
				if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
					
					for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
						tyreUsageHistoryDto = getPreTyreUsageHistory(dto.getTYRE_ID(), valueObject.getInt("companyId"));
						if(tyreUsageHistoryDto != null) {
							tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("fuel_meter",0) - tyreUsageHistoryDto.getVehicleOdometer());
							valueObject.put("tyreUsage", tyreUsage);
							valueObject.put("vehiclePreOdometer", tyreUsageHistoryDto.getVehicleOdometer());
						}
						
						tyreUsageHistory =	tyreUsageHistoryBL.prepareFuelTyreUsageHistory(valueObject,dto);
						tyreUsageHistoryList.add(tyreUsageHistory);
					}
					
					tyreUsageHistoryRepository.saveAll(tyreUsageHistoryList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void saveDSETyreUsageHistory(ValueObject valueObject) throws Exception {
		TyreUsageHistory 					tyreUsageHistory				= null;
		TyreUsageHistoryDto					tyreUsageHistoryDto				= null;
		int									tyreUsage						= 0;
		List<TyreUsageHistory> 				tyreUsageHistoryList			= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		TyreUsageHistoryDto					preTyreUsageHistoryDto				= null;
		try {
			
			preTyreUsageHistoryDto =	getPreVehicleOdometer(valueObject.getInt("vid"),valueObject.getInt("companyId"));
			if(preTyreUsageHistoryDto != null && valueObject.getInt("vehicleOdometer") > preTyreUsageHistoryDto.getVehicleOdometer()) {
				vehicleTyreLayoutPositionList =	vehicleTyreAssignmentService.getAssignedTyreOfVehicle(valueObject);
				tyreUsageHistoryList			= new ArrayList<>();
				if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
					
					for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
						tyreUsageHistoryDto = getPreTyreUsageHistory(dto.getTYRE_ID(), valueObject.getInt("companyId"));
						if(tyreUsageHistoryDto != null) {
							
							tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("vehicleOdometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
							valueObject.put("tyreUsage", tyreUsage);
							valueObject.put("vehiclePreOdometer", tyreUsageHistoryDto.getVehicleOdometer());
						}
						
						tyreUsageHistory =	tyreUsageHistoryBL.prepareDSETyreUsageHistory(valueObject,dto);
						tyreUsageHistoryList.add(tyreUsageHistory);
					}
					
					
					tyreUsageHistoryRepository.saveAll(tyreUsageHistoryList);
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void saveUreaTyreUsageHistory(ValueObject valueObject) throws Exception {
		TyreUsageHistory 					tyreUsageHistory				= null;
		TyreUsageHistoryDto					tyreUsageHistoryDto				= null;
		int									tyreUsage						= 0;
		List<TyreUsageHistory> 				tyreUsageHistoryList			= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		try {
			
			vehicleTyreLayoutPositionList =	vehicleTyreAssignmentService.getAssignedTyreOfVehicle(valueObject);
			tyreUsageHistoryList			= new ArrayList<>();
			if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
				
				for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
					tyreUsageHistoryDto = getPreTyreUsageHistory(dto.getTYRE_ID(), valueObject.getInt("companyId"));
					if(tyreUsageHistoryDto != null) {
						
						tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("ureaOdometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
						valueObject.put("tyreUsage", tyreUsage);
						valueObject.put("vehiclePreOdometer", tyreUsageHistoryDto.getVehicleOdometer());
					}
					
					tyreUsageHistory =	tyreUsageHistoryBL.prepareUreaTyreUsageHistory(valueObject,dto);
					tyreUsageHistoryList.add(tyreUsageHistory);
				}
				
				tyreUsageHistoryRepository.saveAll(tyreUsageHistoryList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void saveIssueTyreUsageHistory(ValueObject valueObject) throws Exception {
		TyreUsageHistory 					tyreUsageHistory				= null;
		TyreUsageHistoryDto					tyreUsageHistoryDto				= null;
		int									tyreUsage						= 0;
		List<TyreUsageHistory> 				tyreUsageHistoryList			= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		
		try {
			vehicleTyreLayoutPositionList =	vehicleTyreAssignmentService.getAssignedTyreOfVehicle(valueObject);
				tyreUsageHistoryList			= new ArrayList<>();
				if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
					
					for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
						tyreUsageHistoryDto = getPreTyreUsageHistory(dto.getTYRE_ID(), valueObject.getInt("companyId"));
						if(tyreUsageHistoryDto != null) {
							
							tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("odometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
							valueObject.put("tyreUsage", tyreUsage);
							valueObject.put("vehiclePreOdometer", tyreUsageHistoryDto.getVehicleOdometer());
						}
						
						tyreUsageHistory =	tyreUsageHistoryBL.prepareIssueTyreUsageHistory(valueObject,dto);
						tyreUsageHistoryList.add(tyreUsageHistory);
					}
					
					tyreUsageHistoryRepository.saveAll(tyreUsageHistoryList);
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void saveWOTyreUsageHistory(ValueObject valueObject) throws Exception {
		TyreUsageHistory 					tyreUsageHistory				= null;
		TyreUsageHistoryDto					tyreUsageHistoryDto				= null;
		int									tyreUsage						= 0;
		List<TyreUsageHistory> 				tyreUsageHistoryList			= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		TyreUsageHistoryDto					preTyreUsageHistoryDto				= null;
		try {
			preTyreUsageHistoryDto =	getPreVehicleOdometer(valueObject.getInt("vid"),valueObject.getInt("companyId"));
			
			if(preTyreUsageHistoryDto != null) {
				
				if(valueObject.getInt("woOdometer") > preTyreUsageHistoryDto.getVehicleOdometer()) {
					vehicleTyreLayoutPositionList =	vehicleTyreAssignmentService.getAssignedTyreOfVehicle(valueObject);
					tyreUsageHistoryList			= new ArrayList<>();
					if(vehicleTyreLayoutPositionList != null && !vehicleTyreLayoutPositionList.isEmpty()) {
						
						for(VehicleTyreLayoutPositionDto dto : vehicleTyreLayoutPositionList) {
							tyreUsageHistoryDto = getPreTyreUsageHistory(dto.getTYRE_ID(), valueObject.getInt("companyId"));
							if(tyreUsageHistoryDto != null) {
								
								tyreUsage = tyreUsageHistoryDto.getTyreUsage() + (valueObject.getInt("woOdometer",0) - tyreUsageHistoryDto.getVehicleOdometer());
								valueObject.put("tyreUsage", tyreUsage);
								valueObject.put("vehiclePreOdometer", tyreUsageHistoryDto.getVehicleOdometer());
							}
							
							tyreUsageHistory =	tyreUsageHistoryBL.prepareWOTyreUsageHistory(valueObject,dto);
							tyreUsageHistoryList.add(tyreUsageHistory);
						}
						
						tyreUsageHistoryRepository.saveAll(tyreUsageHistoryList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public List<TyreUsageHistoryDto> getTyreUsageHistory(Long tyreId, Integer companyId) throws Exception {
		TypedQuery<Object[]> 		query 							= null;
		List<TyreUsageHistoryDto>	tyreUsageHistoryList			= null;
		TyreUsageHistoryDto			tyreUsageHistoryDto				= null;
		try {
			 query = entityManager.createQuery(
				" SELECT TU.tyreUsageHistoryId, TU.creationOn, TU.vid, V.vehicle_registration, TU.transactionUsageTypeId, TU.transactionId, "
				+ " WO.workorders_Number, DSE.dealerServiceEntriesNumber, I.ISSUES_NUMBER, T.tripSheetNumber, F.fuel_Number, UE.ureaEntriesNumber,"
				+ " TU.tyreUsage, TU.vehicleOdometer  FROM TyreUsageHistory AS TU "
				+ " INNER JOIN Vehicle AS V ON V.vid = TU.vid "
				+ " LEFT JOIN WorkOrders AS WO ON WO.workorders_id = TU.transactionId "
				+ " LEFT JOIN DealerServiceEntries AS DSE ON DSE.dealerServiceEntriesId = TU.transactionId "
				+ " LEFT JOIN Issues AS I ON I.ISSUES_ID = TU.transactionId "
				+ " LEFT JOIN TripSheet AS T ON T.tripSheetID = TU.transactionId "
				+ " LEFT JOIN Fuel AS F ON F.fuel_id = TU.transactionId "
				+ " LEFT JOIN UreaEntries AS UE ON UE.ureaEntriesId = TU.transactionId "
				+ " WHERE  TU.tyreId = "+tyreId+" AND TU.companyId = "+ companyId +" AND TU.markForDelete = 0 ORDER BY TU.tyreUsageHistoryId desc ",
					Object[].class);
			 
		
				List<Object[]> results = query.getResultList();
	
				if (results != null && !results.isEmpty()) {				
					tyreUsageHistoryList = new ArrayList<>();
					
					for (Object[] result : results) {
						tyreUsageHistoryDto = new TyreUsageHistoryDto();
						tyreUsageHistoryDto.setTyreUsageHistoryId((Long)result[0]);
						tyreUsageHistoryDto.setCreationDate(dateTimeFormat.format((Date)result[1]));
						tyreUsageHistoryDto.setVid((Integer)result[2]);
						tyreUsageHistoryDto.setVehicleRegistration((String)result[3]);
						tyreUsageHistoryDto.setTransactionUsageTypeId((short)result[4]);
						tyreUsageHistoryDto.setTransactionUsageType(TyreAssignmentConstant.getTransactionUsageType(tyreUsageHistoryDto.getTransactionUsageTypeId()));
						tyreUsageHistoryDto.setTransactionId((Long)result[5] );
						if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_WO) {
							tyreUsageHistoryDto.setTransactionNumber("WO-"+(Long)result[6] );
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_DSE) {
							tyreUsageHistoryDto.setTransactionNumber("DSE-"+(Long)result[7] );
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_ISSUE) {
							tyreUsageHistoryDto.setTransactionNumber("I-"+(Long)result[8] );
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_TRIP) {
							tyreUsageHistoryDto.setTransactionNumber("T-"+(Long)result[9] );
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_FUEL) {
							tyreUsageHistoryDto.setTransactionNumber("F-"+(Long)result[10] );
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_UREA) {
							tyreUsageHistoryDto.setTransactionNumber("UE-"+(Long)result[11] );
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_MOUNT) {
							tyreUsageHistoryDto.setTransactionNumber("Mount");
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_DISMOUNT) {
							tyreUsageHistoryDto.setTransactionNumber("Dismount");
						}else if((short)result[4] == TyreAssignmentConstant.TRANSACTION_USAGE_TYPE_ROTATE) {
							tyreUsageHistoryDto.setTransactionNumber("Rotate");
						}
						
						tyreUsageHistoryDto.setTyreUsage((Integer)result[12] );
						tyreUsageHistoryList.add(tyreUsageHistoryDto);
					}
				}
				
				return tyreUsageHistoryList;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	
	
}
