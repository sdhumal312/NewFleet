package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.UpholsteryTransferStatus;
import org.fleetopgroup.persistence.bl.ClothInventoryBL;
import org.fleetopgroup.persistence.dao.ClothInventoryStockTypeDetailsRepository;
import org.fleetopgroup.persistence.dao.InventoryUpholsteryTransferRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryUpholsteryTransferDto;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.InventoryUpholsteryTransfer;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryUpholsteryTransferService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InventoryUpholsteryTransferService implements IInventoryUpholsteryTransferService {
	
	@Autowired InventoryUpholsteryTransferRepository 			InventoryUpholsteryTransferRepository;
	@Autowired	ClothInventoryStockTypeDetailsRepository		clothInventoryStockTypeDetailsRepository;
	@Autowired	IClothInventoryStockTypeDetailsService			clothInventoryStockTypeDetailsService;
	
	@PersistenceContext EntityManager entityManager;
	ClothInventoryBL	inventoryBL	= new ClothInventoryBL();
	
	SimpleDateFormat	format  = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	format1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	private static final int PAGE_SIZE = 10;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveUpholsteryTransfer(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 				dataArrayObjColl 			= null;
		List<InventoryUpholsteryTransfer>	upholsteryTransfer			= null;
		try {
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("UpholsteryTransfer");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				upholsteryTransfer = new ArrayList<InventoryUpholsteryTransfer>();
				for(ValueObject clothTransfer : dataArrayObjColl) {
					upholsteryTransfer.add(inventoryBL.saveUpholsteryTransfer(clothTransfer, valueObject));
				}
				
				InventoryUpholsteryTransferRepository.saveAll(upholsteryTransfer);
				
				for(InventoryUpholsteryTransfer updateStockTypeDetails : upholsteryTransfer) {
					updateInTransferQtyInStockTypeDetails(updateStockTypeDetails);
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw e;
		}finally {
			dataArrayObjColl	= null;
			upholsteryTransfer	= null;
		}
	}
	
	@Override
	@Transactional
	public void updateInTransferQtyInStockTypeDetails(InventoryUpholsteryTransfer inTransfer) throws Exception {
		short 		stockTypeId					= 0;
		double 		transferQuantity			= 0;
		long		clothTypeId					= 0;
		int			fromLocationId				= 0;
		
		stockTypeId 			= 	inTransfer.getStockTypeId();
		transferQuantity		= 	inTransfer.getQuantity();
		clothTypeId				=   inTransfer.getClothTypesId();
		fromLocationId			=   inTransfer.getFromLocationId();
		
		if(stockTypeId == ClothInvoiceStockType.STOCK_TYPE_NEW) {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.newStockQuantity = VC.newStockQuantity - "+transferQuantity+", "
							+ " VC.inTransferQuantity = VC.inTransferQuantity + "+transferQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+fromLocationId+" AND VC.markForDelete = 0 ")
					.executeUpdate();
		} else {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity - "+transferQuantity+", "
							+ " VC.inTransferQuantity = VC.inTransferQuantity + "+transferQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+fromLocationId+" AND VC.markForDelete = 0 ")
					.executeUpdate();
		}
		
	}
	
	@Override
	public ValueObject getTransferReceivedDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		List<InventoryUpholsteryTransferDto> 	upholsteryReceiveList		= null;
		Integer				   					pageNumber					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);

			Page<InventoryUpholsteryTransfer> page = getDeployment_Page_UpholsteryTransfer(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			upholsteryReceiveList	=	getTransferReceivedDetailsList(pageNumber, userDetails);
			if(upholsteryReceiveList != null && !upholsteryReceiveList.isEmpty())
			valueObject.put("UpholsteryReceiveList", upholsteryReceiveList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails					= null;
			upholsteryReceiveList	= null;
		}
	}
	
	public Page<InventoryUpholsteryTransfer> getDeployment_Page_UpholsteryTransfer(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "upholsteryTransferId");
		return InventoryUpholsteryTransferRepository.getDeployment_Page_UpholsteryTransfer(companyId, pageable);
	}
	
	@Override
	public List<InventoryUpholsteryTransferDto> getTransferReceivedDetailsList(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		
		TypedQuery<Object[]> 							typedQuery 					= null;
		List<Object[]> 									resultList 					= null; 
		List<InventoryUpholsteryTransferDto> 			upholsteryReceiveList 		= null;
		InventoryUpholsteryTransferDto 					upholsteryReceiveDto		= null;

		try {

			typedQuery = entityManager.createQuery(
					" SELECT IT.upholsteryTransferId, IT.clothTypesId, PL.partlocation_name, Pm.partlocation_name, IT.quantity, IT.transferDate, "
							+ " U.firstName, IT.transferViaId, U2.firstName, IT.transferStatusId, IT.transferReceivedDate, IT.stockTypeId, IT.transferReason, "
							+ " IT.createdDate, IT.lastUpdatedDate, IT.transferReceivedById, CT.clothTypeName, IT.fromLocationId, IT.toLocationId "
							+ " FROM InventoryUpholsteryTransfer AS IT "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.fromLocationId "
							+ " INNER JOIN PartLocations Pm ON Pm.partlocation_id = IT.toLocationId "
							+ " INNER JOIN User U ON U.id = IT.transferById "
							+ " INNER JOIN User U2 ON U2.id = IT.transferReceivedById "
							+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = IT.clothTypesId "
							+ " WHERE IT.transferReceivedById = " + userDetails.getId() + " AND IT.companyId = "+userDetails.getCompany_id()+" "
							+ " AND IT.transferStatusId = "+UpholsteryTransferStatus.TRANSFER_STATUS_TRANSFERED+" "
							+ " AND IT.markForDelete = 0  ORDER BY IT.upholsteryTransferId desc ",
					Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				upholsteryReceiveList = new ArrayList<>();

				for (Object[] result : resultList) {
					upholsteryReceiveDto = new InventoryUpholsteryTransferDto();
					
					upholsteryReceiveDto.setUpholsteryTransferId((long) result[0]);
					upholsteryReceiveDto.setClothTypesId((long) result[1]);
					upholsteryReceiveDto.setFromLocationStr((String) result[2]);
					upholsteryReceiveDto.setToLocationStr((String) result[3]);
					if(result[4] != null)
					upholsteryReceiveDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[4])));
					upholsteryReceiveDto.setTransferDateStr(format1.format((Date) result[5]));
					upholsteryReceiveDto.setTransferByIdStr((String) result[6]);
					upholsteryReceiveDto.setTransferViaId((short) result[7]);
					upholsteryReceiveDto.setTransferViaIdStr(UpholsteryTransferStatus.getTransferViaName(upholsteryReceiveDto.getTransferViaId()));
					upholsteryReceiveDto.setTransferReceivedByIdStr((String) result[8]);
					upholsteryReceiveDto.setTransferStatusId((short) result[9]);
					upholsteryReceiveDto.setTransferReceivedDate((Date) result[10]);
					upholsteryReceiveDto.setStockTypeId((short) result[11]);
					if( result[12] != null)
					upholsteryReceiveDto.setTransferReason((String) result[12]);
					upholsteryReceiveDto.setCreatedDate((Date) result[13]);
					upholsteryReceiveDto.setLastUpdatedDate((Date) result[14]);
					upholsteryReceiveDto.setTransferReceivedById((long) result[15]);
					upholsteryReceiveDto.setClothTypeName((String) result[16]);
					upholsteryReceiveDto.setFromLocationId((int) result[17]);
					upholsteryReceiveDto.setToLocationId((int) result[18]);
					
					upholsteryReceiveList.add(upholsteryReceiveDto);
				}
			}

			return upholsteryReceiveList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			upholsteryReceiveList 	= null;
			upholsteryReceiveDto	= null;
		}
	}
	
	@Override
	public ValueObject getReceiveDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		long									upholsteryTransferId		= 0;
		InventoryUpholsteryTransferDto			receiveDeatils				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			upholsteryTransferId = valueObject.getLong("upholsteryTransferId", 0);
			
			receiveDeatils = getReceiveDetailsList(upholsteryTransferId, userDetails.getCompany_id());
			if(receiveDeatils != null)
			valueObject.put("ReceiveDeatils", receiveDeatils);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails					= null;
			receiveDeatils				= null;
		}
	}
	
	@Override
	public InventoryUpholsteryTransferDto getReceiveDetailsList(long upholsteryTransferId, Integer companyId) throws Exception {
		try {
			Query query = entityManager.createQuery(
					" SELECT IT.upholsteryTransferId, IT.clothTypesId, PL.partlocation_name, Pm.partlocation_name, IT.quantity, IT.transferDate, "
							+ " U.firstName, IT.transferViaId, U2.firstName, IT.transferStatusId, IT.transferReceivedDate, IT.stockTypeId, IT.transferReason, "
							+ " IT.createdDate, IT.lastUpdatedDate, IT.transferReceivedById, CT.clothTypeName, IT.fromLocationId, IT.toLocationId "
							+ " FROM InventoryUpholsteryTransfer AS IT "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.fromLocationId "
							+ " INNER JOIN PartLocations Pm ON Pm.partlocation_id = IT.toLocationId "
							+ " INNER JOIN User U ON U.id = IT.transferById "
							+ " INNER JOIN User U2 ON U2.id = IT.transferReceivedById "
							+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = IT.clothTypesId "
							+ " WHERE IT.upholsteryTransferId = " + upholsteryTransferId + " AND IT.companyId = "+companyId+" "
							+ " AND IT.quantity > 0 AND IT.markForDelete = 0 ORDER BY IT.upholsteryTransferId desc ");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryUpholsteryTransferDto upholsteryReceiveDto;
			if (result != null) {
				upholsteryReceiveDto = new InventoryUpholsteryTransferDto();
				
				upholsteryReceiveDto.setUpholsteryTransferId((long) result[0]);
				upholsteryReceiveDto.setClothTypesId((long) result[1]);
				upholsteryReceiveDto.setFromLocationStr((String) result[2]);
				upholsteryReceiveDto.setToLocationStr((String) result[3]);
				if(result[4] != null)
				upholsteryReceiveDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[4])));
				upholsteryReceiveDto.setTransferDateStr(format1.format((Date) result[5]));
				upholsteryReceiveDto.setTransferByIdStr((String) result[6]);
				upholsteryReceiveDto.setTransferViaId((short) result[7]);
				upholsteryReceiveDto.setTransferViaIdStr(UpholsteryTransferStatus.getTransferViaName(upholsteryReceiveDto.getTransferViaId()));
				upholsteryReceiveDto.setTransferReceivedByIdStr((String) result[8]);
				upholsteryReceiveDto.setTransferStatusId((short) result[9]);
				upholsteryReceiveDto.setTransferReceivedDate((Date) result[10]);
				upholsteryReceiveDto.setStockTypeId((short) result[11]);
				if( result[12] != null)
				upholsteryReceiveDto.setTransferReason((String) result[12]);
				upholsteryReceiveDto.setCreatedDate((Date) result[13]);
				upholsteryReceiveDto.setLastUpdatedDate((Date) result[14]);
				upholsteryReceiveDto.setTransferReceivedById((long) result[15]);
				upholsteryReceiveDto.setClothTypeName((String) result[16]);
				upholsteryReceiveDto.setFromLocationId((int) result[17]);
				upholsteryReceiveDto.setToLocationId((int) result[18]);
				
			} else {
				return null;
			}

			return upholsteryReceiveDto;

		} catch (Exception e) {
			throw e;
		} 
		
	}
	
	@Override
	@Transactional
	public ValueObject saveReceiveUpholstery(ValueObject valueObject) throws Exception {
		short									stockType					= 0;
		long									clothTypeId					= 0;
		int										tolocationId				= 0;
		int										fromlocationId				= 0;
		double									receiveQuantity				= 0;
		ClothInventoryStockTypeDetails			clothStockTypeDetails		= null;
		
		try {
			stockType  			= valueObject.getShort("receiveStockTypeId");
			clothTypeId			= valueObject.getLong("receiveClothTypeId");		
			tolocationId		= valueObject.getInt("receivetoLocationId");
			fromlocationId		= valueObject.getInt("receivefromLocationId");
			receiveQuantity		= valueObject.getDouble("receiveQuantityId");	
			
			updateReceiveUpholstery(valueObject);
			
			clothStockTypeDetails = clothInventoryStockTypeDetailsRepository.validateClothInventoryStockTypeDetails(
					clothTypeId,tolocationId);
			
			if (clothStockTypeDetails != null) {
				
				if(stockType == ClothInvoiceStockType.STOCK_TYPE_NEW) {
					clothInventoryStockTypeDetailsService.updateNewStckQty(clothTypeId, tolocationId, receiveQuantity);
				} else {
					clothInventoryStockTypeDetailsService.updateUsedStckQty(clothTypeId, tolocationId, receiveQuantity);
				}
				
			} else {
				
				clothStockTypeDetails = inventoryBL.updateNewAndOldStockQty(valueObject);
				clothInventoryStockTypeDetailsRepository.save(clothStockTypeDetails);
			}
			
			
			clothInventoryStockTypeDetailsService.updateTransferQty(clothTypeId, fromlocationId, receiveQuantity);
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothStockTypeDetails	= null;
		}
	}
	
	@Override
	@Transactional
	public void updateReceiveUpholstery(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		long									upholsteryTransferId		= 0;
		String									receiveDescription			= null;
		Date									receivedDate				;				
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			upholsteryTransferId = valueObject.getLong("receiveUpholsteryTransferId", 0);
			receiveDescription   = valueObject.getString("receiveDescriptionId");
			receivedDate		 = new Date();
			java.sql.Date sqlDate = new java.sql.Date(receivedDate.getTime());
			
			entityManager.createQuery(
					" UPDATE InventoryUpholsteryTransfer AS VC SET VC.transferReceivedDate = '"+sqlDate+"', "
							+ " VC.lastUpdatedDate = '"+sqlDate+"', VC.lastModifiedById = "+userDetails.getId()+", "
							+ " VC.transferStatusId = "+UpholsteryTransferStatus.TRANSFER_STATUS_RECEIVED+", VC.transferReceivedReason = '"+receiveDescription+"'  "
							+ " WHERE VC.upholsteryTransferId = "+upholsteryTransferId+" "
							+ " AND VC.markForDelete = 0 ")
			.executeUpdate();
			
		} catch (Exception e) {   
			throw e;
		}finally {
			userDetails					= null;
		}
	}
	
	
	@Override
	@Transactional
	public ValueObject saveRejectUpholstery(ValueObject valueObject) throws Exception {
		short									stockType					= 0;
		long									clothTypeId					= 0;
		int										fromlocationId				= 0;
		double									rejectQuantity				= 0;
		
		try {
			stockType  			= valueObject.getShort("rejectStockTypeId");
			clothTypeId			= valueObject.getLong("rejectClothTypeId");		
			fromlocationId		= valueObject.getInt("rejectfromLocationId");
			rejectQuantity		= valueObject.getDouble("rejectQuantityId");	
			
			updateRejectUpholstery(valueObject);
			clothInventoryStockTypeDetailsService.updateRejectTransferQty(clothTypeId, fromlocationId, rejectQuantity, stockType);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateRejectUpholstery(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		long									upholsteryTransferId		= 0;
		String									rejectDescription			= null;
		Date									receivedDate				;				
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			upholsteryTransferId = valueObject.getLong("rejectUpholsteryTransferId", 0);
			rejectDescription   = valueObject.getString("rejectDescriptionId");
			receivedDate		 = new Date();
			java.sql.Date sqlDate = new java.sql.Date(receivedDate.getTime());
			
			entityManager.createQuery(
					" UPDATE InventoryUpholsteryTransfer AS VC SET VC.transferReceivedDate = '"+sqlDate+"', "
							+ " VC.lastUpdatedDate = '"+sqlDate+"', VC.lastModifiedById = "+userDetails.getId()+", "
							+ " VC.transferStatusId = "+UpholsteryTransferStatus.TRANSFER_STATUS_REJECTED+", VC.transferReceivedReason = '"+rejectDescription+"'  "
							+ " WHERE VC.upholsteryTransferId = "+upholsteryTransferId+" "
							+ " AND VC.markForDelete = 0 ")
			.executeUpdate();
			
		} catch (Exception e) {   
			throw e;
		}finally {
			userDetails					= null;
		}
	}
	
	@Override
	@Transactional
	public void updateStockQuantity(Double quantity ,long location, int companyId,long clothTypesId) {
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.newStockQuantity = COALESCE(VC.newStockQuantity,0) - "+quantity+", "
						+ " VC.inTransferQuantity = COALESCE(VC.inTransferQuantity,0) + "+quantity+" "
						+ " WHERE VC.clothTypesId = "+clothTypesId+" "
						+ " AND VC.wareHouseLocationId = "+location+" AND VC.companyId ="+companyId+" AND VC.markForDelete = 0 ").executeUpdate();
	}
	
	@Transactional
	public void addRejectedStockQuantity(Double quantity, int companyId,long stockTypeDetailsId) {
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.newStockQuantity = COALESCE(VC.newStockQuantity,0) + "+quantity+", "
						+ " VC.inTransferQuantity = COALESCE(VC.inTransferQuantity,0) - "+quantity+" "
						+ " WHERE VC.clothInventoryStockTypeDetailsId = "+stockTypeDetailsId+" "
						+ " AND VC.companyId ="+companyId+" AND VC.markForDelete = 0 ").executeUpdate();
	}
	
}