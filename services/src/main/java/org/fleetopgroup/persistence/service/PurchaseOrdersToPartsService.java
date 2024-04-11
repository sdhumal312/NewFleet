/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.PurchaseOrderState;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.constant.PurchaseOrdersConfigurationConstant;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToPartsRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToUreaRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToDebitNoteDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.PurchaseOrdersToUrea;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersToPartsService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("PurchaseOrdersToPartsService")
@Transactional

public class PurchaseOrdersToPartsService implements IPurchaseOrdersToPartsService {
	@PersistenceContext EntityManager entityManager;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private PurchaseOrdersToUreaRepository 		purchaseOrdersToUreaRepository;
	@Autowired private IPurchaseOrdersService 				purchaseOrdersService;
	@Autowired private PurchaseOrdersToPartsRepository 		purchaseOrdersToPartsRepository;
	@Autowired private ICompanyConfigurationService 		companyConfigurationService;
	
	PurchaseOrdersBL POBL = new PurchaseOrdersBL();
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	
	@Override
	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersPartDetailsByType(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> 				query							= null;
		List<Object[]> 						results							= null;	
		PurchaseOrdersToPartsDto			purchaseOrdersToPartsDto		= null;
		List<PurchaseOrdersToPartsDto>		purchaseOrdersToPartsList		= null;
		try {
			
			switch (valueObject.getShort("purchaseOrderTypeId")) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				query = entityManager.createQuery(
						"SELECT POU.purchaseOrdersToUreaId, UM.manufacturerName, POU.quantity, POU.unitCost, POU.discount, "
						+ " POU.tax, POU.totalcost, POU.recivedQuantity, POU.ureaManufacturerId,"
						+ " POU.notRecivedQuantity, POU.received_quantity_remark,POU.approvalPartStatusId "
						+ " FROM PurchaseOrdersToUrea AS POU "
						+ " INNER JOIN UreaManufacturer AS UM ON UM.ureaManufacturerId = POU.ureaManufacturerId "	
						+ " WHERE POU.purchaseOrderId="+valueObject.getLong("purchaseOrderId")+" AND POU.companyId = "+userDetails.getCompany_id()+" AND POU.markForDelete = 0", Object[].class);
				break;

			default:
				System.err.println("Default");
				break;
			}
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				purchaseOrdersToPartsList = new ArrayList<PurchaseOrdersToPartsDto>();
				
				for (Object[] result : results) {
					if(result != null) {
					purchaseOrdersToPartsDto = new PurchaseOrdersToPartsDto();
					purchaseOrdersToPartsDto.setPartid((Long) result[0]);
					purchaseOrdersToPartsDto.setPurchaseorder_partname((String) result[1]);
					purchaseOrdersToPartsDto.setQuantity((Double) result[2]);
					purchaseOrdersToPartsDto.setParteachcost((Double) result[3]);
					purchaseOrdersToPartsDto.setDiscount((Double) result[4]);
					purchaseOrdersToPartsDto.setTax((Double) result[5]);
					purchaseOrdersToPartsDto.setTotalcost((Double) result[6]);
					purchaseOrdersToPartsDto.setReceived_quantity((Double) result[7]);//its last default part column
					
					try {// additional details of part 
						purchaseOrdersToPartsDto.setUreaManufacturerId((Long) result[8]);
						purchaseOrdersToPartsDto.setNotreceived_quantity((Double) result[9]);
						purchaseOrdersToPartsDto.setReceived_quantity_remark((String) result[10]);
						//purchaseOrdersToPartsDto.setInventory_all_quantity((Double) result[11]);
					} catch (ArrayIndexOutOfBoundsException e) {
					//	purchaseOrdersToPartsDto.setUreaManufacturerId(Long.parseLong("0"));
						purchaseOrdersToPartsDto.setTYRE_MANUFACTURER("");
					}
					purchaseOrdersToPartsDto.setApprovalPartStatusId((Short) result[11]);
					purchaseOrdersToPartsDto.setApprovalPartStatus(PurchaseOrderState.getPurchaseOrderApprovalStatus(purchaseOrdersToPartsDto.getApprovalPartStatusId()));
					
					double eachReceivedPartAmount = purchaseOrdersToPartsDto.getParteachcost() * purchaseOrdersToPartsDto.getReceived_quantity();
					double costWithTax = eachReceivedPartAmount + (eachReceivedPartAmount * purchaseOrdersToPartsDto.getTax() / 100);
				    double ReceivedPartCost = costWithTax - (costWithTax * purchaseOrdersToPartsDto.getDiscount() /100);
				    
					purchaseOrdersToPartsDto.setFinalReceivedAmount(Double.parseDouble(toFixedTwo.format(ReceivedPartCost)));
					
					purchaseOrdersToPartsList.add(purchaseOrdersToPartsDto);
				}
				}
			}
			
			
			return purchaseOrdersToPartsList;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null; 
			query							= null; 
			results							= null;	
			purchaseOrdersToPartsDto		= null; 
			purchaseOrdersToPartsList		= null;   
		}
	}
	
	@Transactional
	@Override
	public void savePurchaseOrderPartDetails(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		PurchaseOrdersToUrea				purchaseOrdersToUrea			= null;
		Double 								gstCost 						= 0.0;
		Double 								poGstCost 						= 0.0;
		Double 								poGstTotalCost 					= 0.0;
		HashMap<String, Object> 			configuration	        		= null;
		boolean					 			makeApprovalConfig	      		= false;
		
		try {
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			makeApprovalConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.MAKE_APPROVAL, false);
			
			PurchaseOrders purchaseOrders = (PurchaseOrders) valueObject.get("purchaseOrders");
			switch (valueObject.getShort("purchaseOrderTypeId")) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				purchaseOrdersToUrea 		= POBL.prepareUreaPurchaseOrder(valueObject, userDetails);
				if(makeApprovalConfig) {
					purchaseOrdersToUrea.setApprovalPartStatusId(PurchaseOrderState.NOT_APPROVED);
				}else {
					purchaseOrdersToUrea.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
				}
				purchaseOrdersToUreaRepository.save(purchaseOrdersToUrea);
				
				if(purchaseOrders != null &&  valueObject.getBoolean("totalGstCostConfig",false) == true) {
					poGstCost		= purchaseOrders.getPurchaseorder_totaltax_cost();
					gstCost			= (purchaseOrdersToUrea.getQuantity() * purchaseOrdersToUrea.getUnitCost())* (purchaseOrdersToUrea.getTax() / 100);
					poGstTotalCost	= poGstCost+gstCost;
					
					valueObject.put("gstCost", poGstTotalCost);
					valueObject.put("companyId", userDetails.getCompany_id());
					
					purchaseOrdersService.updatePurchaseOrderVariousTotalCost(valueObject);
				}
				break;

			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails								= null;  
			purchaseOrdersToUrea					= null;
		}
	}
	
	@Transactional
	@Override
	public void deletePurchaseOrderPartDetails(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		try {
			switch (valueObject.getShort("purchaseOrderTypeId")) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				purchaseOrdersToUreaRepository.deletePurchaseOrdersById(valueObject.getLong("purchaseOrdersToUreaId"),userDetails.getCompany_id());
				break;

			default:
				System.err.println("Default");
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails								= null;  
		}
	}
	
	@Transactional
	@Override
	public void updateReceivedQuantityOfPart(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		List<PurchaseOrdersToUrea>		purchaseOrdersToUreaList		= null;
		double							receivedQuantity				= 0;
		String							receivedQuantityRemark			= "";
		try {	
			switch (valueObject.getShort("purchaseOrderType")) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				//while re-Entering received quantity will become 0 and noReceived quantity become OrderedQuantity
				purchaseOrdersToUreaList = getPurchaseOrdersToUreaByPurchaseOrderId(valueObject.getLong("purchaseOrderId"),userDetails.getCompany_id());
				if(purchaseOrdersToUreaList != null && !purchaseOrdersToUreaList.isEmpty()) {
					for(PurchaseOrdersToUrea dto :purchaseOrdersToUreaList) {
						purchaseOrdersToUreaRepository.updateReceivedQuantityOfUrea(receivedQuantity,dto.getQuantity(),receivedQuantityRemark,dto.getPurchaseOrdersToUreaId(),userDetails.getCompany_id());
					}
				}
				break;
		default:
			purchaseOrdersUpdateFromReceivedToOrderedStatus(valueObject.getLong("purchaseOrderId"),userDetails.getCompany_id())	;		
			break;
		}
	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails								= null;  
		}
	
	}
	
	
	@Override
	public List<PurchaseOrdersToDebitNoteDto> getPurchaseOrdersDebitNoteDetailsByPurchaseType(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> 			query 							= null;
		try {
			switch (valueObject.getShort("purchaseOrderTypeId")) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				query = entityManager.createQuery(
					"SELECT p.purchaseorderto_debitnoteid, p.partid, UM.manufacturerName,"
							+ " p.received_quantity_remark, p.notreceived_quantity, p.parteachcost, "
							+ " p.discount, p.tax, p.total_return_cost, p.purchaseorder_id"
							+ " From PurchaseOrdersToDebitNote as p "
							+ " LEFT JOIN PurchaseOrdersToUrea AS POU ON POU.purchaseOrdersToUreaId = p.partid "
							+ " LEFT JOIN UreaManufacturer AS UM ON UM.ureaManufacturerId = POU.ureaManufacturerId "
							+ " where p.purchaseorder_id = "+valueObject.getLong("purchaseOrderId")+" "
							+ " AND p.companyId = " + userDetails.getCompany_id() + " AND p.markForDelete = 0 ",
					Object[].class);
				break;

			default:
				System.err.println("Default");
				break;
			}
			

			List<Object[]> results = query.getResultList();

			List<PurchaseOrdersToDebitNoteDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<PurchaseOrdersToDebitNoteDto>();
				PurchaseOrdersToDebitNoteDto list = null;
				for (Object[] result : results) {
					list = new PurchaseOrdersToDebitNoteDto();

					list.setPurchaseorderto_debitnoteid((Long) result[0]);
					list.setPartid((Long) result[1]);
					list.setPurchaseorder_partname((String) result[2]);
					list.setReceived_quantity_remark((String) result[3]);
					if(result[4] != null)
					list.setNotreceived_quantity(Double.parseDouble(toFixedTwo.format( result[4])));
					if(result[5] != null)
					list.setParteachcost(Double.parseDouble(toFixedTwo.format(result[5])));
					if(result[6] != null)
					list.setDiscount(Double.parseDouble(toFixedTwo.format(result[6])));
					if(result[7] != null)
					list.setTax(Double.parseDouble(toFixedTwo.format(result[7])));
					
					list.setTotal_return_cost((Double) result[8]);
					list.setPurchaseorder_id((Long) result[9]);
					

					Dtos.add(list);
				}
			
			}
			return Dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails								= null;  
		}

		}
	@Override
	public List<PurchaseOrdersToUrea> getPurchaseOrdersToUreaByPurchaseOrderId(Long purchaseOrdersId , Integer companyId) throws Exception {
		List<PurchaseOrdersToUrea> purchaseOrdersToUreaList = null;
		try {
			purchaseOrdersToUreaList = purchaseOrdersToUreaRepository.getPurchaseOrdersToUreaByPurchaseOrderId(purchaseOrdersId,companyId);
			return purchaseOrdersToUreaList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public void purchaseOrdersUpdateFromReceivedToOrderedStatus(Long purchaseOrdersId , Integer companyId) throws Exception {
		try {
			purchaseOrdersToPartsRepository.updateFromReceivedToOrderedStatus(purchaseOrdersId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
	
