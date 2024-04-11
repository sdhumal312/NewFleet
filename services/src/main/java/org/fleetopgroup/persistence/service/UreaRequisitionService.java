/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.UreaRequisitionAndTransferStatus;
import org.fleetopgroup.persistence.bl.UreaInventoryBL;
import org.fleetopgroup.persistence.bl.UreaRequisitionBL;
import org.fleetopgroup.persistence.bl.UreaTransferBL;
import org.fleetopgroup.persistence.bl.UreaTransferDetailsBL;
import org.fleetopgroup.persistence.dao.UreaInvoiceToDetailsRepository;
import org.fleetopgroup.persistence.dao.UreaRequisitionRepository;
import org.fleetopgroup.persistence.dao.UreaTransferDetailsRepository;
import org.fleetopgroup.persistence.dao.UreaTransferRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UreaRequisitionDto;
import org.fleetopgroup.persistence.dto.UreaTransferDetailsDto;
import org.fleetopgroup.persistence.dto.UreaTransferDto;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.model.UreaRequisition;
import org.fleetopgroup.persistence.model.UreaTransfer;
import org.fleetopgroup.persistence.model.UreaTransferDetails;
import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IUreaRequisitionService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("UreaRequisitionService")
@Transactional
public class UreaRequisitionService implements IUreaRequisitionService {
	
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateTimeFormat 	= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private static final int PAGE_SIZE 					= 10;
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired private UreaRequisitionRepository 		ureaRequisitionRepository;
	@Autowired private UreaTransferRepository			ureaTransferRepository;
	@Autowired private UreaTransferDetailsRepository	ureaTransferDetailsRepository;
	@Autowired private UreaInvoiceToDetailsRepository	ureaInvoiceToDetailsRepository;
	
	@Autowired private IUreaInvoiceToDetailsService		ureaInvoiceToDetailsService;
	@Autowired private IUreaInventoryService			ureaInventoryService;
	
	
	UreaRequisitionBL		ureaRequisitionBL 			= new UreaRequisitionBL();
	UreaTransferBL			ureaTransferBL				= new UreaTransferBL();
	UreaTransferDetailsBL	ureaTransferDetialsBL		= new UreaTransferDetailsBL();
	UreaInventoryBL			ureaInventoryBL				= new UreaInventoryBL();

	
	
	@Override
	public ValueObject sendUreaRequisition(ValueObject valueInObject) throws Exception {
		ValueObject 					valueOutObject						= null;
		CustomUserDetails				userDetails						= null;
		
		try {
			userDetails							= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueOutObject						= new ValueObject();
			valueOutObject 						= validateSendUreaRequisition(valueInObject);
			
			if(!valueOutObject.getBoolean("validateSuccess")) {
				return valueOutObject;
			}
			UreaRequisition prepareUreaRequisition 	= ureaRequisitionBL.prepareUreaRequisition(valueInObject, userDetails);
			UreaRequisition ureaRequisition 		= ureaRequisitionRepository.save(prepareUreaRequisition);
			valueOutObject.put("ureaRequisition", ureaRequisition);
			valueOutObject.put("save", true);
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject validateSendUreaRequisition(ValueObject valueObject) throws Exception {
		try {
			if(valueObject.getInt("ureaRequiredLocationId",0) <= 0 ) {
				valueObject.put("requiredLocationNotFound", true);
				return valueObject;
			}else if(valueObject.getInt("ureaTransferFromLocationId",0) <= 0 ) {
				valueObject.put("fromLocationNotFound", true);
				return valueObject;
			}else if(valueObject.getInt("ureaRequiredLocationId",0) == valueObject.getInt("ureaTransferFromLocationId",0)) {
				valueObject.put("sameLocationFound", true);
				return valueObject;
			}else if(valueObject.getLong("ureaRequisitionReceiverId",0) < 0 ) {
				valueObject.put("requisitionReceiverNotFound", true);
				return valueObject;
			}else if(valueObject.getString("ureaRequiredDate","").isEmpty() ) {
				valueObject.put("requiredDateNotFound", true);
				return valueObject;
			}else if(valueObject.getDouble("ureaRequiredQuantity",0) <= 0 ) {
				valueObject.put("requiredQuantityNotFound", true);
				return valueObject;
			}else {
				valueObject.put("validateSuccess", true);
				return valueObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteUreaRequisition(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaRequisitionRepository.deleteUreaRequisition(valueObject.getLong("ureaRequisitionId"),userDetails.getCompany_id());
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	
	public Page<UreaRequisition> getDeployment_Page_UR(Integer pageNumber, short status, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaRequisitionId");
		return ureaRequisitionRepository.getDeployment_Page_UR(companyId,status, pageable);
	}
	
	
	@Override
	public ValueObject getRequisitionAndTransferListByStatus(ValueObject valueObject) throws Exception {
		List<UreaRequisitionDto>		ureaRequisitionDtoList		= null;
		CustomUserDetails				userDetails					= null;
		Integer				   			pageNumber					= null;
		Page<UreaRequisition> 			page 						= null;
		String 							query						= "";
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			page 				= getDeployment_Page_UR(pageNumber, valueObject.getShort("ureaRequisitionStatusId"), userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);
			valueObject.put("SelectPage", pageNumber);
			valueObject.put("count", page.getTotalElements());
			
			query = " AND UR.ureaRequisitionStatusId= "+valueObject.getShort("ureaRequisitionStatusId")+"";
			valueObject.put("query", query);
			
			ureaRequisitionDtoList = getRequisitionList(valueObject);
			valueObject.put("requisitionList",ureaRequisitionDtoList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Page<UreaRequisition> getDeployment_Page_Your_UR(Integer pageNumber, short status, long userId, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaRequisitionId");
		return ureaRequisitionRepository.getDeployment_Page_Your_Received_UR(companyId,status,userId, pageable);
	}
	
	@Override
	public ValueObject getYourRequisitionAndTransferListByStatus(ValueObject valueObject) throws Exception {
		List<UreaRequisitionDto>		ureaRequisitionDtoList		= null;
		CustomUserDetails				userDetails					= null;
		Integer				   			pageNumber					= null;
		String 							query						= "";
		Page<UreaRequisition> 			page						= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			
			query = " AND UR.ureaRequisitionReceiverId = "+userDetails.getId()+" AND UR.ureaRequisitionStatusId= "+valueObject.getShort("ureaRequisitionStatusId")+"";
			page = getDeployment_Page_Your_UR(pageNumber, valueObject.getShort("ureaRequisitionStatusId"),userDetails.getId(), userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			valueObject.put("query", query);
			ureaRequisitionDtoList = getRequisitionList(valueObject);
			
			valueObject.put("requisitionList",ureaRequisitionDtoList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<UreaRequisitionDto> getRequisitionList(ValueObject valueInObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		List<UreaRequisitionDto>	ureaRequisitionDtoList		= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT UR.ureaRequisitionId, UR.ureaRequiredDate, UR.ureaRequiredLocationId, PL1.partlocation_name,"
							+ " UR.ureaRequisitionSenderId, U1.firstName, UR.ureaRequisitionReceiverId,"
							+ " U2.firstName, UR.ureaRequiredQuantity, UR.ureaReceivedQuantity, UR.ureaRejectedQuantity,"
							+ " UR.ureaRequisitionRemark, UR.ureaRequisitionStatusId, UR.creationDate , UR.createdById, U3.firstName,"
							+ " UR.lastUpdatedDate, UR.lastUpdatedById, U4.firstName, UT.ureaTransferQuantity, UR.ureaTransferFromLocationId, PL2.partlocation_name"
							+ " FROM UreaRequisition AS UR "
							+ " INNER JOIN PartLocations PL1 ON PL1.partlocation_id = UR.ureaRequiredLocationId "
							+ " INNER JOIN PartLocations PL2 ON PL2.partlocation_id = UR.ureaTransferFromLocationId "
							+ " INNER JOIN User U1 ON U1.id = UR.ureaRequisitionSenderId "
							+ " INNER JOIN User U2 ON U2.id = UR.ureaRequisitionReceiverId "
							+ " INNER JOIN User U3 ON U3.id = UR.createdById "
							+ " INNER JOIN User U4 ON U4.id = UR.lastUpdatedById "
							+ " LEFT JOIN UreaTransfer UT ON UT.ureaRequisitionId = UR.ureaRequisitionId "
							+ " where UR.companyId="+userDetails.getCompany_id()+" "+valueInObject.getString("query","")+" AND UR.markForDelete = 0 ", Object[].class);
			
			queryt.setFirstResult((valueInObject.getInt("SelectPage") - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]>	results = queryt.getResultList();
			
			if(results != null && !results.isEmpty()) {
				ureaRequisitionDtoList	=	new ArrayList<>();
				for (Object[] result : results) {
					UreaRequisitionDto	ureaRequisitionDto = new UreaRequisitionDto();
					ureaRequisitionDto.setUreaRequisitionId((Long)result[0]);
					ureaRequisitionDto.setUreaRequiredDate((Date)result[1]);
					ureaRequisitionDto.setUreaRequiredDateStr(dateFormat.format(ureaRequisitionDto.getUreaRequiredDate()));
					ureaRequisitionDto.setUreaRequiredLocationId((Integer)result[2]);
					ureaRequisitionDto.setUreaRequiredLocation((String)result[3]);
					ureaRequisitionDto.setUreaRequisitionSenderId((Long)result[4]);
					ureaRequisitionDto.setUreaRequisitionSender((String)result[5]);
					ureaRequisitionDto.setUreaRequisitionReceiverId((Long)result[6]);
					ureaRequisitionDto.setUreaRequisitionReceiver((String)result[7]);
					ureaRequisitionDto.setUreaRequiredQuantity((Double)result[8]);
					ureaRequisitionDto.setUreaReceivedQuantity((Double)result[9]);
					ureaRequisitionDto.setUreaRejectedQuantity((Double)result[10]);
					ureaRequisitionDto.setUreaRequisitionRemark((String)result[11]);
					ureaRequisitionDto.setUreaRequisitionStatusId((short)result[12]);
					ureaRequisitionDto.setUreaRequisitionStatus(UreaRequisitionAndTransferStatus.getUreaRequisitionAndTransferStatusName(ureaRequisitionDto.getUreaRequisitionStatusId()));
					ureaRequisitionDto.setCreationDate((Date)result[13]);
					ureaRequisitionDto.setCreationDateStr(dateTimeFormat.format(ureaRequisitionDto.getCreationDate()));
					ureaRequisitionDto.setCreatedById((Long)result[14]);
					ureaRequisitionDto.setCreatedBy((String)result[15]);
					ureaRequisitionDto.setLastUpdatedDate((Date)result[16]);
					ureaRequisitionDto.setLastUpdatedDateStr(dateTimeFormat.format(ureaRequisitionDto.getLastUpdatedDate()));
					ureaRequisitionDto.setLastUpdatedById((Long)result[17]);
					ureaRequisitionDto.setLastUpdatedBy((String)result[18]);
					if(result[19] != null) {
						ureaRequisitionDto.setUreaTransferQuantity((Double)result[19]);
					}
					ureaRequisitionDto.setUreaTransferFromLocationId((Integer)result[20]);
					ureaRequisitionDto.setUreaTransferFromLocation((String)result[21]);
					ureaRequisitionDtoList.add(ureaRequisitionDto);
				}
			}
			return ureaRequisitionDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getRequisitionDetailById(ValueObject valueObject) throws Exception {
		List<UreaRequisitionDto>		ureaRequisitionDtoList				= null;
		String 							query								= "";
		try {
			query = " AND UR.ureaRequisitionId = "+valueObject.getLong("ureaRequisitionId")+" ";
			valueObject.put("query", query);
			ureaRequisitionDtoList = getRequisitionList(valueObject);
			
			if(ureaRequisitionDtoList != null && !ureaRequisitionDtoList.isEmpty()) {
				valueObject.put("requisitionList",ureaRequisitionDtoList.get(0));
			}else {
				valueObject.put("notFound",true);
				
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject approveRequisition(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		UreaRequisition				ureaRequisition				= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaRequisition				= ureaRequisitionRepository.getByUreaRequisitionId(valueObject.getLong("ureaRequisitionId",0),userDetails.getCompany_id());
			valueObject					= validateApprovedRequisition(valueObject);
			
			if(!valueObject.getBoolean("validateSuccess")) {
				return valueObject;
			}
			
			
			if(valueObject.getShort("ureaRequisitionStatusId") == UreaRequisitionAndTransferStatus.ACCEPTED_REQUISTION) {
				UreaTransfer ureaTransfer = ureaTransferBL.saveUreaTransfer(valueObject, userDetails);
				ureaTransferRepository.save(ureaTransfer);
				valueObject.put("acceptReq",true);
				ureaRequisitionRepository.updateRequisitionStatusAndRemark(valueObject.getShort("ureaRequisitionStatusId"),ureaRequisition.getUreaRequisitionRemark(),valueObject.getLong("ureaRequisitionId",0),userDetails.getCompany_id() );
			}else if(valueObject.getShort("ureaRequisitionStatusId") == UreaRequisitionAndTransferStatus.REJECTED_REQUISTION) {
				ureaRequisitionRepository.updateRequisitionStatusAndRemark(valueObject.getShort("ureaRequisitionStatusId"),valueObject.getString("ureaRequisitionRejectRemark"),valueObject.getLong("ureaRequisitionId",0),userDetails.getCompany_id() );
				valueObject.put("rejectReq",true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject validateApprovedRequisition(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(valueObject.getLong("ureaRequisitionSenderId") != userDetails.getId()) {
				valueObject.put("notValidUser", true);
				return valueObject;
			}else {
				valueObject.put("validateSuccess", true);
				return valueObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getUreaTransferByRequisitionId(ValueObject valueObject) throws Exception {
		UreaTransfer		ureaTransfer				= null;
		CustomUserDetails			userDetails					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaTransfer = ureaTransferRepository.getUreaTransferByRequisitionId(valueObject.getLong("ureaRequisitionId"),userDetails.getCompany_id());
			valueObject.put("ureaTransfer",ureaTransfer);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getUreaTransferDetails(ValueObject valueInObject) throws Exception {
		List<UreaTransferDetailsDto>	ureaTransferDetailsList		= null;
		try {
			ureaTransferDetailsList = getUreaTransferDetailsByTransferId(valueInObject);
			valueInObject.put("ureaTransferDetails", ureaTransferDetailsList);
			return valueInObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	@Override
	public List<UreaTransferDetailsDto> getUreaTransferDetailsByTransferId(ValueObject valueInObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		List<UreaTransferDetailsDto>	ureaTransferDetailsDtoList		= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT UTD.ureaTransferDetailsId, UTD.ureaTransferId, UTD.ureaTransferFromLocationId, "
							+ " PL.partlocation_name, UTD.ureaInvoiceToDetailsId, UID.stockQuantity, UTD.ureaInventoryTransferQuantity,"
							+ " UID.ureaInvoiceId, UI.ureaInvoiceNumber, UID.manufacturerId, UID.discount, UID.tax, UID.unitprice, UID.vendor_id "
							+ " FROM UreaTransferDetails AS UTD "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = UTD.ureaTransferFromLocationId "
							+ " INNER JOIN UreaInvoiceToDetails UID ON UID.ureaInvoiceToDetailsId = UTD.ureaInvoiceToDetailsId "
							+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = UID.ureaInvoiceId "
							+ " where UTD.companyId="+userDetails.getCompany_id()+" AND UTD.ureaTransferId = "+valueInObject.getLong("ureaTransferId")+ " AND UTD.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			
			if(results != null && !results.isEmpty()) {
				ureaTransferDetailsDtoList	=	new ArrayList<>();
				for (Object[] result : results) {
					UreaTransferDetailsDto	ureaTransferDetailsDto = new UreaTransferDetailsDto();
					ureaTransferDetailsDto.setUreaTransferDetailsId((Long)result[0]);
					ureaTransferDetailsDto.setUreaTransferId((Long)result[1]);
					ureaTransferDetailsDto.setUreaTransferFromLocationId((Integer)result[2]);
					ureaTransferDetailsDto.setUreaTransferFromLocation((String)result[3]);
					ureaTransferDetailsDto.setUreaInvoiceToDetailsId((Long)result[4]);
					ureaTransferDetailsDto.setUreaStockQuantity((Double)result[5]);
					ureaTransferDetailsDto.setUreaInventoryTransferQuantity((Double)result[6]);
					ureaTransferDetailsDto.setUreaInvoiceId((Long)result[7]);
					ureaTransferDetailsDto.setUreaInvoiceNumber((Long)result[8]);
					ureaTransferDetailsDto.setManufacturerId((Long)result[9]);
					ureaTransferDetailsDto.setDiscount((Double)result[10]);
					ureaTransferDetailsDto.setTax((Double)result[11]);
					ureaTransferDetailsDto.setUnitprice((Double)result[12]);
					ureaTransferDetailsDto.setVendor_id((Integer)result[13]);
					ureaTransferDetailsDtoList.add(ureaTransferDetailsDto);
				}
			}
			
			return ureaTransferDetailsDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject saveTransfer(ValueObject valueInObject) throws Exception {
		ValueObject 					valueOutObject						= null;
		UreaTransferDetails 			ureaTransferDetails 				= null;
		CustomUserDetails				userDetails							= null;
		UreaInvoiceToDetails			ureaInvoiceToDetails				= null	;
		UreaInvoice						ureaInvoice							= null	;
		double							updatedStockQuantity				= 0.0;
		double							updatedTransferQuantity				= 0.0;
		double							updatedTotalTransferQunatity		= 0.0;
		String							query								= "";
		double							transferQuantity					= 0;
		double							ureaTransferQuantity					= 0;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueOutObject					= new ValueObject();
			
			valueOutObject 					= validateSaveTransfer(valueInObject);
			
			if(!valueOutObject.getBoolean("validateSuccess")) {
				return valueOutObject;
			}
			
			/**save Transfer Details*/
			ureaTransferDetails 			= ureaTransferDetialsBL.saveUreaTransferDetails(valueInObject, userDetails);
			ureaTransferDetailsRepository.save(ureaTransferDetails);
			
			/***update TransferQuantity in UreaTransferTable*/
			query = " UT.ureaTransferQuantity + "+ valueInObject.getDouble("ureaInventoryTransferQuantity") + ""; 
			valueInObject.put("query", query);
			updateTransferQuantityInUreaTransfer(valueInObject);
			
			/***update StockQuantity and TransferQuantity In ureaInvoiceToDetails*/
			ureaInvoiceToDetails 	= 	ureaInvoiceToDetailsService.getUreaInvoiceToDetailsById(valueInObject.getLong("ureaInvoiceToDetailsId"));
		if(ureaInvoiceToDetails.getTransferQuantity() == null) {
			transferQuantity = 0;
		}else {
			transferQuantity = ureaInvoiceToDetails.getTransferQuantity();
		}
			updatedStockQuantity 	= (ureaInvoiceToDetails.getStockQuantity() - valueInObject.getDouble("ureaInventoryTransferQuantity",0) );
			updatedTransferQuantity = (transferQuantity + valueInObject.getDouble("ureaInventoryTransferQuantity",0));
			
			valueInObject.put("updatedStockQuantity", updatedStockQuantity);
			valueInObject.put("updatedTransferQuantity", updatedTransferQuantity);
			
			ureaInvoiceToDetailsService.updateUreaTransferQuantityAndUpdateStockQuantityInUreaInvoiceToDetails(valueInObject);
			
			/***update updatedTotalTransferQunatity In ureaInvoice*/
			ureaInvoice 					= ureaInventoryService.getUreaInvoiceByInvoiceId(valueInObject.getLong("ureaInvoiceId"), userDetails.getCompany_id());
			if(ureaInvoice.getTotalTransferQuantity() == null) {
				ureaTransferQuantity = 0;
			}else {
				ureaTransferQuantity = ureaInvoice.getTotalTransferQuantity();
			}
			updatedTotalTransferQunatity 	= (ureaTransferQuantity+valueInObject.getDouble("ureaInventoryTransferQuantity"));
			ureaInventoryService.updateTotalTransferQuantity(updatedTotalTransferQunatity,valueInObject.getLong("ureaInvoiceId"),ureaInvoice.getWareHouseLocation());
			
			
			valueOutObject.put("save", true);
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject validateSaveTransfer(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		UreaInvoiceToDetails		ureaInvoiceToDetails 		= null; 	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(valueObject.getInt("ureaTransferFromLocationId",0) > 0 ) {
			ureaInvoiceToDetails 	= 	ureaInvoiceToDetailsService.getUreaInvoiceToDetailsById(valueObject.getLong("ureaInvoiceToDetailsId"));
			}
			
			if(valueObject.getLong("ureaRequisitionSenderId",0) != userDetails.getId()) {
				valueObject.put("notValidUser", true);
				return valueObject;
			}else if(valueObject.getInt("ureaTransferFromLocationId",0) <=0 ) {
				valueObject.put("transferFromLocationNotFound", true);
				return valueObject;
			}else if(valueObject.getInt("ureaToLocationId",0) <=0 ){
				valueObject.put("transferToLocationNotFound", true);
				return valueObject;
			}else if(valueObject.getInt("ureaToLocationId",0) == valueObject.getInt("ureaTransferFromLocationId",0) ){
				valueObject.put("transferReceiveLocationNotSame", true);
				return valueObject;
			}
			else if(valueObject.getDouble("ureaInventoryTransferQuantity",0) <=0 ){
				valueObject.put("transferQuantityNotFound", true);
				return valueObject;
				
			}else if(valueObject.getDouble("ureaInventoryTransferQuantity",0) > ((valueObject.getDouble("totalRequiredQuantity"))-(valueObject.getDouble("transferedQuantity",0))) ){
				valueObject.put("invalidTransferQuantity", true);
				return valueObject;
				
			}else if(ureaInvoiceToDetails == null) {
				valueObject.put("invoiceNotFound", true);
				return valueObject;
			}else {
				valueObject.put("validateSuccess", true);
				return valueObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void updateTransferQuantityInUreaTransfer(ValueObject valueInObject) throws Exception {
		try {
			entityManager.createQuery("UPDATE UreaTransfer AS UT SET UT.ureaTransferQuantity  = "+valueInObject.getString("query")+""
					+ " WHERE UT.ureaTransferId=" + valueInObject.getLong("ureaTransferId") + "").executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject removeTransfer(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails							= null;
		UreaTransfer					ureaTransfer 						= null;
		UreaInvoice						ureaInvoice							= null	;
		UreaInvoiceToDetails			ureaInvoiceToDetails				= null	;
		Double							quantity							= 0.0;
		double							updatedStockQuantity				= 0.0;
		double							updatedTransferQuantity				= 0.0;
		double							updatedTotalTransferQunatity		= 0.0;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			/***update TransferQuantity in UreaTransferTable*/
			ureaTransfer 	= ureaTransferRepository.getTransferDetailsByUreaTransferId(valueObject.getLong("ureaTransferId"),userDetails.getCompany_id());
			quantity 		= (ureaTransfer.getUreaTransferQuantity() - valueObject.getDouble("ureaInventoryTransferQuantity")); //remove urea quantiy from urea transfertable
			valueObject.put("query", quantity);
			updateTransferQuantityInUreaTransfer(valueObject);
			
			/***update StockQuantity and TransferQuantity In ureaInvoiceToDetails*/
			ureaInvoiceToDetails 		= 	ureaInvoiceToDetailsService.getUreaInvoiceToDetailsById(valueObject.getLong("ureaInvoiceToDetailsId"));
			
			updatedStockQuantity 		= (ureaInvoiceToDetails.getStockQuantity() + valueObject.getDouble("ureaInventoryTransferQuantity") );
			updatedTransferQuantity 	= (ureaInvoiceToDetails.getTransferQuantity() - valueObject.getDouble("ureaInventoryTransferQuantity"));
			
			valueObject.put("updatedStockQuantity", updatedStockQuantity);
			valueObject.put("updatedTransferQuantity", updatedTransferQuantity);
			
			ureaInvoiceToDetailsService.updateUreaTransferQuantityAndUpdateStockQuantityInUreaInvoiceToDetails(valueObject);
			 
			/***update updatedTotalTransferQunatity In ureaInvoice*/
			
			ureaInvoice 				= 	ureaInventoryService.getUreaInvoiceByInvoiceId(ureaInvoiceToDetails.getUreaInvoiceId(), userDetails.getCompany_id());
			
			updatedTotalTransferQunatity = (ureaInvoice.getTotalTransferQuantity()-valueObject.getDouble("ureaInventoryTransferQuantity"));
			ureaInventoryService.updateTotalTransferQuantity(updatedTotalTransferQunatity,ureaInvoiceToDetails.getUreaInvoiceId(),ureaInvoice.getWareHouseLocation());
			
			/***remove TransferDetail */
			ureaTransferDetailsRepository.removeTransfer(valueObject.getLong("ureaTransferDetailsId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject completeTransfer(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject 	= validateCompleteTransfer(valueObject);
			if(!valueObject.getBoolean("validateSuccess")) {
				return valueObject;
			}
			ureaTransferRepository.completeTransfer(UreaRequisitionAndTransferStatus.COMPLETE_TRANSFERED,valueObject.getString("transferRemark"),valueObject.getLong("ureaTransferId"),userDetails.getCompany_id());
			ureaRequisitionRepository.updateRequisitionStatus(UreaRequisitionAndTransferStatus.COMPLETE_TRANSFERED,valueObject.getLong("ureaRequisitionId"),userDetails.getCompany_id());
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	@Override
	public ValueObject validateCompleteTransfer(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(valueObject.getLong("ureaRequisitionSenderId",0) != userDetails.getId()) {
				valueObject.put("notValidUser", true);
				return valueObject;
			}else {
				valueObject.put("validateSuccess", true);
				return valueObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject received_Reject_Urea(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		List<UreaTransferDetailsDto>	ureaTransferDetailsList			= null;
		List<UreaInvoiceToDetails>		ureaInvoiceToDetailsList		= null;
		UreaInvoiceToDetails			ureaInvoiceToDetails			= null;
		UreaInvoice						ureaInvoice						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaInvoiceToDetailsList 	= new ArrayList<>();
			ureaTransferDetailsList 	= getUreaTransferDetailsByTransferId(valueObject);
			Date updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
			 
			valueObject 				= validateReceive_RejectUrea(valueObject);
			if(!valueObject.getBoolean("validateSuccess")) {
				return valueObject;
			}
			/**update in ureaTransfer and Requisition table*/
			ureaTransferRepository.updateTransferStatus(valueObject.getShort("received_reject_status"),valueObject.getLong("ureaTransferId"),userDetails.getCompany_id());
			
			if(UreaRequisitionAndTransferStatus.RECEIVED == valueObject.getShort("received_reject_status") ) {
				/***update received quantity in ureaRequisition table*/
				ureaRequisitionRepository.updateReceivedUreaQuantityInUreaRequisition(valueObject.getShort("received_reject_status"),valueObject.getString("remark"),valueObject.getDouble("received_RejectQuantity"),valueObject.getLong("ureaRequisitionId"),userDetails.getCompany_id());
				
				/**create new row in ureaInvoiceToDetails table with Transferd Quantity with their respective cost */
				
				if(ureaTransferDetailsList != null && !ureaTransferDetailsList.isEmpty()) {
					for(UreaTransferDetailsDto dto: ureaTransferDetailsList) {
						ureaInvoiceToDetails = new UreaInvoiceToDetails();
						
						ureaInvoiceToDetails.setUnitprice(dto.getUnitprice());
						ureaInvoiceToDetails.setTax(dto.getTax());
						ureaInvoiceToDetails.setDiscount(dto.getDiscount());
						
						double tax = ((dto.getUreaInventoryTransferQuantity()*ureaInvoiceToDetails.getUnitprice()) * (dto.getTax() / 100));
						
						double taxCost = ((dto.getUreaInventoryTransferQuantity()*ureaInvoiceToDetails.getUnitprice()) + (tax));
						double discount = ((taxCost) * (dto.getDiscount() / 100));
						double totalCost = taxCost - discount;
						
						
						ureaInvoiceToDetails.setTotal(totalCost);
						ureaInvoiceToDetails.setQuantity(dto.getUreaInventoryTransferQuantity());
						ureaInvoiceToDetails.setStockQuantity(dto.getUreaInventoryTransferQuantity());
						ureaInvoiceToDetails.setManufacturerId(dto.getManufacturerId());
						ureaInvoiceToDetails.setUsedQuantity(0.0);
						ureaInvoiceToDetails.setVendor_id(dto.getVendor_id());
						ureaInvoiceToDetails.setWareHouseLocation(valueObject.getInt("ureaToLocationId"));
						ureaInvoiceToDetails.setSubLocationId(0);
						ureaInvoiceToDetails.setTransferQuantity(0.0);
						ureaInvoiceToDetails.setCreatedById(userDetails.getId());
						ureaInvoiceToDetails.setLastModifiedById(userDetails.getId());
						ureaInvoiceToDetails.setCreated(new Date());
						ureaInvoiceToDetails.setLastupdated(new Date());
						ureaInvoiceToDetails.setCompanyId(userDetails.getCompany_id());
						ureaInvoiceToDetails.setUreaInvoiceId(dto.getUreaInvoiceId());
						ureaInvoiceToDetails.setUreaTransferDetailsId(dto.getUreaTransferDetailsId());
						ureaInvoiceToDetailsList.add(ureaInvoiceToDetails);
					}
				}
				ureaInvoiceToDetailsRepository.saveAll(ureaInvoiceToDetailsList);
				valueObject.put("received", true);
				
			}else if(UreaRequisitionAndTransferStatus.REJECTED == valueObject.getShort("received_reject_status") ) {
				/***update received quantity in ureaRequisition table*/
				ureaRequisitionRepository.updateRejectUreaQuantityInUreaRequisition(valueObject.getShort("received_reject_status"),valueObject.getString("remark"),valueObject.getDouble("received_RejectQuantity"),valueObject.getLong("ureaRequisitionId"),userDetails.getCompany_id());
				
				if(ureaTransferDetailsList != null && !ureaTransferDetailsList.isEmpty()) {
					/**Update transfer quantity in ureaInvoice and ureainvoiceToDetails (original quantity)*/
					for(UreaTransferDetailsDto dto: ureaTransferDetailsList) {
						ureaInvoice = 	ureaInventoryService.getUreaInvoiceByInvoiceId(dto.getUreaInvoiceId(), userDetails.getCompany_id());
						
						entityManager.createQuery("Update UreaInvoiceToDetails AS UID SET UID.stockQuantity = UID.stockQuantity+" +dto.getUreaInventoryTransferQuantity()+", "
								+ " UID.transferQuantity = UID.transferQuantity - "+dto.getUreaInventoryTransferQuantity()+", UID.lastupdated = '"+sqlDate+"', "
								+ " UID.lastModifiedById = "+userDetails.getId()+" "
								+ " where UID.ureaInvoiceToDetailsId = "+dto.getUreaInvoiceToDetailsId()+" AND UID.companyId = "+userDetails.getCompany_id()+" ").executeUpdate();
						
						
						ureaInventoryService.updateTotalTransferQuantity((ureaInvoice.getTotalTransferQuantity()-dto.getUreaInventoryTransferQuantity()),dto.getUreaInvoiceId(),dto.getUreaTransferFromLocationId() );
					}
				}	
				valueObject.put("rejected", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	@Override
	public ValueObject validateReceive_RejectUrea(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(valueObject.getLong("ureaRequisitionReceiverId",0) != userDetails.getId()) {
				valueObject.put("notValidUser", true);
				return valueObject;
			}else {
				valueObject.put("validateSuccess", true);
				return valueObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getUreaTransferByTransferId(ValueObject valueObject) throws Exception {
		UreaTransferDto					ureaTransferDto				= null;
		CustomUserDetails				userDetails						= null;
		Object[] result = null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			javax.persistence.Query query  = entityManager
					.createQuery("SELECT UT.ureaTransferId, UT.ureaRequisitionId, UT.ureaTransferDate, "
							+ " UT.ureaTransferToLoactionId, PL.partlocation_name, UT.ureaRequisitionSenderId, U1.firstName, "
							+ " UT.ureaTransferById, U2.firstName, UT.ureaTransferStatusId, UT.ureaTransferQuantity, UT.ureaTransferRemark,"
							+ " UT.creationDate, UT.createdById, U3.firstName, UT.lastUpdatedDate, UT.lastUpdatedById, U4.firstName "
							+ " FROM UreaTransfer AS UT "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = UT.ureaTransferToLoactionId "
							+ " INNER JOIN User U1 ON U1.id = UT.ureaRequisitionSenderId "
							+ " INNER JOIN User U2 ON U2.id = UT.ureaTransferById "
							+ " INNER JOIN User U3 ON U3.id = UT.createdById "
							+ " INNER JOIN User U4 ON U4.id = UT.lastUpdatedById "
							+ " where UT.companyId="+userDetails.getCompany_id()+" AND UT.ureaTransferId = "+valueObject.getLong("ureaTransferId")+ " AND UT.markForDelete = 0 ");
			
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			
			if(result != null ) {
				ureaTransferDto = new UreaTransferDto();
				ureaTransferDto.setUreaTransferId((Long)result[0]);
				ureaTransferDto.setUreaRequisitionId((Long)result[1]);
				ureaTransferDto.setUreaTransferDate((Date)result[2]);
				ureaTransferDto.setUreaTransferDateStr(dateFormat.format(ureaTransferDto.getUreaTransferDate()));
				ureaTransferDto.setUreaTransferToLoactionId((Integer)result[3]);
				ureaTransferDto.setUreaTransferToLoaction((String)result[4]);
				ureaTransferDto.setUreaRequisitionSenderId((Long)result[5]);
				ureaTransferDto.setUreaRequisitionSender((String)result[6]);
				ureaTransferDto.setUreaTransferById((Long)result[7]);
				ureaTransferDto.setUreaTransferBy((String)result[8]);
				ureaTransferDto.setUreaTransferStatusId((short)result[9]);
				ureaTransferDto.setUreaTransferStatus(UreaRequisitionAndTransferStatus.getUreaRequisitionAndTransferStatusName((short)result[9]));
				ureaTransferDto.setUreaTransferQuantity((Double)result[10]);
				ureaTransferDto.setUreaTransferRemark((String)result[11]);
				ureaTransferDto.setCreationDate((Date)result[12]);
				ureaTransferDto.setCreationDateStr(dateTimeFormat.format(ureaTransferDto.getCreationDate()));
				ureaTransferDto.setCreatedById((Long)result[13]);
				ureaTransferDto.setCreatedBy((String)result[14]);
				ureaTransferDto.setLastUpdatedDate((Date)result[15]);
				ureaTransferDto.setLastUpdatedDateStr(dateTimeFormat.format(ureaTransferDto.getLastUpdatedDate()));
				ureaTransferDto.setLastUpdatedById((Long)result[16]);
				ureaTransferDto.setLastUpdatedBy((String)result[17]);
			}
			
			valueObject.put("ureaTransferDto", ureaTransferDto);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
