package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.persistence.dao.VehicleClothInventoryHistoryRepository;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleClothInventoryHistoryService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleClothInventoryHistoryService implements IVehicleClothInventoryHistoryService {
	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy");
	@PersistenceContext EntityManager entityManager;
	@Autowired	VehicleClothInventoryHistoryRepository			vehicleClothInventoryHistoryRepository;
	private static final int PAGE_SIZE = 10;
	@Override
	public void saveVehicleClothInventoryHistory(VehicleClothInventoryHistory vehicleClothInventoryHistory)
			throws Exception {
		vehicleClothInventoryHistoryRepository.save(vehicleClothInventoryHistory);
	}
	
	@Override
	public List<VehicleClothInventoryHistoryDto> getUpholsteryConsumptionList(ValueObject object) throws Exception {
		TypedQuery<Object[]> 											typedQuery  	= null;
		List<VehicleClothInventoryHistoryDto>							upholsteryList 	= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VC.vehicleClothInventoryHistoryId, VC.vid, V.vehicle_registration ,"
					+ " VC.clothTypesId, c.clothTypeName, VC.asignType , VC.quantity, VC.createdOn, VC.createdById, U.firstName, "
					+ " VC.stockTypeId, VC.companyId, CIST.newStockQuantity, CIST.usedStockQuantity, VCID.quantity "//this quantity is Total Assign Quantity of that vehicle
					+ " FROM VehicleClothInventoryHistory as VC "
					+ " INNER JOIN ClothTypes as c on c.clothTypesId = VC.clothTypesId "
					+ " INNER JOIN Vehicle as V on V.vid = VC.vid "
					+ " INNER JOIN User as U on U.id = VC.createdById "
					+ " INNER JOIN ClothInventoryStockTypeDetails as CIST on CIST.clothTypesId = VC.clothTypesId AND CIST.wareHouseLocationId = VC.locationId AND CIST.markForDelete =0 "
					+ " INNER JOIN VehicleClothInventoryDetails as VCID on VCID.vid = VC.vid and VCID.clothTypesId = VC.clothTypesId "
					+ " where VC.companyId = "+object.getInt("companyId")+"  "+object.getString("queryStr")+" "
					+ " AND VC.markForDelete = 0  ORDER BY VC.createdOn DESC",
					Object[].class);
			
			/*typedQuery.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);*/
			List<Object[]> results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				upholsteryList = new ArrayList<>();
				VehicleClothInventoryHistoryDto	VehicleClothInventoryHistoryDto = null;
				for (Object[] result : results) {
					VehicleClothInventoryHistoryDto	= new VehicleClothInventoryHistoryDto();
					VehicleClothInventoryHistoryDto.setVehicleClothInventoryHistoryId((Long) result[0]);                                     
					VehicleClothInventoryHistoryDto.setVid((Integer) result[1]);                                                             
					VehicleClothInventoryHistoryDto.setVehicleRegistration((String) result[2]);                                              
					VehicleClothInventoryHistoryDto.setClothTypesId((Long) result[3]);                                                       
					VehicleClothInventoryHistoryDto.setClothTypeName((String) result[4]);                                                    
					
					VehicleClothInventoryHistoryDto.setAsignType((short)result[5]);                                                          
					VehicleClothInventoryHistoryDto.setAsignTypeStr(ClothInvoiceStockType.getClothInvoiceAsignNAme((short) result[5]));      
					if(VehicleClothInventoryHistoryDto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN) {
						VehicleClothInventoryHistoryDto.setAsignedQuantity((Double)result[6]);  
						VehicleClothInventoryHistoryDto.setRemovedQuantity(0.0);        
					}else if(VehicleClothInventoryHistoryDto.getAsignType() == ClothInvoiceStockType.CLOTH_ASIGN_TYPE_REMOVE) {
						VehicleClothInventoryHistoryDto.setRemovedQuantity((Double)result[6]);      
						VehicleClothInventoryHistoryDto.setAsignedQuantity(0.0);  
					}
					                                              
					VehicleClothInventoryHistoryDto.setCreatedOnStr(format.format((Date)result[7]));                                        
					VehicleClothInventoryHistoryDto.setCreatedById((Long)result[8]);                                                         
					VehicleClothInventoryHistoryDto.setCreatedByName((String)result[9]);                                                     
					VehicleClothInventoryHistoryDto.setStockTypeId((short)result[10]);                                                       
					VehicleClothInventoryHistoryDto.setStockTypeName(ClothInvoiceStockType.getClothInvoiceStockTypeNAme((short) result[10]));
					VehicleClothInventoryHistoryDto.setCompanyId((Integer)result[11]);
					VehicleClothInventoryHistoryDto.setNewStockQuantity((Double)result[12]);
					VehicleClothInventoryHistoryDto.setOldStockQuantity((Double)result[13]);
					if(result[14] != null) {
						VehicleClothInventoryHistoryDto.setTotalAssignQuantity((Double)result[14]); // for particular vehicle
					}
					upholsteryList.add(VehicleClothInventoryHistoryDto);
					
				
				}
				
			}
			return upholsteryList;
		} catch (Exception e) {
			throw e;
		}finally {
			typedQuery  	= null;
		}
	}
	
}
